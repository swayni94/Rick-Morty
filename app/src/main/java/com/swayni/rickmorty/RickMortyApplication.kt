package com.swayni.rickmorty

import android.app.Application
import com.swayni.rickmorty.injection.component.AppComponent
import com.swayni.rickmorty.injection.component.DaggerAppComponent
import com.swayni.rickmorty.injection.modules.AppModule

class RickMortyApplication : Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
}