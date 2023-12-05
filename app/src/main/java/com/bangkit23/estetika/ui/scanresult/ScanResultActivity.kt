package com.bangkit23.estetika.ui.scanresult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bangkit23.estetika.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanResultActivity : AppCompatActivity() {
    private lateinit var scanRes: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_result)

        scanRes = findViewById(R.id.scanbatik)
        scanRes.text = intent.getStringExtra(EXTRA_RESULT)!!

    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }
}