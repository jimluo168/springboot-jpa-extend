package com.bms.monitor.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.KnowledgeBase;
import com.bms.monitor.service.KnowledgeBaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.bms.common.domain.Result.ok;

/**
 * 专家知识库管理。
 *
 * @author zouyongcan
 * @date 2020/4/3
 */
@RestController
@RequestMapping("/monitor/knowledgebases")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("专家知识库管理")
@Api(value = "专家知识库管理", tags = "专家知识库管理")
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("knowledge_base_create")
    @PostMapping("")
    public Result<KnowledgeBase> create(@RequestBody KnowledgeBase knowledgeBase) {
        knowledgeBaseService.insert(knowledgeBase);
        return ok(knowledgeBase);
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("knowledge_base_edit")
    @PutMapping("/{id}")
    public Result<KnowledgeBase> edit(@PathVariable Long id, @RequestBody KnowledgeBase knowledgeBase) {
        knowledgeBaseService.updateById(id, knowledgeBase);
        return ok(knowledgeBase);
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("knowledge_base_list")
    @GetMapping("/list")
    public Result<PageList<KnowledgeBase>> list(PageRequest pageRequest, QueryParams queryParams) throws IllegalAccessException {
        return ok(knowledgeBaseService.page(pageRequest, BeanMapper.toMap(queryParams)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("knowledge_base_details")
    @GetMapping("/{id}")
    public Result<KnowledgeBase> details(@PathVariable Long id) {
        return Result.ok(knowledgeBaseService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("knowledge_base_delete")
    @DeleteMapping("/{id}")
    public Result<KnowledgeBase> delete(@PathVariable Long id) {
        return ok(knowledgeBaseService.deleteById(id));
    }

    @Data
    public static class QueryParams extends KnowledgeBase{
        private Date begin;
        private Date end;
    }
}
