package com.signied.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.signied.dto.QnAReplyVO;
import com.signied.dto.QnAVO;
import com.signied.util.DBManager;

public class QnADAO {
	private QnADAO() {
	}
	
	public static QnADAO instance = new QnADAO();
	
	public static QnADAO getInstance() {
		return instance;
	}

	public int insertQnA(QnAVO vo) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into qna values (qna_seq.nextval, ?, ?, ?, sysdate , ?, ?)";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getQnaTitle());
			ps.setString(2, vo.getEmail());
			ps.setString(3, vo.getQnaContent());
			ps.setString(4, vo.getQnaPwd());
			ps.setString(5, vo.getQnaCategory());
			
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, ps);
		}
		
		return result;
	}

	public List<QnAVO> getAllQnAList() {
		List<QnAVO> list = new ArrayList<>();
		QnAVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from qna order by qnanum desc";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				vo = new QnAVO();
				vo.setQnaNum(rs.getInt("qnaNum"));
				vo.setQnaTitle(rs.getString("QNATITLE"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setQnaContent(rs.getString("QNACONTENT"));
				vo.setQnaRegidate(rs.getTimestamp("QNAREGIDATE"));
				vo.setQnaPwd(rs.getString("QNAPWD"));
				vo.setQnaCategory(rs.getString("QNACATEGORY"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, ps, rs);
		}
		return list;
	}

	public QnAVO selectOneByNum(int num) {
		QnAVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from qna where qnanum = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				vo = new QnAVO();
				vo.setQnaNum(rs.getInt("qnaNum"));
				vo.setQnaTitle(rs.getString("QNATITLE"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setQnaContent(rs.getString("QNACONTENT"));
				vo.setQnaRegidate(rs.getTimestamp("QNAREGIDATE"));
				vo.setQnaPwd(rs.getString("QNAPWD"));
				vo.setQnaCategory(rs.getString("QNACATEGORY"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, ps, rs);
		}
		
		return vo;
	}

	public int delteQnA(int num) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from qna where qnanum = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, ps);
		}
		
		return result;
	}

	public int insertReply(QnAReplyVO vo) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into qnareply values (qnaReply_seq.nextval, ? , sysdate, (select qnanum from qna where qnanum = ?))";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getReplyContent());
			ps.setInt(2, vo.getQnaNum());
			
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, ps);
		}
		
		return result;
	}

	public List<QnAReplyVO> getAllReplyList() {
		List<QnAReplyVO> list = new ArrayList<>();
		QnAReplyVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from qnareply order by qnanum desc";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				vo = new QnAReplyVO();
				vo.setQnaNum(rs.getInt("qnaNum"));
				vo.setReplyContent(rs.getString("replycontent"));
				vo.setReplyRegidate(rs.getTimestamp("replyregidate"));
				vo.setReplyNum(rs.getInt("replynum"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, ps, rs);
		}
		return list;
	}

	public int checkReply(int num) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select qnanum from qnareply where qnanum = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				result = 1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, ps, rs);
		}
		return result;
	}

	public QnAReplyVO selectOneByReply(int qnaNum) {
		QnAReplyVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from qnareply where qnanum = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, qnaNum);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				vo = new QnAReplyVO();
				vo.setQnaNum(rs.getInt("qnaNum"));
				vo.setReplyContent(rs.getString("replycontent"));
				vo.setReplyRegidate(rs.getTimestamp("replyregidate"));
				vo.setReplyNum(rs.getInt("replynum"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, ps, rs);
		}
		
		return vo;
	}
	
	
}