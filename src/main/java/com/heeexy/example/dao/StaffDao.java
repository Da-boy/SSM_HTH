package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: zandaoguang
 * @description: 文章Dao层
 */
public interface StaffDao {
	/**
	 * 新增文章
	 */
	int addArticle(JSONObject jsonObject);

	/**
	 * 统计文章总数
	 */
	int countStaff(JSONObject jsonObject);

	/**
	 * 统计条件文章的总数
	 */

	int countArticleBySearch(JSONObject jsonObject);

	/**
	 * 文章列表
	 */
	List<JSONObject> listStaff(JSONObject jsonObject);

	/**
	 * 条件文章列表
	 */
	List<JSONObject> listArticleBySearch(JSONObject jsonObject);


	/**
	 * 排序文章列表
	 */
	List<JSONObject> listArticleByOrderAsc(JSONObject jsonObject);

	List<JSONObject> listArticleByContentDesc(JSONObject jsonObject);

	List<JSONObject> listArticleByContentHaveSearchAsc(JSONObject jsonObject);

	List<JSONObject> listArticleByContentHaveSearchDesc(JSONObject jsonObject);

	List<JSONObject> listArticleByOrderCreateTimeAsc(JSONObject jsonObject);

	List<JSONObject> listArticleByOrderCreateTimeDesc(JSONObject jsonObject);

	List<JSONObject> listArticleByOrderCreateTimeHaveSearchAsc(JSONObject jsonObject);

	List<JSONObject> listArticleByOrderCreateTimeHaveSearchDesc(JSONObject jsonObject);

	/**
	 * 更新文章
	 */
	int updateArticle(JSONObject jsonObject);

	/**
	 * 删除文章
	 */

	int deleteArticle(JSONObject jsonObject);
}
