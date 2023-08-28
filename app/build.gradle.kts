plugins {
	alias(libs.plugins.com.android.application)
	alias(libs.plugins.org.jetbrains.kotlin.android)
	alias(libs.plugins.kapt)
}

android {
	namespace = "com.cooder.library.app"
	compileSdk = libs.versions.compileSdk.get().toInt()
	
	defaultConfig {
		applicationId = "com.cooder.library.app"
		minSdk = libs.versions.minSdk.get().toInt()
		targetSdk = libs.versions.targetSdk.get().toInt()
		versionCode = libs.versions.versionCode.get().toInt()
		versionName = libs.versions.versionName.get()
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	buildTypes {
		release {
			isMinifyEnabled = true
			isShrinkResources = true
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
		debug {
			isMinifyEnabled = false
			isShrinkResources = false
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
	buildToolsVersion = libs.versions.buildToolsVersion.get()
	
	buildFeatures {
		viewBinding = true
		dataBinding = true
	}
}

kotlin {
	sourceSets.all {
		languageSettings {
			languageVersion = "2.0"
		}
	}
}

dependencies {
	
	implementation(project(":cooder-ui"))
	implementation(project(":cooder-library"))
	
	implementation(libs.core.kts)
	implementation(libs.appcompat)
	implementation(libs.material)
	implementation(libs.constraintlayout)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.espresso.core)
	
	implementation(libs.glide)
	
	implementation(libs.lifecycle.runtime.ktx)
}