package com.heeexy.example.service.impl;

//import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ArticleDao;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.util.CommonUtil;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import cn.afterturn.easypoi.excel.ExcelExportUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: zandaoguang
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

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
	public JSONObject listArticle(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = articleDao.countArticle(jsonObject);
		List<JSONObject> list = articleDao.listArticle(jsonObject);
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

	@Override
	public JSONObject getArticleByContent(JSONObject jsonObject) {
//		List<JSONObject> list = articleDao.listArticle(jsonObject);
//		return CommonUtil.successPage(jsonObject, list, count);
		List<JSONObject> list = articleDao.getArticleByContent(jsonObject);
		System.out.println("list.size():"+list.size());
		if(list.size() >= 1){
			return CommonUtil.successJson("fail");
		}
		else{
			return CommonUtil.successJson("success");
		}

	}

	@Override
	public JSONObject downExcel(HttpServletResponse response) throws IOException {
//		List<JSONObject> list = articleDao.getAllArticle();
//		System.out.println(list.toString());
//		//指定列表标题和工作表名称
//		ExportParams params = new ExportParams("文章信息表","文章");
//		Workbook workbook = ExcelExportUtil.exportExcel(params,Object.class,list);
//		response.setHeader("content-Type","application/vnd.ms-excel");
//		response.setHeader("Content-Disposition","attachment;filename="+System.currentTimeMillis()+".xls");
//		response.setCharacterEncoding("UTF-8");
//		try {
//			workbook.write(response.getOutputStream());
//			workbook.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return CommonUtil.successJson("导出成功！");

//		HSSFWorkbook workbook = new HSSFWorkbook();
//		HSSFSheet sheet = workbook.createSheet("信息表");
//
//		List<JSONObject> articleList = articleDao.getAllArticle();
//		System.out.println("articleList:"+articleList);
//		String fileName = "article"  + ".xls";//设置要导出的文件的名字
//		//新增数据行，并且设置单元格数据
//
//		int rowNum = 1;
//
//		String[] headers = { "id", "内容", "创建时间", "更新时间", "是否删除"};
//		//headers表示excel表中第一行的表头
//
//		HSSFRow row = sheet.createRow(0);
//		//在excel表中添加表头
//
//		for(int i=0;i<headers.length;i++){
//			HSSFCell cell = row.createCell(i);
//			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//			cell.setCellValue(text);
//		}
//
//		//在表中存放查询到的数据放入对应的列
//		for (JSONObject article : articleList) {
//			HSSFRow row1 = sheet.createRow(rowNum);
//			row1.createCell(0).setCellValue(article.getIntValue("id"));
//			row1.createCell(1).setCellValue(article.getString("content"));
//			row1.createCell(2).setCellValue(article.getTimestamp("create_time"));
//			row1.createCell(3).setCellValue(article.getTimestamp("update_time"));
//			row1.createCell(4).setCellValue(article.getIntValue("delete_status"));
//			rowNum++;
//		}
//
//		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//		response.flushBuffer();
//		workbook.write(response.getOutputStream());
		return CommonUtil.successJson("导出成功！");
	}

	@Override
	public JSONObject getThemeColorByUserId(JSONObject jsonObject) {
		List<JSONObject> list = articleDao.getThemeColorByUserId(jsonObject);
		System.out.println("list.size():"+list.size());
		if(list.size() >= 1){
			return CommonUtil.successJson(list.get(0));
		}
		else{
			return CommonUtil.successJson("failed");
		}
	}

	@Override
	public JSONObject saveColorFun(JSONObject jsonObject) {
		List<JSONObject> list = articleDao.getThemeColorByUserId(jsonObject);
		System.out.println("list.size():"+list.size());
		if (list.size() >= 1){
			articleDao.updateThemeColorByUserId(jsonObject);
			return CommonUtil.successJson("更新成功！");
		}else{
			articleDao.addThemeColorByUserId(jsonObject);
			return CommonUtil.successJson("添加成功！");
		}
	}
}
