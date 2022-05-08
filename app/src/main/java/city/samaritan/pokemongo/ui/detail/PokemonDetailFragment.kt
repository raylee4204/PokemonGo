package city.samaritan.pokemongo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import city.samaritan.pokemongo.R
import city.samaritan.pokemongo.databinding.FragmentPokemonDetailBinding
import city.samaritan.pokemongo.dpToPx
import city.samaritan.pokemongo.loadImage
import city.samaritan.pokemongo.model.Pokemon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private val args: PokemonDetailFragmentArgs by navArgs()
    private val viewModel: PokemonDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pokemon.observe(viewLifecycleOwner) {
            setData(it)
        }
        viewModel.getPokemonById(args.pokemonId)
    }

    private fun setData(pokemon: Pokemon) {
        binding.imgPokemon.loadImage(
            pokemon.defaultFrontImage,
            resources.getDimensionPixelSize(R.dimen.pokemon_large_size)
        )
        binding.txtPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
        var topId = binding.txtTitleBasicInfo.id
        val marginTop = 8.dpToPx(requireContext())
        for (stat in pokemon.stats) {
            val textView = TextView(requireContext()).apply { this.id = View.generateViewId() }
            textView.text = "${stat.name}:  ${stat.base}"
            binding.lytBasicInfo.addView(textView)
            val constraintSet = ConstraintSet().apply { clone(binding.lytBasicInfo) }
            constraintSet.setMargin(textView.id, ConstraintSet.TOP, marginTop)
            constraintSet.connect(textView.id, ConstraintSet.TOP, topId, ConstraintSet.BOTTOM)
            constraintSet.connect(
                textView.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            constraintSet.connect(
                textView.id,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            constraintSet.setHorizontalBias(textView.id, 0f)
            constraintSet.applyTo(binding.lytBasicInfo)
            topId = textView.id
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}