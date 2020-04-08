package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.MoRescueMaterial;
import com.bms.monitor.dao.RescueMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_FALSE;
import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 救援资源管理-车辆.
 *
 * @author luojimeng
 * @date 2020/4/7
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class RescueMaterialService {

    private final RescueMaterialRepository rescueMaterialRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public MoRescueMaterial insert(MoRescueMaterial moRescueMaterial) {
        moRescueMaterial.setId(flakeId.next());
        rescueMaterialRepository.save(moRescueMaterial);
        return moRescueMaterial;
    }

    public MoRescueMaterial updateById(Long id, MoRescueMaterial moRescueMaterial) {
        MoRescueMaterial value = this.findById(id);
        JpaUtils.copyNotNullProperties(moRescueMaterial, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<MoRescueMaterial> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_MATERIAL_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public PageList<MoRescueMaterial> orgnamePage(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_MATERIAL_ORGNAME_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public PageList<MoRescueMaterial> codePage(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_MATERIAL_CODE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public PageList<MoRescueMaterial> typePage(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_MATERIAL_TYPE_PAGE, queryParams));
    }


    @Transactional(readOnly = true)
    public PageList<MoRescueMaterial> originPage(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_MATERIAL_ORIGIN_PAGE, queryParams));
    }



    @Transactional(readOnly = true)
    public MoRescueMaterial findById(Long id) {
        Optional<MoRescueMaterial> moRescueMaterial = rescueMaterialRepository.findById(id);
        if (moRescueMaterial.isPresent()) {
            return moRescueMaterial.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public MoRescueMaterial deleteById(Long id) {
        MoRescueMaterial moRescueMaterial = this.findById(id);
        moRescueMaterial.setDeleted(DELETE_TRUE);
        return moRescueMaterial;
    }

    public void audit(Long id, int status, String reason) {
        MoRescueMaterial moRescueMaterial = this.findById(id);
        moRescueMaterial.setStatus(status);
        moRescueMaterial.setReason(reason);
    }

    @Transactional(readOnly = true)
    public boolean existsByCode(String name, Long id) {
        if (id == null) {
            return rescueMaterialRepository.countByCodeAndDeleted(name, DELETE_FALSE) > 0;
        }
        return rescueMaterialRepository.countByCodeAndIdNotAndDeleted(name, id, DELETE_FALSE) > 0;
    }

    public void scrap(Long id) {
        MoRescueMaterial value = this.findById(id);
        value.setStatus(MoRescueMaterial.STATUS_SCRAP);
    }
}
