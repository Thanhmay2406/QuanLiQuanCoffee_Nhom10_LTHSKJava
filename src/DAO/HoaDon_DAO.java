/*
 * @ (#) HoaDon_DAO.java   1.0     Oct 28, 2025
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;

public class HoaDon_DAO {
	private Connection con;
	private ChiTietHoaDon_DAO ctHoaDon_DAO; // Một DAO gọi DAO khác

	public HoaDon_DAO() {
		con = ConnectDB.getInstance().getConnection();
		ctHoaDon_DAO = new ChiTietHoaDon_DAO(); // Khởi tạo DAO phụ
	}

	/**
	 * Lấy tất cả hóa đơn
	 */
	public List<HoaDon> layTatCa() {
		List<HoaDon> ds = new ArrayList<>();
		String sql = "SELECT * FROM HoaDon";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao").toLocalDate(),
						rs.getString("ghiChu"), rs.getInt("trangThaiThanhToan"), rs.getString("maKhachHang"),
						rs.getString("maNhanVien"), rs.getString("maKM"), rs.getString("maPTTT"));

				ds.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	/**
	 * Thêm một hóa đơn mới vào CSDL (bao gồm cả chi tiết) Đây là một giao dịch
	 */
	public boolean themHoaDon(HoaDon hd) {
		String sqlHD = "INSERT INTO HoaDon (maHoaDon, ngayTao, ghiChu, trangThaiThanhToan, maKhachHang, maNhanVien, maKM, maPTTT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			con.setAutoCommit(false);

			// 1. Thêm Hóa Đơn
			try (PreparedStatement pstmHD = con.prepareStatement(sqlHD)) {
				pstmHD.setString(1, hd.getMaHoaDon());
				pstmHD.setDate(2, java.sql.Date.valueOf(hd.getNgayTao()));
				pstmHD.setString(3, hd.getGhiChu());
				pstmHD.setInt(4, hd.getTrangThaiThanhToan());
				pstmHD.setString(5, hd.getMaKhachHang());
				pstmHD.setString(6, hd.getMaNhanVien());
				pstmHD.setString(7, hd.getMaKM());
				pstmHD.setString(8, hd.getMaPTTT());
				pstmHD.executeUpdate();
			}

			// 2. Thêm tất cả Chi Tiết Hóa Đơn
			for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
				if (!ctHoaDon_DAO.themChiTiet(ct, con)) {
					con.rollback();
					return false;
				}
			}

			con.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public HoaDon timHoaDonTheoMa(String maHoaDon) {
		if (maHoaDon == null || maHoaDon.isEmpty())
			return null;
		String sql = "select * from HoaDon where maHoaDon = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maHoaDon.trim());
			try (ResultSet rs = pstm.executeQuery()) {
				if (rs.next()) {
					HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao").toLocalDate(),
							rs.getString("ghiChu"), rs.getInt("trangThaiThanhToan"), rs.getString("maKhachHang"),
							rs.getString("maNhanVien"), rs.getString("maKM"), rs.getString("maPTTT"));
					return hd;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

//	Lấy hóa đơn theo ngày để thống kê doanh thu
	public ArrayList<HoaDon> layHoaDonTheoNgay(LocalDate tuNgay, LocalDate denNgay) {
		String sql = "Select * from HoaDon where ngayTao between ? and ?";
		List<HoaDon> dsHD = new ArrayList<HoaDon>();
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setDate(1, java.sql.Date.valueOf(tuNgay));
			pstm.setDate(2, java.sql.Date.valueOf(denNgay));
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao").toLocalDate(),
						rs.getString("ghiChu"), rs.getInt("trangThaiThanhToan"), rs.getString("maKhachHang"),
						rs.getString("maNhanVien"), rs.getString("maKM"), rs.getString("maPTTT"));
				dsHD.add(hd);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return (ArrayList<HoaDon>) dsHD;
	}

	public List<HoaDon> layTheoTrangThai(int trangThaiThanhToan) {
		List<HoaDon> ds = new ArrayList<>();
		String sql = "SELECT * FROM HoaDon WHERE trangThaiThanhToan = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setInt(1, trangThaiThanhToan);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao").toLocalDate(),
						rs.getString("ghiChu"), rs.getInt("trangThaiThanhToan"), rs.getString("maKhachHang"),
						rs.getString("maNhanVien"), rs.getString("maKM"), rs.getString("maPTTT"));
				ds.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

// Lấy lịch xử mua hàng của KhachHang
	public ArrayList<HoaDon> layHoaDonTheoMaKhachHang(String maKhachHang) {
		String sql = "select * from HoaDon where maKhachHang = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maKhachHang);
			ResultSet rs = pstm.executeQuery();
			List<HoaDon> dsHD = new ArrayList<HoaDon>();
			while (rs.next()) {
				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao").toLocalDate(),
						rs.getString("ghiChu"), rs.getInt("trangThaiThanhToan"), rs.getString("maKhachHang"),
						rs.getString("maNhanVien"), rs.getString("maKM"), rs.getString("maPTTT"));
				dsHD.add(hd);
			}
			return (ArrayList<HoaDon>) dsHD;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	// hàm giúp cho việc sinh mã hóa đơn
	public String getMaHoaDonCuoiCung() {
		String sql = "select top 1 maHoaDon from HoaDon order by maHoaDon DESC";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				return rs.getString("maHoaDon");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
