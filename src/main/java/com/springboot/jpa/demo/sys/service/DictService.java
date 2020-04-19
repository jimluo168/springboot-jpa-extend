package com.springboot.jpa.demo.sys.service;

import com.springboot.jpa.demo.ErrorCodes;
import com.springboot.jpa.demo.core.config.flake.FlakeId;
import com.springboot.jpa.demo.core.domain.PageList;
import com.springboot.jpa.demo.core.domain.PageRequest;
import com.springboot.jpa.demo.core.util.JpaUtils;
import com.springboot.jpa.demo.entity.Dictionary;
import com.springboot.jpa.demo.sys.dao.DictMapper;
import com.springboot.jpa.demo.sys.dao.DictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.springboot.jpa.demo.core.domain.BaseEntity.DELETE_TRUE;


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
    //    private final HibernateDao hibernateDao;
    private final DictMapper dictMapper;


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
    public PageList<Dictionary> page(PageRequest pageRequest, Dictionary dictionary) {
//        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_DICT_PAGE, queryParams));
        return dictMapper.findAll(pageRequest, dictionary);
    }

    @Transactional(readOnly = true)
    public Dictionary findById(Long id) {
        Optional<Dictionary> dictionary = dictRepository.findById(id);
        if (dictionary.isPresent()) {
            return dictionary.get();
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

    /**
     * 根据字典编码获取该字典下的所有的子节点，并且按照index进行排序.
     *
     * @param code 字典编码
     * @return List&lt;Dictionary&gt;
     */
    @Transactional(readOnly = true)
    public List<Dictionary> findByCode(String code) {
        Optional<Dictionary> dictionary = dictRepository.findByCodeOrderByIndexAsc(code);
        if (dictionary.isPresent()) {
            Dictionary dict = dictionary.get();
            return dict.getChildren();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    @Transactional(readOnly = true)
    public List<Dictionary> findAll() {
        return dictRepository.findByParentIsNullOrderByIndexAsc();
    }
}
