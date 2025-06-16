plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.uney.android.mls.mlswrappertestapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.uney.android.mls.mlswrappertestapp"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "MOCK_ACCESS_TOKEN", "\"eyJ0eXAiOiJqd3QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik44Q0NHMWVCOU9tLWNKNXZERDUyS24tUFJrdFJhSDhpcFM4TnRPN01IQnMifQ.eyJleHAiOjE3NzEwNzI2ODcsImlhdCI6MTc0OTQ3MjY4Nywic3ViIjoiYzA5N2JmODktNTU4MC00OGZlLWFkNTItMmYzOTIyZTY5ZTA5IiwidW5pcXVlRGV2aWNlSWQiOiIzMDRjMTM4Ny0wYzkyLTRmYzEtOTgwOS1kOTQwNWFiY2Q3ZDgiLCJqdGkiOiJiYzZkNjNhYS1jMWZlLTQ3ZTItOTQ5Ny1mMDk3YWM2Y2M4ZmIiLCJhdXRoQ2xpZW50IjoiY29uc3VtZXIiLCJ1c2VybmFtZSI6IkNoYW1vZGE2NjgiLCJlbWFpbCI6ImNoYW1vZGE2NjhAdW5leS5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiY2hhdFRva2VuIjoiTU14eUlXNnVTc3hrWG5TeW9oTE5HN2VCSWRjeURqekkiLCJjaGF0SmlkIjoiYzA5N2JmODktNTU4MC00OGZlLWFkNTItMmYzOTIyZTY5ZTA5QG1scy1lamItbmV4dC5rY2hhdC5jb20ifQ.JYUcqvNKtVh0PhAka25wgwsSQQ3j94reFHZy237UCAhWwbXt9qLigNFLkZ7lDa1WNy8tYI2IThly8L2hCsQHsGJMw8mC-eKaCEzU-OtaIpA5vVyISxU1zV69suOQno3EWfyJ5oTHndpql7dPSgyulbKBjsSbMZLu2nnMD_WYr8RPIP9NBqYvhKr5cZKrcYFInf5TwQlma_B6DHVJDFTcMsZraIxN0rDSqPrXLczdJNJNe5g0Cw0kHUJ9Ci7DCOY8tKxhm-ED7gUZt0I_0krKkczMSLlqz5tYnCHjHjKSSzcCoAXlXcO1Yn903OiIEP_X85GXfUa_xJ63CeLOjv7RBulkIGC5_FAQ685IA1sm8ymsotN5JuXezUqjzjF78srPX3sLhnnxQbGJtqSlkky0jHwjlRrqLiO-Nv_r9SK5gX5r7g8x6oifleIgJ5TmdIClPP6oO64lLKqmuUu4xvnpvswEeg1pm0eP-elPYbJFspK42D0eJ0MI7maJOx0jYI4ILUOlWQivtWb1sKRogH_sv98VunjJKsoLvxqSU3S-SfUIlHGzLRtiVpnptjMy5OjIcth-TeIZ1UHGiFP7_Tu3srvcpEYQP4Lnxb7q6apIhmTHVhZpxqCnaCGPE8xHQvpx6k410CU22-9UNkSknJmP8bam1nZJdu96TQvGHpODS6A\"")
        buildConfigField("String", "CAS_BASE_URL", "\"https://mls-api-next.kchat.com/chat-auth\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    //DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(project(":mlsWrapper"))

}