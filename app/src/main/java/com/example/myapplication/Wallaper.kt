package com.example.myapplication

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import kotlinx.android.synthetic.main.activity_wallaper.*
import java.io.IOException

class Wallaper : AppCompatActivity() {
    private var bottomUp = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallaper)
        val image = intent.getStringExtra("image")
        Glide.with(applicationContext)  //2
            .load(image) //3
            .placeholder(R.drawable.ic_launcher_background) //5
            .error(R.drawable.ic_launcher_foreground) //6
            .into(wall)
        textset.setOnClickListener(View.OnClickListener {
            wallbar.visibility=View.VISIBLE
            Glide.with(applicationContext)
                .asBitmap()
                .load(image)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                        val wallpapermanger = WallpaperManager.getInstance(applicationContext)
                        try {
                            wallpapermanger.setBitmap(resource)
                            wallbar.visibility=View.GONE

                        } catch (ex: IOException) {
                            ex.printStackTrace()
                            wallbar.visibility=View.GONE

                        }
                    }


                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
            Toast.makeText(applicationContext,"Clicked",Toast.LENGTH_LONG).show()


        })
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun toggleBottom(view: View) {
        bottomUp = !bottomUp
        toggle(bottomUp)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        if (bottomUp) {
            toggle(false)
            bottomUp = false
        } else {
            super.onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun toggle(show: Boolean) {
        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.duration = 400
        transition.addTarget(R.id.bottomSheet)
        TransitionManager.beginDelayedTransition(rooted as ViewGroup, transition)
        bottomSheet.visibility = if (show) View.VISIBLE else View.GONE
        if (show) {
            expandButton.visibility = View.GONE
        } else {
            Handler().postDelayed(Runnable {
                expandButton.visibility = View.VISIBLE
            }, 400)
        }
    }

}
