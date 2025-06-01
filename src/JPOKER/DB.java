package JPOKER;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/JPOKER",
                "root",                               
                "0000"                                
            );

            System.out.println("DB 연결 성공!");

            conn.close();

        } catch (Exception e) {
            System.out.println("❌ DB 연결 실패:");
            e.printStackTrace();
        }
    }
}