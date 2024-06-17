package com.example.spotthesugar.ui.history

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.spotthesugar.R
import com.example.spotthesugar.data.ResultState
import com.example.spotthesugar.data.pref.UserSharedPreference
import com.example.spotthesugar.data.source.response.DetailDataItem
import com.example.spotthesugar.data.source.response.HistoryDetailResponse
import com.example.spotthesugar.databinding.ActivityHistoryDetailBinding
import com.example.spotthesugar.factory.ViewModelFactory

class HistoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryDetailBinding
    private val viewModel by viewModels<TrackViewModel> {
        ViewModelFactory.getInstance()
    }
    private val prefs: UserSharedPreference by lazy {
        val sharedPreferences =
            getSharedPreferences(UserSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
        UserSharedPreference(sharedPreferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historyId = intent.getIntExtra(EXTRA_HISTORY_ID,-1)

        val token = prefs.fetchAccessToken()
        historyId.takeIf { it != -1 }?.let { id ->
            viewModel.getDetailHistory("Bearer ${token.toString()}", id).observe(this) { result ->
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }
                    is ResultState.Success -> {
                        showLoading(false)
                       val historyDetailResponse = result.data
                        displayHistoryDetail(historyDetailResponse)
                    }
                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(result.error)
                    }
                }
            }
        }
    }

    private fun displayHistoryDetail(historyDetailResponse: HistoryDetailResponse){
        val detailDataItem = historyDetailResponse.data?.get(0)

        detailDataItem?.let {
            binding.apply {
                detailProductName.text = it.productName
                detailTotalSugarValue.text = it.totalSugar.toString()
                detailServingSugarValue.text = "${it.servingSugar}g"
                Glide.with(this@HistoryDetailActivity)
                    .load(it.imageUrl)
                    .into(detailProductImage)
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_HISTORY_ID = "extra_history_id"
    }
}
