package com.cooder.library.library.log.printer

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.library.library.R
import com.cooder.library.library.log.CoLogLevel
import com.cooder.library.library.log.config.CoLogConfig
import com.cooder.library.library.util.CoMainHandler

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/26 09:47
 *
 * 介绍：将Log显示在界面上
 */
class CoViewPrinter(
	activity: Activity,
) : CoLogPrinter {
	
	private val recyclerView: RecyclerView
	private var adapter: LogAdapter
	private var viewProvider: CoViewPrinterProvider
	
	companion object {
		private var instance: CoLogPrinter? = null
		
		fun getInstance(activity: Activity): CoLogPrinter {
			return instance ?: let {
				synchronized(CoConsolePrinter::class.java) {
					instance ?: let {
						instance = CoViewPrinter(activity)
						instance!!
					}
				}
			}
		}
	}
	
	init {
		val rootView: FrameLayout = activity.findViewById(android.R.id.content)
		recyclerView = RecyclerView(activity)
		adapter = LogAdapter(LayoutInflater.from(recyclerView.context))
		val layoutManager = LinearLayoutManager(recyclerView.context)
		recyclerView.layoutManager = layoutManager
		recyclerView.adapter = adapter
		viewProvider = CoViewPrinterProvider(rootView, recyclerView)
	}
	
	/**
	 * 获取ViewProvider
	 */
	fun getViewProvider(): CoViewPrinterProvider {
		return viewProvider
	}
	
	override fun print(config: CoLogConfig, level: CoLogLevel, tag: String, msg: String) {
		CoMainHandler.post {
			adapter.addItem(CoLogMo(System.currentTimeMillis(), level, tag, msg))
			recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
		}
	}
	
	private class LogAdapter(
		private val inflater: LayoutInflater
	) : RecyclerView.Adapter<LogViewHolder>() {
		
		private val logMos = mutableListOf<CoLogMo>()
		
		fun addItem(logMo: CoLogMo) {
			this.logMos += logMo
			notifyItemInserted(itemCount - 1)
		}
		
		fun getItem(position: Int): CoLogMo {
			return logMos[position]
		}
		
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
			val view = inflater.inflate(R.layout.item_log, parent, false)
			return LogViewHolder(view)
		}
		
		override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
			val item = getItem(position)
			val color = getHighlightColor(item.level)
			holder.logView.setTextColor(color.toInt())
			holder.logView.text = item.toString()
		}
		
		override fun getItemCount(): Int {
			return logMos.size
		}
		
		private fun getHighlightColor(level: CoLogLevel): Long {
			return when (level) {
				CoLogLevel.VERBOSE -> 0xFF4AFF71
				CoLogLevel.DEBUG -> 0xFF74FF66
				CoLogLevel.INFO -> 0xFFD6FF62
				CoLogLevel.WARN -> 0xFFFFE794
				CoLogLevel.ERROR -> 0xFFFF9391
				CoLogLevel.ASSERT -> 0xFFFF6B68
			}
		}
	}
	
	private class LogViewHolder(
		view: View
	) : RecyclerView.ViewHolder(view) {
		var logView: TextView
		
		init {
			logView = view.findViewById(R.id.log)
		}
	}
}