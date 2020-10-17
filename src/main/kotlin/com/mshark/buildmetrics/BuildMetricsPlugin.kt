package com.mshark.buildmetrics

import com.mshark.buildmetrics.managers.BuildMetricsManager
import com.mshark.buildmetrics.repository.BuildMetricsRepository
import com.mshark.buildmetrics.repository.MetricReporter
import com.mshark.buildmetrics.repository.reporters.CSVFileReporter
import com.mshark.buildmetrics.repository.reporters.JsonFileReporter
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * BuildMetricsPlugin class.
 *
 * @author msharkina
 * @since 9/22/20.
 */
class BuildMetricsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "buildmetrics",
            BuildMetricsPluginExtension::class.java
        )
        project.afterEvaluate {
            val buildMetricsRepository =
                BuildMetricsRepository(
                    configureReporters(extension.supportedReporters)
                )
            val buildMetricsManager =
                BuildMetricsManager(
                    project.gradle.startParameter,
                    buildMetricsRepository
                )

            when (extension.loggingLevel) {
                LoggingLevel.BUILD -> project.gradle.addListener(buildMetricsManager.pluginBuildListener)
                LoggingLevel.TASKS -> {
                    project.gradle.addListener(buildMetricsManager.pluginBuildListener)
                    project.gradle.addListener(buildMetricsManager.pluginTaskExecutionListener)
                }
            }

            project.gradle.buildFinished {
                buildMetricsManager.onBuildFinished(it)
            }
        }
    }

    /**
     * Configures supported reporters.
     */
    private fun configureReporters(supportedReportersType: Array<ReporterType>): List<MetricReporter> {
        return supportedReportersType.map { fileFormat ->
            when (fileFormat) {
                ReporterType.JSON -> JsonFileReporter("BuildMetrics.json")
                ReporterType.CSV -> CSVFileReporter("BuildMetrics.csv")
            }
        }
    }
}
