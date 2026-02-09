plugins {
    id("java")
}

group = "ru.map4yk"
version = "1.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT") // Paper
    compileOnly("org.projectlombok:lombok:1.18.42") // Lombok
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}