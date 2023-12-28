package de.k3b.android.k3card

import org.junit.Test

import org.junit.Assert.*
import java.io.StringWriter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val writer = StringWriter()
        SampleVCardData.cardWithDetails.write(writer)
        val s = writer.toString()
        if (s.length > 0) {

        }
    }
}