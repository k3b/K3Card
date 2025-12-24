package de.k3b.android.k3card

import ezvcard.VCard
import ezvcard.VCardVersion
import ezvcard.parameter.AddressType
import ezvcard.parameter.EmailType
import ezvcard.parameter.TelephoneType
import ezvcard.property.*

import java.time.LocalDate

/**
 * SampleData for Jetpack Compose Tutorial
 */
object SampleVCardData {
    val V_CARD_VERSION_DEFAULT = VCardVersion.V4_0

    /*
BEGIN:VCARD
VERSION:4.0
PRODID:ez-vcard 0.12.2-SNAPSHOT
FN:Theo Test
UID:urn:uuid:d3d189a7-0a9e-4200-ba1f-529b0870c179
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
    * */
    val cardWithDetails = VCard(V_CARD_VERSION_DEFAULT,
        FormattedName("Theo Test"),
        Uid.random(),
        StructuredName("Test", "Theo", "Dr", "MD"),
        Address("D81234", "Germany", "Bavaria", "Munic",
            "Leopoldstraße 22", "third floor",
            "000012345"),
        Birthday(LocalDate.of(2001,12,24)),
        Telephone("+49 170 98765")
            .addType(TelephoneType.WORK, TelephoneType.CELL)
            .setPref(1),
        Telephone("+49 30 12345")
            .addType(TelephoneType.HOME),
        Email("TheoTest@mycompany.com")
            .addType(EmailType.WORK)
            .setPref(1),
        Email("TheoTest@hotmail.com")
            .addType(EmailType.HOME),
        Note("Example contact with details"),
        Categories("Example","Full Example"),
    )

    // Sample conversation data
    val cardListSample = listOf(
        VCard(V_CARD_VERSION_DEFAULT,
            FormattedName("John Doe"),
            Uid.random(),
            Note("Example contact without details"),
            Categories("Example"),
        ),
        cardWithDetails,
        VCard(V_CARD_VERSION_DEFAULT,
            FormattedName("Mike Smith"),
            Uid.random(),
            TestAddress("123"),
        ),
    )

    private fun TestAddress(prefix: String): Address {
        val adr = Address()
        adr.streetAddress = prefix + " Main St."
        adr.locality = "Austin"
        adr.region = "TX"
        adr.postalCode = prefix + "45"
        adr.country = "USA"
        adr.types.add(AddressType.WORK)

        //optionally, set the text to print on the mailing label
        adr.label = prefix + " Main St.\nAustin, TX " + prefix + "45\nUSA"
        return adr
    }
}

