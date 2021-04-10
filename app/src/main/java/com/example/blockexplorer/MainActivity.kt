package com.example.blockexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val adapter by lazy {
        BlockAdapter { item ->
            println(item.height)
            val intent = Intent(this, BlockDetailActivity::class.java)
            intent.putExtra("block_id", item.id)
            this.startActivity(intent)

        }
    }
    private val retrofit by lazy {
        ApiHelper.retrofit.create(BlockServiceApi::class.java)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var prLoading: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linearLayout = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.recycler_view)
        prLoading = findViewById(R.id.progress_loading)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayout
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val position = linearLayout.findLastCompletelyVisibleItemPosition()
                if (position == adapter.itemCount - 1){
                    val lastItem = adapter.items[position]
                    val blockNumberRequest = lastItem.height -1
                  loadListBlock(blockNumberRequest.toString())
                }

            }
        })
        loadListBlock("")
    }

    private fun loadListBlock(blockHeight: String) {
        prLoading.isVisible = true
        lifecycleScope.launch {
            val response = retrofit.listBlock(blockHeight)
            if (response.isSuccessful) {
                val items = response.body() ?: emptyList()
                adapter.items.addAll(items)
                adapter.notifyDataSetChanged()
            }
            prLoading.isVisible = false
        }
    }
}