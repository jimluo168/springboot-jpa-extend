package com.bms.sys.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.exception.DatabaseException;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.OperationLog;
import com.bms.entity.User;
import com.bms.sys.service.OpLogService;
import com.bms.sys.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.QueryParameter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

import static com.bms.common.domain.Result.ok;

/**
 * 用户controller.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@RestController
@RequestMapping("/sys/oplogs")
@RequiredArgsConstructor
@RequiresAuthentication
public class OpLogController {

    private final OpLogService opLogService;

    @RequiresPermissions("oplog_list")
    @GetMapping("/list")
    public Result<PageList<User>> list(PageRequest pageRequest, QueryParams params) throws IllegalAccessException {
        return ok(opLogService.page(pageRequest, BeanMapper.toMap(params)));
    }

    @Data
    private static class QueryParams extends OperationLog {
        private String real_name;
        private String org_name;
        private String func_name;
        private Date begin;
        private Date end;
    }
}
