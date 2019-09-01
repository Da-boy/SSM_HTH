package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: dengchenglong
 * @description: 公司信息Dao层
 */
public interface CompanyDao {
	/**
	 * 新增公司信息
	 */
	int addCompany(JSONObject jsonObject);

	/**
	 * 统计岗位信息总数
	 */
	int countCompany(JSONObject jsonObject);

	/**
	 * 统计条件文章的总数
	 */

    /**
	 *
	 * 判断公司名称是否重复
	 */
	List<JSONObject> getCompanyByContent(JSONObject jsonObject);
	List<JSONObject> getTelephoneByContent(JSONObject jsonObject);
	List<JSONObject> getAddressByContent(JSONObject jsonObject);
	List<JSONObject> getRegisterIdByContent(JSONObject jsonObject);
	List<JSONObject> getTaxpayerByContent(JSONObject jsonObject);
	List<JSONObject> getOrganizationalByContent(JSONObject jsonObject);
	List<JSONObject> getSocialByContent(JSONObject jsonObject);
	List<JSONObject> getMailByContent(JSONObject jsonObject);
	List<JSONObject> getFaxByContent(JSONObject jsonObject);

	int countCompanyBySearch(JSONObject jsonObject);

	/**
	 * 公司信息列表
	 */
	List<JSONObject> listCompany(JSONObject jsonObject);

	/**
	 * 条件公司信息列表
	 */
	List<JSONObject> listCompanyBySearch(JSONObject jsonObject);

	/**
	 * 更新公司信息
	 */
	int updateCompany(JSONObject jsonObject);

	/**
	 * 删除公司信息
	 */

	int deleteCompany(JSONObject jsonObject);
}