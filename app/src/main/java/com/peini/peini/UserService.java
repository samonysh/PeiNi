package com.peini.peini;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 2017/8/22.
 */

public class UserService {
    //config
    private static String url="jdbc:mysql://47.94.86.98:3306/peini_users";
    private static String user="root";
    private static String passwordSQL="uAiqwVwjJ8-i";

    //login
    public static int login(User person){
        try{
            Connection conn = MySQLUtil.openConnection(url,user,passwordSQL);
            Statement sta = conn.createStatement();
            String sql = "select * from users;";
            ResultSet res = sta.executeQuery(sql);

            String name = person.getName();
            String password = person.getPassword();

            while (res.next()){
                if(res.getString(2).equals(name)) {
                    if(res.getString(6).equals(password)){
                        if(res.getString(5).equals("old")){
                            PersonData.userName=name;
                            res.close();
                            sta.close();
                            conn.close();
                            return 1;
                        }else{
                            PersonData.userName=name;
                            res.close();
                            sta.close();
                            conn.close();
                            return 2;
                        }
                    }else{
                        res.close();
                        sta.close();
                        conn.close();
                        return 0;
                    }
                }
            }

            res.close();
            sta.close();
            conn.close();
            return 0;

        }
        catch (SQLException e) {
            return 0;
        }

    }

    //register
    public static Boolean register(User person){
        try {
            Connection conn = MySQLUtil.openConnection(url,user,passwordSQL);
            Statement sta = conn.createStatement();
            String sql = "INSERT INTO `users` (`name`, `realname`, `mobile`, `type`, `password`) VALUES ('"+person.getName()+"', '"+person.getRealName()+"', '"+person.getMobile()+"', '"+person.getType()+"', '"+person.getPassword()+"');";
            sta.executeUpdate(sql);

            sta.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //showpair

    //formPair
    public static Boolean formPair(String oldName,String youngName){
        return false;
    }
    //show person data
}
