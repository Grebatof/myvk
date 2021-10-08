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

        for (i in listNewsItems.indices) {
            listGroupsItems.find {
                it.id == listNewsItems[i].id
            }?.let { NewsModel.parse(listNewsItems[i].JSONObject, it.JSONObject) }?.let {
                result.add(it)
            }
        }

        /*// Проходимся по массиву новостей
        for (i in 0 until newsItems.length()) {
            // Если новость не является "постом", то идем дальше по массиву
            if (newsItems.optJSONObject(i).optString("type", "error") != "post")
                continue
            val newsItem = newsItems.getJSONObject(i)
            var groupItem: JSONObject
            // Проходимся по массиву групп
            for (j in 0 until groupItems.length()) {
                // Ищем совпадения индекса новости и индекса группы
                if (newsItem.optString("source_id") == "-" + groupItems.getJSONObject(j).optString("id")) {
                    groupItem = groupItems.getJSONObject(j)
                    // Добавляем новость в результат
                    result.add(NewsModel.parse(newsItem, groupItem))
                    continue
                }
            }
        }*/
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
                id = newsItem.optString("source_id"),
                JSONObject = newsItem,
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
                JSONObject = groupItem,
            ))
        }
        return list
    }

    data class NewsDto(val id: String, val JSONObject: JSONObject)
    data class GroupsDto(val id: String, val JSONObject: JSONObject)

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