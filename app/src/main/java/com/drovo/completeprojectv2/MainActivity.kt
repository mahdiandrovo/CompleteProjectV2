package com.drovo.completeprojectv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.drovo.completeprojectv2.adapter.DogAdapter
import com.drovo.completeprojectv2.adapter.LoadingStateAdapter
import com.drovo.completeprojectv2.databinding.ActivityMainBinding
import com.drovo.completeprojectv2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var dogAdapter: DogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        lifecycleScope.launchWhenStarted {
            mainViewModel.getAllDogs().collectLatest { response->
                binding.apply {
                    recyclerview.isVisible = true
                    progressBar.isVisible = false
                }
                dogAdapter.submitData(response)
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = dogAdapter.withLoadStateHeaderAndFooter(
                    header = LoadingStateAdapter{dogAdapter.retry()},
                    footer = LoadingStateAdapter{dogAdapter.retry()}
                )
            }
        }
    }
}