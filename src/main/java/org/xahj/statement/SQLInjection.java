package org.xahj.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/*
*     create table accounts(
        id    int   auto_increment primary key,
        username   varchar(10)  unique not null,
        password   varchar(10) not null,
        balance     double
);
insert into accounts(username,password,balance)values('tom','tom123',5000),('alice','alice456',100);
*
* */

public class SQLInjection {
    public static void main(String[] args)throws Exception {
        queryBalance("abc","xyz' or '1=1");
    }

    public static void queryBalance(String username,String pwd)throws Exception{
        // 加载MySQL驱动，高版本MySQL可以省略
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
        // 创建操作对象
        Statement stmt = conn.createStatement();
        //  SQL注入成功：select balance from accounts where username='abc' and password='xyz' or '1=1';
        String sql = "select balance from accounts where username='"+username+"' and password='"+pwd+"'";
        System.out.println("拼接的SQL是："+sql);
        // 执行查询语句，返回结果集
        ResultSet rs= stmt.executeQuery(sql);
        if(rs.next()){
            double balance = rs.getDouble("balance");
            System.out.println(username+"，您的余额是："+balance);
        }
    }
}
