package com.example.blockexplorer

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val adapter by lazy {
        BlockAdapter { item ->
            val intent = Intent(this, BlockDetailActivity::class.java)
            intent.putExtra("block_height", item.height)
            this.startActivity(intent)

        }
    }
    private val retrofit by lazy {
        ApiHelper.retrofit.create(BlockServiceApi::class.java)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var prLoading: ProgressBar

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DbHelper.init(application)
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh)
        swipeRefreshLayout.setOnRefreshListener{
            adapter.items.clear()
            adapter.notifyDataSetChanged()
            loadListBlock("")
            swipeRefreshLayout.isRefreshing = false
        }
        val linearLayout = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.recycler_view)
        prLoading = findViewById(R.id.progress_loading)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayout
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = recyclerView.childCount
                val totalItemCount = linearLayout.itemCount
                val firstVisibleItem = linearLayout.findFirstCompletelyVisibleItemPosition()

                if (totalItemCount - visibleItemCount <= firstVisibleItem){
                    val lastItem = adapter.items[linearLayout.findLastCompletelyVisibleItemPosition()]
                    val blockNumberRequest = lastItem.height - 1
                    loadListBlock(blockNumberRequest.toString())
                }
            }
        })
        loadListBlock("")
    }

    private fun loadListBlock(blockHeight: String) {
        prLoading.isVisible = true
        lifecycleScope.launch {
            if (blockHeight.isBlank()) {
                onLoadRemoteBlocks(blockHeight)
            } else {
                val localBlocks = DbHelper.getBlockDao().getBlocks(blockHeight.toInt())
                if (localBlocks.isNotEmpty()) {
                    adapter.items.addAll(localBlocks)
                    adapter.notifyDataSetChanged()
                } else {
                    onLoadRemoteBlocks(blockHeight)
                }
            }
            prLoading.isVisible = false
        }
    }

    private suspend fun onLoadRemoteBlocks(blockHeight: String) {
        val blocks = getBlocksFromRequest(retrofit.listBlock(blockHeight))
        if (blocks.isNotEmpty()) {
            DbHelper.getBlockDao().insertBlocks(blocks)
            adapter.items.addAll(blocks)
            adapter.notifyDataSetChanged()
        }
    }

    private fun getBlocksFromRequest(res: Response<List<Block>>): List<Block> {
        return if (res.isSuccessful) {
            res.body() ?: emptyList()
        } else {
            emptyList()
        }
    }
}