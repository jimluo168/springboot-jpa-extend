package com.bms.industry.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.exception.ServiceException;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.BusOnlineDataDeclare;
import com.bms.entity.BusOnlineDataDeclareItem;
import com.bms.industry.service.BusOnlineDataDeclareService;
import com.bms.industry.service.BusOnlineDataDeclareItemService;
import com.bms.industry.view.DeclareItemExcelModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.bms.common.domain.Result.ok;

/**
 * 网上申报管理
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@RestController
@RequestMapping("/industry/datadeclares")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("网上申报管理")
@Api("网上申报管理")
public class BusOnlineDataDeclareController {
    private static final Logger logger = LoggerFactory.getLogger(BusOnlineDataDeclareController.class);

    private final BusOnlineDataDeclareService busOnlineDataDeclareService;
    private final BusOnlineDataDeclareItemService declareItemService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("online_data_declare_create")
    @PostMapping("")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result<BusOnlineDataDeclare> create(@RequestBody BusOnlineDataDeclare busOnlineDataDeclare, MultipartFile file) throws IOException, IllegalAccessException  {
        try {
            BusOnlineDataDeclare declare = busOnlineDataDeclareService.insert(busOnlineDataDeclare);
            EasyExcel.read(file.getInputStream(), DeclareItemExcelModel.class, new BusOnlineDataDeclareController.ImportDataListener(declareItemService)).sheet().doRead();
            return ok(declare);
        } catch (Exception e) {
            logger.error("import data error", e);
            if (e instanceof ServiceException) {
                throw e;
            }
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_ERR);
        }
    }


    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("online_data_declare_edit")
    @PutMapping("/{id}")
    public Result<BusOnlineDataDeclare> edit(@PathVariable Long id, @RequestBody BusOnlineDataDeclare busOnlineDataDeclare) {
        busOnlineDataDeclareService.updateById(id, busOnlineDataDeclare);
        return ok(busOnlineDataDeclare);
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("online_data_declare_list")
    @GetMapping("/list")
    public Result<PageList<BusOnlineDataDeclare>> list(PageRequest pageRequest, BusOnlineDataDeclare busOnlineDataDeclare) throws IllegalAccessException {
        return ok(busOnlineDataDeclareService.page(pageRequest, BeanMapper.toMap(busOnlineDataDeclare)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("online_data_declare_details")
    @GetMapping("/{id}")
    public Result<BusOnlineDataDeclare> details(@PathVariable Long id) {
        return Result.ok(busOnlineDataDeclareService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("online_data_declare_delete")
    @DeleteMapping("/{id}")
    public Result<BusOnlineDataDeclare> delete(@PathVariable Long id) {
        return ok(busOnlineDataDeclareService.deleteById(id));
    }

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("online_data_declare_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<BusOnlineDataDeclare> audit(@PathVariable Long id, @PathVariable int status, @RequestBody BusOnlineDataDeclare busOnlineDataDeclare) {
        busOnlineDataDeclareService.audit(id, status, busOnlineDataDeclare.getReason());
        return ok();
    }

@RequiredArgsConstructor
private static class ImportDataListener extends AnalysisEventListener<DeclareItemExcelModel> {
    private static final Logger logger = LoggerFactory.getLogger(BusOnlineDataDeclareController.ImportDataListener.class);
    private static final int BATCH_COUNT = 3000;
    private List<DeclareItemExcelModel> list = new ArrayList<>();

    private final BusOnlineDataDeclareItemService declareItemService;

    @Override
    public void invoke(DeclareItemExcelModel data, AnalysisContext context) {
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
        List<BusOnlineDataDeclareItem> batchData = new ArrayList<>();
        list.stream().forEach(o -> {
            BusOnlineDataDeclareItem target = new BusOnlineDataDeclareItem();
            BeanUtils.copyProperties(o, target);
            batchData.add(target);
        });
        declareItemService.saveAll(batchData);
    }
}
}