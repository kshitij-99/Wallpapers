package com.example.myapplication

import io.reactivex.rxjava3.core.Observable

class ImagesManager(private val api: RestApi = RestApi()) {
    fun getNews(after: String="",limit: String = "25"): Observable<RedditNewsResponse> {
        return Observable.create { subscriber ->
            val callResponse = api.getNews(after, limit)
            val response = callResponse.execute()
            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}