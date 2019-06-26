package com.kalashnyk.denys.redditapp.presentation.item

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kalashnyk.denys.redditapp.databinding.NetworkStateItemBinding
import com.kalashnyk.denys.redditapp.repository.network.NetworkState
import com.kalashnyk.denys.redditapp.repository.network.Status
import kotlinx.android.synthetic.main.network_state_item.view.*

class NetworkStateItemViewHolder(private val binding: NetworkStateItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(networkState: NetworkState?) {
        binding.root.networkProgressBar.visibility = toVisibility(networkState?.status == Status.RUNNING)
        binding.root.btnRetry.visibility = toVisibility(networkState?.status == Status.FAILED)
        binding.root.txtNetworkError.visibility = toVisibility(networkState?.msg != null)
        binding.root.txtNetworkError.text = networkState?.msg
    }

    companion object {
        fun create(parent: ViewGroup): NetworkStateItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = NetworkStateItemBinding.inflate(inflater)
            return NetworkStateItemViewHolder(binding)
        }

        fun toVisibility(constraint : Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
