package com.eygraber.kodein.di.androidx.components

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.eygraber.kodein.di.KodeinDIComponentInitializer
import com.eygraber.kodein.di.KodeinDIComponentInitializer.PARENT_FRAGMENT_TAG
import com.eygraber.kodein.di.androidx.ALLOWED_BUT_NOT_REQUIRED
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.bind
import org.kodein.di.provider

interface KodeinDIDialogFragmentInitializer :
    KodeinDIComponentInitializer<DialogFragment> {
    val bindParentFragment: Boolean get() = false

    override fun initializeKodeinDI() = DI.lazy(allowSilentOverride = true) {
        val parentFragment = kodeinDIComponent.parentFragment
        if (parentFragment is DIAware) {
            extend(parentFragment.di, allowOverride = true)
        } else {
            extend(parentDI)
        }

        bind<Fragment>() with provider { kodeinDIComponent }
        if (bindParentFragment) {
            bind<Fragment>(
                tag = PARENT_FRAGMENT_TAG, overrides = ALLOWED_BUT_NOT_REQUIRED
            ) with provider { parentFragment!! }
        }

        import(provideOverridingModule(), allowOverride = true)
    }
}

abstract class KodeinDIDialogFragment : DialogFragment(),
    KodeinDIDialogFragmentInitializer {
    override val parentDI: DI by closestDI()

    @Suppress("LeakingThis")
    override val di = initializeKodeinDI()
}
