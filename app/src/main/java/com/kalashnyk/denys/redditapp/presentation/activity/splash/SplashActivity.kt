package com.kalashnyk.denys.redditapp.presentation.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.kalashnyk.denys.redditapp.R
import com.kalashnyk.denys.redditapp.databinding.ActivitySplashBinding
import com.kalashnyk.denys.redditapp.presentation.activity.main.MainActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        private val AUTO_HIDE_DELAY_MILLIS = 1500
    }

    private val hideHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hideHandler.postDelayed(mainOpen, AUTO_HIDE_DELAY_MILLIS.toLong())
    }

    private val mainOpen = Runnable {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
