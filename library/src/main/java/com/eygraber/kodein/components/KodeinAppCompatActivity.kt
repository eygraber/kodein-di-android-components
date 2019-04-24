package com.eygraber.kodein.components

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.eygraber.kodein.components.KodeinComponentInitializer.ACTIVITY_LAYOUT_INFLATER_TAG
import org.kodein.di.Kodein
import org.kodein.di.android.kodein
import org.kodein.di.bindings.subTypes
import org.kodein.di.erased.bind
import org.kodein.di.erased.provider
import org.kodein.di.erased.with
import org.kodein.di.jvmType

interface KodeinAppCompatActivityInitializer : KodeinComponentInitializer<AppCompatActivity> {
  override fun initializeKodein() = Kodein.lazy {
    extend(parentKodein)

    bind<Context>(overrides = true).subTypes().with { type ->
      when(type.jvmType) {
        Application::class.java -> provider { kodeinComponent.application as Context }
        else -> provider { kodeinComponent as Context }
      }
    }

    bind<FragmentManager>() with provider { kodeinComponent.supportFragmentManager }
    bind<LayoutInflater>(tag = ACTIVITY_LAYOUT_INFLATER_TAG) with provider { kodeinComponent.layoutInflater }

    import(provideOverridingModule(), allowOverride = true)
  }
}

abstract class KodeinAppCompatActivity : AppCompatActivity(), KodeinAppCompatActivityInitializer {
  override val parentKodein: Kodein by kodein()

  @Suppress("LeakingThis")
  override val kodein = initializeKodein()
}
