package com.nuhlp.googlemapapi.util

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.googlemapapi.R
import com.nuhlp.googlemapapi.network.model.place.Document
import com.nuhlp.googlemapapi.ui.maps.MapsActivity


@BindingAdapter("liveListDocument","lifecycle","activity")
fun bindDocument(view: TextView, live:LiveData<List<Document>>,lifecycleOwner: LifecycleOwner,activity:MapsActivity ) {
        live.observe(lifecycleOwner){ list ->
                list.forEach {
                     activity.setMarker(LatLng(it.y.toDouble(),it.x.toDouble()))
                 }
                Log.d("BindingAdapter","size: "+list.size.toString())
        }
}
@BindingAdapter("liveLatLng","lifecycle")
fun bindLatlng(view: TextView, live:LiveData<LatLng>,lifecycleOwner: LifecycleOwner ) {
        live.observe(lifecycleOwner){
                view.text = it.toString()
        }
}

@BindingAdapter("liveListDocument","lifecycle","activity")
fun bindPlaces(view : FragmentContainerView,live:LiveData<List<Document>>,lifecycleOwner: LifecycleOwner,activity:MapsActivity ) {
     /*   view.getFragment<SupportMapFragment>().getMapAsync {map->
                live.observe(lifecycleOwner){ list ->
                        list.forEach {
                                activity.setMarker(LatLng(it.y.toDouble(),it.x.toDouble()),map)
                        }
                        Log.d("BindingAdapter","size: "+list.size.toString())
                }
        }*/
}


/*@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri){
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }

    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<MarsPhoto>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView,
               status: MarsApiStatus?) {
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}*/
