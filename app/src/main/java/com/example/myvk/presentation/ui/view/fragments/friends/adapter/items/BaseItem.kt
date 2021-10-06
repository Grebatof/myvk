package com.example.myvk.presentation.ui.view.fragments.friends.adapter.items

import com.example.myvk.domain.model.FriendModel

sealed class BaseItem(val type: Int) {
    companion object {
        const val TYPE_ERROR = 0
        const val TYPE_LOADING = 1
        const val TYPE_FRIEND = 2
    }
}
data class Error(val message: String): BaseItem(TYPE_ERROR)
object Loading: BaseItem(TYPE_LOADING)
data class Friend(val friend: FriendModel): BaseItem(TYPE_FRIEND)
