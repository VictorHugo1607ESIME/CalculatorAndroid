package com.example.calculator

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var binding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        setContentView(binding!!.root)
    }

    private fun initViewModel() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding!!.mainViewModel = viewModel

        viewModel.lastValue.observe(this, Observer{
                result -> binding!!.display.text = result.toString()
        })

        viewModel.resultadoAcumulado.observe(this, Observer{
                accumulatedResult -> binding!!.resulFinal.text = accumulatedResult.toString()
        })

        viewModel.lastOperation.observe(this, Observer{
            result -> binding!!.presentOperator.text = result.toString()
        })
    }
}