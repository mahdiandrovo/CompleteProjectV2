package com.drovo.completeprojectv2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.drovo.completeprojectv2.data.Dog
import com.drovo.completeprojectv2.databinding.EachRowBinding
import javax.inject.Inject

class DogAdapter @Inject constructor(

): PagingDataAdapter<Dog, DogAdapter.DogViewHolder>(Diff) {

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dogs = getItem(position)
        if (dogs != null){
            holder.bind(dogs)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        return DogViewHolder(EachRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class DogViewHolder(private val binding: EachRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(dogs: Dog){
            binding.apply {
                image.load(dogs.url)
            }
        }
    }

    object Diff: DiffUtil.ItemCallback<Dog>(){
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean =
            oldItem == newItem
    }
}