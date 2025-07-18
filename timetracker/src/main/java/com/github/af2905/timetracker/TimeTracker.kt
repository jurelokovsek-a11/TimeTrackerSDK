package com.github.af2905.timetracker

import android.util.Log

/**
 * TimeTracker is a lightweight utility for measuring execution time of code blocks or operations.
 * It can be used in both development and production environments.
 */
object TimeTracker {

    private val startTimes = mutableMapOf<String, Long>()
    private val durations = mutableMapOf<String, Long>()

    /** Enable or disable tracking globally. */
    private var isEnabled: Boolean = true

    /**
     * Optional custom logger.
     * Default: logs to Logcat with tag "TimeTracker"
     */
    private var logger: ((String, String) -> Unit)? = { tag, message ->
        Log.d(tag, message)
    }

    /** Starts tracking a tag */
    fun start(tag: String) {
        if (!isEnabled) return
        startTimes[tag] = System.currentTimeMillis()
        logger?.invoke("TimeTracker", "Started timer for [$tag]")
    }

    /** Stops tracking a tag and calculates duration */
    fun stop(tag: String) {
        if (!isEnabled) return
        val startTime = startTimes[tag]
        if (startTime != null) {
            val duration = System.currentTimeMillis() - startTime
            durations[tag] = duration
            logger?.invoke("TimeTracker", "Stopped timer for [$tag], duration: ${duration}ms")
        } else {
            logger?.invoke("TimeTracker", "No start time found for tag: $tag")
        }
    }

    /** Returns duration in milliseconds for a tag, or null if not available */
    fun getDuration(tag: String): Long? {
        return if (isEnabled) durations[tag] else null
    }

    /** Logs all recorded durations */
    fun printAllDurations() {
        if (!isEnabled) return
        durations.forEach { (tag, duration) ->
            logger?.invoke("TimeTracker", "[$tag] took ${duration}ms")
        }
    }

    /** Clears all start times and durations */
    fun reset() {
        startTimes.clear()
        durations.clear()
        logger?.invoke("TimeTracker", "All timers cleared")
    }

    /**
     * Measures execution time of a code block automatically.
     * Usage:
     * TimeTracker.measure("load_data") {
     *     // Your logic
     * }
     */
    inline fun <T> measure(tag: String, block: () -> T): T {
        start(tag)
        val result = block()
        stop(tag)
        return result
    }
}

/**
 * ===============================
 * Example Usage (Production-safe)
 * ===============================
 *
 * // Enable TimeTracker only in debug mode
 * TimeTracker.isEnabled = BuildConfig.DEBUG
 *
 * // Optional: set a custom logger (e.g., Crashlytics)
 * TimeTracker.logger = { tag, msg -> MyCrashlytics.log("$tag: $msg") }
 *
 * // Manual timing:
 * TimeTracker.start("load_user")
 * // ... do something ...
 * TimeTracker.stop("load_user")
 * val duration = TimeTracker.getDuration("load_user")
 *
 * // Automatic timing:
 * TimeTracker.measure("fetch_config") {
 *     fetchRemoteConfig()
 * }
 */
