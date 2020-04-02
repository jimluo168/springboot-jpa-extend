package com.bms.industry.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.exception.ServiceException;
import com.bms.common.util.BeanMapper;
import com.bms.common.util.ResponseUtils;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.BusViolation;
import com.bms.industry.service.BusRouteService;
import com.bms.industry.service.BusViolationService;
import com.bms.industry.service.PractitionerService;
import com.bms.industry.service.VehicleService;
import com.bms.industry.view.BusViolationExcelModel;
import com.bms.sys.service.DictService;
import com.bms.sys.service.OrganizationService;
import com.bms.sys.view.OrganizationExcelModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
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
 * 违规信息管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@RestController
@RequestMapping("/industry/busviolations")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("违规信息管理")
@Api(value = "违规信息管理",tags = "违规信息管理")
public class BusViolationController {
    private static final Logger logger = LoggerFactory.getLogger(BusViolationController.class);

    private final BusViolationService busViolationService;
    private final ObjectMapper objectMapper;
    private final DictService dictService;
    private final PractitionerService practitionerService;
    private final VehicleService vehicleService;
    private final BusRouteService busRouteService;
    private final OrganizationService organizationService;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("bus_violation_create")
    @PostMapping("")
    public Result<BusViolation> create(@RequestBody BusViolation busViolation) {
        busViolationService.insert(busViolation);
        return ok(busViolation);
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("bus_violation_edit")
    @PutMapping("/{id}")
    public Result<BusViolation> edit(@PathVariable Long id, @RequestBody BusViolation busViolation) {
        busViolationService.updateById(id, busViolation);
        return ok(busViolation);
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("bus_violation_list")
    @GetMapping("/list")
    public Result<PageList<BusViolation>> list(PageRequest pageRequest, BusViolation busViolation) throws IllegalAccessException {
        return ok(busViolationService.page(pageRequest, BeanMapper.toMap(busViolation)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("bus_violation_details")
    @GetMapping("/{id}")
    public Result<BusViolation> details(@PathVariable Long id) {
        return Result.ok(busViolationService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("bus_violation_delete")
    @DeleteMapping("/{id}")
    public Result<BusViolation> delete(@PathVariable Long id) {
        return ok(busViolationService.deleteById(id));
    }

    @ApiOperation("处理")
    @OpLog("处理")
    @RequiresPermissions("bus_violation_deal")
    @PostMapping("/{id}/deals")
    public Result<BusViolation> deal(@PathVariable Long id, @RequestBody BusViolation busViolation) {
        busViolationService.deal(id, busViolation);
        return ok();
    }

    @ApiOperation("导出")
    @OpLog("导出")
    @RequiresPermissions("bus_violation_export")
    @GetMapping("/export")
    public Result<Void> export(BusViolation busViolation, HttpServletResponse response) throws IOException, IllegalAccessException {
        try {
            PageRequest pageRequest = new PageRequest(1, Constant.EXPORT_EXCEL_MAX_LINE);
            PageList<BusViolation> pageList = busViolationService.page(pageRequest, BeanMapper.toMap(busViolation));
            List<BusViolationExcelModel> data = new ArrayList<>();
            pageList.getList().stream().forEach(o -> {
                BusViolationExcelModel m = new BusViolationExcelModel(dictService, practitionerService, vehicleService, busRouteService, organizationService);
                BeanUtils.copyProperties(o, m);
                data.add(m);
            });

            ResponseUtils.setHeader(response, DateFormatUtils.format(new Date(), Constant.DATE_FORMAT_YYYYMMDD));
            EasyExcel.write(response.getOutputStream(), BusViolationExcelModel.class)
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
    @RequiresPermissions("bus_violation_import")
    @PostMapping("/import")
    public Result<Void> imports(MultipartFile file, String name) throws IOException, IllegalAccessException {
        try {
            EasyExcel.read(file.getInputStream(), BusViolationExcelModel.class, new ImportDataListener(busViolationService)).sheet().doRead();
            return ok();
        } catch (Exception e) {
            logger.error("import data error", e);
            if (e instanceof ServiceException) {
                throw e;
            }
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_ERR);
        }
    }

    @RequiredArgsConstructor
    private static class ImportDataListener extends AnalysisEventListener<BusViolationExcelModel> {
        private static final Logger logger = LoggerFactory.getLogger(ImportDataListener.class);
        private static final int BATCH_COUNT = 3000;
        private List<BusViolationExcelModel> list = new ArrayList<BusViolationExcelModel>();

        private final BusViolationService busViolationService;

        @Override
        public void invoke(BusViolationExcelModel data, AnalysisContext context) {
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
            List<BusViolation> batchData = new ArrayList<>();
            list.stream().forEach(o -> {
                BusViolation target = new BusViolation();
                BeanUtils.copyProperties(o, target);
                batchData.add(target);
            });
            busViolationService.saveAll(batchData);
        }
    }

}
