package com.kalashnyk.denys.redditapp.di.module

import com.kalashnyk.denys.redditapp.di.scope.ViewModelScope
import com.kalashnyk.denys.redditapp.domain.SubRedditTopPostsViewModel
import com.kalashnyk.denys.redditapp.repository.AppRepository
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @ViewModelScope
    @Provides
    internal fun providesListViewModel(repository: AppRepository): SubRedditTopPostsViewModel {
        return SubRedditTopPostsViewModel(repository)
    }
}