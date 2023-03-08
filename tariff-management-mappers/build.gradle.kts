plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":tariff-management-api-jackson"))
    implementation(project(":tariff-management-common"))

    testImplementation(kotlin("test-junit"))
}