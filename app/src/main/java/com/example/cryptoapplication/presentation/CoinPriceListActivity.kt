package com.example.cryptoapplication.presentation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapplication.R
import com.example.cryptoapplication.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapplication.domain.CoinInfo
import com.example.cryptoapplication.presentation.adapters.CoinInfoAdapter


class CoinPriceListActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                if (isOnePaneMode()) {
                    launchCoinDetailActivity(coinPriceInfo.fromSymbol)
                } else launchCoinDetailFragment(coinPriceInfo.fromSymbol)
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }

    }

    private fun isOnePaneMode() = binding.fragmentContainer == null
    private fun launchCoinDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(this@CoinPriceListActivity, fromSymbol)
        startActivity(intent)
    }

    private fun launchCoinDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

}

