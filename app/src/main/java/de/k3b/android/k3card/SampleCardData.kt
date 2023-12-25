package de.k3b.android.k3card

import ezvcard.parameter.AddressType
import ezvcard.parameter.EmailType
import ezvcard.parameter.TelephoneType
import ezvcard.property.Address
import ezvcard.property.Birthday
import ezvcard.property.Note
import ezvcard.property.Telephone

/**
 * SampleData for Jetpack Compose Tutorial
 */
object SampleVCardData {
    // Sample conversation data
    val conversationSample = listOf(
        VCard(
            "John Doe",
            Note("Example contact without details"),
            Categories("Example"),
        ),
        VCard(
            "Theo Test",
            StructuredName("Test", "Theo", "Dr", "MD"),
            Birthday("2001-12-24"),
            Telephone("+49 170 98765")
                .addType(TelephoneType.WORK, TelephoneType.CELL)
                .setPref(1),
            Telephone("+49 30 12345")
                .addType(TelephoneType.HOME),
            Email("TheoTest@mycompany.com", EmailType.WORK).setPref(1),
            Email("TheoTest@hotmail.com", EmailType.HOME),
            TestAddress("123"),
            Note("Example contact with details"),
            Categories("Example","Full Example"),
        ),
        VCard("Mike Smith"),
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

