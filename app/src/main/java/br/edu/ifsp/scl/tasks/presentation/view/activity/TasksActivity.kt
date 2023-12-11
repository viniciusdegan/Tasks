package br.edu.ifsp.scl.tasks.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.edu.ifsp.scl.tasks.R
import br.edu.ifsp.scl.tasks.databinding.ActivityTasksBinding

class TasksActivity : AppCompatActivity() {
    private val binding: ActivityTasksBinding by lazy {
        ActivityTasksBinding.inflate(layoutInflater)
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarTasks)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerTasks) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerTasks)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}