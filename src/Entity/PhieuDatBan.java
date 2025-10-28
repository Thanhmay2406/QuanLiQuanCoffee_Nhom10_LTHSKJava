package Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class PhieuDatBan {
	private String maPhieuDat;
	private LocalDate ngay;
	private LocalTime gioBatDau;
	private LocalTime gioKetThuc;
	private int soNguoi;
	private String ghiChu;
	private int trangThai;
	private String maNhanVien;

	public PhieuDatBan() {
	}

	public PhieuDatBan(String maPhieuDat, LocalDate ngay, LocalTime gioBatDau, LocalTime gioKetThuc, 
			int soNguoi, String ghiChu, int trangThai, String maNhanVien) {
		super();
		setMaPhieuDat(maPhieuDat);
		setNgay(ngay);
		setGioBatDau(gioBatDau);
		setGioKetThuc(gioKetThuc);
		setSoNguoi(soNguoi);
		setGhiChu(ghiChu);
		setTrangThai(trangThai);
		setMaNhanVien(maNhanVien);
	}

	public String getMaPhieuDat() {
		return maPhieuDat;
	}

	public void setMaPhieuDat(String maPhieuDat) {
		if (maPhieuDat == null || !maPhieuDat.matches("^PDB\\d{3,}$"))
			throw new IllegalArgumentException("Mã phiếu đặt không hợp lệ (phải có dạng PDBxxx)");
		this.maPhieuDat = maPhieuDat;
	}

	public LocalDate getNgay() {
		return ngay;
	}

	public void setNgay(LocalDate ngay) {
		if (ngay == null)
			throw new IllegalArgumentException("Ngày không được để trống");
		this.ngay = ngay;
	}

	public LocalTime getGioBatDau() {
		return gioBatDau;
	}

	public void setGioBatDau(LocalTime gioBatDau) {
		if (gioBatDau == null)
			throw new IllegalArgumentException("Giờ bắt đầu không được để trống");
		this.gioBatDau = gioBatDau;
	}

	public LocalTime getGioKetThuc() {
		return gioKetThuc;
	}

	public void setGioKetThuc(LocalTime gioKetThuc) {
		if (gioKetThuc == null)
			throw new IllegalArgumentException("Giờ kết thúc không được để trống");
		this.gioKetThuc = gioKetThuc;
	}

	public int getSoNguoi() {
		return soNguoi;
	}

	public void setSoNguoi(int soNguoi) {
		if (soNguoi <= 0)
			throw new IllegalArgumentException("Số người phải lớn hơn 0");
		this.soNguoi = soNguoi;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = (ghiChu == null) ? "" : ghiChu.trim();
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		if (trangThai < 0 || trangThai > 2) //  0 = đã hủy, 1 = đang chờ, 2 = hoàn thành
			throw new IllegalArgumentException("Trạng thái không hợp lệ");
		this.trangThai = trangThai;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		if (maNhanVien == null || !maNhanVien.matches("^NV\\d{3,}$"))
			throw new IllegalArgumentException("Mã nhân viên không hợp lệ (phải có dạng NVxxx)");
		this.maNhanVien = maNhanVien;
	}

	

	public void capNhatThongTin(LocalDate ngayMoi, int soNguoiMoi, LocalTime gioBatDauMoi) {
		if (ngayMoi != null) setNgay(ngayMoi);
		if (soNguoiMoi > 0) setSoNguoi(soNguoiMoi);
		if (gioBatDauMoi != null) setGioBatDau(gioBatDauMoi);
	}


	public void huyPhieu() {
		setTrangThai(0); // 0 = đã hủy
	}

	public void nhanBan() {
		setTrangThai(2); // 2 = hoàn thành
	}

	@Override
	public int hashCode() {
		return Objects.hash(maPhieuDat);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhieuDatBan other = (PhieuDatBan) obj;
		return Objects.equals(maPhieuDat, other.maPhieuDat) ;
	}

}
