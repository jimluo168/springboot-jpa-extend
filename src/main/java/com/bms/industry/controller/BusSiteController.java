package com.bms.industry.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.BusSite;
import com.bms.entity.Practitioner;
import com.bms.industry.service.BusSiteService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bms.common.domain.Result.ok;

/**
 * 公交站点管理.
 *
 * @author zouyongcan
 * @date 2020/3/18
 */

@RestController
@RequestMapping("/industry/bussites")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("公交场站管理")
public class BusSiteController {
    private final BusSiteService busSiteService;

    @OpLog("新增")
    @RequiresPermissions("bus_site_create")
    @PostMapping("")
    public Result<BusSite> create(@RequestBody BusSite busSite) {
        busSiteService.insert(busSite);
        return Result.ok(busSite);
    }

    @OpLog("编辑")
    @RequiresPermissions("bus_site_edit")
    @PutMapping("/{id}")
    public Result<BusSite> edit(@PathVariable Long id, @RequestBody BusSite busSite) {
        busSiteService.updateById(id, busSite);
        return Result.ok(busSite);
    }

    @OpLog("查询")
    @RequiresPermissions("bus_site_list")
    @GetMapping("/list")
    public Result<PageList<BusSite>> list(PageRequest pageRequest, BusSite busSite) throws IllegalAccessException {
        return ok(busSiteService.page(pageRequest, BeanMapper.toMap(busSite)));
    }

    @OpLog("详情")
    @RequiresPermissions("bus_site_details")
    @GetMapping("/{id}")
    public Result<BusSite> details(@PathVariable Long id) {
        return Result.ok(busSiteService.findById(id));
    }

    @OpLog("删除")
    @RequiresPermissions("bus_site_delete")
    @DeleteMapping("/{id}")
    public Result<BusSite> deleteById(@PathVariable Long id) {
        return Result.ok(busSiteService.deleteById(id));
    }

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("bus_site_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<Practitioner> audit(@PathVariable Long id, @PathVariable int status, @RequestBody BusSite busSite) {
        busSiteService.audit(id, status, busSite.getReason());
        return ok();
    }

    @ApiOperation("导出")
    @OpLog("导出")
    @RequiresPermissions("bus_site_export")
    @GetMapping("/export")
    public Result<Void> export(BusSite busSite, HttpServletResponse response) throws IOException, IllegalAccessException {
        return ok();
    }

    @ApiOperation("导入")
    @OpLog("导入")
    @RequiresPermissions("bus_site_import")
    @PostMapping("/import")
    public Result<Void> imports(MultipartFile file, String name) throws IOException, IllegalAccessException {
        return ok();
    }
}
