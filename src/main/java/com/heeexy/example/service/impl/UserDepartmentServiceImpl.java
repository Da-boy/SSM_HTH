package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PersonnelDao;
import com.heeexy.example.dao.UserDao;
import com.heeexy.example.service.PersonnelService;
import com.heeexy.example.service.UserService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author: zandaoguang
 * @description: 用户/角色/权限
 */
@Service
public class UserDepartmentServiceImpl implements PersonnelService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private PersonnelDao personnelDao;


	@Override
	public JSONObject updateDepartments(JSONObject jsonObject) {
//		List preDepts = (ArrayList) jsonObject.get("preDepartment");
		JSONArray preDepartment = jsonObject.getJSONArray("preDepartment");
		JSONArray value = jsonObject.getJSONArray("value");
		JSONArray position = jsonObject.getJSONArray("positionList");

		System.out.println("preDepartment:"+preDepartment);
		System.out.println("value:"+value);




		//算法：去重操作，优化小算法

		for(int i = 0 ; i < preDepartment.size() ; i++ ){
			for(int j = 0 ; j < value.size() ; j++){
//				System.out.println("i:" + i);
//				System.out.println("j:"+j);
				System.out.println(preDepartment.getIntValue(i));
				System.out.println(value.getIntValue(j));
//				System.out.println("pre:"+preDepartment);
//				System.out.println("value:"+value);
//				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx");
				if(preDepartment.getIntValue(i) == value.getIntValue(j)){
					preDepartment.remove(i);
					value.remove(j);
					i = -1;
					j = -1;
					break;
				}
			}
		}

		for (int i = 0 ; i < value.size() ; i++){
			System.out.println("value:" + value.getIntValue(i));
			JSONObject valueJson = new JSONObject();
			valueJson.put("deptId",value.getIntValue(i));
			valueJson.put("userId",jsonObject.getIntValue("userId"));
			valueJson.put("posiId",jsonObject.getIntValue("posiId"));
//			System.out.println("xxxxxxxttttttttttttttttttttttt");
//			System.out.println(value.getIntValue(i));
//			System.out.println(jsonObject.getIntValue("userId"));
//			System.out.println(jsonObject.getIntValue("posiId"));
//			valueJson.put("positionId",jsonObject.getIntValue(""));
			personnelDao.addDept(valueJson);
//			JSONObject valueJson = value.getJSONObject(i);
			System.out.println(valueJson);
		}

		for(int i = 0 ; i < preDepartment.size() ;i++){
			System.out.println("pre:"+preDepartment.get(i));
			JSONObject preJson = new JSONObject();
			preJson.put("deptId",preDepartment.getIntValue(i));
			preJson.put("userId",jsonObject.getIntValue("userId"));
			System.out.println(preJson);
			personnelDao.delDeptById(preJson);
//			personnelDao.deleteDepartment()
		}



		return CommonUtil.successJson("很好啊");
	}
}
