package com.blockstream.green.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.blockstream.gdk.AssetManager
import com.blockstream.gdk.GreenWallet
import com.blockstream.green.BuildConfig
import com.blockstream.green.database.WalletRepository
import com.blockstream.green.gdk.SessionManager
import com.blockstream.green.lifecycle.AppLifecycleObserver
import com.blockstream.green.settings.Migrator
import com.blockstream.green.settings.SettingsManager
import com.blockstream.green.utils.AppKeystore
import com.blockstream.green.utils.QATester
import com.blockstream.green.utils.isDevelopmentFlavor
import com.blockstream.libgreenaddress.KotlinGDK
import com.blockstream.libwally.KotlinWally
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CryptoModule {
    @Singleton
    @Provides
    fun provideKotlinGDK(): KotlinGDK {
        return KotlinGDK()
    }

    @Singleton
    @Provides
    fun provideKotlinWally(): KotlinWally {
        return KotlinWally()
    }

    @Singleton
    @Provides
    fun provideGreenWallet(
        @ApplicationContext context: Context,
        gdk: KotlinGDK,
        wally: KotlinWally
    ): GreenWallet {
        return GreenWallet(gdk, wally, context.filesDir.absolutePath, context.isDevelopmentFlavor())
    }

    @Singleton
    @Provides
    fun provideSessionManager(
        settingsManager: SettingsManager,
        assetManager: AssetManager,
        greenWallet: GreenWallet,
        QATester: QATester
    ): SessionManager {
        return SessionManager(settingsManager, assetManager, greenWallet, QATester)
    }

    @Singleton
    @Provides
    fun provideAssetManager(@ApplicationContext context: Context, QATester: QATester): AssetManager {
        return AssetManager(context, QATester, BuildConfig.APPLICATION_ID)
    }

    @Singleton
    @Provides
    fun provideSettingsManager(@ApplicationContext context: Context): SettingsManager {
        return SettingsManager(context)
    }

    @Singleton
    @Provides
    fun provideAppKeystore(): AppKeystore {
        return AppKeystore()
    }

    @Singleton
    @Provides
    fun provideMigrator(@ApplicationContext context: Context, walletRepository: WalletRepository, greenWallet: GreenWallet, settingsManager: SettingsManager): Migrator {
        return Migrator(context, walletRepository, greenWallet, settingsManager)
    }

    @Singleton
    @Provides
    fun provideAppLifecycleObserver(): AppLifecycleObserver {
        return AppLifecycleObserver()
    }

    @Singleton
    @Provides
    fun provideQATester(@ApplicationContext context: Context): QATester {
        return QATester(context)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}