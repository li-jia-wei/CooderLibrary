package com.cooder.cooder.library.log.printer

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.R
import com.cooder.cooder.library.log.CoLogConfig
import com.cooder.cooder.library.log.CoLogType

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
	
	override fun print(config: CoLogConfig, level: Int, tag: String, printString: String) {
		adapter.addItem(CoLogMo(System.currentTimeMillis(), level, tag, printString))
		recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
	}
	
	private class LogAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<LogViewHolder>() {
		
		private val logMos = mutableListOf<CoLogMo>()
		
		fun addItem(logMo: CoLogMo) {
			this.logMos += logMo
			notifyItemInserted(itemCount - 1)
		}
		
		fun getItem(position: Int): CoLogMo {
			return logMos[position]
		}
		
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
			val view = inflater.inflate(R.layout.log_item, parent, false)
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
		
		private fun getHighlightColor(@CoLogType.Type level: Int): Long {
			return when (level) {
				CoLogType.V -> 0xFF4AFF71
				CoLogType.D -> 0xFF74FF66
				CoLogType.I -> 0xFFD6FF62
				CoLogType.W -> 0xFFFFE794
				CoLogType.E -> 0xFFFF9391
				CoLogType.A -> 0xFFFF6B68
				else -> 0xFFFFFFFF
			}
		}
	}
	
	private class LogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		var logView: TextView
		
		init {
			logView = view.findViewById(R.id.log)
		}
	}
}