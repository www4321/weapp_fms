package com.bupt.service.service.bill.bean;

import java.io.Serializable;
import java.math.BigDecimal;
// 账单列表，controller层返回前端的数据结构
public class BillDto implements Serializable {
    private Long id;
    private String time;
    private String type;
    private String desc;
    private BigDecimal money;
    private Integer status;

    public BillDto(Long id, String time, String type, String desc, BigDecimal money, Integer status) {
        this.id = id;
        this.time = time;
        this.type = type;
        this.desc = desc;
        this.money = money;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
