package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.DepartmentDao;
import com.heeexy.example.service.DepartmentService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: bguan
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentDao departmentDao;

	/**
	 * 新增部门
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addDepartment(JSONObject jsonObject) {
		departmentDao.addDepartment(jsonObject);
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb"+CommonUtil.successJson());
		return CommonUtil.successJson();
	}

	/**
	 * 全部部门列表
	 */
	@Override
	public JSONObject listDepartment(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = departmentDao.countDepartment(jsonObject);
		System.out.println("count:"+count);
		List<JSONObject> list = departmentDao.listDepartment(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 子部门列表
	 */
	@Override
	public JSONObject listDepartmentByParent(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject1:"+jsonObject);
		int count = departmentDao.countDepartmentByParent(jsonObject);
		System.out.println("count:"+count);
		List<JSONObject> list = departmentDao.listDepartmentByParent(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 搜索部门列表
	 */
	@Override
	public JSONObject listDepartmentBySearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = departmentDao.countDepartmentBySearch(jsonObject);
		System.out.println("count:"+count);
		List<JSONObject> list = departmentDao.listDepartmentBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 排序部门列表
	 */
	@Override
	public JSONObject listDepartmentByOrder(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = departmentDao.countDepartment(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			 list = departmentDao.listDepartmentByOrderAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			System.out.println("DESC..........................");
			 list = departmentDao.listDepartmentByContentDesc(jsonObject);
		}else{
			System.out.println("null..........................");
			list = departmentDao.listDepartment(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 当搜索部门的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部
	 */
	@Override
	public JSONObject listDepartmentByContentHaveSearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = departmentDao.countDepartmentBySearch(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = departmentDao.listDepartmentByContentHaveSearchAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			System.out.println("DESC..........................");
			list = departmentDao.listDepartmentByContentHaveSearchDesc(jsonObject);
		}else{
			System.out.println("null..........................");
			list = departmentDao.listDepartment(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 搜索部门的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码
	 */
	@Override
	public JSONObject getListByOrderCreateTimeHaveSearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = departmentDao.countDepartmentBySearch(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = departmentDao.listDepartmentByOrderCreateTimeHaveSearchAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			System.out.println("DESC..........................");
			list = departmentDao.listDepartmentByOrderCreateTimeHaveSearchDesc(jsonObject);
		}else{
			System.out.println("null..........................");
			list = departmentDao.listDepartment(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 排序部门列表
	 */
	@Override
	public JSONObject listDepartmentByOrderCreateTime(JSONObject jsonObject) {
		System.out.println("我是昝道广Create Time！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = departmentDao.countDepartment(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = departmentDao.listDepartmentByOrderCreateTimeAsc(jsonObject);
		}else {
			System.out.println("DESC..........................");
			list = departmentDao.listDepartmentByOrderCreateTimeDesc(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 更新部门
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateDepartment(JSONObject jsonObject) {
		departmentDao.updateDepartment(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除部门
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteDepartment(JSONObject jsonObject) {
		departmentDao.deleteDepartment(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 按名称查询部门
	 */
	@Override
	public JSONObject getDepartmentByName(JSONObject jsonObject) {
		List<JSONObject> list = departmentDao.getDepartmentByName(jsonObject);
		System.out.println("list.size():" + list.size());
		if (list.size() >= 1) {
			return CommonUtil.successJson("fail");
		} else {
			return CommonUtil.successJson("success");
		}
	}
}
