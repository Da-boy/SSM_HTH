package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.CompanyService;
import com.heeexy.example.service.OrderService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: dengchenglong
 * @description: 订单信息Controller
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    private CompanyService companyService;

    /**
     * 查询订单信息列表
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/listOrder")
    public JSONObject listOrder(HttpServletRequest request) {
        return orderService.listOrder(CommonUtil.request2Json(request));
    }

    /**
     * 查询父订单对应的所有子订单
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/getSonOrderByfathor")
    public JSONObject getSonOrderByfathor(HttpServletRequest request) {
        return orderService.getSonOrderByfathor(CommonUtil.request2Json(request));
    }

    /**
     * 查询父订单基本信息列表
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/listMordOrder")
    public JSONObject listMordOrder(HttpServletRequest request) {
        return orderService.listMordOrder(CommonUtil.request2Json(request));
    }

    /**
     * 查询详细订单信息列表
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/getRowDetailByID")
    public JSONObject getRowDetailByID(HttpServletRequest request) {
        return orderService.getRowDetailByID(CommonUtil.request2Json(request));
    }

    /**
     * 查询销售经理
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/listSales")
    public JSONObject listSales(HttpServletRequest request) {
        return orderService.listSales(CommonUtil.request2Json(request));
    }

    @RequiresPermissions("order:check")
    @PostMapping("/checkSub")
    public JSONObject checkSub(@RequestBody JSONObject requestJson) {
//		System.out.println("requestJson:"+requestJson);
//		CommonUtil.hasAllRequired(requestJson, "company_name, telephone, address, register_id, taxpayer_identification_number, organizational_code");
//		return companyService.updateCompany(requestJson);
//		System.out.println("审核："+requestJson);
        JSONObject result = orderService.updateOrderByCheck(requestJson);
        return CommonUtil.successJson("成功");
    }

    @RequiresPermissions("order:warehouseout")
    @PostMapping("/addexwarehouse")
    public JSONObject addexwarehouse(@RequestBody JSONObject requestJson) {
//		System.out.println("requestJson:"+requestJson);
//		CommonUtil.hasAllRequired(requestJson, "company_name, telephone, address, register_id, taxpayer_identification_number, organizational_code");
//		return companyService.updateCompany(requestJson);
//		System.out.println("出库订单："+requestJson);
        JSONObject result = orderService.addexwarehouse(requestJson);
        return CommonUtil.successJson("成功");
    }

    //创建设计订单记录
    @RequiresPermissions("order:design")
    @PostMapping("/adddesign")
    public JSONObject adddesign(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "sales_mord_id, create_staff_id, status");
        return orderService.adddesign(requestJson);
    }

    //二次更新设计订单记录
    @RequiresPermissions("design_order:update")
    @PostMapping("/updatedesign")
    public JSONObject updatedesign(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "saling_son_order_id, design_staff_id");
        return orderService.updatedesign(requestJson);
    }

    //二次更新设计订单完成时间
    @RequiresPermissions("design_order:update")
    @PostMapping("/updatedesigndonetime")
    public JSONObject updatedesigndonetime(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "saling_son_order_id");
        return orderService.updatedesigndonetime(requestJson);
    }

    @RequiresPermissions("order:product")
    @PostMapping("/addproduct")
    public JSONObject addproduct(@RequestBody JSONObject requestJson) {
//		System.out.println("requestJson:"+requestJson);
//		CommonUtil.hasAllRequired(requestJson, "company_name, telephone, address, register_id, taxpayer_identification_number, organizational_code");
//		return companyService.updateCompany(requestJson);
//		System.out.println("生产订单："+requestJson);
        JSONObject result = orderService.addproduct(requestJson);
        return CommonUtil.successJson("成功");
    }

    //	@RequiresPermissions("company:list")
    @GetMapping("/getCompanyByContent")
    public JSONObject getCompanyByContent(HttpServletRequest request) {
        return companyService.getCompanyByContent(CommonUtil.request2Json(request));
    }

    //	@RequiresPermissions("company:list")
    @GetMapping("/getTelephoneByContent")
    public JSONObject getTelephoneByContent(HttpServletRequest request) {
        return companyService.getTelephoneByContent(CommonUtil.request2Json(request));
    }

    //	@RequiresPermissions("company:list")
    @GetMapping("/getAddressByContent")
    public JSONObject getAddressByContent(HttpServletRequest request) {
        return companyService.getAddressByContent(CommonUtil.request2Json(request));
    }

    //	@RequiresPermissions("company:list")
    @GetMapping("/getRegisterIdByContent")
    public JSONObject getRegisterIdByContent(HttpServletRequest request) {
        return companyService.getRegisterIdByContent(CommonUtil.request2Json(request));
    }

    //	@RequiresPermissions("company:list")
    @GetMapping("/getTaxpayerByContent")
    public JSONObject getTaxpayerByContent(HttpServletRequest request) {
        return companyService.getTaxpayerByContent(CommonUtil.request2Json(request));
    }

    //	@RequiresPermissions("company:list")
    @GetMapping("/getOrganizationalByContent")
    public JSONObject getOrganizationalByContent(HttpServletRequest request) {
        return companyService.getOrganizationalByContent(CommonUtil.request2Json(request));
    }

    //	@RequiresPermissions("company:list")
    @GetMapping("/getSocialByContent")
    public JSONObject getSocialByContent(HttpServletRequest request) {
        return companyService.getSocialByContent(CommonUtil.request2Json(request));
    }

    //	@RequiresPermissions("company:list")
    @GetMapping("/getMailByContent")
    public JSONObject getMailByContent(HttpServletRequest request) {
        return companyService.getMailByContent(CommonUtil.request2Json(request));
    }

    //	@RequiresPermissions("company:list")
    @GetMapping("/getFaxByContent")
    public JSONObject getFaxByContent(HttpServletRequest request) {
        return companyService.getFaxByContent(CommonUtil.request2Json(request));
    }

    /**
     * 搜索订单信息列表
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/listOrderBySearch")
    public JSONObject listOrderBySearch(HttpServletRequest request) {
        return orderService.listOrderBySearch(CommonUtil.request2Json(request));
    }

    /**
     * 根据order_id搜索order_type
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/getOrderTypeByID")
    public JSONObject getOrderTypeByID(HttpServletRequest request) {
        return orderService.getOrderTypeByID(CommonUtil.request2Json(request));
    }

    /**
     * 根据order_id搜索所有相关RNA Oligo内容
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/getRNAOligoByID")
    public JSONObject getRNAOligoByID(HttpServletRequest request) {
        return orderService.getRNAOligoByID(CommonUtil.request2Json(request));
    }

    /**
     * 根据order_id搜索所有相关载体内容
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/getCarrierByID")
    public JSONObject getCarrierByID(HttpServletRequest request) {
        return orderService.getCarrierByID(CommonUtil.request2Json(request));
    }

    /**
     * 根据order_id搜索所有相关DNA探针内容
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/getDNAProbeByID")
    public JSONObject getDNAProbeByID(HttpServletRequest request) {
        return orderService.getDNAProbeByID(CommonUtil.request2Json(request));
    }

    /**
     * 根据order_id搜索所有相关FISH试剂盒及其他内容
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/getFISHKitByID")
    public JSONObject getFISHKitByID(HttpServletRequest request) {
        return orderService.getFISHKitByID(CommonUtil.request2Json(request));
    }

    /**
     * 根据order_id搜索所有相关试剂盒引物探针套装内容
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/getKitProbeByID")
    public JSONObject getKitProbeByID(HttpServletRequest request) {
        return orderService.getKitProbeByID(CommonUtil.request2Json(request));
    }

    /**
     * 根据order_id搜索所有相关病毒内容
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/getVirusByID")
    public JSONObject getVirusByID(HttpServletRequest request) {
//		System.out.println("virus: "+ orderService.getVirusByID(CommonUtil.request2Json(request)));
        return orderService.getVirusByID(CommonUtil.request2Json(request));
    }

    /**
     * 根据order_id搜索所有相关全基因内容
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/getWholeGeneByID")
    public JSONObject getWholeGeneByID(HttpServletRequest request) {
        return orderService.getWholeGeneByID(CommonUtil.request2Json(request));
    }

    /**
     * 新增Oligo订单信息
     */
    @RequiresPermissions("order:add")
    @PostMapping("/addOligo")
    public JSONObject addOligo(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.addOligo(requestJson);
    }

    /**
     * 修改Oligo订单信息
     */
    @RequiresPermissions("design_order:update")
    @PostMapping("/updateOligo")
    public JSONObject updateOligo(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.updateOligo(requestJson);
    }

    /**
     * 新增试剂盒&引物探针套装订单信息
     */
    @RequiresPermissions("order:add")
    @PostMapping("/addKitProbe")
    public JSONObject addKitProbe(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.addKitProbe(requestJson);
    }

    /**
     * 修改试剂盒&引物探针套装订单信息
     */
    @RequiresPermissions("design_order:update")
    @PostMapping("/updateKitProbe")
    public JSONObject updateKitProbe(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.updateKitProbe(requestJson);
    }

    /**
     * 新增载体订单信息
     */
    @RequiresPermissions("order:add")
    @PostMapping("/addCarrier")
    public JSONObject addCarrier(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.addCarrier(requestJson);
    }

    /**
     * 修改载体订单信息
     */
    @RequiresPermissions("design_order:update")
    @PostMapping("/updateCarrier")
    public JSONObject updateCarrier(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.updateCarrier(requestJson);
    }

    /**
     * 新增DNA探针合成订购单信息
     */
    @RequiresPermissions("order:add")
    @PostMapping("/addDNAProbe")
    public JSONObject addDNAProbe(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.addDNAProbe(requestJson);
    }

    /**
     * 修改DNA探针合成订购单信息
     */
    @RequiresPermissions("design_order:update")
    @PostMapping("/updateDNAProbe")
    public JSONObject updateDNAProbe(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.updateDNAProbe(requestJson);
    }

    /**
     * 新增FISH试剂盒&其他订购单信息
     */
    @RequiresPermissions("order:add")
    @PostMapping("/addFISH")
    public JSONObject addFISH(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.addFISH(requestJson);
    }

    /**
     * 修改FISH试剂盒&其他订购单信息
     */
    @RequiresPermissions("design_order:update")
    @PostMapping("/updateFISH")
    public JSONObject updateFISH(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.updateFISH(requestJson);
    }

    /**
     * 新增病毒订购单信息
     */
    @RequiresPermissions("order:add")
    @PostMapping("/addVirus")
    public JSONObject addVirus(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.addVirus(requestJson);
    }

    /**
     * 修改病毒订购单信息
     */
    @RequiresPermissions("design_order:update")
    @PostMapping("/updateVirus")
    public JSONObject updateVirus(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.updateVirus(requestJson);
    }

    /**
     * 新增全基因合成订购单信息
     */
    @RequiresPermissions("order:add")
    @PostMapping("/addWholeGene")
    public JSONObject addWholeGene(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.addWholeGene(requestJson);
    }

    /**
     * 修改全基因合成订购单信息
     */
    @RequiresPermissions("design_order:update")
    @PostMapping("/updateWholeGene")
    public JSONObject updateWholeGene(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "client_name");
        return orderService.updateWholeGene(requestJson);
    }

    /**
     * 删除订单信息
     */
    @RequiresPermissions("order:delete")
    @PostMapping("/deleteOrder")
    public JSONObject deleteOrder(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "order_id");
        return orderService.deleteOrder(requestJson);
    }

    /**
     * 审核订单
     */
    @RequiresPermissions("order:check")
    @PostMapping("/checkOrder")
    public JSONObject checkOrder(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "mord_id");
        return orderService.checkOrder(requestJson);
    }

    /**
     * 审核订单
     */
    @RequiresPermissions("order:check")
    @PostMapping("/updateOrderStatus")
    public JSONObject updateOrderStatus(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "order_id");
        return orderService.updateOrderStatus(requestJson);
    }

    /**
     * 按状态搜索订单列表（待办事项功能）(author:bguan)
     */
//	@RequiresPermissions("article:list")
    @PostMapping("/todoSearchByStatus")
    public JSONObject todoSearchByStatus(@RequestBody JSONObject requestJson) {
        System.out.println("requestJson:" + requestJson);
        CommonUtil.hasAllRequired(requestJson, "status");
        return orderService.todoSearchByStatus(requestJson);
    }

    /**
     * 按列排序
     */
//	@RequiresPermissions("order:list")
    @GetMapping("/listOrderBySort")
    public JSONObject listPurchaseBySort(HttpServletRequest request) {
        System.out.println(CommonUtil.request2Json(request));
        return orderService.listOrderBySort(CommonUtil.request2Json(request));
    }


    /**
     * 根据各项参数搜索所有符合条件的销售子订单全字段数据（目前只使用mord_id)
     */
//	@RequiresPermissions("order:list")
    @PostMapping("/listSubSaleOrderByParams")
    public JSONObject listSubSaleOrderByParams(@RequestBody JSONObject requestJson) {
        return orderService.listSubSaleOrderByParams(requestJson);
    }


//	注：以下代码已移至代码ChargeoffController.java中！！（测试无误后，在交付版本中需删除，包括Service、Dao、XML中的相应代码）
//	/**
//	 * 销账操作
//	 */
//	@RequiresPermissions("order:add")
//	@PostMapping("/orderChargeoff")
//	public JSONObject orderChargeoff(@RequestBody JSONObject requestJson) {
//		CommonUtil.hasAllRequired(requestJson, "sum, status, mord_id");
//		return orderService.orderChargeoff(requestJson);
//	}
//	@RequiresPermissions("order:list")
//	@GetMapping("/listChargeoffRecord")
//	public JSONObject listChargeoffRecord(HttpServletRequest request) {
//		return orderService.listChargeoffRecord(CommonUtil.request2Json(request));
//	}
//	//销账催账：仅列出账单状态的父订单：["已签收待支付","部分支付","已支付"]
//	@RequiresPermissions("order:list")
//	@GetMapping("/listBillOrder")
//	public JSONObject listBillOrder(HttpServletRequest request) {
//		return orderService.listBillOrder(CommonUtil.request2Json(request));
//	}
//	//销账催账：检索功能
//	@RequiresPermissions("order:list")
//	@GetMapping("/listBillOrderBySearch")
//	public JSONObject listBillOrderBySearch(HttpServletRequest request) {
//		return orderService.listBillOrderBySearch(CommonUtil.request2Json(request));
//	}
//	//销账催账：发送邮件短信功能，同时记录数据库
//	@RequiresPermissions("order:list")
//	@PostMapping("/doNotification")
//	public JSONObject doNotification(@RequestBody JSONObject requestJson) {
//		CommonUtil.hasAllRequired(requestJson, "toStaffEmail, emailSubject, emailContent");
//		return orderService.doNotification(requestJson);
//	}

}
