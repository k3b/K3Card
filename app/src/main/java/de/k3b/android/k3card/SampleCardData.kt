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
UID:urn:uuid:9afaa51e-d578-4e2c-b6f3-4fd037e838d4
N:Test;Theo;;Dr;MD
BDAY:20011224
TEL;TYPE=work,cell;PREF=1:+49 170 98765
TEL;TYPE=home:+49 30 12345
EMAIL;TYPE=work;PREF=1:TheoTest@mycompany.com
EMAIL;TYPE=home:TheoTest@hotmail.com
ADR;TYPE=work;LABEL="123 Main St.\nAustin, TX 12345\nUSA":;;123 Main St.;Au
 stin;TX;12345;USA
NOTE:Example contact with details
CATEGORIES:Example,Full Example
END:VCARD
    * */
    val cardWithDetails = VCard(V_CARD_VERSION_DEFAULT,
        FormattedName("Theo Test"),
        Uid.random(),
        StructuredName("Test", "Theo", "Dr", "MD"),
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
        TestAddress("123"),
        Note("Example contact with details"),
        Categories("Example","Full Example"),
    )

    // Sample conversation data
    val conversationSample = listOf(
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

