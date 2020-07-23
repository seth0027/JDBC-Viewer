package jdbc.builder;

public class URLBuilderFactory {

    public static JDBCURLBuilder getURLBuilder(String db){

        switch(db.toLowerCase()){
            case "mysql": return new MySQLURLBuilder();

            default: return null;
        }
    }
}
