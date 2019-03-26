package com.cherlshall.wechat.util.sql;

import com.cherlshall.wechat.mapper.NpcMapper;
import com.cherlshall.wechat.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MapperUtil {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NpcMapper npcMapper;

    private static MapperUtil mapperUtil;

    @PostConstruct
    public void init() {
        mapperUtil = this;
    }

    public static MapperUtil getInstance() {
        return mapperUtil;
    }

    public UserMapper getUserMapper() {
        return this.userMapper;
    }

    public  NpcMapper getNpcMapper() {
        return this.npcMapper;
    }
}
