package junjun.chapter6;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/html;charset=gb2312");
		String name = req.getParameter("user");
		String password = req.getParameter("password");
		
		if (name!=null && password!=null 
			&& name.equalsIgnoreCase("zhangsan") && password.equals("1234"))
		{
			//sendRedirect ���û��������ض��� success.jsp��
			//�÷���ͨ���޸�HTTPЭ���HEADER����(����״̬����302,��������������·�������),��������´��ض���ָ��ģ�
			//�����������location��ָ����URL�������ʹ�������ʾ�ض�����ҳ������
			resp.sendRedirect("success.jsp"); 
		}
		else
		{
			resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "������æ�����Ժ��½");
		}
		
		resp.flushBuffer();		
	}
}
