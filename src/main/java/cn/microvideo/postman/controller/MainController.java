package cn.microvideo.postman.controller;

import cn.microvideo.postman.model.Result;
import cn.microvideo.postman.service.MainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 主控制器
 *
 * @author xab
 * @date 2021-10-31 17:29
 */
@RestController
@RequestMapping("/controller")
public class MainController {

    @Resource
    private MainService mainService;

    /**
     * 将postman接口文档导出为excel
     *
     * @param jsonFile -
     * @return void
     * @author xab
     * @date 2021/10/31 17:35
     */
    @PostMapping("/exchangeToExcel")
    public Result exchangeToExcel(MultipartFile jsonFile) throws JsonProcessingException {
        return mainService.exchangeToExcel(jsonFile);
    }

}
