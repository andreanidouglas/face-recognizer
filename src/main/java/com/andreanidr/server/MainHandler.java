package com.andreanidr.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class MainHandler extends AbstractHandler {

	public void handle(String arg0, Request arg1, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp");
		arg1.setAttribute("org.eclipse.multipartConfig", multipartConfigElement);
		System.out.println(arg0);

		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			handlePostData(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write(returnIndex(arg0).toCharArray());

		arg1.setHandled(true);

	}

	private String returnIndex(String path) throws IOException {
		String currentLine;
		String fileContext = new String("");

		if (path.equals("/")) {
			BufferedReader reader = new BufferedReader(new FileReader("./resources/html/index.html"));

			while ((currentLine = reader.readLine()) != null) {
				fileContext += currentLine;

			}

			reader.close();
			return fileContext;
		} else {
			BufferedReader reader = new BufferedReader(new FileReader("./resources/html/" + path + ".html"));
			while ((currentLine = reader.readLine()) != null) {
				fileContext += currentLine;

			}

			reader.close();
			return fileContext;
		}

	}

	private void handlePostData(HttpServletRequest request) throws IOException, FileUploadException {

		boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
		if (isMultiPart) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			File repository = new File("./resources/temp");
			factory.setRepository(repository);

			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);

			Iterator<FileItem> iterator = items.iterator();

			while (iterator.hasNext()) {
				FileItem item = iterator.next();
				if (!item.isFormField()) {
					String fileName = item.getName();
					File uploadedFile = new File("./resources/images/" + fileName);
					try {
						item.write(uploadedFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}

	}

}
