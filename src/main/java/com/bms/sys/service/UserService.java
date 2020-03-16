package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.config.session.SessionInfo;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.common.util.StringsUtils;
import com.bms.entity.User;
import com.bms.sys.Constant;
import com.bms.ErrorCodes;
import com.bms.sys.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.functors.ExceptionFactory;
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

    public PageList<User> page(PageRequest pageRequest, String account, String realName, String organization, int status) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", account);
        params.put("realName", realName);
        params.put("organization", organization);
        params.put("status", status);
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_USER_PAGE, params));
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
        user.setDeleted(DELETE_TRUE);
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
        String encryptPasswd = StringsUtils.sha256Hex("123456", user.getSalt(), Long.toString(user.getCreateDate().getTime()));
        user.setPasswd(encryptPasswd);
        return user;
    }
}
