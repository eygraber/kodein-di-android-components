package com.eygraber.kodein.androidx

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleService
import org.kodein.di.Kodein
import org.kodein.di.bindings.NoArgSimpleBindingKodein
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import org.kodein.di.erased.scoped
import org.kodein.di.erased.singleton
import org.kodein.di.internal.synchronizedIfNull
import org.kodein.di.generic.scoped as genericallyScoped
import org.kodein.di.generic.singleton as genericSingleton

@PublishedApi
internal object AndroidScope : Scope<Any> {
    private val newRegistry = ::StandardScopeRegistry
    private val map = HashMap<LifecycleOwner, ScopeRegistry>()

    override fun getRegistry(context: Any): ScopeRegistry {
        (context as? LifecycleOwner)
            ?: throw IllegalStateException("The context of an AndroidScope must be a LifecycleOwner")

        return synchronizedIfNull(
            lock = map,
            predicate = { map[context] },
            ifNotNull = { it },
            ifNull = {
                val registry = newRegistry()
                map[context] = registry
                context.lifecycle.addObserver(
                    object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            context.lifecycle.removeObserver(this)
                            registry.clear()
                            map.remove(context)
                        }
                    }
                )
                registry
            }
        )
    }
}

@PublishedApi
internal object FragmentViewScope : Scope<Fragment> {
    private val newRegistry = ::StandardScopeRegistry
    private val map = HashMap<LifecycleOwner, ScopeRegistry>()

    override fun getRegistry(context: Fragment): ScopeRegistry {
        return synchronizedIfNull(
            lock = map,
            predicate = { map[context] },
            ifNotNull = { it },
            ifNull = {
                val registry = newRegistry()
                map[context] = registry
                context.viewLifecycleOwner.lifecycle.addObserver(
                    object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            context.lifecycle.removeObserver(this)
                            registry.clear()
                            map.remove(context)
                        }
                    }
                )
                registry
            }
        )
    }
}

inline fun <reified T : Any> Kodein.Builder.activitySingleton(
    noinline creator: NoArgSimpleBindingKodein<Activity>.() -> T
) = scoped<Activity>(AndroidScope).singleton(creator = creator)

inline fun <reified T : Any> Kodein.Builder.genericActivitySingleton(
    noinline creator: NoArgSimpleBindingKodein<Activity>.() -> T
) = genericallyScoped<Activity>(AndroidScope).genericSingleton(creator = creator)

inline fun <reified T : Any> Kodein.Builder.fragmentSingleton(
    noinline creator: NoArgSimpleBindingKodein<Fragment>.() -> T
) = scoped<Fragment>(AndroidScope).singleton(creator = creator)

inline fun <reified T : Any> Kodein.Builder.fragmentViewSingleton(
    noinline creator: NoArgSimpleBindingKodein<Fragment>.() -> T
) = scoped(FragmentViewScope).singleton(creator = creator)

inline fun <reified T : Any> Kodein.Builder.genericFragmentSingleton(
    noinline creator: NoArgSimpleBindingKodein<Fragment>.() -> T
) = genericallyScoped<Fragment>(AndroidScope).genericSingleton(creator = creator)

inline fun <reified T : Any> Kodein.Builder.genericFragmentViewSingleton(
    noinline creator: NoArgSimpleBindingKodein<Fragment>.() -> T
) = genericallyScoped(FragmentViewScope).genericSingleton(creator = creator)

inline fun <reified T : Any> Kodein.Builder.lifecycleServiceSingleton(
    noinline creator: NoArgSimpleBindingKodein<LifecycleService>.() -> T
) = scoped<LifecycleService>(AndroidScope).singleton(creator = creator)
