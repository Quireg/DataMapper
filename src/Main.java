import exampleClasses.Company;
import exampleClasses.Store;
import exampleClasses.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws DataMapperException, IOException, SQLException, IllegalAccessException {




        User u1 = new User("Kolia", "Loboda", 22, 5000);
        Company c1 = new Company("intel", "Toronto");
        Store s1 = new Store();

//        QueryBuilder.buildInsertSql(u1);
        User u2 = new User("test", "test", 22, 5000);
        DbMapper mp = new DbMapper();
        mp.save(u2);
////
//        User u111 = (User)mapper.load(4, User.class);
//        System.out.println(u111.getName()+u111.getLname()+u111.getAge()+u111.getSalary());
//        System.out.println();
//        Company comp = (Company)mapper.load(1, Company.class);
//        System.out.println(comp.getName()+comp.getAddress());


    }
}
