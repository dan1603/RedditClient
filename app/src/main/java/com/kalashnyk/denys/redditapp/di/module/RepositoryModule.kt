package com.kalashnyk.denys.redditapp.di.module

import com.kalashnyk.denys.redditapp.di.scope.RepositoryScope
import com.kalashnyk.denys.redditapp.repository.AppRepository
import com.kalashnyk.denys.redditapp.repository.api.RedditApi
import com.kalashnyk.denys.redditapp.repository.database.AppDatabase
import com.kalashnyk.denys.redditapp.repository.server.ServerCommunicator
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @RepositoryScope
    @Provides
    internal fun providesProductRepository(api: ServerCommunicator, database: AppDatabase): AppRepository {
        return AppRepository(api, database)
    }
}