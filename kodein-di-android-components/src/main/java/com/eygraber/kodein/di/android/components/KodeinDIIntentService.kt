package com.eygraber.kodein.di.android.components

import android.app.Application
import android.app.IntentService
import android.app.Service
import android.content.Context
import com.eygraber.kodein.di.KodeinDIComponentInitializer
import org.kodein.di.DI
import org.kodein.di.android.di
import org.kodein.di.bind
import org.kodein.di.bindings.subTypes
import org.kodein.di.provider
import org.kodein.di.with
import org.kodein.type.jvmType

interface KodeinDIIntentServiceInitializer :
    KodeinDIComponentInitializer<IntentService> {
  override fun initializeKodeinDI() = DI.lazy {
    extend(parentDI)

    bind<Context>(overrides = true).subTypes().with { type ->
      when(type.jvmType) {
        Application::class.java -> provider { kodeinDIComponent.application as Context }
        else -> provider { kodeinDIComponent }
      }
    }

    import(provideOverridingModule(), allowOverride = true)
  }
}

abstract class KodeinDIIntentService : Service(),
  KodeinDIIntentServiceInitializer {
  override val parentDI: DI by di()

  @Suppress("LeakingThis")
  override val di = initializeKodeinDI()
}
