package com.bms.industry.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
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
import com.bms.entity.Vehicle;
import com.bms.industry.service.VehicleService;
import com.bms.sys.service.OrganizationService;
import com.bms.sys.view.OrganizationExcelModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
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
 * 公交车辆管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@RestController
@RequestMapping("/industry/vehicles")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("公交车辆管理")
@Api("公交车辆管理")
public class VehicleController {
    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("bus_vehicle_create")
    @PostMapping("")
    public Result<Vehicle> create(@RequestBody Vehicle vehicle) {
        vehicleService.insert(vehicle);
        return ok(vehicle);
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("bus_vehicle_edit")
    @PutMapping("/{id}")
    public Result<Vehicle> edit(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        vehicleService.updateById(id, vehicle);
        return ok(vehicle);
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("bus_vehicle_list")
    @GetMapping("/list")
    public Result<PageList<Vehicle>> list(PageRequest pageRequest, Vehicle vehicle) throws IllegalAccessException {
        return ok(vehicleService.page(pageRequest, BeanMapper.toMap(vehicle)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("bus_vehicle_details")
    @GetMapping("/{id}")
    public Result<Vehicle> details(@PathVariable Long id) {
        return Result.ok(vehicleService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("bus_vehicle_delete")
    @DeleteMapping("/{id}")
    public Result<Vehicle> delete(@PathVariable Long id) {
        return ok(vehicleService.deleteById(id));
    }

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("bus_vehicle_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<Vehicle> audit(@PathVariable Long id, @PathVariable int status, @RequestBody Vehicle vehicle) {
        vehicleService.audit(id, status, vehicle.getReason());
        return ok();
    }

    @ApiOperation("导出")
    @OpLog("导出")
    @RequiresPermissions("bus_vehicle_export")
    @GetMapping("/export")
    public Result<Void> export(Vehicle vehicle, HttpServletResponse response) throws IOException, IllegalAccessException {
        try {
            PageRequest pageRequest = new PageRequest(1, Integer.MAX_VALUE);
            PageList<Vehicle> pageList = vehicleService.page(pageRequest, BeanMapper.toMap(vehicle));
            List<OrganizationExcelModel> data = new ArrayList<>();
            pageList.getList().stream().forEach(o -> {
                OrganizationExcelModel m = new OrganizationExcelModel();
                BeanUtils.copyProperties(o, m);
                data.add(m);
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
    @RequiresPermissions("bus_vehicle_import")
    @PostMapping("/import")
    public Result<Void> imports(MultipartFile file, String name) throws IOException, IllegalAccessException {
        try {
            EasyExcel.read(file.getInputStream(), OrganizationExcelModel.class, new ImportDataListener(vehicleService)).sheet().doRead();
            return ok();
        } catch (Exception e) {
            logger.error("import data error", e);
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_ERR);
        }
    }

    @RequiredArgsConstructor
    private static class ImportDataListener extends AnalysisEventListener<OrganizationExcelModel> {
        private static final Logger logger = LoggerFactory.getLogger(ImportDataListener.class);
        private static final int BATCH_COUNT = 3000;
        private List<OrganizationExcelModel> list = new ArrayList<OrganizationExcelModel>();

        private final VehicleService vehicleService;

        @Override
        public void invoke(OrganizationExcelModel data, AnalysisContext context) {
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
            List<Vehicle> batchData = new ArrayList<>();
            list.stream().forEach(o -> {
                Vehicle target = new Vehicle();
                BeanUtils.copyProperties(o, target);
                batchData.add(target);
            });
            vehicleService.saveAll(batchData);
        }
    }

}
