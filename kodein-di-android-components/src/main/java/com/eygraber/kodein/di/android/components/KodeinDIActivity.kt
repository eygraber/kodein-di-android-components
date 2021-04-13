package com.eygraber.kodein.di.android.components

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import com.eygraber.kodein.di.KodeinDIComponentInitializer
import com.eygraber.kodein.di.KodeinDIComponentInitializer.ACTIVITY_LAYOUT_INFLATER_TAG
import org.kodein.di.DI
import org.kodein.di.android.closestDI
import org.kodein.di.bind
import org.kodein.di.bindings.subTypes
import org.kodein.di.provider
import org.kodein.di.with
import org.kodein.type.jvmType

interface KodeinDIActivityInitializer :
    KodeinDIComponentInitializer<Activity> {
    override fun initializeKodeinDI() = DI.lazy {
        extend(parentDI)

        bind<Context>(overrides = true).subTypes().with { type ->
            when (type.jvmType) {
                Application::class.java -> provider { kodeinDIComponent.application as Context }
                else -> provider { kodeinDIComponent }
            }
        }

        bind<LayoutInflater>(tag = ACTIVITY_LAYOUT_INFLATER_TAG) with provider { kodeinDIComponent.layoutInflater }

        import(provideOverridingModule(), allowOverride = true)
    }
}

abstract class KodeinDIActivity : Activity(),
    KodeinDIActivityInitializer {
    override val parentDI: DI by closestDI()

    @Suppress("LeakingThis")
    override val di = initializeKodeinDI()
}
