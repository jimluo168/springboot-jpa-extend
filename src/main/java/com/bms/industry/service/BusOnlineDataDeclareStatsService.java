package com.bms.industry.service;

import com.bms.Constant;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.entity.BusOnlineDataDeclare;
import com.bms.entity.BusOnlineDataDeclareItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * 统计数据表.
 *
 * @author luojimeng
 * @date 2020/3/30
 */
@Service
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusOnlineDataDeclareStatsService {
    private final HibernateDao hibernateDao;

    public BusOnlineDataDeclareItem carbon() {
        BusOnlineDataDeclareItem item = hibernateDao.getSingle(new DaoCmd(Constant.MAPPER_BUS_ONLINE_DATA_DECLARE_STATS, null, BusOnlineDataDeclareItem.class));
        if (item == null) {
            item = new BusOnlineDataDeclareItem();
            item.setGasQuantity(new BigDecimal(0.0));
            item.setDieselOilQuantity(new BigDecimal(0.0));
            item.setNaturalGasQuantity(new BigDecimal(0.0));
            item.setElectricQuantity(new BigDecimal(0.0));
            return item;
        }
        item.setGasQuantity(Optional.ofNullable(item.getGasQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(2.7)));
        item.setDieselOilQuantity(Optional.ofNullable(item.getDieselOilQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(3.115)));
        item.setNaturalGasQuantity(Optional.ofNullable(item.getNaturalGasQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(1.96)));
        item.setElectricQuantity(Optional.ofNullable(item.getElectricQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(0.997)));
        return item;
    }

    public BusOnlineDataDeclareItem cutEmissions(Map<String, Object> params) {
        BusOnlineDataDeclareItem item = hibernateDao.getSingle(new DaoCmd(Constant.MAPPER_BUS_ONLINE_DATA_DECLARE_STATS, params, BusOnlineDataDeclareItem.class));
        if (item == null) {
            item = new BusOnlineDataDeclareItem();
            item.setGasQuantity(new BigDecimal(0.0));
            item.setDieselOilQuantity(new BigDecimal(0.0));
            item.setNaturalGasQuantity(new BigDecimal(0.0));
            item.setElectricQuantity(new BigDecimal(0.0));
            return item;
        }
        item.setGasQuantity(Optional.ofNullable(item.getGasQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(2.7)));
        item.setDieselOilQuantity(Optional.ofNullable(item.getDieselOilQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(3.115)));
        item.setNaturalGasQuantity(Optional.ofNullable(item.getNaturalGasQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(1.96)));
        item.setElectricQuantity(Optional.ofNullable(item.getElectricQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(0.997)));
        return item;
    }
}
