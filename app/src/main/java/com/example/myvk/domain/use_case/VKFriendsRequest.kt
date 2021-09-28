package com.example.myvk.domain.use_case

import com.example.myvk.domain.model.FriendModel
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKFriendsRequest: VKRequest<List<FriendModel>> {
    constructor(): super("friends.get") {
        addParam(name = "fields", value = "firstName")
        addParam(name = "fields", value = "last_name")
        addParam(name = "fields", value = "photo_100")
    }

    override fun parse(r: JSONObject): List<FriendModel> {
        val users = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<FriendModel>()
        for (i in 0 until users.length()) {
            result.add(FriendModel.parse(users.getJSONObject(i)))
        }
        return result
    }

    override fun onExecute(manager: VKApiManager): List<FriendModel> {
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