package org.jdc.kmp.template.resources.strings.locales

import org.jdc.kmp.template.resources.strings.StringResources

open class DefaultStringResources : StringResources {
    override val about = "About"
    override val add = "Add"
    override val stuff = "Stuff"
    override fun didItXTimes(times: Int, name: String) = "did it $times times $name"
}