package com.swayni.rickmorty.viewcontroller

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.swayni.rickmorty.RickMortyApplication
import com.swayni.rickmorty.adapter.AllCharactersAdapter
import com.swayni.rickmorty.databinding.ActivityCharactersBinding
import com.swayni.rickmorty.model.Character
import com.swayni.rickmorty.viewmodel.CharacterViewModel
import com.swayni.rickmorty.viewmodel.util.viewModelProvider
import javax.inject.Inject

class CharactersActivity : AppCompatActivity() , AllCharactersAdapter.CharacterListener{
    private var _binding : ActivityCharactersBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private fun getViewModel():CharacterViewModel{
        return viewModelProvider(viewModelFactory)
    }

    private val adapter by lazy { AllCharactersAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        RickMortyApplication().appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.charactersRecyclerView.adapter = adapter
        getViewModel().getAllCharacters()

        getViewModel().allCharacterLiveData.observe(this, {
            adapter.addAllCharacters(it)
            binding.progressBarComponent.progressBarContainer.visibility = GONE
        })

        getViewModel().errorString.observe(this, {
            binding.progressBarComponent.progressBarContainer.visibility = GONE
            if (it != ""){
                AlertDialog.Builder(this).setMessage(it).setPositiveButton("Repeat!"
                ) { dialog, which ->
                    getViewModel().getAllCharacters()
                    binding.progressBarComponent.progressBarContainer.visibility = VISIBLE
                    dialog.dismiss()
                }.setNegativeButton("Cancel"
                ) { dialog, which -> dialog.dismiss() }.show()
            }
        })
    }

    override fun characterOnClick(character: Character) {
        Intent(this, CharacterDetailActivity::class.java).apply {
            putExtra("id", character.id)
            startActivity(this)
        }
    }
}