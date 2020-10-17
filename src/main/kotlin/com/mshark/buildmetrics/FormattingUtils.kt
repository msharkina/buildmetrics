package com.mshark.buildmetrics

import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Helper formatting class.
 *
 * @author msharkina
 * @since 9/22/20.
 */
object FormattingUtils {
    const val M_SS_MS = "%d:%02d:%02d"
    const val M_SS_MSS = "%d:%02d.%03d"
    /**
     * Formats duration to a readable string.
     */
    fun formatDuration(ms: Long): String {
        var reminder = ms
        val hours = TimeUnit.MILLISECONDS.toHours(ms)
        reminder -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(reminder)
        reminder -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(reminder)
        reminder -= TimeUnit.SECONDS.toMillis(seconds)
        val millis = reminder

        return if (hours > 0) {
            String.format(
                M_SS_MS,
                hours,
                minutes,
                seconds
            )
        } else {
            String.format(
                M_SS_MSS,
                minutes,
                seconds,
                millis
            )
        }
    }

    /**
     * Formats start/end time to a readable string.
     */
    fun formatTime(ms: Long): String {
        return DateFormat.getDateTimeInstance().format(Date(ms))
    }
}