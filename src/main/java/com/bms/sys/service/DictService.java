package com.bms.sys.service;

import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.config.session.SessionInfo;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.common.util.StringsUtils;
import com.bms.entity.Dictionary;
import com.bms.entity.Dictionary;
import com.bms.entity.User;
import com.bms.sys.Constant;
import com.bms.sys.dao.DictRepository;
import com.bms.sys.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;


/**
 * 字典管理.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class DictService {

    private final DictRepository dictRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;


    public Dictionary insert(Dictionary dictionary) {
        dictionary.setId(flakeId.next());
        dictRepository.save(dictionary);
        return dictionary;
    }

    public Dictionary updateById(Long id, Dictionary organization) {
        Dictionary value = this.findById(id);
        JpaUtils.copyNotNullProperties(organization, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<Dictionary> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_DICT_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public Dictionary findById(Long id) {
        Optional<Dictionary> organization = dictRepository.findById(id);
        if (organization.isPresent()) {
            return organization.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public Dictionary deleteById(Long id) {
        Dictionary organization = this.findById(id);
        organization.setDeleted(DELETE_TRUE);
        return organization;
    }

    public void status(Long id, int status) {
        Dictionary dict = this.findById(id);
        dict.setStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Dictionary> findByCode(String code) {
        return dictRepository.findByCodeOrderByIndexAsc(code);
    }

    @Transactional(readOnly = true)
    public List<Dictionary> findAll() {
        return dictRepository.findByParentIsNullOrderByIndexAsc();
    }
}
