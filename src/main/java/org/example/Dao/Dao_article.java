package org.example.Dao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class Dao_article extends DaoFather {


    public Dao_article(int choseDB, int choseTable) {
        super(choseDB, choseTable);
    }

    public void Methoc_ChangeDownState(int ID){
        String sql1 = "update "+tableName+"  set  DownState = '是' where  ID ="+ID;
        MethodIUD(sql1);
    }

    public ArrayList<Object> MethodFind_hasecomments() {
        ArrayList<Object> results = new ArrayList<Object>();
        try {
//            String query = "SELECT*FROM " + this.tableName + " where Comment_Count != 0 AND C_ID<=69338";
            String query = "SELECT*FROM " + this.tableName + "  where C_comment_count != 0 ";
            MethodCreateSomeObject();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                Class F = Class.forName(this.beanName);
                //Class F = obj.getClass();
                //实例化
                Object Find = F.newInstance();
                //获取列名
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                //获取列数
                int lieNumber = resultSetMetaData.getColumnCount();
//                System.out.println(lieNumber);
                for (int i = 0; i < lieNumber; i++) {
                    //通过序号获取列名
                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    //获取值
                    Object columnValue = resultSet.getObject(i + 1);
                    //根据列名获取属性.getDeclaredField,获取类中所有的声明字段
                    Field field = F.getDeclaredField(columnName);
                    //可以向私有属性中写值,将private变为public
                    field.setAccessible(true);
                    //写值
                    field.set(Find, columnValue);
                }
                results.add(Find);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return results;
    }


}
