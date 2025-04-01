// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
   alias(libs.plugins.android.application) apply false
   alias(libs.plugins.jetbrains.kotlin.android) apply false
   alias(libs.plugins.jetbrains.kotlin.serialization) apply false

   // Use this version instead of 2.X listed in the zyBook
   id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}