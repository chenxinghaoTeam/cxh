package com.cxh.project.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.cxh.project.core.UploadClient;
import com.cxh.project.web.viewmodel.FileModel;

/**
 * 文件上传
 * 
 * @author chenxinghao 2021年7月14日-上午9:44:27
 */
@RestController
public class uploadController {
	public static final Logger log = LogManager.getLogger(uploadController.class);

	@Autowired
	UploadClient client;

	/**
	 * 查询文件信息
	 * 
	 * @return
	 */
	@RequestMapping("/getAllFile")
	public List<Map<String, Object>> getAllFile(String jsonParm) {
		Map map = (Map) JSON.parse(jsonParm);
		List<Map<String, Object>> result = client.getFileResult(map);
		return result;
	}

	@RequestMapping("/deleteFile")
	public FileModel deleteFile(String jsonParm) {
		FileModel vm = new FileModel();
		Map map = (Map) JSON.parse(jsonParm);
		int result = client.deleteFile(map);
		if (result == 1) {
			String path = System.getProperty("user.dir") + "/src//main/resources/file//";
			File file = new File(path + map.get("filename"));
			if (file.exists()) {
				file.delete();
				vm.setSuccessful(true);
			} else {
				vm.setSuccessful(false);
			}
		}
		return vm;
	}

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param fileImage
	 * @param title
	 * @return
	 */
	@RequestMapping("/uploadFile")
	public FileModel uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileImage") MultipartFile fileImage, @RequestParam("title") String title,
			HttpServletRequest request) {
		FileModel vm = new FileModel();
		if (file.isEmpty()) {
			vm.setSuccessful(true);
			vm.setText("上传失败，请选择文件!");
			vm.setStatus("error");
			return vm;
		}
		try {
			uploadFileService(file, fileImage, title, vm);
		} catch (Exception e) {
			e.printStackTrace();
			vm.setSuccessful(true);
			vm.setText("文件上传失败");
			vm.setStatus("error");
		}
		return vm;
	}

	public FileModel uploadFileService(MultipartFile file, MultipartFile image, String title, FileModel vm)
			throws Exception {
		File imagePath; // 封面图片存放地址
		File fileRealPath; // 文件存放地址
		String path = System.getProperty("user.dir"); // 获取项目相对路径
		fileRealPath = new File(path + "/src//main/resources/file");
		imagePath = new File(path + "/src//main/resources/images");
		// 判断文件夹是否存在
		if (!fileRealPath.exists()) {
			// 不存在，创建
			fileRealPath.mkdirs();
		}
		if (!imagePath.exists()) {
			// 不存在，创建
			imagePath.mkdirs();
		}
		// 获取文件名称
		String fileName = file.getOriginalFilename();
		String imageName = image.getOriginalFilename();
		// 创建文件存放地址
		File resultPath = new File(fileRealPath + "/" + fileName);
		if (resultPath.exists()) {
			vm.setSuccessful(true);
			vm.setText("文件已经存在！");
			vm.setStatus("error");
			return vm;
		}
		// 创建图片存放地址
		File imageResultPath = new File(imagePath + "/" + imageName);
		if (imageResultPath.exists()) {
			vm.setSuccessful(true);
			vm.setText("图片已经存在！");
			vm.setStatus("error");
			return vm;
		}
		file.transferTo(resultPath);
		image.transferTo(imageResultPath);
		Map<String, Object> map = new HashMap<>();
		map.put("title", title);
		map.put("imageName", imageName);
		map.put("fileName", fileName);
		// 文件信息存入库中
		client.insertFile(map);
		System.out.println("absolutePath:" + fileRealPath.getCanonicalPath());
		System.out.println("resultPath:" + resultPath.getCanonicalPath());
		System.out.println("imageResultPath:" + imageResultPath.getCanonicalPath());
		vm.setSuccessful(true);
		vm.setText("文件上传成功！");
		vm.setStatus("success");
		return vm;
	}


	/***
	 * 文件下载
	 * 
	 * @param filename
	 *            需要下载文件的绝对路径
	 * @param res
	 * @throws IOException
	 */

	@RequestMapping("/download")
	public static void download(String filename, HttpServletResponse res) throws IOException {
		InputStream inputStream = null;
		OutputStream os = null;
		try {
			// File file = new
			// File("C:\\Users\\ASUS\\git\\cxh\\com.cxh.project.parent\\com.cxh.project.web\\src\\main\\resources\\file\\"+filename);
			String path = System.getProperty("user.dir") + "/src//main/resources/file//";
			File file = new File(path + filename);
			String name = file.getName();
			inputStream = new FileInputStream(file);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			inputStream.close();
			byte[] data = outStream.toByteArray();
			res.reset();
			res.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
			res.setContentType("application/octet-stream");
			os = res.getOutputStream();
			os.write(data);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (os != null) {
				os.close();
			}
		}
	}

}
