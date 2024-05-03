package org.xahj.statement;

import java.sql.DriverManager;
import java.sql.*;

public class StatementQuery {
    public static void main(String[] args)throws Exception {
        // 加载MySQL驱动，高版本MySQL可以省略
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
        // 创建操作对象
        Statement stmt = conn.createStatement();
        String sql = "select name,score from student where score>=96";
        // 执行查询语句，返回结果集
        ResultSet rs= stmt.executeQuery(sql);
        System.out.println("姓名\t成绩");
        // 每次循环前先调用next()，移动游标指针，判断结果集中当前游标指针指向的是否有记录
        while(rs.next()){
            String stuname = rs.getString("name");
            double stuscore = rs.getDouble("score");
            System.out.println(stuname+"\t"+stuscore);
        }
        rs.close();
        stmt.close();
        conn.close();
    }
}
