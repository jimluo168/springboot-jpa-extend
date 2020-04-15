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
    /**
     * 最大支持页码大小 4000条记录.
     */
    public static final int MAX_PAGE_SIZE = 4000;

    private final int page;
    private final int size;

    public PageRequest(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("page index must not be less than zero!");
        }

        if (size < 1) {
            throw new IllegalArgumentException("page size must not be less than one!");
        }
//
//        if (size > MAX_PAGE_SIZE) {
//            throw new IllegalArgumentException("page size must not be greater than" + MAX_PAGE_SIZE);
//        }

        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
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
