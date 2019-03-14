package com.bupt.service.service.bill.bean;

import java.math.BigDecimal;

// 数据库层orm的映射，有数据层返回到service层
public class BillStatistics {
    private String type;

    private Byte status;

    private Integer num;

    private BigDecimal sum;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
