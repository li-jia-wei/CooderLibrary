package com.cooder.library.app.ui.cache

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cooder.library.app.R
import com.cooder.library.app.databinding.ActivityCoCacheBinding
import com.cooder.library.library.cache.CoStorage
import com.cooder.library.library.cache.CoStorageType
import java.io.Serializable

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/5 21:07
 *
 * 介绍：CoRoomActivity
 */
class CoCacheActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoCacheBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoCacheBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		val save: Button = findViewById(R.id.save)
		save.setOnClickListener {
			val name: String = binding.name.text.toString()
			val age: Int = binding.age.text.toString().toInt()
			val student = Student(name, age)
			CoStorage.saveCache(CoStorageType.RECORD, "student", student)
			Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
		}
		
		val remove: Button = findViewById(R.id.remove)
		remove.setOnClickListener {
			CoStorage.deleteCache(CoStorageType.CONFIG, "student")
			Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show()
		}
		
		val query: Button = findViewById(R.id.query)
		query.setOnClickListener {
			val student = CoStorage.getCache<Student>(CoStorageType.RECORD, "student")
			Toast.makeText(this, student.toString(), Toast.LENGTH_SHORT).show()
		}
	}
	
	data class Student(val name: String, val age: Int) : Serializable {
		companion object {
			const val serialVersionUID = 1L
		}
	}
}