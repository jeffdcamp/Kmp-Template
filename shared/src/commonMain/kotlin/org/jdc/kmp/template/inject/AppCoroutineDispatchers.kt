package org.jdc.kmp.template.inject

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutineDispatchers(
    val default: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher,
)