package de.k3b.android.k3card

import org.junit.Test

import org.junit.Assert.*
import java.io.StringWriter

/**
 * unittest for the VCard - Data operations
 */
class VCardDataUnitTest {
    @Test
    fun regressiontest_generateSampleVCardData_sameResult() {
        val vcardSampleData = SampleVCardData.cardWithDetails

        val writer = StringWriter()
        vcardSampleData.write(writer)
        val generatedSampleData = writer.toString()

        // Note: not working in ez-vcard 0.12.1 :
        vcardSampleData.setProductId("MyUnitTest ProductID")

        val expectedSampleData = """
BEGIN:VCARD
VERSION:4.0
PRODID:ez-vcard 0.12.1
FN:Theo Test
UID:urn:unittest:MyTestId4711
N:Test;Theo;;Dr;MD
ADR:000012345;third floor;Leopoldstraße 22;Munic;Bavaria;D81234;Germany
BDAY:20011224
TEL;TYPE=work,cell;PREF=1:+49 170 98765
TEL;TYPE=home:+49 30 12345
EMAIL;TYPE=work;PREF=1:TheoTest@mycompany.com
EMAIL;TYPE=home:TheoTest@hotmail.com
NOTE:Example contact with details
CATEGORIES:Example,Full Example
END:VCARD
"""

        assertEquals(expectedSampleData.normalize(), generatedSampleData.normalize())
    }

    private fun String.normalize() : String {
        return this.trim().replace("\r","")
    }
}
