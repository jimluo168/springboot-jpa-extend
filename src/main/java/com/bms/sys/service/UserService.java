package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.config.session.SessionInfo;
import com.bms.common.domain.PageList;
import com.bms.common.exception.ExceptionFactory;
import com.bms.common.exception.ServiceException;
import com.bms.common.util.StringsUtils;
import com.bms.entity.User;
import com.bms.sys.ErrorCode;
import com.bms.sys.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    public SessionInfo loginValidate(String account, String passwd) {
        User user = userRepository.findByAccount(account);
        if (user == null) {
            throw new ServiceException(ErrorCode.ACCOUNT_NOT_EXIST, "账号不存在");
        }
        String encryptPasswd = StringsUtils.sha256Hex(passwd, user.getSalt(), Long.toString(user.getCreateDate().getTime()));
        if (!StringUtils.equals(encryptPasswd, user.getPasswd())) {
            throw new ServiceException(ErrorCode.PASSWD_ERR, "密码错误");
        }
        SessionInfo info = new SessionInfo();
        info.setAccount(user.getAccount());
        info.setId(user.getId());
        info.setName(user.getRealName());
        info.setOrgId(user.getOrganization().getId());
        return info;
    }

    public long insert(User user) {
        user.setId(flakeId.next());
        user.setSalt(Long.toString(System.currentTimeMillis()));
        User presentUser = userRepository.save(user);
        String encryptPasswd = StringsUtils.sha256Hex(user.getPasswd(), user.getSalt(), Long.toString(presentUser.getCreateDate().getTime()));
        presentUser.setPasswd(encryptPasswd);
        return user.getId();
    }

    public PageList<User> page(Pageable pageable, String keyword) {
        Page<User> page = userRepository.findByAccountOrRealNameLike(pageable, keyword, keyword);
        return new PageList<>(page.getTotalElements(), page.getContent());
    }

    public User findById(Long userId) {
        Optional<User> optional = userRepository.findById(userId);
        if (!optional.isPresent()) {
            throw ExceptionFactory.dataNotExistException();
        }
        return optional.get();
    }
}
