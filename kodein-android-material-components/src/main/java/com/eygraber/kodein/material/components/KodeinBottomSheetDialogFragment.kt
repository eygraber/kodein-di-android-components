package com.eygraber.kodein.material.components

import androidx.fragment.app.Fragment
import com.eygraber.kodein.KodeinComponentInitializer
import com.eygraber.kodein.KodeinComponentInitializer.PARENT_FRAGMENT_TAG
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.provider

interface KodeinBottomSheetDialogFragmentInitializer : KodeinComponentInitializer<BottomSheetDialogFragment> {
    val ignoreParentFragment: Boolean get() = false

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun initializeKodein() = Kodein.lazy {
        val parentFragment = kodeinComponent.parentFragment
        if (parentFragment is KodeinAware) {
            extend(parentFragment.kodein, allowOverride = true)
        } else {
            extend(parentKodein, allowOverride = true)
        }

        if (parentFragment != null && !ignoreParentFragment) {
            bind<Fragment>(tag = PARENT_FRAGMENT_TAG) with provider { parentFragment!! }
            bind<Fragment>(overrides = true) with provider { kodeinComponent }
        } else {
            bind<Fragment>() with provider { kodeinComponent }
        }

        import(provideOverridingModule(), allowOverride = true)
    }
}

abstract class KodeinBottomSheetDialogFragment : BottomSheetDialogFragment(),
    KodeinBottomSheetDialogFragmentInitializer {
    override val parentKodein: Kodein by kodein()

    @Suppress("LeakingThis")
    override val kodein = initializeKodein()
}
