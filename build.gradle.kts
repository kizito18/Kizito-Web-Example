

buildscript {

    dependencies {
        //Proto data store
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.6.10")
    }
}








// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}