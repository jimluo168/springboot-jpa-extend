package com.bms.sys.controller;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.util.ResponseUtils;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.Organization;
import com.bms.sys.service.OrganizationService;
import com.bms.sys.view.OrganizationExcelModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bms.common.domain.Result.ok;

/**
 * 公交企业管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@RestController
@RequestMapping("/sys/organizations")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("公交企业管理")
@Api("公交企业管理")
public class OrganizationController {
    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    private final OrganizationService organizationService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("organization_create")
    @PostMapping("")
    public Result<Organization> create(@RequestBody Organization organization) {
        organizationService.insert(organization);
        return ok(organization);
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("organization_edit")
    @PutMapping("/{id}")
    public Result<Organization> edit(@PathVariable Long id, @RequestBody Organization organization) {
        organizationService.updateById(id, organization);
        return ok(organization);
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("organization_list")
    @GetMapping("/list")
    public Result<PageList<Organization>> list(PageRequest pageRequest, Organization organization) throws IllegalAccessException {
        return ok(organizationService.page(pageRequest, BeanMapper.toMap(organization)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("organization_details")
    @GetMapping("/{id}")
    public Result<Organization> details(@PathVariable Long id) {
        return Result.ok(organizationService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("organization_delete")
    @DeleteMapping("/{id}")
    public Result<Organization> delete(@PathVariable Long id) {
        return ok(organizationService.deleteById(id));
    }

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("organization_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<Organization> audit(@PathVariable Long id, @PathVariable int status, @RequestBody Organization organization) {
        organizationService.audit(id, status, organization.getReason());
        return ok();
    }

    @ApiOperation("导出")
    @OpLog("导出")
    @RequiresPermissions("organization_export")
    @GetMapping("/export")
    public Result<Void> export(Organization organization, HttpServletResponse response) throws IOException, IllegalAccessException {
        try {
            PageRequest pageRequest = new PageRequest(1, Integer.MAX_VALUE);
            PageList<Organization> pageList = organizationService.page(pageRequest, BeanMapper.toMap(organization));
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
    @RequiresPermissions("organization_import")
    @PostMapping("/import")
    public Result<Void> imports(MultipartFile file, String name) throws IOException, IllegalAccessException {
        try {
            EasyExcel.read(file.getInputStream(), OrganizationExcelModel.class, new ImportDataListener(organizationService)).sheet().doRead();
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

        private final OrganizationService organizationService;

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
            List<Organization> batchData = new ArrayList<>();
            list.stream().forEach(o -> {
                Organization target = new Organization();
                BeanUtils.copyProperties(o, target);
                batchData.add(target);
            });
            organizationService.saveAll(batchData);
        }
    }


}
