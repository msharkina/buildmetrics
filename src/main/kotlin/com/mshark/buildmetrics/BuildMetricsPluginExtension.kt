package com.mshark.buildmetrics

/**
 * This class defines configurable BuildMetricsPlugin properties.
 *
 * @author msharkina
 * @since 9/22/20.
 */
open class BuildMetricsPluginExtension {
    var supportedReporters: Array<ReporterType> = arrayOf(
        ReporterType.JSON
    )
    var loggingLevel: LoggingLevel =
        LoggingLevel.BUILD
}

/**
 * Defines supported reporter types.
 */
enum class ReporterType {
    CSV,
    JSON
}

/**
 * Defines supported logging level.
 * TASKS - logs tasks and build timing
 * BUILD - logs build timing
 */
enum class LoggingLevel {
    TASKS,
    BUILD
}
