package com.springboot.jpa.demo.sys.dao;

import com.springboot.jpa.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户管理.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByAccount(String account);

    Page<User> findByAccountOrRealNameLike(Pageable pageable, String account, String nickName);

    int countByAccount(String account);
}
