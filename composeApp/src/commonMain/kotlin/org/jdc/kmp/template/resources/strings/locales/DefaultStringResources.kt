package org.jdc.kmp.template.resources.strings.locales

import org.jdc.kmp.template.resources.strings.StringResources

open class DefaultStringResources : StringResources {
    override val about = "About"
    override val add = "Add"
    override val alarmTime = "Alarm Time"
    override val available = "Available"
    override val birthDate = "BirthDate"
    override val edit = "Edit"
    override val editIndividual = "Edit Individual"
    override val email = "Email"
    override val firstName = "First Name"
    override val delete = "Delete"
    override val deleteIndividualConfirm = "Are you sure you want to delete this individual?"
    override val individual = "Individual"
    override val individualType = "Individual Type"
    override val invalidEmail = "Invalid Email"
    override val invalidBirthDate = "Invalid Birthdate"
    override val lastName = "Last Name"
    override val phone = "Phone"
    override val required = "Required"
    override val save = "Save"
    override val search = "Search"
    override val settings = "Settings"
    override val stuff = "Stuff"
    override fun didItXTimes(times: Int, name: String) = "did it $times times $name"
}