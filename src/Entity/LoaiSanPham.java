package Entity;

import java.util.Objects;

public class LoaiSanPham {
	private String maLoaiSP;
	private String tenLoai;
	private String moTa;
	private int trangThai;
	public LoaiSanPham() {
		this("", "", "", 0);
	}
	public LoaiSanPham(String maLoaiSP, String tenLoai, String moTa, int trangThai) {
		super();
		this.maLoaiSP = maLoaiSP;
		this.tenLoai = tenLoai;
		this.moTa = moTa;
		this.trangThai = trangThai;
	}
	public String getMaLoaiSP() {
		return maLoaiSP;
	}
	public void setMaLoaiSP(String maLoaiSP) {
		this.maLoaiSP = maLoaiSP;
	}
	public String getTenLoai() {
		return tenLoai;
	}
	public void setTenLoai(String tenLoai) {
		this.tenLoai = tenLoai;
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
	public void themSanPham() {
	}
	public void suaSanPham() {
	}
	@Override
	public int hashCode() {
		return Objects.hash(maLoaiSP);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoaiSanPham other = (LoaiSanPham) obj;
		return Objects.equals(maLoaiSP, other.maLoaiSP);
	}
}
