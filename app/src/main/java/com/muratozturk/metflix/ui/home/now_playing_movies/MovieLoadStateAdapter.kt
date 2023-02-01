package com.muratozturk.metflix.ui.home.now_playing_movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.metflix.databinding.LoadStateViewBinding

class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            LoadStateViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class ViewHolder(private val binding: LoadStateViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retry: () -> Unit) {
            with(binding) {
                loadStateRetry.isVisible = loadState !is LoadState.Loading
                loadStateErrorMessage.isVisible = loadState !is LoadState.Loading
                loadStateProgress.isVisible = loadState is LoadState.Loading

                if (loadState is LoadState.Error) {
                    loadStateErrorMessage.text = loadState.error.localizedMessage
                }

                loadStateRetry.setOnClickListener {
                    retry.invoke()
                }

            }
        }
    }
}