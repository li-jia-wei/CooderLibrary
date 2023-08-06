plugins {
	alias(libs.plugins.com.android.library)
	alias(libs.plugins.org.jetbrains.kotlin.android)
	alias(libs.plugins.kapt)
	alias(libs.plugins.ksp)
}

android {
	namespace = "com.cooder.library.library"
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

ksp {
	arg("room.schemaLocation", "$projectDir/build/schema/room")
}

dependencies {
	
	implementation(libs.core.kts)
	implementation(libs.appcompat)
	implementation(libs.material)
	implementation(libs.constraintlayout)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.espresso.core)
	
	implementation(libs.room.runtime)
	ksp(libs.room.compiler)
}