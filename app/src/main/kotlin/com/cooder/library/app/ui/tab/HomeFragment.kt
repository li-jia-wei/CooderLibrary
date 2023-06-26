package com.cooder.library.app.ui.tab

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.cooder.cooder.app.R
import com.cooder.library.app.ui.log.CoLogActivity

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/27 01:25
 *
 * 介绍：HomeFragment
 */
class HomeFragment : Fragment() {
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return LayoutInflater.from(requireContext()).inflate(R.layout.fragment_home, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.findViewById<Button>(R.id.test).setOnClickListener {
			startActivity(Intent(requireContext(), CoLogActivity::class.java))
		}
	}
}