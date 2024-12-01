import de.undercouch.gradle.tasks.download.Download

plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    jacoco
    id("de.undercouch.download") version "5.6.0"
    id("info.solidsoft.pitest") version "1.15.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "at.aau"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("org.evomaster:evomaster-client-java-controller:3.3.0")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("io.rest-assured:spring-mock-mvc")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.vintage:junit-vintage-engine")
    testRuntimeOnly("com.h2database:h2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport, tasks.pitest)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.pitest {
    dependsOn(tasks.test, tasks.jacocoTestReport)
}

pitest {
    pitestVersion = "1.15.2"
    junit5PluginVersion = "1.2.1"
    threads = Runtime.getRuntime().availableProcessors()
    jvmArgs = listOf("-Xmx16384m", "-Dspring.test.constructor.autowire.mode=all")
}

val testSourceDir = layout.projectDirectory.dir("src/test/java")
val libDir = layout.projectDirectory.dir("lib")

val randoopVersion = "4.3.3"
val randoopJarName = "randoop-all-$randoopVersion.jar"
val randoopJarUrl = "https://github.com/randoop/randoop/releases/download/v$randoopVersion/$randoopJarName"
val randoopJunitOutputDir = testSourceDir
val randoopJunitPackage = "at.aau.workoutservicejava.randoop"
val randoopClassListPath = libDir.file("class-list.txt")
val randoopJarFile = libDir.file(randoopJarName)

tasks.register("createLibDir") {
    group = "Test Generation"
    description = "Create the lib directory if it does not exist."

    doLast {
        if (!libDir.asFile.exists()) {
            libDir.asFile.mkdirs()
        }
    }
}

tasks.register<Download>("downloadRandoopJar") {
    group = "Test Generation"
    description = "Download the Randoop jar file required for test generation."

    dependsOn("createLibDir")

    onlyIf { !randoopJarFile.asFile.exists() }
    src("https://github.com/randoop/randoop/releases/download/v$randoopVersion/randoop-all-$randoopVersion.jar")
    dest(randoopJarFile)
    overwrite(false)
}

tasks.register("generateClassList") {
    group = "Test Generation"
    description = "Generate class-list.txt with a list of all classes in the project."

    val outputFile = randoopClassListPath.asFile
    val classesDirs = sourceSets["main"].output.classesDirs

    dependsOn(tasks.classes, "createLibDir")

    onlyIf { !randoopClassListPath.asFile.exists() }
    doLast {
        val classNames = mutableListOf<String>()
        classesDirs.forEach { dir ->
                dir.walkTopDown()
                    .filter { it.isFile && it.extension == "class" }
                    .forEach { file ->
                        val relativeFilePath = file.relativeTo(dir).path
                        val className = relativeFilePath
                            .replace('/', '.')
                            .replace('\\', '.')
                            .removeSuffix(".class")
                        classNames.add(className)
                    }
        }
        outputFile.writeText(classNames.joinToString("\n"))
    }
}

tasks.register<JavaExec>("runRandoop") {
    group = "Test Generation"
    description = "Run Randoop to generate unit tests for the specified classes."

    dependsOn("generateClassList", "downloadRandoopJar")
    maxHeapSize = "16g"

    mainClass.set("randoop.main.Main")
    classpath = files(
        sourceSets["main"].runtimeClasspath,
        randoopJarFile,
    )
    args = listOf(
        "gentests",
        "--classlist=$randoopClassListPath",
        "--junit-package-name=$randoopJunitPackage",
        "--junit-output-dir=$randoopJunitOutputDir",
        "--jvm-max-memory=16g",
        "--omit-methods=.*toString",
    )

    logging.captureStandardOutput(LogLevel.INFO)
}

val evoMasterVersion = "3.3.0"
val evoMasterJarName = "evomaster.jar"
val evoMasterJarUrl = "https://github.com/WebFuzzing/EvoMaster/releases/download/v$evoMasterVersion/$evoMasterJarName"
val evoMasterJarFile = libDir.file(evoMasterJarName)

tasks.register<Download>("downloadEvoMasterJar") {
    group = "Test Generation"
    description = "Download the EvoMaster jar file required for test generation."

    dependsOn("createLibDir")

    onlyIf { !evoMasterJarFile.asFile.exists() }
    src(evoMasterJarUrl)
    dest(evoMasterJarFile)
    overwrite(false)
}

tasks.register<JavaExec>("runSutController") {
    group = "Test Generation"
    description = "Run EvoMaster SuT Controller implementation."

    maxHeapSize = "16g"

    mainClass.set("at.aau.workoutservicejava.EMDriver")
    classpath = files(
        sourceSets["main"].runtimeClasspath,
        sourceSets["test"].runtimeClasspath,
    )
    jvmArgs = listOf(
        "-Djdk.attach.allowAttachSelf=true",
        "--add-opens", "java.base/java.util=ALL-UNNAMED",
        "--add-opens", "java.base/java.util.regex=ALL-UNNAMED",
        "--add-opens", "java.base/java.net=ALL-UNNAMED",
        "--add-opens", "java.base/java.lang=ALL-UNNAMED"
    )

    logging.captureStandardOutput(LogLevel.INFO)
}

tasks.register<Exec>("runEvoMasterJar") {
    group = "Test Generation"
    description =
        "Run the EvoMaster JAR file to generate API tests using the SuT Controller. Requires a running SuT Controller."

    dependsOn("downloadEvoMasterJar")

    commandLine(
        "java", "-jar", evoMasterJarFile,
        "--outputFolder", testSourceDir.dir("at/aau/workoutservicejava/evomaster"),
        "--outputFormat", "JAVA_JUNIT_5",
        "--maxTime", "12h",
        "--prematureStop", "30m",
        "--namingStrategy", "ACTION",
    )
}