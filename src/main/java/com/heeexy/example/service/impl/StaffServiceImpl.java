package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ArticleDao;
import com.heeexy.example.dao.StaffDao;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.service.StaffService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zandaoguang
 */
@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private StaffDao staffDao;
	/**
	 * 新增文章
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addArticle(JSONObject jsonObject) {
		articleDao.addArticle(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 全部文章列表
	 */
	@Override
	public JSONObject listStaff(JSONObject jsonObject) {
//		System.out.println("yyyyyyyyyyyyyyyyyyyyyy");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
//		System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		int count = staffDao.countStaff(jsonObject);
		List<JSONObject> list = staffDao.listStaff(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 搜索文章列表
	 */
	@Override
	public JSONObject listArticleBySearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = articleDao.countArticleBySearch(jsonObject);
		System.out.println("count:"+count);
		List<JSONObject> list = articleDao.listArticleBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 排序文章列表
	 */
	@Override
	public JSONObject listArticleByOrder(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = articleDao.countArticle(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			 list = articleDao.listArticleByOrderAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			System.out.println("DESC..........................");
			 list = articleDao.listArticleByContentDesc(jsonObject);
		}else{
			System.out.println("null..........................");
			list = articleDao.listArticle(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 当搜索文章的时候，也就是有搜索内容的时候，排序只能排序搜索内容，不能排序全部
	 */
	@Override
	public JSONObject listArticleByContentHaveSearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = articleDao.countArticleBySearch(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = articleDao.listArticleByContentHaveSearchAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			System.out.println("DESC..........................");
			list = articleDao.listArticleByContentHaveSearchDesc(jsonObject);
		}else{
			System.out.println("null..........................");
			list = articleDao.listArticle(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 搜索文章的时候，也就是有搜索内容的时候，按照创建时间排序只能排序搜索内容，不能排序全部，这一部分是解决带搜索内容的按照创建时间排序的代码
	 */
	@Override
	public JSONObject getListByOrderCreateTimeHaveSearch(JSONObject jsonObject) {
		System.out.println("我是昝道广！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = articleDao.countArticleBySearch(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = articleDao.listArticleByOrderCreateTimeHaveSearchAsc(jsonObject);
		}else if(jsonObject.get("order").toString().equals("DESC")){
			System.out.println("DESC..........................");
			list = articleDao.listArticleByOrderCreateTimeHaveSearchDesc(jsonObject);
		}else{
			System.out.println("null..........................");
			list = articleDao.listArticle(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 排序文章列表
	 */
	@Override
	public JSONObject listArticleByOrderCreateTime(JSONObject jsonObject) {
		System.out.println("我是昝道广Create Time！！！！！");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = articleDao.countArticle(jsonObject);
		System.out.println(jsonObject.get("order").toString());
		System.out.println("count:"+count);
		List<JSONObject> list;
		if (jsonObject.get("order").toString().equals("ASC")){
			System.out.println("ASC.........................");
			list = articleDao.listArticleByOrderCreateTimeAsc(jsonObject);
		}else {
			System.out.println("DESC..........................");
			list = articleDao.listArticleByOrderCreateTimeDesc(jsonObject);
		}
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 更新文章
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateArticle(JSONObject jsonObject) {
		articleDao.updateArticle(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除文章
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteArticle(JSONObject jsonObject) {
		articleDao.deleteArticle(jsonObject);
		return CommonUtil.successJson();
	}
}
