package org.xahj.homework;
import java.sql.*;
import java.util.*;

public class LoginRegister {
    public static void main(String[] args)throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean flag=true;
        while(flag){
            System.out.println("1.注册  2.登录  3.退出");
            int choice = scan.nextInt();
            switch (choice){
                case 1:
                    System.out.print("注册用户名：");
                    String regname = scan.next();
                    System.out.print("注册密码：");
                    String regpwd = scan.next();
                    if(reg(regname,regpwd)){
                        System.out.println("恭喜，注册成功！");
                    }else{
                        System.out.println("注册失败！");
                    }
                    break;
                case 2:
                    System.out.print("登录名：");
                    String logname = scan.next();
                    System.out.print("登录密码：");
                    String logpwd = scan.next();
                    String info=login(logname,logpwd);
                    System.out.println(info);
                    break;
                case 3:
                    flag=false;
                    break;
            }
        }
    }

    public static boolean reg(String regname,String regpwd)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
        String sql="insert into accounts(username,password)values(?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,regname);
        ps.setString(2,regpwd);
        int rows=ps.executeUpdate();
        ps.close();
        conn.close();
        if(rows>0){
            return true;
        }else{
            return false;
        }
    }

    public static String login(String logname,String logpwd)throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
        String sql1="select username from accounts where username=?";
        PreparedStatement ps = conn.prepareStatement(sql1);
        ps.setString(1,logname);
        ResultSet rs=ps.executeQuery();
        if(!rs.next()){
            return "登录用户名错误！";
        }
        String sql2="select username from accounts where username=? and password=?";
        PreparedStatement ps2 = conn.prepareStatement(sql2);
        ps2.setString(1,logname);
        ps2.setString(2,logpwd);
        ResultSet rs2=ps2.executeQuery();
        if(!rs2.next()){
            return "登录密码错误！";
        }
        return "恭喜，登录成功！";
    }

}
