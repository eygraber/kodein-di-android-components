package com.eygraber.kodein.androidx.components

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.eygraber.kodein.KodeinComponentInitializer
import com.eygraber.kodein.KodeinComponentInitializer.PARENT_FRAGMENT_TAG
import com.eygraber.kodein.androidx.ALLOWED_BUT_NOT_REQUIRED
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.provider

interface KodeinDialogFragmentInitializer : KodeinComponentInitializer<DialogFragment> {
    val bindParentFragment: Boolean get() = false

    override fun initializeKodein() = Kodein.lazy(allowSilentOverride = true) {
        val parentFragment = kodeinComponent.parentFragment
        if (parentFragment is KodeinAware) {
            extend(parentFragment.kodein, allowOverride = true)
        } else {
            extend(parentKodein)
        }

        bind<Fragment>() with provider { kodeinComponent }
        if (bindParentFragment) {
            bind<Fragment>(
                tag = PARENT_FRAGMENT_TAG, overrides = ALLOWED_BUT_NOT_REQUIRED
            ) with provider { parentFragment!! }
        }

        import(provideOverridingModule(), allowOverride = true)
    }
}

abstract class KodeinDialogFragment : DialogFragment(),
    KodeinDialogFragmentInitializer {
    override val parentKodein: Kodein by kodein()

    @Suppress("LeakingThis")
    override val kodein = initializeKodein()
}
