package com.nuhlp.recyclerviewwithindex.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.nuhlp.recyclerviewwithindex.R
import com.nuhlp.recyclerviewwithindex.adapter.ItemListAdapter
import com.nuhlp.recyclerviewwithindex.base.BaseViewBindingFragment
import com.nuhlp.recyclerviewwithindex.databinding.FragmentHomeBinding


class HomeFragment :BaseViewBindingFragment<FragmentHomeBinding>()  {
    private val viewModel: HomeViewModel by activityViewModels { HomeViewModelFactory() }
    lateinit var _layoutManager: LinearLayoutManager
    val positionRawXY = MutableLiveData<List<Float>>()
    var positionXY = MutableLiveData<List<Float>>()
    var checkText = MutableLiveData<String>()


    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
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

        _layoutManager.scrollToPositionWithOffset(15,0)

        positionRawXY.observe(viewLifecycleOwner){
          binding.positionXYText.text = "rawX: ${it[0]} rawY: ${it[1]} \n ${it[2]} ${it[3]}"
            binding.eventText.x = it[0] - it[2]
            binding.eventText.y = it[1]  - 150
        }
        checkText.observe(viewLifecycleOwner){
            binding.checkText.text = checkText.value
        }

        setListener()

    }
    @SuppressLint("ClickableViewAccessibility")
    fun setListener()= binding.apply {

        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getHorizontalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        btn1.setOnClickListener {
            // todo 어답터 위치 이동부터
            smoothScroller.targetPosition = 0
            _layoutManager.startSmoothScroll(smoothScroller)
        }
        btn2.setOnClickListener {
            smoothScroller.targetPosition = 15
            _layoutManager.startSmoothScroll(smoothScroller)
        }
        btn3.setOnClickListener {
            smoothScroller.targetPosition = 30
            _layoutManager.startSmoothScroll(smoothScroller)
        }

        btn1IndexSize.setOnClickListener {
            mutableIndexHorizontal.setElements(createIndexNum(10))
        }
        btn2IndexSize.setOnClickListener {
            mutableIndexHorizontal.setElements(createIndexNum(20))
        }
        btn3IndexSize.setOnClickListener {
            mutableIndexHorizontal.setElements(createIndexNum(30))
        }

        eventText.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_MOVE -> {
                    positionRawXY.value = listOf(event.rawX,event.rawY,positionXY.value!![0],positionXY.value!![1])
                }
                MotionEvent.ACTION_UP ->{ }
                MotionEvent.ACTION_DOWN ->{ positionXY.value = listOf(event.x,event.y) }
            }
            //리턴값은 return 없이 아래와 같이
            true
        }

      /*  mutableIndexHorizontal.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_MOVE ->{
                       this@HomeFragment.checkText.value = "device : ${event.device}  \n  " +
                               "classification : ${event.classification}  \n"
                }
            }
            true
        }
        */

    }
    fun createIndexNum(size:Int):String{
        var str = ""
        for (i  in 1..size )
            str += "$i "
        return str
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?, )
    : FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    fun showToast(str:String) = Toast.makeText(activity,str,Toast.LENGTH_SHORT).show()
}