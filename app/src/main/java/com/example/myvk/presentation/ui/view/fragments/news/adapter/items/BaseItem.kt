package com.example.myvk.presentation.ui.view.fragments.news.adapter.items

import com.example.myvk.domain.model.NewsModel

sealed class BaseItem(val type: Int) {
    companion object {
        const val TYPE_ERROR = 0
        const val TYPE_LOADING = 1
        const val TYPE_NEWS = 2
    }
}
data class Error(val message: String): BaseItem(TYPE_ERROR)
object Loading: BaseItem(TYPE_LOADING)
data class News(val news: NewsModel): BaseItem(TYPE_NEWS)