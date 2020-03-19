package com.bms.industry.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.Notice;
import com.bms.entity.Suggest;
import com.bms.industry.service.NoticeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.bms.common.domain.Result.ok;

/**
 * 行政管理
 *
 * @author zouyongcan
 * @date 2020/3/19
 */
@RestController
@RequestMapping("/industry/notices")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("行政管理")
public class NoticeController {
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    private final NoticeService noticeService;

    @OpLog("新增")
    @RequiresPermissions("notice_create")
    @PostMapping("")
    public Result<Notice> create(@RequestBody Notice notice) {
        noticeService.insert(notice);
        return Result.ok(notice);
    }

    @OpLog("编辑")
    @RequiresPermissions("notice_edit")
    @PutMapping("/{id}")
    public Result<Notice> edit(@PathVariable Long id, @RequestBody Notice notice) {
        noticeService.updateById(id, notice);
        return Result.ok(notice);
    }

    @OpLog("查询")
    @RequiresPermissions("notice_list")
    @GetMapping("/list")
    public Result<PageList<Notice>> list(PageRequest pageRequest, QueryParams notice) throws IllegalAccessException {
        return ok(noticeService.page(pageRequest, BeanMapper.toMap(notice)));
    }

    @OpLog("详情")
    @RequiresPermissions("notice_details")
    @GetMapping("/{id}")
    public Result<Notice> details(@PathVariable Long id) {
        return Result.ok(noticeService.findById(id));
    }

    @OpLog("删除")
    @RequiresPermissions("notice_delete")
    @DeleteMapping("/{id}")
    public Result<Notice> deleteById(@PathVariable Long id) {
        return Result.ok(noticeService.deleteById(id));
    }

    @Data
    private static class QueryParams extends Suggest {
        private Date begin;
        private Date end;
    }
}
