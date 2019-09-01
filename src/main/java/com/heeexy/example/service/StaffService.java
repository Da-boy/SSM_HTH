package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: zandaoguang
 */
public interface StaffService {
	/**
	 * 新增文章
	 */
	JSONObject addArticle(JSONObject jsonObject);

	/**
	 * 文章列表
	 */
	JSONObject listStaff(JSONObject jsonObject);

	/**
	 * 搜索列表
	 */

	JSONObject listArticleBySearch(JSONObject jsonObject);

	/**
	 * 按照内容排序列表
	 */
	JSONObject listArticleByOrder(JSONObject jsonObject);

	/**
	 * 当搜索文章的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部，按照内容排序列表
	 */
	JSONObject listArticleByContentHaveSearch(JSONObject jsonObject);



	/**
	 * 当搜索文章的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码
	 */
	JSONObject getListByOrderCreateTimeHaveSearch(JSONObject jsonObject);
	/**
	 * 按照创建时间排序列表，默认搜索全部
	 */
	JSONObject listArticleByOrderCreateTime(JSONObject jsonObject);

	/**
	 * 更新文章
	 */
	JSONObject updateArticle(JSONObject jsonObject);

	/**
	 * 删除文章
	 */
	
	JSONObject deleteArticle(JSONObject jsonObject);

}
