package com.thadocizn.imageviewer.classes

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.thadocizn.imageviewer.R
import com.thadocizn.imageviewer.activities.DetailsActivity
import com.thadocizn.imageviewer.activities.MainActivity

import java.util.ArrayList

class ImageAdapter(internal var imageList: ArrayList<ImageData>) : RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    private var context: Context? = null

    val itemCount: Int
        @Override
        get() = imageList.size()

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtTitle: TextView
        var imgPicture: ImageView
        var parentLayout: LinearLayout

        init {
            txtTitle = view.findViewById(R.id.tvTitle)
            imgPicture = view.findViewById(R.id.ivPicture)
            parentLayout = view.findViewById(R.id.parentLayout)
        }
    }

    @NonNull
    @Override
    fun onCreateViewHolder(@NonNull parent: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_layout, parent, false)

        context = parent.getContext()
        return MyViewHolder(view)
    }

    @Override
    fun onBindViewHolder(@NonNull myViewHolder: MyViewHolder, i: Int) {
        val imageData = imageList.get(i)
        val test = imageData.getName()
        myViewHolder.txtTitle.setText(test)
        myViewHolder.imgPicture.setImageURI(imageData.getUri())
        myViewHolder.parentLayout.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(MainActivity.IMAGE_URL, imageData.getUri().toString())
                intent.putExtra(MainActivity.IMAGE_NAME, test)
                context!!.startActivity(intent)

            }
        })
    }

}
