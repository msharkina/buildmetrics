package com.mshark.buildmetrics.metrics

/**
 * This class holds timing information for each build task.
 */
sealed class Metrics(val name: String, val duration: Long) {
    /**
     * Build metric for a task.
     */
    class TaskMetrics(
        duration: Long,
        name: String,
        val didWork: Boolean
    ) : Metrics(name, duration)

    /**
     * Build metric for a build.
     */
    class BuildMetrics(duration: Long, name: String) : Metrics(name, duration)
}
