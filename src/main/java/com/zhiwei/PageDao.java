package com.zhiwei;

import java.sql.*;

public class PageDao {
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/PaChong", "root", "root123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 判断页面是否存在
     * @param url
     * @return
     */
    public Boolean exist(String url) {
        try {
            Connection con = this.getConnection();
            PreparedStatement prep = con.prepareStatement("select count(1) from pages where url = ?");
            prep.setString(1, url);
            ResultSet res = prep.executeQuery();
            int count = 0;
            if (res.first()) {
                count = res.getInt(1);
            }
            res.close();
            con.close();
            prep.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 将数据插入到数据库
     * @param url   url地址
     * @param loc   保存在本地路径
     * @param keywords  关键字
     */
    public void insert(String url,String loc,String keywords) {
        try {
            Connection con = this.getConnection();
            PreparedStatement prep = con.prepareStatement(" insert into pages(url,loc,keywords) value(?,?,?) ");
            prep.setString(1, url);
            prep.setString(2, loc);
            prep.setString(3, keywords);
            prep.executeUpdate();

            con.close();
            prep.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}



