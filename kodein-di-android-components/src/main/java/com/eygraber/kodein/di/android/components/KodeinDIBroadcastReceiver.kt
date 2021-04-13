package com.eygraber.kodein.di.android.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.android.di

abstract class KodeinDIBroadcastReceiver : BroadcastReceiver(), DIAware {
  private lateinit var context: Context

  override val di: DI by closestDI { context }

  final override fun onReceive(context: Context, intent: Intent) {
    this.context = context
    onBroadcastReceived(context, intent)
  }

  /**
   * Override this instead of onReceive to handle received broadcast intents.
   *
   * @param context The context.
   * @param intent The intent.
   */
  abstract fun onBroadcastReceived(context: Context, intent: Intent)
}
