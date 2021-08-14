package com.swayni.rickmorty.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swayni.rickmorty.databinding.ItemEpisodeBinding
import com.swayni.rickmorty.model.Episode

class EpisodesListAdapter : RecyclerView.Adapter<EpisodesListAdapter.ViewHolder>() {

    private val episodesArrayList = ArrayList<Episode>()

    class ViewHolder(private val binding:ItemEpisodeBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(episode: Episode){
            binding.episodeText.text = "${episode.name} (${episodeStringToFormat(episode.episode)})"
        }

        fun episodeStringToFormat(episodeString: String):String{
            var resultString = ""
            var term = episodeString.substring(0,3)
            resultString = if (term[1] == '0'){
                "${term.replace("0", "")} - "
            } else{
                "$term -"
            }
            term = episodeString.substring(3)
            resultString = if (term[1] == '0'){
                "$resultString${term.replace("0","")}"
            } else{
                "$resultString$term"
            }

            return resultString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(episodesArrayList[position])
    }

    override fun getItemCount(): Int {
        return episodesArrayList.size
    }

    fun addEpisodeList(episodeList: List<Episode>){
        episodesArrayList.addAll(episodeList)
        notifyItemInserted(episodesArrayList.size)
    }
}