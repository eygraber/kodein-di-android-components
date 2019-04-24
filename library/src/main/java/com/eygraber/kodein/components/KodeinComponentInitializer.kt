@file:Suppress("ClassName")

package com.eygraber.kodein.components

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

interface KodeinComponentInitializer<T> : KodeinAware {
  object ACTIVITY_LAYOUT_INFLATER_TAG
  object PARENT_FRAGMENT_TAG

  val parentKodein: Kodein

  @Suppress("UNCHECKED_CAST")
  val kodeinComponent: T
    get() = this as T

  val kodeinModuleNmae: String get() = this::class.java.simpleName

  fun provideOverridingModule(): Kodein.Module = Kodein.Module(kodeinModuleNmae) {}

  fun initializeKodein(): Kodein
}