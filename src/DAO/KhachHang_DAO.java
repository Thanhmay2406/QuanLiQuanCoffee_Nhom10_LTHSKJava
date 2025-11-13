/*
 * @ (#) KhachHang_DAO.java   1.0     Oct 28, 2025
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
import Entity.KhachHang;

public class KhachHang_DAO {
	private Connection con;

	public KhachHang_DAO() {
		con = ConnectDB.getInstance().getConnection();
	}

	public List<KhachHang> layTatCa() {
		List<KhachHang> ds = new ArrayList<>();
		try {
			String sql = "SELECT * FROM KhachHang";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				KhachHang kh = new KhachHang(rs.getString("maKhachHang"), rs.getString("hoTen"),
						rs.getString("soDienThoai"), rs.getString("email"), rs.getString("diaChi"),
						rs.getDouble("diemTichLuy"), rs.getDate("ngayDangKy").toLocalDate());
				ds.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public boolean themKhachHang(KhachHang kh) {
		String sql = "INSERT INTO KhachHang (maKhachHang, hoTen, soDienThoai, email, diemTichLuy, ngayDangKy) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, kh.getMaKhachHang());
			pstm.setString(2, kh.getHoTen());
			pstm.setString(3, kh.getSoDienThoai());
			pstm.setString(4, kh.getEmail());
			pstm.setString(5, kh.getDiaChi());
			pstm.setDouble(6, kh.getDiemTichLuy());
			pstm.setDate(7, java.sql.Date.valueOf(kh.getNgayDangKy()));

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean xoaKhachHang(String maKH) {
		String sql = "DELETE FROM KhachHang WHERE maKhachHang = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maKH.trim());
			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean capNhatKhachHang(KhachHang kh) {
		String sql = "UPDATE KhachHang SET hoTen = ?, soDienThoai = ?, email = ?, diemTichLuy = ? WHERE maKhachHang = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, kh.getHoTen());
			pstm.setString(2, kh.getSoDienThoai());
			pstm.setString(3, kh.getEmail());
			pstm.setString(4, kh.getDiaChi());
			pstm.setDouble(5, kh.getDiemTichLuy());
			pstm.setString(6, kh.getMaKhachHang());

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public KhachHang timKhachHangTheoMaKH(String maKH) {
		String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maKH.trim());
			ResultSet rs = pstm.executeQuery();

			if (rs.next()) {
				return new KhachHang(rs.getString("maKhachHang"), rs.getString("hoTen"), rs.getString("soDienThoai"),
						rs.getString("email"), rs.getString("diaChi"), rs.getDouble("diemTichLuy"),
						rs.getDate("ngayDangKy").toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public KhachHang timKhachHangTheoSDT(String soDienThoai) {
		String sql = "SELECT * FROM KhachHang WHERE soDienThoai = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, soDienThoai.trim());
			ResultSet rs = pstm.executeQuery();

			if (rs.next()) {
				return new KhachHang(rs.getString("maKhachHang"), rs.getString("hoTen"), rs.getString("soDienThoai"),
						rs.getString("email"), rs.getString("diaChi"), rs.getDouble("diemTichLuy"),
						rs.getDate("ngayDangKy").toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getDiemTichLuyTheoMa(String soDienThoai) {
		String sql = "select diemTichLuy from KhachHang where soDienThoai = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, soDienThoai);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				return rs.getInt("diemTichLuy");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	public boolean capNhatDiemTichLuy(String maKhachHang, double diemTichLuyMoi) {
		String sql = "update KhachHang set diemTichLuy = ?  where maKhachHang = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setDouble(1, diemTichLuyMoi);
			pstm.setString(2, maKhachHang);
			return pstm.executeUpdate() > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
