package com.bms.sys.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.Dictionary;
import com.bms.sys.service.DictService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bms.common.domain.Result.ok;

/**
 * 字典管理.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@RestController
@RequestMapping("/sys/dict")
@RequiredArgsConstructor
@RequiresAuthentication
@Api("字典管理")
public class DictController {

    private final DictService dictService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("dict_create")
    @PostMapping("")
    public Result<Dictionary> create(@RequestBody Dictionary organization) {
        dictService.insert(organization);
        return ok(organization);
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("dict_edit")
    @PutMapping("/{id}")
    public Result<Dictionary> edit(@PathVariable Long id, @RequestBody Dictionary organization) {
        dictService.updateById(id, organization);
        return ok(organization);
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("dict_list")
    @GetMapping("/list")
    public Result<PageList<Dictionary>> list(PageRequest pageRequest, Dictionary organization) throws IllegalAccessException {
        return ok(dictService.page(pageRequest, BeanMapper.toMap(organization)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("dict_details")
    @GetMapping("/{id}")
    public Result<Dictionary> details(@PathVariable Long id) {
        return Result.ok(dictService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("dict_delete")
    @DeleteMapping("/{id}")
    public Result<Dictionary> delete(@PathVariable Long id) {
        return ok(dictService.deleteById(id));
    }

    @ApiOperation("禁用/启用")
    @OpLog("禁用/启用")
    @RequiresPermissions("dict_status")
    @PostMapping("/{id}/status/{status}")
    public Result<Dictionary> status(@PathVariable Long id, @PathVariable int status) {
        dictService.status(id, status);
        return ok();
    }

    @ApiOperation("根据编码获取字典值集合")
    @GetMapping("/codes/{code}")
    public Result<List<Dictionary>> findByCode(@PathVariable String code) {
        return ok(dictService.findByCode(code));
    }

    @ApiOperation("根据编码获取字典值集合")
    @GetMapping("/all")
    public Result<Iterable<Dictionary>> all() {
        return ok(dictService.findAll());
    }

}
