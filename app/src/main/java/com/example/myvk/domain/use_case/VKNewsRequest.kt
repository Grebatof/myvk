package com.example.myvk.domain.use_case

import com.example.myvk.domain.model.NewsModel
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKNewsRequest() : VKRequest<List<NewsModel>>("newsfeed.get") {
    init {
        addParam(name = "fields", value = "date,text")
    }

    override fun parse(r: JSONObject): List<NewsModel> {
        val newsItems = r.getJSONObject("response").getJSONArray("items")
        val groupItems = r.getJSONObject("response").getJSONArray("groups")
        val result = ArrayList<NewsModel>()
        // Проходимся по массиву новостей
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
        }
        return result
    }

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