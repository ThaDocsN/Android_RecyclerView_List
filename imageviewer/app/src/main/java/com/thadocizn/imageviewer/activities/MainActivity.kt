package com.thadocizn.imageviewer.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.thadocizn.imageviewer.R
import com.thadocizn.imageviewer.classes.ImageAdapter
import com.thadocizn.imageviewer.classes.ImageData

import java.util.ArrayList
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    private var imageArray: ArrayList<ImageData>? = null
    private val linearLayout: LinearLayout? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: ImageAdapter? = null

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, String.format("%s - onCreate", getLocalClassName()))

        setContentView(R.layout.activity_main)

        imageArray = ArrayList()
        recyclerView = findViewById(R.id.recycleViewer)
        adapter = ImageAdapter(imageArray)


        findViewById(R.id.btnAddImage).setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {

                val imageIntent = Intent(Intent.ACTION_GET_CONTENT)
                imageIntent.setType("image/*")
                startActivityForResult(imageIntent, IMAGE_REQUEST_CODE)
            }
        })

    }

    @Override
    protected fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_REQUEST_CODE) {
                if (data != null) {
                    val imageUri = data!!.getData()
                    var newImageData: ImageData? = null
                    if (imageUri != null) {
                        newImageData = ImageData(imageUri!!.toString(), imageArray!!.size())
                    }

                    imageArray!!.add(newImageData)
                    val imageIndex = imageArray!!.size() - 1
                    adapter!!.notifyItemInserted(imageIndex)


                    val layoutManager = LinearLayoutManager(getApplicationContext())
                    recyclerView!!.setLayoutManager(layoutManager)
                    val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                    recyclerView!!.addItemDecoration(itemDecor)
                    recyclerView!!.setHasFixedSize(true)
                    recyclerView!!.setAdapter(adapter)

                }
            }
        }
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

        private val TAG = MainActivity::class.java!!.getSimpleName()
        val IMAGE_REQUEST_CODE = 72
        val IMAGE_URL = "imageUrl"
        val IMAGE_NAME = "image_name"
        val IMAGE_ID = "imageId"
    }
}
