/*
 * @ (#) DanhSachBan.java   1.0     Oct 25, 2025
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

public class DanhSachBan {

	private ArrayList<Ban> dsBan;

	public DanhSachBan() {
		// TODO Auto-generated constructor stub
		dsBan = new ArrayList<Ban>();
	}

	public boolean themBan(Ban ban) {
		if (dsBan.contains(ban)) {
			return false;
		}
		dsBan.add(ban);
		return true;
	}

	public boolean xoaBan(String maBan) {
		if (maBan == null || maBan.isEmpty()) {
			throw new IllegalArgumentException("Vui lòng nhập mã nhân viên để xóa");
		}
		return dsBan.removeIf(ban -> ban.getMaBan().equalsIgnoreCase(maBan));
	}

	public boolean suaBan(Ban b) {
		if (!dsBan.contains(b)) {
			return false;
		}
		Ban ban = dsBan.stream().filter(bn -> bn.getMaBan().equalsIgnoreCase(b.getMaBan())).findFirst().orElse(null);
		ban.setTenBan(b.getTenBan());
		ban.setKhuVuc(b.getKhuVuc());
		ban.setSoLuongChoNgoi(b.getSoLuongChoNgoi());
		ban.setGhiChu(b.getGhiChu());
		ban.setTrangThai(b.getTrangThai());
		return true;
	}

	public Ban timTheoMaBan(String maBan) {
		if (maBan == null || maBan.isEmpty()) {
			return null;
		}
		return dsBan.stream().filter(ban1 -> ban1.getMaBan().equalsIgnoreCase(maBan)).findFirst().orElse(null);
	}

	public Ban timTheoTenBan(String tenBan) {
		if (tenBan == null || tenBan.isEmpty()) {
			return null;
		}
		return dsBan.stream().filter(ban1 -> ban1.getTenBan().equalsIgnoreCase(tenBan)).findFirst().orElse(null);
	}

	public ArrayList<Ban> getDanhSachBan() {
		return dsBan;
	}

	public void clear() {
		dsBan.clear();
	}

}
