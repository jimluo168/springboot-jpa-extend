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
import com.bms.entity.Practitioner;
import com.bms.industry.service.PractitionerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bms.common.domain.Result.ok;

/**
 * 从业人员 controller
 *
 * @author zouyongcan
 * @date 2020/3/16
 */
@RestController
@RequestMapping("/industry/practitioners")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("从业人员管理")
public class PractitionerController {

    private final PractitionerService practitionerService;

    @OpLog("新增")
    @RequiresPermissions("practitioner_create")
    @PostMapping("")
    public Result<Practitioner> create(@RequestBody Practitioner practitioner) {
        practitionerService.insert(practitioner);
        return Result.ok(practitioner);
    }

    @OpLog("编辑")
    @RequiresPermissions("practitioner_edit")
    @PutMapping("/{id}")
    public Result<Practitioner> edit(@PathVariable Long id, @RequestBody Practitioner practitioner) {
        practitionerService.updateById(id, practitioner);
        return Result.ok(practitioner);
    }

    @OpLog("查询")
    @RequiresPermissions("practitioner_list")
    @GetMapping("/list")
    public Result<PageList<Practitioner>> list(PageRequest pageRequest,
                                               @RequestParam(defaultValue = "") String name,
                                               @RequestParam(defaultValue = "") String gender,
                                               @RequestParam(defaultValue = "") String organization,
                                               @RequestParam(defaultValue = "", name = "certificate_number") String certificateNumber,
                                               @RequestParam(defaultValue = "", name = "ID_number") String IDNumber
    ) throws IllegalAccessException {
        return ok(practitionerService.page(pageRequest, name, gender, organization, certificateNumber, IDNumber));
    }

    @OpLog("详情")
    @RequiresPermissions("practitioner_details")
    @GetMapping("/{id}")
    public Result<Practitioner> details(@PathVariable Long id) {
        return Result.ok(practitionerService.findById(id));
    }

    @OpLog("删除")
    @RequiresPermissions("practitioner_delete")
    @DeleteMapping("/{id}")
    public Result<Practitioner> deleteById(@PathVariable Long id) {
        return Result.ok(practitionerService.deleteById(id));
    }

    @ApiOperation("导出")
    @OpLog("导出")
    @RequiresPermissions("practitioner_export")
    @GetMapping("/export")
    public Result<Void> export(@RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String gender,
                               @RequestParam(defaultValue = "") String organization,
                               @RequestParam(defaultValue = "", name = "certificate_number") String certificateNumber,
                               @RequestParam(defaultValue = "", name = "ID_number") String IDNumber,
                               HttpServletResponse response) throws IOException, IllegalAccessException {
        return ok();
    }

    @ApiOperation("导入")
    @OpLog("导入")
    @RequiresPermissions("practitioner_import")
    @PostMapping("/import")
    public Result<Void> imports(MultipartFile file, String name) throws IOException, IllegalAccessException {
        return ok();
    }
}
