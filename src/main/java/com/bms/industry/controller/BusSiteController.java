package com.bms.industry.controller;

import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 站点controller.
 *
 * @author zouyongcan
 * @date 2020/3/18
 */

@RestController
@RequestMapping("/industry/bussite")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("公交场站管理")
public class BusSizeController {
}
