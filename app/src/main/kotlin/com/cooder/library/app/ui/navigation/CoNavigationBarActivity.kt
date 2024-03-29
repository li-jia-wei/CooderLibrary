package com.cooder.library.app.ui.navigation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cooder.library.app.R
import com.cooder.library.app.databinding.ActivityCoNavigationBarBinding
import com.cooder.library.ui.R as RUi

class CoNavigationBarActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoNavigationBarBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoNavigationBarBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		binding.navigationBar2.setOnClickNavListener {
			Toast.makeText(this, "返回按钮", Toast.LENGTH_SHORT).show()
		}
		
		binding.navigationBar4.setOnClickNavListener {
			Toast.makeText(this, "返回按钮", Toast.LENGTH_SHORT).show()
		}
		
		val menu51 = binding.navigationBar5.addRightTextButton("菜单1")
		menu51.setOnClickListener {
			Toast.makeText(this, "菜单1", Toast.LENGTH_SHORT).show()
		}
		val menu52 = binding.navigationBar5.addRightTextButton("菜单2")
		menu52.setOnClickListener {
			Toast.makeText(this, "菜单2", Toast.LENGTH_SHORT).show()
		}
		
		val menu61 = binding.navigationBar6.addRightIconButton(R.string.ic_category_category_products)
		menu61.setOnClickListener {
			Toast.makeText(this, "分类图标", Toast.LENGTH_SHORT).show()
		}
		
		val menu71 = binding.navigationBar7.addRightIconButton(R.string.ic_category_category_products)
		menu71.setOnClickListener {
			Toast.makeText(this, "分类图标", Toast.LENGTH_SHORT).show()
		}
		binding.navigationBar7.setOnClickNavListener {
			Toast.makeText(this, "返回按钮", Toast.LENGTH_SHORT).show()
		}
		
		binding.navigationBar8.setOnClickNavListener {
			Toast.makeText(this, "返回按钮", Toast.LENGTH_SHORT).show()
		}
		
		val menu81 = binding.navigationBar8.addRightIconButton(R.string.ic_category_category_products)
		menu81.setOnClickListener {
			Toast.makeText(this, "分类图标", Toast.LENGTH_SHORT).show()
		}
		
		binding.navigationBar9.setOnClickNavListener {
			Toast.makeText(this, "返回按钮", Toast.LENGTH_SHORT).show()
		}
		val menu91 = binding.navigationBar9.addRightTextButton("菜单1")
		menu91.setOnClickListener {
			Toast.makeText(this, "菜单1", Toast.LENGTH_SHORT).show()
		}
		val menu92 = binding.navigationBar9.addRightTextButton("菜单2")
		menu92.setOnClickListener {
			Toast.makeText(this, "菜单2", Toast.LENGTH_SHORT).show()
		}
		
		val menu101 = binding.navigationBar10.addRightTextButton("菜单1", android.R.color.holo_blue_light)
		menu101.setOnClickListener {
			Toast.makeText(this, "菜单1", Toast.LENGTH_SHORT).show()
		}
		val menu102 = binding.navigationBar10.addRightTextButton("菜单2")
		menu102.setOnClickListener {
			Toast.makeText(this, "菜单2", Toast.LENGTH_SHORT).show()
		}
		
		val menu111 = binding.navigationBar11.addRightIconButton(RUi.string.ic_global_fill, android.R.color.holo_blue_light)
		menu111.setOnClickListener {
			Toast.makeText(this, "外部服务", Toast.LENGTH_SHORT).show()
		}
		val menu112 = binding.navigationBar11.addRightIconButton(RUi.string.ic_layers_fill)
		menu112.setOnClickListener {
			Toast.makeText(this, "分层", Toast.LENGTH_SHORT).show()
		}
	}
}