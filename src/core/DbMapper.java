package core;


import exampleClasses.Table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class DbMapper implements DataMapper {
    Connection conn;

    DbMapper() throws SQLException, IOException {
        conn = DBConnection.getConnection();
    }

    @Override
    public void save(Object obj) throws DataMapperException, SQLException, IllegalAccessException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(QueryBuilder.buildInsertSql(obj));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int count = 1;
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            System.out.println(field.getType().getTypeName());
            if (field.getType().getTypeName().equals("java.lang.String")) {
                st.setString(count, field.get(obj).toString());
            } else if (field.getType().getTypeName().equals("int")) {
                st.setInt(count, Integer.parseInt(field.get(obj).toString()));
            }
            count++;
        }


        st.executeUpdate();
    }

    @Override
    public Object load(long id, Class clazz) throws DataMapperException, SQLException, IllegalAccessException, InstantiationException {
        Object attempt = EntityCache.loadFromCache(clazz.newInstance(), id);
        if(attempt != null){
            return attempt;
        }else {
            Object obj = clazz.newInstance();
            String tableName = obj.getClass().getAnnotation(Table.class).name();
            String objectId = "id" + tableName;
            String query = "SELECT * FROM " + tableName + " WHERE " + objectId + "=" + id;
            System.out.println(query);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            String[] data = null;
            while (rs.next()) {
                String tempData = "";
                for (int i = 1; i <= columnsNumber; i++) {
                    tempData += rs.getString(i) + " ";
                }
                data = tempData.split(" ");

            }

            try {

                for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
                    Field[] f = clazz.getDeclaredFields();
                    f[i].setAccessible(true);
                    System.out.println(f[i].getName());


                    if (f[i].getType().equals(String.class)) {
                        f[i].set(obj, data[i + 1]);
                    } else if (f[i].getType().equals(Integer.class)) {
                        f[i].set(obj, Integer.parseInt(data[i + 1]));
                    } else if (f[i].getType().equals(long.class)) {
                        f[i].set(obj, Long.parseLong(data[i + 1]));
                    } else if (f[i].getType().equals(float.class)) {
                        f[i].set(obj, Float.parseFloat(data[i + 1]));
                    } else if (f[i].getType().equals(double.class)) {
                        f[i].set(obj, Double.parseDouble(data[i + 1]));
                    } else if (f[i].getType().equals(short.class)) {
                        f[i].set(obj, Short.parseShort(data[i + 1]));
                    } else if (f[i].getType().equals(int.class)) {
                        f[i].set(obj, Integer.parseInt(data[i + 1]));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new DataMapperException();
            }
            EntityCache.saveToCache(id, obj);
            return obj;
        }

    }

    @Override
    public ArrayList<Object> loadAll(Class clazz) throws DataMapperException, IllegalAccessException, InstantiationException, SQLException {
        Object tempObj = clazz.newInstance();
        String tableName = tempObj.getClass().getAnnotation(Table.class).name();
        ArrayList<Object> result = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        Object obj;
        while (rs.next()) {
            String[] data;
            String tempData = "";
            for (int i = 1; i <= columnsNumber; i++) {
                tempData += rs.getString(i) + " ";
            }
            data = tempData.split(" ");
            try {
                obj = clazz.newInstance();
                for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
                    Field[] f = clazz.getDeclaredFields();
                    f[i].setAccessible(true);
                    System.out.println(f[i].getName());


                    if (f[i].getType().equals(String.class)) {
                        f[i].set(obj, data[i + 1]);
                    } else if (f[i].getType().equals(Integer.class)) {
                        f[i].set(obj, Integer.parseInt(data[i + 1]));
                    } else if (f[i].getType().equals(long.class)) {
                        f[i].set(obj, Long.parseLong(data[i + 1]));
                    } else if (f[i].getType().equals(float.class)) {
                        f[i].set(obj, Float.parseFloat(data[i + 1]));
                    } else if (f[i].getType().equals(double.class)) {
                        f[i].set(obj, Double.parseDouble(data[i + 1]));
                    } else if (f[i].getType().equals(short.class)) {
                        f[i].set(obj, Short.parseShort(data[i + 1]));
                    } else if (f[i].getType().equals(int.class)) {
                        f[i].set(obj, Integer.parseInt(data[i + 1]));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new DataMapperException();
            }
            result.add(obj);
        }



        return result;
    }

    @Override
    public void update(long id, Object obj) throws DataMapperException, IllegalAccessException, InstantiationException, SQLException{

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(QueryBuilder.buildUpdateSql(obj, id));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int count = 1;
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            System.out.println(field.getType().getTypeName());
            if (field.getType().getTypeName().equals("java.lang.String")) {
                st.setString(count, field.get(obj).toString());
            } else if (field.getType().getTypeName().equals("int")) {
                st.setInt(count, Integer.parseInt(field.get(obj).toString()));
            }
            count++;
        }


        st.executeUpdate();
        EntityCache.saveToCache(id, obj);
//UPDATE table_name SET field1=new-value1, field2=new-value2
//        st.executeUpdate();
    }
}
