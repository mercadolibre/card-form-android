## Installation

### Android Studio

Add this line to your app's `build.gradle` inside the `dependencies` section:

    implementation 'com.mercadolibre.android:cardform:1.+'

### Local deployment

With this command you can generate a local version for testing:

    ./gradlew publishLocal

## 🐒 How to use?

```kotlin
CardForm.withAccessToken("user_access_token", "current_site_id", "integration_flow_id").build()
    .start("activity_from", "request_code")
```
