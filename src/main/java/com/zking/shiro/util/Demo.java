package com.zking.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class Demo {
    public static void main(String[] args) {
        //1.创建IniDefaultSecurityManager读取加载ini配置文件
        IniSecurityManagerFactory factory=
                new IniSecurityManagerFactory("classpath:shiro-role.ini");
        //2.创建securityManager安全管理器
        SecurityManager securityManager = factory.getInstance();
        //3.将SecurityManger安全管理器交由securityUtils管理
        SecurityUtils.setSecurityManager(securityManager);
        //4.创建Subject
        // 主体，抽象的，在这里我们使用账号密码形式
        Subject subject = SecurityUtils.getSubject();
        //5.创建身份校验的token令牌
        //token：身份和凭据
        //IncorrectcredentialsException:密码错误
        //UnknowAccountExeption:账号错误
        UsernamePasswordToken  token=
                new UsernamePasswordToken("zs","123");
        //6.进行身份验证
        try {
            subject.login(token);
            System.out.println("身份验证通过");
        }catch (Exception e){
            e.printStackTrace();
        }

        if (subject.hasRole("role1")) {
            System.out.println("角色验证通过");
        } else {
            System.out.println("角色验证失败");
        }

        if (subject.isPermitted("user:select1")) {
            System.out.println("权限验证通过");
        } else {
            System.out.println("权限验证失败");
        }
        //7.推出
        subject.logout();
    }
}
