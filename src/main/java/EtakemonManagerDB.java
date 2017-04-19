import java.sql.PreparedStatement;
import com.mysql.jdbc.Connection;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Hashtable;


/**
 * Created by Ignacio on 19/04/2017.
 */
public class EtakemonManagerDB<Usuario> {
    Connection connection;
    PreparedStatement stm;
    public void add(Usuario e)throws Exception{
        StringBuffer  comanda = new StringBuffer("INSERT INTO ").append(this.getClass().getSimpleName()).append("s (");
        Field[] atributos = this.getClass().getDeclaredFields();
        for (int i=0;i<atributos.length;i++) {

            Field a=atributos[i];
            comanda.append(a.getName());
            if (i<atributos.length-1)
                comanda.append(",");
        }
        comanda.append(") VALUES (");
        for (int i=0;i<atributos.length;i++) {
            if(i<atributos.length-1)
                comanda.append("'").append(atributos[i].get(e).toString()).append("',");
            else
                comanda.append(atributos[i].get(e).toString()).append(")");
        }
        try {
            connection= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/datos1","root","root");
            stm = connection.prepareStatement(comanda.toString());
            //stm.setObject(1,"asdasa");
            stm.execute();
            connection.close();
        }catch (SQLException sqle) {
            System.out.println("Error en la ejecución:"
                    + sqle.getErrorCode() + " " + sqle.getMessage());}
        System.out.println(comanda);
    }
    public void delete(Usuario e)throws Exception{
        StringBuffer  comanda = new StringBuffer("DELETE FROM ").append(this.getClass().getSimpleName());
        Field[] atributos = this.getClass().getDeclaredFields();
        comanda.append(" WHERE ");
        for (int i=0;i<atributos.length;i++) {
            if(i<atributos.length-1)
                comanda.append(atributos[i].getName()).append("='").append(atributos[i].get(e).toString()).append("',");
            else
                comanda.append("'").append(atributos[i].getName()).append(atributos[i].get(e).toString()).append("'");
        }
        System.out.println(comanda);
    }
    public void modify(Usuario e)throws Exception{
        StringBuffer  comanda = new StringBuffer("UPDATE  ").append(this.getClass().getSimpleName()).append("(");
        Field[] atributos = this.getClass().getDeclaredFields();
        for (Field a:atributos) {
            comanda.append(a.getName()).append(",");
        }
        comanda.append(") SET (");
        for (int i=0;i<atributos.length;i++) {
            if(i<atributos.length-1)
                comanda.append("").append(atributos[i].get(e).toString()).append(",");
            else
                comanda.append(atributos[i].get(e).toString()).append(")");
        }
        System.out.println(comanda);
    }
    public Usuario select(Usuario e, Hashtable<String,String>conditions)throws Exception{
        StringBuffer  comanda = new StringBuffer("SELECT ");
        if(conditions.contains("All"))
            comanda.append("*");
        else {
            for (String s:conditions.values()) {
                comanda.append(s);
            }
        }
        comanda.append(" FROM ");
        comanda.append(this.getClass().getSimpleName()).append("s ");
        Field[] atributos = this.getClass().getDeclaredFields();
        comanda.append(" WHERE id = ");
        for (int i=0;i<atributos.length;i++) {
            if(atributos[i].getName()=="id")
                comanda.append(atributos[i].get(e).toString()).append("");
        }
        try {
            connection= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/datos1","root","root");
            stm = connection.prepareStatement(comanda.toString());
            ResultSet resultSet=stm.executeQuery();
            Usuario result = getMapObject(e, resultSet);
            System.out.println(comanda);
            connection.close();
            return result;
        }
        catch (SQLException sqle) {
            System.out.println("Error en la ejecución:"
                    + sqle.getErrorCode() + " " + sqle.getMessage());
            return null;
        }
    }
    private Usuario getMapObject(Usuario e, ResultSet resultSet){

        try {
            Class nameClass = e.getClass();
            Field[] propertyClass = nameClass.getDeclaredFields();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next())
            {
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    String columnName = resultSetMetaData.getColumnLabel(i);
                    String columnType = resultSetMetaData.getColumnTypeName(i);
                    for(int x = 0; x <= propertyClass.length; x++) {
                        if (columnName.equals(propertyClass[x].getName()) || columnType.equals(propertyClass[x].getType().toString())) {
                            Object value = getConvertValueType(columnType, resultSet, i);
                            if(value != null)
                            {
                                propertyClass[x].set(e, value);
                            }
                            break;
                        }
                    }
                }
            }
            return e;
        }
        catch(Exception ex) {
            return null;
        }
    }
    private Object getConvertValueType(String columnType, ResultSet resultSet, int index) throws SQLException {
       /*
        if( Boolean.class.isAssignableFrom( type ) ) return resultSet.getBoolean(index);
        if( Byte.class.isAssignableFrom( type) ) return resultSet.getByte(index);
        if( Short.class.isAssignableFrom(type ) ) return resultSet.getShort(index);
        if( int.class.isAssignableFrom(type ) ) return resultSet.getInt(index);
        if( Long.class.isAssignableFrom( type ) ) return resultSet.getLong(index);
        if( Float.class.isAssignableFrom(type ) ) return resultSet.getFloat(index);
        if( Double.class.isAssignableFrom(type ) ) return resultSet.getDouble(index);
        if( String.class.isAssignableFrom(type)) return resultSet.getString(index);
        return null;

       */
        try{
            switch (columnType) {
                case "VARCHAR":
                    return resultSet.getString(index);

                case "INT":
                    return resultSet.getInt(index);

                case "DOUBLE":
                    return resultSet.getDouble(index);

                case "TINYINT":
                    return resultSet.getBoolean(index);

                default:
                    return null;
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }

}
