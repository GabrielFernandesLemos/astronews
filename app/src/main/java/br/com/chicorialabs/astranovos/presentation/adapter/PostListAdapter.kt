package br.com.chicorialabs.astranovos.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.databinding.ItemPostBinding

class PostListAdapter : ListAdapter<Post, PostListAdapter.PostViewHolder>(PostDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {

            fun from(parent: ViewGroup) : PostViewHolder {
                val binding: ItemPostBinding = ItemPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                false
                )
                return PostViewHolder(binding)
            }
        }

        fun bind(item: Post) {
            binding.post = item
        }

    }

    private class PostDiffCallback : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }

}