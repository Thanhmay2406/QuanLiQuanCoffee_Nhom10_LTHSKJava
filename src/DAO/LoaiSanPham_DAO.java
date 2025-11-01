/*
 * @ (#) LoaiSanPham_DAO.java   1.0     Oct 28, 2025
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
import Entity.LoaiSanPham;

public class LoaiSanPham_DAO {
	private Connection con;

	public LoaiSanPham_DAO() {
		con = ConnectDB.getInstance().getConnection();
	}

	public List<LoaiSanPham> layTatCa() {
		List<LoaiSanPham> ds = new ArrayList<>();
		String sql = "SELECT * FROM LoaiSanPham";
		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {
				LoaiSanPham lsp = new LoaiSanPham(rs.getString("maLoaiSP"), rs.getString("tenLoai"),
						rs.getString("moTa"), rs.getInt("trangThai"));
				ds.add(lsp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public boolean themLoaiSanPham(LoaiSanPham lsp) {
		String sql = "INSERT INTO LoaiSanPham (maLoaiSP, tenLoai, moTa, trangThai) VALUES (?, ?, ?, ?)";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, lsp.getMaLoaiSP());
			pstm.setString(2, lsp.getTenLoai());
			pstm.setString(3, lsp.getMoTa());
			pstm.setInt(4, lsp.getTrangThai());

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// ... Thêm các hàm capNhat, timTheoMa nếu cần
}
