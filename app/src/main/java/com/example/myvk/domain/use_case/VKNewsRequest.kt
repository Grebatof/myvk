package com.example.myvk.domain.use_case

import com.example.myvk.domain.model.NewsModel
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONArray
import org.json.JSONObject

class VKNewsRequest() : VKRequest<List<NewsModel>>("newsfeed.get") {
    init {
        addParam(name = "fields", value = "date,text")
    }

    override fun parse(r: JSONObject): List<NewsModel> {
        val newsItems = r.getJSONObject("response").getJSONArray("items")
        val groupItems = r.getJSONObject("response").getJSONArray("groups")
        val result = ArrayList<NewsModel>()

        val listNewsItems = parseNewsItems(newsItems)
        val listGroupsItems = parseGroupsItems(groupItems)

        listNewsItems.forEach { newsDto ->
            listGroupsItems.find {
                it.id == newsDto.source_id
            }?.let { NewsModel(
                groupIcon = it.groupIcon,
                groupName = it.groupName,
                date = newsDto.date,
                text = newsDto.text,
                photos = newsDto.photos,
                likes = newsDto.likes,
                comments = newsDto.comments,
                reposts = newsDto.reposts,
                views = newsDto.views,
            ) }?.let {
                result.add(it)
            }
        }
        return result
    }

    fun parseNewsItems(newsItems: JSONArray): List<NewsDto> {
        val list = ArrayList<NewsDto>()
        for (i in 0 until newsItems.length()) {
            val newsItem = newsItems.getJSONObject(i)
            if (newsItem.optString("type", "error") != "post") {
                continue
            }
            list.add(NewsDto(
                source_id = newsItem.optString("source_id"),
                date = java.text.SimpleDateFormat("dd MMMM HH:mm").format(java.util.Date(newsItem.optLong("date", 0L) * 1000)),
                text = newsItem.optString("text", ""),
                photos = parsePhotos(newsItem),
                likes = newsItem.optJSONObject("likes")?.optString("count", "-1").toString(),
                comments = newsItem.optJSONObject("comments")?.optString("count", "-1").toString(),
                reposts = newsItem.optJSONObject("reposts")?.optString("count", "-1").toString(),
                views = newsItem.optJSONObject("views")?.optString("count", "-1").toString(),
            ))
        }
        return list
    }

    fun parseGroupsItems(groupItems: JSONArray): List<GroupsDto> {
        val list = ArrayList<GroupsDto>()
        for (i in 0 until groupItems.length()) {
            val groupItem = groupItems.getJSONObject(i)
            list.add(GroupsDto(
                id = "-" + groupItem.optString("id"),
                groupIcon = groupItem.optString("photo_100", ""),
                groupName = groupItem.optString("name", ""),
            ))
        }
        return list
    }

    fun parsePhotos(item: JSONObject): List<String>{
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

    data class NewsDto(
        val source_id: String,
        val date: String,
        val text: String,
        val photos: List<String>,
        val likes: String,
        val comments: String,
        val reposts: String,
        val views: String)
    data class GroupsDto(
        val id: String,
        val groupIcon: String,
        val groupName: String,)

    override fun onExecute(manager: VKApiManager): List<NewsModel> {
        val config = manager.config

        params["lang"] = "ru"
        params["device_id"] = config.deviceId.value
        params["v"] = config.version

        return manager.execute(VKMethodCall.Builder()
            .args(params)
            .method(method)
            .version(config.version)
            .build(), this)
    }
}