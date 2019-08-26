package com.eygraber.kodein.android

import android.app.Activity
import android.app.Service
import org.kodein.di.Kodein
import org.kodein.di.bindings.NoArgSimpleBindingKodein
import org.kodein.di.bindings.WeakContextScope
import org.kodein.di.erased.scoped
import org.kodein.di.erased.singleton

inline fun <reified T : Any> Kodein.Builder.legacyActivitySingleton(
    noinline creator: NoArgSimpleBindingKodein<Activity>.() -> T
) = scoped(WeakContextScope.of<Activity>()).singleton(creator = creator)

inline fun <reified T : Any> Kodein.Builder.serviceSingleton(
    noinline creator: NoArgSimpleBindingKodein<Service>.() -> T
) = scoped(WeakContextScope.of<Service>()).singleton(creator = creator)
