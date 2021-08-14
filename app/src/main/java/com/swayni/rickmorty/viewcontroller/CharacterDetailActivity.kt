package com.swayni.rickmorty.viewcontroller

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.swayni.rickmorty.R
import com.swayni.rickmorty.adapter.EpisodesListAdapter
import com.swayni.rickmorty.databinding.ActivityCharacterDetailBinding
import com.swayni.rickmorty.service.remote.RemoteDataSourceImpl
import com.swayni.rickmorty.service.repository.Repository
import com.swayni.rickmorty.viewmodel.CharacterDetailViewModel

class CharacterDetailActivity : AppCompatActivity() {
    private var _binding : ActivityCharacterDetailBinding? =null
    private val binding get() = _binding!!

    private val adapter by lazy { EpisodesListAdapter() }
    private lateinit var repository: Repository
    private lateinit var viewModel:CharacterDetailViewModel
    private var id:Int? = null
    private var isExpandRecyclerview = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listViewEpisodes.adapter = adapter
        repository = Repository(RemoteDataSourceImpl())
        viewModel = CharacterDetailViewModel(repository)

        id = intent.getIntExtra("id", 0)

        viewModel.getCharacterInfo(id!!)

        viewModel.characterInfoLiveData.observe(this, { character->
            Picasso.get().load(character.image).fit().into(binding.image)
            binding.nameText.text = character.name
            binding.statusSpeciesText.text = "${character.status}, ${character.species}"
            binding.genderText.text = character.gender
            var idsString = ""
            character.episode.forEach { url->
                idsString = "${idsString},${url.substring(40)}"
            }
            viewModel.getEpisodesNames(idsString.substring(1))
        })

        viewModel.episodeInfoListLiveData.observe(this,{
            adapter.addEpisodeList(it)
            binding.progressBarComponent.progressBarContainer.visibility = GONE
        })

        viewModel.errorString.observe(this, {
            binding.progressBarComponent.progressBarContainer.visibility = GONE
            if (it != ""){
                AlertDialog.Builder(this).setMessage(it).setPositiveButton("Ok!"
                ) { dialog, which ->
                    dialog.dismiss()
                    finish()
                }.show()
            }
        })
    }

    fun expandAndCollapseRecyclerView(view: View) {
        if(isExpandRecyclerview){
            binding.listViewEpisodes.startAnimation(AnimationUtils.loadAnimation(this, R.anim.collapse))
            binding.expandAndCollapseButton.setImageResource(R.drawable.icon_arrow_drop_down)
            isExpandRecyclerview=false
        }else{
            binding.listViewEpisodes.visibility = VISIBLE
            binding.listViewEpisodes.startAnimation(AnimationUtils.loadAnimation(this, R.anim.expand))
            binding.expandAndCollapseButton.setImageResource(R.drawable.icon_arrow_drop_up)
            isExpandRecyclerview = true
        }

    }

    fun closeActivity(view: View) {
        finish()
    }
}