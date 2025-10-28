/*
 * @ (#) ChiTietDatBan.java   1.0     Oct 27, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package Entity;
/*
* @description
* @author: Van Long
* @date: Oct 27, 2025
* @version: 1.0
*/

public class ChiTietDatBan {
	private PhieuDatBan phieuDatBan;
	private Ban ban;
	private String ghiChu;

	public ChiTietDatBan(PhieuDatBan phieuDatBan, Ban ban, String ghiChu) {
		super();
		this.phieuDatBan = phieuDatBan;
		this.ban = ban;
		this.ghiChu = ghiChu;
	}

	public ChiTietDatBan() {
		this(null, null, "");
	}

	public PhieuDatBan getPhieuDatBan() {
		return phieuDatBan;
	}

	public void setPhieuDatBan(PhieuDatBan phieuDatBan) {
		this.phieuDatBan = phieuDatBan;
	}

	public Ban getBan() {
		return ban;
	}

	public void setBan(Ban ban) {
		this.ban = ban;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public void capNhatGhiChu(String ghiChu) {

	}

}
