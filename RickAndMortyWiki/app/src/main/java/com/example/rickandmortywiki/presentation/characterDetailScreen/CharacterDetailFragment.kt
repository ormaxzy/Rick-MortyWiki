package com.example.rickandmortywiki.presentation.characterDetailScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortywiki.api.Character


class CharacterDetailFragment : Fragment() {


    private var character: Character? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        character = CharacterDetailFragmentArgs.fromBundle(requireArguments()).character
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return ComposeView(requireContext()).apply {
            if (character != null) {
                setContent {
                    val navController = rememberNavController()
                    CharacterDetailScreen(character = character!!)
                }
            }
            else{
                Log.d("@@@CharacterFragment", "is null")
            }
        }
    }
}
