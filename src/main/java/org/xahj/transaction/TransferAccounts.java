package org.xahj.transaction;
import java.sql.*;

// 这只是一个演示事务操作，具体业务逻辑，可自行完善
public class TransferAccounts {
    public static void main(String[] args) {
        boolean flag=transfer("tom","alice",200);
        if(flag){
            System.out.println("转账成功~~~");
        }else{
            System.out.println("转账失败！");
        }
    }

    public static boolean transfer(String fromUser,String toUser,double money){
        Connection conn=null;
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        boolean flag=true;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
            // 取消自动提交事务的机制
            conn.setAutoCommit(false);
            // 转出业务
            String sql1 = "update accounts set balance=balance-? where username=?";
            ps1 = conn.prepareStatement(sql1);
            ps1.setDouble(1,money);
            ps1.setString(2,fromUser);
            ps1.executeUpdate();
            int a = 5 / 0;  // 此处会发生异常
            // 转入业务
            String sql2 = "update accounts set balance=balance+? where username=?";
            ps2 = conn.prepareStatement(sql2);
            ps2.setDouble(1,money);
            ps2.setString(2,toUser);
            ps2.executeUpdate();
            // 提交事务，结束事务
            conn.commit();
        }catch(Exception e){
            try{
                if(conn!=null){
                    conn.rollback();  // 回滚事务，结束事务
                }
                flag=false;  // 标识符修改为false
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }finally {
            if(ps2!=null){
                try{
                    ps2.close();
                }catch (Exception e){
                }
            }
            if(ps1!=null){
                try{
                    ps1.close();
                }catch (Exception e){
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch (Exception e){
                }
            }
            return flag;
        }
    }
}
