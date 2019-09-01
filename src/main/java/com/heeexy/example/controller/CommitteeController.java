package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.CommitteeService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/committee")
public class CommitteeController {

	@Autowired
	private CommitteeService committeeService;

	/**
	 * 查询委员会列表
	 */
//	@RequiresPermissions("committee:list")
	@GetMapping("/listCommittee")
	public JSONObject listCommittee(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return committeeService.listCommittee(CommonUtil.request2Json(request));
	}

//	@RequiresPermissions("committee:list")
//	@GetMapping("/getCommitteeByContent")
//	public JSONObject getCommitteeByContent(HttpServletRequest request) {
//		System.out.println(CommonUtil.request2Json(request));
//		return committeeService.getCommitteeByContent(CommonUtil.request2Json(request));
//	}

    /**
     * 搜索委员会列表
     */
//    @RequiresPermissions("committee:list")
    @GetMapping("/listCommitteeBySearch")
    public JSONObject listCommitteeBySearch(HttpServletRequest request) {
        return committeeService.listCommitteeBySearch(CommonUtil.request2Json(request));
    }

	/**
	 * 新增委员会
	 */
	@RequiresPermissions("committee:add")
	@PostMapping("/addCommittee")
	public JSONObject addCommittee(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "committee_name,committee_desc");
		return committeeService.addCommittee(requestJson);
	}

	/**
	 * 修改委员会
	 */
	@RequiresPermissions("committee:update")
	@PostMapping("/updateCommittee")
	public JSONObject updateCommittee(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "committee_id,committee_name,committee_desc");
		return committeeService.updateCommittee(requestJson);
	}

	/**
	 * 删除委员会
	 */
	@RequiresPermissions("committee:delete")
	@PostMapping("/deleteCommittee")
	public JSONObject deleteCommittee(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "committee_id");
		return committeeService.deleteCommittee(requestJson);
	}
}
