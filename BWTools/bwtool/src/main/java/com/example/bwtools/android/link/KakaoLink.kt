package com.onedtwod.illuwa.util.link

import android.content.Context
import android.content.Intent
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.onedtwod.illuwa.model.type.LinkType

abstract class KakaoLink(private val context: Context):SnsInvitation, SnsInvitation.handle {
    companion object{
        const val ROOM_ID = "room_id"
    }
    private var linkObject: LinkObject? = null
    private var roomID:String? = null

    private fun getLinkObject():LinkObject{
        if(linkObject == null)
            linkObject = LinkObject.newBuilder()
                .setWebUrl(handleUninstallApp())
                .setMobileWebUrl(handleUninstallApp())
                .setAndroidExecutionParams(handleStartApp())
                .build()

        return linkObject!!
    }

    override fun sendInviteLink() {
        val params = FeedTemplate.newBuilder(
            ContentObject
                .newBuilder(
                    "일루와",
                    "http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg",
                    getLinkObject())
                .setDescrption("초대 메시지")
                .build())
            .build()


        KakaoLinkService.getInstance().sendDefault(context, params, object : ResponseCallback<KakaoLinkResponse>(){
            override fun onSuccess(result: KakaoLinkResponse?) {
                handleSuccessSendInviteLink()
            }

            override fun onFailure(errorResult: ErrorResult?) {
                handleFailSendInviteLink()
            }
        })
    }

    override fun getLinkType(): LinkType {
        return LinkType.Kakao
    }

    override fun roomID(roomID: String):SnsInvitation {
        this.roomID = roomID
        return this
    }

    //handle
    private fun handleUninstallApp():String {
        return "https://developers.kakao.com"
    }

    private fun handleStartApp():String{
        return "$ROOM_ID=$roomID"
    }

}