package Entity;

import java.sql.Date;
import java.util.Objects;

public class KhuyenMai {
	private String maKM;
	private String tenKM;
	private double giaTriKM;
	private Date ngayBatDau;
	private Date ngayKetThuc;
	private int trangThai;
	public KhuyenMai(String maKM, String tenKM, double giaTriKM, Date ngayBatDau, Date ngayKetThuc, int trangThai) {
		super();
		this.maKM = maKM;
		this.tenKM = tenKM;
		this.giaTriKM = giaTriKM;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.trangThai = trangThai;
	}
	public String getMaKM() {
		return maKM;
	}
	public void setMaKM(String maKM) {
		this.maKM = maKM;
	}
	public String getTenKM() {
		return tenKM;
	}
	public void setTenKM(String tenKM) {
		this.tenKM = tenKM;
	}
	public double getGiaTriKM() {
		return giaTriKM;
	}
	public void setGiaTriKM(double giaTriKM) {
		this.giaTriKM = giaTriKM;
	}
	public Date getNgayBatDau() {
		return ngayBatDau;
	}
	public void setNgayBatDau(Date ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}
	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}
	public void setNgayKetThuc(Date ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}
	public int getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKM);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhuyenMai other = (KhuyenMai) obj;
		return Objects.equals(maKM, other.maKM);
	}
}
