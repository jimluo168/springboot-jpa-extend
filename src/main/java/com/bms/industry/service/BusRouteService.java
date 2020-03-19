package com.bms.industry.service;

import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.BusRoute;
import com.bms.entity.BusRouteAudit;
import com.bms.industry.dao.BusRouteAuditRepository;
import com.bms.industry.dao.BusRouteRepository;
import com.bms.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 公交路线管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusRouteService {

    private final BusRouteRepository busRouteRepository;
    private final FlakeId flakeId;
    private final BusRouteAuditRepository busRouteAuditRepository;
    private final HibernateDao hibernateDao;

    public BusRoute insert(BusRoute busRoute) {
        busRoute.setId(flakeId.next());
        busRouteRepository.save(busRoute);
        return busRoute;
    }

    public BusRoute updateById(Long id, BusRoute busRoute) {
        BusRoute value = this.findById(id);
        JpaUtils.copyNotNullProperties(busRoute, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<BusRoute> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_BUS_ROUTE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public BusRoute findById(Long id) {
        Optional<BusRoute> busRoute = busRouteRepository.findById(id);
        if (busRoute.isPresent()) {
            return busRoute.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public BusRoute deleteById(Long id) {
        BusRoute busRoute = this.findById(id);
        busRoute.setDeleted(DELETE_TRUE);
        return busRoute;
    }

    public void audit(Long id, int status, String reason) {
        BusRoute busRoute = this.findById(id);
        busRoute.setStatus(status);
        busRoute.setReason(reason);

        BusRouteAudit audit = new BusRouteAudit();
        audit.setId(flakeId.next());
        audit.setBusRoute(busRoute);
        audit.setReason(reason);
        busRouteAuditRepository.save(audit);
    }

    public void saveAll(List<BusRoute> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        busRouteRepository.saveAll(list);
    }
}
