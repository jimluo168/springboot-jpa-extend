package com.bms.common.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Page分页请求模型.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
public class PageRequest implements Serializable {

    private final int page;
    private final int size;

    public PageRequest(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        }

        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }

        this.page = page;
        this.size = size;
    }

    public int getPageNumber() {
        return page;
    }

    public int getPageSize() {
        return size;
    }

    public int getOffset() {
        return (page - 1) * size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageRequest that = (PageRequest) o;
        return page == that.page &&
                size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, size);
    }
}
