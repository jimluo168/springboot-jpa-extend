package com.bms.industry.view;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.bms.ErrorCodes;
import com.bms.common.util.DateUtil;
import com.bms.entity.Organization;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * 公交线路管理-导入/导出 Excel模型.
 *
 * @author luojimeng
 * @date 2020/3/19
 */
@Data
public class BusRouteExcelModel {
    /**
     * 名称.
     */
    @ColumnWidth(30)
    @ExcelProperty(value = "线路名称", index = 0)
    private String name;
    /**
     * 票价.
     */
    @ColumnWidth(15)
    @ExcelProperty(value = "票价", index = 2)
    private Float price;
    /**
     * 里程.
     */
    @ColumnWidth(30)
    @ExcelProperty(value = "营业里程", index = 1)
    private String mileage;
    /**
     * 首发站.
     */
    @ExcelIgnore
    private String startSite;
    /**
     * 终点站.
     */
    @ExcelIgnore
    private String endSite;
    /**
     * 方向.
     */
    @ColumnWidth(50)
    @ExcelProperty(value = "方向", index = 3)
    private String directionText;
    /**
     * 首班时间.
     */
    @ExcelIgnore
    private Date startTime;
    @ColumnWidth(30)
    @ExcelProperty(value = "首班时间", index = 4)
    private String startTimeText;

    /**
     * 末班时间.
     */
    @ExcelIgnore
    private Date lastTime;
    @ColumnWidth(30)
    @ExcelProperty(value = "末班时间", index = 5)
    private String lastTimeText;
    /**
     * 状态(1=待审核 2=通过审核 3=未通过审核).
     */
    @ExcelIgnore
    private Integer status;
    @ColumnWidth(15)
    @ExcelProperty(value = "状态", index = 6)
    private String statusText;

    public Date getStartTime() {
        if (StringUtils.isBlank(startTimeText)) {
            return null;
        }
        try {
            return DateUtil.gmt82utc(DateUtils.parseDate(startTimeText, "HH:mm"));
        } catch (ParseException e) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "首班时间格式错误");
        }
    }

    public String getStartTimeText() {
        if (startTime == null) {
            return "";
        }
        return DateFormatUtils.format(DateUtil.utc2gmt8(startTime), "HH:mm");
    }

    public Date getLastTime() {
        if (StringUtils.isBlank(lastTimeText)) {
            return null;
        }
        try {
            return DateUtil.gmt82utc(DateUtils.parseDate(lastTimeText, "HH:mm"));
        } catch (ParseException e) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "首班时间格式错误");
        }
    }

    public String getLastTimeText() {
        if (lastTime == null) {
            return "";
        }
        return DateFormatUtils.format(DateUtil.utc2gmt8(lastTime), "HH:mm");
    }

    public String getDirectionText() {
        return startSite + "—" + endSite;
    }

    public String getStartSite() {
        if (StringUtils.isBlank(directionText)) {
            return "";
        }
        if (!StringUtils.contains(directionText, "—")) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR);
        }
        return directionText.split("—")[0];
    }

    public String getEndSite() {
        if (StringUtils.isBlank(directionText)) {
            return "";
        }
        if (!StringUtils.contains(directionText, "—")) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR);
        }
        return directionText.split("—")[1];
    }


    public Integer getStatus() {
        if (StringUtils.isBlank(statusText)) {
            return -1;
        }
        if (StringUtils.equals(statusText, "待审核")) {
            return Organization.STATUS_TO_BE_AUDIT;
        }
        if (StringUtils.equals(statusText, "已通过")) {
            return Organization.STATUS_PASS_AUDIT;
        }
        if (StringUtils.equals(statusText, "未通过")) {
            return Organization.STATUS_UN_AUDIT;
        }
        throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR);
    }

    public String getStatusText() {
        if (status == null) {
            return "";
        }
        if (status == Organization.STATUS_TO_BE_AUDIT) {
            return "待审核";
        }
        if (status == Organization.STATUS_PASS_AUDIT) {
            return "已通过";
        }
        if (status == Organization.STATUS_UN_AUDIT) {
            return "未通过";
        }
        return status.toString();
    }

}
