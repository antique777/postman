package cn.microvideo.postman.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

/**
 * excel导出文件列
 *
 * @author xab
 * @date 2021-10-31 17:25
 */
public class ExcelColumn {

    @ExcelProperty("序号")
    @ColumnWidth(10)
    private int sortNumber;
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

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getResponseParameters() {
        return responseParameters;
    }

    public void setResponseParameters(String responseParameters) {
        this.responseParameters = responseParameters;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ExcelColumn{" +
                "sortNumber=" + sortNumber +
                ", interfaceName='" + interfaceName + '\'' +
                ", urlName='" + urlName + '\'' +
                ", description='" + description + '\'' +
                ", parameters='" + parameters + '\'' +
                ", typeName='" + typeName + '\'' +
                ", responseParameters='" + responseParameters + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
