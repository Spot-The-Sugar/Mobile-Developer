package com.example.spotthesugar.ui.history

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotthesugar.data.ResultState
import com.example.spotthesugar.data.pref.UserSharedPreference
import com.example.spotthesugar.data.source.response.DataItem
import com.example.spotthesugar.databinding.ActivityHistoryBinding
import com.example.spotthesugar.factory.ViewModelFactory
import com.example.spotthesugar.ui.adapter.HistoryAdapter

class HistoryActivity : AppCompatActivity() {
    private val viewModel by viewModels<TrackViewModel> {
        ViewModelFactory.getInstance()
    }
    private lateinit var binding:ActivityHistoryBinding
    private val prefs: UserSharedPreference by lazy {
        val sharedPreferences =
            getSharedPreferences(UserSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
        UserSharedPreference(sharedPreferences)
    }
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        observeViewModel()

    }

    private fun setupAdapter(){
        historyAdapter = HistoryAdapter(emptyList())
        binding.histories.layoutManager = LinearLayoutManager(this)
        binding.histories.adapter =historyAdapter
    }

    private fun observeViewModel() {
        val token = prefs.fetchAccessToken()
        viewModel.getHistories("Bearer ${token.toString()}").observe(this@HistoryActivity){
                result ->
            when(result){
                is ResultState.Loading -> showLoading(true)
                is ResultState.Error ->{
                    showLoading(false)
                    showToast(result.error)
                }
                is ResultState.Success -> {
                    showLoading(false)
                    val histories =result.data?.filterNotNull() ?: emptyList()
                    historyAdapter.setData(histories)
                    logHistories(histories)
                }
            }
        }
    }

    private fun logHistories(histories: List<DataItem?>) {
        for (history in histories) {
            Log.d("HistoryItem", history.toString())
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}