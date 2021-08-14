package com.swayni.rickmorty.viewcontroller

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.swayni.rickmorty.adapter.AllCharactersAdapter
import com.swayni.rickmorty.databinding.ActivityCharactersBinding
import com.swayni.rickmorty.model.Character
import com.swayni.rickmorty.service.remote.RemoteDataSourceImpl
import com.swayni.rickmorty.service.repository.Repository
import com.swayni.rickmorty.viewmodel.CharacterViewModel
import okhttp3.internal.wait

class CharactersActivity : AppCompatActivity() , AllCharactersAdapter.CharacterListener{

    private var _binding : ActivityCharactersBinding? = null
    private val binding get() = _binding!!

   // private val viewModel by lazy { CharacterViewModel() }
    private lateinit var repository : Repository
    private lateinit var viewModel : CharacterViewModel
    private val adapter by lazy { AllCharactersAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.charactersRecyclerView.adapter = adapter
        repository = Repository(RemoteDataSourceImpl())
        viewModel = CharacterViewModel(repository)
        viewModel.getAllCharacters()

        viewModel.allCharacterLiveData.observe(this, {
            adapter.addAllCharacters(it)
            binding.progressBarComponent.progressBarContainer.visibility = GONE
        })

        viewModel.errorString.observe(this, {
            binding.progressBarComponent.progressBarContainer.visibility = GONE
            if (it != ""){
                AlertDialog.Builder(this).setMessage(it).setPositiveButton("Repeat!"
                ) { dialog, which ->
                    viewModel.getAllCharacters()
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