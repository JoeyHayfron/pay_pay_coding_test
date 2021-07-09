package com.example.paypay_coding_test.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paypay_coding_test.R
import com.example.paypay_coding_test.databinding.ActivityMainBinding
import com.example.paypay_coding_test.presentation.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: RecyclerAdapter
    val viewModel: MainActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = GridLayoutManager(this, 3)
        viewModel.currencyData.observe(this, {
            val arrayAdapter = ArrayAdapter(this, R.layout.list_item, it.keys.toTypedArray())
            (binding.currencies as? AutoCompleteTextView)?.setAdapter(arrayAdapter)
        })

        viewModel.currencyRateData.observe(this, {
            if (it.isNotEmpty()) {
                binding.noRatesTextView.visibility = View.GONE
                binding.ratesRecyclerView.visibility = View.VISIBLE
                recyclerViewAdapter = RecyclerAdapter( it ?: mapOf())
                binding.ratesRecyclerView.layoutManager = layoutManager
                binding.ratesRecyclerView.adapter = recyclerViewAdapter
            }else{
                binding.noRatesTextView.visibility = View.VISIBLE
                binding.ratesRecyclerView.visibility = View.GONE
            }
        })

        binding.currenciesLayout.editText?.doOnTextChanged { text, _, _, _ ->
            var amountToChange = 1.0
            if(binding.amountToConvert.editText?.text.toString().isNotEmpty())
                amountToChange = binding.amountToConvert.editText?.text.toString().toDouble()
            viewModel.updateCurrencySource(text.toString(), amountToChange)
        }

        binding.amountToConvert.editText?.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty())
                viewModel.convertAmount(text.toString().toDouble())
            else
                viewModel.convertAmount(null)
        }
    }
}