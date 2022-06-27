package com.nuhlp.recyclerviewwithindex.ui.learning

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuhlp.recyclerviewwithindex.R
import com.nuhlp.recyclerviewwithindex.adapter.ItemListAdapter
import com.nuhlp.recyclerviewwithindex.base.BaseViewBindingFragment
import com.nuhlp.recyclerviewwithindex.data.doc
import com.nuhlp.recyclerviewwithindex.databinding.FragmentCustomViewPracBinding



class CustomViewPracFragment :BaseViewBindingFragment<FragmentCustomViewPracBinding>() {

    var x= 1f
    var y= 100f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myCustomView.updateItem(createItem((tmpN++)%3))
        binding.myCustomView2.updateItem(createItem((tmpN++)%3))
        setListener()
        setObserver()
        setComponent()
        setData()
    }

    private fun setData() {

    }

    var tmpN = 0

   private fun setListener()=binding.apply {
        rect1.setOnClickListener {
            myCustomView.updateItem(createItem ((tmpN++)%3))
            myCustomView2.updateItem(createItem ((tmpN++)%3))
        }
   }
    private fun setObserver()=binding.apply {
        myCustomView.unit.observe(viewLifecycleOwner){
            positionText.text = it.toString()
        }

    }
    private fun setComponent()=binding.apply {
        val adapter = ItemListAdapter{}
        adapter.submitList(createDoc(1))
        myRcycler.adapter = adapter
        myRcycler.layoutManager =LinearLayoutManager(this@CustomViewPracFragment.context, LinearLayoutManager.HORIZONTAL,false)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCustomViewPracBinding {
       return FragmentCustomViewPracBinding.inflate(inflater,container,false)
    }

    fun createItem(i :Int):List<Int>{
        val list = mutableListOf<Int>()
        when(i){
            1->{
                for(i in 1..30)
                    if(i%2 == 0)
                        list.add(i)
            }
            2->{
                for(i in 1..30)
                    if(i%3 == 0)
                        list.add(i)
            }
            else->{
                for(i in 1..30)
                    list.add(i)
            }

        }
        return list
    }
    fun createDoc(i:Int):List<doc>{
        val list = mutableListOf<doc>()
        when(i){
            1->{
                repeat(10){
                    list.add(doc("title$it","contents$it"))
                }
            }
                else->{list}
        }

        return list
    }
}