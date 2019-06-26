package com.kalashnyk.denys.redditapp.repository.base

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.kalashnyk.denys.redditapp.repository.network.NetworkState

data class BaseListing<T>(
        val pagedList: LiveData<PagedList<T>>,
        val networkState: LiveData<NetworkState>,
        val refreshState: LiveData<NetworkState>,
        val refresh: () -> Unit,
        val retry: () -> Unit)