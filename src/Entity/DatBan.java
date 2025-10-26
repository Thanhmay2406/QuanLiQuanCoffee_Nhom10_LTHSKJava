package Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class DatBan {
    private String maDatBan;
    private LocalDate ngay;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private int soNguoi;
    private String ghiChu;
    private int trangThai;

    public DatBan() {
    }

    public DatBan(String maDatBan, LocalDate ngay, LocalTime gioBatDau, LocalTime gioKetThuc, int soNguoi, String ghiChu, int trangThai) {
        this.maDatBan = maDatBan;
        this.ngay = ngay;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.soNguoi = soNguoi;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public String getMaDatBan() {
        return maDatBan;
    }

    public void setMaDatBan(String maDatBan) {
        this.maDatBan = maDatBan;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
    }

    public LocalTime getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(LocalTime gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public LocalTime getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(LocalTime gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    @Override
	public int hashCode() {
		return Objects.hash(maDatBan);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatBan other = (DatBan) obj;
		return Objects.equals(maDatBan, other.maDatBan) ;
	}
    
}
