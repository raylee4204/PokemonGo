package city.samaritan.pokemongo.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import city.samaritan.pokemongo.R
import city.samaritan.pokemongo.databinding.FragmentExploreBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class ExploreFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentExploreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ExploreFragment()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        val toronto = LatLng(43.651070, -79.347015)
        googleMap.addMarker(
            MarkerOptions()
                .position(toronto)
                .title("Toronto")
        )
        googleMap.setMinZoomPreference(10f)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(toronto))
    }

}