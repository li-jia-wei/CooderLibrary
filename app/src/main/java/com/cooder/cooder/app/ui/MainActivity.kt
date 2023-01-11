package com.cooder.cooder.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.app.R
import com.cooder.cooder.app.ui.banner.CooderBannerActivity
import com.cooder.cooder.app.ui.item.CooderDataItemActivity
import com.cooder.cooder.app.ui.log.CooderLogActivity
import com.cooder.cooder.app.ui.refresh.CooderRefreshActivity
import com.cooder.cooder.app.ui.slider.CooderSliderActivity
import com.cooder.cooder.app.ui.tab.CooderTabBottomActivity
import com.cooder.cooder.app.ui.tab.CooderTabTopActivity
import com.cooder.cooder.app.ui.test.TestActivity
import com.cooder.cooder.ui.item.CooderAdapter
import com.cooder.cooder.ui.item.CooderDataItem
import kotlin.reflect.KClass

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 00:34
 *
 * 介绍：MainActivity
 */
class MainActivity : AppCompatActivity() {

    private val components: List<Pair<KClass<out AppCompatActivity>, String>> = listOf(
        TestActivity::class to "Test",
        CooderLogActivity::class to "Log",
        CooderTabBottomActivity::class to "TabBottom",
        CooderTabTopActivity::class to "TabTop",
        CooderRefreshActivity::class to "Refresh",
        CooderBannerActivity::class to "Banner",
        CooderDataItemActivity::class to "DataItem",
        CooderSliderActivity::class to "Slider"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter = CooderAdapter(this)
        recyclerView.adapter = adapter
        val dataItems = mutableListOf<ComponentsDataItem>()
        components.forEach {
            dataItems += ComponentsDataItem(this, it)
        }
        adapter.addItems(dataItems)
    }

    class ComponentsDataItem(
        private val context: Context,
        private val itemData: Pair<KClass<out AppCompatActivity>, String>
    ) : CooderDataItem<Pair<KClass<out AppCompatActivity>, String>, ComponentsHolder>(itemData) {

        override fun getItemLayoutRes(): Int {
            return R.layout.item_component
        }

        override fun getSpanSize(): Int {
            return 1
        }

        override fun onBindData(holder: ComponentsHolder, position: Int) {
            holder.name.text = itemData.second
            holder.name.setOnClickListener {
                context.startActivity(Intent(context, itemData.first.java))
            }
        }
    }

    class ComponentsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
    }
}