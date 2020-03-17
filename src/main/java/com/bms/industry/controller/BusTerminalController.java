package com.bms.industry.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.BusTerminal;
import com.bms.industry.service.BusTerminalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
