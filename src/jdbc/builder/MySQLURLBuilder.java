package jdbc.builder;

import java.util.Map;

public class MySQLURLBuilder extends JDBCURLBuilder {


    public MySQLURLBuilder(){
        this.setDB("mysql");

    }
    @Override
    public String getURL(){

        String property="";

        if(!properties.isEmpty()) {
            int len=properties.size()-1;
            int counter=0;
            property+="?";
            for (Map.Entry<String, String> prop : properties.entrySet()) {
                if(counter==len)
                property += prop.getKey() + "=" + prop.getValue();

                else{
                    property += prop.getKey() + "=" + prop.getValue()+"&";


                }
                counter++;


            }

        }

        String url=String.format("%s:%s://%s:%s/%s%s",JDBCURLBuilder.JDBC,this.dbType,this.hostAddress,this.portNumber,this.catalogName,property);

            return url;
    }

}
