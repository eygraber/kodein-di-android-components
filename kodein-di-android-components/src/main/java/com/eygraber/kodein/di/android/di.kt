package com.eygraber.kodein.di.android

import android.app.Activity
import android.app.Service
import org.kodein.di.DI
import org.kodein.di.bindings.NoArgSimpleBindingDI
import org.kodein.di.bindings.WeakContextScope
import org.kodein.di.scoped
import org.kodein.di.singleton

inline fun <reified T : Any> DI.Builder.legacyActivitySingleton(
    noinline creator: NoArgSimpleBindingDI<Activity>.() -> T
) = scoped(WeakContextScope.of<Activity>()).singleton(creator = creator)

inline fun <reified T : Any> DI.Builder.serviceSingleton(
    noinline creator: NoArgSimpleBindingDI<Service>.() -> T
) = scoped(WeakContextScope.of<Service>()).singleton(creator = creator)
