package com.bms.industry.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.exception.ServiceException;
import com.bms.common.util.JpaUtils;
import com.bms.entity.BusOnlineDataDeclare;
import com.bms.entity.BusOnlineDataDeclareAudit;
import com.bms.entity.BusOnlineDataDeclareItem;
import com.bms.industry.controller.BusOnlineDataDeclareController;
import com.bms.industry.dao.BusOnlineDataDeclareAuditRepository;
import com.bms.industry.dao.BusOnlineDataDeclareRepository;
import com.bms.industry.view.DeclareItemExcelModel;
import com.bms.sys.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 申报管理.
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusOnlineDataDeclareService {
    private final BusOnlineDataDeclareRepository busOnlineDataDeclareRepository;
    private final FlakeId flakeId;
    private final BusOnlineDataDeclareAuditRepository busOnlineDataDeclareAuditRepository;
    private final HibernateDao hibernateDao;
    private final BusOnlineDataDeclareItemService declareItemService;
    private final BusTeamService busTeamService;
    private final BusRouteService busRouteService;
    private final OrganizationService organizationService;

    private static final Logger logger = LoggerFactory.getLogger(BusOnlineDataDeclareService.class);

    public BusOnlineDataDeclare insert(BusOnlineDataDeclare declare, MultipartFile file) throws IOException, IllegalAccessException {
            declare.setId(flakeId.next());
            busOnlineDataDeclareRepository.save(declare);

        try {
            // 从excel第三行开始读取
            EasyExcel.read(file.getInputStream(), DeclareItemExcelModel.class, new BusOnlineDataDeclareService.ImportDataListener(declareItemService, busTeamService, busRouteService, organizationService, declare)).sheet().headRowNumber(2).doRead();
            return declare;
        } catch (Exception e) {
//            logger.error("import data error", e);
            if (e instanceof ServiceException) {
                throw e;
            }
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_ERR);
        }
    }

    public BusOnlineDataDeclare updateById(Long id, BusOnlineDataDeclare busOnlineDataDeclare) {
        BusOnlineDataDeclare value = this.findById(id);
        JpaUtils.copyNotNullProperties(busOnlineDataDeclare, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<BusOnlineDataDeclare> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_ONLINE_DATA_DECLARE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public BusOnlineDataDeclare findById(Long id) {
        Optional<BusOnlineDataDeclare> busOnlineDataDeclare = busOnlineDataDeclareRepository.findById(id);
        if (busOnlineDataDeclare.isPresent()) {
            return busOnlineDataDeclare.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public BusOnlineDataDeclare deleteById(Long id) {
        BusOnlineDataDeclare busOnlineDataDeclare = this.findById(id);
        busOnlineDataDeclare.setDeleted(DELETE_TRUE);
        return busOnlineDataDeclare;
    }

    public void audit(Long id, int status, String reason) {
        BusOnlineDataDeclare busOnlineDataDeclare = this.findById(id);
        busOnlineDataDeclare.setStatus(status);
        busOnlineDataDeclare.setReason(reason);

        BusOnlineDataDeclareAudit audit = new BusOnlineDataDeclareAudit();
        audit.setId(flakeId.next());
        audit.setDeclare(busOnlineDataDeclare);
        audit.setReason(reason);
        busOnlineDataDeclareAuditRepository.save(audit);
    }

    public void saveAll(List<BusOnlineDataDeclare> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        busOnlineDataDeclareRepository.saveAll(list);
    }

    @RequiredArgsConstructor
    private static class ImportDataListener extends AnalysisEventListener<DeclareItemExcelModel> {
        private static final Logger logger = LoggerFactory.getLogger(BusOnlineDataDeclareService.ImportDataListener.class);
        private static final int BATCH_COUNT = 3000;
        private List<DeclareItemExcelModel> list = new ArrayList<>();

        private final BusOnlineDataDeclareItemService declareItemService;
        private final BusTeamService busTeamService;
        private final BusRouteService busRouteService;
        private final OrganizationService organizationService;
        private final BusOnlineDataDeclare declare;

        @Override
        public void invoke(DeclareItemExcelModel data, AnalysisContext context) {
            data.setBusRouteService(busRouteService);
            data.setBusTeamService(busTeamService);
            data.setOrganizationService(organizationService);
            list.add(data);
            if (list.size() >= BATCH_COUNT) {
                saveData();
                // 存储完成清理 list
                list.clear();
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            saveData();
            logger.info("所有数据解析完成！");
        }

        private void saveData() {
            List<BusOnlineDataDeclareItem> batchData = new ArrayList<>();
            list.stream().forEach(o -> {
                BusOnlineDataDeclareItem target = new BusOnlineDataDeclareItem();
                BeanUtils.copyProperties(o, target);
                target.setDeclare(declare);
                batchData.add(target);
            });
            declareItemService.saveAll(batchData);
        }
    }

}
