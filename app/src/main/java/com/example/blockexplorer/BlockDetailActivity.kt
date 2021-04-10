package com.example.blockexplorer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class BlockDetailActivity : AppCompatActivity() {

    private val retrofit by lazy {
        ApiHelper.retrofit.create(BlockServiceApi::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.block_item)

        val blockId = intent?.extras?.getString("block_id").toString()

        lifecycleScope.launch {
            val response =  retrofit.getBlock(blockId)
            if (response.isSuccessful){
                findViewById<TextView>(R.id.tv_height).text = response.body()?.blockHeight.toString()
            }
        }
    }
}