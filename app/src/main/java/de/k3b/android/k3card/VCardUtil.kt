package de.k3b.android.k3card

import ezvcard.property.*

fun StructuredName(family : String? = null, given : String? = null, prefix : String? = null, suffix : String? = null) : StructuredName {
    val n = ezvcard.property.StructuredName()
    if (family != null) n.family = family
    if (given != null) n.given = given
    if (prefix != null) n.prefixes.add(prefix)
    if (suffix != null) n.suffixes.add(suffix)
    return n
}
