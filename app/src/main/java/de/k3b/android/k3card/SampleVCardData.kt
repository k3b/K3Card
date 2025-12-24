package de.k3b.android.k3card

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

    val cardWithDetails = VCard(V_CARD_VERSION_DEFAULT,
        FormattedName("Theo Test"),
        Uid("urn:unittest:MyTestId4711"),
        ProductId("MyUnitTest-ProductId"),
        StructuredName("Test", "Theo", "Dr", "MD"),
        Address("D81234", "Germany", "Bavaria", "Munic",
            "Leopoldstraße 22", "third floor",
            "000012345"),
        Birthday(LocalDate.of(2001,12,24)),
        Telephone("+49 170 98765")
            .addType(TelephoneType.WORK, TelephoneType.CELL)
            .setPreference(1) ,
        Telephone("+49 30 12345")
            .addType(TelephoneType.HOME),
        Email("TheoTest@mycompany.com")
            .addType(EmailType.WORK)
            .setPreference(1),
        Email("TheoTest@hotmail.com")
            .addType(EmailType.HOME),
        Note("Example contact note with details"),
        Categories("ExampleCategory","OtherExampleCategory"),
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

