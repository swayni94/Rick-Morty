package com.swayni.rickmorty.injection.component

import android.content.Context
import com.swayni.rickmorty.injection.modules.AppModule
import com.swayni.rickmorty.injection.modules.RepositoryModule
import com.swayni.rickmorty.viewcontroller.CharacterDetailActivity
import com.swayni.rickmorty.viewcontroller.CharactersActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    RepositoryModule::class
])
interface AppComponent {

    fun context(): Context

    fun inject(charactersActivity: CharactersActivity)
    fun inject(characterDetailActivity: CharacterDetailActivity)
}