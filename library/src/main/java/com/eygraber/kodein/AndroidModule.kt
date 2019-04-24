@file:Suppress("DEPRECATION")

package com.eygraber.kodein

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.AppOpsManager
import android.app.Application
import android.app.DownloadManager
import android.app.KeyguardManager
import android.app.NotificationManager
import android.app.SearchManager
import android.app.UiModeManager
import android.app.WallpaperManager
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.NetworkStatsManager
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.RestrictionsManager
import android.content.SharedPreferences
import android.content.pm.LauncherApps
import android.content.pm.ShortcutManager
import android.content.res.Resources
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.fingerprint.FingerprintManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.midi.MidiManager
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.BatteryManager
import android.os.Build
import android.os.DropBoxManager
import android.os.HardwarePropertiesManager
import android.os.PowerManager
import android.os.UserManager
import android.os.Vibrator
import android.os.health.SystemHealthManager
import android.os.storage.StorageManager
import android.preference.PreferenceManager
import android.print.PrintManager
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import org.kodein.di.DKodeinAware
import org.kodein.di.Kodein.Module
import org.kodein.di.bindings.subTypes
import org.kodein.di.erased.bind
import org.kodein.di.erased.factory
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.with

@SuppressLint("ObsoleteSdkInt")
fun autoAndroidModule(app: Application) = Module("Auto Android (No Context Scope)") {
  bind<Context>().subTypes().with {
    provider { app.applicationContext }
  }

  bind<ContentResolver>() with provider { androidContext.contentResolver }

  bind<Resources>() with provider { androidContext.resources }

  bind<SharedPreferences>() with provider {
    PreferenceManager.getDefaultSharedPreferences(androidContext)
  }

  bind<SharedPreferences>() with factory { name: String ->
    androidContext.getSharedPreferences(name, Context.MODE_PRIVATE)
  }

  bind<AccessibilityManager>() with provider { androidContext.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager }
  bind<AccountManager>() with provider { androidContext.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager }
  bind<ActivityManager>() with provider { androidContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }
  bind<AlarmManager>() with provider { androidContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager }
  bind<AudioManager>() with provider { androidContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
  bind<ClipboardManager>() with provider { androidContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
  bind<ConnectivityManager>() with provider { androidContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
  bind<DevicePolicyManager>() with provider { androidContext.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
  bind<DownloadManager>() with provider { androidContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
  bind<DropBoxManager>() with provider { androidContext.getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager }
  bind<InputMethodManager>() with provider { androidContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
  bind<KeyguardManager>() with provider { androidContext.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
  bind<LayoutInflater>() with provider { androidContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
  bind<LocationManager>() with provider { androidContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
  bind<NfcManager>() with provider { androidContext.getSystemService(Context.NFC_SERVICE) as NfcManager }
  bind<NotificationManager>() with provider { androidContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
  bind<PowerManager>() with provider { androidContext.getSystemService(Context.POWER_SERVICE) as PowerManager }
  bind<SearchManager>() with provider { androidContext.getSystemService(Context.SEARCH_SERVICE) as SearchManager }
  bind<SensorManager>() with provider { androidContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
  bind<StorageManager>() with provider { androidContext.getSystemService(Context.STORAGE_SERVICE) as StorageManager }
  bind<TelephonyManager>() with provider { androidContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
  bind<TextServicesManager>() with provider { androidContext.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager }
  bind<UiModeManager>() with provider { androidContext.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager }
  bind<UsbManager>() with provider { androidContext.getSystemService(Context.USB_SERVICE) as UsbManager }
  bind<Vibrator>() with provider { androidContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
  bind<WallpaperManager>() with provider { androidContext.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager }
  bind<WifiP2pManager>() with provider { androidContext.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager }
  bind<WifiManager>() with provider { androidContext.getSystemService(Context.WIFI_SERVICE) as WifiManager }
  bind<WindowManager>() with provider { androidContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

  if(Build.VERSION.SDK_INT >= 16) {
    bind<InputManager>() with provider { androidContext.getSystemService(Context.INPUT_SERVICE) as InputManager }
    bind<MediaRouter>() with provider { androidContext.getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter }
    bind<NsdManager>() with provider { androidContext.getSystemService(Context.NSD_SERVICE) as NsdManager }
  }

  if(Build.VERSION.SDK_INT >= 17) {
    bind<DisplayManager>() with provider { androidContext.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
    bind<UserManager>() with provider { androidContext.getSystemService(Context.USER_SERVICE) as UserManager }
  }

  if(Build.VERSION.SDK_INT >= 18) {
    bind<BluetoothManager>() with provider { androidContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
  }

  if(Build.VERSION.SDK_INT >= 19) {
    bind<AppOpsManager>() with provider { androidContext.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager }
    bind<CaptioningManager>() with provider { androidContext.getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager }
    bind<ConsumerIrManager>() with provider { androidContext.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager }
    bind<PrintManager>() with provider { androidContext.getSystemService(Context.PRINT_SERVICE) as PrintManager }
  }

  if(Build.VERSION.SDK_INT >= 21) {
    bind<AppWidgetManager>() with provider { androidContext.getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager }
    bind<BatteryManager>() with provider { androidContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
    bind<CameraManager>() with provider { androidContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager }
    bind<JobScheduler>() with provider { androidContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler }
    bind<LauncherApps>() with provider { androidContext.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps }
    bind<MediaProjectionManager>() with provider { androidContext.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager }
    bind<MediaSessionManager>() with provider { androidContext.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager }
    bind<RestrictionsManager>() with provider { androidContext.getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager }
    bind<TelecomManager>() with provider { androidContext.getSystemService(Context.TELECOM_SERVICE) as TelecomManager }
    bind<TvInputManager>() with provider { androidContext.getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager }
  }

  if(Build.VERSION.SDK_INT >= 22) {
    bind<SubscriptionManager>() with provider { androidContext.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager }
    bind<UsageStatsManager>() with provider { androidContext.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager }
  }

  if(Build.VERSION.SDK_INT >= 23) {
    bind<CarrierConfigManager>() with provider { androidContext.getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager }
    bind<FingerprintManager>() with provider { androidContext.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager }
    bind<MidiManager>() with provider { androidContext.getSystemService(Context.MIDI_SERVICE) as MidiManager }
    bind<NetworkStatsManager>() with provider { androidContext.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager }
  }

  if(Build.VERSION.SDK_INT >= 24) {
    bind<HardwarePropertiesManager>() with provider { androidContext.getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager }
    bind<SystemHealthManager>() with provider { androidContext.getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager }
  }

  if(Build.VERSION.SDK_INT >= 25) {
    bind<ShortcutManager>() with provider { androidContext.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager }
  }
}

private val DKodeinAware.androidContext get() = instance<Context>()
