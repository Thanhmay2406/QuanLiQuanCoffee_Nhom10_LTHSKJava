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
import Entity.SanPham;

/**
 * @description DAO cho ChiTietHoaDon, đã được refactor
 * @author: Van Long
 * @date: Nov 13, 2025
 * @version: 2.0
 */
public class ChiTietHoaDon_DAO {

	// DAO phụ
	private SanPham_DAO sanPham_DAO;
	// Lưu ý: Không nên khởi tạo HoaDon_DAO ở đây để tránh lỗi lặp vô hạn

	public ChiTietHoaDon_DAO() {
		sanPham_DAO = new SanPham_DAO();
	}

	/**
	 * === THAY ĐỔI: Thêm Chi Tiết (dùng cho Transaction) === Hàm này nhận
	 * Connection từ HoaDon_DAO để đảm bảo Giao dịch
	 */
	public boolean themChiTiet(ChiTietHoaDon ct, Connection con) throws SQLException {
		String sql = "INSERT INTO ChiTietHoaDon (maHoaDon, maSanPham, soLuong, donGia) VALUES (?, ?, ?, ?)";

		// Không tạo connection mới, dùng connection được truyền vào
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, ct.getHoaDon().getMaHoaDon());
			pstm.setString(2, ct.getSanPham().getMaSanPham());
			pstm.setInt(3, ct.getSoLuong());
			pstm.setBigDecimal(4, ct.getDonGia());

			return pstm.executeUpdate() > 0;
		}
		// Không catch, để `finally` của HoaDon_DAO xử lý rollback
	}

	/**
	 * === THAY ĐỔI: Lấy chi tiết theo mã Hóa Đơn === Hàm này phải "lắp ráp"
	 * (hydrate) các đối tượng
	 */
	public List<ChiTietHoaDon> layChiTietTheoMaHoaDon(String maHoaDon) {
		List<ChiTietHoaDon> ds = new ArrayList<>();
		String sql = "SELECT * FROM ChiTietHoaDon WHERE maHoaDon = ?";

		// Cần có HoaDon_DAO để lấy đối tượng HoaDon (nếu cần)
		// Nhưng chúng ta có thể truyền đối tượng HoaDon vào
		// Tuy nhiên, để đơn giản, ta chỉ cần set `null` và `maHoaDon`

		// Lấy connection mới vì đây là 1 truy vấn độc lập
		Connection con = ConnectDB.getInstance().getConnection();

		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maHoaDon);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				// 1. Lấy mã SanPham (String) từ CSDL
				String maSP = rs.getString("maSanPham");
				// 2. Dùng DAO phụ để lấy đối tượng SanPham đầy đủ
				SanPham sp = sanPham_DAO.timTheoMa(maSP); // Giả định bạn có hàm `timTheoMa`

				int soLuong = rs.getInt("soLuong");
				java.math.BigDecimal donGia = rs.getBigDecimal("donGia");

				// 3. Tạo đối tượng ChiTietHoaDon
				// Chúng ta không cần đối tượng HoaDon ở đây (để tránh lặp)
				// nên ta truyền `null`
				ChiTietHoaDon ct = new ChiTietHoaDon(null, sp, soLuong, donGia);
				ds.add(ct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}
}