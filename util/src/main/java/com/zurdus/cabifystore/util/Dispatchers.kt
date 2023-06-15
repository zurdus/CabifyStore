package com.zurdus.cabifystore.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

interface Dispatchers {
    val main: CoroutineContext
    val mainImmediate: CoroutineContext
    val default: CoroutineContext
    val IO: CoroutineContext
}

class DefaultDispatchers : Dispatchers {
    override val main: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Main
    override val mainImmediate: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Main.immediate
    override val default: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Default
    override val IO: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO
}
