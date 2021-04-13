package com.eygraber.kodein.di.androidx.components

import androidx.fragment.app.Fragment
import com.eygraber.kodein.di.KodeinDIComponentInitializer
import com.eygraber.kodein.di.KodeinDIComponentInitializer.PARENT_FRAGMENT_TAG
import com.eygraber.kodein.di.androidx.ALLOWED_BUT_NOT_REQUIRED
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.bind
import org.kodein.di.provider

interface KodeinDIFragmentInitializer :
    KodeinDIComponentInitializer<Fragment> {
    val ignoreParentFragment: Boolean get() = false

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun initializeKodeinDI() = DI.lazy(allowSilentOverride = true) {
        val parentFragment = kodeinDIComponent.parentFragment
        if (parentFragment is DIAware) {
            extend(parentFragment.di, allowOverride = true)
        } else {
            extend(parentDI, allowOverride = true)
        }

        if (parentFragment != null && !ignoreParentFragment) {
            bind<Fragment>(
                tag = PARENT_FRAGMENT_TAG, overrides = ALLOWED_BUT_NOT_REQUIRED
            ) with provider { parentFragment!! }
            bind<Fragment>(overrides = true) with provider { kodeinDIComponent }
        } else {
            bind<Fragment>() with provider { kodeinDIComponent }
        }

        import(provideOverridingModule(), allowOverride = true)
    }
}

abstract class KodeinDIFragment : Fragment(),
    KodeinDIFragmentInitializer {
    override val parentDI: DI by closestDI()

    @Suppress("LeakingThis")
    override val di = initializeKodeinDI()
}
