package com.cooder.cooder.app.ui.refresh

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.R
import com.cooder.cooder.library.util.setStatusBar
import com.cooder.cooder.ui.refresh.CoRefresh
import com.cooder.cooder.ui.refresh.CoRefreshLayout
import com.cooder.cooder.ui.refresh.overview.CoTextOverView

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
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_co_refresh)
		setStatusBar(true, Color.WHITE)
		
		val refreshLayout: CoRefreshLayout = findViewById(R.id.refresh_layout)
		refreshLayout.setRefreshOverView(CoTextOverView(this))
		refreshLayout.setRefreshListener(object : CoRefresh.CoRefreshListener {
			override fun onRefresh() {
				Toast.makeText(this@CoRefreshActivity, "刷新事件", Toast.LENGTH_SHORT).show()
				Handler(Looper.myLooper()!!).postDelayed({
					refreshLayout.refreshFinished()
				}, 1500L)
			}
		})
	}
}