package com.eygraber.kodein.di.androidx.components

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.eygraber.kodein.di.KodeinDIComponentInitializer
import com.eygraber.kodein.di.KodeinDIComponentInitializer.ACTIVITY_LAYOUT_INFLATER_TAG
import org.kodein.di.DI
import org.kodein.di.android.di
import org.kodein.di.bind
import org.kodein.di.bindings.subTypes
import org.kodein.di.provider
import org.kodein.di.with
import org.kodein.type.jvmType

interface KodeinDIFragmentActivityInitializer :
    KodeinDIComponentInitializer<FragmentActivity> {
    override fun initializeKodeinDI() = DI.lazy {
        extend(parentDI)

        bind<Context>(overrides = true).subTypes().with { type ->
            when (type.jvmType) {
                Application::class.java -> provider { kodeinDIComponent.application as Context }
                else -> provider { kodeinDIComponent }
            }
        }

        bind<FragmentManager>() with provider { kodeinDIComponent.supportFragmentManager }
        bind<LayoutInflater>(tag = ACTIVITY_LAYOUT_INFLATER_TAG) with provider { kodeinDIComponent.layoutInflater }

        import(provideOverridingModule(), allowOverride = true)
    }
}

abstract class KodeinDIFragmentActivity : FragmentActivity(),
    KodeinDIFragmentActivityInitializer {
    override val parentDI: DI by di()

    @Suppress("LeakingThis")
    override val di = initializeKodeinDI()
}
