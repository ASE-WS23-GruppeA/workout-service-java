import de.undercouch.gradle.tasks.download.Download

plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    jacoco
    id("de.undercouch.download") version "5.6.0"
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
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("io.rest-assured:spring-mock-mvc")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.vintage:junit-vintage-engine")

    testRuntimeOnly("com.h2database:h2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

val randoopVersion = "4.3.3"
val randoopJarName = "randoop-all-$randoopVersion.jar"
val randoopJarUrl = "https://github.com/randoop/randoop/releases/download/v$randoopVersion/$randoopJarName"

val junitOutputDir = layout.projectDirectory.dir("src/test/java")
val junitPackage = "at.aau.workoutservicejava.randoop"

val libDir = layout.projectDirectory.dir("lib")
val classListPath = libDir.file("class-list.txt")
val randoopJarFile = libDir.file(randoopJarName)

tasks.register<JavaExec>("runRandoop") {
    group = "Test Generation"
    description = "Run Randoop to generate unit tests for the specified classes."

    dependsOn("generateClassList", "downloadRandoopJar", tasks.classes)
    maxHeapSize = "16g"

    mainClass.set("randoop.main.Main")
    classpath = files(
        sourceSets["main"].runtimeClasspath,
        randoopJarFile,
    )
    args = listOf(
        "gentests",
        "--classlist=$classListPath",
        "--junit-package-name=$junitPackage",
        "--junit-output-dir=$junitOutputDir",
        "--jvm-max-memory=16g",
        "--omit-methods=.*toString",
//        "--output-limit=10",
    )
}

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

    val outputFile = classListPath.asFile
    val classesDirs = sourceSets["main"].output.classesDirs

    dependsOn(tasks.classes, "createLibDir")

    onlyIf { !classListPath.asFile.exists() }
    doLast {
        val classNames = mutableListOf<String>()
        classesDirs.forEach { dir ->
            if (dir.exists()) {
                dir.walkTopDown()
                    .filter { it.isFile && it.extension == "class" }
                    .forEach { file ->
                        val relativePath = file.relativeTo(dir).path
                        val className = relativePath
                            .replace('/', '.')
                            .replace('\\', '.')
                            .removeSuffix(".class")
                        classNames.add(className)
                    }
            }
        }
        outputFile.writeText(classNames.joinToString("\n"))
    }
}