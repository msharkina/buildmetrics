package com.mshark.buildmetrics.repository

import com.mshark.buildmetrics.metrics.Metrics

/**
 * Defines a default reporters interface which all the supported reporters should provide.
 *
 * @author msharkina
 * @since 9/22/20.
 */
interface MetricReporter {
    /**
     * Report metrics.
     */
    fun report(timing: List<Metrics>)
}
