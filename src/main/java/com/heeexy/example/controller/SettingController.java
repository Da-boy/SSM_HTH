package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.SettingService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: bguan
 * @description: 系统中设置相关
 */
@RestController
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    /**getDefaultPageNum
     * 自动催账设置和读取
     */
    @RequiresPermissions("article:add")
    @PostMapping("/setAutoUrgeBill")
    public JSONObject setAutoUrgeBill(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "autoUrgeBill, weekNum, warningMethod");
        return settingService.setAutoUrgeBill(requestJson);
    }

//    @RequiresPermissions("article:list")
    @GetMapping("/getAutoUrgeBill")
    public JSONObject getAutoUrgeBill(HttpServletRequest request) {
        System.out.println(CommonUtil.request2Json(request));
        return settingService.getAutoUrgeBill(CommonUtil.request2Json(request));
    }



    /**
     * 保存打印方案
     */
    @PostMapping("/setPrintScheme")
    public JSONObject setPrintScheme(@RequestBody JSONObject requestJson) {
        System.out.println("setPrintScheme, requestJson = " + requestJson);
        CommonUtil.hasAllRequired(requestJson, "staff_id, scheme_name");
        return settingService.setPrintScheme(requestJson);
    }

    /**
     * 读取打印方案
     */
//    @RequiresPermissions("product_order:list")
    @GetMapping("/getPrintScheme")
    public JSONObject getPrintScheme(HttpServletRequest request) {
        System.out.println("getPrintScheme, request = " + CommonUtil.request2Json(request));
        return settingService.getPrintScheme(CommonUtil.request2Json(request));
    }

    /**
     * 保存全局设置
     */
    @PostMapping("/setGlobalConfig")
    public JSONObject setGlobalConfig(@RequestBody JSONObject requestJson) {
        System.out.println("setGlobalConfig, requestJson = " + requestJson);
        CommonUtil.hasAllRequired(requestJson, "user_id");
        return settingService.setGlobalConfig(requestJson);
    }

    /**
     * 读取全局设置
     */
//    @RequiresPermissions("article:list")
    @GetMapping("/getGlobalConfig")
    public JSONObject getGlobalConfig(HttpServletRequest request) {
        System.out.println("getGlobalConfig, request = " + CommonUtil.request2Json(request));
        return settingService.getGlobalConfig(CommonUtil.request2Json(request));
    }


//    @RequiresPermissions("article:list")
    @GetMapping("/getThemeColorByUserId")
    public JSONObject getThemeColorByUserId(HttpServletRequest request) {
        System.out.println(CommonUtil.request2Json(request));
        return settingService.getThemeColorByUserId(CommonUtil.request2Json(request));
    }

//    @RequiresPermissions("article:list")
    @GetMapping("/saveColorFun")
    public JSONObject saveColorFun(HttpServletRequest request) {
        System.out.println(CommonUtil.request2Json(request));
        return settingService.saveColorFun(CommonUtil.request2Json(request));
    }

    /**
     * 各模块主页默认显示数据条数的设置
     */
    @GetMapping("/getDefaultPageNum")
    public JSONObject getDefPageNum(HttpServletRequest request) {
        System.out.println(CommonUtil.request2Json(request));
        return settingService.getDefPageNum(CommonUtil.request2Json(request));
    }

    @PostMapping("/setDefaultPageNum")
    public JSONObject updateDefPageNum(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "user_id, mod_name, page_num");
        return settingService.updateDefPageNum(requestJson);
    }


//    @RequiresPermissions("article:list")
    @GetMapping("/getTableDisplayByUserId")
    public JSONObject getTableDisplayByUserId(HttpServletRequest request) {
        System.out.println(CommonUtil.request2Json(request));
        return settingService.getTableDisplayByUserId(CommonUtil.request2Json(request));
    }


    /**
     * 新增
     */
//    @RequiresPermissions("article:add")
    @PostMapping("/addTableDisplayByUserId")
    public JSONObject addTableDisplayByUserId(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "scheme_name");
        return settingService.addTableDisplayByUserId(requestJson);
    }

//    @RequiresPermissions("article:add")
    @PostMapping("/insertTableDisplayByUserId")
    public JSONObject insertTableDisplayByUserId(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "scheme_name");
        return settingService.insertTableDisplayByUserId(requestJson);
    }
    /**
     * 修改
     */
//    @RequiresPermissions("article:update")
    @PostMapping("/setTableDisplayByUserId")
    public JSONObject setTableDisplayByUserId(@RequestBody JSONObject requestJson) {
        System.out.println("requestJson:"+requestJson);
        CommonUtil.hasAllRequired(requestJson, "scheme_name");
        return settingService.setTableDisplayByUserId(requestJson);
    }
}
