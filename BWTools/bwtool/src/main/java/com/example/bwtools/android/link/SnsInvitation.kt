package com.onedtwod.illuwa.util.link

import com.onedtwod.illuwa.model.type.LinkType

interface SnsInvitation {
    fun getLinkType():LinkType
    fun roomID(roomID:String):SnsInvitation
    fun sendInviteLink()

    interface handle{
        fun handleFailSendInviteLink()
        fun handleSuccessSendInviteLink()
    }

}