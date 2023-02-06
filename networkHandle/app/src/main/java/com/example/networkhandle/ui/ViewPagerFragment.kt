package com.example.networkhandle.ui

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.networkhandle.R
import com.example.networkhandle.ViewPagerAdapter
import com.example.networkhandle.base.DataBindingFragment
import com.example.networkhandle.databinding.FragmentViewPagerBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import timber.log.Timber


@AndroidEntryPoint
class ViewPagerFragment :
    DataBindingFragment<FragmentViewPagerBinding>(R.layout.fragment_view_pager) {
    val indicator = MutableLiveData<String>()
    var adapterPos = 0
    lateinit var job: Job
    lateinit var idxAdapter: ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return super.onCreateView(inflater, container, savedInstanceState)
    }

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

        binding.vpfVpBanner.apply {
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

    override fun onResume() {
        super.onResume()
        scrollJobCreate()
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

    fun indicatorUpdate(idx: String) {
        indicator.postValue(idx)
    }

    fun scrollJobCreate() {
        job = lifecycleScope.launchWhenResumed {
            while (true) {
                delay(3000)
                binding.vpfVpBanner.setCurrentItemWithDuration(++adapterPos, 2000)
            }
        }
    }

    fun ViewPager2.setCurrentItemWithDuration(
        item: Int, duration: Long,
        interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
        pagePxWidth: Int = width // ViewPager2 View 의 getWidth()에서 가져온 기본값
    ) {
        val pxToDrag: Int = pagePxWidth * (item - currentItem)
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0

        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            val currentPxToDrag = (currentValue - previousValue).toFloat()
            fakeDragBy(-currentPxToDrag)
            previousValue = currentValue
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                beginFakeDrag()
            }

            override fun onAnimationEnd(animation: Animator) {
                endFakeDrag()
            }

            override fun onAnimationCancel(animation: Animator) { /* Ignored */
            }

            override fun onAnimationRepeat(animation: Animator) { /* Ignored */
            }
        })

        animator.interpolator = interpolator
        animator.duration = duration
        animator.start()
    }

    val scrollListener = object : OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            if (positionOffsetPixels == 0) {
                val size = idxAdapter.item.size
                indicatorUpdate("${(position % size) + 1} / ${size} 모두보기")
                adapterPos = position
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