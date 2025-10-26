/*
 * @ (#) ChiTietHoaDon.java   1.0     Oct 25, 2025
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

public class ChiTietHoaDon {
	private HoaDon hoaDon;
	private SanPham sanPham;
	private int soLuong;
	private BigDecimal donGia;

	public ChiTietHoaDon(HoaDon hoaDon, SanPham sanPham, int soLuong, BigDecimal donGia) {
		super();
		if (hoaDon == null || sanPham == null) {
			throw new IllegalArgumentException("Hóa đơn và Sản phẩm không được null");
		}
		if (soLuong <= 0)
			throw new IllegalArgumentException("Số lượng phải > 0");
		if (donGia == null || donGia.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("Đơn giá phải > 0");

		this.hoaDon = hoaDon;
		this.sanPham = sanPham;
		this.soLuong = soLuong;
		this.donGia = donGia;
	}

	public ChiTietHoaDon() {
		this.hoaDon = null;
		this.sanPham = null;
		this.soLuong = 1;
		this.donGia = BigDecimal.ZERO;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public BigDecimal getDonGia() {
		return donGia;
	}

	public String getTenSanPham() {
		return sanPham.getTenSanPham();
	}

	public void setDonGia(BigDecimal donGia) {
		this.donGia = donGia;
	}

	public String getMaSanPham() {
		return sanPham.getMaSanPham();
	}

	public String getMaHoaDon() {
		return hoaDon.getMaHoaDon();
	}

	public BigDecimal tinhThanhTien() {
		return donGia.multiply(new BigDecimal(soLuong));
	}

	@Override
	public String toString() {
		return "ChiTietHoaDon [hoaDon=" + hoaDon + ", sanPham=" + sanPham + ", soLuong=" + soLuong + ", donGia="
				+ donGia + "]";
	}
}
