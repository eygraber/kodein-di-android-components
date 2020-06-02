@file:Suppress("ClassName")

package com.eygraber.kodein.di

import org.kodein.di.DI
import org.kodein.di.DIAware

interface KodeinDIComponentInitializer<T> : DIAware {
    object ACTIVITY_LAYOUT_INFLATER_TAG
    object PARENT_FRAGMENT_TAG

    val parentDI: DI

    @Suppress("UNCHECKED_CAST")
    val kodeinDIComponent: T
        get() = this as T

    val kodeinDIModuleNmae: String get() = this::class.java.simpleName

    fun provideOverridingModule(): DI.Module = DI.Module(kodeinDIModuleNmae) {}

    fun initializeKodeinDI(): DI
}
