package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.CompanyDao;
import com.heeexy.example.service.CompanyService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: dengchenglong
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	/**
	 * 新增公司信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addCompany(JSONObject jsonObject) {
		companyDao.addCompany(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 全部岗位信息列表
	 */
	@Override
	public JSONObject listCompany(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = companyDao.countCompany(jsonObject);
		List<JSONObject> list = companyDao.listCompany(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	@Override
	public JSONObject getCompanyByContent(JSONObject jsonObject) {
		List<JSONObject> list = companyDao.getCompanyByContent(jsonObject);
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}
	}
	@Override
	public JSONObject getTelephoneByContent(JSONObject jsonObject) {
		List<JSONObject> list = companyDao.getTelephoneByContent(jsonObject);
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}
	}
	@Override
	public JSONObject getAddressByContent(JSONObject jsonObject) {
		List<JSONObject> list = companyDao.getAddressByContent(jsonObject);
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}
	}
	@Override
	public JSONObject getRegisterIdByContent(JSONObject jsonObject) {
		List<JSONObject> list = companyDao.getRegisterIdByContent(jsonObject);
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}
	}
	@Override
	public JSONObject getTaxpayerByContent(JSONObject jsonObject) {
		List<JSONObject> list = companyDao.getTaxpayerByContent(jsonObject);
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}
	}
	@Override
	public JSONObject getOrganizationalByContent(JSONObject jsonObject) {
		List<JSONObject> list = companyDao.getOrganizationalByContent(jsonObject);
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}
	}
	@Override
	public JSONObject getSocialByContent(JSONObject jsonObject) {
		List<JSONObject> list = companyDao.getSocialByContent(jsonObject);
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}
	}
	@Override
	public JSONObject getMailByContent(JSONObject jsonObject) {
		List<JSONObject> list = companyDao.getMailByContent(jsonObject);
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}
	}
	@Override
	public JSONObject getFaxByContent(JSONObject jsonObject) {
		List<JSONObject> list = companyDao.getFaxByContent(jsonObject);
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}
	}

	/**
	 * 搜索岗位信息列表
	 */
	@Override
	public JSONObject listCompanyBySearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = companyDao.countCompanyBySearch(jsonObject);
		List<JSONObject> list = companyDao.listCompanyBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 更新公司信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateCompany(JSONObject jsonObject) {
		companyDao.updateCompany(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除岗位信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteCompany(JSONObject jsonObject) {
		companyDao.deleteCompany(jsonObject);
		return CommonUtil.successJson();
	}
}