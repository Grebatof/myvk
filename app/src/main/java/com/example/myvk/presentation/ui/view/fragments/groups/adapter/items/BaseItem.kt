package com.example.myvk.presentation.ui.view.fragments.groups.adapter.items

import com.example.myvk.domain.model.GroupModel

sealed class BaseItem(val type: Int) {
    data class Error(val message: String): BaseItem(TYPE_ERROR)
    object Loading: BaseItem(TYPE_LOADING)
    data class Group(val group: GroupModel): BaseItem(TYPE_GROUP)
    companion object {
        const val TYPE_ERROR = 0
        const val TYPE_LOADING = 1
        const val TYPE_GROUP = 2
    }
}