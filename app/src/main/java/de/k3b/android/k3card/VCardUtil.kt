package de.k3b.android.k3card

import ezvcard.parameter.TelephoneType
import ezvcard.parameter.VCardParameters
import ezvcard.property.*

/**
 * Kotlin helper to create a [StructuredName]
 * @param [family] family- or last-name i.e. Smith
 * @param [given] first name i.e. Peter
 * @param [prefix] title or academic grade to be written before the name i.e. "Dr."
 * @param [suffix] title or academic grade to be written after the name i.e. "phd"
 */
fun StructuredName(family : String? = null, given : String? = null, prefix : String? = null, suffix : String? = null) : StructuredName {
    val n = ezvcard.property.StructuredName()
    if (family != null) n.family = family
    if (given != null) n.given = given
    if (prefix != null) n.prefixes.add(prefix)
    if (suffix != null) n.suffixes.add(suffix)
    return n
}

/**
 * Kotlin helper to create an [Address]
 * @param [region] i.e. Texas in Usa or Bavaria in Germany
 * @param [locality] city name i.e. Dallas in Texas, Usa
 */
fun Address(
    postalCode : String? = null,
    country : String? = null,
    region : String? = null,
    locality : String? = null,

    streetAddress : String? = null,
    extendedAddress : String? = null,

    poBox : String? = null) : Address {
    val n = ezvcard.property.Address()
    if (postalCode != null) n.postalCode = postalCode
    if (country != null) n.country = country
    if (region != null) n.region = region
    if (locality != null) n.locality = locality

    if (streetAddress != null) n.streetAddress = streetAddress
    if (extendedAddress != null) n.extendedAddress = extendedAddress
    if (poBox != null) n.poBox = poBox
    return n
}

private fun getVEmoji(default : String?, properties : VCardParameters) : String {
    var pref = properties.pref
    var result = ""
    if (default != null) result = default

    val types = properties[VCardParameters.TYPE]
    if (types.contains(TelephoneType.CELL.toString())) result = "📱"
    if (types.contains(TelephoneType.WORK.toString())) result = "🏢" + result

    if (types.contains(TelephoneType.PREF.toString())) pref = 0

    if (pref != null) result = "" + pref + result;

    return result;
}

fun getVEmoji(property : VCardProperty) : String {
    val result = when (property) {
        is FormattedName -> "😆"
        is StructuredName  ->  "😎"
        is Address -> "🏠"
        is Telephone -> "📞"
        is Email -> "📧"
        is Birthday  ->  "🎂"
        is Note  ->  "🗒"
        is Categories  ->  "🔖"
        is Uid  ->  "#"
        else -> property::class.simpleName
    }
    return getVEmoji(result, property.parameters)
}

fun getVValue(property : VCardProperty) : String {
    val result = when (property) {
        is TextProperty -> getVValue(property.value) // FormattedName, Email, Note, Uid
        is StructuredName  ->  getVValue(property.prefixes, property.given, property.additionalNames, property.family, property.suffixes)
/*
        is Address -> value(property.
        is Telephone -> "📞"
        is Birthday  ->  "🎂"
        is Categories  ->  "🔖"

 */
        else -> property.toString()
    }
    return result
}

private fun getVValue(vararg items : Any?) : String {
    val result = StringBuilder();

    items.filter { it != null } .forEach {
        when (it) {
            is List<Any?> -> result.append(getVValue(it))
            else -> {
                val newValue = it.toString()
                if (newValue.isNotBlank()) result.append(newValue).append(" ") }
        }
    }

    return result.toString();
}
