package com.example.myvk.domain.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class GroupModel(
    val groupIcon: String,
    val groupName: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,)


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(groupIcon)
        parcel.writeString(groupName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupModel> {
        override fun createFromParcel(parcel: Parcel): GroupModel {
            return GroupModel(parcel)
        }

        override fun newArray(size: Int): Array<GroupModel?> {
            return arrayOfNulls(size)
        }

        fun parse(group: JSONObject): GroupModel{

            return GroupModel(
                groupIcon = group.optString("photo_100", ""),
                groupName = group.optString("name", ""),
            )
        }
    }
}
