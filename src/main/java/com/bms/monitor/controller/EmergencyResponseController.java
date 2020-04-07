package com.bms.monitor.controller;

import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.MoEmergencyResponse;
import com.bms.monitor.service.EmergencyResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static com.bms.common.domain.Result.ok;

/**
 * 应急响应处理.
 *
 * @author luojimeng
 * @date 2020/4/6
 */
@RestController
@RequestMapping("/monitor/emergencyresponses")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("应急响应处理")
@Api(value = "应急响应处理", tags = "应急响应处理")
public class EmergencyResponseController {
    private static final Logger logger = LoggerFactory.getLogger(EmergencyResponseController.class);

    private final EmergencyResponseService emergencyResponseService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("emergency_response_treatment_create")
    @PostMapping("")
    public Result<MoEmergencyResponse> create(@RequestBody MoEmergencyResponse emergencyResponse) {
        if (emergencyResponseService.existsByName(emergencyResponse.getName(), null)) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "事件名称已存在", true);
        }
        return ok(emergencyResponseService.insert(emergencyResponse));
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("emergency_response_treatment_edit")
    @PutMapping("/{id}")
    public Result<MoEmergencyResponse> edit(@PathVariable Long id, @RequestBody MoEmergencyResponse emergencyResponse) {
        if (emergencyResponseService.existsByName(emergencyResponse.getName(), id)) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "事件名称已存在", true);
        }
        return ok(emergencyResponseService.updateById(id, emergencyResponse));
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions(value = {"emergency_response_treatment_list", "emergency_response_history_list", "emergency_response_case_list"})
    @GetMapping("/list")
    public Result<PageList<MoEmergencyResponse>> list(PageRequest pageRequest, MoEmergencyResponse emergencyResponse) throws IllegalAccessException {
        return ok(emergencyResponseService.page(pageRequest, BeanMapper.toMap(emergencyResponse)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions({"emergency_response_treatment_details", "emergency_response_history_details", "emergency_response_case_details"})
    @GetMapping("/{id}")
    public Result<MoEmergencyResponse> details(@PathVariable Long id) {
        return Result.ok(emergencyResponseService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions({"emergency_response_treatment_delete", "emergency_response_history_delete", "emergency_response_case_delete"})
    @DeleteMapping("/{id}")
    public Result<MoEmergencyResponse> delete(@PathVariable Long id) {
        return ok(emergencyResponseService.deleteById(id));
    }

    @ApiOperation("跟进")
    @OpLog("跟进")
    @RequiresPermissions("emergency_response_treatment_follow")
    @PutMapping("/follows/{id}")
    public Result<MoEmergencyResponse> follow(@PathVariable Long id, @RequestBody MoEmergencyResponse emergencyResponse) {
        return ok(emergencyResponseService.follow(id, emergencyResponse));
    }

    @ApiOperation("评估")
    @OpLog("评估")
    @RequiresPermissions("emergency_response_history_evaluate")
    @PutMapping("/evaluates/{id}")
    public Result<MoEmergencyResponse> evaluate(@PathVariable Long id, @RequestBody MoEmergencyResponse emergencyResponse) {
        return ok(emergencyResponseService.evaluate(id, emergencyResponse));
    }

    @ApiOperation("案例生成")
    @OpLog("案例生成")
    @RequiresPermissions("emergency_response_history_generate")
    @PostMapping("/generatecases/{id}")
    public Result<MoEmergencyResponse> generateCase(@PathVariable Long id) {
        return ok(emergencyResponseService.generateCase(id));
    }

}
