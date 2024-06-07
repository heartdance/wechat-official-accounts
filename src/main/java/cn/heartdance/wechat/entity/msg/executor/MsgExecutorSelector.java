package cn.heartdance.wechat.entity.msg.executor;

import cn.heartdance.wechat.util.wechat.WeChatConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MsgExecutorSelector {

    @Autowired
    private TextMsgExecutor textMsgStrategy;

    @Autowired
    private SubscribeMsgExecutor subscribeMsgStrategy;

    public MsgExecutor selectMsgExecutor(Map<String, String> requestMap) {
        String type = requestMap.get(WeChatConstant.MSG_TYPE);
        switch (type) {
            case WeChatConstant.REQ_MESSAGE_TYPE_TEXT:
                return textMsgStrategy;
            case WeChatConstant.REQ_MESSAGE_TYPE_IMAGE:
                return new ImageMsgExecutor();
            case WeChatConstant.REQ_MESSAGE_TYPE_VOICE:
                return new VoiceMsgExecutor();
            case WeChatConstant.REQ_MESSAGE_TYPE_VIDEO:
                return new VideoMsgExecutor();
            case WeChatConstant.REQ_MESSAGE_TYPE_LOCATION:
                return new LocationMsgExecutor();
            case WeChatConstant.REQ_MESSAGE_TYPE_LINK:
                return new LinkMsgExecutor();
            // 事件推送
            case WeChatConstant.REQ_MESSAGE_TYPE_EVENT:
                // 事件类型
                String eventType = requestMap.get(WeChatConstant.EVENT);
                switch (eventType) {
                    case WeChatConstant.EVENT_TYPE_SUBSCRIBE:
                        return subscribeMsgStrategy;
                    case WeChatConstant.EVENT_TYPE_UNSUBSCRIBE:
                        return new UnsubscribeMsgExecutor();
                    case WeChatConstant.EVENT_TYPE_SCAN:
                        return new ScanMsgExecutor();
                    case WeChatConstant.EVENT_TYPE_LOCATION:
                        return new ReportLocationMsgExecutor();
                    case WeChatConstant.EVENT_TYPE_CLICK:
                        return new ClickMsgExecutor();
                }
                break;
        }
        return new UnknowMsgExecutor();
    }
}
