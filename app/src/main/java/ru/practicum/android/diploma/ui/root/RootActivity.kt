package ru.practicum.android.diploma.ui.root

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.isVisible = isBottomNavigationVisible(destination)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    private fun isBottomNavigationVisible(destination: NavDestination): Boolean {
        return destination.id != R.id.filterFragment
            && destination.id != R.id.vacancyFragment
    }
}
