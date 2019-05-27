package com.troy.playground.piccollage

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.troy.playground.R
import com.troy.playground.databinding.FragmentPiccollageBinding
import com.troy.playground.piccollage.viewmodel.PicCollageViewModel
import com.troy.playground.utility.Log
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class PicCollageFragment : DaggerFragment() {

    @field:[Inject Named("piccollage")]
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: FragmentPiccollageBinding? = null
    private var viewModel : PicCollageViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get<PicCollageViewModel>(PicCollageViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_piccollage, container, false)
        binding?.viewModel = viewModel
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvHelloWorld?.setText("Hi PicCollage")

        Log.d("5566 5 : " + getHead(5566) + "body 566 " + getBody(5566) + " , 6 " + getHead(764948) + " body 64948 " + getBody(764948))
        Log.d("5566 exp 0 : " + counting(5))
        Log.d("5566 exp 1 : " + counting(9))
        Log.d("5566 exp 5 : " + counting(19))
        Log.d("5566 exp 9 : " + convertTo9(19))
        Log.d("5566 exp 9999 : " + convertTo9(19225))
        Log.d("5566 exp 999 : " + convertTo9(1223))
        Log.d("5566 exp 999999 : " + convertTo9(1924959))


        Log.d("5566 ans exp 0 : " + counting( 5))
        Log.d("5566 ans exp 1 : " + counting( 7))
        Log.d("5566 ans exp 1 : " + counting( 10))
        Log.d("5566 ans exp 2 : " + counting( 17))
        Log.d("5566 ans exp 19 : " + counting( 100))

        Log.d("5566 ans exp 1 : " + counting2( 10))
        Log.d("5566 ans exp 2 : " + counting2( 17))
        Log.d("5566 ans exp 19 : " + counting2( 100))
        Log.d("5566 ans exp " + counting2( 1000) + " : " + counting( 1000))
        Log.d("5566 ans exp " + counting2( 2468) + " : " + counting( 2468))
        Log.d("5566 ans exp " + counting2( 999) + " : " + counting( 999))
        Log.d("5566 ans exp " + counting2( 77) + " : " + counting( 77))
        Log.d("5566 ans exp " + counting( 78877999) + " : " + counting( 78877999))
    }

    private fun counting( n : Int) : Int{
        if(n <= 9) {
            return if (n >= 7) { 1 } else { 0 }
        } else {
            val head = getHead(n) // head must >= 1
            val body = getBody(n)

            if (head < 7) {
                return (head) * counting(convertTo9(n)) + counting(body)
            } else if (head == 7) {
                return (head) * counting(convertTo9(n)) + body + 1
            } else {
                return (head - 1) * counting(convertTo9(n)) + counting(body) + convertTo9(n) + 1
            }
        }
    }

    private fun counting2 ( n : Int) : Int{
        var counter = 0
        for (i in 1..n) {
            if (has7(i)) {
                counter++
            }
        }

        return  counter
    }

    fun has7 (n : Int) : Boolean {
        return n.toString().contains("7")
    }

    private fun getHead(n : Int) : Int {
        return Character.getNumericValue(n.toString()[0])
    }

    private fun getBody(n : Int) : Int {
        return n.toString().subSequence(1, n.toString().length).toString().toInt()
    }

    //input a length n digit , out put a n-1 length 9...9 digit
    private fun convertTo9(n : Int) : Int {

        var num = 0

        for (i in 1 until(n.toString().length)) {
            num = num * 10 + 9
        }
        return num
    }
}