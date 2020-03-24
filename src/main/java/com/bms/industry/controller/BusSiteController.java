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
import com.bms.common.util.BeanMapper;
import com.bms.common.util.ResponseUtils;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.BusSite;
import com.bms.entity.Practitioner;
import com.bms.industry.service.BusSiteService;
import com.bms.industry.view.BusSiteExcelModel;
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
 * 公交站点管理.
 *
 * @author zouyongcan
 * @date 2020/3/18
 */

@RestController
@RequestMapping("/industry/bussites")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("公交站点管理")
public class BusSiteController {

    private static final Logger logger = LoggerFactory.getLogger(BusSiteController.class);

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
        try {
            PageRequest pageRequest = new PageRequest(1, Constant.EXPORT_EXCEL_MAX_LINE);
            PageList<BusSite> pageList = busSiteService.page(pageRequest, BeanMapper.toMap(busSite));
            List<BusSiteExcelModel> data = new ArrayList<>();
            pageList.getList().stream().forEach(o -> {
                BusSiteExcelModel bs = new BusSiteExcelModel();
                BeanUtils.copyProperties(o, bs);
                System.out.println(o.getId().toString());
                System.out.println(o.getLatitude());
                data.add(bs);
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
    @RequiresPermissions("bus_site_import")
    @PostMapping("/import")
    public Result<Void> imports(MultipartFile file, String name) throws IOException, IllegalAccessException {
        try {
            EasyExcel.read(file.getInputStream(), BusSiteExcelModel.class, new BusSiteController.ImportDataListener(busSiteService)).sheet().doRead();
            return ok();
        } catch (Exception e) {
            logger.error("import data error", e);
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_ERR);
        }
    }

    @RequiredArgsConstructor
    private static class ImportDataListener extends AnalysisEventListener<BusSiteExcelModel> {
        private static final Logger logger = LoggerFactory.getLogger(BusSiteController.ImportDataListener.class);
        private static final int BATCH_COUNT = 3000;
        private List<BusSiteExcelModel> list = new ArrayList<BusSiteExcelModel>();

        private final BusSiteService busSiteService;

        @Override
        public void invoke(BusSiteExcelModel data, AnalysisContext context) {
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
            List<BusSite> batchData = new ArrayList<>();
            list.stream().forEach(o -> {
                BusSite target = new BusSite();
                BeanUtils.copyProperties(o, target);
                batchData.add(target);
            });
            busSiteService.saveAll(batchData);
        }
    }
}
