plugins {
	alias(libs.plugins.com.android.library)
	alias(libs.plugins.org.jetbrains.kotlin.android)
	alias(libs.plugins.kapt)
}

android {
	namespace = "com.cooder.library.ui"
	compileSdk = libs.versions.compileSdk.get().toInt()
	
	defaultConfig {
		minSdk = libs.versions.minSdk.get().toInt()
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
	}
	buildTypes {
		release {
			isMinifyEnabled = true
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
		debug {
			isMinifyEnabled = false
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
	implementation(project(":cooder-library"))
	
	implementation(libs.core.kts)
	implementation(libs.appcompat)
	implementation(libs.material)
	implementation(libs.constraintlayout)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.espresso.core)
}