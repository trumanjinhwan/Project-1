package dao;

import java.sql.*;
import java.util.*;

public class ClientDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/orcl";
	String user = "c##apple";
	String password = "1111";

	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;

	public ArrayList<String> list(String name) {

		ArrayList<String> str = new ArrayList<>();

		try {
			connDB();

			// SQL 쿼리에서 파라미터 바인딩
			String query = "SELECT * FROM Client WHERE name = ?";
			stmt = con.prepareStatement(query); // PreparedStatement 사용
			stmt.setString(1, name); // 첫 번째 파라미터에 name 값을 바인딩

			rs = stmt.executeQuery(); // 쿼리 실행

			while (rs.next()) {
				String irum = rs.getString("NAME");
				String email = rs.getString("EMAIL");
				String ps = rs.getString("PASSWORD");

				// name, email, password 값을 하나의 문자열로 결합하여 리스트에 추가
				String record = "Name: " + irum + ", Email: " + email + ", Password: " + ps;
				str.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str; // 결과 반환
	}

	public void connDB() {
		try {
			Class.forName(driver); // JDBC 드라이버 로드
			System.out.println("jdbc driver loading success.");

			// 첫 번째 URL로 연결 시도
			try {
				con = DriverManager.getConnection(url, user, password); // DB 연결
				System.out.println("oracle connection success with URL: " + url);
			} catch (Exception e) {
				System.out.println("Connection failed with URL: " + url);
				System.out.println("Retrying with alternate URL...");

				// 두 번째 URL로 재시도
				String alternateUrl = "jdbc:oracle:thin:@localhost:1521/XE";
				con = DriverManager.getConnection(alternateUrl, user, password);
				System.out.println("oracle connection success with alternate URL: " + alternateUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
