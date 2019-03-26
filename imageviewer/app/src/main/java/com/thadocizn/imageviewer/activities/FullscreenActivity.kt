package com.thadocizn.imageviewer.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

import com.thadocizn.imageviewer.R

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {
    private val mHideHandler = Handler()
    private var mContentView: ImageView? = null
    private val mHidePart2Runnable = object : Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        fun run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView!!.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }
    }
    private var mControlsView: View? = null
    private val mShowPart2Runnable = object : Runnable() {
        @Override
        fun run() {
            // Delayed display of UI elements
            val actionBar = getSupportActionBar()
            if (actionBar != null) {
                actionBar!!.show()
            }
            mControlsView!!.setVisibility(View.VISIBLE)
        }
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = object : Runnable() {
        @Override
        fun run() {
            hide()
        }
    }
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val mDelayHideTouchListener = object : View.OnTouchListener() {
        @Override
        fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS)
            }
            return false
        }
    }

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)

        Log.i(TAG, String.format("%s - onCreate", getLocalClassName()))


        setContentView(R.layout.activity_fullscreen)


        mVisible = true
        mControlsView = findViewById(R.id.fullscreen_content_controls)
        mContentView = findViewById(R.id.fullscreen_content)

        val intent = getIntent()
        val image = intent.getStringExtra(DetailsActivity.IMAGE_URL)

        intent.getSerializableExtra(image)
        val uri = Uri.parse(image)
        mContentView!!.setImageURI(uri)


        // Set up the user interaction to manually show or hide the system UI.
        mContentView!!.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(view: View) {
                toggle()
            }
        })

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener)
    }

    @Override
    protected fun onPostCreate(savedInstanceState: Bundle) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        val actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar!!.hide()
        }
        mControlsView!!.setVisibility(View.GONE)
        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY)
    }

    @SuppressLint("InlinedApi")
    private fun show() {
        // Show the system bar
        mContentView!!.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        mVisible = true

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY)
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis)
    }

    @Override
    protected fun onStop() {
        super.onStop()
        Log.i(TAG, String.format("%s - onStop", getLocalClassName()))

    }

    @Override
    protected fun onPause() {
        super.onPause()
        Log.i(TAG, String.format("%s - onPause", getLocalClassName()))

    }

    @Override
    protected fun onStart() {
        super.onStart()
        Log.i(TAG, String.format("%s - onStart", getLocalClassName()))

    }

    @Override
    protected fun onResume() {
        super.onResume()
        Log.i(TAG, String.format("%s - onResume", getLocalClassName()))

    }

    @Override
    protected fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, String.format("%s - onDestroy", getLocalClassName()))

    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [.AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [.AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
        private val TAG = FullscreenActivity::class.java!!.getSimpleName()
    }
}
