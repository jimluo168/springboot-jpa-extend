package com.bms.common.domain;

import java.util.List;

/**
 * 分页信息.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
public class PageList<T> {
    private long count;
    private List<T> list;

    public PageList(long count, List<T> list) {
        this.count = count;
        this.list = list;
    }

    public long getCount() {
        return count;
    }

    public PageList<T> setCount(long count) {
        this.count = count;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public PageList<T> setList(List<T> list) {
        this.list = list;
        return this;
    }
}
