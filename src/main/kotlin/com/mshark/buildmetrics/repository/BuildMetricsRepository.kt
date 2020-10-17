package com.mshark.buildmetrics.repository

import com.mshark.buildmetrics.metrics.Metrics

/**
 * This class is responsible for keeping build metrics and reporting them to the supported destinations.
 *
 * @author msharkina
 * @since 9/22/20.
 */
class BuildMetricsRepository(private val reporters: List<MetricReporter>) {
    private val metrics = mutableListOf<Metrics>()

    /**
     * Calls supported reporters to dispatch collected metrics to appropriate destinations.
     */
    fun reportMetrics() {
        reporters.forEach { it.report(metrics) }
    }

    /**
     * Creates and stores in memory task metric.
     */
    fun addTaskMetric(durationMs: Long, taskName: String, didWork: Boolean) {
        metrics +=
            Metrics.TaskMetrics(
                durationMs,
                taskName,
                didWork
            )
    }

    /**
     * Creates and stores in memory build metric.
     */
    fun addBuildMetric(durationMs: Long, taskName: String) {
        metrics +=
            Metrics.BuildMetrics(
                durationMs,
                taskName
            )
    }
}
