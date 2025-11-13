/*
 * @ (#) HoaDon.java   1.0     Oct 25, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package Entity;

/*
* @description
* @author: Van Long
* @date: Oct 25, 2025
* @version: 1.0
*/

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class HoaDon {
	private String maHoaDon;
	private LocalDate ngayTao;
	private String ghiChu;
	private int trangThaiThanhToan;
	private String maKhachHang;
	private String maNhanVien;
	private String maKM;
	private String maPTTT;

	private ArrayList<ChiTietHoaDon> dsChiTiet;

// Constructor cho việc lấy hóa đơn từ CSDL
	public HoaDon(String maHoaDon, LocalDate ngayTao, String ghiChu, int trangThaiThanhToan, String maKhachHang,
			String maNhanVien, String maKM, String maPTTT) {
		this.maHoaDon = maHoaDon;
		this.ngayTao = ngayTao;
		this.ghiChu = ghiChu;
		this.trangThaiThanhToan = trangThaiThanhToan;
		this.dsChiTiet = new ArrayList<>();
		this.maKhachHang = maKhachHang;
		this.maNhanVien = maNhanVien;
		this.maKM = maKM;
		this.maPTTT = maPTTT;
	}

//Constructor cho việc tạo hóa đơn mới (chưa có mã)
	public HoaDon(LocalDate ngayTao, String ghiChu, int trangThaiThanhToan, String maKhachHang, String maNhanVien,
			String maKM, String maPTTT) {
		this.maHoaDon = null;
		this.ngayTao = ngayTao;
		this.ghiChu = ghiChu;
		this.trangThaiThanhToan = trangThaiThanhToan;
		this.maKhachHang = maKhachHang;
		this.maNhanVien = maNhanVien;
		this.maKM = maKM;
		this.maPTTT = maPTTT;
		this.dsChiTiet = new ArrayList<>();

	}

	public HoaDon() {
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaKM() {
		return maKM;
	}

	public void setMaKM(String maKM) {
		this.maKM = maKM;
	}

	public String getMaPTTT() {
		return maPTTT;
	}

	public void setMaPTTT(String maPTTT) {
		this.maPTTT = maPTTT;
	}

	public void setDsChiTiet(ArrayList<ChiTietHoaDon> dsChiTiet) {
		this.dsChiTiet = dsChiTiet;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		if (maHoaDon == null || maHoaDon.isEmpty())
			throw new IllegalArgumentException("Mã hóa đơn không được rỗng");

		if (!maHoaDon.matches("^HD\\d{3}$"))
			throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx");

		this.maHoaDon = maHoaDon;
	}

	public LocalDate getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(LocalDate ngayTao) {
		this.ngayTao = ngayTao;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public int getTrangThaiThanhToan() {
		return trangThaiThanhToan;
	}

	public void setTrangThaiThanhToan(int trangThaiThanhToan) {
		this.trangThaiThanhToan = trangThaiThanhToan;
	}

	public ArrayList<ChiTietHoaDon> getDsChiTiet() {
		return dsChiTiet;
	}

	public boolean themChiTiet(SanPham sp, int soLuong) {
		if (sp == null || soLuong <= 0)
			return false;

		for (ChiTietHoaDon ct : dsChiTiet) {
			if (ct.getMaSanPham().equalsIgnoreCase(sp.getMaSanPham())) {
				ct.setSoLuong(ct.getSoLuong() + soLuong);
				return true;
			}
		}

		BigDecimal donGia = sp.getGia();
		ChiTietHoaDon ctHD = new ChiTietHoaDon(this, sp, soLuong, donGia);
		dsChiTiet.add(ctHD);
		return true;
	}

	public boolean xoaChiTiet(ChiTietHoaDon ctHD) {
		if (ctHD == null)
			return false;
		return dsChiTiet.remove(ctHD);
	}

	public BigDecimal tinhTongTien() {
		BigDecimal tong = BigDecimal.ZERO;

		for (ChiTietHoaDon ct : dsChiTiet) {
			tong = tong.add(ct.tinhThanhTien());
		}

		return tong;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("===== THÔNG TIN HÓA ĐƠN =====\n");
		sb.append("Mã hóa đơn: ").append(maHoaDon).append("\n");
		sb.append("Ngày tạo: ").append(ngayTao).append("\n");
		sb.append("Trạng thái: ").append(trangThaiThanhToan == 1 ? "Đã thanh toán" : "Chưa thanh toán").append("\n");
		sb.append("Ghi chú: ").append(ghiChu).append("\n");
		sb.append("------------------------------\n");

		if (dsChiTiet.isEmpty()) {
			sb.append("Không có chi tiết hóa đơn.\n");
		} else {
			for (ChiTietHoaDon ct : dsChiTiet) {
				sb.append(ct).append("\n");
			}
		}

		sb.append("============================\n");
		return sb.toString();
	}

}