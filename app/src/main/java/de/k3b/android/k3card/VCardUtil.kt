package de.k3b.android.k3card

import ezvcard.VCard
import ezvcard.VCardVersion
import ezvcard.parameter.EmailType
import ezvcard.parameter.TelephoneType
import ezvcard.parameter.VCardParameters
import ezvcard.property.*


/**
 * Kotlin helper to create a [VCard]
 * @param version the version to assign to the vCard
 * @param properties the initial properties of the vCard.
 */
fun VCard(version: VCardVersion?, vararg properties: VCardProperty) : VCard {
    val n = ezvcard.VCard(version)
    for (p in properties) {
        n.addProperty(p);
    }
    return n;
}

fun VCard.asDisplayString() : String {
    val result = StringBuilder()
    result.append("VCard-Version:").append(this.version).append("\n")
    for (p in properties) {
        result.append(p.getVEmoji()).append(" ").append(p.getVValue()).append("\n")
    }
    return result.toString()
}

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

/**
 * Kotlin helper to create an [Categories]
 */
fun Categories(vararg
    names : String) : Categories {
    val n = ezvcard.property.Categories()
    n.values.addAll(names)
    return n
}

/**
 * ... to allow fluent interface
 */
fun Telephone.addType(vararg types: TelephoneType): Telephone {
    if (types.size > 0) getTypes().addAll(types);
    return this;
}

/**
 * ... to allow fluent interface
 */
fun Email.addType(vararg types: EmailType): Email {
    if (types.size > 0) getTypes().addAll(types);
    return this;
}

/**
 * ... to allow fluent interface
 */
fun VCardProperty.setPreference(pref: Int?): VCardProperty {
    this.getParameters().setPref(pref)
    return this;
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

fun VCardProperty.getVEmoji() : String {
    val property = this
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

fun VCardProperty.getVValue() : String {
    val p = this
    val result = when (p) {
        is TextProperty -> getVValue(p.value) // FormattedName, Email, Note, Uid
        is StructuredName  ->  getVValue(p.prefixes, p.given, p.additionalNames, p.family, p.suffixes)
        is Telephone -> getVValue(p.text,p.uri)
        is DateOrTimeProperty  ->  getVValue(p.text,p.partialDate, p.date) // DateOrTimeProperty
        is TextListProperty  ->  getVValue(p.values) // Categories
        is Address  -> {
            val label = p.label
            if (label != null && label.isNotBlank()) {
                return getVValue(p.label.replace("\r","").replace("\n"," "))
            } else {
                return getVValue(
                    p.postalCodes,
                    p.localities,
                    p.regions,
                    p.countries,
                    p.streetAddresses,
                    p.extendedAddresses,
                    p.poBoxes
                )
            }
        }
        else -> p.toString()
    }
    return result
}

private fun getVValue(vararg items : Any?) : String {
    return getVListValue(items.asList())
}

private fun getVListValue(items : List<Any?>) : String {
    val result = StringBuilder();

    items.filter { it != null } .forEach {
        when (it) {
            is List<Any?> -> result.append(getVListValue(it))
            else -> {
                val newValue = it.toString()
                if (newValue.isNotBlank()) result.append(newValue).append(" ") }
        }
    }

    return result.toString().trim();
}
