package org.xahj.batch;
import java.sql.*;
// Statement批处理，针对：批处理不同的SQL结构
public class StatementBatch {
    public static void main(String[] args)throws Exception {
        // 加载MySQL驱动，高版本MySQL可以省略
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
        // 创建操作对象
        Statement stmt = conn.createStatement();
        conn.setAutoCommit(false);  // 取消事务的自动提交机制
        String sql1 = "insert into accounts(username,password,balance)values('悟空','123456',300)";
        String sql2 = "update accounts set balance=200 where id=2";
        String sql3 = "delete from accounts where id=1";
        // 将三条SQL语句加入批处理中
        stmt.addBatch(sql1);
       // int a=5/0;
        stmt.addBatch(sql2);
        stmt.addBatch(sql3);
        // 执行批处理SQL语句
        stmt.executeBatch();
        conn.commit();  // 提交事务，使批处理SQL正式生效
        System.out.println("批处理SQL执行成功！");
        stmt.close();
        conn.close();
    }
}
