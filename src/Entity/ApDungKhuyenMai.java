package Entity;

import java.math.BigDecimal;
import java.util.Objects;

public class ApDungKhuyenMai {
	private String maHoaDon;
	private String maKM;
	private BigDecimal soTienGiam;
	private String ghiChu;
	public ApDungKhuyenMai(String maHoaDon, String maKM, BigDecimal soTienGiam, String ghiChu) {
		super();
		this.maHoaDon = maHoaDon;
		this.maKM = maKM;
		this.soTienGiam = soTienGiam;
		this.ghiChu = ghiChu;
	}
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public String getMaKM() {
		return maKM;
	}
	public void setMaKM(String maKM) {
		this.maKM = maKM;
	}
	public BigDecimal getSoTienGiam() {
		return soTienGiam;
	}
	public void setSoTienGiam(BigDecimal soTienGiam) {
		this.soTienGiam = soTienGiam;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public BigDecimal tinhTienGiam() {
		BigDecimal ans = new BigDecimal(0);
		return ans;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maHoaDon, maKM);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApDungKhuyenMai other = (ApDungKhuyenMai) obj;
		return Objects.equals(maHoaDon, other.maHoaDon) && Objects.equals(maKM, other.maKM);
	}
	
}
