package com.example.myvk.presentation.ui.view.fragments.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myvk.domain.model.NewsModel
import com.example.myvk.domain.use_case.VKNewsRequest
import com.example.myvk.presentation.ui.view.fragments.news.adapter.items.BaseItem
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    var listNewsLiveData = MutableLiveData<List<BaseItem>>()

    fun loadNews() {
        listNewsLiveData.postValue(listOf(BaseItem.Loading))
        VK.execute(VKNewsRequest(), object : VKApiCallback<List<NewsModel>> {
            override fun success(result: List<NewsModel>) {
                listNewsLiveData.postValue(generateList(result))
            }

            override fun fail(error: Exception) {
                Log.d("!!!", "Error is ${error.toString()}")
                listNewsLiveData.postValue(listOf(BaseItem.Error(error.toString())))
            }
        })
    }

    fun generateList(news: List<NewsModel>): List<BaseItem> {
        val list = ArrayList<BaseItem>()
        news.forEach {
            list.add(BaseItem.Header(groupIcon = it.groupIcon, groupName = it.groupName, date = it.date))
            list.add(BaseItem.TextContent(text = it.text)) // !null
            it.photos.forEach { photo ->
                list.add(BaseItem.ImageContent(photo = photo))
            }
            list.add(BaseItem.Statistics(likes = it.likes, comments = it.comments, reposts = it.reposts, views = it.views))
        }
        return list
    }
}