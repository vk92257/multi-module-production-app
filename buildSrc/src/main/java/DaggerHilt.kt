object DaggerHilt {
    const val  version = "2.42"
    const val HiltAndroid = "com.google.dagger:hilt-android:$version"


    const  val HiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$version"


    private const  val HiltLifecycleViewModelVersion = "1.0.0-alpha03"
    const  val HiltLifecycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$HiltLifecycleViewModelVersion"

    private const val HiltCompilerVersion = "1.0.0"
    const  val HiltCompilerLibrary = "androidx.hilt:hilt-compiler:$HiltCompilerVersion"
    const val HiltWorkLibrary = "androidx.hilt:hilt-work:$HiltCompilerVersion"

    const val HiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$HiltCompilerVersion"


}