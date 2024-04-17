package org.jdc.kmp.template.resources.strings.locales

import org.jdc.kmp.template.resources.strings.StringResources

open class DefaultStringResources : StringResources {
    override val about = "About"
    override val stuff = "Stuff"
    override fun didItXTimes(times: Int) = "I did it $times times"
}