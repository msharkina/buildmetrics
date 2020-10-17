package com.mshark.buildmetrics.managers

import com.mshark.buildmetrics.repository.BuildMetricsRepository
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.StartParameter
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

/**
 * This class is responsible for measuring task and build execution.
 *
 * @author msharkina
 * @since 9/22/20.
 */
class BuildMetricsManager(
    startParameter: StartParameter,
    private val repository: BuildMetricsRepository
) {
    private val buildStartTime: Long = System.currentTimeMillis()
    private var taskStartTime: Long = 0

    /**
     *  Build listener.
     */
    var pluginBuildListener: BuildListener
        private set
    /**
     * Task execution listener.
     */
    var pluginTaskExecutionListener: TaskExecutionListener
        private set

    init {
        val sb = StringBuilder()
        startParameter.taskNames.forEach {
            sb.append(it)
            sb.append(" ")
        }
        val durationMs = System.currentTimeMillis() - buildStartTime
        repository.addBuildMetric(
            durationMs,
            sb.toString()
        )
        pluginBuildListener = BuildListenerImpl()
        pluginTaskExecutionListener = TaskExecutionListenerImpl()
    }

    /**
     * [BuildListener] implementation class which is notified of the major lifecycle events as
     * a build is executed.
     * @see BuildListener
     */
    inner class BuildListenerImpl : BuildListener {
        override fun settingsEvaluated(settings: Settings) {
            val durationMs = System.currentTimeMillis() - buildStartTime
            repository.addBuildMetric(
                durationMs,
                "SettingsEvaluated"
            )
        }

        override fun buildFinished(result: BuildResult) {
            val durationMs = System.currentTimeMillis() - buildStartTime
            repository.addBuildMetric(
                durationMs,
                "BuildFinished"
            )
        }

        override fun projectsLoaded(gradle: Gradle) {
            val durationMs = System.currentTimeMillis() - buildStartTime
            repository.addBuildMetric(
                durationMs,
                "ProjectsLoaded"
            )
        }

        override fun buildStarted(gradle: Gradle) {
            val durationMs = System.currentTimeMillis() - buildStartTime
            repository.addBuildMetric(
                durationMs,
                "BuildStarted"
            )
        }

        override fun projectsEvaluated(gradle: Gradle) {
            val durationMs = System.currentTimeMillis() - buildStartTime
            repository.addBuildMetric(
                durationMs,
                "ProjectsEvaluated"
            )
        }
    }

    /**
     * [TaskExecutionListener] implementation class which is notified of the execution
     * of the tasks in a build.
     * @see TaskExecutionListener
     */
    inner class TaskExecutionListenerImpl : TaskExecutionListener {
        override fun beforeExecute(task: Task) {
            taskStartTime = System.currentTimeMillis()
        }

        override fun afterExecute(task: Task, state: TaskState) {
            val durationMs = System.currentTimeMillis() - taskStartTime
            repository.addTaskMetric(
                durationMs,
                task.path + task.name,
                task.didWork
            )
        }
    }

    /**
     * Calls repository to dispatch recorded metrics.
     */
    fun onBuildFinished(result: BuildResult) {
        result.gradle?.rootProject?.let { repository.reportMetrics() }
    }
}
