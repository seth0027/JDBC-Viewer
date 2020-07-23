package jdbc.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class JDBCURLBuilder {

protected static  final String JDBC="jdbc";
protected Map<String,String> properties;
protected String dbType;
protected int portNumber;
protected String hostAddress;
protected String catalogName;

public JDBCURLBuilder(){

    properties=new HashMap<>();

}

public void setPort(String port){


          int pNmber = Integer.parseInt(port);

          if(pNmber<0){
              throw new IllegalArgumentException("Port Number can not be negative");
          }

          this.portNumber=pNmber;


}

public void addURLProperty(String key,String value){
    Objects.requireNonNull(value,"property value cannot be null");

    properties.put(key,value);

}
protected  void setDB(String db){
    Objects.requireNonNull(db,"DB can not be null");
    this.dbType=db;

}

public abstract String getURL();

public void setPort(int port){
   if(port<0){
       throw new IllegalArgumentException("Port Number can not be negative");

   }
   this.portNumber=port;

}

public void setAddress(String address){

    Objects.requireNonNull(address, "Address can not be null");
    this.hostAddress=address;

}
public void setCatalog(String catalog){
    Objects.requireNonNull(catalog,"Catalog can not be null");
    this.catalogName=catalog;

}
}

