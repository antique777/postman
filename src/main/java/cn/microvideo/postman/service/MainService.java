package cn.microvideo.postman.service;

import cn.microvideo.postman.model.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

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
     * @param jsonFile -
     * @return void
     * @author xab
     * @date 2021/10/31 17:36
     */
    Result exchangeToExcel(MultipartFile jsonFile) throws JsonProcessingException;
}
