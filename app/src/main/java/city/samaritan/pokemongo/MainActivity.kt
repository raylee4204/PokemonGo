package city.samaritan.pokemongo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import city.samaritan.pokemongo.ui.main.ExploreFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ExploreFragment.newInstance())
                .commitNow()
        }
    }
}