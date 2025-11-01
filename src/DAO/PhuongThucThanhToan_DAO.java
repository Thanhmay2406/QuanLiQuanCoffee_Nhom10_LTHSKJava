/*
 * @ (#) PhuongThucThanhToan_DAO.java   1.0     Oct 28, 2025
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import Entity.PhuongThucThanhToan;

public class PhuongThucThanhToan_DAO {
	private Connection con;

	public PhuongThucThanhToan_DAO() {
		con = ConnectDB.getInstance().getConnection();
	}

	public List<PhuongThucThanhToan> layTatCa() {
		List<PhuongThucThanhToan> ds = new ArrayList<>();
		String sql = "SELECT * FROM PhuongThucThanhToan WHERE trangThai = 1"; // Chỉ lấy PTTT đang hoạt động
		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {
				PhuongThucThanhToan pttt = new PhuongThucThanhToan(rs.getString("maPTTT"), rs.getString("tenPTTT"),
						rs.getInt("trangThai"), rs.getString("moTa"));
				ds.add(pttt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	// ... Thêm các hàm them, capNhat nếu dự án của bạn cho phép quản lý PTTT
}