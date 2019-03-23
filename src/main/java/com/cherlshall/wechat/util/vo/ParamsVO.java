package com.cherlshall.wechat.util.vo;

import com.cherlshall.wechat.util.sql.annotation.Invisible;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.CaseFormat;
import lombok.Data;

@Data
public class ParamsVO {
    @Invisible
    @JsonIgnore // 返回response时忽略
    private Integer currentPage;
    @Invisible
    @JsonIgnore
    private Integer pageSize;
    @Invisible
    @JsonIgnore
    private String orderName;
    @Invisible
    @JsonIgnore
    private String orderDirection;

    @JsonIgnore
    public Integer getStartIndex() {
        if (this.currentPage == null || this.pageSize == null)
            return null;
        return (this.currentPage - 1) * this.pageSize;
    }

    @JsonIgnore
    public String getOrderName() {
        if (this.orderName == null)
            return null;
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.orderName);
    }
}
