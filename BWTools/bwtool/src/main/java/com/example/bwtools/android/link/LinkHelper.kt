package com.onedtwod.illuwa.util.link

import android.content.Context
import android.os.Handler
import android.util.Log
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.onedtwod.illuwa.interfaces.module.InvitationInterface
import com.onedtwod.illuwa.model.type.LinkType

class LinkHelper(private val context: Context):SnsInvitation{
    private var roomID:String? = null
    var successRunnable:Runnable? = null
    var failureRunnable:Runnable? = null

    private val linkObjectList by lazy {
        arrayOf(object :KakaoLink(context){
            private val handler = Handler()

            override fun handleFailSendInviteLink() {
                failureRunnable?.let {
                    handler.post(it)
                }
            }

            override fun handleSuccessSendInviteLink() {
                successRunnable?.let {
                    handler.post(it)
                }
            }
        })
    }

    private var linkType:LinkType = LinkType.None

    override fun sendInviteLink() {
        if(roomID == null){
            showRoomIdNullError()
            return
        }

        for(linkObject in linkObjectList){
            if(linkObject.getLinkType() == linkType){
                linkObject.roomID(roomID!!)
                linkObject.sendInviteLink()
            }
        }
    }

    override fun getLinkType(): LinkType {
        return linkType
    }

    fun setLinkType(type: LinkType):SnsInvitation{
        linkType = type
        return this
    }

    override fun roomID(roomID: String):SnsInvitation {
        this.roomID = roomID
        return this
    }

    private fun showRoomIdNullError(){
        Log.e("LinkHelper", "Roomid Error")
    }
}