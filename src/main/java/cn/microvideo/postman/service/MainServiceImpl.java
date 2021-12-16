package cn.microvideo.postman.service;

import cn.microvideo.postman.model.ExcelColumn;
import cn.microvideo.postman.model.Result;
import cn.microvideo.postman.param.*;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
    private static final String GET = "GET";
    private static final String POST = "POST";

    private static final int STRING_LANG_MAX = 1500;
    private static final int STRING_NEED_MAX = 1400;

    @Override
    public Result exchangeToExcel(MultipartFile jsonFile) throws JsonProcessingException {
        num = 0;
        PostmanJson postmanJson = new ObjectMapper().readValue(readJsonFile(jsonFile), PostmanJson.class);
        List<ExcelColumn> excelColumnList = new ArrayList<>(128);
        excelColumnList.add(new ExcelColumn(){{
            this.setFolderName(postmanJson.getInfo().getName());
        }});
        writeExcel(excelColumnList, postmanJson.getItem());
        EasyExcelFactory.write(postmanJson.getInfo().getName() + ".xlsx", ExcelColumn.class)
                //.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(getTableHeadStyle())
                .sheet(postmanJson.getInfo().getName()).doWrite(excelColumnList);
        return new Result(excelColumnList);
    }

    @Override
    public void getExcel(MultipartFile jsonFile, HttpServletResponse response) throws IOException {
        num = 0;
        PostmanJson postmanJson = new ObjectMapper().readValue(readJsonFile(jsonFile), PostmanJson.class);
        List<ExcelColumn> excelColumnList = new ArrayList<>(128);
        writeExcel(excelColumnList, postmanJson.getItem());

        response.reset();
        response.setCharacterEncoding("UTF-8");
        //响应内容格式
        response.setContentType("application/vnd.ms-excel");
        //设置文件名
        String fileName =jsonFile.getName() + ".xlsx";

        try {
            //设置前端下载文件名
            response.setHeader("Content-disposition", "attachment;filename=" +new String(fileName.getBytes("UTF-8"), "GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //向前端写入文件流流
        EasyExcelFactory.write(response.getOutputStream(), ExcelColumn.class)
                .autoCloseStream(true)
                .registerWriteHandler(getTableHeadStyle())
                .sheet(postmanJson.getInfo().getName()).doWrite(excelColumnList);

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
                excelColumnList.add(new ExcelColumn(){{
                    this.setFolderName(item.getName());
                }});
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
        if (body.length() > STRING_LANG_MAX) {
            return body.substring(0, STRING_NEED_MAX);
        }
        return body;
    }

    private String parseParameters(Request request) {
        if (request.getMethod().equals(GET)) {
            try {
                return parseGetParameters(request.getUrl().getQuery());
            } catch (NullPointerException e) {
                System.err.println("parseParameters ：" + request);
                e.printStackTrace();
                return "";
            }
        } else if (request.getMethod().equals(POST)) {
            return parsePostParameters(request);
        } else {
            throw new IllegalArgumentException("暂时只支持get和post请求");
        }
    }

    private String parsePostParameters(Request request) {
        if (request.getBody() == null) {
            return parseGetParameters(request.getUrl().getQuery());
        }
        if (request.getBody().getRaw() == null) {
            List<Formdata> formdata1 = request.getBody().getFormdata();
            if (CollectionUtils.isEmpty(formdata1)) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (Formdata formdata : formdata1) {
                sb.append(formdata.getKey()).append("  :  ").append(formdata.getDescription())
                        .append("(").append(formdata.getType()).append(") \r\n");
            }
            if (sb.length() > STRING_LANG_MAX) {
                return sb.substring(0, STRING_NEED_MAX);
            }
            return sb.toString();
        } else {
            if (request.getBody().getRaw().length() > STRING_LANG_MAX) {
                return request.getBody().getRaw().substring(0, STRING_NEED_MAX);
            }
            return request.getBody().getRaw();
        }
    }

    private String parseGetParameters(List<Query> query) {
        if (CollectionUtils.isEmpty(query)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Query query1 : query) {
            if (query1.getDisabled() != null && query1.getDisabled()) {
                continue;
            }
            sb.append(query1.getKey()).append("   :   ")
                    //.append(StringUtils.isBlank(query1.getValue()) ? "" : query1.getValue()).append(" ")
                    .append(StringUtils.isBlank(query1.getDescription()) ? "" : query1.getDescription())
                    .append("\r\n");
        }
        if (sb.length() > STRING_LANG_MAX) {
            return sb.substring(0, STRING_NEED_MAX);
        }
        return sb.toString();
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
            StringBuilder sb = new StringBuilder();
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
