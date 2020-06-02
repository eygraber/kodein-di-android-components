package com.eygraber.kodein.di.material.components

import androidx.fragment.app.Fragment
import com.eygraber.kodein.di.KodeinDIComponentInitializer
import com.eygraber.kodein.di.KodeinDIComponentInitializer.PARENT_FRAGMENT_TAG
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.bind
import org.kodein.di.provider

interface KodeinDIBottomSheetDialogFragmentInitializer :
    KodeinDIComponentInitializer<BottomSheetDialogFragment> {
    val ignoreParentFragment: Boolean get() = false

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun initializeKodeinDI() = DI.lazy {
        val parentFragment = kodeinDIComponent.parentFragment
        if (parentFragment is DIAware) {
            extend(parentFragment.di, allowOverride = true)
        } else {
            extend(parentDI, allowOverride = true)
        }

        if (parentFragment != null && !ignoreParentFragment) {
            bind<Fragment>(tag = PARENT_FRAGMENT_TAG) with provider { parentFragment!! }
            bind<Fragment>(overrides = true) with provider { kodeinDIComponent }
        } else {
            bind<Fragment>() with provider { kodeinDIComponent }
        }

        import(provideOverridingModule(), allowOverride = true)
    }
}

abstract class KodeinDIBottomSheetDialogFragment : BottomSheetDialogFragment(),
    KodeinDIBottomSheetDialogFragmentInitializer {
    override val parentDI: DI by di()

    @Suppress("LeakingThis")
    override val di = initializeKodeinDI()
}
