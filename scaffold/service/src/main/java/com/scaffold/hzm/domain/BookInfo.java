package com.scaffold.hzm.domain;

import lombok.Data;

@Data
public class BookInfo {
    private String beginTime;
    String bookDate;
    String endTime;
    Integer maxPeople;
    Integer saleStatus;
    Integer totalPeople;
    String lineCode;
    String feeType;
    String maxBookDate;
    String ticketCategory;
}
