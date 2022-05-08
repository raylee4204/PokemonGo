package city.samaritan.pokemongo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import city.samaritan.pokemongo.R
import city.samaritan.pokemongo.databinding.FragmentMainBinding
import city.samaritan.pokemongo.ui.captures.CapturedPokemonsFragment
import city.samaritan.pokemongo.ui.explore.ExploreFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private lateinit var viewPagerAdapter: MainViewPagerAdapter
    private val binding get() = _binding!!
    private val pageNames by lazy {
        resources.getStringArray(R.array.tab_names)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerAdapter = MainViewPagerAdapter(this)
        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = pageNames[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class MainViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = ITEM_COUNT

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) {
                ExploreFragment.newInstance()
            } else {
                CapturedPokemonsFragment.newInstance()
            }
        }
    }

    companion object {
        private const val ITEM_COUNT = 2

        fun newInstance() = MainFragment()
    }
}