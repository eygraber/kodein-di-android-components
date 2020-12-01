package com.eygraber.kodein.di.android

import android.app.Activity
import android.app.Service
import org.kodein.di.DI
import org.kodein.di.bindings.NoArgBindingDI
import org.kodein.di.bindings.WeakContextScope
import org.kodein.di.scoped
import org.kodein.di.singleton

inline fun <reified T : Any> DI.Builder.legacyActivitySingleton(
    noinline creator: NoArgBindingDI<Activity>.() -> T
) = scoped(WeakContextScope.of<Activity>()).singleton(creator = creator)

inline fun <reified T : Any> DI.Builder.serviceSingleton(
    noinline creator: NoArgBindingDI<Service>.() -> T
) = scoped(WeakContextScope.of<Service>()).singleton(creator = creator)
