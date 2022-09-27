package com.example.calculator

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var resultadoAcumulado : MutableLiveData<Float> = MutableLiveData()
    private var resultadoAcumuladoHelp: Float = 0f
    var lastValue : MutableLiveData<String> = MutableLiveData()
    private var lastOperation: Char? = null

    init {
        reset()
    }

    fun reset(){
        resultadoAcumulado.value = 0f
        resultadoAcumuladoHelp = 0f
        lastValue.value = "0"
        lastOperation= null
    }

    fun setChar(number: Char){

        if(lastValue.value.equals("0") && isNumber(number)){
            lastValue.value = ""
        }

        if(isNumber(number)){
            lastValue.value += number
        }

        if(lastOperation != null) {
            when (lastOperation) {
                '+' -> add()
                '-' -> subtract()
                '*' -> multiply()
                '/' -> split()
            }
            return
        }
        add()
    }

    private fun isNumber(num: Char): Boolean{
        var valReturn: Boolean
        try{
            num.digitToInt()
            valReturn = true
        }catch(e: Exception){
            valReturn = false
        }
        return valReturn
    }

    private fun add(){
        resultadoAcumulado.value = resultadoAcumuladoHelp + lastValue.value!!.toFloat()
    }

    private fun subtract(){
        resultadoAcumulado.value = resultadoAcumuladoHelp - lastValue.value!!.toFloat()
    }

    private fun multiply(){
        resultadoAcumulado.value = resultadoAcumuladoHelp * lastValue.value!!.toFloat()
    }

    private fun split(){
        if(lastValue.value!!.toFloat() == 0f){
            return
        }
        resultadoAcumulado.value = resultadoAcumuladoHelp / lastValue.value!!.toFloat()
    }

    fun initValues(operation: Char){
        resultadoAcumuladoHelp = resultadoAcumulado.value!!
        lastValue.value = "0"
        lastOperation = operation
    }

    fun deleteLastDigit(){
        lastValue.value = lastValue.value!!.substring(0, lastValue.value!!.length-1)
        setChar('d')
    }
}