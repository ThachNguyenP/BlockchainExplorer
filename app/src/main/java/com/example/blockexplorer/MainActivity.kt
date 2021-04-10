package com.example.blockexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val adapter by lazy {
        BlockAdapter()
    }
    private val retrofit by lazy {
        ApiHelper.retrofit.create(BlockServiceApi::class.java)
    }
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        init()
    }

    private fun init() {
        lifecycleScope.launch {
          val response =  retrofit.listBlock(678163)
            if (response.isSuccessful){
                val items = response.body()?: emptyList()
                adapter.items.addAll(items)
                adapter.notifyDataSetChanged()
            }
        }
    }
}