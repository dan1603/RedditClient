package com.kalashnyk.denys.redditapp.di.module

import com.kalashnyk.denys.redditapp.repository.database.AppDatabase
import com.kalashnyk.denys.redditapp.repository.database.dao.RedditPostDao
import dagger.Module
import dagger.Provides

@Module
class DaoModule(private val database: AppDatabase) {

    @Provides
    internal fun providesRoomDatabase(): AppDatabase {
        return database
    }

    @Provides
    internal fun providesPostDao(appDatabase: AppDatabase): RedditPostDao {
        return appDatabase.postDao()
    }
}