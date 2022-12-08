apply {
    from("$rootDir/compose-module.gradle")
}
dependencies {
    "implementation"(WorkManger.workManager)
    "implementation"(GsonConvertor.GsonConvertorLibrary)
    "implementation"(project(Modules.coreUiModule))
    "implementation"(project(Modules.coreModule))


}
