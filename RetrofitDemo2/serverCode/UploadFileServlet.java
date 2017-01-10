package cn.itcast.tackout.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.tackout.bean.Response;

import com.google.gson.Gson;

public class UploadFileServlet extends HttpServlet {


	public UploadFileServlet() {
		super();
	}

	

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//进行处理:
		// 设置response的编码格式
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");

        System.out.println("UploadFileServlet doPost ......");
		// 创建文件项目工厂对象
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 设置文件上传路径
		// String upload = this.getServletContext().getRealPath("/");
		String upload = "D:/test/";

		// 获取系统默认的临时文件保存路径，该路径为Tomcat根目录下的temp文件夹
//		String temp = System.getProperty("Java.io.tmpdir");
	
		String temp="D:/test/temp";
		// 设置缓冲区大小为 5M
		factory.setSizeThreshold(1024 * 1024 * 5);
		// 设置临时文件夹为temp
		factory.setRepository(new File(temp));
		// 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		   System.out.println("UploadFileServlet doPost ......22222");

		// 解析结果放在List中
		try {
				ArrayList<FileItem> list = (ArrayList<FileItem>)servletFileUpload.parseRequest(request);
				System.out.println("UploadFileServlet doPost ......33333");
				for (FileItem item : list) {
					String name = item.getFieldName();
					InputStream is = item.getInputStream();
		
		
					System.out.println("the current name is " + name);
		
		
					if (name.contains("aFile")) {
						try {
							inputStream2File(is, upload + "\\" + /*
							* System.
							* currentTimeMillis
							* () +
							*/item.getName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						String key = item.getName();
						String value = item.getString();
						System.out.println(key + "---" + value);
					}
				}


				// out.write("success");
			    Gson gson = new Gson();
				Response data = new Response();
				data.setCode("200");
				data.setData("upload files success!...");
				String gsonStr = gson.toJson(data);
		
//		        String gsonStr="{\\\"code\\\":\\\"200\\\";\\\"data\\\":\\\"suceess upload fiels\\\"}";
				System.out.println("响应数据：：" + gsonStr);
				response.getWriter().print(gsonStr);
		} catch (FileUploadException e) {
			e.printStackTrace();
			System.out.println("failure");
		}
		
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}

	
	// 流转化成字符串
	public static String inputStream2String(InputStream is) throws IOException {
	     ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     	int i = -1;
	     	while ((i = is.read()) != -1) {
	     		baos.write(i);
	     	}
	     	return baos.toString();
	}


	// 流转化成文件
	public static void inputStream2File(InputStream is, String savePath)
															throws Exception {
		System.out.println("the file path is  :" + savePath);
		File file = new File(savePath);
		InputStream inputSteam = is;
		BufferedInputStream fis = new BufferedInputStream(inputSteam);
		FileOutputStream fos = new FileOutputStream(file);
		int f;
		while ((f = fis.read()) != -1) {
		fos.write(f);
		}
		fos.flush();
		fos.close();
		fis.close();
		inputSteam.close();


	}
}
