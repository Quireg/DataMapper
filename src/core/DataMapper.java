package core;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DataMapper {

    public void save(Object obj) throws DataMapperException, SQLException, IllegalAccessException;

    public Object load(long id, Class clazz) throws DataMapperException, SQLException, IllegalAccessException, InstantiationException;

    public ArrayList<Object> loadAll(Class clazz) throws DataMapperException, SQLException, IllegalAccessException, InstantiationException;

    public void update(long id, Object obj) throws DataMapperException, IllegalAccessException, InstantiationException, SQLException;


}
