package com.example.blockexplorer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class BlockDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.block_item)

        val blockId = intent.getIntExtra("block_height",0)

        lifecycleScope.launch {
            val block =  DbHelper.getBlockDao().getBlock(blockId)
            findViewById<TextView>(R.id.tv_height).text = block.height.toString()
            findViewById<TextView>(R.id.tv_transaction).text = block.transaction.toString()
            findViewById<TextView>(R.id.tv_size).text = block.size.toString()
        }
    }
}