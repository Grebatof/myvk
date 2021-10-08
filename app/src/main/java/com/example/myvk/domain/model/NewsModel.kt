package com.example.myvk.domain.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class NewsModel(
    val groupIcon: String,
    val groupName: String,
    val date: String,
    val text: String,
    val photos: List<String>,
    val likes: String,
    val comments: String,
    val reposts: String,
    val views: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        arrayListOf<String>().apply {
            parcel.readList(this, String()::class.java.classLoader)
        },
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!)


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(groupIcon)
        parcel.writeString(groupName)
        parcel.writeString(date)
        parcel.writeString(text)
        parcel.writeStringList(photos)
        parcel.writeString(likes)
        parcel.writeString(comments)
        parcel.writeString(reposts)
        parcel.writeString(views)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsModel> {
        override fun createFromParcel(parcel: Parcel): NewsModel {
            return NewsModel(parcel)
        }

        override fun newArray(size: Int): Array<NewsModel?> {
            return arrayOfNulls(size)
        }

        fun parse(item: JSONObject, group: JSONObject): NewsModel{

            return NewsModel(
                groupIcon = group.optString("photo_100", ""),
                groupName = group.optString("name", ""),
                date = item.optString("date", ""),
                text = item.optString("text", ""),
                photos = takePhotos(item),
                likes = item.optJSONObject("likes")?.optString("count", "-1").toString(),
                comments = item.optJSONObject("comments")?.optString("count", "-1").toString(),
                reposts = item.optJSONObject("reposts")?.optString("count", "-1").toString(),
                views = item.optJSONObject("views")?.optString("count", "-1").toString(),
            )
        }

        fun takePhotos(item: JSONObject): List<String>{
            val list = ArrayList<String>()
            val attachments = item.optJSONArray("attachments")
            attachments?.let {
                for (j in 0 until attachments.length()) {
                    if (attachments.optJSONObject(j).optString("type") == "photo") {
                        val photo = attachments.optJSONObject(j)?.optJSONObject("photo")?.optJSONArray("sizes")
                        photo?.optJSONObject(photo.length() - 1)?.optString("url")?.let {
                            list.add(it)
                        }
                    }
                }
            }
            return list
        }
    }
}
