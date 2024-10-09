package ru.aleynikov.recyclerview.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import ru.aleynikov.recyclerview.R
import ru.aleynikov.recyclerview.api.CharacterDto
import ru.aleynikov.recyclerview.databinding.CharacterBinding

class RnMAdapter(
    private val onClick: (CharacterDto) -> Unit
) : PagingDataAdapter<CharacterDto, RnMAdapter.RnMViewHolder>(
    DiffUtilCallback()
) {

    inner class RnMViewHolder(val binding: CharacterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RnMViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item.let {
                Glide.with(avatar.context).load(it?.image).into(holder.binding.avatar)
                holder.binding.characterName.text = it?.name
                holder.binding.characterStatus.text = holder.binding.root.context.getString(
                    R.string.status_line, it?.status, it?.species
                )
                holder.binding.characterLocation.text = it?.location?.name
            }
        }
        holder.binding.root.setOnClickListener { onClick(item!!) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RnMViewHolder {
        val binding = CharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RnMViewHolder(binding)
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<CharacterDto>() {
    override fun areItemsTheSame(
        oldItem: CharacterDto, newItem: CharacterDto
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CharacterDto, newItem: CharacterDto
    ): Boolean = oldItem == newItem
}