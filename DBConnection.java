package db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.postgresql.PGStatement;

/**
 *
 * @author Cristian
 */
public class DBConnection {
    
    AbstractDB conn;
    Connection conection;
    PreparedStatement ps;
    
    /**
     * Si no recibe Teruel como argumento, tomamos que es Soria.
     * @param zone 
     */
    public DBConnection(String zone) {
        if(zone.equalsIgnoreCase("Teruel")) {
            conn = new Conexion_postgresql();
        } else/* if (zone.equals("Soria*/ {
            conn = new Conexion_mysql();
        }
        conection = conn.conectar();
    }
    
    public boolean isConnected() {
        return conection != null;
    }
    
    public void prepareQuery(String query) throws SQLException {
        if(conn.getType() == "postgresql") {
            if(query.contains("sucursal")) {
                if(query.contains("sucursal = ?")) {
                    query = query.replace("sucursal = ?", "sucursal = ?::sucursal");
                } else if(query.contains("sucursal=?")) {
                    query = query.replace("sucursal=?", "sucursal = ?::sucursal");
                } else if(query.contains("INSERT")) {
                    query = query.substring(0, query.length()-2);
                    query += "?::sucursal)";
                }
            }
        }
        ps = conection.prepareStatement(query);
    }
    
    public void setInt(int pos, int value) throws SQLException {
        ps.setInt(pos, value);
    }

    public void setDouble(int pos, double value) throws SQLException {
        ps.setDouble(pos, value);
    }
    
    public void setString(int pos, String value) throws SQLException {
        ps.setString(pos, value);
    }
    //http://jdbc.postgresql.org/documentation/81/server-prepare.html
    public ResultSet executeQuery() throws SQLException {
        System.out.println(toString());
        ResultSet rs = null;
        System.out.println(conn.getType());
        //if(conn.getType().equalsIgnoreCase("TERUEL")) {
            String query = toString().substring(toString().indexOf("SELECT"));
            System.out.println("query: " + query);
        //}
        /*if(conn.getType().equals("TERUEL")) {
            org.postgresql.PGConnection pgconn;
            org.postgresql.PGStatement pgstmt;
            pgconn = (org.postgresql.PGConnection)conn;
            pgstmt = (org.postgresql.PGStatement)ps;
            pgstmt.setPrepareThreshold(3);
            pgstmt.
        }*/
        rs = ps.executeQuery();
        return rs;
    }
    
    public void close() throws SQLException {
        conection.close();
    }
    
    public int executeUpdate () throws SQLException {
        System.out.println(toString());
        int result = ps.executeUpdate();
        return result;
    }
    
    
    //esto hay que borrarlo
    public String toString() {
        return ps.toString();
    }
    
}
