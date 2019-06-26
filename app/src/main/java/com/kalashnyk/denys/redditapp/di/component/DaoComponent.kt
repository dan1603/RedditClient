package com.kalashnyk.denys.redditapp.di.component

import com.kalashnyk.denys.redditapp.di.module.DaoModule
import com.kalashnyk.denys.redditapp.repository.database.AppDatabase
import dagger.Component

@Component(modules = [DaoModule::class])
interface DaoComponent {
    val database: AppDatabase
}