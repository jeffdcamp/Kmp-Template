package org.jdc.kmp.template.resources.strings

import org.jdc.kmp.template.resources.strings.locales.DefaultStringResources
import org.jdc.kmp.template.resources.strings.locales.EsStringResources

interface StringResources {
    val about: String
    val add: String
    val alarmTime: String
    val available: String
    val birthDate: String
    val edit: String
    val editIndividual: String
    val email: String
    val firstName: String
    val delete: String
    val deleteIndividualConfirm: String
    val individual: String
    val individualType: String
    val invalidEmail: String
    val invalidBirthDate: String
    val lastName: String
    val phone: String
    val required: String
    val save: String
    val search: String
    val settings: String
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
