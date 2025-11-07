/*
 * @ (#) PhieuDatBan_DAO.java   1.0     Oct 28, 2025
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import Entity.ChiTietDatBan;
import Entity.PhieuDatBan;

public class PhieuDatBan_DAO {
	private Connection con;
	private ChiTietDatBan_DAO ctDatBan_DAO;

	public PhieuDatBan_DAO() {
		con = ConnectDB.getInstance().getConnection();
		ctDatBan_DAO = new ChiTietDatBan_DAO();
	}

	public List<PhieuDatBan> layTatCa() {
		List<PhieuDatBan> ds = new ArrayList<>();
		String sql = "SELECT * FROM PhieuDatBan";
		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {
				PhieuDatBan pdb = new PhieuDatBan(rs.getString("maPhieuDat"), rs.getDate("ngayDat").toLocalDate(),
						rs.getTime("gioBatDau").toLocalTime(), rs.getTime("gioKetThuc").toLocalTime(),
						rs.getInt("soNguoi"), rs.getString("ghiChu"), rs.getInt("trangThai"),
						rs.getString("maKhachHang"), rs.getString("maNhanVien"));

				ds.add(pdb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	/**
	 * Thêm một phiếu đặt bàn mới (bao gồm cả chi tiết) Đây là một giao dịch
	 * (Transaction)
	 */
	public boolean themPhieuDatBan(PhieuDatBan pdb) {
		String sqlPDB = "INSERT INTO PhieuDatBan (maPhieuDat, ngay, gioBatDau, gioKetThuc, soNguoi, ghiChu, trangThai, maNhanVien) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			con.setAutoCommit(false); // Bắt đầu Transaction

			// 1. Thêm Phiếu Đặt Bàn
			try (PreparedStatement pstm = con.prepareStatement(sqlPDB)) {
				pstm.setString(1, pdb.getMaPhieuDat());
				pstm.setDate(2, java.sql.Date.valueOf(pdb.getngayDat()));
				pstm.setTime(3, java.sql.Time.valueOf(pdb.getGioBatDau()));
				pstm.setTime(4, java.sql.Time.valueOf(pdb.getGioKetThuc()));
				pstm.setInt(5, pdb.getSoNguoi());
				pstm.setString(6, pdb.getGhiChu());
				pstm.setInt(7, pdb.getTrangThai());
				pstm.setString(8, pdb.getMaKhachHang());
				pstm.setString(9, pdb.getMaNhanVien());
				pstm.executeUpdate();
			}

			// 2. Thêm Chi Tiết Đặt Bàn
			for (ChiTietDatBan ct : pdb.getDsChiTiet()) {
				ct.setPhieuDatBan(pdb);

				if (!ctDatBan_DAO.themChiTiet(ct, con)) {
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

	public ArrayList<PhieuDatBan> layPhieuDatBanTheoNgay(LocalDate ngay) {
		String sql = "select * from PhieuDatBan where maKhachHang = ?";
		ArrayList<PhieuDatBan> dsPDB = new ArrayList<PhieuDatBan>();
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setDate(1, java.sql.Date.valueOf(ngay));
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				PhieuDatBan pdb = new PhieuDatBan(rs.getString("maPhieuDatBan"), rs.getDate("ngayDat").toLocalDate(),
						rs.getTime("gioBatDau").toLocalTime(), rs.getTime("gioKetThuc").toLocalTime(),
						rs.getInt("soNguoi"), rs.getString("ghiChu"), rs.getInt("trangThai"),
						rs.getString("maKhachHang"), rs.getString("maNhanVien"));
				dsPDB.add(pdb);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPDB;
	}

	public boolean xoaPhieuDatBan(String maPhieuDatBan) {
		String sql = "delete from PhieuDatBan where maPhieuDatBan = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maPhieuDatBan);
			return pstm.executeUpdate() > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	// ... Thêm các hàm capNhatTrangThaiPhieu, xoaPhieu (hủy phiếu)...
}
