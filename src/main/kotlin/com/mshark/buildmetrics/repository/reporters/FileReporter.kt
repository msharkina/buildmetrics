package com.mshark.buildmetrics.repository.reporters

import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * Base file reporter class. Writes data to a file and closes the file when writing is done.
 *
 * @author msharkina
 * @since 9/22/20.
 */
class FileReporter private constructor(private val fileName: String) {
    private val writer: FileWriter

    init {
        val file = File(fileName)
        val filePath = file.getParentFile()
        filePath?.mkdirs()
        writer = FileWriter(file, true)
    }

    companion object {
        /**
         * A factory method to use a file reporter functionality.
         */
        @Throws(IOException::class)
        fun use(fileName: String, block: UseInterface<FileReporter, IOException>) {
            val fileReporter =
                FileReporter(
                    fileName
                )
            try {
                block.accept(fileReporter)
            } finally {
                fileReporter.close()
            }
        }
    }

    /**
     * Closes the file.
     */
    private fun close() {
        writer.flush()
        writer.close()
    }

    /**
     * Writes message to a file.
     */
    fun write(message: String) {
        writer.write(message)
    }
}
