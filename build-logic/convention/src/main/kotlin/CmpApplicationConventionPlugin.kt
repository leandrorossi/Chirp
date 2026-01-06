import com.leandrour.chirp.convention.configureAndroidTarget
import com.leandrour.chirp.convention.configureIosTargets
import com.leandrour.chirp.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CmpApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.leandrour.convention.android.application.compose")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            configureAndroidTarget()
            configureIosTargets()

            dependencies {
                "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
            }
        }
    }
}