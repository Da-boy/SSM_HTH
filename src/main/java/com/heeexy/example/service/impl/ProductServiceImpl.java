package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ProductDao;
import com.heeexy.example.service.ProductService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	/**
	 * 新增产品订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addProduct(JSONObject jsonObject) {
		productDao.addProduct(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 全部产品订单列表
	 */
	@Override
	public JSONObject listProduct(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = productDao.countProduct(jsonObject);
		List<JSONObject> list = productDao.listProduct(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 搜索产品订单列表
	 */
	@Override
	public JSONObject listProductBySearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = productDao.countProductBySearch(jsonObject);
		List<JSONObject> list = productDao.listProductBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}


	/**
	 * 更新产品订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateProduct(JSONObject jsonObject) {
		productDao.updateProduct(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 更新订单状态
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateOrdersStatus(JSONObject jsonObject) {
		String id = jsonObject.getString("order_id");
		if (null == id || id.length() == 0) {
			productDao.updateProductStatus(jsonObject);
		} else {
			productDao.updateOrdersStatus(jsonObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 审核产品订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject auditProduct(JSONObject jsonObject) {
		String id = jsonObject.getString("order_id");
		if (null == id || id.length() == 0) {
			productDao.updateProductStatus(jsonObject);
		} else {
			productDao.updateOrdersStatus(jsonObject);
		}
		productDao.auditProductForProduct(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除产品订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteProduct(JSONObject jsonObject) {
		productDao.deleteProduct(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 货品列表
	 */
	@Override
	public JSONObject listScItem(JSONObject jsonObject) {
		int count = productDao.countScItem(jsonObject);
		List<JSONObject> list = productDao.listScItem(jsonObject);
		return CommonUtil.successPage(list);
	}

	/**
	 * 新增入库单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject createEntry(JSONObject jsonObject) {
		jsonObject.put("warehousing_son_order_id", CommonUtil.getCurTimestamp());
		productDao.createEntry(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 按状态搜索采购订单列表（待办事项功能）(返回所有符合条件订单Json对象)(author: bguan)
	 */
	@Override
	public JSONObject todoSearchByStatus(JSONObject jsonObject) {
		System.out.println("todoSearchByStatus:xxxxxxxxxxxxxxxx");
		CommonUtil.fillPageParam(jsonObject);
		System.out.println("jsonObject:"+jsonObject);
		int count = productDao.todoSearchByStatus(jsonObject);
		System.out.println(jsonObject.get("status").toString());
		System.out.println("status:"+count);
		List<JSONObject> list = productDao.todoSearchEventByStatus(jsonObject);

		return CommonUtil.successPage(jsonObject, list, count);
	}
}
