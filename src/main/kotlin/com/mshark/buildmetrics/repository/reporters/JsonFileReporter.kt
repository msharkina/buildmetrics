package com.mshark.buildmetrics.repository.reporters

import com.mshark.buildmetrics.metrics.Metrics
import com.mshark.buildmetrics.repository.MetricReporter
import groovy.json.JsonBuilder
import java.io.IOException

/**
 * Transforms recorded metric to JSON format and writes them to file.
 *
 * @author msharkina
 * @since 9/22/20.
 */
class JsonFileReporter(private val filePath: String) :
    MetricReporter {
    override fun report(metrics: List<Metrics>) {
        FileReporter.use(
            filePath,
            object :
                UseInterface<FileReporter, IOException> {
                override fun accept(instance: FileReporter) {
                    instance.write(JsonBuilder(metrics).toPrettyString())
                }
            })
    }
}
