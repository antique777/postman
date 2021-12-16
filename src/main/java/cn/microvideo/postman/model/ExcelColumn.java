package cn.microvideo.postman.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import lombok.ToString;

/**
 * excel导出文件列
 *
 * @author xab
 * @date 2021-10-31 17:25
 */
@Data
@ToString
public class ExcelColumn {
    @ExcelProperty("序号")
    @ColumnWidth(10)
    private int sortNumber;
    @ExcelProperty("文件夹名称")
    @ColumnWidth(25)
    private String folderName;
    @ExcelProperty("接口名称")
    @ColumnWidth(40)
    private String interfaceName;
    @ExcelProperty("URL")
    @ColumnWidth(65)
    private String urlName;
    @ExcelProperty("描述")
    @ColumnWidth(30)
    private String description;
    @ExcelProperty("接口参数")
    @ColumnWidth(70)
    private String parameters;
    @ExcelProperty("请求")
    @ColumnWidth(8)
    private String typeName;
    @ExcelProperty("返回值参数")
    @ColumnWidth(50)
    private String responseParameters;
    @ExcelProperty("备注")
    @ColumnWidth(10)
    private String remark;
}
