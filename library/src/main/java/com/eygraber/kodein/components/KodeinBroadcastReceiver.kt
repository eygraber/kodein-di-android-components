package com.eygraber.kodein.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

abstract class KodeinBroadcastReceiver : BroadcastReceiver(), KodeinAware {
  private lateinit var context: Context

  override val kodein: Kodein by kodein { context }

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