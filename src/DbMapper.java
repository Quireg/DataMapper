import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        ArrayList<Field> fields = new ArrayList<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            fields.add(field);
            System.out.println(field.getType());
        }


        st.setString(1, fields.get(0).get(obj).toString());
        st.setString(2, fields.get(1).get(obj).toString());
        st.setInt(3, Integer.parseInt(fields.get(2).get(obj).toString()));
        st.setInt(4, Integer.parseInt(fields.get(3).get(obj).toString()));
        st.executeUpdate();
    }
//asdasfjlkajfk
    @Override
    public Object load(long id, Class clazz) throws DataMapperException {
        return null;
    }

    @Override
    public ArrayList<Object> loadAll(Class clazz) {
        return null;
    }

    @Override
    public void update(long id, Object obj) {

    }
}
