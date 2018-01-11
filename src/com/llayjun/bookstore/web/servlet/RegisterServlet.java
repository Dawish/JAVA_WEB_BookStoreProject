package com.llayjun.bookstore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.omg.CORBA.UserException;

import com.llayjun.bookstore.domain.Users;
import com.llayjun.bookstore.service.UserService;
import com.mchange.v2.codegen.bean.BeangenUtils;

public class RegisterServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ������֤��
		String _userCode = req.getParameter("ckcode");
		String _realCode = (String) req.getSession().getAttribute("checkcode_session");
		// ���������֤�벻һ�£������ע�����
		if (!_realCode.equals(_userCode)) {
			req.setAttribute("ckcode_msg", "��֤�����");
			req.getRequestDispatcher("/register.jsp").forward(req, resp);
			return;
		}

		// ��ȡ������
		Users _users = new Users();
		_users.setActiveCode(UUID.randomUUID().toString());// �ֶ����ü�����
		try {
			BeanUtils.populate(_users, req.getParameterMap());

			// ����ҵ���߼�
			UserService _uUserService = new UserService();
			_uUserService.regist(_users);

			// �ַ�ת��
			req.getSession().setAttribute("user", _users);// ���û���Ϣ��װ��session��
			req.getRequestDispatcher("/registersuccess.jsp").forward(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("user_msg", e.getMessage());
			req.getRequestDispatcher("/register.jsp").forward(req, resp);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
