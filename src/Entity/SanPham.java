/*
 * @ (#) SanPham.java   1.0     Oct 25, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package Entity;

import java.math.BigDecimal;
import java.util.Objects;

/*
* @description
* @author: Van Long
* @date: Oct 25, 2025
* @version: 1.0
*/

public class SanPham {
	private String maSanPham;
	private String tenSanPham;
	private String donViTinh;
	private BigDecimal gia;
	private String moTa;
	private int trangThai;

	public SanPham(String maSanPham, String tenSanPham, String donViTinh, BigDecimal gia, String moTa, int trangThai) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.donViTinh = donViTinh;
		this.gia = gia;
		this.moTa = moTa;
		this.trangThai = trangThai;
	}

	public SanPham() {
		super();
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	public BigDecimal getGia() {
		return gia;
	}

	public void setGia(BigDecimal gia) {
		this.gia = gia;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maSanPham);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SanPham other = (SanPham) obj;
		return Objects.equals(maSanPham, other.maSanPham);
	}

	@Override
	public String toString() {
		return "SanPham [maSanPham=" + maSanPham + ", tenSanPham=" + tenSanPham + ", donViTinh=" + donViTinh + ", gia="
				+ gia + ", moTa=" + moTa + ", trangThai=" + trangThai + "]";
	}

}
