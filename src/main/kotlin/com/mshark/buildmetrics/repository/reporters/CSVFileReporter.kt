package com.mshark.buildmetrics.repository.reporters

import com.mshark.buildmetrics.FormattingUtils
import com.mshark.buildmetrics.metrics.Metrics
import com.mshark.buildmetrics.repository.MetricReporter
import java.io.IOException

/**
 * Transforms recorded metric to CSV format and writes them to file.
 *
 * @author msharkina
 * @since 9/22/20.
 */
class CSVFileReporter(private val filePath: String) :
    MetricReporter {
    override fun report(timing: List<Metrics>) {
        FileReporter.use(
            filePath,
            object :
                UseInterface<FileReporter, IOException> {
                override fun accept(instance: FileReporter) {
                    instance.write(getCSVFormattedList(timing))
                }
            })
    }

    /**
     * Exports a list of recorded metrics in csv format.
     */
    private fun getCSVFormattedList(timing: List<Metrics>): String {
        return getTasksTimingString(timing) + getBuildTimingString(timing)
    }

    /**
     * Formats tasks metrics.
     */
    private fun getTasksTimingString(timing: List<Metrics>): String {
        val filteredTaskList = timing.mapNotNull { it as? Metrics.TaskMetrics }
        val firstLine = "\nTask Name, Duration, Worked\n"
        return firstLine + filteredTaskList.joinToString(separator = "\n") {
            "\"${it.name}\", \"${FormattingUtils.formatDuration(it.duration)}\", \"${it.didWork}\""
        }
    }

    /**
     * Formats build metrics.
     */
    private fun getBuildTimingString(timing: List<Metrics>): String {
        val filteredBuildTimingList = timing.mapNotNull { it as? Metrics.BuildMetrics }
        val firstLine = "\nBuild Action Name, Duration\n"
        return firstLine + filteredBuildTimingList.joinToString(separator = "\n") {
            "\"${it.name}\", \"${FormattingUtils.formatDuration(it.duration)}\""
        }
    }
}