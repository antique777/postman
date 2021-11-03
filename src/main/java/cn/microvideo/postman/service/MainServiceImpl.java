package cn.microvideo.postman.service;

import cn.microvideo.postman.model.ExcelColumn;
import cn.microvideo.postman.model.Result;
import cn.microvideo.postman.param.*;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现层
 *
 * @author xab
 * @date 2021-10-31 17:30
 */
@Service
public class MainServiceImpl implements MainService {

    private int num;

    @Override
    public Result exchangeToExcel(MultipartFile jsonFile) throws JsonProcessingException {
        num = 0;
        PostmanJson postmanJson = new ObjectMapper().readValue(readJsonFile(jsonFile), PostmanJson.class);
        List<ExcelColumn> excelColumnList = new ArrayList<>(128);
        writeExcel(excelColumnList, postmanJson.getItem());
        EasyExcelFactory.write(postmanJson.getInfo().getName() + ".xlsx", ExcelColumn.class)
                //.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(getTableHeadStyle())
                .sheet(postmanJson.getInfo().getName()).doWrite(excelColumnList);
        return new Result(excelColumnList);
    }

    private HorizontalCellStyleStrategy getTableHeadStyle() {
        WriteCellStyle headStyle = new WriteCellStyle() {{
            this.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            this.setHorizontalAlignment(HorizontalAlignment.CENTER);
            this.setWriteFont(new WriteFont() {{
                this.setFontHeightInPoints((short) 20);
            }});
        }};
        WriteCellStyle contentStyle = new WriteCellStyle() {{
            this.setWriteFont(new WriteFont() {{
                this.setFontHeightInPoints((short) 15);
            }});
            this.setVerticalAlignment(VerticalAlignment.CENTER);
            this.setWrapped(true);
            this.setShrinkToFit(true);
        }};
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色
        // .头默认了 FillPatternType所以可以不指定
        //contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND)
        return new HorizontalCellStyleStrategy(headStyle, contentStyle);
    }

    private void writeExcel(List<ExcelColumn> excelColumnList, List<Item> items) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        for (Item item : items) {
            if (item.getRequest() == null) {
                writeExcel(excelColumnList, item.getItem());
            } else {
                ExcelColumn excelColumn = new ExcelColumn();
                excelColumn.setSortNumber(++num);
                excelColumn.setDescription(
                        StringUtils.isBlank(item.getRequest().getDescription()) ? "" : item.getRequest().getDescription());
                excelColumn.setInterfaceName(item.getName());
                excelColumn.setParameters(parseParameters(item.getRequest()));
                excelColumn.setTypeName(item.getRequest().getMethod());
                excelColumn.setResponseParameters(parseResponseParameters(item.getResponse()));
                excelColumn.setUrlName(parseUrlName(item.getRequest()));
                excelColumnList.add(excelColumn);
            }
        }
    }

    private String parseResponseParameters(List<Response> response) {
        if (CollectionUtils.isEmpty(response)) {
            return "";
        }
        String body = response.get(0).getBody();
        if (body.length() > 1500) {
            return body.substring(0, 1400);
        }
        return body;
    }

    private String parseParameters(Request request) {
        if (request.getMethod().equals("GET")) {
            try {
                return parseGetParameters(request.getUrl().getQuery());
            } catch (NullPointerException e) {
                System.err.println("parseParameters ：" + request.toString());
                e.printStackTrace();
                return "";
            }
        } else {
            return parsePostParameters(request);
        }
    }

    private String parsePostParameters(Request request) {
        if (request.getBody() == null) {
            parseGetParameters(request.getUrl().getQuery());
            return "";
        }
        if (request.getBody().getRaw() == null) {
            List<Formdata> formdata1 = request.getBody().getFormdata();
            if (CollectionUtils.isEmpty(formdata1)) {
                return "";
            }
            StringBuffer sb = new StringBuffer();
            for (Formdata formdata : formdata1) {
                sb.append(formdata.getKey()).append("  :  ").append(formdata.getDescription())
                        .append("(").append(formdata.getType()).append(") \r\n");
            }
            if (sb.length() > 1500) {
                return sb.toString().substring(0, 1400);
            }
            return sb.toString();
        } else {
            if (request.getBody().getRaw().length() > 1500) {
                return request.getBody().getRaw().substring(0, 1400);
            }
            return request.getBody().getRaw();
        }
    }

    private String parseGetParameters(List<Query> query) {
        if (CollectionUtils.isEmpty(query)) {
            return "";
        }
        StringBuffer ss = new StringBuffer();
        for (Query query1 : query) {
            if (query1.getDisabled() != null && query1.getDisabled()) {
                continue;
            }
            ss.append(query1.getKey()).append("   :   ")
                    //.append(StringUtils.isBlank(query1.getValue()) ? "" : query1.getValue()).append(" ")
                    .append(StringUtils.isBlank(query1.getDescription()) ? "" : query1.getDescription())
                    .append("\r\n");
        }
        if (ss.length() > 1500) {
            return ss.toString().substring(0, 1400);
        }
        return ss.toString();
    }

    private String parseUrlName(Request request) {
        try {
            String raw = request.getUrl().getRaw();
            raw = raw.split("\\?")[0];
            if (raw.contains("{{base}}")) {
                raw = raw.substring(8);
            }
            return raw;
            //return "/hrm" + raw
        } catch (NullPointerException e) {
            System.err.println("parseUrlName : " + request.toString());
            e.printStackTrace();
            return "";
        }
    }

    private String readJsonFile(MultipartFile jsonFile) {
        try (InputStream inputStream = jsonFile.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            System.err.println("readJsonFile : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}
