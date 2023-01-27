package com.example.networkhandle

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.networkhandle.base.DataBindingFragment
import com.example.networkhandle.databinding.FragmentViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewPagerFragment :
    DataBindingFragment<FragmentViewPagerBinding>(R.layout.fragment_view_pager) {
    val adapterIdx = MutableLiveData<String>()
    lateinit var idxAdapter :ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.vpfVpSquare.apply {
            adapter = ViewPagerAdapter(
                arrayListOf(
                    R.drawable.bn_cat1,
                    R.drawable.bn_cat1,
                    R.drawable.bn_cat1,
                    R.drawable.bn_cat1
                ),
                2
            )
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 1
            // setPageTransformer(ZoomOutPageTransformer()) // 에니메이션
            setPageTransformer(MarginTransformer())// 마진
        }

        binding.vpfVpRect.apply {
            idxAdapter = ViewPagerAdapter(
                arrayListOf(
                    R.drawable.bn_cat4,
                    R.drawable.bn_cat4,
                    R.drawable.bn_cat4,
                    R.drawable.bn_cat4
                ),
                1
            )
            adapter = idxAdapter

            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            //setPageTransformer(ZoomOutPageTransformer()) // 에니메이션
            registerOnPageChangeCallback(scrollListener)


        }

    }

    fun idxUpdate(idx:String) {
        adapterIdx.postValue(idx)
    }
    val scrollListener = object : OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            if (positionOffsetPixels == 0) {
                val size =  idxAdapter.item.size
                idxUpdate("${(position % size)+1} / ${size} 모두보기")
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            //mIndicator.animatePageSelected(position % num_page)
        }
    }
    inner class ZoomOutPageTransformer : ViewPager2.PageTransformer {
        private val MIN_SCALE = 0.85f // 뷰가 몇퍼센트로 줄어들 것인지
        private val MIN_ALPHA = 0.5f // 어두워지는 정도를 나타낸 듯 하다.
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 1 -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        // Fade the page relative to its size.
                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }

    inner class MarginTransformer : ViewPager2.PageTransformer {
        val pageMarginPx =
            resources.getDimensionPixelOffset(R.dimen.pageMargin) // dimen 파일 안에 크기를 정의해두었다.
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth) // dimen 파일이 없으면 생성해야함
        val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 너비 길이를 가져옴
        val offsetPx = screenWidth - pageMarginPx - pagerWidth
        override fun transformPage(page: View, position: Float) {
            page.translationX = position * -offsetPx
        }
    }
}