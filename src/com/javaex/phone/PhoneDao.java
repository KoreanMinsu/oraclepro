package com.javaex.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "phonedb";
	private String pw = "phonedb";

	private void getConnection() {
		// driver
		try {
			Class.forName(driver);
			// jdbc connecting
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("driver not loaded" + e);
		} catch (SQLException e) {
			System.out.println("error" + e);
		}
	}

	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error" + e);
		}
	}

//insert
	public int PersonInsert(PersonVo personVo) {
		int count = -1;
		this.getConnection();
		try {
			String query = "";
			query += " insert into person ";
			query += " values(seq_person_id.nextval, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personVo.getPersonId());
			pstmt.setString(2, personVo.getName());
			pstmt.setString(3, personVo.getHp());
			pstmt.setString(4, personVo.getCompany());

			count = pstmt.executeUpdate();

			System.out.println(count + "건이 등록되었네.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
		return count;
	}

//update
	public int PersonUpdate(PersonVo personVo) {
		int count = -1;
		this.getConnection();
		try {
			String query = "";
			query += " update from person ";
			query += " set    name = ?, ";
			query += "        hp= ?, ";
			query += "        company = ? ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());

			count = pstmt.executeUpdate();
			System.out.println(count + "건이 수정되었네.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
		return count;
	}

//delete
	public int PersonDelete(int personId) {
		int count = -1;
		this.getConnection();
		try {
			String query = "";
			query += " delete from person ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);
			count = pstmt.executeUpdate();
			System.out.println(count + "건이 삭제되었네.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
		return count;
	}

//personList
	public List<PersonVo> getPersonList() {
		List<PersonVo> personList = new ArrayList<PersonVo>();

		this.getConnection();

		try {
			String query = "";
			query += " select person_id, ";
			query += " 		 name, ";
			query += " 		 hp, ";
			query += " 		 company ";
			query += " from person ";

			pstmt = conn.prepareStatement(query);

			while (rs.next()) {
				int personId = rs.getInt("personId");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo personVo = new PersonVo(personId, name, hp, company);

				personList.add(personVo);
			}

			rs = pstmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
		return personList;
	}

}
