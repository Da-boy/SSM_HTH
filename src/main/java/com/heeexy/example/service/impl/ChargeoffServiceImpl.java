package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ChargeoffDao;
import com.heeexy.example.service.ChargeoffService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: bguan
 * @description: 系统中设置相关
 */
@Service
public class ChargeoffServiceImpl implements ChargeoffService {

    @Autowired
    private ChargeoffDao chargeoffDao;

    @Autowired
    SendMailService sendMailService;

    /**
     * 按状态搜索（待办事项功能）(返回所有符合条件订单Json对象)(author: bguan)
     */
    @Override
    public JSONObject todoSearchByStatus(JSONObject jsonObject) {
//		System.out.println("todoSearchByStatus:xxxxxxxxxxxxxxxx");
        CommonUtil.fillPageParam(jsonObject);
//		System.out.println("jsonObject:"+jsonObject);
        int count = chargeoffDao.todoSearchByStatus(jsonObject);
//		System.out.println(jsonObject.get("status").toString());
//		System.out.println("status:"+count);
        List<JSONObject> list = chargeoffDao.todoSearchEventByStatus(jsonObject);

        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 新增催账销账单，供其他模块调用，如物流模块签收后可调用
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addOrderBill(JSONObject jsonObject) {
        chargeoffDao.addOrderBill(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 销账操作
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject orderChargeoff(JSONObject jsonObject) {
        String time = CommonUtil.getCurTimestampForDB();
        jsonObject.put("updatetime", time);
        //查询数据库是否存在设置
        int count = chargeoffDao.countChargeoffRecord(jsonObject);
        if(count == 0){
            chargeoffDao.addChargeoffRecord(jsonObject);
        }
        else{
            chargeoffDao.updateChargeoffRecord(jsonObject);
            chargeoffDao.updateAdvancePayment(jsonObject);
        }
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject listChargeoffRecord(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = chargeoffDao.listChargeoffRecord(jsonObject);
//		System.out.print(list);
        int count = list.size();
        return CommonUtil.successPage(jsonObject, list, count);
    }

    //销账催账：仅列出账单状态的父订单：["已签收待支付","部分支付","已支付"]
    @Override
    public JSONObject listBillOrder(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = chargeoffDao.countBillOrder(jsonObject);
        List<JSONObject> list = chargeoffDao.listBillOrder(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
    //销账催账：检索功能
    @Override
    public JSONObject listBillOrderBySearch(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = chargeoffDao.countBillOrderBySearch(jsonObject);
        List<JSONObject> list = chargeoffDao.listBillOrderBySearch(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
    //销账催账：发送邮件短信功能，同时记录数据库
    @Override
    public JSONObject doNotification(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        String toEmail = jsonObject.getString("toStaffEmail");
        String subject = jsonObject.getString("emailSubject");
        String content = jsonObject.getString("emailContent");
        String personalStr = jsonObject.getString("emailPersonalStr");
        String timeStamp = CommonUtil.getCurTimestampForDB();
        jsonObject.put("noteTime", timeStamp);
        jsonObject.put("fromEmail", sendMailService.getFromEmail());

        //test
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx toEmail: " + toEmail);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx subject: " + subject);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx content: " + content);

        try {
//			System.out.println("00000000000000000000000000000000000000000");
            sendMailService.SendOneRecommend(toEmail, subject, content, personalStr);
            System.out.println("11111111111111111111111111111111111111111");
            //通知成功记录
            jsonObject.put("status1", 0);
            chargeoffDao.logUigeBillRecord(jsonObject);
        } catch (Exception e) {
            System.out.println("22222222222222222222222222222222222222222:" + e);
            e.printStackTrace();
//			System.out.println("33333333333333333333333333333333333333333");
            //通知失败记录
            jsonObject.put("status1", 1);
            chargeoffDao.logUigeBillRecord(jsonObject);
        }

        System.out.println("4444444444444444444444444444444444444");
        return CommonUtil.successJson();
    }

    /**
     * 添加客户到黑名单/移除客户从黑名单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateClientBlackList(JSONObject jsonObject) {
        chargeoffDao.updateClientBlackList(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject listClientBlackList(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = chargeoffDao.listClientBlackList(jsonObject);
        int count = list.size();
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
