<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import = "scheduleAPP.scheduleAPPServer" %>
<%@ page import = "scheduleAPP.Schedule" %>
<%
   request.setCharacterEncoding("UTF-8");
   String schedule_id = request.getParameter("sche_id");
   String id = request.getParameter("id");
   String schedule_name = request.getParameter("sche_name");
   String type = request.getParameter("type");
   //싱글톤 방식으로 자바 클래스를 불러옵니다.
   scheduleAPPServer connectDB = scheduleAPPServer.getInstance();
   
  
  
%>