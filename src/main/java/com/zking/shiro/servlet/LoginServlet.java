package com.zking.shiro.servlet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet",urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //获取登录信息
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //创建用户账号密码token令牌
        UsernamePasswordToken token=
                new UsernamePasswordToken(username,password);
        //进行登录身份验证
        try {
            subject.login(token);
            req.getSession().setAttribute("username",username);
            resp.sendRedirect(req.getContextPath()+"/main.jsp");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            req.getSession().setAttribute("message",e.getMessage());
            resp.sendRedirect(req.getContextPath()+"/login.jsp");
        }
    }
}
