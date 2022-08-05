package com.nuhlp.restapi.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nuhlp.restapi.R
import com.nuhlp.restapi.network.kakao.KaKaoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
       viewModel.status.observe(viewLifecycleOwner){
           Log.d("MainFragment", "size : $it")
       }
        //todo 여기부터
        var str =""
        CoroutineScope(Dispatchers.IO).launch {
            str = "${KaKaoApi.retrofitService.getLocations("경기도 용인시 기흥구 상하동 660")}"
            Log.d("MainFragment", "size : $str")
        }




    }

}