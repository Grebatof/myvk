package com.example.myvk.domain.use_case

import com.example.myvk.domain.model.FriendModel
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
        val items = r.getJSONObject("response").getJSONArray("items")
        val groups = r.getJSONObject("response").getJSONArray("groups")
        val result = ArrayList<NewsModel>()
        for (i in 0 until items.length()) {
            if (items.optJSONObject(i).optString("type", "error") != "post")
                continue
            val item = items.getJSONObject(i)
            var group: JSONObject = groups.getJSONObject(0)
            for (j in 0 until groups.length()) {
                if (item.optString("source_id") == "-" + groups.getJSONObject(j).optString("id")) {
                    group = groups.getJSONObject(j)
                }
            }
            result.add(NewsModel.parse(item, group))
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