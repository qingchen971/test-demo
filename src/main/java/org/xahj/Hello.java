package org.xahj;
import java.sql.*;

public class Hello {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // 加载MySQL驱动，高版本MySQL可以省略
         //   Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取数据库连接对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
            // 创建操作对象
            stmt = conn.createStatement();
            String sql = "insert into school(name,introduce)values('八戒大学','八戒大学很好')";
            int rows = stmt.executeUpdate(sql);
            System.out.println("影响了"+rows+"行！");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                stmt.close();
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
