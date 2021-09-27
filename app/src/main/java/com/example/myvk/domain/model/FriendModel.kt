package com.example.myvk.domain.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class FriendModel(
    val firstName: String,
    val lastName: String,
    val city: String?,
    val avatar: String?,
    val isOnline: Boolean,
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte())


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(city)
        parcel.writeString(avatar)
        parcel.writeByte(if (isOnline) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FriendModel> {
        override fun createFromParcel(parcel: Parcel): FriendModel {
            return FriendModel(parcel)
        }

        override fun newArray(size: Int): Array<FriendModel?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject) = FriendModel(
            firstName = json.optString("first_name", ""),
            lastName = json.optString("last_name", ""),
            city = "",
            avatar = json.optString("photo_100", ""),
            isOnline = true)
    }
}
