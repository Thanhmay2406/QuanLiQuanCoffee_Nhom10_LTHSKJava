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

import java.time.LocalDate;
import java.util.Objects;

public class Ban {
	private String maBan;
	private String tenBan;
	private int soLuongChoNgoi;
	private String khuVuc;
	private int trangThai;
	private String ghiChu;

	public Ban(String maBan, String tenBan, int soLuongChoNgoi, String khuVuc, int trangThai, String ghiChu) {
		super();
		if (maBan == null || maBan.isEmpty())
			throw new IllegalArgumentException("Mã bàn không được rỗng");
		if (tenBan == null || tenBan.isEmpty())
			throw new IllegalArgumentException("Tên bàn không được rỗng");
		if (soLuongChoNgoi <= 0)
			throw new IllegalArgumentException("Số lượng chỗ ngồi phải lớn hơn 0");
		if (khuVuc == null || khuVuc.isEmpty())
			throw new IllegalArgumentException("Khu vực bàn không được rỗng");

		this.maBan = maBan;
		this.tenBan = tenBan;
		this.soLuongChoNgoi = soLuongChoNgoi;
		this.khuVuc = khuVuc;
		this.trangThai = trangThai;
		this.ghiChu = ghiChu;
	}

	public Ban() {
		// TODO Auto-generated constructor stub
		this("", "", 1, "", 0, "");
	}

	public String getMaBan() {
		return maBan;
	}

	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}

	public String getTenBan() {
		return tenBan;
	}

	public void setTenBan(String tenBan) {
		this.tenBan = tenBan;
	}

	public int getSoLuongChoNgoi() {
		return soLuongChoNgoi;
	}

	public void setSoLuongChoNgoi(int soLuongChoNgoi) {
		this.soLuongChoNgoi = soLuongChoNgoi;
	}

	public String getKhuVuc() {
		return khuVuc;
	}

	public void setKhuVuc(String khuVuc) {
		this.khuVuc = khuVuc;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maBan);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ban other = (Ban) obj;
		return Objects.equals(maBan, other.maBan);
	}

	public boolean datBan(KhachHang kh, LocalDate thoiGian) {
		if (kh == null) {
			return false;
		}
		// Bàn đã được đặt hoặc đang được sử dụng
		if (trangThai != 0) {
			return false;
		}
		this.trangThai = 1;
		this.ghiChu = "Khách" + kh.getHoTen() + "đặt lúc" + thoiGian;
		return true;
	}

	public void thayDoiTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public String toString() {
		return "Ban [maBan=" + maBan + ", tenBan=" + tenBan + ", soLuongChoNgoi=" + soLuongChoNgoi + ", khuVuc="
				+ khuVuc + ", trangThai=" + trangThai + ", ghiChu=" + ghiChu + "]";
	}

}
