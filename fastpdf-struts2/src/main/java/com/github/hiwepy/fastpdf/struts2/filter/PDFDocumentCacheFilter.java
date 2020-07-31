package com.github.hiwepy.fastpdf.struts2.filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jeefw.fastpdf.core.context.ItextContext;
import com.jeefw.fastpdf.core.document.elements.ItextXMLElement;
import com.jeefw.fastweb.servlet.OncePerRequestFilter;
import com.jeefw.fastweb.servlet.http.HttpServletCacheResponseWrapper;
import com.opensymphony.xwork2.util.ValueStack;

public class PDFDocumentCacheFilter extends OncePerRequestFilter {

	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterchain) throws ServletException, IOException {
		
		ServletContext sc = request.getSession().getServletContext();
		// 根据ID得到要导出文档的相关信息
		String documentID = request.getParameter("documentID");
		ItextXMLElement element = ItextContext.getElement(documentID);
		if ("html".equalsIgnoreCase(element.attr("model"))) {
			ValueStack stack = ServletActionContext.getValueStack(request);
			String uuid = UUID.randomUUID().toString();
			stack.set("uuid", uuid);
			stack.set("documentID", documentID);
			File tempDir = (File) sc
					.getAttribute("javax.servlet.context.tempdir");
			// get possible cache
			String temp = tempDir.getAbsolutePath();
			File file = new File(temp + File.separator + uuid + ".html");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				HttpServletCacheResponseWrapper wrappedResponse = new HttpServletCacheResponseWrapper(response, baos);
				filterchain.doFilter(request, wrappedResponse);
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(baos.toByteArray());
				fos.flush();
				fos.close();
			} catch (ServletException e) {
				if (!file.exists()) {
					throw new ServletException(e);
				}
			} catch (IOException e) {
				if (!file.exists()) {
					throw e;
				}
			}
		} else {
			filterchain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}
}
