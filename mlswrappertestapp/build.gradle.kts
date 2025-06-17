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

        buildConfigField("String", "MOCK_ACCESS_TOKEN", "\"eyJ0eXAiOiJqd3QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik44Q0NHMWVCOU9tLWNKNXZERDUyS24tUFJrdFJhSDhpcFM4TnRPN01IQnMifQ.eyJleHAiOjE3NzE3NDM5MzYsImlhdCI6MTc1MDE0MzkzNiwic3ViIjoiNmYxM2MwY2UtMTQ5My00YTAwLWI2YjMtY2ZhNTM0NjcxMjY1IiwidW5pcXVlRGV2aWNlSWQiOiJjYzM1MmQ3NS02ZTQ0LTRlM2YtOGNmOS0yNDUzZDI0YWIzYWQiLCJqdGkiOiIzOWRmMmU5NS1iNTc1LTQ4ZjEtYTM1NC05YzYwZTRiMTcyNGIiLCJhdXRoQ2xpZW50IjoiY29uc3VtZXIiLCJ1c2VybmFtZSI6IkNoYW1vZGE3NzciLCJlbWFpbCI6ImNoYW1vZGE3NzdAdW5leS5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiY2hhdFRva2VuIjoiVGVVS3ZtVWpwVjMxcnFmb250ZFdEZ1pUUGN6d2lldEsiLCJjaGF0SmlkIjoiNmYxM2MwY2UtMTQ5My00YTAwLWI2YjMtY2ZhNTM0NjcxMjY1QG1scy1lamItbmV4dC5rY2hhdC5jb20ifQ.HAx2wezmlriLAhhkgTbGf186fb7zYuYYAzycWTPWbj6waeRBrIqtHZUh0R2T4bSa4RxTnqPgLDfF7tcuSZTHzh8z8qf9hOEWJxXG8Tp1u2F2TroWGiiEM0Vfq6HRP9njGlR-rx-JVPx0twXH1kQnNtcCJjTWQjlH0sfXSGBgw7yMQ8yafzcENANLrPAXAnLCyCDXn4hmNHeKLa-PdHIMfNPDqwfG42nMSUlP_rQWLJgDp6a2aXavNsPolO2PSXFcSmeLmXXLowyRyuay5gEe6tIWnXSbKFmD760T11XKbvH_VV1H2p9lcx63j18E-uModf45BUpISAtOQuYSlMpdhGPNgeJcKGDBdgWtLXIVK-ledhTggIy5tcGDft06dfROzhz30J17eCaKyD0CQmLFJF6s2WczNo34sWVQxVyEKqXVnmRsF26Ud-F2Q7dSutcNCSnz-YLuMkVPu1GaoIuXZGA6_bmYjMtAF8jsk4crL7H09WfVURQH4_Elqah3aCoL6-M0kIJmp9pRrMxMP0KpfLn1rkvQ9RTXjdv1z9p7FpT0LaYrAPGZgaVxD_E1dLuUxJB_ptUhQAXw_gvc3ZzJ12KJq45iiaA1pwzilvYeC5RoUZDgEIPpppQ_UQSxxjVAZWfoqNSxdLKPHHY5NEEZk-1v2TJ_dMOrFMM9yA3sX_4\"")
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

    implementation (libs.gson)

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