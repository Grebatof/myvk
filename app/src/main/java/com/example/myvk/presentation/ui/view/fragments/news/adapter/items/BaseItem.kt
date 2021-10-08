package com.example.myvk.presentation.ui.view.fragments.news.adapter.items

sealed class BaseItem(val type: Int) {
    data class Error(val message: String): BaseItem(TYPE_ERROR)
    object Loading: BaseItem(TYPE_LOADING)
    data class Header(val groupIcon: String, val groupName: String, val date: String): BaseItem(TYPE_HEADER)
    data class TextContent(val text: String): BaseItem(TYPE_TEXT_CONTENT)
    data class ImageContent(val photo: String): BaseItem(TYPE_IMAGE_CONTENT)
    data class State(val likes: String, val comments: String, val reposts: String, val views: String): BaseItem(TYPE_STATE)

    companion object {
        const val TYPE_ERROR = 0
        const val TYPE_LOADING = 1
        const val TYPE_HEADER = 2
        const val TYPE_TEXT_CONTENT = 3
        const val TYPE_IMAGE_CONTENT = 4
        const val TYPE_STATE = 5
    }
}
