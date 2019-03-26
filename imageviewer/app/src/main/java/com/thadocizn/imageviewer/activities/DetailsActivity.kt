package com.thadocizn.imageviewer.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.thadocizn.imageviewer.R
import com.thadocizn.imageviewer.classes.ImageData

import java.util.Objects

class DetailsActivity : AppCompatActivity() {

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        Log.i(TAG, String.format("%s - onCreate", getLocalClassName()))


        val imageView = findViewById(R.id.imageView)
        val textView = findViewById(R.id.textView)

        val intent = getIntent()
        val image = intent.getStringExtra(MainActivity.IMAGE_URL)
        val index = Objects.requireNonNull(getIntent().getExtras()).getInt(MainActivity.IMAGE_ID)
        val imageName = intent.getStringExtra(MainActivity.IMAGE_NAME)

        val imageData = ImageData(imageName, image, index)

        intent.getSerializableExtra(image)
        textView.setText(imageData.getName())
        imageView.setImageURI(imageData.getUri())

        imageView.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                val intent1 = Intent(getApplicationContext(), FullscreenActivity::class.java)
                intent1.putExtra(IMAGE_URL, image)
                startActivity(intent1)

            }
        })

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

        val IMAGE_URL = "imageUrl"
        private val TAG = DetailsActivity::class.java!!.getSimpleName()
    }
}
