package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.config.session.SessionInfo;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.exception.ExceptionFactory;
import com.bms.common.exception.ServiceException;
import com.bms.common.util.JpaUtils;
import com.bms.common.util.StringsUtils;
import com.bms.entity.User;
import com.bms.sys.Constant;
import com.bms.sys.ErrorCode;
import com.bms.sys.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;


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
    private final HibernateDao hibernateDao;

    public SessionInfo loginValidate(String account, String passwd) {
        User user = userRepository.findByAccount(account);
        if (user == null) {
            throw new ServiceException(ErrorCode.ACCOUNT_NOT_EXIST, "账号不存在");
        }
        if (user.getStatus() == User.STATUS_DISABLE) {
            throw new ServiceException(ExceptionFactory.ERR_USER_STATUS_DISABLED, "用户已禁用");
        }
        String encryptPasswd = StringsUtils.sha256Hex(passwd, user.getSalt(), Long.toString(user.getCreateDate().getTime()));
        if (!StringUtils.equals(encryptPasswd, user.getPasswd())) {
            throw new ServiceException(ErrorCode.PASSWD_ERR, "密码错误");
        }
        SessionInfo info = new SessionInfo();
        info.setId(user.getId());
        info.setAccount(user.getAccount());
        info.setName(user.getRealName());
        info.setOrgId(user.getOrganization().getId());
        info.setOrgName(user.getOrganization().getName());
        return info;
    }

    public User insert(User user) {
        user.setId(flakeId.next());
        user.setSalt(Long.toString(System.currentTimeMillis()));
        User presentUser = userRepository.save(user);
        String encryptPasswd = StringsUtils.sha256Hex(user.getPasswd(), user.getSalt(), Long.toString(presentUser.getCreateDate().getTime()));
        presentUser.setPasswd(encryptPasswd);
        return presentUser;
    }

    public PageList<User> page(PageRequest pageRequest, String keyword) {
        Map<String, Object> params = new HashMap<>();
        String likeName = keyword;
        if (StringUtils.isNotBlank(likeName)) {
            likeName = likeName + "%";
        }
        params.put("keyword", likeName);
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_USER_PAGE, params));
    }

    public User updateById(Long id, User updateBody) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User value = user.get();
            updateBody.setAccount(null);
            updateBody.setPasswd(null);
            JpaUtils.copyNotNullProperties(updateBody, value);
            return value;
        }
        throw ExceptionFactory.dataNotExistException();
    }

    public User deleteById(Long id) {
        User user = this.findById(id);
        user.setDeleted(DELETE_TRUE);
        return user;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw ExceptionFactory.dataNotExistException();
    }

    public User resetPasswd(Long id) {
        User user = this.findById(id);
        String encryptPasswd = StringsUtils.sha256Hex("123456", user.getSalt(), Long.toString(user.getCreateDate().getTime()));
        user.setPasswd(encryptPasswd);
        return user;
    }
}
