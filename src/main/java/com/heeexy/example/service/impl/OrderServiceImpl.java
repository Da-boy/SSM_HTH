package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.OrderDao;
import com.heeexy.example.service.OrderService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.rmi.CORBA.Util;
import javax.swing.plaf.metal.MetalIconFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: dengchenglong
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

//	@Autowired
//	SendMailService sendMailService;

	/**
	 * 新增Oligo订单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addOligo(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}
		int max_mord_id = orderDao.maxMord_Id(jsonObject);

		newObject.put("mord_id", (max_mord_id + 1));
		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));
		newObject.put("is_import", jsonObject.getString("is_import"));
		try {
			newObject.put("imported_staff", jsonObject.getString("imported_staff"));
			newObject.put("imported_time", jsonObject.getString("imported_time"));
		}catch (Exception e){
			newObject.put("imported_staff", null);
			newObject.put("imported_time", null);
		}

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != "") {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}
		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			newObject.put("order_id", (max_mord_id + 1) + "-" + (i + 1));
			//判断输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("catalog_id") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("catalog_id").length(); j++){
					if ((!Character.isDigit(tabledata.getJSONObject(i).getString("catalog_id").charAt(j))) && tabledata.getJSONObject(i).getString("catalog_id").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("catalog_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("catalog_id"))));
				}else {
					newObject.put("catalog_id", null);
				}
			}else {
				newObject.put("catalog_id", null);
			}
			newObject.put("gene_name", tabledata.getJSONObject(i).getString("gene_name"));
			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("sense_53", tabledata.getJSONObject(i).getString("sense_53"));
			newObject.put("antisense_53", tabledata.getJSONObject(i).getString("antisense_53"));
			newObject.put("synthetic_spec", tabledata.getJSONObject(i).getString("synthetic_spec"));

			//判断输入是否为数字
			boolean tflag = true;
			if(tabledata.getJSONObject(i).getString("div_tubes_number") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("div_tubes_number").length(); j++){
					if ((!Character.isDigit(tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j))) && tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j) != '.') {
						tflag = false;
						break;
					}
				}
				if(tflag) {
					newObject.put("div_tubes_number", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("div_tubes_number"))));
				}else {
					newObject.put("div_tubes_number", null);
				}
			}else {
				newObject.put("div_tubes_number", null);
			}

			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != "" && tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}

			newObject.put("chemical_modification", tabledata.getJSONObject(i).getString("chemical_modification"));
			newObject.put("flu_labeling", tabledata.getJSONObject(i).getString("flu_labeling"));
			newObject.put("special_modi", tabledata.getJSONObject(i).getString("special_modi"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.addOligo(newObject);
		}

		return CommonUtil.successJson();
	}

	/**
	 * 修改Oligo订单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateOligo(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != null) {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}

		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			//判断输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("catalog_id") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("catalog_id").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("catalog_id").charAt(j))  && tabledata.getJSONObject(i).getString("catalog_id").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("catalog_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("catalog_id"))));
				}else {
					newObject.put("catalog_id", null);
				}
			}else {
				newObject.put("catalog_id", null);
			}

			newObject.put("gene_name", tabledata.getJSONObject(i).getString("gene_name"));
			newObject.put("sense_53", tabledata.getJSONObject(i).getString("sense_53"));
			newObject.put("antisense_53", tabledata.getJSONObject(i).getString("antisense_53"));
			newObject.put("synthetic_spec", tabledata.getJSONObject(i).getString("synthetic_spec"));

			//判断输入是否为数字
			boolean tflag = true;
			if(tabledata.getJSONObject(i).getString("div_tubes_number") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("div_tubes_number").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j)) && tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j) != '.') {
						tflag = false;
						break;
					}
				}
				if(tflag) {
					newObject.put("div_tubes_number", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("div_tubes_number"))));
				}else {
					newObject.put("div_tubes_number", null);
				}
			}else {
				newObject.put("div_tubes_number", null);
			}

			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j))  && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}

			//判断sc_item_id的输入是否为数字
			boolean scflag = true;
			try {
				if(tabledata.getJSONObject(i).getString("gene_name_id") != "") {
					for (int j = 0; j < tabledata.getJSONObject(i).getString("gene_name_id").length(); j++){
						if ((!Character.isDigit(tabledata.getJSONObject(i).getString("gene_name_id").charAt(j)))) {
							dflag = false;
							break;
						}
					}
					if(dflag) {
						newObject.put("sc_item_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("gene_name_id"))));
					}else {
						newObject.put("sc_item_id", null);
					}
				}else {
					newObject.put("sc_item_id", null);
				}
			}catch (NullPointerException e){
				newObject.put("sc_item_id", null);
			}

			newObject.put("order_id", tabledata.getJSONObject(i).getString("order_id"));
			newObject.put("chemical_modification", tabledata.getJSONObject(i).getString("chemical_modification"));
			newObject.put("flu_labeling", tabledata.getJSONObject(i).getString("flu_labeling"));
			newObject.put("special_modi", tabledata.getJSONObject(i).getString("special_modi"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.updateOligo(newObject);
		}

		return CommonUtil.successJson();
	}

	/**
	 * 新增试剂盒&引物探针套装订单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addKitProbe(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		int max_mord_id = orderDao.maxMord_Id(jsonObject);

		newObject.put("mord_id", (max_mord_id + 1));
		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));
		newObject.put("is_import", jsonObject.getString("is_import"));
		try {
			newObject.put("imported_staff", jsonObject.getString("imported_staff"));
			newObject.put("imported_time", jsonObject.getString("imported_time"));
		}catch (Exception e){
			newObject.put("imported_staff", null);
			newObject.put("imported_time", null);
		}

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != "") {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();
			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}
		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			newObject.put("order_id", (max_mord_id + 1) + "-" + (i + 1));

			//判断输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("catalog_id") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("catalog_id").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("catalog_id").charAt(j)) && tabledata.getJSONObject(i).getString("catalog_id").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("catalog_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("catalog_id"))));
				}else {
					newObject.put("catalog_id", null);
				}
			}else {
				newObject.put("catalog_id", null);
			}
			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != "" && tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}
			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("gene_name", tabledata.getJSONObject(i).getString("gene_name"));
			newObject.put("product_name", tabledata.getJSONObject(i).getString("product_name"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));
			newObject.put("specifications", tabledata.getJSONObject(i).getString("specifications"));
			newObject.put("gene_type", tabledata.getJSONObject(i).getString("special_instructions"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.addKitProbe(newObject);
		}

		return CommonUtil.successJson();
	}

	/**
	 * 修改试剂盒&引物探针套装订单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateKitProbe(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != null) {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}
		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			//判断输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("catalog_id") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("catalog_id").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("catalog_id").charAt(j)) && tabledata.getJSONObject(i).getString("catalog_id").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("catalog_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("catalog_id"))));
				}else {
					newObject.put("catalog_id", null);
				}
			}else {
				newObject.put("catalog_id", null);
			}
			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}

			//判断sc_item_id的输入是否为数字
			boolean scflag = true;
			try {
				if(tabledata.getJSONObject(i).getString("gene_name_id") != "") {
					for (int j = 0; j < tabledata.getJSONObject(i).getString("gene_name_id").length(); j++){
						if ((!Character.isDigit(tabledata.getJSONObject(i).getString("gene_name_id").charAt(j)))) {
							dflag = false;
							break;
						}
					}
					if(dflag) {
						newObject.put("sc_item_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("gene_name_id"))));
					}else {
						newObject.put("sc_item_id", null);
					}
				}else {
					newObject.put("sc_item_id", null);
				}
			}catch (NullPointerException e){
				newObject.put("sc_item_id", null);
			}

			newObject.put("order_id", tabledata.getJSONObject(i).getString("order_id"));
			newObject.put("gene_name", tabledata.getJSONObject(i).getString("gene_name"));
			newObject.put("product_name", tabledata.getJSONObject(i).getString("product_name"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));
			newObject.put("specifications", tabledata.getJSONObject(i).getString("specifications"));
			newObject.put("gene_type", tabledata.getJSONObject(i).getString("special_instructions"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.updateKitProbe(newObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 新增载体订单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addCarrier(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		int max_mord_id = orderDao.maxMord_Id(jsonObject);

		newObject.put("mord_id", (max_mord_id + 1));
		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));
		newObject.put("is_import", jsonObject.getString("is_import"));
		try {
			newObject.put("imported_staff", jsonObject.getString("imported_staff"));
			newObject.put("imported_time", jsonObject.getString("imported_time"));
		}catch (Exception e){
			newObject.put("imported_staff", null);
			newObject.put("imported_time", null);
		}

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != "") {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}
		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			newObject.put("order_id", (max_mord_id + 1) + "-" + (i + 1));

			//判断输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("catalog_id") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("catalog_id").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("catalog_id").charAt(j))  && tabledata.getJSONObject(i).getString("catalog_id").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("catalog_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("catalog_id"))));
				}else {
					newObject.put("catalog_id", null);
				}
			}else {
				newObject.put("catalog_id", null);
			}
			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != "" && tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}
			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("carrier_type", tabledata.getJSONObject(i).getString("carrier_type"));
			newObject.put("product_name", tabledata.getJSONObject(i).getString("product_name"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));
			newObject.put("sequence_53", tabledata.getJSONObject(i).getString("sequence_53"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.addCarrier(newObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 修改载体订单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateCarrier(JSONObject jsonObject){
		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != null) {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}
		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			//判断输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("catalog_id") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("catalog_id").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("catalog_id").charAt(j)) && tabledata.getJSONObject(i).getString("catalog_id").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("catalog_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("catalog_id"))));
				}else {
					newObject.put("catalog_id", null);
				}
			}else {
				newObject.put("catalog_id", null);
			}
			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}

			try {
				if(tabledata.getJSONObject(i).getString("gene_name_id") != "") {
					for (int j = 0; j < tabledata.getJSONObject(i).getString("gene_name_id").length(); j++){
						if ((!Character.isDigit(tabledata.getJSONObject(i).getString("gene_name_id").charAt(j)))) {
							dflag = false;
							break;
						}
					}
					if(dflag) {
						newObject.put("sc_item_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("gene_name_id"))));
					}else {
						newObject.put("sc_item_id", null);
					}
				}else {
					newObject.put("sc_item_id", null);
				}
			}catch (NullPointerException e){
				newObject.put("sc_item_id", null);
			}

			newObject.put("order_id", tabledata.getJSONObject(i).getString("order_id"));
			newObject.put("carrier_type", tabledata.getJSONObject(i).getString("carrier_type"));
			newObject.put("product_name", tabledata.getJSONObject(i).getString("product_name"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));
			newObject.put("sequence_53", tabledata.getJSONObject(i).getString("sequence_53"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.updateCarrier(newObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 新增DNA探针合成订购单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addDNAProbe(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		int max_mord_id = orderDao.maxMord_Id(jsonObject);

		newObject.put("mord_id", (max_mord_id + 1));
		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));
		newObject.put("is_import", jsonObject.getString("is_import"));
		try {
			newObject.put("imported_staff", jsonObject.getString("imported_staff"));
			newObject.put("imported_time", jsonObject.getString("imported_time"));
		}catch (Exception e){
			newObject.put("imported_staff", null);
			newObject.put("imported_time", null);
		}

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != "") {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}

		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			newObject.put("order_id", (max_mord_id + 1) + "-" + (i + 1));

			//判断目录号输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("catalog_id") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("catalog_id").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("catalog_id").charAt(j)) && tabledata.getJSONObject(i).getString("catalog_id").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("catalog_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("catalog_id"))));
				}else {
					newObject.put("catalog_id", null);
				}
			}else {
				newObject.put("catalog_id", null);
			}
			newObject.put("product_type", tabledata.getJSONObject(i).getString("product_type"));
			newObject.put("dna_sequ_53", tabledata.getJSONObject(i).getString("dna_sequ_53"));
			newObject.put("gene_name", tabledata.getJSONObject(i).getString("gene_name"));

			//判断碱基数输入是否为数字
			boolean bflag = true;
			if(tabledata.getJSONObject(i).getString("base_number") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("base_number").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("base_number").charAt(j)) && tabledata.getJSONObject(i).getString("base_number").charAt(j) != '.') {
						bflag = false;
						break;
					}
				}
				if(bflag) {
					newObject.put("base_number", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("base_number"))));
				}else {
					newObject.put("base_number", null);
				}
			}else {
				newObject.put("base_number", null);
			}

			//判断分管输入是否为数字
			boolean tflag = true;
			if(tabledata.getJSONObject(i).getString("div_tubes_number") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("div_tubes_number").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j)) && tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j) != '.') {
						tflag = false;
						break;
					}
				}
				if(tflag) {
					newObject.put("div_tubes_number", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("div_tubes_number"))));
				}else {
					newObject.put("div_tubes_number", null);
				}
			}else {
				newObject.put("div_tubes_number", null);
			}

			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != "" && tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}
			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("synthetic_spec", tabledata.getJSONObject(i).getString("synthetic_spec"));
			newObject.put("modifi_5", tabledata.getJSONObject(i).getString("modifi_5"));
			newObject.put("modifi_3", tabledata.getJSONObject(i).getString("modifi_3"));
			newObject.put("purification_method", tabledata.getJSONObject(i).getString("purification_method"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.addDNAProbe(newObject);
		}

		return CommonUtil.successJson();
	}

	/**
	 * 修改DNA探针合成订购单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateDNAProbe(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != null) {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}

		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			//判断目录号输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("catalog_id") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("catalog_id").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("catalog_id").charAt(j)) && tabledata.getJSONObject(i).getString("catalog_id").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("catalog_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("catalog_id"))));
				}else {
					newObject.put("catalog_id", null);
				}
			}else {
				newObject.put("catalog_id", null);
			}
			newObject.put("product_type", tabledata.getJSONObject(i).getString("product_type"));
			newObject.put("dna_sequ_53", tabledata.getJSONObject(i).getString("dna_sequ_53"));
			newObject.put("gene_name", tabledata.getJSONObject(i).getString("gene_name"));

			//判断碱基数输入是否为数字
			boolean bflag = true;
			if(tabledata.getJSONObject(i).getString("base_number") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("base_number").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("base_number").charAt(j)) && tabledata.getJSONObject(i).getString("base_number").charAt(j) != '.') {
						bflag = false;
						break;
					}
				}
				if(bflag) {
					newObject.put("base_number", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("base_number"))));
				}else {
					newObject.put("base_number", null);
				}
			}else {
				newObject.put("base_number", null);
			}

			//判断分管输入是否为数字
			boolean tflag = true;
			if(tabledata.getJSONObject(i).getString("div_tubes_number") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("div_tubes_number").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j)) && tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j) != '.') {
						tflag = false;
						break;
					}
				}
				if(tflag) {
					newObject.put("div_tubes_number", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("div_tubes_number"))));
				}else {
					newObject.put("div_tubes_number", null);
				}
			}else {
				newObject.put("div_tubes_number", null);
			}

			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}

			//判断sc_item_id的输入是否为数字
			boolean scflag = true;
			try {
				if(tabledata.getJSONObject(i).getString("gene_name_id") != "") {
					for (int j = 0; j < tabledata.getJSONObject(i).getString("gene_name_id").length(); j++){
						if ((!Character.isDigit(tabledata.getJSONObject(i).getString("gene_name_id").charAt(j)))) {
							dflag = false;
							break;
						}
					}
					if(dflag) {
						newObject.put("sc_item_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("gene_name_id"))));
					}else {
						newObject.put("sc_item_id", null);
					}
				}else {
					newObject.put("sc_item_id", null);
				}
			}catch (NullPointerException e){
				newObject.put("sc_item_id", null);
			}

			newObject.put("order_id", tabledata.getJSONObject(i).getString("order_id"));
			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("synthetic_spec", tabledata.getJSONObject(i).getString("synthetic_spec"));
			newObject.put("modifi_5", tabledata.getJSONObject(i).getString("modifi_5"));
			newObject.put("modifi_3", tabledata.getJSONObject(i).getString("modifi_3"));
			newObject.put("purification_method", tabledata.getJSONObject(i).getString("purification_method"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.updateDNAProbe(newObject);
		}

		return CommonUtil.successJson();
	}

	/**
	 * 新增FISH试剂盒&其他订购单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addFISH(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		int max_mord_id = orderDao.maxMord_Id(jsonObject);

		newObject.put("mord_id", (max_mord_id + 1));
		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));
		newObject.put("is_import", jsonObject.getString("is_import"));
		try {
			newObject.put("imported_staff", jsonObject.getString("imported_staff"));
			newObject.put("imported_time", jsonObject.getString("imported_time"));
		}catch (Exception e){
			newObject.put("imported_staff", null);
			newObject.put("imported_time", null);
		}

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != "") {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}

		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			newObject.put("order_id", (max_mord_id + 1) + "-" + (i + 1));

			//判断目录号输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("catalog_id") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("catalog_id").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("catalog_id").charAt(j)) && tabledata.getJSONObject(i).getString("catalog_id").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("catalog_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("catalog_id"))));
				}else {
					newObject.put("catalog_id", null);
				}
			}else {
				newObject.put("catalog_id", null);
			}
			newObject.put("sample_type", tabledata.getJSONObject(i).getString("sample_type"));
			newObject.put("product_name", tabledata.getJSONObject(i).getString("product_name"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));
			newObject.put("probe_sequence_53", tabledata.getJSONObject(i).getString("probe_sequence_53"));

			//判断碱基数输入是否为数字
			boolean bflag = true;
			if(tabledata.getJSONObject(i).getString("numbers") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("numbers").charAt(j)) && tabledata.getJSONObject(i).getString("numbers").charAt(j) != '.') {
						bflag = false;
						break;
					}
				}
				if(bflag) {
					newObject.put("numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("numbers"))));
				}else {
					newObject.put("numbers", null);
				}
			}else {
				newObject.put("numbers", null);
			}
			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != "" && tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}
			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("company", tabledata.getJSONObject(i).getString("company"));
			newObject.put("modifi_5", tabledata.getJSONObject(i).getString("modifi_5"));
			newObject.put("modifi_3", tabledata.getJSONObject(i).getString("modifi_3"));
			newObject.put("purification_method", tabledata.getJSONObject(i).getString("purification_method"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.addFISH(newObject);
		}

		return CommonUtil.successJson();
	}

	/**
	 * 修改FISH试剂盒&其他订购单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateFISH(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != null) {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}

		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			//判断目录号输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("catalog_id") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("catalog_id").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("catalog_id").charAt(j)) && tabledata.getJSONObject(i).getString("catalog_id").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("catalog_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("catalog_id"))));
				}else {
					newObject.put("catalog_id", null);
				}
			}else {
				newObject.put("catalog_id", null);
			}
			newObject.put("sample_type", tabledata.getJSONObject(i).getString("sample_type"));
			newObject.put("product_name", tabledata.getJSONObject(i).getString("product_name"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));
			newObject.put("probe_sequence_53", tabledata.getJSONObject(i).getString("probe_sequence_53"));

			//判断碱基数输入是否为数字
			boolean bflag = true;
			if(tabledata.getJSONObject(i).getString("numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("numbers").charAt(j)) && tabledata.getJSONObject(i).getString("numbers").charAt(j) != '.') {
						bflag = false;
						break;
					}
				}
				if(bflag) {
					newObject.put("numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("numbers"))));
				}else {
					newObject.put("numbers", null);
				}
			}else {
				newObject.put("numbers", null);
			}
			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}

			//判断sc_item_id的输入是否为数字
			boolean scflag = true;
			try {
				if(tabledata.getJSONObject(i).getString("gene_name_id") != "") {
					for (int j = 0; j < tabledata.getJSONObject(i).getString("gene_name_id").length(); j++){
						if ((!Character.isDigit(tabledata.getJSONObject(i).getString("gene_name_id").charAt(j)))) {
							dflag = false;
							break;
						}
					}
					if(dflag) {
						newObject.put("sc_item_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("gene_name_id"))));
					}else {
						newObject.put("sc_item_id", null);
					}
				}else {
					newObject.put("sc_item_id", null);
				}
			}catch (NullPointerException e){
				newObject.put("sc_item_id", null);
			}

			newObject.put("order_id", tabledata.getJSONObject(i).getString("order_id"));
			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("company", tabledata.getJSONObject(i).getString("company"));
			newObject.put("modifi_5", tabledata.getJSONObject(i).getString("modifi_5"));
			newObject.put("modifi_3", tabledata.getJSONObject(i).getString("modifi_3"));
			newObject.put("purification_method", tabledata.getJSONObject(i).getString("purification_method"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.updateFISH(newObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 新增病毒订购单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addVirus(JSONObject jsonObject) {
		JSONObject newObject = new JSONObject();
//		System.out.println("add virus: " + jsonObject);

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}
		int max_mord_id = orderDao.maxMord_Id(jsonObject);

		newObject.put("mord_id", (max_mord_id + 1));
		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));
		newObject.put("is_import", jsonObject.getString("is_import"));
		try {
			newObject.put("imported_staff", jsonObject.getString("imported_staff"));
			newObject.put("imported_time", jsonObject.getString("imported_time"));
		}catch (Exception e){
			newObject.put("imported_staff", null);
			newObject.put("imported_time", null);
		}

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != "") {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}

		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			newObject.put("order_id", (max_mord_id + 1) + "-" + (i + 1));

			//判断目录号输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("order_quantity") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("order_quantity").length(); j++){
					if ((!Character.isDigit(tabledata.getJSONObject(i).getString("order_quantity").charAt(j))) && tabledata.getJSONObject(i).getString("order_quantity").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("order_quantity", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("order_quantity"))));
				}else {
					newObject.put("order_quantity", null);
				}
			}else {
				newObject.put("order_quantity", null);
			}
			newObject.put("viruses_type", tabledata.getJSONObject(i).getString("viruses_type"));
			newObject.put("carrier_type", tabledata.getJSONObject(i).getString("carrier_type"));
			newObject.put("sequence_53", tabledata.getJSONObject(i).getString("sequence_53"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));

			//判断碱基数输入是否为数字
			boolean bflag = true;
			if(tabledata.getJSONObject(i).getString("div_tubes_number") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("div_tubes_number").length(); j++){
					if ((!Character.isDigit(tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j))) && tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j) != '.') {
						bflag = false;
						break;
					}
				}
				if(bflag) {
					newObject.put("div_tubes_number", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("div_tubes_number"))));
				}else {
					newObject.put("div_tubes_number", null);
				}
			}else {
				newObject.put("div_tubes_number", null);
			}

			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != "" && tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if ((!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j))) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}
			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("titer_requirement", tabledata.getJSONObject(i).getString("titer_requirement"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.addVirus(newObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 修改病毒订购单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateVirus(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != null) {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}

		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			//判断目录号输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("order_quantity") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("order_quantity").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("order_quantity").charAt(j)) && tabledata.getJSONObject(i).getString("order_quantity").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("order_quantity", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("order_quantity"))));
				}else {
					newObject.put("order_quantity", null);
				}
			}else {
				newObject.put("order_quantity", null);
			}
			newObject.put("viruses_type", tabledata.getJSONObject(i).getString("viruses_type"));
			newObject.put("carrier_type", tabledata.getJSONObject(i).getString("carrier_type"));
			newObject.put("sequence_53", tabledata.getJSONObject(i).getString("sequence_53"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));

			//判断碱基数输入是否为数字
			boolean bflag = true;
			if(tabledata.getJSONObject(i).getString("div_tubes_number") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("div_tubes_number").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j)) && tabledata.getJSONObject(i).getString("div_tubes_number").charAt(j) != '.') {
						bflag = false;
						break;
					}
				}
				if(bflag) {
					newObject.put("div_tubes_number", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("div_tubes_number"))));
				}else {
					newObject.put("div_tubes_number", null);
				}
			}else {
				newObject.put("div_tubes_number", null);
			}

			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}

			//判断sc_item_id的输入是否为数字
			boolean scflag = true;
			try {
				if(tabledata.getJSONObject(i).getString("gene_name_id") != "") {
					for (int j = 0; j < tabledata.getJSONObject(i).getString("gene_name_id").length(); j++){
						if ((!Character.isDigit(tabledata.getJSONObject(i).getString("gene_name_id").charAt(j)))) {
							dflag = false;
							break;
						}
					}
					if(dflag) {
						newObject.put("sc_item_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("gene_name_id"))));
					}else {
						newObject.put("sc_item_id", null);
					}
				}else {
					newObject.put("sc_item_id", null);
				}
			}catch (NullPointerException e){
				newObject.put("sc_item_id", null);
			}

			newObject.put("order_id", tabledata.getJSONObject(i).getString("order_id"));
			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("titer_requirement", tabledata.getJSONObject(i).getString("titer_requirement"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.updateVirus(newObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 新增全基因合成订购单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addWholeGene(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		int max_mord_id = orderDao.maxMord_Id(jsonObject);

		newObject.put("mord_id", (max_mord_id + 1));
		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));
		newObject.put("is_import", jsonObject.getString("is_import"));
		try {
			newObject.put("imported_staff", jsonObject.getString("imported_staff"));
			newObject.put("imported_time", jsonObject.getString("imported_time"));
		}catch (Exception e){
			newObject.put("imported_staff", null);
			newObject.put("imported_time", null);
		}

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != "") {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}

		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			newObject.put("order_id", (max_mord_id + 1) + "-" + (i + 1));

			//判断目录号输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("order_quantity") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("order_quantity").length(); j++){
					if ((!Character.isDigit(tabledata.getJSONObject(i).getString("order_quantity").charAt(j))) && tabledata.getJSONObject(i).getString("order_quantity").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("order_quantity", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("order_quantity"))));
				}else {
					newObject.put("order_quantity", null);
				}
			}else {
				newObject.put("order_quantity", null);
			}
			newObject.put("carrier_type", tabledata.getJSONObject(i).getString("carrier_type"));
			newObject.put("sequence_53", tabledata.getJSONObject(i).getString("sequence_53"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));

			//判断基因长度输入是否为数字
			boolean bflag = true;
			if(tabledata.getJSONObject(i).getString("gene_length") != "") {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("gene_length").length(); j++){
					if ((!Character.isDigit(tabledata.getJSONObject(i).getString("gene_length").charAt(j))) && tabledata.getJSONObject(i).getString("gene_length").charAt(j) != '.') {
						bflag = false;
						break;
					}
				}
				if(bflag) {
					newObject.put("gene_length", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("gene_length"))));
				}else {
					newObject.put("gene_length", null);
				}
			}else {
				newObject.put("gene_length", null);
			}

			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != "" && tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}

			try {
				if(tabledata.getJSONObject(i).getString("gene_name_id") != "") {
					for (int j = 0; j < tabledata.getJSONObject(i).getString("gene_name_id").length(); j++){
						if ((!Character.isDigit(tabledata.getJSONObject(i).getString("gene_name_id").charAt(j)))) {
							dflag = false;
							break;
						}
					}
					if(dflag) {
						newObject.put("sc_item_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("gene_name_id"))));
					}else {
						newObject.put("sc_item_id", null);
					}
				}else {
					newObject.put("sc_item_id", null);
				}
			}catch (NullPointerException e){
				newObject.put("sc_item_id", null);
			}

			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("carrier_vector", tabledata.getJSONObject(i).getString("carrier_vector"));
			newObject.put("cloning_site", tabledata.getJSONObject(i).getString("cloning_site"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.addWholeGene(newObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 修改全基因合成订购单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateWholeGene(JSONObject jsonObject) {

		JSONObject newObject = new JSONObject();

		//判断client表中用户是否存在
		List<JSONObject> name_phone = orderDao.getClient_name_phone(jsonObject);
		boolean flag = false;
		for(int i=0; i<name_phone.size(); i++){
			if(jsonObject.getString("client_name").equals(name_phone.get(i).getString("client_name")) && jsonObject.getString("client_phone").equals(name_phone.get(i).getString("client_phone")) && jsonObject.getString("client_email").equals(name_phone.get(i).getString("client_email"))){
				flag = true;
				break;
			}
		}
		if (flag){
			orderDao.updateClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}else {
			orderDao.addClient(jsonObject);
			newObject.put("client_id", orderDao.getClientID(jsonObject));
		}

		newObject.put("create_time", jsonObject.getString("create_time"));
		newObject.put("delivery_time", jsonObject.getString("delivery_time"));
		newObject.put("order_type", jsonObject.getString("order_type"));
		newObject.put("order_status", jsonObject.getString("order_status"));
		newObject.put("notification_of_delivery", jsonObject.getString("notification_of_delivery"));
		newObject.put("invoice_title", jsonObject.getString("invoice_title"));

		//判断价格输入是否为数字
		boolean oflag = true;
		if(jsonObject.getString("order_price") != null) {
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			oflag = pattern.matcher(jsonObject.getString("order_price")).matches();

			if(oflag) {
				newObject.put("order_price", Float.parseFloat(jsonObject.getString("order_price")));
			}else {
				newObject.put("order_price", null);
			}
		}else {
			newObject.put("order_price", null);
		}

		newObject.put("remark", jsonObject.getString("remark"));
		newObject.put("sales_manager_id", jsonObject.getString("sales_manager_id"));
		newObject.put("form_of_invoice", jsonObject.getString("form_of_invoice"));
		newObject.put("advance_customer_name", jsonObject.getString("advance_customer_name"));
		newObject.put("courier_scervices_company", jsonObject.getString("courier_scervices_company"));

		JSONArray tabledata = jsonObject.getJSONArray("tableDatas");
		for(int i = 0; i < tabledata.size(); i++){
			//判断目录号输入是否为数字
			boolean dflag = true;
			if(tabledata.getJSONObject(i).getString("order_quantity") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("order_quantity").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("order_quantity").charAt(j)) && tabledata.getJSONObject(i).getString("order_quantity").charAt(j) != '.') {
						dflag = false;
						break;
					}
				}
				if(dflag) {
					newObject.put("order_quantity", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("order_quantity"))));
				}else {
					newObject.put("order_quantity", null);
				}
			}else {
				newObject.put("order_quantity", null);
			}
			newObject.put("carrier_type", tabledata.getJSONObject(i).getString("carrier_type"));
			newObject.put("sequence_53", tabledata.getJSONObject(i).getString("sequence_53"));
			newObject.put("gene_name_type", tabledata.getJSONObject(i).getString("gene_name_type"));

			//判断基因长度输入是否为数字
			boolean bflag = true;
			if(tabledata.getJSONObject(i).getString("gene_length") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("gene_length").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("gene_length").charAt(j)) && tabledata.getJSONObject(i).getString("gene_length").charAt(j) != '.') {
						bflag = false;
						break;
					}
				}
				if(bflag) {
					newObject.put("gene_length", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("gene_length"))));
				}else {
					newObject.put("gene_length", null);
				}
			}else {
				newObject.put("gene_length", null);
			}

			//判断输入是否为数字
			boolean iflag = true;
			if(tabledata.getJSONObject(i).getString("item_numbers") != null) {
				for (int j = 0; j < tabledata.getJSONObject(i).getString("item_numbers").length(); j++){
					if (!Character.isDigit(tabledata.getJSONObject(i).getString("item_numbers").charAt(j)) && tabledata.getJSONObject(i).getString("item_numbers").charAt(j) != '.') {
						iflag = false;
						break;
					}
				}
				if(iflag) {
					newObject.put("item_numbers", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("item_numbers"))));
				}else {
					newObject.put("item_numbers", null);
				}
			}else {
				newObject.put("item_numbers", null);
			}

			//判断sc_item_id的输入是否为数字
			boolean scflag = true;
			try {
				if(tabledata.getJSONObject(i).getString("gene_name_id") != "") {
					for (int j = 0; j < tabledata.getJSONObject(i).getString("gene_name_id").length(); j++){
						if ((!Character.isDigit(tabledata.getJSONObject(i).getString("gene_name_id").charAt(j)))) {
							dflag = false;
							break;
						}
					}
					if(dflag) {
						newObject.put("sc_item_id", (int)(Float.parseFloat(tabledata.getJSONObject(i).getString("gene_name_id"))));
					}else {
						newObject.put("sc_item_id", null);
					}
				}else {
					newObject.put("sc_item_id", null);
				}
			}catch (NullPointerException e){
				newObject.put("sc_item_id", null);
			}

			newObject.put("order_id", tabledata.getJSONObject(i).getString("order_id"));
			newObject.put("sc_item_id", tabledata.getJSONObject(i).getString("gene_name_id"));
			newObject.put("carrier_vector", tabledata.getJSONObject(i).getString("carrier_vector"));
			newObject.put("cloning_site", tabledata.getJSONObject(i).getString("cloning_site"));
			newObject.put("item_unit", tabledata.getJSONObject(i).getString("item_unit"));
			newObject.put("special_instructions", tabledata.getJSONObject(i).getString("special_instructions"));

			orderDao.updateWholeGene(newObject);
		}
		return CommonUtil.successJson();
	}


	/**
	 * 全部订单信息列表
	 */
	@Override
	public JSONObject listOrder(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countOrder(jsonObject);
		List<JSONObject> list = orderDao.listOrder(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 全部父订单对应所有子订单信息列表
	 */
	@Override
	public JSONObject getSonOrderByfathor(JSONObject jsonObject) {
		List<JSONObject> list = orderDao.getSonOrderByfathor(jsonObject);
		return CommonUtil.successJson(list);
	}

	/**
	 * 全部父订单基本信息列表
	 */
	@Override
	public JSONObject listMordOrder(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countMordOrder(jsonObject);
		List<JSONObject> list = orderDao.listMordOrder(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 全部订单详细信息列表
	 */
	@Override
	public JSONObject getRowDetailByID(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		List<JSONObject> list = orderDao.getRowDetailByID(jsonObject);
		return CommonUtil.successPage(list);
	}

	/**
	 * 全部销售经理信息列表
	 */
	@Override
	public JSONObject listSales(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		List<JSONObject> list = orderDao.listSales(jsonObject);
		return CommonUtil.successPage(list);
	}

	/**
	 * 根据order_id搜索order_type
	 */
	@Override
	public JSONObject getOrderTypeByID(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		JSONObject orderType = orderDao.getOrderTypeByID(jsonObject);
		return CommonUtil.successJson(orderType);
	}

	/**
	 * 根据order_id搜索所有相关RNA Oligo内容
	 */
	@Override
	public JSONObject getRNAOligoByID(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countMordByID(jsonObject);
		List<JSONObject> list = orderDao.getRNAOligoByID(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 根据order_id搜索所有相关载体内容
	 */
	@Override
	public JSONObject getCarrierByID(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countMordByID(jsonObject);
		List<JSONObject> list = orderDao.getCarrierByID(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 根据order_id搜索所有相关DNA探针内容
	 */
	@Override
	public JSONObject getDNAProbeByID(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countMordByID(jsonObject);
		List<JSONObject> list = orderDao.getDNAProbeByID(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 根据order_id搜索所有相关FISH试剂盒及其他内容
	 */
	@Override
	public JSONObject getFISHKitByID(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countMordByID(jsonObject);
		List<JSONObject> list = orderDao.getFISHKitByID(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 根据order_id搜索所有相关试剂盒引物探针套装内容
	 */
	@Override
	public JSONObject getKitProbeByID(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countMordByID(jsonObject);
		List<JSONObject> list = orderDao.getKitProbeByID(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 根据order_id搜索所有相关病毒内容
	 */
	@Override
	public JSONObject getVirusByID(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countMordByID(jsonObject);
		List<JSONObject> list = orderDao.getVirusByID(jsonObject);
//		System.out.println("virus data: "+ list);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 根据order_id搜索所有相关全基因内容
	 */
	@Override
	public JSONObject getWholeGeneByID(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countMordByID(jsonObject);
		List<JSONObject> list = orderDao.getWholeGeneByID(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 审核订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject checkOrder(JSONObject jsonObject) {
		if((!jsonObject.getString("radio").equals("不变")) && (!jsonObject.getString("order_status").equals("状态不变"))) {
			orderDao.checkOrder(jsonObject);
		}
		return CommonUtil.successJson();
	}

	/**
	 * 更新订单状态
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateOrderStatus(JSONObject jsonObject) {
		orderDao.updateOrderStatus(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除订单信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteOrder(JSONObject jsonObject) {
		orderDao.deleteOrder(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject updateOrderByCheck(JSONObject requestJson) {
		orderDao.updateOrderByCheck(requestJson);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject addexwarehouse(JSONObject requestJson) {
		orderDao.updatewarehouseStatus(requestJson);
//		requestJson.put("unit","件");
		orderDao.addexwarehouse(requestJson);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject adddesign(JSONObject requestJson) {
		List<JSONObject> list = orderDao.getDesignOrderList(requestJson);
		for (int i = 0; i < list.size(); i++) {
			requestJson.put("saling_son_order_id", list.get(i).get("order_id"));
			orderDao.updatewarehouseStatus(requestJson);
			orderDao.adddesign(requestJson);
		}
		return CommonUtil.successJson();
	}
	@Override
	public JSONObject updatedesign(JSONObject requestJson) {
		orderDao.updatedesign(requestJson);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject updatedesigndonetime(JSONObject requestJson) {
		requestJson.put("design_done_time", CommonUtil.getCurTimestampForDB());
		orderDao.updatedesigndonetime(requestJson);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject addproduct(JSONObject requestJson) {
		orderDao.updatewarehouseStatus(requestJson);
		orderDao.addproduct(requestJson);
		return CommonUtil.successJson();
	}

	/**
	 * 按状态搜索订单列表（待办事项功能）(返回所有符合条件订单Json对象)(author: bguan)
	 */
	@Override
	public JSONObject todoSearchByStatus(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.todoSearchByStatus(jsonObject);
		List<JSONObject> list = orderDao.todoSearchEventByStatus(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 搜索订单列表
	 */
	@Override
	public JSONObject listOrderBySearch(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countOrderBySearch(jsonObject);
		List<JSONObject> list = orderDao.listOrderBySearch(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 按列排序
	 */
	@Override
	public JSONObject listOrderBySort(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countOrderBySort(jsonObject);
		List<JSONObject> list = orderDao.listOrderBySort(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}



	/**
	 * 根据各项参数搜索所有符合条件的销售子订单全字段数据（目前只使用mord_id)
	 */
	@Override
	public JSONObject listSubSaleOrderByParams(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		List<JSONObject> list = orderDao.listSubSaleOrderByParams(jsonObject);
		return CommonUtil.successPage(list);
	}


}