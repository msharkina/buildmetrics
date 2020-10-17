package com.mshark.buildmetrics.repository.reporters

import java.io.IOException

/**
 * FunctionalInterface that accepts an instance of a generic type and which might throw an exception.
 *
 * @author msharkina
 * @since 9/22/20.
 */
@FunctionalInterface
interface UseInterface<T, X : Throwable> {
    /**
     * Accepts an instance of a generic type and which might throw an exception.
     */
    @Throws(IOException::class)
    fun accept(instance: T)
}
