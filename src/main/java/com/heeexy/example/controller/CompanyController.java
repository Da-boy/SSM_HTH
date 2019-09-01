package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.CompanyService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: dengchenglong
 * @description: 公司信息Controller
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	/**
	 * 查询岗位信息列表
	 */
//	@RequiresPermissions("company:list")
	@GetMapping("/listCompany")
	public JSONObject listCompany(HttpServletRequest request) {
		return companyService.listCompany(CommonUtil.request2Json(request));
	}

//	@RequiresPermissions("company:list")
	@GetMapping("/getCompanyByContent")
	public JSONObject getCompanyByContent(HttpServletRequest request) {
		return companyService.getCompanyByContent(CommonUtil.request2Json(request));
	}
//	@RequiresPermissions("company:list")
	@GetMapping("/getTelephoneByContent")
	public JSONObject getTelephoneByContent(HttpServletRequest request) {
		return companyService.getTelephoneByContent(CommonUtil.request2Json(request));
	}
//	@RequiresPermissions("company:list")
	@GetMapping("/getAddressByContent")
	public JSONObject getAddressByContent(HttpServletRequest request) {
		return companyService.getAddressByContent(CommonUtil.request2Json(request));
	}
//	@RequiresPermissions("company:list")
	@GetMapping("/getRegisterIdByContent")
	public JSONObject getRegisterIdByContent(HttpServletRequest request) {
		return companyService.getRegisterIdByContent(CommonUtil.request2Json(request));
	}
//	@RequiresPermissions("company:list")
	@GetMapping("/getTaxpayerByContent")
	public JSONObject getTaxpayerByContent(HttpServletRequest request) {
		return companyService.getTaxpayerByContent(CommonUtil.request2Json(request));
	}
//	@RequiresPermissions("company:list")
	@GetMapping("/getOrganizationalByContent")
	public JSONObject getOrganizationalByContent(HttpServletRequest request) {
		return companyService.getOrganizationalByContent(CommonUtil.request2Json(request));
	}
//	@RequiresPermissions("company:list")
	@GetMapping("/getSocialByContent")
	public JSONObject getSocialByContent(HttpServletRequest request) {
		return companyService.getSocialByContent(CommonUtil.request2Json(request));
	}
//	@RequiresPermissions("company:list")
	@GetMapping("/getMailByContent")
	public JSONObject getMailByContent(HttpServletRequest request) {
		return companyService.getMailByContent(CommonUtil.request2Json(request));
	}
//	@RequiresPermissions("company:list")
	@GetMapping("/getFaxByContent")
	public JSONObject getFaxByContent(HttpServletRequest request) {
		return companyService.getFaxByContent(CommonUtil.request2Json(request));
	}

    /**
     * 搜索+排序公司信息列表
     */
//    @RequiresPermissions("company:list")
    @GetMapping("/listCompanyBySearch")
    public JSONObject listCompanyBySearch(HttpServletRequest request) {
        return companyService.listCompanyBySearch(CommonUtil.request2Json(request));
    }

	/**
	 * 新增公司信息
	 */
	@RequiresPermissions("company:add")
	@PostMapping("/addCompany")
	public JSONObject addCompany(@RequestBody JSONObject requestJson) {
		//CommonUtil.hasAllRequired(requestJson, "company_name, telephone, address, register_id, taxpayer_identification_number, organizational_code");
		CommonUtil.hasAllRequired(requestJson, "company_name"); //采购订单模块也有调用
		return companyService.addCompany(requestJson);
	}

	/**
	 * 修改公司信息
	 */
	@RequiresPermissions("company:update")
	@PostMapping("/updateCompany")
	public JSONObject updateCompany(@RequestBody JSONObject requestJson) {
//		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "company_name, telephone, address, register_id, taxpayer_identification_number, organizational_code");
		return companyService.updateCompany(requestJson);
	}

	/**
	 * 删除公司信息
	 */
	@RequiresPermissions("company:delete")
	@PostMapping("/deleteCompany")
	public JSONObject deleteCompany(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "company_id");
		return companyService.deleteCompany(requestJson);
	}
}
