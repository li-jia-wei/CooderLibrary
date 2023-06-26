package com.cooder.cooder.app.ui.navigation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.R
import com.cooder.cooder.app.databinding.ActivityCoNavigationBarBinding

class CoNavigationBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoNavigationBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoNavigationBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationBar2.setNavigationListener {
            Toast.makeText(this, "返回按钮", Toast.LENGTH_SHORT).show()
        }

        binding.navigationBar4.setNavigationListener {
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
        binding.navigationBar7.setNavigationListener {
            Toast.makeText(this, "返回按钮", Toast.LENGTH_SHORT).show()
        }

        binding.navigationBar8.setNavigationListener {
            Toast.makeText(this, "返回按钮", Toast.LENGTH_SHORT).show()
        }

        val menu81 = binding.navigationBar8.addRightIconButton(R.string.ic_category_category_products)
        menu81.setOnClickListener {
            Toast.makeText(this, "分类图标", Toast.LENGTH_SHORT).show()
        }

        binding.navigationBar9.setNavigationListener {
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
    }
}