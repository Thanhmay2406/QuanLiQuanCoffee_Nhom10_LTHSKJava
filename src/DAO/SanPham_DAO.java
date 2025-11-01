/*
 * @ (#) SanPham_DAO.java   1.0     Oct 28, 2025
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
import Entity.SanPham;

public class SanPham_DAO {
	private Connection con;

	public SanPham_DAO() {
		con = ConnectDB.getInstance().getConnection();
	}

	public List<SanPham> layTatCa() {
		List<SanPham> ds = new ArrayList<>();
		String sql = "SELECT * FROM SanPham";
		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {
				SanPham sp = new SanPham(rs.getString("maSanPham"), rs.getString("tenSanPham"),
						rs.getString("donViTinh"), rs.getBigDecimal("gia"), rs.getString("hinhAnh"),
						rs.getInt("trangThai"), rs.getString("maLoaiSP"));
				ds.add(sp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public boolean themSanPham(SanPham sp) {
		String sql = "INSERT INTO SanPham (maSanPham, tenSanPham, donViTinh, gia, hinhAnh, trangThai) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, sp.getMaSanPham());
			pstm.setString(2, sp.getTenSanPham());
			pstm.setString(3, sp.getDonViTinh());
			pstm.setBigDecimal(4, sp.getGia());
			pstm.setString(5, sp.gethinhAnh());
			pstm.setInt(6, sp.getTrangThai());

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean capNhatSanPham(SanPham sp) {
		String sql = "UPDATE SanPham SET tenSanPham = ?, donViTinh = ?, gia = ?, hinhAnh = ?, trangThai = ? WHERE maSanPham = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, sp.getTenSanPham());
			pstm.setString(2, sp.getDonViTinh());
			pstm.setBigDecimal(3, sp.getGia());
			pstm.setString(4, sp.gethinhAnh());
			pstm.setInt(5, sp.getTrangThai());
			pstm.setString(6, sp.getMaSanPham());

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public SanPham timTheoMa(String maSP) {
		String sql = "SELECT * FROM SanPham WHERE maSanPham = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maSP);
			ResultSet rs = pstm.executeQuery();

			if (rs.next()) {
				return new SanPham(rs.getString("maSanPham"), rs.getString("tenSanPham"), rs.getString("donViTinh"),
						rs.getBigDecimal("gia"), rs.getString("hinhAnh"), rs.getInt("trangThai"),
						rs.getString("maLoaiSP"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean xoaBan(String maSanPham) {
		String sql = "delete from SanPham where maSanPham = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maSanPham);
			return pstm.executeUpdate() > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<SanPham> timTheoLoai(String maLoaiSP) {
		ArrayList<SanPham> dsSP = new ArrayList<SanPham>();
		String sql = "select * from SanPham where maLoaiSP = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maLoaiSP);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				SanPham sp = new SanPham();
				sp.setMaSanPham(rs.getString("maSanPham"));
				sp.setTenSanPham(rs.getString("tenSanPham"));
				sp.setDonViTinh("donViTinh");
				sp.setGia(rs.getBigDecimal("gia"));
				sp.sethinhAnh("hinhAnh");
				sp.setTrangThai(rs.getInt("trangThai"));
				sp.setmaLoaiSP("maLoaiSP");
				dsSP.add(sp);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Không tìm theo loại được");
			e.printStackTrace();
		}

		return dsSP;
	}

	public SanPham timSanPhamTheoTen(String tenSanPham) {
		ArrayList<SanPham> dsSP = new ArrayList<SanPham>();
		String sql = "select * from SanPham where tenSanPham = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, tenSanPham);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				SanPham sp = new SanPham();
				sp.setMaSanPham(rs.getString("maSanPham"));
				sp.setTenSanPham(rs.getString("tenSanPham"));
				sp.setDonViTinh("donViTinh");
				sp.setGia(rs.getBigDecimal("gia"));
				sp.sethinhAnh("hinhAnh");
				sp.setTrangThai(rs.getInt("trangThai"));
				sp.setmaLoaiSP("maLoaiSP");
				dsSP.add(sp);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Không tìm theo loại được");
			e.printStackTrace();
		}
		return null;
	}
}