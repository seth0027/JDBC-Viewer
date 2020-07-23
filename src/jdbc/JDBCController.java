package jdbc;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.builder.JDBCURLBuilder;

import java.util.List;

public class JDBCController {
    private JDBCURLBuilder builder;
    private JDBCModel model;
    private StringProperty tableInUse;
    private ObservableList<String> tableNamesList;

    public JDBCController(){
    tableNamesList= FXCollections.observableArrayList();
    model=new JDBCModel();
    tableInUse=new SimpleStringProperty();
    tableInUse.addListener(((observable, oldValue, newValue) -> {
        model.getAndInitializeColumnNames(newValue);
    }));

    }

    public StringProperty tableInUseProperty() {
        return tableInUse;
    }
    public JDBCController setURLBuilder(JDBCURLBuilder builder){
        this.builder=builder;
        return this;
    }
    public JDBCController setDataBase(String address,String port,String catalog){
        builder.setAddress(address);
        builder.setPort(port);
        builder.setCatalog(catalog);
        return this;
    }

    public JDBCController addConnectionURLProperty(String key, String value){

        builder.addURLProperty(key,value);

        return  this;
    }

    public JDBCController setCredentials(String user,String pass){
        model.setCredentials(user,pass);
        return  this;
    }

    public JDBCController connect(){

        this.model.connectTo(builder.getURL());
        return this;
    }

    public boolean isConnected(){
        return model.isConnected();
    }
    public List<String> getColumnNames(){
        return model.getAndInitializeColumnNames(this.tableInUse.get());
    }

    public List<List<Object>> getAll(){
        return model.getAll(this.tableInUse.get());
    }

    public List<List<Object>> search(String searchTerm){
        return model.search(this.tableInUse.get(),searchTerm);
    }

    public void close(){

        model.close();

    }

    public ObservableList<String> getTableNames(){
        if(model.isConnected()){
            tableNamesList.clear();
            tableNamesList.addAll(model.getAndInitializeTableNames());
        }

        return tableNamesList;
    }

}
