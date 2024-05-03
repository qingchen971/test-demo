package org.xahj.preparedstatement;
import java.sql.*;

public class StopSQLInjection {
    public static void main(String[] args)throws Exception {
        queryBalance("abc","xyz' or '1=1");
        //queryBalance("alice","alice456");
    }

    public static void queryBalance(String username,String pwd)throws Exception{
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            // 加载MySQL驱动，高版本MySQL可以省略
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取数据库连接对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
            // 编写将要预编译的SQL
            String sql = "select balance from accounts where username=? and password=?";
            // 创建PreparedStatement预操作对象
            ps = conn.prepareStatement(sql);
            // 填充占位符
            ps.setString(1,username);
            ps.setString(2,pwd);
            // 执行查询语句，返回结果集，注意：此时就不要在该方法中传SQL字符串了
            rs= ps.executeQuery();
            if(rs.next()){
                double balance = rs.getDouble("balance");
                System.out.println(username+"，您的余额是："+balance);
            }else{
                System.out.println("用户名或密码错误！");
            }
        }catch (Exception e){
            System.out.println("操作有问题，请仔细检查！");
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                ps.close();
                conn.close();
                System.out.println("finally...");
            }catch (Exception e){
                System.out.println("finally...");
            }

        }

    }
}
