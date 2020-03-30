package com.bms.industry.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 申报管理.
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusOnlineDataDeclareService {

}
