package com.kalashnyk.denys.redditapp

import android.app.Application
import androidx.room.Room
import com.kalashnyk.denys.redditapp.di.component.*
import com.kalashnyk.denys.redditapp.di.module.ApiModule
import com.kalashnyk.denys.redditapp.di.module.DaoModule
import com.kalashnyk.denys.redditapp.di.module.RepositoryModule
import com.kalashnyk.denys.redditapp.di.module.ViewModelModule
import com.kalashnyk.denys.redditapp.repository.database.AppDatabase

class App: Application() {

    var viewModelComponent: ViewModelComponent? = null
        private set
    private var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        initRoom()
        initDagger()
    }

    private fun initRoom() {
        database = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "reddit.db")
                .allowMainThreadQueries()
                .build()
    }

    private fun initDagger() {
        val apiComponent = DaggerApiComponent.builder()
                .apiModule(ApiModule())
                .build()

        val daoComponent = DaggerDaoComponent.builder()
                .daoModule(DaoModule(database!!))
                .build()

        val repositoryComponent = DaggerRepositoryComponent.builder()
                .apiComponent(apiComponent)
                .daoComponent(daoComponent)
                .repositoryModule(RepositoryModule())
                .build()

        viewModelComponent = DaggerViewModelComponent.builder()
                .repositoryComponent(repositoryComponent)
                .viewModelModule(ViewModelModule())
                .build()
    }
}