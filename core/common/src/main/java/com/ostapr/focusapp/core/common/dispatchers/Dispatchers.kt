package com.ostapr.focusapp.core.common.dispatchers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val focusAppDispatcher: FocusAppDispatchers)

enum class FocusAppDispatchers {
    Default,
    IO,
}
