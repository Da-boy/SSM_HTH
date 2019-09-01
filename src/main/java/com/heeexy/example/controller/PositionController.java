package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PositionService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: dengchenglong
 * @description: 岗位信息Controller
 */
@RestController
@RequestMapping("/position")
public class PositionController {

	@Autowired
	private PositionService positionService;

	/**
	 * 查询岗位信息列表
	 */
//	@RequiresPermissions("position:list")
	@GetMapping("/listPosition")
	public JSONObject listPosition(HttpServletRequest request) {
		return positionService.listPosition(CommonUtil.request2Json(request));
	}

//	@RequiresPermissions("position:list")
	@GetMapping("/getPositionByContent")
	public JSONObject getPositionByContent(HttpServletRequest request) {
		return positionService.getPositionByContent(CommonUtil.request2Json(request));
	}

    /**
     * 搜索岗位信息列表
     */
//    @RequiresPermissions("position:list")
    @GetMapping("/listPositionBySearch")
    public JSONObject listPositionBySearch(HttpServletRequest request) {
        return positionService.listPositionBySearch(CommonUtil.request2Json(request));
    }

	/**
	 * 新增岗位信息
	 */
	@RequiresPermissions("position:add")
	@PostMapping("/addPosition")
	public JSONObject addPosition(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "position_name, position_desc");
		return positionService.addPosition(requestJson);
	}

	/**
	 * 修改岗位信息
	 */
	@RequiresPermissions("position:update")
	@PostMapping("/updatePosition")
	public JSONObject updatePosition(@RequestBody JSONObject requestJson) {
//		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "id, position_name, position_desc");
		return positionService.updatePosition(requestJson);
	}

	/**
	 * 删除岗位信息
	 */
	@RequiresPermissions("position:delete")
	@PostMapping("/deletePosition")
	public JSONObject deletePosition(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "position_id");
		return positionService.deletePosition(requestJson);
	}
}
