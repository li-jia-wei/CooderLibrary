package com.cooder.library.app.ui.refresh

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cooder.library.app.databinding.ActivityCoRefreshBinding
import com.cooder.library.library.util.expends.setStatusBar
import com.cooder.library.ui.refresh.CoRefresh
import com.cooder.library.ui.refresh.overview.CoTextOverView

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/13 16:57
 *
 * 介绍：CooderRefreshActivity
 */
class CoRefreshActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoRefreshBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoRefreshBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setStatusBar(true, Color.WHITE)
		
		binding.refreshLayout.setRefreshOverView(CoTextOverView(this))
		binding.refreshLayout.setRefreshListener(object : CoRefresh.CoRefreshListener {
			override fun onRefresh() {
				Toast.makeText(this@CoRefreshActivity, "刷新事件", Toast.LENGTH_SHORT).show()
				Handler(Looper.myLooper()!!).postDelayed({
					binding.refreshLayout.refreshFinished()
				}, 1500L)
			}
		})
	}
}