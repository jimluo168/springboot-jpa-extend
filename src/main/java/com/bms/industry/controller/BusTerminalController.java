package com.bms.industry.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.util.ResponseUtils;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.BusRoute;
import com.bms.entity.BusTerminal;
import com.bms.entity.Organization;
import com.bms.entity.Practitioner;
import com.bms.industry.service.BusTerminalService;
import com.bms.industry.view.BusTerminalExcelModel;
import com.bms.sys.controller.OrganizationController;
import com.bms.sys.view.OrganizationExcelModel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bms.common.domain.Result.ok;

/**
 * 公交场站 controller
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@RestController
@RequestMapping("/industry/busterminals")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("公交场站管理")
public class BusTerminalController {

    private final BusTerminalService busTerminalService;

    @OpLog("新增")
    @RequiresPermissions("bus_terminal_create")
    @PostMapping("")
    public Result<BusTerminal> create(@RequestBody BusTerminal busTerminal) {
        busTerminalService.insert(busTerminal);
        return Result.ok(busTerminal);
    }

    @OpLog("编辑")
    @RequiresPermissions("bus_terminal_edit")
    @PutMapping("/{id}")
    public Result<BusTerminal> edit(@PathVariable Long id, @RequestBody BusTerminal busTerminal) {
        busTerminalService.updateById(id, busTerminal);
        return Result.ok(busTerminal);
    }

    @OpLog("查询")
    @RequiresPermissions("bus_terminal_list")
    @GetMapping("/list")
    public Result<PageList<BusTerminal>> list(PageRequest pageRequest, BusTerminal busTerminal) throws IllegalAccessException {
        return ok(busTerminalService.page(pageRequest, BeanMapper.toMap(busTerminal)));
    }

    @OpLog("详情")
    @RequiresPermissions("bus_terminal_details")
    @GetMapping("/{id}")
    public Result<BusTerminal> details(@PathVariable Long id) {
        return Result.ok(busTerminalService.findById(id));
    }

    @OpLog("删除")
    @RequiresPermissions("bus_terminal_delete")
    @DeleteMapping("/{id}")
    public Result<BusTerminal> deleteById(@PathVariable Long id) {
        return Result.ok(busTerminalService.deleteById(id));
    }

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("bus_terminal_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<Practitioner> audit(@PathVariable Long id, @PathVariable int status, @RequestBody BusTerminal busTerminal) {
        busTerminalService.audit(id, status, busTerminal.getReason());
        return ok();
    }

    @ApiOperation("导出")
    @OpLog("导出")
    @RequiresPermissions("bus_terminal_export")
    @GetMapping("/export")
    public Result<Void> export(BusTerminal busTerminal, HttpServletResponse response) throws IOException, IllegalAccessException {
        try {
            PageRequest pageRequest = new PageRequest(1, Integer.MAX_VALUE);
            PageList<BusTerminal> pageList = busTerminalService.page(pageRequest, BeanMapper.toMap(busTerminal));
            List<BusTerminalExcelModel> data = new ArrayList<>();
            pageList.getList().stream().forEach(o -> {
                BusTerminalExcelModel bt = new BusTerminalExcelModel();
                BeanUtils.copyProperties(o, bt);
                data.add(bt);
            });

            ResponseUtils.setHeader(response, DateFormatUtils.format(new Date(), "yyyyMMdd"));
            EasyExcel.write(response.getOutputStream(), OrganizationExcelModel.class)
                    .autoCloseStream(Boolean.FALSE)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet()
                    .doWrite(data);
            return null;
        } catch (Exception e) {
            // 重置response
            response.reset();
            throw ErrorCodes.build(ErrorCodes.EXPORT_DATA_ERR);
        }
    }

    @ApiOperation("导入")
    @OpLog("导入")
    @RequiresPermissions("bus_terminal_import")
    @PostMapping("/import")
    public Result<Void> imports(MultipartFile file, String name) throws IOException, IllegalAccessException {
        return ok();
    }
}
