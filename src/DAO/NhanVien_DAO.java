/*
 * @ (#) NhanVien_DAO.java   1.0     Oct 28, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package DAO;
/*
* @description
* @author: Van Long
* @date: Oct 28, 2025
* @version: 1.0
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import Entity.NhanVien;

public class NhanVien_DAO {
	private Connection con;

	public NhanVien_DAO() {
		con = ConnectDB.getInstance().getConnection();
	}

	public List<NhanVien> layTatCa() {
		List<NhanVien> ds = new ArrayList<>();
		String sql = "SELECT * FROM NhanVien";
		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {
				NhanVien nv = new NhanVien(rs.getString("maNhanVien"), rs.getString("hoTen"),
						rs.getString("soDienThoai"), rs.getString("email"), rs.getString("chucVu"),
						rs.getDate("ngayVaoLam").toLocalDate(), rs.getInt("trangThai"));
				ds.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public boolean themNhanVien(NhanVien nv) {
		String sql = "INSERT INTO NhanVien (maNhanVien, hoTen, soDienThoai, email, chucVu, ngayVaoLam, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, nv.getMaNhanVien());
			pstm.setString(2, nv.getHoTen());
			pstm.setString(3, nv.getSoDienThoai());
			pstm.setString(4, nv.getEmail());
			pstm.setString(5, nv.getChucVu());
			pstm.setDate(6, java.sql.Date.valueOf(nv.getNgayVaoLam()));
			pstm.setInt(7, nv.getTrangThai());

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean capNhatNhanVien(NhanVien nv) {
		String sql = "UPDATE NhanVien SET hoTen = ?, soDienThoai = ?, email = ?, chucVu = ?, ngayVaoLam = ?, trangThai = ? WHERE maNhanVien = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, nv.getHoTen());
			pstm.setString(2, nv.getSoDienThoai());
			pstm.setString(3, nv.getEmail());
			pstm.setString(4, nv.getChucVu());
			pstm.setDate(5, java.sql.Date.valueOf(nv.getNgayVaoLam()));
			pstm.setInt(6, nv.getTrangThai());
			pstm.setString(7, nv.getMaNhanVien());

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean kiemTraEmailTonTai(String email) {
		String sql = "select count(*) from NhanVien where email = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, email.trim());
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return false;
	}

	public boolean kiemTraSoDienThoaiTonTai(String soDienThoai) {
		String sql = "select count(*) from NhanVien where soDienThoai = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, soDienThoai.trim());
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return false;
	}

	public NhanVien timTheoMa(String maNV) {
		String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maNV.trim());
			ResultSet rs = pstm.executeQuery();

			if (rs.next()) {
				return new NhanVien(rs.getString("maNhanVien"), rs.getString("hoTen"), rs.getString("soDienThoai"),
						rs.getString("email"), rs.getString("chucVu"), rs.getDate("ngayVaoLam").toLocalDate(),
						rs.getInt("trangThai"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean xoaNhanVien(String maNhanVien) {
		String sql = "delete * from NhanVien where maNhanVien = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maNhanVien.trim());
			return pstm.executeUpdate() > 0;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Không xóa được nhân viên");
			e.printStackTrace();
		}
		return false;
	}

//	Lấy nhân viên theo email (dùng cho việc kiểm tra đăng nhập hoặc khôi phục tài khoản)
	public NhanVien layNhanVienTheoEmail(String email) {
		String sql = "select * from NhanVien where email = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, email.trim());
			ResultSet rs = pstm.executeQuery();
			NhanVien nv = new NhanVien(rs.getString("maNhanVien"), rs.getString("hoTen"), rs.getString("soDienThoai"),
					rs.getString("email"), rs.getString("chucVu"), rs.getDate("ngayVaoLam").toLocalDate(),
					rs.getInt("trangThai"));
			return nv;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
