package com.heeexy.example.service.impl;



import com.sf.csim.express.service.CallExpressServiceTools;
import com.heeexy.example.service.DeliveryService;
//import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.DeliveryDao;
import com.heeexy.example.service.DeliveryService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import org.springframework.stereotype.Service;
//import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.alibaba.fastjson.JSONObject;
import org.springframework.transaction.annotation.Transactional;

//import java.text.SimpleDateFormat;
//import java.util.Date;

//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.Workbook;
//import cn.afterturn.easypoi.excel.ExcelExportUtil;

/**
 * @author:
=======
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.DeliveryDao;
import com.heeexy.example.service.DeliveryService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.jar.JarEntry;

/**
 * @author: lingling
>>>>>>> 6cb20ec44eabc9358fe186e63d59d2d2cd2ecb7c
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {


//	@Autowired
//	private PurchaseDao purchaseDao;

//	public String getCurTimestamp() {
//		//JSONObject resultJson = new JSONObject();
//
//		//使用Date创建日期对象
//		Date date = new Date();
//		System.out.println("当前的日期是------>"+date);
//		SimpleDateFormat curTime = new SimpleDateFormat("yyyyMMddHHmmss");
//		System.out.println("格式化后的时间------->"+curTime.format(date));
//
//		return curTime.format(date);
//	}


//    private String clientCode = "GRXM_pESEy";
//
//	private String checkword = "XMhA66EE5GzRaSgSu15MDVHNxewdSB7X";
//
//	private String custid;
	/**
	 *
	 */
	@Override
	public String getOrderServiceRequestXml(JSONObject jsonObject) {

		JSONObject params=jsonObject;

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<Request service=\"OrderService\" lang=\"zh-CN\">");
		strBuilder.append("<Head>" + params.get("clientCode") + "</Head>");
		strBuilder.append("<Body>");
		strBuilder.append("<Order").append(" ");
		strBuilder.append("orderid=\"" + params.get("orderid") + "\"").append(" ");
		//返回顺丰运单号
		strBuilder.append("express_type=\"1\"").append(" ");
		//寄件方信息
		strBuilder.append("j_company=\"" + params.get("j_company") + "\"").append(" ");
		strBuilder.append("j_contact=\"" + params.get("j_contact") + "\"").append(" ");
		strBuilder.append("j_tel=\"" + params.get("j_tel") + "\"").append(" ");


		strBuilder.append("j_mobile=\"" + params.get("j_mobile") + "\"").append(" ");
		strBuilder.append("j_province=\"" + params.get("j_province") + "\"").append(" ");
		strBuilder.append("j_city=\"" + params.get("j_city") + "\"").append(" ");

		strBuilder.append("j_county=\"" + params.get("j_county") + "\"").append(" ");
		strBuilder.append("j_address=\"" + params.get("j_address") + "\"").append(" ");


		//收件方信息
		strBuilder.append("d_company=\"" + params.get("d_company") + "\"").append(" ");
		strBuilder.append("d_contact=\"" + params.get("d_contact") + "\"").append(" ");
		strBuilder.append("d_tel=\"" + params.get("d_tel") + "\"").append(" ");
		strBuilder.append("d_mobile=\"" + params.get("d_mobile") + "\"").append(" ");
		strBuilder.append("d_province=\"" + params.get("d_province") + "\"").append(" ");
		strBuilder.append("d_city=\"" + params.get("d_city") + "\"").append(" ");
		strBuilder.append("d_county=\"" + params.get("d_county") + "\"").append(" ");
		strBuilder.append("d_address=\"" + params.get("d_address") + "\"").append(" ");
		//货物信息
		strBuilder.append("express_type=\"" + params.get("express_type") + "\"").append(" ");
		strBuilder.append("pay_method=\"" + params.get("pay_method") + "\"").append(" ");
		strBuilder.append("custid =\"" + params.get("custid") + "\"").append(" ");
		strBuilder.append("parcel_quantity =\"" + params.get("parcel_quantity") + "\"").append(" ");
		strBuilder.append("is_docall =\"" + params.get("is_docall") + "\"").append(" ");
		strBuilder.append("sendstarttime =\"" + params.get("sendstarttime") + "\"").append(" ");
		strBuilder.append("remark =\"" + params.get("remark") + "\"").append(" ");
		strBuilder.append("is_unified_waybill_no =\"" + params.get("is_unified_waybill_no") + "\"").append(">");
//		strBuilder.append("customs_batchs=\"\"").append(" ");
//		strBuilder.append("cargo=\"服装\"").append(">");
//		strBuilder.append("<AddedService name=\"COD' value='1.01' value1='7551234567' />");
		strBuilder.append("</Order>");
		strBuilder.append("</Body>");
		strBuilder.append("</Request>");

		return strBuilder.toString();

//		//订单号
//		String orderNo = jsonObject.get("orderNo").toString();
//		//收件人
//		String receiverName = jsonObject.get("receiverName").toString();
//		//收件人电话
//		String receiverMobile = jsonObject.get("receiverMobile").toString();
//		//收件人详细地址
//		String receiverAddress = jsonObject.get("address").toString();
//		//商品名称
//		String commodityName = jsonObject.get("commodityName").toString();
//		//商品数量
//		String orderNum = jsonObject.get("orderNum").toString();
//
//
//		StringBuilder strBuilder = new StringBuilder();
//		strBuilder.append("<Request service='OrderService' lang='zh-CN'>");
//		strBuilder.append("<Head>" + clientCode + "</Head>");
//		strBuilder.append("<Body>");
//		strBuilder.append("<Order").append(" ");
//		strBuilder.append("orderid='" + orderNo.toString().trim() + "" + "'").append(" ");
//		//返回顺丰运单号
//		strBuilder.append("is_gen_bill_no='1'").append(" ");
//		//寄件方信息
//		strBuilder.append("j_company='" + j_company + "'").append(" ");
//		strBuilder.append("j_contact='" + j_contact + "'").append(" ");
//		strBuilder.append("j_tel='" + j_tel + "'").append(" ");
//		strBuilder.append("j_address='" + j_province+j_city+j_county+j_address + "'").append(" ");
//		//收件方信息
//		strBuilder.append("d_company='" + d_company + "'").append(" ");
//		strBuilder.append("d_contact='" + receiverName.toString().trim() + "'").append(" ");
//		strBuilder.append("d_tel='" + receiverMobile.toString().trim() + "'").append(" ");
//		strBuilder.append("d_address='" + receiverAddress.toString().trim() + "'").append(" ");
//		strBuilder.append(" > ");
//		//货物信息
//		strBuilder.append("<Cargo").append(" ");
//		strBuilder.append("name='" + commodityName + "'").append(" ");
//		strBuilder.append("count='" + orderNum.toString() + "'").append(" ");
//		strBuilder.append("unit='双'").append(">");
//		strBuilder.append("</Cargo>");
//
//		strBuilder.append("</Order>");
//		strBuilder.append("</Body>");
//		strBuilder.append("</Request>");
//
//		return strBuilder.toString();

//		return CommonUtil.successPage(jsonObject, list, count);
	}




	@Override
	public JSONObject getOrderServiceRequestXmlResult(String resultxmlStr){
		org.json.JSONObject params= XML.toJSONObject(resultxmlStr);
		com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
		StringBuilder strBuilder = new StringBuilder();
		String head="";
		String errorContent="";

		System.out.println("resultxmlStr-XML:"+params);
		if(params.has("Response")){
			org.json.JSONObject myhead = params.getJSONObject("Response");
			if(myhead.has("Head")){
				String headdetail=myhead.get("Head").toString();
				if(headdetail.equals("OK")){
					result.put("addOrderResult","交易成功");
					if(myhead.has("Body")){
						if(myhead.getJSONObject("Body").has("OrderResponse")){
							if(myhead.getJSONObject("Body").getJSONObject("OrderResponse").has("mailno")){
								result.put("mailno",myhead.getJSONObject("Body").getJSONObject("OrderResponse").get("mailno").toString());
							}
						}
					}
				}else if(headdetail.equals("ERR")){
					result.put("addOrderResult","发生系统或业务异常，交易失败");
				}else{
					result.put("addOrderResult",myhead.get("Head").toString());
				}
				if(myhead.has("ERROR")){
					org.json.JSONObject myerror = myhead.getJSONObject("ERROR");
					if(myerror.has("content"))
						result.put("reason",myerror.get("content").toString());
					else
						result.put("reason","unknown error");
				}
			}else{
				result.put("addOrderResult","Empty");
			}
		}else{
			result.put("addOrderResult","Empty");
		}

		System.out.println("resultxmlStr:"+params);
		System.out.println("result:"+result);
//		String orderNo = params.get("orderNo").toString();
//		String clientCode = params.get("clientCode").toString();
//		StringBuilder strBuilder = new StringBuilder();
//		strBuilder.append("<Request service='OrderSearchService' lang='zh-CN'>");
//		strBuilder.append("<Head>" + clientCode + "</Head>");
//		strBuilder.append("<Body>");
//		strBuilder.append("<OrderSearch").append(" ");
//		strBuilder.append("orderid='" + orderNo.toString().trim() + "" + "'").append(" > ");
//		strBuilder.append("</OrderSearch>");
//		strBuilder.append("</Body>");
//		strBuilder.append("</Request>");
		return result;
	}


	/**
	 * 顺丰接口
	 * @param //params
	 * @param //type  1-下订单接口  2-订单结果查询接口
	 * @return
	 */
	@Override
	public String callSf(String xmlStr, String url, String clientCode, String checkword ) {
		CallExpressServiceTools client = CallExpressServiceTools.getInstance();
//		String url="http://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService";
//		String clientCode="GRXM_pESEy";
//		String checkword="XMhA66EE5GzRaSgSu15MDVHNxewdSB7X";

//		log.info("开始调用顺丰接口下单，请求报文:{}", xmlStr);
		String respXml = client.callSfExpressServiceByCSIM(url, xmlStr, clientCode, checkword);
//		log.info("请求完成，返回报文:{}", respXml);
		return respXml;
	}


	@Override
	public String getOrderSearchServiceRequestXml(JSONObject jsonObject){
		JSONObject params=jsonObject;

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<Request service='RouteService' lang='zh-CN'>");
		strBuilder.append("<Head>" + params.get("clientCode") + "</Head>");
		strBuilder.append("<Body>");
		strBuilder.append("<RouteRequest").append(" ");
		strBuilder.append("tracking_type='1'").append(" ");
		strBuilder.append("method_type='1'").append(" ");
		strBuilder.append("tracking_number='" + params.get("mailno") + "'").append(" >");
		strBuilder.append("</RouteRequest>");
		strBuilder.append("</Body>");
		strBuilder.append("</Request>");

		return strBuilder.toString();
	}

	@Override
	public JSONObject getOrderSearchServiceRequestXmlResult(String resultxmlStr){
		org.json.JSONObject params= XML.toJSONObject(resultxmlStr);
		com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
		StringBuilder strBuilder = new StringBuilder();
		String head="";
		String errorContent="";

		System.out.println("resultxmlStr-XML:"+params);
		if(params.has("Response")){
			org.json.JSONObject myhead = params.getJSONObject("Response");
			if(myhead.has("Body")){
				org.json.JSONObject mybody = myhead.getJSONObject("Body");
				if(mybody.has("RouteResponse")){
					org.json.JSONObject myrouteresponse = mybody.getJSONObject("RouteResponse");
					if(myrouteresponse.has("Route")){
						Object listArray = myrouteresponse.get("Route");
						if (listArray instanceof org.json.JSONArray){
							org.json.JSONArray jsonArray = (org.json.JSONArray)listArray;
							for (int k = 0; k < jsonArray.length(); k++) {
								org.json.JSONObject parameterObject = jsonArray.getJSONObject(k);
								if(jsonArray.getJSONObject(k).has("accept_address"))
									result.put("accept_address"+k,jsonArray.getJSONObject(k).get("accept_address"));
								else
									result.put("accept_address"+k,"empty");
								if(jsonArray.getJSONObject(k).has("accept_time"))
									result.put("accept_time"+k,jsonArray.getJSONObject(k).get("accept_time"));
								else
									result.put("accept_time"+k,"empty");
								if(jsonArray.getJSONObject(k).has("remark"))
									result.put("remark"+k,jsonArray.getJSONObject(k).get("remark"));
								else
									result.put("remark"+k,"empty");
								System.out.println(parameterObject);
							}
							if(myhead.has("Head"))
								result.put("searchOrderResult",myhead.get("Head"));
//							if(jsonArray.getJSONObject(jsonArray.length()-1).has("accept_address"))
//								result.put("accept_address",jsonArray.getJSONObject(jsonArray.length()-1).get("accept_address"));
//							if(jsonArray.getJSONObject(jsonArray.length()-1).has("accept_time"))
//								result.put("accept_time",jsonArray.getJSONObject(jsonArray.length()-1).get("accept_time"));
//							if(jsonArray.getJSONObject(jsonArray.length()-1).has("remark"))
//								result.put("remark",jsonArray.getJSONObject(jsonArray.length()-1).get("remark"));
						}else if (listArray instanceof org.json.JSONObject) {
							JSONObject jsonObject3 = (JSONObject)listArray;
							System.out.println(jsonObject3);
						}else{
							System.out.println("nonononononononono");
						}
					}else if(myhead.has("Head")){
						result.put("searchOrderResult",myhead.get("Head"));
						if(myhead.has("ERROR"))
							result.put("reason",myhead.get("ERROR"));
					}
				}
			}
		}




//		if(params.has("Response")){
//			org.json.JSONObject myhead = params.getJSONObject("Response");
//			if(myhead.has("Head")){
//				String headdetail=myhead.get("Head").toString();
//				if(headdetail.equals("OK")){
//					result.put("addOrderResult","交易成功");
//					if(myhead.has("Body")){
//						if(myhead.getJSONObject("Body").has("OrderResponse")){
//							if(myhead.getJSONObject("Body").getJSONObject("OrderResponse").has("mailno")){
//								result.put("mailno",myhead.getJSONObject("Body").getJSONObject("OrderResponse").get("mailno").toString());
//							}
//						}
//					}
//				}else if(headdetail.equals("ERR")){
//					result.put("addOrderResult","发生系统或业务异常，交易失败");
//				}else{
//					result.put("addOrderResult",myhead.get("Head").toString());
//				}
//				if(myhead.has("ERROR")){
//					org.json.JSONObject myerror = myhead.getJSONObject("ERROR");
//					if(myerror.has("content"))
//						result.put("reason",myerror.get("content").toString());
//					else
//						result.put("reason","unknown error");
//				}
//			}else{
//				result.put("addOrderResult","Empty");
//			}
//		}else{
//			result.put("addOrderResult","Empty");
//		}

//		System.out.println("resultxmlStr:"+params);
		System.out.println("result:"+result);

		return result;
	}

	@Override
	public List<JSONObject>  getOrderSearchServiceRequestXmlResultByList(String resultxmlStr){
		org.json.JSONObject params= XML.toJSONObject(resultxmlStr);

		List<JSONObject> resultList= new ArrayList<JSONObject>();
		StringBuilder strBuilder = new StringBuilder();
		String head="";
		String errorContent="";

		System.out.println("resultxmlStr-XML:"+params);
		if(params.has("Response")){
			org.json.JSONObject myhead = params.getJSONObject("Response");
			if(myhead.has("Body")){
				org.json.JSONObject mybody = myhead.getJSONObject("Body");
				if(mybody.has("RouteResponse")){
					org.json.JSONObject myrouteresponse = mybody.getJSONObject("RouteResponse");
					if(myrouteresponse.has("Route")){
						if(myhead.has("Head")){
							com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
							result.put("searchOrderResult",myhead.get("Head"));
							resultList.add(result);
						}
						Object listArray = myrouteresponse.get("Route");
						if (listArray instanceof org.json.JSONArray){
							org.json.JSONArray jsonArray = (org.json.JSONArray)listArray;
							for (int k = 0; k < jsonArray.length(); k++) {
								org.json.JSONObject parameterObject = jsonArray.getJSONObject(k);
								com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
								if(jsonArray.getJSONObject(k).has("accept_address"))
									result.put("accept_address",jsonArray.getJSONObject(k).get("accept_address"));
								else
									result.put("accept_address","empty");
								if(jsonArray.getJSONObject(k).has("accept_time"))
									result.put("accept_time",jsonArray.getJSONObject(k).get("accept_time"));
								else
									result.put("accept_time","empty");
								if(jsonArray.getJSONObject(k).has("remark"))
									result.put("remark",jsonArray.getJSONObject(k).get("remark"));
								else
									result.put("remark","empty");
								resultList.add(result);
								System.out.println(parameterObject);
							}


//							if(jsonArray.getJSONObject(jsonArray.length()-1).has("accept_address"))
//								result.put("accept_address",jsonArray.getJSONObject(jsonArray.length()-1).get("accept_address"));
//							if(jsonArray.getJSONObject(jsonArray.length()-1).has("accept_time"))
//								result.put("accept_time",jsonArray.getJSONObject(jsonArray.length()-1).get("accept_time"));
//							if(jsonArray.getJSONObject(jsonArray.length()-1).has("remark"))
//								result.put("remark",jsonArray.getJSONObject(jsonArray.length()-1).get("remark"));
						}else if (listArray instanceof org.json.JSONObject) {
							JSONObject jsonObject3 = (JSONObject)listArray;
							resultList.add(jsonObject3);
							System.out.println(jsonObject3);
						}else{
							System.out.println("nonononononononono");
						}
					}else if(myhead.has("Head")){
						com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
						result.put("searchOrderResult",myhead.get("Head"));

						if(myhead.has("ERROR"))
							result.put("reason",myhead.get("ERROR"));

						resultList.add(result);
					}
				}
			}
		}




//		if(params.has("Response")){
//			org.json.JSONObject myhead = params.getJSONObject("Response");
//			if(myhead.has("Head")){
//				String headdetail=myhead.get("Head").toString();
//				if(headdetail.equals("OK")){
//					result.put("addOrderResult","交易成功");
//					if(myhead.has("Body")){
//						if(myhead.getJSONObject("Body").has("OrderResponse")){
//							if(myhead.getJSONObject("Body").getJSONObject("OrderResponse").has("mailno")){
//								result.put("mailno",myhead.getJSONObject("Body").getJSONObject("OrderResponse").get("mailno").toString());
//							}
//						}
//					}
//				}else if(headdetail.equals("ERR")){
//					result.put("addOrderResult","发生系统或业务异常，交易失败");
//				}else{
//					result.put("addOrderResult",myhead.get("Head").toString());
//				}
//				if(myhead.has("ERROR")){
//					org.json.JSONObject myerror = myhead.getJSONObject("ERROR");
//					if(myerror.has("content"))
//						result.put("reason",myerror.get("content").toString());
//					else
//						result.put("reason","unknown error");
//				}
//			}else{
//				result.put("addOrderResult","Empty");
//			}
//		}else{
//			result.put("addOrderResult","Empty");
//		}

//		System.out.println("resultxmlStr:"+params);
		System.out.println("result:"+resultList);

		return resultList;
	}

//	@Override
//	public JSONObject getOrderSearchServiceRequestXmlResult(String resultxmlStr){
//		org.json.JSONObject params= XML.toJSONObject(resultxmlStr);
//		com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
//		StringBuilder strBuilder = new StringBuilder();
//		String head="";
//		String errorContent="";
//
//		System.out.println("resultxmlStr-XML:"+params);
//		if(params.has("Response")){
//			org.json.JSONObject myhead = params.getJSONObject("Response");
//			if(myhead.has("Body")){
//				org.json.JSONObject mybody = myhead.getJSONObject("Body");
//				if(mybody.has("RouteResponse")){
//					org.json.JSONObject myrouteresponse = mybody.getJSONObject("RouteResponse");
//					if(myrouteresponse.has("Route")){
//						Object listArray = myrouteresponse.get("Route");
//						if (listArray instanceof org.json.JSONArray){
//							org.json.JSONArray jsonArray = (org.json.JSONArray)listArray;
//							for (int k = 0; k < jsonArray.length(); k++) {
//								org.json.JSONObject parameterObject = jsonArray.getJSONObject(k);
//								if(jsonArray.getJSONObject(k).has("accept_address"))
//									result.put("accept_address"+k,jsonArray.getJSONObject(k).get("accept_address"));
//								else
//									result.put("accept_address"+k,"empty");
//								if(jsonArray.getJSONObject(k).has("accept_time"))
//									result.put("accept_time"+k,jsonArray.getJSONObject(k).get("accept_time"));
//								else
//									result.put("accept_time"+k,"empty");
//								if(jsonArray.getJSONObject(k).has("remark"))
//									result.put("remark"+k,jsonArray.getJSONObject(k).get("remark"));
//								else
//									result.put("remark"+k,"empty");
//								System.out.println(parameterObject);
//							}
//							if(myhead.has("Head"))
//								result.put("searchOrderResult",myhead.get("Head"));
////							if(jsonArray.getJSONObject(jsonArray.length()-1).has("accept_address"))
////								result.put("accept_address",jsonArray.getJSONObject(jsonArray.length()-1).get("accept_address"));
////							if(jsonArray.getJSONObject(jsonArray.length()-1).has("accept_time"))
////								result.put("accept_time",jsonArray.getJSONObject(jsonArray.length()-1).get("accept_time"));
////							if(jsonArray.getJSONObject(jsonArray.length()-1).has("remark"))
////								result.put("remark",jsonArray.getJSONObject(jsonArray.length()-1).get("remark"));
//						}else if (listArray instanceof org.json.JSONObject) {
//							JSONObject jsonObject3 = (JSONObject)listArray;
//							System.out.println(jsonObject3);
//						}else{
//							System.out.println("nonononononononono");
//						}
//					}else if(myhead.has("Head")){
//						result.put("searchOrderResult",myhead.get("Head"));
//						if(myhead.has("ERROR"))
//							result.put("reason",myhead.get("ERROR"));
//					}
//				}
//			}
//		}
//
//
//
//
////		if(params.has("Response")){
////			org.json.JSONObject myhead = params.getJSONObject("Response");
////			if(myhead.has("Head")){
////				String headdetail=myhead.get("Head").toString();
////				if(headdetail.equals("OK")){
////					result.put("addOrderResult","交易成功");
////					if(myhead.has("Body")){
////						if(myhead.getJSONObject("Body").has("OrderResponse")){
////							if(myhead.getJSONObject("Body").getJSONObject("OrderResponse").has("mailno")){
////								result.put("mailno",myhead.getJSONObject("Body").getJSONObject("OrderResponse").get("mailno").toString());
////							}
////						}
////					}
////				}else if(headdetail.equals("ERR")){
////					result.put("addOrderResult","发生系统或业务异常，交易失败");
////				}else{
////					result.put("addOrderResult",myhead.get("Head").toString());
////				}
////				if(myhead.has("ERROR")){
////					org.json.JSONObject myerror = myhead.getJSONObject("ERROR");
////					if(myerror.has("content"))
////						result.put("reason",myerror.get("content").toString());
////					else
////						result.put("reason","unknown error");
////				}
////			}else{
////				result.put("addOrderResult","Empty");
////			}
////		}else{
////			result.put("addOrderResult","Empty");
////		}
//
////		System.out.println("resultxmlStr:"+params);
//		System.out.println("result:"+result);
//
//		return result;
//	}

    @Autowired
    private DeliveryDao deliveryDao;

    /**
     * 全部物流信息列表
     */
    @Override
    public JSONObject listDelivery(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        System.out.println("jsonObject:" + jsonObject);
        int count = deliveryDao.countDelivery(jsonObject);
        List<JSONObject> list = deliveryDao.listDelivery(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 按列排序+搜索
     */
    @Override
    public JSONObject listDeliveryBySearch(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = deliveryDao.countDeliveryBySearch(jsonObject);
        List<JSONObject> list = deliveryDao.listDeliveryBySearch(jsonObject);
        System.out.print(list);
        return CommonUtil.successPage(jsonObject, list, count);
    }

	/**
	 * 更新物流信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateDelivery(JSONObject jsonObject) {
		deliveryDao.updateDelivery(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 新增物流待发货单，供其他模块调用，如出库模块
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addDeliveryOrder(JSONObject jsonObject) {
		deliveryDao.addDeliveryOrder(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 按状态搜索（待办事项功能）(返回所有符合条件订单Json对象)(author: bguan)
	 */
	@Override
	public JSONObject todoSearchByStatus(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = deliveryDao.todoSearchByStatus(jsonObject);
		List<JSONObject> list = deliveryDao.todoSearchEventByStatus(jsonObject);

		return CommonUtil.successPage(jsonObject, list, count);
	}
}
