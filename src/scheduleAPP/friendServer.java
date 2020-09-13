package scheduleAPP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class friendServer {
	private static friendServer instance = new friendServer();
	
	public static friendServer getInstance() {
		return instance;
	}
	
	public friendServer() {
		
	}
	
	String jdbc_url = "jdbc:mysql://localhost:3306/example?characterEncoding=euckr&useUnicode=true&mysqlEncoding=euckr&useSSL=false&serverTimezone=Asia/Seoul";
	String dbId = "root"; // MySQL
	String dbPw = "hansung"; // MySQL password
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	ResultSet rs = null;
	String sql = "";
	String returns = "";
	StringBuilder sb = new StringBuilder();
	final int FRIEND_STATUS = 1;
	final int REQUEST_STATUS = 0; // 나에게 친구요청한 경우
	final int WAITING_STATUS = -1; // 내가 친구요청한 경우
	
	public String loadUser(String userId) {
		returns = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from user where id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				returns += rs.getString("name");
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
	
	public String loadOthers(String userId) {
		returns = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from user a left outer join friends_" + userId + " b"
					+ " on a.id = b.id"
					+ " where b.id is null";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				returns += rs.getString("name") + "\t";
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
	
	public String loadFriends(String userId) {
		returns = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from friends_" + userId + " where status=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, FRIEND_STATUS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				returns += rs.getString("name") + "\t";
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
	
	public String loadWaiters(String userId) {
		returns = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from friends_" + userId + " where status=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, REQUEST_STATUS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				returns += rs.getString("name") + "\t";
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

	public String loadAllUsers() {
		returns = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from user";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				returns += rs.getString("name") + "\t";
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
	
	public String friendAccept(String userId, String friendName) {
		returns = "";
		String friendId = ""; // 상대방 아이디
		String userName = ""; // 사용자 이름
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from user where name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, friendName);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				friendId += rs.getString("id");
			}
			
			sql = "update friends_" + userId + " set status = ? where id = ?"; // 사용자한테 추가
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, FRIEND_STATUS);
			pstmt.setString(2, friendId);
			pstmt.executeUpdate();
			
			sql = "select * from user where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				userName += rs.getString("name");
			}
			
			sql = "update friends_" + friendId + " set status = ? where id = ?"; // 상대방한테 추가
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, FRIEND_STATUS);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
			
			returns = "true";
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
	
	public String friendReject(String userId, String friendName) {
		returns = "";
		String friendId = ""; // 상대방 아이디
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from user where name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, friendName);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				friendId += rs.getString("id");
			}
			
			sql = "delete from friends_" + userId + " where name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, friendName);
			pstmt.executeUpdate();
			
			sql = "delete from friends_" + friendId + " where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.executeUpdate();
			
			returns = "true";
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
	
	public String friendRequest(String userId, String friendName) {
		returns = "";
		String friendId = ""; // 상대방 아이디
		String userName = ""; // 사용자 이름
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbc_url, dbId, dbPw);
			sql = "select * from user where name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, friendName);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				friendId += rs.getString("id");
			}
			
			sql = "insert into friends_" + userId + " values (?,?,?)"; // 상대방한테 추가
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, friendId);
			pstmt.setString(2, friendName);
			pstmt.setInt(3, WAITING_STATUS);
			pstmt.executeUpdate();
			
			sql = "select * from user where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				userName += rs.getString("name");
			}
			
			sql = "insert into friends_" + friendId + " values (?,?,?)"; // 상대방한테 추가
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userName);
			pstmt.setInt(3, REQUEST_STATUS);
			pstmt.executeUpdate();
			
			returns = "true";
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

}