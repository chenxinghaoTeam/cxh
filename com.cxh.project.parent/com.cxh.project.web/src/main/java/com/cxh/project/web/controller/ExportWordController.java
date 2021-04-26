package com.cxh.project.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.Configuration;
import freemarker.template.Template;

@RestController
@RequestMapping("/ExportWord")
public class ExportWordController {
	public static final Logger log = LogManager.getLogger(ExportWordController.class);

	/**
	 * 导出Word并赋值
	 * 依赖freemarker.jar
	 * @param jsonParm
	 * @return
	 */
	@RequestMapping("/exportMillCerttificateWord")
	private  void exportMillCerttificateWord(HttpServletRequest requesrt, HttpServletResponse response, String cs)
			throws IOException {
		String path = ExportWordController.class.getClassLoader().getResource("").getPath();
		Configuration cf = new Configuration(Configuration.getVersion());
		// 设置模板所在目录
		cf.setDirectoryForTemplateLoading(new File(path));
		cf.setDefaultEncoding("utf-8");
		// 加载模板
		Template template = cf.getTemplate("tt.tfl");
		File file = null;
		InputStream fin = null;
		ServletOutputStream out = null;
		Map<String, Object> map = new HashMap<>();
		map.put("name", "陈行昊");
		map.put("xb", "男");
		file = createDoc(map, template);
		fin = new FileInputStream(file);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/msword");
		response.setHeader("Content-Disposition",
				"attachment;filename=".concat(String.valueOf(URLEncoder.encode("个人简历.doc", "utf-8"))));
		out = response.getOutputStream();
		byte[] buffer = new byte[512];
		int bytesToRead = -1;
		while ((bytesToRead = fin.read(buffer)) != -1) {
			out.write(buffer, 0, bytesToRead);
		}
		fin.close();
		out.close();
		file.delete();
	}

	private static File createDoc(Map<?, ?> dataMap, Template template) {
		String name = "tt.doc";
		File f = new File(name);
		Template t = template;
		try {
			Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
			t.process(dataMap, w);
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e); 
		}
		return f;
	}
}
