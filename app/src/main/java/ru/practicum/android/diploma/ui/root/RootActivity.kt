package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding
import ru.practicum.android.diploma.ui.main.MainFragment


class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.menu.findItem(R.id.mainFragment).isChecked = true
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.isVisible = isBottomNavigationVisible(destination)
        }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    private fun isBottomNavigationVisible(destination: NavDestination): Boolean {
        return ((destination.id != R.id.filterFragment)
            && (destination.id != R.id.vacancyFragment))
    }
}
