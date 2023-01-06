package com.learning.myrecyclerview

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.learning.myrecyclerview.databinding.ActivityMainBinding
import com.learning.myrecyclerview.presentation.UiUserListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@BindingAdapter("bindStatus")
fun bindStatus(statusImageView: ImageView,
               status: UiStatus) {
    Timber.tag("timber").d("${status.name}")
    when (status) {
        UiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        UiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        UiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
@BindingAdapter("listData")
fun listData(view: RecyclerView,
               list: List<UiUser>?) {
    val adapter = view.adapter as UiUserListAdapter
    adapter.submitList(list)
}
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rcyUsers.adapter = UiUserListAdapter()
    }

}