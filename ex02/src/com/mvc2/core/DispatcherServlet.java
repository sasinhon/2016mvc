package com.mvc2.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc2.controller.AddController;
import com.mvc2.controller.CMDimp;
import com.mvc2.controller.IndexController;
import com.mvc2.controller.ListController;
import com.mvc2.controller.OneController;
import com.mvc2.model.GuestDao;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
	private static GuestDao dao = new GuestDao(); 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doDispatcher(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doDispatcher(request, response);		
	}
	protected void doDispatcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String srvPath = request.getServletPath();
		System.out.println(srvPath);
		
		CMDimp imp = null;
		
		// ��ü ����(Spring - HandlerMapping)
		if(srvPath.equals("/")){
			imp = new IndexController();
		}else if(srvPath.equals("/list.do")){
			imp = new ListController(dao);
		}else if(srvPath.equals("/add.do")){
			imp = new AddController(dao);
		}else if(srvPath.equals("/One.do")){
			imp = new OneController(dao);
		}
		
		// controller���� ����(Spring - Adapter)
		String url = imp.execute(request, response);
		
		// view�� ����(Spring - ViewResolver)
		String prefix="/WEB-INF/page/";
		String suffix=".jsp";
		url = prefix+url+suffix;
		request.getRequestDispatcher(url).forward(request, response);
	}
	
}












