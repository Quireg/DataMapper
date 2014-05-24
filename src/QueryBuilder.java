import exampleClasses.Entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Scanner;

public class QueryBuilder {
    public static String buildInsertSql(Object o) throws FileNotFoundException {
       String tableName = o.getClass().getAnnotation(Entity.class).name();
        File file = new File("C:\\Users\\blaze.fun\\IdeaProjects\\DataMapper\\src\\configs\\UserDb.conf");
        Scanner scanner = new Scanner(file);
        ArrayList<String> arr = new ArrayList<>();
        while (scanner.hasNextLine()){
            String line =scanner.nextLine();
            String[] params = line.split(":");
            arr.add(params[1]);
        }
        Annotation[] asd = o.getClass().getAnnotations();
        for (Annotation annotation : asd) {
            System.out.println(annotation);
        }

        String params = "";
        String values = "";
        for (int i = 0; i < arr.size(); i++) {



            if(i != arr.size() -1){
                values = values + "?,";
                params = params + arr.get(i) + ", ";
            }
            else if(i == arr.size() -1){
                params = params + arr.get(i);
            values = values + "?";}

        }

        String sql = "INSERT INTO "+ tableName+ " ("+ params +") VALUES " + "("+values+")";
        System.out.println(sql);
        return sql;
    }
}
