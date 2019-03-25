package com.cherlshall.wechat.entity.msg.strategy;

import com.cherlshall.wechat.util.wechat.WeChatConstant;

import java.util.Map;

public class MsgStrategyFactory {

    public static MsgStrategy getReceiveMsg(Map<String, String> requestMap) {
        String type = requestMap.get(WeChatConstant.MSG_TYPE);
        switch (type) {
            case WeChatConstant.REQ_MESSAGE_TYPE_TEXT:
                return new TextMsgStrategy();
            case WeChatConstant.REQ_MESSAGE_TYPE_IMAGE:
                return new ImageMsgStrategy();
            case WeChatConstant.REQ_MESSAGE_TYPE_VOICE:
                return new VoiceMsgStrategy();
            case WeChatConstant.REQ_MESSAGE_TYPE_VIDEO:
                return new VideoMsgStrategy();
            case WeChatConstant.REQ_MESSAGE_TYPE_LOCATION:
                return new LocationMsgStrategy();
            case WeChatConstant.REQ_MESSAGE_TYPE_LINK:
                return new LinkMsgStrategy();
            // 事件推送
            case WeChatConstant.REQ_MESSAGE_TYPE_EVENT:
                // 事件类型
                String eventType = requestMap.get(WeChatConstant.EVENT);
                switch (eventType) {
                    case WeChatConstant.EVENT_TYPE_SUBSCRIBE:
                        return new SubscribeMsgStrategy();
                    case WeChatConstant.EVENT_TYPE_UNSUBSCRIBE:
                        return new UnsubscribeMsgStrategy();
                    case WeChatConstant.EVENT_TYPE_SCAN:
                        return new ScanMsgStrategy();
                    case WeChatConstant.EVENT_TYPE_LOCATION:
                        return new ReportLocationMsgStrategy();
                    case WeChatConstant.EVENT_TYPE_CLICK:
                        return new ClickMsgStrategy();
                }
                break;
        }
        return new UnknowMsgStrategy();
    }
}
