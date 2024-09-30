package org.jdc.kmp.template.resources.strings

import org.jdc.kmp.template.resources.strings.locales.DefaultStringResources
import org.jdc.kmp.template.resources.strings.locales.EsStringResources

interface StringResources {
    val about: String
    val add: String
    val stuff: String
    fun didItXTimes(times: Int, name: String): String

    companion object {
        private val defaultStrings: StringResources by lazy { DefaultStringResources() }
        private val esStrings: StringResources by lazy { EsStringResources() }

        fun getStrings(locale: String): StringResources {
            return when (locale) {
                StringLocale.EN.value -> defaultStrings
                StringLocale.ES.value -> esStrings
                else -> defaultStrings
            }
        }

        fun getStrings(locale: StringLocale = StringLocale.EN): StringResources {
            return when (locale) {
                StringLocale.EN -> defaultStrings
                StringLocale.ES -> esStrings
            }
        }
    }
}
