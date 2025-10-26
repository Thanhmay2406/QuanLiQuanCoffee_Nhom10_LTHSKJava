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
	private int trangThai;

	private ArrayList<ChiTietHoaDon> dsChiTiet;

// Constructor cho việc lấy hóa đơn từ CSDL
	public HoaDon(String maHoaDon, LocalDate ngayTao, String ghiChu, int trangThai) {
		setMaHoaDon(maHoaDon);
		this.ngayTao = ngayTao;
		this.ghiChu = ghiChu;
		this.trangThai = trangThai;
		this.dsChiTiet = new ArrayList<>();
	}

//Constructor cho việc tạo hóa đơn mới (chưa có mã)
	public HoaDon(LocalDate ngayTao, String ghiChu, int trangThai) {
		this.maHoaDon = null; // Chấp nhận null khi TẠO MỚI
		this.ngayTao = ngayTao;
		this.ghiChu = ghiChu;
		this.trangThai = trangThai;
		this.dsChiTiet = new ArrayList<>();
	}

	public HoaDon() {
		this("", LocalDate.now(), "", 0);
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		if (maHoaDon == null || maHoaDon.isEmpty())
			throw new IllegalArgumentException("Mã hóa đơn không được rỗng");
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

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public ArrayList<ChiTietHoaDon> getDsChiTiet() {
		return dsChiTiet;
	}

	public String xuatHoaDon() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n====== HÓA ĐƠN THANH TOÁN ======\n");
		sb.append("Mã hóa đơn: ").append(maHoaDon).append("\n");
		sb.append("Ngày tạo: ").append(ngayTao).append("\n");
		sb.append("Trạng thái: ").append(trangThai == 1 ? "Đã thanh toán" : "Chưa thanh toán").append("\n");
		sb.append("--------------------------------\n");
		sb.append(String.format("%-20s %-8s %-12s %-12s\n", "Sản phẩm", "SL", "Đơn giá", "Thành tiền"));
		sb.append("--------------------------------\n");

		BigDecimal tongTien = BigDecimal.ZERO;

		for (ChiTietHoaDon ct : dsChiTiet) {
			sb.append(String.format("%-20s %-8d %-12s %-12s\n", ct.getTenSanPham(), ct.getSoLuong(),
					ct.getDonGia().toString(), ct.tinhThanhTien().toString()));

			tongTien = tongTien.add(ct.tinhThanhTien());
		}

		sb.append("--------------------------------\n");
		sb.append("TỔNG TIỀN: ").append(tongTien.toString()).append(" VND\n");
		sb.append("================================\n");

		return sb.toString();
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
		sb.append("Trạng thái: ").append(trangThai == 1 ? "Đã thanh toán" : "Chưa thanh toán").append("\n");
		sb.append("Ghi chú: ").append(ghiChu == null || ghiChu.isEmpty() ? "Không có" : ghiChu).append("\n");
		sb.append("============================\n");
		return sb.toString();
	}
}