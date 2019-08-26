package com.eygraber.kodein.android.components

import android.app.Application
import android.app.Service
import android.content.Context
import com.eygraber.kodein.KodeinComponentInitializer
import org.kodein.di.Kodein
import org.kodein.di.android.kodein
import org.kodein.di.bindings.subTypes
import org.kodein.di.erased.bind
import org.kodein.di.erased.provider
import org.kodein.di.erased.with
import org.kodein.di.jvmType

interface KodeinServiceInitializer : KodeinComponentInitializer<Service> {
  override fun initializeKodein() = Kodein.lazy {
    extend(parentKodein)

    bind<Context>(overrides = true).subTypes().with { type ->
      when(type.jvmType) {
        Application::class.java -> provider { kodeinComponent.application as Context }
        else -> provider { kodeinComponent as Context }
      }
    }

    import(provideOverridingModule(), allowOverride = true)
  }
}

abstract class KodeinService : Service(), KodeinServiceInitializer {
  override val parentKodein: Kodein by kodein()

  @Suppress("LeakingThis")
  override val kodein = initializeKodein()
}
