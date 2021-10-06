package com.example.myvk.presentation.ui.view.fragments.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myvk.domain.model.NewsModel
import com.example.myvk.domain.use_case.VKNewsRequest
import com.example.myvk.presentation.ui.view.fragments.news.adapter.items.BaseItem
import com.example.myvk.presentation.ui.view.fragments.news.adapter.items.Loading
import com.example.myvk.presentation.ui.view.fragments.news.adapter.items.News
import com.example.myvk.presentation.ui.view.fragments.news.adapter.items.Error
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    var listNewsLiveData = MutableLiveData<List<BaseItem>>()

    fun loadNews() {
        listNewsLiveData.postValue(listOf(Loading))
        VK.execute(VKNewsRequest(), object : VKApiCallback<List<NewsModel>> {
            override fun success(result: List<NewsModel>) {
                listNewsLiveData.postValue(result.map { News(it) })
            }

            override fun fail(error: Exception) {
                Log.d("!!!", "Error is ${error.toString()}")
                listNewsLiveData.postValue(listOf(Error(error.toString())))
            }
        })
    }
}