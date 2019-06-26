package com.kalashnyk.denys.redditapp.di.component

import com.kalashnyk.denys.redditapp.di.module.RepositoryModule
import com.kalashnyk.denys.redditapp.di.scope.RepositoryScope
import com.kalashnyk.denys.redditapp.repository.AppRepository
import dagger.Component

@RepositoryScope
@Component(modules = [RepositoryModule::class], dependencies = [ApiComponent::class, DaoComponent::class])
interface RepositoryComponent {
    val testRepository: AppRepository
}