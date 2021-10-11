package com.example.myvk.domain.use_case

import com.example.myvk.domain.model.GroupModel
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGroupsRequest() : VKRequest<List<GroupModel>>("newsfeed.get") {
    init {
        //addParam(name = "fields", value = "name")
    }

    override fun parse(r: JSONObject): List<GroupModel> {
        val groupItems = r.getJSONObject("response").getJSONArray("groups")
        val result = ArrayList<GroupModel>()
        for (i in 0 until groupItems.length()) {
            result.add(GroupModel.parse(groupItems.getJSONObject(i)))
            continue
        }
        return result
    }

    override fun onExecute(manager: VKApiManager): List<GroupModel> {
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