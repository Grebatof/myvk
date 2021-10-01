package com.example.myvk.presentation.ui.view.fragment.friends.adapter.items

import com.example.myvk.domain.model.FriendModel

sealed class BaseItem(val type: Int) {
    data class Error(val message: String): BaseItem(TYPE_ERROR)
    class Loading: BaseItem(TYPE_LOADING)
    data class Friend(val friend: FriendModel): BaseItem(TYPE_FRIEND)

    companion object {
        const val TYPE_ERROR = 0
        const val TYPE_LOADING = 1
        const val TYPE_FRIEND = 2
    }
}
