package city.samaritan.pokemongo.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import city.samaritan.pokemongo.R
import city.samaritan.pokemongo.databinding.FragmentExploreBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var pokeBallMarker: BitmapDescriptor
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExploreViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragment.getMapAsync(this)
        viewModel.randomPokemonLocations.observe(viewLifecycleOwner) { locations ->
            for (location in locations) {
                val addedMarker = googleMap.addMarker(
                    MarkerOptions()
                        .position(location.coordinates)
                        .icon(pokeBallMarker)

                ) ?: return@observe
                addedMarker.tag = location.pokemon.id
            }
        }

        val icon =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_pokeball)!!.toBitmap()
        pokeBallMarker = BitmapDescriptorFactory.fromBitmap(icon)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setOnMarkerClickListener { marker ->
            findNavController().navigate(
                R.id.action_pokemon_detail,
                Bundle().also { it.putInt("pokemonId", marker.tag as Int) })
            return@setOnMarkerClickListener true
        }
        googleMap.setOnCameraIdleListener {
            googleMap.clear()
            val bounds = googleMap.projection.visibleRegion.latLngBounds
            viewModel.getRandomLocations(bounds)
        }
        val toronto = LatLng(43.6710603, -79.3758142)
        googleMap.setMinZoomPreference(14f)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(toronto))
    }

    companion object {
        fun newInstance() = ExploreFragment()
    }
}