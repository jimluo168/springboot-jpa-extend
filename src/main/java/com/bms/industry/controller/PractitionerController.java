package com.bms.industry.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.ResponseUtils;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.Practitioner;
import com.bms.industry.service.PractitionerService;
import com.bms.industry.view.PractitionerExcelModel;
import com.bms.sys.view.OrganizationExcelModel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(PractitionerController.class);

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

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("practitioner_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<Practitioner> audit(@PathVariable Long id, @PathVariable int status, @RequestBody Practitioner practitioner) {
        practitionerService.audit(id, status, practitioner.getReason());
        return ok();
    }

    @ApiOperation("导出")
    @OpLog("导出")
    @RequiresPermissions("practitioner_export")
    @GetMapping("/export")
    public Result<Void> export(@RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String gender,
                               @RequestParam(defaultValue = "") String organization,
                               @RequestParam(defaultValue = "", name = "cert_no") String certNo,
                               @RequestParam(defaultValue = "", name = "id_number") String idNumber,
                               HttpServletResponse response) throws IOException, IllegalAccessException {
        try {
            PageRequest pageRequest = new PageRequest(1, Integer.MAX_VALUE);
            PageList<Practitioner> pageList = practitionerService.page(pageRequest, name, gender, organization, certNo, idNumber);
            List<PractitionerExcelModel> data = new ArrayList<>();
            pageList.getList().stream().forEach(o -> {
                PractitionerExcelModel p = new PractitionerExcelModel();
                BeanUtils.copyProperties(o, p);
                data.add(p);
            });

            ResponseUtils.setHeader(response, DateFormatUtils.format(new Date(), Constant.DATE_FORMAT_YYYYMMDD));
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
    @RequiresPermissions("practitioner_import")
    @PostMapping("/import")
    public Result<Void> imports(MultipartFile file, String name) throws IOException, IllegalAccessException {
        try {
            EasyExcel.read(file.getInputStream(), PractitionerExcelModel.class, new PractitionerController.ImportDataListener(practitionerService)).sheet().doRead();
            return ok();
        } catch (Exception e) {
            logger.error("import data error", e);
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_ERR);
        }
    }

    @RequiredArgsConstructor
    private static class ImportDataListener extends AnalysisEventListener<PractitionerExcelModel> {
        private static final Logger logger = LoggerFactory.getLogger(PractitionerController.ImportDataListener.class);
        private static final int BATCH_COUNT = 3000;
        private List<PractitionerExcelModel> list = new ArrayList<PractitionerExcelModel>();

        private final PractitionerService practitionerService;

        @Override
        public void invoke(PractitionerExcelModel data, AnalysisContext context) {
            list.add(data);
            if (list.size() >= BATCH_COUNT) {
                saveData();
                // 存储完成清理 list
                list.clear();
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            saveData();
            logger.info("所有数据解析完成！");
        }

        private void saveData() {
            List<Practitioner> batchData = new ArrayList<>();
            list.stream().forEach(o -> {
                Practitioner target = new Practitioner();
                BeanUtils.copyProperties(o, target);
                batchData.add(target);
            });
            practitionerService.saveAll(batchData);
        }
    }
}
