package cn.microvideo.postman.controller;

import cn.microvideo.postman.model.Result;
import cn.microvideo.postman.service.MainService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public Result exchangeToExcel(MultipartFile jsonFile) throws IOException {
        return mainService.exchangeToExcel(jsonFile);
    }

    /**
     * 获取postman接口文档的excel格式
     *
     * @param jsonFile -
     * @return cn.microvideo.postman.model.Result
     * @author xab
     * @date 2021/11/7 16:02
     */
    @PostMapping("/getExcel")
    public void getExcel(MultipartFile jsonFile, HttpServletResponse response) throws IOException {

        System.out.println(jsonFile.getSize());
        System.out.println(jsonFile.getOriginalFilename());
        System.out.println(jsonFile.getContentType());
        System.out.println(jsonFile.getResource().getFilename());
        System.out.println(jsonFile.getResource().getDescription());

        mainService.getExcel(jsonFile,response);
    }

}
