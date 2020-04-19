package com.springboot.jpa.demo.sys.service;

import com.springboot.jpa.demo.ErrorCodes;
import com.springboot.jpa.demo.core.config.flake.FlakeId;
import com.springboot.jpa.demo.core.config.session.SessionInfo;
import com.springboot.jpa.demo.core.domain.BaseEntity;
import com.springboot.jpa.demo.core.domain.PageList;
import com.springboot.jpa.demo.core.domain.PageRequest;
import com.springboot.jpa.demo.core.util.JpaUtils;
import com.springboot.jpa.demo.core.util.StringsUtils;
import com.springboot.jpa.demo.entity.User;
import com.springboot.jpa.demo.sys.dao.UserMapper;
import com.springboot.jpa.demo.sys.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * @author luojimeng
 * @date 2020/3/9
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FlakeId flakeId;
    private final UserMapper userMapper;

    public SessionInfo loginValidate(String account, String passwd) {
        User user = userRepository.findByAccount(account);
        if (user == null) {
            throw ErrorCodes.build(ErrorCodes.ACCOUNT_NOT_EXIST);
        }
        if (user.getStatus() == User.STATUS_DISABLE) {
            throw ErrorCodes.build(ErrorCodes.USER_STATUS_DISABLED);
        }
        String encryptPasswd = StringsUtils.sha256Hex(passwd, user.getSalt(), Long.toString(user.getCreateDate().getTime()));
        if (!StringUtils.equals(encryptPasswd, user.getPasswd())) {
            throw ErrorCodes.build(ErrorCodes.PASSWD_ERR);
        }
        SessionInfo info = new SessionInfo();
        info.setId(user.getId());
        info.setAccount(user.getAccount());
        info.setName(user.getRealName());
        return info;
    }

    public User insert(User user) {
        user.setId(flakeId.next());
        user.setSalt(Long.toString(System.currentTimeMillis()));
        User presentUser = userRepository.save(user);
        if (StringUtils.isBlank(user.getPasswd())) {
            user.setPasswd(User.DEFAULT_PASSWD);
        }
        String encryptPasswd = StringsUtils.sha256Hex(user.getPasswd(), user.getSalt(), Long.toString(presentUser.getCreateDate().getTime()));
        presentUser.setPasswd(encryptPasswd);
        return presentUser;
    }

    public PageList<User> page(PageRequest pageRequest, User user) {
        return userMapper.findAll(pageRequest, user);
    }

    public User updateById(Long id, User updateBody) {
        User value = this.findById(id);
        updateBody.setAccount(null);
        updateBody.setPasswd(null);
        JpaUtils.copyNotNullProperties(updateBody, value);
        return value;
    }

    public User deleteById(Long id) {
        User user = this.findById(id);
        user.setDeleted(BaseEntity.DELETE_TRUE);
        return user;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public User resetPasswd(Long id) {
        User user = this.findById(id);
        String encryptPasswd = StringsUtils.sha256Hex(User.DEFAULT_PASSWD, user.getSalt(), Long.toString(user.getCreateDate().getTime()));
        user.setPasswd(encryptPasswd);
        return user;
    }

    public User status(Long id, int status) {
        User user = this.findById(id);
        user.setStatus(status);
        return user;
    }

    @Transactional(readOnly = true)
    public boolean existsByAccount(String account) {
        return userRepository.countByAccount(account) > 0;
    }
}
