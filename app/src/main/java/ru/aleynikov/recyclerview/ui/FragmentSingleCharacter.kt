package ru.aleynikov.recyclerview.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.aleynikov.recyclerview.databinding.FragmentSingleCharacterBinding

private const val TAG = "TAG"

@AndroidEntryPoint
class FragmentSingleCharacter : Fragment() {
    private var _binding: FragmentSingleCharacterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleCharacterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: FragmentSingleCharacterArgs by navArgs()

        (activity as AppCompatActivity).supportActionBar?.title = args.singleCharacter.name

        Glide.with(requireView().context).load(args.singleCharacter.image)
            .into(binding.currentAvatar)

        binding.currentCreated.text = args.singleCharacter.origin.name
        binding.currentGender.text = args.singleCharacter.gender
        binding.currentLocation.text = args.singleCharacter.location?.name

        val episodeList = StringBuilder()
        args.singleCharacter.episode.map { episode ->
            Log.d(TAG, "onViewCreated: $episode")
            val lastIndex = episode.lastIndexOf('/')
            val temp = episode.removeRange(0, lastIndex + 1)
            if (episodeList.isBlank()) episodeList.append(temp) else episodeList.append(", $temp")
        }
        binding.currentEpisodes.text = episodeList
        Log.d(TAG, "onViewCreated: $episodeList")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}