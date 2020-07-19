package com.michaeludjiawan.githubfinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michaeludjiawan.githubfinder.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main_base_container, MainFragment())
            .commit()
    }
}
