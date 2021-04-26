package com.cxh.project.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cxh.common.utils.commonUtils;
import com.cxh.project.core.TestClient;
import com.cxh.project.web.viewmodel.TestViewMode;

@RestController
@RequestMapping("/controller")
public class TestController {
	public static final Logger log = LogManager.getLogger(TestController.class);
	@Autowired
	TestClient service;
	
	/**
	 * 邮箱发送依赖
	 */
	@Autowired
    private JavaMailSender javaMailSender;

	// 查询列表
	@RequestMapping("/initPage")
	public TestViewMode initPage(String jsonParm) {
		TestViewMode vm = new TestViewMode();
		Map map = (Map) JSON.parse(jsonParm);
		System.out.println(commonUtils.currenYear);
		List<Map<String, Object>> result = service.getResult(map);
		int total = result.size();
		vm.setRows(result);
		vm.setTotal(total);
		vm.setSuccessful(true);
		return vm;
	}
	
	
	//发送普通文本邮件
    @RequestMapping("/sendMail")
    public String sendMail(Model model) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("542013089@qq.com"); //发送者
        message.setTo("1538956448@qq.com");  //接受者
//        message.setCc("654321***@163.com"); //抄送，填发送者的邮箱即可
        message.setSubject("你好");	//主题
        message.setText("你好陈泽希！");	//内容
        try {
            javaMailSender.send(message);
            System.out.println("邮件已经发送");
        } catch (Exception e) {
            System.out.println("邮件时发生异常！"+e.toString());
        }
        model.addAttribute("msg", "");
        return "邮件已经发送";
    }
}
