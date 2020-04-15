package com.bms.monitor.view;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 车辆运行检测-左侧树结构.
 *
 * @author zouyongcan
 * @date 2020/4/13
 */
@Data
public class MoBusVehicleTreeView implements Serializable {
    /**
     * 1:类型(公司)
     */
    public static final Integer MENU_TYPE_ORG = 1;
    /**
     * 2:类型(线路)
     */
    public static final Integer MENU_TYPE_ROUTE = 2;
    /**
     * 3:类型(场站)
     */
    public static final Integer MENU_TYPE_TERMINAL = 3;

    /**
     * 菜单ID
     */
    private Long id;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 顺序
     */
    private Integer index;
    /**
     * 类型(1=公司 2=线路 3=场站)
     */
    private Integer type;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 子菜单-子公司或场站信息
     */
    private List<MoBusVehicleTreeView> children;
}
