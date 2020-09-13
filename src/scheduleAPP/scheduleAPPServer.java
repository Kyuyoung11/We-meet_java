package scheduleAPP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class scheduleAPPServer {
	// ̱ ϱ ڵ
	private static scheduleAPPServer instance = new scheduleAPPServer();

	public static scheduleAPPServer getInstance() {
		return instance;
	}

	public scheduleAPPServer() {

	}

	String jdbc_url = "jdbc:mysql://localhost:3306/example?characterEncoding=euckr&useUnicode=true&mysqlEncoding=euckr&useSSL=false&serverTimezone=Asia/Seoul";
	String dbId = "root"; // MySQL
	String dbPw = "hansung"; // й ȣ
	Connection conn = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	Statement stmt = null;

	ResultSet rs = null;
	ResultSet res = null;
	String sql = "";
	String sql2 = "";
	String sql3 = ""; // 추가
	String returns = "";
	String returns2 = "";
	String returns3 = "";
	StringBuilder sb = new StringBuilder();

	public String joindb(String id, String pwd, String name) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			if (id.equals("")) {
				return "emptyid";
			} else if (pwd.equals("")) {
				return "emptypw";
			} else if (name.equals("")) {
				return "emptyname";
			} else {
				sql = "select id from user where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					if (rs.getString("id").equals(id)) { // ̹ ̵ ִ
						returns = "id";
					}
				} else { // Է ̵

					sql2 = "insert into user values(?,?,?,?)";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, pwd);
					pstmt2.setString(3, name);
					pstmt2.setString(4, "지역");

					pstmt2.executeUpdate();

					stmt = conn.createStatement();
					sql3 = sb.append("create table calendar").append(id).append("( id char(15) NOT NULL, ")
							.append("date date NOT NULL, ").append("schedule varchar(80), ").append("memo text, ")
							.append("foreign key(id) references user(id), ").append("primary key(id, date, schedule));")
							.toString();

					stmt.execute(sql3);

					returns = "ok";

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String logindb(String id, String pwd) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select id,pw from user where id=? and pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("id").equals(id) && rs.getString("pw").equals(pwd)) {
					returns2 = "true";// 로그인 가능
				} else {
					returns2 = "false"; // 로그인 실패
				}
			} else {
				returns2 = "noId"; // 아이디 또는 비밀번호 존재 X
			}

		} catch (Exception e) {
			returns2 = "NO!";
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns2;
	}

	public String calendardb(String id, String date, String schedule, String memo) {
		String user_calendar = "calendar" + id;
		String rowdb = "";
		returns3 = "";

		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = sb.append("select date, schedule, memo from ").append(user_calendar).append(" where date = ?")
					.toString();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				rowdb = rowdb + rs.getString("date") + "," + rs.getString("schedule") + "," + rs.getString("memo")
						+ ","; // date, schedule, memo
			}

			returns3 = rowdb;

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns3;
	}

	public String addCalendar(String id, String date, String schedule, String memo) {
		String user_calendar = "calendar" + id;

		returns3 = "";

		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = sb.append("select date, schedule from ").append(user_calendar)
					.append(" where date = ? and schedule = ? ").toString();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setString(2, schedule);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				returns3 = "false";

			} else {
				sb = new StringBuilder();
				sql2 = sb.append("insert into ").append(user_calendar).append(" values(?,?,?,?) ").toString();
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, date);
				pstmt2.setString(3, schedule);
				pstmt2.setString(4, memo);
				pstmt2.executeUpdate();

				returns3 = "done";
			}

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
		}
		return returns3;
	}

	public String editCalendar(String id, String date, String schedule, String memo, String old) {
		String user_calendar = "calendar" + id;

		returns3 = "";

		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = sb.append("select date, schedule from ").append(user_calendar)
					.append(" where date = ? and schedule = ? ").toString();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setString(2, schedule);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				returns3 = "false";

			} else {
				sb = new StringBuilder();
				sql2 = sb.append("update ").append(user_calendar)
						.append(" set schedule = ?, memo = ? where schedule =?").toString();
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, schedule);
				pstmt2.setString(2, memo);
				pstmt2.setString(3, old);

				pstmt2.executeUpdate();

				returns3 = "done";
			}

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
		}
		return returns3;
	}

	public String loadSchedule(String sche_id) {
		returns = "";
		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from schedule where schedule_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sche_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				returns += rs.getString("schedule_name") + "\t" + rs.getDate("date") + "\t" + rs.getString("location")
						+ "\t" + rs.getString("participants") + "\t";

			}

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;

	}

	public String setDate(String sche_id, String date) {
		returns3 = "";

		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);

			sql = sb.append("update schedule set date = ? where schedule_id =?").toString();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setString(2, sche_id);

			pstmt.executeUpdate();

			returns3 = "done";

		} catch (

		Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
		}
		return returns3;
	}

	public String setLocation(String sche_id, String location) {
		returns3 = "";

		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);

			sql = sb.append("update schedule set location = ? where schedule_id =?").toString();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, location);
			pstmt.setString(2, sche_id);

			pstmt.executeUpdate();

			returns3 = "done";

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
		}
		return returns3;
	}

	public String modiScheduleName(String sche_id, String new_sche_name) {
		returns3 = "";

		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);

			sql = sb.append("update schedule set schedule_name = ? where schedule_id =?").toString();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new_sche_name);
			pstmt.setString(2, sche_id);

			pstmt.executeUpdate();

			returns3 = "done";

		} catch (

		Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
		}
		return returns3;
	}

	public String loadAllSchedule() {
		returns = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from schedule";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				returns += rs.getString("schedule_name") + "\t" + rs.getInt("schedule_id") + "\t";

			}

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;

	}

	public String deleteSchedule(String sche_id) {
		returns = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "delete from schedule where schedule_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sche_id);

			pstmt.executeUpdate();

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;

	}

	public String addSchedule(String id, String sche_name) {

		returns3 = "";
		int cnt = 0;
		try {
			sb = new StringBuilder();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			pstmt = conn.prepareStatement("select count(*) from schedule");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt(1);
				cnt++;

				pstmt2 = conn.prepareStatement("select schedule_id from schedule where schedule_id = ?");
				pstmt2.setInt(1, cnt);
				res = pstmt2.executeQuery();

				while (res.next()) {
					if (cnt == res.getInt(1)) {
						pstmt2 = conn.prepareStatement("select schedule_id from schedule where schedule_id = ?");
						pstmt2.setInt(1, cnt);
						res = pstmt2.executeQuery();
					}
					cnt++;
				}

			}
			sql = sb.append("insert into schedule(schedule_id, id, schedule_name)").append(" values(?,?,?) ")
					.toString();
			System.out.println("insert");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cnt);
			pstmt.setString(2, id);
			pstmt.setString(3, sche_name);

			pstmt.executeUpdate();

			returns3 = Integer.toString(cnt);

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
		}

		return returns3;
	}

}