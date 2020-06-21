package com.yppcat.common.view.banner

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.contains
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.yppcat.common.R
import com.yppcat.common.extends.dp2px
import com.yppcat.common.view.VpImagesAdapter

class Banner : FrameLayout {

    companion object {
        const val INDICATOR_DEFAUTL_GRAVITY = 1
        const val DOT_LIGHT_DEFAULT_COLOR = 0xFFD81B60.toInt()
        const val DOT_DARK_DEFAULT_COLOR = 0xFF000000.toInt()
        const val INDICATOR_BACKGROUND_DEFAULT_COLOR = 0x77ffffff
        private const val TAG = "Banner"
    }

    //指示器布局位置
    private var mIndicatorGravity = INDICATOR_DEFAUTL_GRAVITY

    //是否显示指示器
    private var mShowIndicator = true

    //指示器亮点颜色
    private var mDotLightColor = DOT_LIGHT_DEFAULT_COLOR

    //指示器暗点颜色
    private var mDotDarkColor = DOT_DARK_DEFAULT_COLOR

    //指示器背景颜色
    private var mIndicatorBackgroundColor = INDICATOR_BACKGROUND_DEFAULT_COLOR

    //轮播间隔时间
    private var mDuration = 3000L

    private lateinit var mAdapter: VpImagesAdapter
    private var mImgViews: ArrayList<ImageView> = arrayListOf()
    private val mViewPager = ViewPager(context)
    private var mImgs: ArrayList<Any> = arrayListOf()
    private var mInitialImgSize = 0 //列表数量
    private var mListener: onPageClickListener? = null
    private val mFrameIndicators = FrameLayout(context)
    private val mLlIndicators = LinearLayout(context)
    private val mRedIndicator = ImageView(context)
    private var mHandler = Handler(Handler.Callback { msg ->
        mViewPager.currentItem = msg.what
        true
    })
    private val mDotDarkDrawable by lazy {
        val drawable = GradientDrawable()
        drawable.apply {
            shape = GradientDrawable.OVAL
            setSize(dp2px(context, 3), dp2px(context, 3))
            setColor(mDotDarkColor)
        }
        drawable
    }

    private val mDotLightDrawable by lazy {
        val drawable = GradientDrawable()
        drawable.apply {
            shape = GradientDrawable.OVAL
            setSize(dp2px(context, 3), dp2px(context, 3))
            setColor(mDotLightColor)
        }
        drawable
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.Banner)
        mShowIndicator = ta.getBoolean(R.styleable.Banner_showIndicator, true)
        mDotLightColor = ta.getColor(R.styleable.Banner_dotLightColor, DOT_LIGHT_DEFAULT_COLOR)
        mDotDarkColor = ta.getColor(R.styleable.Banner_dotDarkColor, DOT_DARK_DEFAULT_COLOR)
        mIndicatorGravity =
            ta.getInt(R.styleable.Banner_indicator_gravity, INDICATOR_DEFAUTL_GRAVITY)
        mIndicatorBackgroundColor =
            ta.getColor(
                R.styleable.Banner_indicatorBackgroundColor,
                INDICATOR_BACKGROUND_DEFAULT_COLOR
            )
        clipChildren = false
        mViewPager.clipChildren = false
        addView(mViewPager)
        ta.recycle()
    }

    fun setDataFromUrl(urls: ArrayList<String>): Banner {
        mImgs.clear()
        mImgs.addAll(urls)
        mImgViews.clear()
        return setData()
    }

    private fun setData(): Banner {
        Log.d(TAG, "setData: " + mImgs.size)
        mImgs.apply {
            mInitialImgSize = size
            add(0, last())
            add(mImgs[1])
            var img: ImageView
            val load = Glide.with(context).load("")
            forEachIndexed { index, data ->
                img = ImageView(context)
                img.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                img.scaleType = ImageView.ScaleType.FIT_XY
                load.load(data).into(img)
                if (index in 1 until lastIndex) {
                    img.setOnClickListener { mListener?.onPageClick(index) }
                }
                mImgViews.add(img)
            }
        }
        setData2Vp()
        if (mShowIndicator) {
            addIndicators()
        }
        return this
    }

    private fun setData2Vp() {
        mAdapter = VpImagesAdapter(mImgViews)
        mViewPager.apply {
            adapter = mAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            setVPScrollSpeed()
            offscreenPageLimit = mImgViews.size
            currentItem = 1
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                val lastIndex = mImgViews.lastIndex
                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        mHandler.removeCallbacksAndMessages(null)
                    } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                        when (mViewPager.currentItem) {
                            0 -> mViewPager.setCurrentItem(lastIndex - 1, false)
                            lastIndex -> mViewPager.setCurrentItem(1, false)
                        }
                    }
                    mHandler.sendEmptyMessageDelayed(mViewPager.currentItem + 1, mDuration - 1000)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    if (positionOffset != 0.toFloat()) {
                        if (position > 0 && position < lastIndex - 1)
                            mRedIndicator.translationX =
                                (positionOffset * mRedIndicator.measuredWidth) + ((position - 1) * mRedIndicator.measuredWidth)
                        else if (position == lastIndex - 1 || position == 0)
                            mRedIndicator.translationX =
                                ((1 - positionOffset) * (mFrameIndicators.measuredWidth - mLlIndicators.getChildAt(
                                    0
                                ).measuredWidth))
                    }
                }

                override fun onPageSelected(position: Int) {

                }

            })
        }
    }

    private fun setVPScrollSpeed(duration: Long = 1000) {
        val field = ViewPager::class.java.getDeclaredField("mScroller")
        field.isAccessible = true
        val scroller = MyScroller(context)
        scroller.scrollDuration = duration
        field.set(mViewPager, scroller)
    }

    private fun addIndicators() {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        Log.d(TAG, "addIndicators: " +mIndicatorGravity)
        params.apply {
            val margin = dp2px(context, 10)
            when (mIndicatorGravity) {
                0 -> {
                    gravity = Gravity.BOTTOM or Gravity.START
                    marginStart = margin
                }
                1 -> gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                2 -> {
                    gravity = Gravity.BOTTOM or Gravity.END
                    marginEnd = margin
                }
            }
            bottomMargin = margin
        }
        mFrameIndicators.layoutParams = params
        val bgDrawable = GradientDrawable()
        bgDrawable.apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 30F
            setColor(mIndicatorBackgroundColor)
        }
        mFrameIndicators.background = bgDrawable
        //添加指示器
        this.removeView(mFrameIndicators)
        addView(mFrameIndicators)
        val padding = dp2px(context, 8)
        mLlIndicators.layoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mLlIndicators.orientation = LinearLayout.HORIZONTAL
        var indicator: ImageView
        for (i in 0 until mInitialImgSize) {
            indicator = ImageView(context)
            indicator.setImageDrawable(mDotDarkDrawable)
            indicator.setPadding(padding, padding, padding, padding)
            indicator.layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            mLlIndicators.removeView(indicator)
            mLlIndicators.addView(indicator)
        }
        mFrameIndicators.removeView(mLlIndicators)
        mFrameIndicators.addView(mLlIndicators)
        mRedIndicator.setImageDrawable(mDotLightDrawable)
        mRedIndicator.setPadding(padding, padding, padding, padding)
        mRedIndicator.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        mFrameIndicators.removeView(mRedIndicator)
        mFrameIndicators.addView(mRedIndicator)
    }

    fun startLoop() {
        mHandler.sendEmptyMessageDelayed(mViewPager.currentItem + 1, mDuration)
    }

    fun setOnPageClickListener(listener: onPageClickListener) {
        mListener = listener
    }

    interface onPageClickListener {
        fun onPageClick(index: Int)
    }


}