///*
// * @ (#) HoaDon_DAO.java   1.0     Oct 28, 2025
// *
// * Copyright (c) 2025 IUH.
// * All rights reserved.
// */
//
//package DAO;
///*
//* @description
//* @author: Van Long
//* @date: Oct 28, 2025
//* @version: 1.0
//*/
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import ConnectDB.ConnectDB;
//import Entity.ChiTietHoaDon;
//import Entity.HoaDon;
//
//public class HoaDon_DAO {
//	private Connection con;
//	private ChiTietHoaDon_DAO ctHoaDon_DAO; // Một DAO gọi DAO khác
//
//	public HoaDon_DAO() {
//		con = ConnectDB.getInstance().getConnection();
//		ctHoaDon_DAO = new ChiTietHoaDon_DAO(); // Khởi tạo DAO phụ
//	}
//
//	/**
//	 * Lấy tất cả hóa đơn
//	 */
//	public List<HoaDon> layTatCa() {
//		List<HoaDon> ds = new ArrayList<>();
//		String sql = "SELECT * FROM HoaDon";
//		try (PreparedStatement pstm = con.prepareStatement(sql)) {
//			ResultSet rs = pstm.executeQuery();
//			while (rs.next()) {
//				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao").toLocalDate(),
//						rs.getString("ghiChu"), rs.getInt("trangThaiThanhToan"), rs.getString("maKhachHang"),
//						rs.getString("maNhanVien"), rs.getString("maKM"), rs.getString("maPTTT"));
//
//				ds.add(hd);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return ds;
//	}
//
//	/**
//	 * Thêm một hóa đơn mới vào CSDL (bao gồm cả chi tiết) Đây là một giao dịch
//	 */
//	public boolean themHoaDon(HoaDon hd) {
//		String sqlHD = "INSERT INTO HoaDon (maHoaDon, ngayTao, ghiChu, trangThaiThanhToan, maKhachHang, maNhanVien, maKM, maPTTT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//
//		try {
//			con.setAutoCommit(false);
//
//			// 1. Thêm Hóa Đơn
//			try (PreparedStatement pstmHD = con.prepareStatement(sqlHD)) {
//				pstmHD.setString(1, hd.getMaHoaDon());
//				pstmHD.setDate(2, java.sql.Date.valueOf(hd.getNgayTao()));
//				pstmHD.setString(3, hd.getGhiChu());
//				pstmHD.setInt(4, hd.getTrangThaiThanhToan());
//				pstmHD.setString(5, hd.getMaKhachHang());
//				pstmHD.setString(6, hd.getMaNhanVien());
//				pstmHD.setString(7, hd.getMaKM());
//				pstmHD.setString(8, hd.getMaPTTT());
//				pstmHD.executeUpdate();
//			}
//
//			// 2. Thêm tất cả Chi Tiết Hóa Đơn
//			for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
//				if (!ctHoaDon_DAO.themChiTiet(ct, con)) {
//					con.rollback();
//					return false;
//				}
//			}
//
//			con.commit();
//			return true;
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			return false;
//		} finally {
//			try {
//				con.setAutoCommit(true);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public HoaDon timHoaDonTheoMa(String maHoaDon) {
//		if (maHoaDon == null || maHoaDon.isEmpty())
//			return null;
//		String sql = "select * from HoaDon where maHoaDon = ?";
//		try (PreparedStatement pstm = con.prepareStatement(sql)) {
//			pstm.setString(1, maHoaDon.trim());
//			try (ResultSet rs = pstm.executeQuery()) {
//				if (rs.next()) {
//					HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao").toLocalDate(),
//							rs.getString("ghiChu"), rs.getInt("trangThaiThanhToan"), rs.getString("maKhachHang"),
//							rs.getString("maNhanVien"), rs.getString("maKM"), rs.getString("maPTTT"));
//					return hd;
//				}
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return null;
//	}
//
////	Lấy hóa đơn theo ngày để thống kê doanh thu
//	public ArrayList<HoaDon> layHoaDonTheoNgay(LocalDate tuNgay, LocalDate denNgay) {
//		String sql = "Select * from HoaDon where ngayTao between ? and ?";
//		List<HoaDon> dsHD = new ArrayList<HoaDon>();
//		try (PreparedStatement pstm = con.prepareStatement(sql)) {
//			pstm.setDate(1, java.sql.Date.valueOf(tuNgay));
//			pstm.setDate(2, java.sql.Date.valueOf(denNgay));
//			ResultSet rs = pstm.executeQuery();
//			while (rs.next()) {
//				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao").toLocalDate(),
//						rs.getString("ghiChu"), rs.getInt("trangThaiThanhToan"), rs.getString("maKhachHang"),
//						rs.getString("maNhanVien"), rs.getString("maKM"), rs.getString("maPTTT"));
//				dsHD.add(hd);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return (ArrayList<HoaDon>) dsHD;
//	}
//
//	public List<HoaDon> layTheoTrangThai(int trangThaiThanhToan) {
//		List<HoaDon> ds = new ArrayList<>();
//		String sql = "SELECT * FROM HoaDon WHERE trangThaiThanhToan = ?";
//		try (PreparedStatement pstm = con.prepareStatement(sql)) {
//			pstm.setInt(1, trangThaiThanhToan);
//			ResultSet rs = pstm.executeQuery();
//
//			while (rs.next()) {
//				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao").toLocalDate(),
//						rs.getString("ghiChu"), rs.getInt("trangThaiThanhToan"), rs.getString("maKhachHang"),
//						rs.getString("maNhanVien"), rs.getString("maKM"), rs.getString("maPTTT"));
//				ds.add(hd);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return ds;
//	}
//
//// Lấy lịch xử mua hàng của KhachHang
//	public ArrayList<HoaDon> layHoaDonTheoMaKhachHang(String maKhachHang) {
//		String sql = "select * from HoaDon where maKhachHang = ?";
//		try (PreparedStatement pstm = con.prepareStatement(sql)) {
//			pstm.setString(1, maKhachHang);
//			ResultSet rs = pstm.executeQuery();
//			List<HoaDon> dsHD = new ArrayList<HoaDon>();
//			while (rs.next()) {
//				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao").toLocalDate(),
//						rs.getString("ghiChu"), rs.getInt("trangThaiThanhToan"), rs.getString("maKhachHang"),
//						rs.getString("maNhanVien"), rs.getString("maKM"), rs.getString("maPTTT"));
//				dsHD.add(hd);
//			}
//			return (ArrayList<HoaDon>) dsHD;
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	// hàm giúp cho việc sinh mã hóa đơn
//	public String getMaHoaDonCuoiCung() {
//		String sql = "select top 1 maHoaDon from HoaDon order by maHoaDon DESC";
//		try (PreparedStatement pstm = con.prepareStatement(sql)) {
//			ResultSet rs = pstm.executeQuery();
//			if (rs.next()) {
//				return rs.getString("maHoaDon");
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//}

package DAO;

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
import Entity.KhachHang; // Thêm import
import Entity.KhuyenMai; // Thêm import
import Entity.NhanVien; // Thêm import
import Entity.PhuongThucThanhToan; // Thêm import

/**
 * @description Lớp DAO cho Hóa Đơn, đã được refactor
 * @author: Van Long
 * @date: Nov 13, 2025
 * @version: 2.0
 */
public class HoaDon_DAO {
	private Connection con;

	// === THAY ĐỔI 1: Thêm các DAO phụ ===
	// Một DAO sẽ cần các DAO khác để xây dựng đối tượng hoàn chỉnh
	private ChiTietHoaDon_DAO ctHoaDon_DAO;
	private KhachHang_DAO khachHang_DAO;
	private NhanVien_DAO nhanVien_DAO;
	private KhuyenMai_DAO khuyenMai_DAO;
	private PhuongThucThanhToan_DAO pttt_DAO;

	public HoaDon_DAO() {
		con = ConnectDB.getInstance().getConnection();

		// Khởi tạo tất cả các DAO cần thiết
		ctHoaDon_DAO = new ChiTietHoaDon_DAO();
		khachHang_DAO = new KhachHang_DAO();
		nhanVien_DAO = new NhanVien_DAO();
		khuyenMai_DAO = new KhuyenMai_DAO();
		pttt_DAO = new PhuongThucThanhToan_DAO();
	}

	/**
	 * === THAY ĐỔI 2: Hàm helper để xây dựng đối tượng HoaDon từ ResultSet === Hàm
	 * này không tải danh sách ChiTietHoaDon (để tránh N+1 query)
	 * 
	 * @param rs ResultSet đang trỏ tới một hàng
	 * @return Đối tượng HoaDon (chưa có dsChiTiet)
	 * @throws SQLException
	 */
	private HoaDon buildHoaDonFromResultSet(ResultSet rs) throws SQLException {
		String maHD = rs.getString("maHoaDon");
		LocalDate ngayTao = rs.getDate("ngayTao").toLocalDate();
		String ghiChu = rs.getString("ghiChu");

		// Lấy int từ CSDL và chuyển thành Enum
		int trangThai = (rs.getInt("trangThaiThanhToan"));

		// Lấy ID (String) và dùng DAO phụ để lấy đối tượng
		KhachHang kh = khachHang_DAO.timKhachHangTheoMaKH(rs.getString("maKhachHang"));
		NhanVien nv = nhanVien_DAO.timTheoMa(rs.getString("maNhanVien"));
		KhuyenMai km = khuyenMai_DAO.timTheoMaKhuyenMai(rs.getString("maKM"));
		PhuongThucThanhToan pttt = pttt_DAO.layPTTTTheoTen(rs.getString("maPTTT"));

		// Gọi constructor đã refactor
		HoaDon hd = new HoaDon(maHD, ngayTao, ghiChu, trangThai, kh, nv, km, pttt);

		return hd;
	}

	/**
	 * Lấy tất cả hóa đơn (không kèm chi tiết)
	 */
	public List<HoaDon> layTatCa() {
		List<HoaDon> ds = new ArrayList<>();
		String sql = "SELECT * FROM HoaDon";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				// Sử dụng hàm helper
				HoaDon hd = buildHoaDonFromResultSet(rs);
				ds.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	/**
	 * === THAY ĐỔI 3: Cập nhật hàm themHoaDon === Thêm một hóa đơn mới vào CSDL
	 * (bao gồm cả chi tiết)
	 */
	public boolean themHoaDon(HoaDon hd) {
		String sqlHD = "INSERT INTO HoaDon (maHoaDon, ngayTao, ghiChu, trangThaiThanhToan, maKhachHang, maNhanVien, maKM, maPTTT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			con.setAutoCommit(false); // Bắt đầu Giao dịch (Transaction)

			// 1. Thêm Hóa Đơn
			try (PreparedStatement pstmHD = con.prepareStatement(sqlHD)) {
				pstmHD.setString(1, hd.getMaHoaDon());
				pstmHD.setDate(2, java.sql.Date.valueOf(hd.getNgayTao()));
				pstmHD.setString(3, hd.getGhiChu());

				// Chuyển Enum thành int để lưu
				pstmHD.setInt(4, hd.getTrangThaiThanhToan());

				// Mở đối tượng để lấy ID, kiểm tra null
				pstmHD.setString(5, (hd.getKhachHang() != null) ? hd.getKhachHang().getMaKhachHang() : null);
				pstmHD.setString(6, (hd.getNhanVien() != null) ? hd.getNhanVien().getMaNhanVien() : null);
				pstmHD.setString(7, (hd.getKhuyenMai() != null) ? hd.getKhuyenMai().getMaKM() : null);
				pstmHD.setString(8,
						(hd.getPhuongThucThanhToan() != null) ? hd.getPhuongThucThanhToan().getMaPTTT() : null);

				pstmHD.executeUpdate();
			}

			// 2. Thêm tất cả Chi Tiết Hóa Đơn
			// Giả định: ctHoaDon_DAO.themChiTiet cũng đã được refactor
			for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
				// Quan trọng: Phải truyền `con` để `themChiTiet`
				// tham gia vào cùng một giao dịch
				if (!ctHoaDon_DAO.themChiTiet(ct, con)) {
					con.rollback(); // Nếu thêm chi tiết thất bại -> Hủy bỏ
					return false;
				}
			}

			con.commit(); // Hoàn tất giao dịch
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback(); // Hủy bỏ nếu có lỗi SQL
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				con.setAutoCommit(true); // Trả lại trạng thái mặc định
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * === THAY ĐỔI 4: Cập nhật hàm timHoaDonTheoMa === Tìm hóa đơn theo mã (bao gồm
	 * cả chi tiết)
	 */
	public HoaDon timHoaDonTheoMa(String maHoaDon) {
		if (maHoaDon == null || maHoaDon.isEmpty())
			return null;
		String sql = "select * from HoaDon where maHoaDon = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maHoaDon.trim());
			try (ResultSet rs = pstm.executeQuery()) {
				if (rs.next()) {
					// 1. Xây dựng đối tượng Hóa Đơn
					HoaDon hd = buildHoaDonFromResultSet(rs);

					// 2. Tải danh sách chi tiết
					// Giả định: ctHoaDon_DAO.layChiTietTheoMaHoaDon đã được refactor
					List<ChiTietHoaDon> dsCT = ctHoaDon_DAO.layChiTietTheoMaHoaDon(hd.getMaHoaDon());
					hd.setDsChiTiet(dsCT);

					return hd;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// === THAY ĐỔI 5: Cập nhật các hàm tìm kiếm khác ===
	// (Các hàm này chỉ trả về danh sách, không cần tải chi tiết)
	public ArrayList<HoaDon> layHoaDonTheoNgay(LocalDate tuNgay, LocalDate denNgay) {
		String sql = "Select * from HoaDon where ngayTao between ? and ?";
		ArrayList<HoaDon> dsHD = new ArrayList<HoaDon>();
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setDate(1, java.sql.Date.valueOf(tuNgay));
			pstm.setDate(2, java.sql.Date.valueOf(denNgay));
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				dsHD.add(buildHoaDonFromResultSet(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsHD;
	}

	public List<HoaDon> layTheoTrangThai(int trangThai) {
		List<HoaDon> ds = new ArrayList<>();
		String sql = "SELECT * FROM HoaDon WHERE trangThaiThanhToan = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setInt(1, trangThai);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				ds.add(buildHoaDonFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public ArrayList<HoaDon> layHoaDonTheoMaKhachHang(String maKhachHang) {
		String sql = "select * from HoaDon where maKhachHang = ?";
		ArrayList<HoaDon> dsHD = new ArrayList<HoaDon>();
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maKhachHang);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				dsHD.add(buildHoaDonFromResultSet(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsHD;
	}

	// (Hàm này không thay đổi)
	public String getMaHoaDonCuoiCung() {
		String sql = "select top 1 maHoaDon from HoaDon order by maHoaDon DESC";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				return rs.getString("maHoaDon");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
