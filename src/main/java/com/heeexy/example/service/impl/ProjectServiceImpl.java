package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ProjectDao;
import com.heeexy.example.service.ProjectService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: bguan
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;

	/**
	 * 新增项目
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addProject(JSONObject jsonObject) {
		projectDao.addProject(jsonObject);
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb"+CommonUtil.successJson());
		return CommonUtil.successJson();
	}

	/**
	 * 全部项目列表
	 */
	@Override
	public JSONObject listProject(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = projectDao.countProject(jsonObject);
		System.out.println("count:"+count);
		List<JSONObject> list = projectDao.listProject(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 获取所有项目岗位
	 */
	@Override
	public JSONObject getAllProjectPositions(JSONObject jsonObject){
		List<JSONObject> list = projectDao.getAllProjectPositions(jsonObject);
		System.out.println("list.size():" + list.size());
		return CommonUtil.successPage(list);
	}

	/**
	* 根据项目Id，找到项目的详细信息
	*
	* */
	@Override
	public JSONObject getProjectListById(JSONObject jsonObject) {
		List<JSONObject> list = projectDao.getProjectListById(jsonObject);
		System.out.println("我是小昝");
		return CommonUtil.successJson(list);
	}


	/**
	 * 更新项目
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateProject(JSONObject jsonObject) {
		projectDao.updateProject(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除项目
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteProject(JSONObject jsonObject) {
		projectDao.deleteProject(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 按名称查询项目
	 */
	@Override
	public JSONObject getProjectByName(JSONObject jsonObject) {
		List<JSONObject> list = projectDao.getProjectByName(jsonObject);
		System.out.println("list.size():" + list.size());
		if (list.size() >= 1) {
			return CommonUtil.successJson("fail");
		} else {
			return CommonUtil.successJson("success");
		}
	}

	/**
	 * 按项目ID查成员
	 */
	@Override
	public JSONObject getProjectMemberByID(JSONObject jsonObject) {
		List<JSONObject> list = projectDao.getProjectMemberByID(jsonObject);
		System.out.println("list.size():" + list.size());
		return CommonUtil.successPage(list);
	}

	/**
	 * 删除项目成员
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteProjectMember(JSONObject jsonObject){
		projectDao.deleteProjectMember(jsonObject);
		projectDao.deleteProjectStaffPosition(jsonObject);
		return CommonUtil.successJson();
	}

    /**
     * 添加成员到指定ID项目中
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addProjectMember(JSONObject jsonObject) {
		System.out.println("add_member_to_project:jsonObject:"+jsonObject);
		//add_member_to_project:jsonObject:{"positionName":"研发总监","staffIdName":"10033-test02",
		// "deparmentName":"研发部","projectPositionId":-1,"userName":"test02","userId":10033,"projectId":1,"projectPositionName":"项目总监"}
		System.out.println("projectPositionId(Front End): " + jsonObject.getIntValue("projectPositionId"));
		//System.out.println("projectPositionId(Front End): " + jsonObject.getInteger("projectPositionId"));
		System.out.println("projectPositionName(Front End): " + jsonObject.getString("projectPositionName"));
		if(jsonObject.getIntValue("projectPositionId") == -1) {
			System.out.println("Get a new project position name");
			projectDao.addProjectPosition(jsonObject);
			int id = projectDao.getProjectPositionId(jsonObject);
			System.out.println("projectPositionId(DB): "+id);
			jsonObject.put("projectPositionId", id);
		}
		//System.out.println("Prepare to add a member to project!");
		System.out.println("add_member_to_project:jsonObject:"+jsonObject);
        projectDao.addProjectMember(jsonObject);
		projectDao.addProjectStaffPosition(jsonObject);
        return CommonUtil.successJson();
    }

	/**
	 * 搜索项目列表
	 */
	@Override
	public JSONObject listProjectBySearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = projectDao.countProjectBySearch(jsonObject);
		System.out.println("count:"+count);
		List<JSONObject> list = projectDao.listProjectBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 排序部门列表(not used)
	 */
	@Override
	public JSONObject listProjectByOrder(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = projectDao.countProject(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			 list = projectDao.listProjectByOrderAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			System.out.println("DESC..........................");
			 list = projectDao.listProjectByContentDesc(jsonObject);
		}else{
			System.out.println("null..........................");
			list = projectDao.listProject(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 当搜索部门的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部(not used)
	 */
	@Override
	public JSONObject listProjectByContentHaveSearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = projectDao.countProjectBySearch(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = projectDao.listProjectByContentHaveSearchAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			System.out.println("DESC..........................");
			list = projectDao.listProjectByContentHaveSearchDesc(jsonObject);
		}else{
			System.out.println("null..........................");
			list = projectDao.listProject(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 搜索部门的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码(not used)
	 */
	@Override
	public JSONObject getListByOrderCreateTimeHaveSearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = projectDao.countProjectBySearch(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = projectDao.listProjectByOrderCreateTimeHaveSearchAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			System.out.println("DESC..........................");
			list = projectDao.listProjectByOrderCreateTimeHaveSearchDesc(jsonObject);
		}else{
			System.out.println("null..........................");
			list = projectDao.listProject(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 排序部门列表(not used)
	 */
	@Override
	public JSONObject listProjectByOrderCreateTime(JSONObject jsonObject) {
		System.out.println("我是昝道广Create Time！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = projectDao.countProject(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = projectDao.listProjectByOrderCreateTimeAsc(jsonObject);
		}else {
			System.out.println("DESC..........................");
			list = projectDao.listProjectByOrderCreateTimeDesc(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}



}
