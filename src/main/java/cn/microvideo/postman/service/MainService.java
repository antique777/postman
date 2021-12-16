package cn.microvideo.postman.service;

import cn.microvideo.postman.model.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 主接口层
 *
 * @author xab
 * @date 2021-10-31 17:30
 */
public interface MainService {

    /**
     * 将postman接口文档导出为excel
     *
     * @throws JsonProcessingException -
     * @param jsonFile -
     * @return void
     * @author xab
     * @date 2021/10/31 17:36
     */
    Result exchangeToExcel(MultipartFile jsonFile) throws JsonProcessingException;

    /**
     * 获取postman接口文档的excel格式
     *
     * @throws IOException -
     * @param jsonFile -
     * @param response -
     * @author xab
     * @date 2021/11/7 20:59
     */
    void getExcel(MultipartFile jsonFile, HttpServletResponse response) throws IOException;
}
