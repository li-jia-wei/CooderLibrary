package com.cooder.cooder.app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/27 18:34
 *
 * 介绍：ComponentsAdapter
 */
class ComponentsAdapter(private val context: Context, private val data: List<Pair<Class<out AppCompatActivity>, String>>) : BaseAdapter() {
	
	private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
	
	override fun getCount(): Int {
		return data.size
	}
	
	override fun getItem(position: Int): Pair<Class<out AppCompatActivity>, String> {
		return data[position]
	}
	
	override fun getItemId(position: Int): Long {
		return position.toLong()
	}
	
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		var view = convertView
		val name: TextView
		if (view == null) {
			view = inflater.inflate(R.layout.item_component, parent, false)
			name = view!!.findViewById(R.id.name)
			view.tag = name
		} else {
			name = view.tag as TextView
		}
		val item = getItem(position)
		name.text = item.second
		name.setOnClickListener {
			context.startActivity(Intent(context, item.first))
		}
		return view
	}
}