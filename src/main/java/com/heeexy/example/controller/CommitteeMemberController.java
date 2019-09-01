package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.CommitteeMemberService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/committeeMember")
public class CommitteeMemberController {

	@Autowired
	private CommitteeMemberService committeeMemberService;

	/**
	 * 查询委员会成员列表
	 */
//	@RequiresPermissions("committee_member:list")
	@GetMapping("/listCommitteeMember")
	public JSONObject listCommitteeMember(HttpServletRequest request) {
		System.out.println("request: " + CommonUtil.request2Json(request));
		return committeeMemberService.listCommitteeMember(CommonUtil.request2Json(request));
	}

	/**
	 * 查询委员会成员列表
	 */
//	@RequiresPermissions("committee_member:list")
	@GetMapping("/listCommitteeMemberByCommittee")
	public JSONObject listCommitteeMemberByCommittee(HttpServletRequest request) {
		System.out.println("request: " + CommonUtil.request2Json(request));
		return committeeMemberService.listCommitteeMemberByCommittee(CommonUtil.request2Json(request));
	}

    /**
     * 搜索委员会成员列表
     */
//    @RequiresPermissions("committee_member:list")
    @GetMapping("/listCommitteeMemberByCommitteeAndSearch")
    public JSONObject listCommitteeMemberByCommitteeAndSearch(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
        return committeeMemberService.listCommitteeMemberByCommitteeAndSearch(CommonUtil.request2Json(request));
    }

	/**
	 * 新增委员会成员
	 */
	@RequiresPermissions("committee_member:add")
	@PostMapping("/addCommitteeMember")
	public JSONObject addCommitteeMember(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "is_staff,is_chairman");
		return committeeMemberService.addCommitteeMember(requestJson);
	}

	/**
	 * 修改委员会成员
	 */
	@RequiresPermissions("committee_member:update")
	@PostMapping("/updateCommitteeMember")
	public JSONObject updateCommitteeMember(@RequestBody JSONObject requestJson) {
		return committeeMemberService.updateCommitteeMember(requestJson);
	}

	/**
	 * 删除委员会成员
	 */
	@RequiresPermissions("committee_member:delete")
	@PostMapping("/deleteCommitteeMember")
	public JSONObject deleteCommitteeMember(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "committee_member_id");
		return committeeMemberService.deleteCommitteeMember(requestJson);
	}

	/**
	 * 删除委员会成员
	 */
	@RequiresPermissions("committee_member:delete")
	@PostMapping("/deleteMemberFromCommittee")
	public JSONObject deleteMemberFromCommittee(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "committee_member_id");
		return committeeMemberService.deleteMemberFromCommittee(requestJson);
	}
}
