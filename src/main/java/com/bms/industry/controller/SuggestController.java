package com.bms.industry.controller;

import com.bms.common.config.session.SessionInfo;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.Suggest;
import com.bms.entity.Practitioner;
import com.bms.industry.service.SuggestService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.bms.common.domain.Result.ok;

/**
 * 投诉建议管理
 *
 * @author zouyongcan
 * @date 2020/3/19
 */
@RestController
@RequestMapping("/industry/suggests")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("投诉建议管理")
public class SuggestController {
    private final SuggestService suggestService;

    @OpLog("新增")
    @RequiresPermissions("suggest_create")
    @PostMapping("")
    public Result<Suggest> create(@RequestBody Suggest suggest) {
        suggestService.insert(suggest);
        return Result.ok(suggest);
    }

    @OpLog("编辑")
    @RequiresPermissions("suggest_edit")
    @PutMapping("/{id}")
    public Result<Suggest> edit(@PathVariable Long id, @RequestBody Suggest suggest) {
        suggestService.updateById(id, suggest);
        return Result.ok(suggest);
    }

    @OpLog("查询")
    @RequiresPermissions("suggest_list")
    @GetMapping("/list")
    public Result<PageList<Suggest>> list(PageRequest pageRequest, QueryParams suggest) throws IllegalAccessException {
        return ok(suggestService.page(pageRequest, BeanMapper.toMap(suggest)));
    }

    @OpLog("详情")
    @RequiresPermissions("suggest_details")
    @GetMapping("/{id}")
    public Result<Suggest> details(@PathVariable Long id) {
        return Result.ok(suggestService.findById(id));
    }

    @OpLog("删除")
    @RequiresPermissions("suggest_delete")
    @DeleteMapping("/{id}")
    public Result<Suggest> deleteById(@PathVariable Long id) {
        return Result.ok(suggestService.deleteById(id));
    }

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("suggest_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<Practitioner> audit(@PathVariable Long id, @PathVariable int status, @RequestBody Suggest suggest) {
        Long userId = SessionInfo.getCurrentUserId();
        suggestService.audit(id, status, suggest.getReason(), userId);
        return ok();
    }

    @Data
    private static class QueryParams extends Suggest {
        private Date begin;
        private Date end;
    }
}
