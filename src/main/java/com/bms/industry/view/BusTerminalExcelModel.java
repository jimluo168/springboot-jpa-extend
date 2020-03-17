package com.bms.industry.view;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * TODO(类的简要说明)
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@Data
public class BusTerminalExcelModel {

    @ColumnWidth(50)
    @ExcelProperty(value = "场站名称", index = 0)
    private String name;

    @ColumnWidth(50)
    @ExcelProperty(value = "场站编码", index = 0)
    private String code;
}
