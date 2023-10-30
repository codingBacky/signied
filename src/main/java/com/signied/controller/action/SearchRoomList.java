package com.signied.controller.action;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.signied.dao.SigniedSearchDAO;
import com.signied.dto.RoomVO;

public class SearchRoomList implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception {

		SigniedSearchDAO sDao = SigniedSearchDAO.getInstance();

		// client가 원하는 날짜 -> header.jsp에서 넘어옴
		String date = request.getParameter("input-id");

		// client가 원하는 인원수 -> header.jsp에서 넘어옴
		int totalAmount = Integer.parseInt(request.getParameter("adultAmount"))
				+ Integer.parseInt(request.getParameter("childAmount"));

		List<RoomVO> roomList = sDao.searchRoom(date, totalAmount);
		request.setAttribute("roomList", roomList);

		request.setAttribute("checkIn", request.getParameter("checkIn"));
		request.setAttribute("checkOut", request.getParameter("checkOut"));
		request.setAttribute("bak", request.getParameter("bak"));
		request.setAttribute("adult", request.getParameter("adultCount"));
		request.setAttribute("child", request.getParameter("childCount"));

		RequestDispatcher dis = request.getRequestDispatcher("roomList.jsp");
		dis.forward(request, response);
	}

}
