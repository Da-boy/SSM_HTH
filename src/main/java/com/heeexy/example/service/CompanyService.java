package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: dengchenglong
 */
public interface CompanyService {
	/**
	 * 新增公司信息
	 */
	JSONObject addCompany(JSONObject jsonObject);

	/**
	 * 公司信息列表
	 */
	JSONObject listCompany(JSONObject jsonObject);

    /**
     *
	 * 根据公司名称判断是否重复
	 */
	JSONObject getCompanyByContent(JSONObject request2Json);
	JSONObject getTelephoneByContent(JSONObject request2Json);
	JSONObject getAddressByContent(JSONObject request2Json);
	JSONObject getRegisterIdByContent(JSONObject request2Json);
	JSONObject getTaxpayerByContent(JSONObject request2Json);
	JSONObject getOrganizationalByContent(JSONObject request2Json);
	JSONObject getSocialByContent(JSONObject request2Json);
	JSONObject getMailByContent(JSONObject request2Json);
	JSONObject getFaxByContent(JSONObject request2Json);


	/**
	 * 搜索列表
	 */

	JSONObject listCompanyBySearch(JSONObject jsonObject);

	/**
	 * 更新公司信息
	 */
	JSONObject updateCompany(JSONObject jsonObject);

	/**
	 * 删除公司信息
	 */
	JSONObject deleteCompany(JSONObject jsonObject);
}