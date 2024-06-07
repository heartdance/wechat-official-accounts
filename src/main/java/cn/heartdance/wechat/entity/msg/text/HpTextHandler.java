package cn.heartdance.wechat.entity.msg.text;

import cn.heartdance.wechat.entity.po.Npc;
import cn.heartdance.wechat.mapper.NpcMapper;
import cn.heartdance.wechat.util.wechat.SendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HpTextHandler extends TextHandler {

    @Autowired
    private NpcMapper npcMapper;

    public HpTextHandler() {
        super("血量", "hp", "HP");
    }

    public HpTextHandler(NpcMapper npcMapper, String... keywords) {
        super(keywords);
        this.npcMapper = npcMapper;
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        Npc npc = npcMapper.getNpcById(1);
        return SendUtil.sendTextMsg(requestMap, "周建云剩余血量：" + npc.getHp());
    }
}
