<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="scheduleAPP.scheduleAPPServer"%>
<%
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	String name = request.getParameter("name");
	String type = request.getParameter("type");
	String date = request.getParameter("date");
	String schedule = request.getParameter("schedule");
	String memo = request.getParameter("memo");
	String old = request.getParameter("old");
	String sche_name = request.getParameter("sche_name");
	String sche_id = request.getParameter("sche_id");
	String location = request.getParameter("location");
	String sche_date = request.getParameter("sche_date");
	String old_sche_name = request.getParameter("old_sche_name");
	String new_sche_name = request.getParameter("new_sche_name");
	//싱글톤 방식으로 자바 클래스를 불러옵니다.
	scheduleAPPServer connectDB = scheduleAPPServer.getInstance();
	if (type.equals("login")) {
		String returns = connectDB.logindb(id, pwd);
		out.print(returns);
	} else if (type.equals("join")) {
		String returns = connectDB.joindb(id, pwd, name);
		out.print(returns);
	} else if (type.equals("calendar_main")) {
		String returns = connectDB.calendardb(id, date, schedule, memo);
		out.print(returns);
	} else if (type.equals("calendar_add")) {
		String returns = connectDB.addCalendar(id, date, schedule, memo);
		out.print(returns);
	} else if (type.equals("calendar_edit")) {
		String returns = connectDB.editCalendar(id, date, schedule, memo, old);
		out.print(returns);
	} else if (type.equals("loadAllSche")) {
		String returns = connectDB.loadAllSchedule();
		out.print(returns);
	} else if (type.equals("loadSche")) {
		String returns = connectDB.loadSchedule(sche_id);
		out.print(returns);
	} else if (type.equals("setDate")) {
		String returns = connectDB.setDate(sche_id, sche_date);
		out.print(returns);
	} else if (type.equals("setLocation")) {
		String returns = connectDB.setLocation(sche_id, location);
		out.print(returns);
	} else if (type.equals("modiSche")) {
		String returns = connectDB.modiScheduleName(sche_id, new_sche_name);
		out.print(returns);
	} else if (type.equals("delSche")) {
		String returns = connectDB.deleteSchedule(sche_id);
		out.print(returns);
	} else if (type.equals("addSche")) {
		String returns = connectDB.addSchedule(id, sche_name);
		out.print(returns);
	} 
%>