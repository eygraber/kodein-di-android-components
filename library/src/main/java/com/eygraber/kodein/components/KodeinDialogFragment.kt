package com.eygraber.kodein.components

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.eygraber.kodein.components.KodeinComponentInitializer.PARENT_FRAGMENT_TAG
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.provider

interface KodeinDialogFragmentInitializer : KodeinComponentInitializer<DialogFragment> {
  val bindParentFragment: Boolean get() = false

  override fun initializeKodein() = Kodein.lazy {
    val parentFragment = kodeinComponent.parentFragment
    if(parentFragment is KodeinAware) {
      extend(parentFragment.kodein, allowOverride = true)
    }
    else {
      extend(parentKodein)
    }

    bind<Fragment>() with provider { kodeinComponent }
    if(bindParentFragment) {
      bind<Fragment>(PARENT_FRAGMENT_TAG) with provider { kodeinComponent.parentFragment!! }
    }

    import(provideOverridingModule(), allowOverride = true)
  }
}

abstract class KodeinDialogFragment : DialogFragment(), KodeinDialogFragmentInitializer {
  override val parentKodein: Kodein by kodein()

  @Suppress("LeakingThis")
  override val kodein = initializeKodein()
}
