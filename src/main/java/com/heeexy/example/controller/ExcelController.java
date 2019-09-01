package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.OrderDao;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.service.OrderService;
import com.heeexy.example.util.CommonUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.text.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/excel")
public class ExcelController {

//    @Autowired
//    private TopicService topicService;
    @Autowired
    private ArticleService articleService;

//    @Autowired
//    private ArticleDao articleDao;

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderService orderService;

    private void readController(Sheet sheet, JSONObject order, JSONArray tableDatas, boolean flag, String sheetName){

        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();     //每张表行数
        // System.out.println("共有" + physicalNumberOfRows + "行");
        for (int j = 0; j < physicalNumberOfRows; j++) {
//            System.out.println("第" + j + "行");
            int physicalNumberOfCells = sheet.getRow(j).getPhysicalNumberOfCells(); //每行的列数
//            System.out.println("该行有" + physicalNumberOfCells + "列");

            //表格读取完毕条件：首个单元格内容为“备注”
            if (sheet.getRow(j).getCell(0).toString().indexOf("备注") != -1) {
//                System.out.println("信息表读取完毕！！！！！！！！");
                order.put("tableDatas", tableDatas);                    //信息表读取完毕，存储
                order.put("remark", sheet.getRow(j).getCell(1).toString());
                continue;
            }
            //表格读取完毕，退出
            if (sheet.getRow(j).getCell(0).toString().indexOf("其他") != -1) {
//                System.out.println("sheet读取完毕！！退出！！");
                break;
            }

            //判断是否读到订单信息
            if (flag) {
                if(sheetName.indexOf("病毒") != -1) {
                    this.readVirusOrderInfo(sheet, j, physicalNumberOfCells, tableDatas);         //读病毒订单信息
                }else if(sheet.getSheetName().indexOf("RNA") != -1) {
                    this.readRNAOrderInfo(sheet, j, physicalNumberOfCells, tableDatas);        //RNA
                }else if(sheet.getSheetName().indexOf("试剂盒") != -1) {
                    this.readKitOrderInfo(sheet, j, physicalNumberOfCells, tableDatas);        //试剂盒
                }else if(sheet.getSheetName().indexOf("载体") != -1) {
                    this.readCarrierOrderInfo(sheet, j, physicalNumberOfCells, tableDatas);        //载体
                }else if(sheet.getSheetName().indexOf("探针合成") != -1) {
                    this.readProbeOrderInfo(sheet, j, physicalNumberOfCells, tableDatas);        //探针合成
                }else if(sheet.getSheetName().indexOf("FISH") != -1) {
                    this.readFISHOrderInfo(sheet, j, physicalNumberOfCells, tableDatas);        //FISH
                }else if(sheet.getSheetName().indexOf("全基因") != -1) {
                    this.readGeneOrderInfo(sheet, j, physicalNumberOfCells, tableDatas);        //全基因
                }
            }

            //读基本信息
            if (!flag) {
                flag = this.readBasicInfo(sheet, j, physicalNumberOfCells, order, flag);
            }
        }
    }

    //读取客户基本信息
    private boolean readBasicInfo(Sheet sheet, int rowNum, int colNum, JSONObject order, boolean flag){
        for (int k = 0; k < colNum; k++) {
            try {
                Cell cell = sheet.getRow(rowNum).getCell(k);

                if (cell.toString().indexOf("订购日期") != -1) {
                    Cell order_data = sheet.getRow(rowNum).getCell(k + 2);

                    switch (order_data.getCellType()){          //获取数值类型
                        case Cell.CELL_TYPE_NUMERIC:
                            short date_type = order_data.getCellStyle().getDataFormat();        //判断日期类型 yyyy-MM-dd-----	14，yyyy年m月d日---	31，yyyy年m月-------57，m月d日  ----------	58，HH:mm-----------	20，h时mm分  -------	32
                            SimpleDateFormat sdf = null;
                            if(date_type == 14 || date_type == 31 || date_type == 57 || date_type == 58){
                                sdf = new SimpleDateFormat("yyyy-MM-dd");
                            }else if(date_type == 20 || date_type == 32){
                                sdf = new SimpleDateFormat("HH:mm");
                            }

                            if (DateUtil.isCellDateFormatted(order_data)) {
                                Date date = DateUtil.getJavaDate(order_data.getNumericCellValue());
                                String value = sdf.format(date);
//                                System.out.println("数值型——订购日期：" + value);
                                order.put("create_time", value);
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            String newDate = null;
                            if(order_data.toString().indexOf('.') != -1){
                                newDate = order_data.toString().replace('.', '-');
                            }else if(order_data.toString().indexOf('-') != -1){
                                newDate = order_data.toString();
                            }
//                            System.out.println("字符型——订购日期：" + newDate);
                            order.put("create_time", newDate);
                            break;
                    }

                } else if (cell.toString().indexOf("客户姓名:") != -1) {
                    Cell client_name = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("客户姓名：" + client_name);
                    order.put("client_name", client_name.toString().trim());
                } else if (cell.toString().indexOf("导师/管家姓名") != -1) {
                    Cell manager_name = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("导师/管家姓名：" + manager_name);
                    order.put("manager_name", manager_name.toString().trim());
                } else if (cell.toString().indexOf("客户单位") != -1) {
                    Cell client_affiliation = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("客户单位：" + client_affiliation);
                    order.put("client_affiliation", client_affiliation.toString().trim());
                } else if (cell.toString().indexOf("送货地址") != -1) {
                    Cell shipping_address = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("送货地址：" + shipping_address);
                    order.put("shipping_address", shipping_address.toString().trim());
                } else if (cell.toString().indexOf("邮编") != -1) {
                    Cell postal_code = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("邮编：" + postal_code);
                    order.put("postal_code", postal_code.toString().trim());
                } else if (cell.toString().indexOf("发票抬头") != -1) {
                    Cell invoice_title = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("发票抬头：" + invoice_title);
                    order.put("invoice_title", invoice_title.toString().trim());
                } else if (cell.toString().indexOf("开票形式") != -1) {
                    Cell form_of_invoice = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("开票形式：" + form_of_invoice);
                    order.put("form_of_invoice", form_of_invoice.toString().trim());
                } else if (cell.toString().indexOf("固定电话") != -1) {
                    Cell telephone = sheet.getRow(rowNum).getCell(k + 2);
                    switch (telephone.getCellType()){          //获取数值类型
                        case Cell.CELL_TYPE_NUMERIC:
                            DecimalFormat df = new DecimalFormat("#");
                            String newPhone = df.format(telephone.getNumericCellValue());
                            if(newPhone.equals("0")){
                                newPhone = "";
                            }
                            order.put("telephone", newPhone);
                            break;
                        case Cell.CELL_TYPE_STRING:
                            order.put("telephone", telephone.toString().trim());
                            break;
                    }
                } else if (cell.toString().indexOf("手机") != -1) {
                    Cell client_phone = sheet.getRow(rowNum).getCell(k + 2);
                    switch (client_phone.getCellType()){          //获取数值类型
                        case Cell.CELL_TYPE_NUMERIC:
                            DecimalFormat df = new DecimalFormat("#");
                            String newPhone = df.format(client_phone.getNumericCellValue());
                            if(newPhone.equals("0")){
                                newPhone = "";
                            }
                            order.put("client_phone", newPhone);
                            break;
                        case Cell.CELL_TYPE_STRING:
                            order.put("client_phone", client_phone.toString().trim());
                            break;
                    }
                } else if (cell.toString().indexOf("email") != -1) {
                    Cell client_email = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("email：" + client_email);
                    order.put("client_email", client_email.toString().trim());
                } else if (cell.toString().indexOf("销售经理") != -1) {          //有问题-------------
                    Cell sales_manager_id = sheet.getRow(rowNum).getCell(k + 2);
                    JSONObject saleManagerName = new JSONObject();
                    saleManagerName.put("username", sales_manager_id.toString().trim());
                    int saleManagerID = orderDao.getSalesManagerID(saleManagerName);
//                    System.out.println("销售经理：" + sales_manager_id);
//                    System.out.println("销售经理ID：" + saleManagerID);
                    order.put("sales_manager_id", saleManagerID);
                } else if (cell.toString().indexOf("订单金额") != -1) {
                    Cell order_price = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("订单金额：" + order_price);
                    order.put("order_price", order_price.toString().trim());
                } else if (cell.toString().indexOf("预付款") != -1) {       //有问题-------------
                    Cell name = sheet.getRow(rowNum).getCell(k);
                    String advance_customer_name = name.toString().split("：")[1];
//                    System.out.println("预付款客户姓名：" + advance_customer_name);
                    order.put("advance_customer_name", advance_customer_name);
                } else if (cell.toString().indexOf("快递公司") != -1) {
                    Cell courier_scervices_company = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("快递公司：" + courier_scervices_company);
                    order.put("courier_scervices_company", courier_scervices_company.toString().trim());
                } else if (cell.toString().indexOf("发货通知方式") != -1) {
                    Cell notification_of_delivery = sheet.getRow(rowNum).getCell(k + 2);
//                    System.out.println("发货通知方式：" + notification_of_delivery);
                    order.put("notification_of_delivery", notification_of_delivery.toString().trim());
                } else if (cell.toString().indexOf("特殊说明") != -1) {
//                    System.out.println("特殊说明");
                    flag = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
        return flag;
    }

    //读取病毒订单信息
    private void readVirusOrderInfo(Sheet sheet, int rowNum,int colNum, JSONArray tableDatas){
        JSONObject temp = new JSONObject();
        temp.put("viruses_type", "");
        temp.put("gene_name_type", "");
        temp.put("carrier_type", "");
        temp.put("sequence_53", "");
        temp.put("order_quantity", "");
        temp.put("div_tubes_number", "");
        temp.put("titer_requirement", "");
        temp.put("special_instructions", "");
        temp.put("item_numbers", "");                                 //数量
        temp.put("item_unit", "");                                    //单位

        boolean jump_pos = true;
        for (int k = 0; k < colNum; k++) {
            try {
                if (sheet.getRow(rowNum).getCell(k).toString() == "" && sheet.getRow(rowNum).getCell(k + 1).toString() == "" && sheet.getRow(rowNum).getCell(k + 2).toString() == "" && sheet.getRow(rowNum).getCell(k + 3).toString() == "" && sheet.getRow(rowNum).getCell(k + 4).toString() == "" && sheet.getRow(rowNum).getCell(k + 5).toString() == "" && sheet.getRow(rowNum).getCell(k + 6).toString() == "" && sheet.getRow(rowNum).getCell(k + 7).toString() == "") {
                    jump_pos = false;
                    break;
                } else {
                    Cell cell = sheet.getRow(rowNum).getCell(k);
                    if (k == 0) {
                        temp.put("viruses_type", cell.toString());
                    } else if (k == 1) {
                        temp.put("gene_name_type", cell.toString());
                    } else if (k == 2) {
                        temp.put("carrier_type", cell.toString());
                    } else if (k == 3) {
                        temp.put("sequence_53", cell.toString());
                    } else if (k == 4) {
                        temp.put("order_quantity", cell.toString());
                    } else if (k == 5) {
                        temp.put("div_tubes_number", cell.toString());
                    } else if (k == 6) {
                        temp.put("titer_requirement", cell.toString());
                    } else if (k == 7) {
                        temp.put("special_instructions", cell.toString());
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
        if (jump_pos) {
            tableDatas.add(temp);
        }
    }

    //读取RNA订单信息
    private void readRNAOrderInfo(Sheet sheet, int rowNum,int colNum, JSONArray tableDatas){
        JSONObject temp = new JSONObject();
        temp.put("catalog_id", "");
        temp.put("gene_name", "");
        temp.put("sense_53", "");
        temp.put("antisense_53", "");
        temp.put("synthetic_spec", "");
        temp.put("div_tubes_number", "");
        temp.put("chemical_modification", "");
        temp.put("flu_labeling", "");
        temp.put("special_modi", "");
        temp.put("special_instructions", "");
        temp.put("item_numbers", "");                                 //数量
        temp.put("item_unit", "");                                    //单位

        boolean jump_pos = true;
        for (int k = 0; k < colNum; k++) {
            try {
                if (sheet.getRow(rowNum).getCell(k).toString() == "" && sheet.getRow(rowNum).getCell(k + 1).toString() == "" && (sheet.getRow(rowNum).getCell(k + 2).toString() == "" || sheet.getRow(rowNum).getCell(k + 2).toString().indexOf("sense") != -1) && (sheet.getRow(rowNum).getCell(k + 3).toString() == ""  || sheet.getRow(rowNum).getCell(k + 3).toString().indexOf("antisense") != -1) && sheet.getRow(rowNum).getCell(k + 4).toString() == "" && sheet.getRow(rowNum).getCell(k + 5).toString() == "" && sheet.getRow(rowNum).getCell(k + 6).toString() == "" && sheet.getRow(rowNum).getCell(k + 7).toString() == "" && sheet.getRow(rowNum).getCell(k + 8).toString() == "" && sheet.getRow(rowNum).getCell(k + 9).toString() == "") {
                    jump_pos = false;
                    break;
                } else {
                    Cell cell = sheet.getRow(rowNum).getCell(k);
                    if (k == 0) {
                        temp.put("catalog_id", cell.toString());
                    } else if (k == 1) {
                        temp.put("gene_name", cell.toString());
                    } else if (k == 2) {
                        temp.put("sense_53", cell.toString());
                    } else if (k == 3) {
                        temp.put("antisense_53", cell.toString());
                    } else if (k == 4) {
                        temp.put("synthetic_spec", cell.toString());
                    } else if (k == 5) {
                        temp.put("div_tubes_number", cell.toString());
                    } else if (k == 6) {
                        temp.put("chemical_modification", cell.toString());
                    } else if (k == 7) {
                        temp.put("flu_labeling", cell.toString());
                    } else if (k == 8) {
                        temp.put("special_modi", cell.toString());
                    } else if (k == 9) {
                        temp.put("special_instructions", cell.toString());
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
        if (jump_pos) {
            tableDatas.add(temp);
        }
    }

    //读取试剂盒&引物探针套装订单信息
    private void readKitOrderInfo(Sheet sheet, int rowNum,int colNum, JSONArray tableDatas){
        JSONObject temp = new JSONObject();
        temp.put("catalog_id", "");
        temp.put("product_name", "");
        temp.put("gene_name", "");
        temp.put("gene_name_type", "");
        temp.put("specifications", "");
        temp.put("special_instructions", "");
        temp.put("item_numbers", "");                                 //数量
        temp.put("item_unit", "");                                    //单位

        boolean jump_pos = true;
        for (int k = 0; k < colNum; k++) {
            try {
                if (sheet.getRow(rowNum).getCell(k).toString() == "" && sheet.getRow(rowNum).getCell(k + 1).toString() == "" && sheet.getRow(rowNum).getCell(k + 3).toString() == "" && sheet.getRow(rowNum).getCell(k + 4).toString() == "" && sheet.getRow(rowNum).getCell(k + 7).toString() == "" && sheet.getRow(rowNum).getCell(k + 9).toString() == "") {
                    jump_pos = false;
                    break;
                } else {
                    Cell cell = sheet.getRow(rowNum).getCell(k);
                    if (k == 0) {
                        temp.put("catalog_id", cell.toString());
                    } else if (k == 1) {
                        temp.put("product_name", cell.toString());
                    } else if (k == 3) {
                        temp.put("gene_name", cell.toString());
                    } else if (k == 4) {
                        temp.put("gene_name_type", cell.toString());
                    } else if (k == 7) {
                        temp.put("specifications", cell.toString());
                    } else if (k == 9) {
                        temp.put("special_instructions", cell.toString());
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
        if (jump_pos) {
            tableDatas.add(temp);
        }
    }

    //读取载体订单信息
    private void readCarrierOrderInfo(Sheet sheet, int rowNum,int colNum, JSONArray tableDatas){
        JSONObject temp = new JSONObject();
        temp.put("catalog_id", "");
        temp.put("product_name", "");
        temp.put("carrier_type", "");
        temp.put("gene_name_type", "");
        temp.put("sequence_53", "");
        temp.put("special_instructions", "");
        temp.put("item_numbers", "");                                 //数量
        temp.put("item_unit", "");                                    //单位

        boolean jump_pos = true;
        for (int k = 0; k < colNum; k++) {
            try {
                if (sheet.getRow(rowNum).getCell(k).toString() == "" && sheet.getRow(rowNum).getCell(k + 1).toString() == "" && sheet.getRow(rowNum).getCell(k + 3).toString() == "" && sheet.getRow(rowNum).getCell(k + 4).toString() == "" && sheet.getRow(rowNum).getCell(k + 6).toString() == "" && sheet.getRow(rowNum).getCell(k + 9).toString() == "") {
                    jump_pos = false;
                    break;
                } else {
                    Cell cell = sheet.getRow(rowNum).getCell(k);
                    if (k == 0) {
                        temp.put("catalog_id", cell.toString());
                    } else if (k == 1) {
                        temp.put("product_name", cell.toString());
                    } else if (k == 3) {
                        temp.put("carrier_type", cell.toString());
                    } else if (k == 4) {
                        temp.put("gene_name_type", cell.toString());
                    } else if (k == 6) {
                        temp.put("sequence_53", cell.toString());
                    } else if (k == 9) {
                        temp.put("special_instructions", cell.toString());
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
        if (jump_pos) {
            tableDatas.add(temp);
        }
    }

    //读取探针合成订单信息
    private void readProbeOrderInfo(Sheet sheet, int rowNum,int colNum, JSONArray tableDatas){
        JSONObject temp = new JSONObject();
        temp.put("catalog_id", "");
        temp.put("product_type", "");
        temp.put("gene_name", "");
        temp.put("dna_sequ_53", "");
        temp.put("base_number", "");
        temp.put("synthetic_spec", "");
        temp.put("div_tubes_number", "");
        temp.put("modifi_5", "");
        temp.put("modifi_3", "");
        temp.put("purification_method", "");
        temp.put("gene_name_type", "");
        temp.put("special_instructions", "");
        temp.put("item_numbers", "");                                 //数量
        temp.put("item_unit", "");                                    //单位

        boolean jump_pos = true;
        for (int k = 0; k < colNum; k++) {
            try {
                if (sheet.getRow(rowNum).getCell(k).toString() == "" && sheet.getRow(rowNum).getCell(k + 1).toString() == "" && sheet.getRow(rowNum).getCell(k + 2).toString() == "" && sheet.getRow(rowNum).getCell(k + 3).toString() == "" && sheet.getRow(rowNum).getCell(k + 4).toString() == "" && sheet.getRow(rowNum).getCell(k + 5).toString() == "" && sheet.getRow(rowNum).getCell(k + 6).toString() == "" && (sheet.getRow(rowNum).getCell(k + 7).toString() == "" || sheet.getRow(rowNum).getCell(k + 7).toString().indexOf("5") != -1) && (sheet.getRow(rowNum).getCell(k + 8).toString() == "" || sheet.getRow(rowNum).getCell(k + 8).toString().indexOf("3") != -1) && sheet.getRow(rowNum).getCell(k + 9).toString() == "" && sheet.getRow(rowNum).getCell(k + 10).toString() == "" && sheet.getRow(rowNum).getCell(k + 11).toString() == "") {
                    jump_pos = false;
                    break;
                } else {
                    Cell cell = sheet.getRow(rowNum).getCell(k);
                    if (k == 0) {
                        temp.put("catalog_id", cell.toString());
                    } else if (k == 1) {
                        temp.put("product_type", cell.toString());
                    } else if (k == 2) {
                        temp.put("gene_name", cell.toString());
                    } else if (k == 3) {
                        temp.put("dna_sequ_53", cell.toString());
                    } else if (k == 4) {
                        temp.put("base_number", cell.toString());
                    } else if (k == 5) {
                        temp.put("synthetic_spec", cell.toString());
                    } else if (k == 6) {
                        temp.put("div_tubes_number", cell.toString());
                    } else if (k == 7) {
                        temp.put("modifi_5", cell.toString());
                    } else if (k == 8) {
                        temp.put("modifi_3", cell.toString());
                    }else if (k == 9) {
                        temp.put("purification_method", cell.toString());
                    }else if (k == 10) {
                        temp.put("gene_name_type", cell.toString());
                    } else if (k == 11) {
                        temp.put("special_instructions", cell.toString());
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
        if (jump_pos) {
            tableDatas.add(temp);
        }
    }

    //读取FISH试剂盒&其他订单信息
    private void readFISHOrderInfo(Sheet sheet, int rowNum,int colNum, JSONArray tableDatas){
        JSONObject temp = new JSONObject();
        temp.put("catalog_id", "");
        temp.put("sample_type", "");
        temp.put("product_name", "");
        temp.put("gene_name_type", "");
        temp.put("probe_sequence_53", "");
        temp.put("numbers", "");
        temp.put("company", "");
        temp.put("modifi_5", "");
        temp.put("modifi_3", "");
        temp.put("purification_method", "");
        temp.put("special_instructions", "");
        temp.put("item_numbers", "");                                 //数量
        temp.put("item_unit", "");                                    //单位

        boolean jump_pos = true;
        for (int k = 0; k < colNum; k++) {
            try {
                if (sheet.getRow(rowNum).getCell(k).toString() == "" && sheet.getRow(rowNum).getCell(k + 1).toString() == "" && sheet.getRow(rowNum).getCell(k + 2).toString() == "" && sheet.getRow(rowNum).getCell(k + 3).toString() == "" && sheet.getRow(rowNum).getCell(k + 4).toString() == "" && (sheet.getRow(rowNum).getCell(k + 5).toString() == "" || sheet.getRow(rowNum).getCell(k + 5).toString().indexOf("数量") != -1) && (sheet.getRow(rowNum).getCell(k + 6).toString() == "" || sheet.getRow(rowNum).getCell(k + 6).toString().indexOf("单位") != -1) && (sheet.getRow(rowNum).getCell(k + 7).toString() == "" || sheet.getRow(rowNum).getCell(k + 7).toString().indexOf("5") != -1) && (sheet.getRow(rowNum).getCell(k + 8).toString() == "" || sheet.getRow(rowNum).getCell(k + 8).toString().indexOf("3") != -1) && sheet.getRow(rowNum).getCell(k + 9).toString() == "" && sheet.getRow(rowNum).getCell(k + 10).toString() == "") {
                    jump_pos = false;
                    break;
                } else {
                    Cell cell = sheet.getRow(rowNum).getCell(k);
                    if (k == 0) {
                        temp.put("catalog_id", cell.toString());
                    } else if (k == 1) {
                        temp.put("sample_type", cell.toString());
                    } else if (k == 2) {
                        temp.put("product_name", cell.toString());
                    } else if (k == 3) {
                        temp.put("gene_name_type", cell.toString());
                    } else if (k == 4) {
                        temp.put("probe_sequence_53", cell.toString());
                    } else if (k == 5) {
                        temp.put("numbers", cell.toString());
                    } else if (k == 6) {
                        temp.put("company", cell.toString());
                    } else if (k == 7) {
                        temp.put("modifi_5", cell.toString());
                    } else if (k == 8) {
                        temp.put("modifi_3", cell.toString());
                    }else if (k == 9) {
                        temp.put("purification_method", cell.toString());
                    } else if (k == 10) {
                        temp.put("special_instructions", cell.toString());
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
        if (jump_pos) {
            tableDatas.add(temp);
        }
    }

    //读取全基因订单信息
    private void readGeneOrderInfo(Sheet sheet, int rowNum,int colNum, JSONArray tableDatas){
        JSONObject temp = new JSONObject();
        temp.put("carrier_type", "");
        temp.put("gene_name_type", "");
        temp.put("sequence_53", "");
        temp.put("gene_length", "");
        temp.put("carrier_vector", "");
        temp.put("cloning_site", "");
        temp.put("order_quantity", "");
        temp.put("special_instructions", "");
        temp.put("item_numbers", "");                                 //数量
        temp.put("item_unit", "");                                    //单位

        boolean jump_pos = true;
        for (int k = 0; k < colNum; k++) {
            try {
                if (sheet.getRow(rowNum).getCell(k).toString() == "" && sheet.getRow(rowNum).getCell(k + 1).toString() == "" && sheet.getRow(rowNum).getCell(k + 2).toString() == "" && sheet.getRow(rowNum).getCell(k + 4).toString() == "" && sheet.getRow(rowNum).getCell(k + 5).toString() == "" && sheet.getRow(rowNum).getCell(k + 7).toString() == "" && sheet.getRow(rowNum).getCell(k + 8).toString() == "" && sheet.getRow(rowNum).getCell(k + 9).toString() == "") {
                    jump_pos = false;
                    break;
                } else {
                    Cell cell = sheet.getRow(rowNum).getCell(k);
                    if (k == 0) {
                        temp.put("carrier_type", cell.toString());
                    } else if (k == 1) {
                        temp.put("gene_name_type", cell.toString());
                    } else if (k == 2) {
                        temp.put("sequence_53", cell.toString());
                    } else if (k == 4) {
                        temp.put("gene_length", cell.toString());
                    } else if (k == 5) {
                        temp.put("carrier_vector", cell.toString());
                    } else if (k == 7) {
                        temp.put("cloning_site", cell.toString());
                    } else if (k == 8) {
                        temp.put("order_quantity", cell.toString());
                    } else if (k == 9) {
                        temp.put("special_instructions", cell.toString());
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
        if (jump_pos) {
            tableDatas.add(temp);
        }
    }


//    @RequestMapping(value = "/importOrder", method = RequestMethod.POST)
//    public String uploadFile(HttpServletRequest request, HttpServletRequest response, HttpSession session) {
//        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
//        MultipartFile multipartFile = multipartRequest.getFile("file");//file是form-data中二进制字段对应的name
//        String[] multipartFile1 = multipartRequest.getParameterValues("order_type");//file是form-data中二进制字段对应的name
//        System.out.println(multipartFile.getOriginalFilename());
//        System.out.println("文件为："+multipartFile1[0]);
//        return " ";
//    }


    @RequiresPermissions("order:list")
    @PostMapping("/importOrder")
    public JSONObject importExcel(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "order_type", required = true) String order_type, @RequestParam(value = "userId", required = true) String userId) throws IOException {
//        System.out.println("文件类型为："+ order_type[0]);
//        System.out.println("文件类型为："+ order_type[1]);
//        System.out.println("文件名称为："+file.getOriginalFilename());

        int numberOfSheets = 0;
        Workbook workbook = null;          // 初始化
        if(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());        // 解析xls格式的excel
            numberOfSheets = workbook.getNumberOfSheets();             // 获取一共有多少sheet,然后遍历
        }else if(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).equals("xlsx")){
            workbook = new XSSFWorkbook(file.getInputStream());       // 解析xlsx格式的excel
            numberOfSheets = workbook.getNumberOfSheets();            // 获取一共有多少sheet,然后遍历
        }

//        for (int k = 0; k < order_type.length; k++) {
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);                                   //读每张表
//                System.out.println("第" + k + "个");
//                System.out.println("第" + order_type[k] + "类型");
//                System.out.println("#######tab名#######：" + sheet.getSheetName());

                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                boolean flag = false;
                JSONObject order = new JSONObject();
                JSONArray tableDatas = new JSONArray();                          // 存储订单信息
                order.put("order_status", "待审核");                            //订单状态
                order.put("order_id", "");                                      //子订单编号
                order.put("mord_id", "");                                       //父订单编号
                order.put("imported_staff", userId);                           //导入员工ID
                order.put("imported_time", df.format(day));                    //导入时间
                order.put("is_import", 1);                                      //是否导入

                //判断读取哪个sheet，默认最后一个sheet为“填表说明”
                if (sheet.getSheetName().equals("填表说明")) {
//                    System.out.println("sheet读取完毕！！退出整个文件！！");
                    break;
                } else if ("病毒".equals(order_type) && (sheet.getSheetName().indexOf("病毒") != -1)) {   //读取指定名字的sheet
//                    System.out.println("读取sheet：" + sheet.getSheetName());
                    order.put("order_type", "病毒");                                                  //订单类型
                    this.readController(sheet, order, tableDatas, flag, sheet.getSheetName());        //基本读取流程
                    orderService.addVirus(order);
                } else if ("RNA oligo".equals(order_type) && (sheet.getSheetName().indexOf("RNA") != -1)) {
//                    System.out.println("读取sheet：" + sheet.getSheetName());
                    order.put("order_type", "RNA oligo");                                             //订单类型
                    this.readController(sheet, order, tableDatas, flag, sheet.getSheetName());        //基本读取流程
                    orderService.addOligo(order);
                } else if ("试剂盒&引物探针套装".equals(order_type) && (sheet.getSheetName().indexOf("试剂盒") != -1)) {
//                    System.out.println("读取sheet：" + sheet.getSheetName());
                    order.put("order_type", "试剂盒&引物探针套装");                                   //订单类型
                    this.readController(sheet, order, tableDatas, flag, sheet.getSheetName());        //基本读取流程
                    orderService.addKitProbe(order);
                } else if ("载体".equals(order_type) && (sheet.getSheetName().indexOf("载体") != -1)) {
//                    System.out.println("读取sheet：" + sheet.getSheetName());
                    order.put("order_type", "载体");                                                  //订单类型
                    this.readController(sheet, order, tableDatas, flag, sheet.getSheetName());        //基本读取流程
                    orderService.addCarrier(order);
                } else if ("探针合成".equals(order_type) && (sheet.getSheetName().indexOf("探针合成") != -1)) {
//                    System.out.println("读取sheet：" + sheet.getSheetName());
                    order.put("order_type", "探针合成");                                                  //订单类型
                    this.readController(sheet, order, tableDatas, flag, sheet.getSheetName());        //基本读取流程
                    orderService.addDNAProbe(order);
                } else if ("FISH试剂盒&其他".equals(order_type) && (sheet.getSheetName().indexOf("FISH") != -1)) {
//                    System.out.println("读取sheet：" + sheet.getSheetName());
                    order.put("order_type", "FISH试剂盒&其他");                                      //订单类型
                    this.readController(sheet, order, tableDatas, flag, sheet.getSheetName());        //基本读取流程
                    orderService.addFISH(order);
                } else if ("全基因".equals(order_type) && (sheet.getSheetName().indexOf("全基因") != -1)) {
//                    System.out.println("读取sheet：" + sheet.getSheetName());
                    order.put("order_type", "全基因");                                                //订单类型
                    this.readController(sheet, order, tableDatas, flag, sheet.getSheetName());        //基本读取流程
                    orderService.addWholeGene(order);
                }

//                System.out.println("order: " + order);
//                System.out.println("================================================");
            }
//        }
        return CommonUtil.successJson();
    }
}
