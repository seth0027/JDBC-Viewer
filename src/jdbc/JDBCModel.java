package jdbc;

import java.sql.*;
import java.util.*;

public class JDBCModel {

    private List<String> columnNames;
    private List<String> tableNames;
    private Connection connection;
    private String user;
    private String pass;

    JDBCModel(){

        columnNames=new ArrayList<>();
        tableNames=new ArrayList<>();

    }

    public void setCredentials(String user,String pass){
        this.user=user;
        this.pass=pass;
    }

    private void checkConnectionIsValid() {

        try {
            if(connection==null || connection.isClosed()){
                throw new Exception("Connection is not valid");
            }
        } catch (Exception e) {


            e.printStackTrace();
        }

    }

    private void checkTableNameAndColumnsAreValid(String table){

        Objects.requireNonNull(table,"table name cannot be  null");
        table=table.trim();

        if(tableNames.isEmpty()){
            this.getAndInitializeTableNames();
        }

        if(columnNames.isEmpty()){
            this.getAndInitializeColumnNames(table);
        }

        if(table.isEmpty() || !tableNames.contains(table)){
            throw new IllegalArgumentException("table name=\""+table+"\" is not valid");
        }

    }

    public void connectTo(String url)  {

     try {
         if (this.isConnected()) {
             this.close();
         }
        //  Class.forName("com.mysql.cj.jdbc.Driver");

         connection = DriverManager.getConnection(url,this.user,this.pass);
     }catch (Exception ex){
         ex.printStackTrace();
     }

    }

    public boolean isConnected(){
        return connection!=null;
    }


    public List<String> getAndInitializeColumnNames(String table){


            this.checkConnectionIsValid();
            columnNames.clear();

            try {
                DatabaseMetaData dbMeta = connection.getMetaData();
                synchronized (this) {
                    ResultSet rs = dbMeta.getColumns(connection.getCatalog(), null, table, null);

                    while (rs.next()) {
                        columnNames.add(rs.getString("COLUMN_NAME"));
                    }

                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            List<String> list=Collections.unmodifiableList(columnNames);
            return list;



    }

    public List<String> getAndInitializeTableNames(){


            this.checkConnectionIsValid();
            tableNames.clear();
          try {
              DatabaseMetaData dbMeta = connection.getMetaData();

              synchronized (this) {
                  ResultSet rs = dbMeta.getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});

                  while (rs.next()) {

                      tableNames.add(rs.getString("TABLE_NAME"));
                  }

              }

          }catch (Exception ex){
              ex.printStackTrace();
          }
          List<String> list= Collections.unmodifiableList(tableNames);

           return  list;

    }

    public List<List<Object>> getAll(String table){


    return this.search(table,"");
    }

    public List<List<Object>> search(String table,String searchTerm){


            this.checkConnectionIsValid();
            this.checkTableNameAndColumnsAreValid(table);

            List<List<Object>>  li=new LinkedList<>();

            String query=this.buildSQLSearchQuery(table,true);
            try( PreparedStatement ps = connection.prepareStatement( query)){

                if(searchTerm!=null){
                    searchTerm=String.format("%%%s%%",searchTerm);

                    for(int i=0;i<columnNames.size();i++){
                        ps.setObject(i+1,searchTerm);
                    }

                    this.extractRowsFromResultSet(ps,li);

                }


            }catch (Exception ex){
                ex.printStackTrace();
            }
            return li;






    }

    private String buildSQLSearchQuery(String table,boolean withParameters){

        StringBuilder sb=new StringBuilder("select * from ");
        sb.append(table);

        if(!withParameters){
            return sb.toString();
        }
        sb.append(" where ");



        for(int i=0;i<columnNames.size();i++){
            if(i==columnNames.size()-1){
                sb.append(columnNames.get(i)+" like ?");
            }
            else{
                sb.append(columnNames.get(i)+" like ? or ");
            }
        }

        return sb.toString();
    }

    private void extractRowsFromResultSet(PreparedStatement ps,List<List<Object>> list){

        try( ResultSet rs = ps.executeQuery()){
            while( rs.next()){
                List< Object> row = new ArrayList<>( );
                for( int i = 1; i <= columnNames.size(); i++){
                    row.add( rs.getObject( i));
                }
                list.add(row);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void close(){
try {
    if (this.isConnected()) {
        connection.close();
    }
}catch (Exception ex){
    ex.printStackTrace();
}
    }



}
