package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.doanhThuSP;

public class ThongKe_DAO {
	private Connection con;

	public ThongKe_DAO() {
		con = ConnectDB.getInstance().getConnection();
	}
	
	public double tongDoanhThu() {
		String sql = "SELECT SUM(cthd.soLuong * cthd.donGia * (1 - ISNULL(km.phanTramGiam, 0))) AS TongDoanhThu "
				+ "FROM HoaDon hd "
				+ "INNER JOIN ChiTietHoaDon cthd ON hd.maHoaDon = cthd.maHoaDon "
				+ "LEFT JOIN KhuyenMai km ON hd.maKM = km.maKM "
				+ "WHERE YEAR(hd.ngayTao) = YEAR(GETDATE()) "
				+ "AND MONTH(hd.ngayTao) = MONTH(GETDATE()) "
				+ "AND hd.trangThaiThanhToan = 1 ";
		try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {	
			if(rs.next()) {
				return rs.getDouble("TongDoanhThu");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0.0;
	}
	
	public int soLuongSP() {
		String sql = "SELECT SUM(cthd.soLuong) AS TongSoLuongSanPham "
				+ "FROM HoaDon hd "
				+ "INNER JOIN ChiTietHoaDon cthd ON hd.maHoaDon = cthd.maHoaDon "
				+ "WHERE YEAR(hd.ngayTao) = YEAR(GETDATE()) "
				+ "AND MONTH(hd.ngayTao) = MONTH(GETDATE()) "
				+ "AND hd.trangThaiThanhToan = 1 ";
		try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {	
			if(rs.next()) {
				return rs.getInt("TongSoLuongSanPham");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	public String sanPhamBanChayNhat() {
		String sql = "SELECT TOP 1 sp.tenSanPham "
				+ "FROM HoaDon hd "
				+ "INNER JOIN ChiTietHoaDon cthd ON hd.maHoaDon = cthd.maHoaDon "
				+ "INNER JOIN SanPham sp ON cthd.maSanPham = sp.maSanPham "
				+ "WHERE YEAR(hd.ngayTao) = YEAR(GETDATE()) "
				+ "AND MONTH(hd.ngayTao) = MONTH(GETDATE()) "
				+ "AND hd.trangThaiThanhToan = 1 "
				+ "GROUP BY sp.maSanPham, sp.tenSanPham "
				+ "ORDER BY SUM(cthd.soLuong) DESC";
		try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {	
			if(rs.next()) {
				return rs.getString("tenSanPham");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "Không tìm thấy";
	}
	
	public ArrayList<doanhThuSP> DSDoanhThuSP() {
		ArrayList<doanhThuSP> arr = new ArrayList<doanhThuSP>();
		String sql = "SELECT "
				+ "sp.tenSanPham, "
				+ "SUM(cthd.soLuong) AS SoLuongBan, "
				+ "SUM(cthd.soLuong * cthd.donGia * (1 - ISNULL(km.phanTramGiam, 0))) AS TongTien "
				+ "FROM HoaDon hd "
				+ "INNER JOIN ChiTietHoaDon cthd ON hd.maHoaDon = cthd.maHoaDon "
				+ "INNER JOIN SanPham sp ON cthd.maSanPham = sp.maSanPham "
				+ "LEFT JOIN KhuyenMai km ON hd.maKM = km.maKM "
				+ "WHERE hd.trangThaiThanhToan = 1 "
				+ "GROUP BY sp.tenSanPham "
				+ "ORDER BY TongTien DESC";
		try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {	
			while(rs.next()) {
				doanhThuSP tmp = new doanhThuSP(
						rs.getString("tenSanPham"),
		                rs.getInt("SoLuongBan"),
		                rs.getDouble("TongTien")
				);
				arr.add(tmp);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return arr;
	}
}
