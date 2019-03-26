package com.thadocizn.imageviewer.classes

import android.net.Uri

import java.io.Serializable

class ImageData : Serializable {

    var name: String? = null
    private var uri: String? = null
    var id: Int = 0
        private set

    constructor(name: String, uri: String, id: Int) {
        this.name = name
        this.uri = uri
        this.id = id
    }

    constructor(uri: String, id: Int) {
        this.uri = uri
        this.id = id
    }

    constructor(id: Int) {
        this.id = id
    }

    fun getUri(): Uri {
        return Uri.parse(uri)
    }

    fun setUri(uri: Uri) {
        this.uri = uri.toString()
    }
}
