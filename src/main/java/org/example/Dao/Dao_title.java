package org.example.Dao;

public class Dao_title extends DaoFather {


    public Dao_title(int choseDB, int choseTable) {
        super(choseDB, choseTable);
    }

    public void Methoc_ChangeDownState(int ID){
        String sql1 = "update "+tableName+"  set  DownState = '是' where C_ID ="+ID;
        MethodIUD(sql1);
    }

//    public ArrayList<Object> MethodFind(int groupNumber) {
//        ArrayList<Object> results = new ArrayList<Object>();
//        try {
//            String query = "SELECT*FROM " + this.tableName + "   where GroupNumber = "+groupNumber;
//            System.out.println(query);
//            MethodCreateSomeObject();
//            ResultSet resultSet = stmt.executeQuery(query);
//            while (resultSet.next()) {
//                Class F = Class.forName(this.beanName);
//                //Class F = obj.getClass();
//                //实例化
//                Object Find = F.newInstance();
//                //获取列名
//                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//                //获取列数
//                int lieNumber = resultSetMetaData.getColumnCount();
////                System.out.println(lieNumber);
//                for (int i = 0; i < lieNumber; i++) {
//                    //通过序号获取列名
//                    String columnName = resultSetMetaData.getColumnName(i + 1);
//                    //获取值
//                    Object columnValue = resultSet.getObject(i + 1);
//                    //根据列名获取属性.getDeclaredField,获取类中所有的声明字段
//                    Field field = F.getDeclaredField(columnName);
//                    //可以向私有属性中写值,将private变为public
//                    field.setAccessible(true);
//                    //写值
//                    field.set(Find, columnValue);
//                }
//                results.add(Find);
//            }
//        } catch (Exception ex) {
//            System.out.println(ex.toString());
//        }
//        return results;
//    }

}
