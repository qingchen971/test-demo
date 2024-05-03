package org.xahj.preparedstatement;
import java.sql.*;

public class PreStatement_insert
{
    public static void main(String[] args)throws Exception {
        // 加载MySQL驱动，高版本MySQL可以省略
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
        // 编写SQL，并使用占位符
        String sql="insert into student(name,score)values(?,?)";
        // 创建PreparedStatement预处理对象
        PreparedStatement ps= conn.prepareStatement(sql);
        for(int i=0;i<10;i++){
            // 填充占位符
            ps.setString(1,"Alex"+i);
            ps.setDouble(2,99.5+i);
            int rows = ps.executeUpdate();
            System.out.println("添加了"+rows+"条记录！");
        }
        ps.close();
        conn.close();

    }
}
