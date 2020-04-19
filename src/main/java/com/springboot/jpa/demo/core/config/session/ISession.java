package com.springboot.jpa.demo.core.config.session;

import java.io.Serializable;
import java.util.Set;

/**
 * 会话接口.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
public interface ISession {
    /**
     * 获取Session ID.
     *
     * @return
     */
    String getSessionId() throws SessionException;

    /**
     * 获取会话的属性.
     *
     * @param attrName 属性名称
     * @return
     */
    Serializable getAttribute(String attrName) throws SessionException;

    /**
     * 可以通过设置返回类型，而获取属性.
     *
     * @param attrName
     * @param type
     * @return
     */
    <T extends Serializable> T getAttribute(String attrName, Class<T> type) throws SessionException;

    /**
     * 删除会话的属性
     *
     * @param attrName
     * @return
     */
    boolean removeAttribute(String attrName) throws SessionException;

    /**
     * 设置属性，会话的属性都有有效期，这个方法将使用系统默认的有效期设置.
     *
     * @param attrName
     * @param value
     */
    void setAttribute(String attrName, Serializable value) throws SessionException;

    /**
     * 设置属性，并设置会话的有效期时间，单位为秒.
     *
     * @param attrName
     * @param value
     * @param expireSeconds
     */
    void setAttribute(String attrName, Serializable value, int expireSeconds) throws SessionException;

    /**
     * 获取会话中所有的属性名称.
     *
     * @return
     * @throws SessionException
     */
    Set<String> getAllAttributeKeys() throws SessionException;
}
