package com.tupian;

import java.sql.*;

public class Dao {



    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/PaChongTuPian", "root", "root123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //查询Url是否存在
    public Boolean selectUrl(String url) {
        Connection conn = this.getConnection();
        try {
            PreparedStatement prep = conn.prepareStatement("select count(1) from pages where url=?");
            prep.setString(1, url);
            ResultSet res = prep.executeQuery();
            int count = 0;
            if (res.first()) {
                count = res.getInt(1);
            }
            res.close();
            conn.close();
            prep.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}
