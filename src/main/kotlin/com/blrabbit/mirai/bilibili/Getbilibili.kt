package com.blrabbit.mirai.setu

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class BilibiliTimeline(
    val code: Int = 0,
    val message: String = "",
    val result: List<Result> = emptyList()
)

@Serializable
data class Result(
    val date: String = "",
    val date_ts: Long = 0,
    val day_of_week: Int = 0,
    val is_today: Int = 0,
    val seasons: List<Season> = listOf()
)

@Serializable
data class Season(
    val cover: String = "",
    val delay: Int = 0,
    val delay_id: Int = 0,
    val delay_index: String = "",
    val delay_reason: String = "",
    val ep_id: Int = 0,
    val favorites: Int = 0,
    val follow: Int = 0,
    val is_published: Int = 0,
    val pub_index: String = "",
    val pub_time: String = "",
    val pub_ts: Long = 0,
    val season_id: Int = 0,
    val season_status: Int = 0,
    val square_cover: String = "",
    val title: String = "",
    val url: String = ""
)

@KtorExperimentalAPI
suspend fun bangumi_timeline(title: String, i: Int): String {
    var json = ""
    try {
        json = HttpClient().use { client ->
            client.get<String>("https://bangumi.bilibili.com/web_api/timeline_global")
        }
    } catch (e: Exception) {
        return e.toString()
    }
    val result = Json.decodeFromString<BilibiliTimeline>(json)
    var msg = "$title\n"
    for (index in result.result[i].seasons) {
        msg += "---------------\n"
        msg += "${index.title} ${index.pub_index}\n更新时间:${index.pub_time}"
        msg += if (index.delay == 0) {
            if (index.is_published == 1)
                "(已更新)\n"
            else
                "(未更新)\n"
        } else {
            "(${index.delay_reason})\n"
        }
    }
    return msg
}

suspend fun bangumi_timeline_cn(title: String, i: Int): String {
    var json = ""
    try {
        json = HttpClient().use { client ->
            client.get<String>("https://bangumi.bilibili.com/web_api/timeline_cn")
        }
    } catch (e: Exception) {
        return e.toString()
    }
    val result = Json.decodeFromString<BilibiliTimeline>(json)
    var msg = "$title\n"
    for (index in result.result[i].seasons) {
        msg += "---------------\n"
        msg += "${index.title} ${index.pub_index}\n更新时间:${index.pub_time}"
        msg += if (index.delay == 0) {
            if (index.is_published == 1)
                "(已更新)\n"
            else
                "(未更新)\n"
        } else {
            "(${index.delay_reason})\n"
        }
    }
    return msg
}