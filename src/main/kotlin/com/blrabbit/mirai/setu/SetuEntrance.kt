package com.blrabbit.mirai.setu

import com.blrabbit.mirai.Util.storge.Mydata
import com.blrabbit.mirai.Util.checkpower
import com.blrabbit.mirai.Util.storge.Command
import com.blrabbit.mirai.Util.storge.Message
import io.ktor.util.*
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.nextMessage

@KtorExperimentalAPI
fun SetuEntrance() {
    GlobalEventChannel.subscribeGroupMessages {
        //色图时间
        always {

            if (Command.setucommand.command_get.contains(message.contentToString())) {
                if (Mydata.Grouppower[group.id] != null) {
                    val setu = SetuImage(group)
                    setu.getsetu()
                    setu.getstr()
                    setu.sendsetu()
                } else {
                    group.sendMessage(Message.setumodeoff)
                }
            }
        }
        //搜色图
        always {
            Command.setucommand.command_search.startWith(message.contentToString()).let {
                if (it != null) {
                    if (Mydata.Grouppower[group.id] != null) {
                        if (it.isNotEmpty()) {
                            val setu = SetuImage(group)
                            setu.getsetu(it)
                            setu.getstr()
                            setu.sendsetu()
                        } else {
                            group.sendMessage(Message.setusearchkey)
                            val msg = nextMessage()
                            val setu = SetuImage(group)
                            setu.getsetu(msg.contentToString())
                            setu.getstr()
                            setu.sendsetu()
                        }
                    } else {
                        group.sendMessage(Message.setumodeoff)
                    }
                }
            }
        }

        always {
            if (Command.setucommand.command_off.contains(message.contentToString())) {
                if (checkpower(sender)) {
                    if (Mydata.Grouppower[group.id] == null) {
                        group.sendMessage(Message.setumode_1to_1)
                    } else {
                        group.sendMessage(Message.setumode_1)
                        Mydata.Grouppower.remove(group.id)
                    }
                } else {
                    group.sendMessage(Message.setunopermission)
                }
            }

            if (Command.setucommand.command_setumode0.contains(message.contentToString())) {
                if (checkpower(sender)) {
                    if (Mydata.Grouppower[group.id] == 0) {
                        group.sendMessage(Message.setumode0to0)
                    } else {
                        group.sendMessage(Message.setumode0)
                        Mydata.Grouppower[group.id] = 0
                    }
                } else {
                    group.sendMessage(Message.setunopermission)
                }
            }

            if (Command.setucommand.command_setumode1.contains(message.contentToString())) {
                if (checkpower(sender)) {
                    if (Mydata.Grouppower[group.id] == 1) {
                        group.sendMessage(Message.setumode1to1)
                    } else {
                        group.sendMessage(Message.setumode1)
                        Mydata.Grouppower[group.id] = 1
                    }
                } else {
                    group.sendMessage(Message.setunopermission)
                }
            }

            if (Command.setucommand.command_setumode2.contains(message.contentToString())) {
                if (checkpower(sender)) {
                    if (Mydata.Grouppower[group.id] == 2) {
                        group.sendMessage(Message.setumode2to2)
                    } else {
                        group.sendMessage(Message.setumode2)
                        Mydata.Grouppower.put(group.id, 2)
                    }
                } else {
                    group.sendMessage(Message.setunopermission)
                }
            }
        }
    }
}

private fun MutableList<String>.startWith(contentToString: String): String? {
    this.forEach {
        if (contentToString.startsWith(it)) {
            return contentToString.replace(it, "").replace(" ", "")
        }
    }
    return null
}
