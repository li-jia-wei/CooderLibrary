package com.cooder.cooder.app.ui.slider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.R
import com.cooder.cooder.library.util.CooderStatusBar

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/24 16:05
 *
 * 介绍：CooderSliderActivity
 */
class CooderSliderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooder_slider)
        CooderStatusBar.immersiveStatusBar(this, true)
    }
}