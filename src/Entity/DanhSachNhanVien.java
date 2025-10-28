/*
 * @ (#) DanhSachNhanVien.java   1.0     Oct 25, 2025
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

import java.util.ArrayList;

public class DanhSachNhanVien {
	private ArrayList<NhanVien> dsNV;

	public DanhSachNhanVien() {
		// TODO Auto-generated constructor stub
		dsNV = new ArrayList<NhanVien>();
	}

	public boolean themNhanVien(NhanVien nv) {
		if (dsNV.contains(nv)) {
			return false;
		}
		dsNV.add(nv);
		return true;
	}

	public boolean xoaNhanVien(String maNhanVien) {
		if (maNhanVien == null || maNhanVien.isEmpty()) {
			throw new IllegalArgumentException("Vui lòng nhập mã nhân viên để xóa");
		}
		return dsNV.removeIf(nv -> nv.getMaNhanVien().equalsIgnoreCase(maNhanVien));
	}

	public boolean suaNhanVien(NhanVien nv) {
//		Nhân viên chưa tồn tại
		if (!dsNV.contains(nv)) {
			return false;
		}
		NhanVien nhanVien = dsNV.stream().filter(nv1 -> nv1.getMaNhanVien().equalsIgnoreCase(nv.getMaNhanVien()))
				.findFirst().orElse(null);
		nhanVien.setHoTen(nv.getHoTen());
		nhanVien.setNgayVaoLam(nv.getNgayVaoLam());
		nhanVien.setEmail(nv.getEmail());
		nhanVien.setSoDienThoai(nv.getSoDienThoai());
		nhanVien.setChucVu(nv.getChucVu());
		nhanVien.setTrangThai(nv.getTrangThai());
		return true;
	}

	public NhanVien timTheoMaNhanVien(String maNhanVien) {
		if (maNhanVien == null || maNhanVien.isEmpty()) {
			return null;
		}
		return dsNV.stream().filter(nv1 -> nv1.getMaNhanVien().equalsIgnoreCase(maNhanVien)).findFirst().orElse(null);
	}

	public NhanVien timTheoTenNhanVien(String tenNhanVien) {
		if (tenNhanVien == null || tenNhanVien.isEmpty()) {
			return null;
		}
		return dsNV.stream().filter(nv1 -> nv1.getHoTen().equalsIgnoreCase(tenNhanVien)).findFirst().orElse(null);
	}

	public ArrayList<NhanVien> getDanhSachNhanVien() {
		return dsNV;
	}

	public void clear() {
		dsNV.clear();
	}

}
