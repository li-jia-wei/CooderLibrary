package com.cooder.cooder.app.ui.refresh

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.R
import com.cooder.cooder.library.util.CooderStatusBar
import com.cooder.cooder.ui.refresh.CooderRefresh
import com.cooder.cooder.ui.refresh.CooderRefreshLayout
import com.cooder.cooder.ui.refresh.overview.CooderTextOverView

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/13 16:57
 *
 * 介绍：CooderRefreshActivity
 */
class CooderRefreshActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_cooder_refresh)
		CooderStatusBar.setStatusBar(this, true, Color.WHITE)
		
		val refreshLayout: CooderRefreshLayout = findViewById(R.id.refresh_layout)
		refreshLayout.setRefreshOverView(CooderTextOverView(this))
		refreshLayout.setRefreshListener(object : CooderRefresh.CooderRefreshListener {
			override fun onRefresh() {
				Toast.makeText(this@CooderRefreshActivity, "刷新事件", Toast.LENGTH_SHORT).show()
				Handler(Looper.myLooper()!!).postDelayed({
					refreshLayout.refreshFinished()
				}, 1500L)
			}
		})
	}
}