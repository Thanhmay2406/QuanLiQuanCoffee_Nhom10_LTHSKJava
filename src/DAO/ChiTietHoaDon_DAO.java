/*
 * @ (#) ChiTietHoaDon_DAO.java   1.0     Oct 28, 2025
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
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import Entity.ChiTietHoaDon;
import Entity.HoaDon; // Cần để tạo đối tượng
import Entity.SanPham; // Cần để tạo đối tượng

public class ChiTietHoaDon_DAO {

	private Connection con;
	private SanPham_DAO sanPham_DAO;

	public ChiTietHoaDon_DAO() {
		con = ConnectDB.getInstance().getConnection();
		sanPham_DAO = new SanPham_DAO();
	}

	/**
	 * Lấy tất cả chi tiết của 1 hóa đơn
	 */
	public List<ChiTietHoaDon> layChiTietTheoMaHD(String maHD) {
		List<ChiTietHoaDon> dsChiTietHoaDon = new ArrayList<>();
		String sql = "SELECT * FROM ChiTietHoaDon WHERE maHoaDon = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maHD);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				HoaDon hd = new HoaDon();
				hd.setMaHoaDon(maHD);
				SanPham sp = sanPham_DAO.timTheoMa(rs.getString("maSanPham"));
				sp.setMaSanPham(rs.getString("maSanPham"));

				ChiTietHoaDon ct = new ChiTietHoaDon(hd, sp, rs.getInt("soLuong"), rs.getBigDecimal("donGia"));
				dsChiTietHoaDon.add(ct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsChiTietHoaDon;
	}

	/**
	 * Thêm một chi tiết hóa đơn (dùng cho Transaction của HoaDon_DAO)
	 */
	public boolean themChiTiet(ChiTietHoaDon ct, Connection transCon) {
		String sql = "INSERT INTO ChiTietHoaDon (maHoaDon, maSanPham, soLuong, donGia) VALUES (?, ?, ?, ?)";
		try (PreparedStatement pstm = transCon.prepareStatement(sql)) {
			pstm.setString(1, ct.getMaHoaDon());
			pstm.setString(2, ct.getMaSanPham());
			pstm.setInt(3, ct.getSoLuong());
			pstm.setBigDecimal(4, ct.getDonGia());

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean xoaChiTietHoaDon(String maHoaDon, String maSanPham) {
		String sql = "delete from ChiTietHoaDon where maHoaDon = ? and maSanPham = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maHoaDon);
			pstm.setString(2, maSanPham);
			return pstm.executeUpdate() > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
