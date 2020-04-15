package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.BeanMapper;
import com.bms.entity.BusRoute;
import com.bms.entity.Organization;
import com.bms.industry.service.BusRouteService;
import com.bms.monitor.view.*;
import com.bms.sys.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 车辆运行监控.
 *
 * @author luojimeng
 * @date 2020/4/15
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class MoBusVehicleService {
    private final HibernateDao hibernateDao;
    private final OrganizationService organizationService;
    private final BusRouteService busRouteService;

    @Transactional(readOnly = true)
    public PageList<MoBusVehicleView> pageVehicleListByRouteIdList(PageRequest pageRequest, MoBusVehicleView view) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_BUS_VEHICLE_LIST_BY_ROUTE_ID_LIST, BeanMapper.toMap(view), MoBusVehicleView.class));
    }

    @Transactional(readOnly = true)
    public PageList<MoBusVehicleHistoryTrackView> pageVehicleTrackListByVehCode(String vehCode, PageRequest pageRequest, MoBusVehicleHistoryTrackView view) {
        view.setVehCode(vehCode);
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_BUS_VEHICLE_HISTORY_TRACK_LIST_BY_VEHCODE, BeanMapper.toMap(view), MoBusVehicleHistoryTrackView.class));
    }

    @Transactional(readOnly = true)
    public PageList<List<BigDecimal[]>> pageVehicleTrackPointByVehCode(String vehCode, PageRequest pageRequest, MoBusVehicleHistoryTrackView view) {
        view.setVehCode(vehCode);
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_BUS_VEHICLE_HISTORY_TRACK_POINT_BY_VEHCODE, BeanMapper.toMap(view)));
    }

    @Transactional(readOnly = true)
    public PageList<MoBusRouteView> pageRouteListByRouteIdList(PageRequest pageRequest, MoBusRouteView view) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_BUS_VEHICLE_ROUTE_LIST_BY_ROUTE_ID_LIST, BeanMapper.toMap(view), MoBusRouteView.class));
    }

    @Transactional(readOnly = true)
    public PageList<MoBusSiteView> pageSiteListByRouteIdList(PageRequest pageRequest, MoBusSiteView view) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_BUS_VEHICLE_SITE_LIST_BY_ROUTE_ID_LIST, BeanMapper.toMap(view), MoBusSiteView.class));
    }

    @Transactional(readOnly = true)
    public List<MoBusVehicleTreeView> findByVehicleMenuAll() {
        List<Organization> orgParentList = organizationService.findByParentIsNullOrderByIndexAsc();
        if (orgParentList == null || orgParentList.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, MoBusVehicleTreeView> rootMap = new HashMap<>();
        List<MoBusVehicleTreeView> rootList = new ArrayList<>();

        /**
         * 公司 第一级.
         */
        List<Long> orgParentIdList = new ArrayList<>();
        orgParentList.stream().forEach(o -> {
            orgParentIdList.add(o.getId());
            MoBusVehicleTreeView tree = copyOrg2TreeView(o);
            tree.setType(MoBusVehicleTreeView.MENU_TYPE_ORG);
            rootList.add(tree);
            rootMap.put(o.getId(), tree);
        });

        buildRouteTree(rootMap, orgParentIdList);

        /**
         * 公司 第二级.
         */
        List<Long> orgParentIdLevel2List = buildOrgTree(orgParentIdList, rootMap);
        if (!orgParentIdLevel2List.isEmpty()) {
            buildRouteTree(rootMap, orgParentIdLevel2List);
        }

        /**
         * 公司 第三级.
         */
        List<Long> orgParentIdLevel3List = buildOrgTree(orgParentIdLevel2List, rootMap);
        if (!orgParentIdLevel3List.isEmpty()) {
            buildRouteTree(rootMap, orgParentIdLevel3List);
        }
        return rootList;
    }

    private MoBusVehicleTreeView copyOrg2TreeView(Organization org) {
        MoBusVehicleTreeView tree = new MoBusVehicleTreeView();
        BeanUtils.copyProperties(org, tree, "children");
        return tree;
    }

    private List<Long> buildOrgTree(List<Long> orgParentIdList, Map<Long, MoBusVehicleTreeView> rootMap) {
        List<Organization> orgList = organizationService.findByParentIdInOrderByIndexAsc(orgParentIdList);
        List<Long> orgParentIdLevel2List = new ArrayList<>();
        if (orgList != null && !orgList.isEmpty()) {
            orgList.stream().forEach(o -> {
                orgParentIdLevel2List.add(o.getId());
                MoBusVehicleTreeView tree = copyOrg2TreeView(o);
                tree.setType(MoBusVehicleTreeView.MENU_TYPE_ORG);

                MoBusVehicleTreeView parentTree = rootMap.get(o.getParentId());
                if (parentTree.getChildren() == null) {
                    parentTree.setChildren(new ArrayList<>());
                }
                parentTree.getChildren().add(tree);
                rootMap.put(o.getId(), tree);
            });
        }
        return orgParentIdLevel2List;
    }

    /**
     * 构建线路树结构.
     */
    private void buildRouteTree(Map<Long, MoBusVehicleTreeView> rootMap, List<Long> orgParentIdList) {
        List<BusRoute> routeList = busRouteService.findByOrgIdList(orgParentIdList);
        if (routeList != null && !routeList.isEmpty()) {
            routeList.stream().forEach(route -> {
                MoBusVehicleTreeView tree = new MoBusVehicleTreeView();
                BeanUtils.copyProperties(route, tree);
                tree.setType(MoBusVehicleTreeView.MENU_TYPE_ROUTE);
                tree.setParentId(route.getOrgId());

                MoBusVehicleTreeView parentTree = rootMap.get(route.getOrgId());
                if (parentTree.getChildren() == null) {
                    parentTree.setChildren(new ArrayList<>());
                }
                parentTree.getChildren().add(tree);
            });
        }
    }
}
