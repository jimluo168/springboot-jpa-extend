package com.bms.industry.dao;

import com.bms.entity.Practitioner;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 从业人员 DAO.
 *
 * @author zouyongcan
 * @date 2020/3/16
 */
@Repository
public interface PractitionerRepository extends PagingAndSortingRepository<Practitioner, Long> {
    Practitioner findByName(String name);

    int countByStaffNumberAndDeleted(String staffNumber, int deleted);

    int countByStaffNumberAndIdNotAndDeleted(String staffNumber, Long id, int deleted);

    int countByIdNumberAndDeleted(String idNumber, int deleted);

    int countByIdNumberAndIdNotAndDeleted(String idNumber, Long id, int deleted);
}
