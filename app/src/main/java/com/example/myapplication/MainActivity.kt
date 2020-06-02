package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private val imagesManager by lazy { ImagesManager() }
    private val w : MutableList<String> = ArrayList()
    private var after : String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        val lm = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.apply {
            layoutManager = lm
            adapter = ImagesAdapter(applicationContext, w)
        }


        recyclerview.clearOnScrollListeners()
        recyclerview.addOnScrollListener(InfiniteScrollListener({requestimages()},lm))
        requestimages()


    }
     private fun requestimages()
    {
        listprogressbar.visibility=View.VISIBLE
        val myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable.add(imagesManager.getNews(after,"25")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))
    }

    private fun onResponse(response: RedditNewsResponse) {
        val childrens : List<RedditChildrenResponse> = response.data.children
        after=response.data.after!!

                for (x in childrens)
                {
                    w.add(x.data.url!!)
                }
    recyclerview.adapter!!.notifyDataSetChanged()
        listprogressbar.visibility=View.GONE



    }
    private fun onFailure(t: Throwable) {
        Toast.makeText(this,t.message, Toast.LENGTH_SHORT).show()
        Log.d("failll",t.message.toString())
    }


    override fun onDestroy() {
        super.onDestroy()

    }
}
