package com.springboot.jpa.demo;

/**
 * 字典编码常量.
 *
 * @author luojimeng
 * @date 2020/3/20
 */
public interface DictConstant {
    /**
     * 字典的子节点 约定俗成 后缀必须是_CHILD.
     */
    String CHILD_NODE = "_CHILD";

    static String buildChildType(String type) {
        return type + CHILD_NODE;
    }
}
