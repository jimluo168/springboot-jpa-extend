package com.bms.sys.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.OperationLog;
import com.bms.entity.User;
import com.bms.sys.service.OpLogService;
import com.bms.sys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @RequiresPermissions("user_list")
    @GetMapping("/list")
    public Result<PageList<User>> list(PageRequest pageRequest, OperationLog log) {
        return ok(opLogService.page(pageRequest, log));
    }
}
