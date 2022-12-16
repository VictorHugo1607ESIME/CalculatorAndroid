package com.example.calculator

import android.nfc.Tag
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var resultadoAcumulado : MutableLiveData<Float> = MutableLiveData()
    var lastValue : MutableLiveData<String> = MutableLiveData()
    var lastOperation : MutableLiveData<Char> = MutableLiveData()
    private var resultadoAcumuladoHelp: Float = 0f

    init {
        reset()
    }

    fun reset(){
        resultadoAcumulado.value = 0f
        resultadoAcumuladoHelp = 0f
        lastValue.value = "0"
        lastOperation.value = ' '
    }

    fun setChar(number: Char){
        if(lastValue.value.equals("0") && isNumber(number)){
            lastValue.value = ""
        }

        if(isNumber(number) || number == '.'){
            lastValue.value += number
        }

        if(lastOperation.value != null) {
            when (lastOperation.value) {
                ' ' -> add()
                '+' -> add()
                '-' -> subtract()
                '*' -> multiply()
                '/' -> split()
                '%' -> module()
            }
        }
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
        if(lastValue.value!!.toFloat() == 0f)
            return
        resultadoAcumulado.value = resultadoAcumuladoHelp / lastValue.value!!.toFloat()
    }

    private fun module() {
        resultadoAcumulado.value = resultadoAcumuladoHelp % lastValue.value!!.toFloat()
    }

    fun initValues(operation: Char){
        resultadoAcumuladoHelp = resultadoAcumulado.value!!
        lastValue.value = "0"
        lastOperation.value = operation
    }

    fun deleteLastDigit(){
        if(lastValue.value!!.count() > 1)
            lastValue.value = lastValue.value!!.substring(0, lastValue.value!!.length-1)
        else  {
            resultadoAcumulado.value = 0f
            lastValue.value = "0"
        }
        setChar('d')
    }
}

