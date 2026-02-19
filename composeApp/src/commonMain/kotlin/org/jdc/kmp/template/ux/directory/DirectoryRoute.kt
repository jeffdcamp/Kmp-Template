package org.jdc.kmp.template.ux.directory

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.dbtools.kmp.commons.compose.navigation3.deeplink.SimpleRouteMatcher
import org.dbtools.kmp.commons.network.ktor.toUri

@Serializable
object DirectoryRoute: NavKey

object DirectoryRouteMatcher : SimpleRouteMatcher<DirectoryRoute>(DirectoryRoute, "/directory".toUri())
