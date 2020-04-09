package com.bms.industry.sync.busbasic.view;

import lombok.Data;

import java.io.Serializable;

/**
 * 贵阳公交返回的数据结构.
 *
 * @author luojimeng
 * @date 2020/4/9
 */
@Data
public class BusBasicResult implements Serializable {
    private boolean success;
    private String message;
    private int code;
    private long timestamp;
    private String result;
}
