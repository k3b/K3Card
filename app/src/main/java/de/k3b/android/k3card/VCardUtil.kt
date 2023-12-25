package de.k3b.android.k3card

import ezvcard.VCard
import ezvcard.parameter.*
import ezvcard.property.*

fun VCard(name: String, vararg properties: VCardProperty) : VCard {
    val v = VCard()
    v.addFormattedName(FormattedName(name))
    v.properties += properties
    return v
}

fun StructuredName(family : String? = null, given : String? = null, prefix : String? = null, suffix : String? = null) : StructuredName {
    val n = StructuredName()
    if (family != null) n.family = family
    if (given != null) n.given = given
    if (prefix != null) n.prefixes.add(prefix)
    if (suffix != null) n.suffixes.add(suffix)
    return n
}

fun Telephone(number: String, typ: TelephoneType? = null, typ2: TelephoneType? = null): Telephone {
    val p = Telephone(number)
    if (typ != null) p.types += typ
    if (typ2 != null) p.types += typ2
    return p
}

fun Email(number: String, typ: EmailType? = null, typ2: EmailType? = null): Email {
    val p = Email(number)
    if (typ != null) p.types += typ
    if (typ2 != null) p.types += typ2
    return p
}

fun Categories(vararg categoryNames: String): Categories {
    val p = ezvcard.property.Categories()
    p.values += categoryNames
    return p
}