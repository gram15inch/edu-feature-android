package com.nuhlp.recyclerviewwithindex.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.nuhlp.recyclerviewwithindex.adapter.ItemListAdapter
import com.nuhlp.recyclerviewwithindex.base.BaseViewBindingFragment
import com.nuhlp.recyclerviewwithindex.databinding.FragmentHomeBinding


class HomeFragment :BaseViewBindingFragment<FragmentHomeBinding>()  {
    private val viewModel: HomeViewModel by activityViewModels { HomeViewModelFactory() }
    lateinit var _layoutManager: LinearLayoutManager
    val positionXY = MutableLiveData<List<Float>>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ItemListAdapter {}
        viewModel.allDocs.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        viewModel.updateDocs(30)
        binding.recyclerView.adapter = adapter
        _layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView.layoutManager =_layoutManager

        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getHorizontalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        _layoutManager.scrollToPositionWithOffset(15,0)
        positionXY.observe(viewLifecycleOwner){
            binding.positionXYText.text = it[0].toString() + it[1].toString()
        }
/*
        binding.indexHorizontal.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_MOVE -> {
                    positionXY.value = listOf(event.rawX,event.rawY)
                }
            }
            //리턴값은 return 없이 아래와 같이
            true
        }*/
        binding.btn1.setOnClickListener {
           // todo 어답터 위치 이동부터
            smoothScroller.targetPosition = 0
            _layoutManager.startSmoothScroll(smoothScroller)
        }
        binding.btn2.setOnClickListener {
            smoothScroller.targetPosition = 15
            _layoutManager.startSmoothScroll(smoothScroller)
        }
        binding.btn3.setOnClickListener {
            smoothScroller.targetPosition = 30
            _layoutManager.startSmoothScroll(smoothScroller)
        }


    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    fun showToast(str:String) = Toast.makeText(activity,str,Toast.LENGTH_SHORT).show()
}