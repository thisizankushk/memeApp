package com.example.memeapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }

    var currentimageurl: String? = null
    private fun loadmeme() {
        progressbar.visibility = View.VISIBLE
// Instantiate the RequestQueue.
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentimageurl = response.getString("url")

                Glide.with(this).load(currentimageurl).listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        memeimage.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        memeimage.visibility = View.VISIBLE
                        return false
                    }


                }).into(memeimage)
            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun next(view: View) {
        memeimage.visibility = View.GONE
        loadmeme()
    }

    fun share(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "oye, yeh meme dekh  $currentimageurl"
        )
        val chooser = Intent.createChooser(intent, "Share this meme using..")
        startActivity(chooser)
    }
}