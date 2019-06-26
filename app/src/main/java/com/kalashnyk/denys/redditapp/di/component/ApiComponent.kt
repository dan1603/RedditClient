package com.kalashnyk.denys.redditapp.di.component

import com.kalashnyk.denys.redditapp.di.module.ApiModule
import com.kalashnyk.denys.redditapp.di.scope.ApiScope
import com.kalashnyk.denys.redditapp.repository.api.RedditApi
import com.kalashnyk.denys.redditapp.repository.server.ServerCommunicator
import dagger.Component

@ApiScope
@Component(modules = [ApiModule::class])
public interface ApiComponent {
    val communicator: ServerCommunicator
}