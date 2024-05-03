package org.xahj.batch;
import java.sql.*;

// // PreparedStatement批处理，针对：批处理相同的SQL结构，这种情况下效率高
public class PreparedBatch {
    public static void main(String[] args)throws Exception {
        // 加载MySQL驱动，高版本MySQL可以省略
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
        conn.setAutoCommit(false);  // 取消事务自动提交机制
        String sql = "insert into accounts(username,password,balance)values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for(int i=0;i<10;i++){
            ps.setString(1,"齐天大圣"+i);
            ps.setString(2,"swk"+i);
            ps.setDouble(3,1000+i);
            ps.addBatch();   // 将当前编译好的SQL加入批处理队列中
        }
        ps.executeBatch();  // 执行批处理
        conn.commit();  // 提交事务，使得数据库操作生效
        System.out.println("批处理SQL执行成功！");
        ps.close();
        conn.close();
    }
}
