plugins {
    id("java")
}

group = "dev.ole.netease"
version = "1.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform(rootProject.libs.junitBom))
    testImplementation(rootProject.libs.bundles.testing)

    testAnnotationProcessor(rootProject.libs.lombok)

    implementation(rootProject.libs.netty5)
    implementation(rootProject.libs.bundles.utils)

    annotationProcessor(rootProject.libs.lombok)
}

tasks.test {
    useJUnitPlatform()
}

allprojects {
    apply(plugin = "maven-publish")
}
