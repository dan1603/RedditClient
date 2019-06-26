package com.kalashnyk.denys.redditapp.di.component

import com.kalashnyk.denys.redditapp.di.module.ViewModelModule
import com.kalashnyk.denys.redditapp.di.scope.ViewModelScope
import com.kalashnyk.denys.redditapp.presentation.activity.main.MainActivity
import dagger.Component

@ViewModelScope
@Component(modules = [ViewModelModule::class], dependencies = [RepositoryComponent::class])
interface ViewModelComponent {
    fun inject(activity: MainActivity)
}