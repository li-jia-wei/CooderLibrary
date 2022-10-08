package com.cooder.cooder.library.log.printer

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.R
import com.cooder.cooder.library.log.CooderLogConfig
import com.cooder.cooder.library.log.CooderLogType

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/26 09:47
 *
 * 介绍：将Log显示在界面上
 */
class CooderViewPrinter(
	activity: Activity,
) : CooderLogPrinter {
	
	private val recyclerView: RecyclerView
	private var adapter: LogAdapter
	private var viewProvider: CooderViewPrinterProvider
	
	private lateinit var a: ListView
	init {
		val rootView: FrameLayout = activity.findViewById(android.R.id.content)
		recyclerView = RecyclerView(activity)
		adapter = LogAdapter(LayoutInflater.from(recyclerView.context))
		val layoutManager = LinearLayoutManager(recyclerView.context)
		recyclerView.layoutManager = layoutManager
		recyclerView.adapter = adapter
		viewProvider = CooderViewPrinterProvider(rootView, recyclerView)
	}
	
	/**
	 * 获取ViewProvider
	 */
	fun getViewProvider(): CooderViewPrinterProvider {
		return viewProvider
	}
	
	override fun print(config: CooderLogConfig, level: Int, tag: String, printString: String) {
		adapter.addItem(CooderLogMo(System.currentTimeMillis(), level, tag, printString))
		recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
	}
	
	private class LogAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<LogViewHolder>() {
		
		private val logMos = mutableListOf<CooderLogMo>()
		
		fun addItem(logMo: CooderLogMo) {
			this.logMos += logMo
			notifyItemInserted(itemCount - 1)
		}
		
		fun getItem(position: Int): CooderLogMo {
			return logMos[position]
		}
		
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
			val view = inflater.inflate(R.layout.cooder_log_item, parent, false)
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
		
		private fun getHighlightColor(@CooderLogType.Type level: Int): Long {
			return when (level) {
				CooderLogType.V -> 0xFF4AFF71
				CooderLogType.D -> 0xFF74FF66
				CooderLogType.I -> 0xFFD6FF62
				CooderLogType.W -> 0xFFFFE794
				CooderLogType.E -> 0xFFFF9391
				CooderLogType.A -> 0xFFFF6B68
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