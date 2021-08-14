package com.swayni.rickmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swayni.rickmorty.R
import com.swayni.rickmorty.databinding.ItemCharacterBinding
import com.swayni.rickmorty.model.Character

class AllCharactersAdapter (private val listener: CharacterListener) : RecyclerView.Adapter<AllCharactersAdapter.ViewHolder>() {

    private val allCharactersArrayList = ArrayList<Character>()

    class  ViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(character:Character){
            Picasso.get().load(character.image).fit().into(binding.image)
            binding.characterName.text = character.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(allCharactersArrayList[position])
        holder.itemView.setOnClickListener {
            listener.characterOnClick(allCharactersArrayList[position])
        }
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.left_to_right)
    }

    override fun getItemCount(): Int {
        return allCharactersArrayList.size
    }

    fun addAllCharacters(characterList: List<Character>){
        allCharactersArrayList.addAll(characterList)
        notifyItemInserted(allCharactersArrayList.size)
    }

    interface CharacterListener{
        fun characterOnClick(character: Character)
    }
}