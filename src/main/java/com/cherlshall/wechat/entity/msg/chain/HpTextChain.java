package com.cherlshall.wechat.entity.msg.chain;

import com.cherlshall.wechat.entity.mysql.Npc;
import com.cherlshall.wechat.mapper.NpcMapper;
import com.cherlshall.wechat.util.sql.MapperUtil;
import com.cherlshall.wechat.util.wechat.SendUtil;

import java.util.Map;

public class HpTextChain extends AbstractTextChain {

    public HpTextChain() {
        super("血量", "hp", "HP");
    }

    public HpTextChain(String... keywords) {
        super(keywords);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        MapperUtil mapperUtil = MapperUtil.getInstance();
        NpcMapper npcMapper = mapperUtil.getNpcMapper();
        Npc npc = npcMapper.getNpcById(1);
        return SendUtil.sendTextMsg(requestMap, "周建云剩余血量：" + npc.getHp());
    }
}
