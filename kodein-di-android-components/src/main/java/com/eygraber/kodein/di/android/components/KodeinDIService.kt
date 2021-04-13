package com.eygraber.kodein.di.android.components

import android.app.Application
import android.app.Service
import android.content.Context
import com.eygraber.kodein.di.KodeinDIComponentInitializer
import org.kodein.di.DI
import org.kodein.di.android.closestDI
import org.kodein.di.bind
import org.kodein.di.bindings.subTypes
import org.kodein.di.provider
import org.kodein.di.with
import org.kodein.type.jvmType

interface KodeinDIServiceInitializer :
    KodeinDIComponentInitializer<Service> {
    override fun initializeKodeinDI() = DI.lazy {
        extend(parentDI)

        bind<Context>(overrides = true).subTypes().with { type ->
            when (type.jvmType) {
                Application::class.java -> provider { kodeinDIComponent.application as Context }
                else -> provider { kodeinDIComponent }
            }
        }

        import(provideOverridingModule(), allowOverride = true)
    }
}

abstract class KodeinDIService : Service(),
    KodeinDIServiceInitializer {
    override val parentDI: DI by closestDI()

    @Suppress("LeakingThis")
    override val di = initializeKodeinDI()
}
