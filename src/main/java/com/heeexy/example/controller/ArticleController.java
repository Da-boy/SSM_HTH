package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zandaoguang
 * @description: 文章相关Controller
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	/**
	 * 查询文章列表
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listArticle")
	public JSONObject listArticle(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return articleService.listArticle(CommonUtil.request2Json(request));
	}

//	@RequiresPermissions("article:list")
	@GetMapping("/getArticleByContent")
	public JSONObject getArticleByContent(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return articleService.getArticleByContent(CommonUtil.request2Json(request));
	}

//	@RequiresPermissions("article:list")
	@GetMapping("/getThemeColorByUserId")
	public JSONObject getThemeColorByUserId(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return articleService.getThemeColorByUserId(CommonUtil.request2Json(request));
	}

//	@RequiresPermissions("article:list")
	@GetMapping("/saveColorFun")
	public JSONObject saveColorFun(HttpServletRequest request) {
		System.out.println(CommonUtil.request2Json(request));
		return articleService.saveColorFun(CommonUtil.request2Json(request));
	}

    /**
     * 搜索文章列表
     */
//    @RequiresPermissions("article:list")
    @GetMapping("/listArticleBySearch")
    public JSONObject listArticleBySearch(HttpServletRequest request) {
        return articleService.listArticleBySearch(CommonUtil.request2Json(request));
    }


	/**
	 * 搜索文章列表
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listArticleByOrder")
	public JSONObject listArticleByOrder(HttpServletRequest request) {
		return articleService.listArticleByOrder(CommonUtil.request2Json(request));
	}


	/**
	 * 搜索文章列表
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listArticleByOrderCreateTime")
	public JSONObject listArticleByOrderCreateTime(HttpServletRequest request) {
		return articleService.listArticleByOrderCreateTime(CommonUtil.request2Json(request));
	}


	/**
	 * 当搜索文章的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/listArticleByContentHaveSearch")
	public JSONObject listArticleByContentHaveSearch(HttpServletRequest request) {
		return articleService.listArticleByContentHaveSearch(CommonUtil.request2Json(request));
	}


	/**
	 * 当搜索文章的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码
	 */
//	@RequiresPermissions("article:list")
	@GetMapping("/getListByOrderCreateTimeHaveSearch")
	public JSONObject getListByOrderCreateTimeHaveSearch(HttpServletRequest request) {
		return articleService.getListByOrderCreateTimeHaveSearch(CommonUtil.request2Json(request));
	}

	/**
	 * 新增文章
	 */
	@RequiresPermissions("article:add")
	@PostMapping("/addArticle")
	public JSONObject addArticle(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "content");
		return articleService.addArticle(requestJson);
	}

	/**
	 * 修改文章
	 */
	@RequiresPermissions("article:update")
	@PostMapping("/updateArticle")
	public JSONObject updateArticle(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "id,content");
		return articleService.updateArticle(requestJson);
	}

	/**
	 * 修改文章
	 */
	@RequiresPermissions("article:delete")
	@PostMapping("/deleteArticle")
	public JSONObject deleteArticle(@RequestBody JSONObject requestJson) {
		System.out.println("requestJson:"+requestJson);
		CommonUtil.hasAllRequired(requestJson, "id,content,deleteStatus");
		return articleService.deleteArticle(requestJson);
	}


	/**
	 * 根据id删除文章
	 */
	@RequiresPermissions("article:delete")
	@PostMapping("/deleteArticleById")
	public JSONObject deleteArticleById(@RequestBody JSONObject requestJson) {
		System.out.println("articleId:"+requestJson);
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//		CommonUtil.hasAllRequired(requestJson, "id,content,deleteStatus");
//		return articleService.deleteArticle(requestJson);
		return new JSONObject();
	}
}
