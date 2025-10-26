/*
 * @ (#) Ban.java   1.0     Oct 25, 2025
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
		setHoaDon(hoaDon);
		setSanPham(sanPham);
		setSoLuong(soLuong);
		setDonGia(donGia);
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
		if (hoaDon == null) {
			throw new IllegalArgumentException("Hóa đơn không được null");
		}
		this.hoaDon = hoaDon;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		if (sanPham == null) {
			throw new IllegalArgumentException("Sản phẩm không được null");
		}
		this.sanPham = sanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		if (soLuong <= 0)
			throw new IllegalArgumentException("Số lượng phải > 0");
		this.soLuong = soLuong;
	}

	public BigDecimal getDonGia() {
		return donGia;
	}

	public String getTenSanPham() {
		return sanPham.getTenSanPham();
	}

	public void setDonGia(BigDecimal donGia) {
		if (donGia == null || donGia.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("Đơn giá phải > 0");
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