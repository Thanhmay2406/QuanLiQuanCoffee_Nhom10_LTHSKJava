package Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhieuDatBan {
	private String maPhieuDat;
	private LocalDate ngayDat;
	private LocalTime gioBatDau;
	private LocalTime gioKetThuc;
	private int soNguoi;
	private String ghiChu;
	private int trangThai;
	private String maKhachHang;
	private String maNhanVien;
	private String soDienThoai;
	private List<ChiTietDatBan> dsChiTiet;

	public PhieuDatBan() {
		this.dsChiTiet = new ArrayList<>();
	}

	public PhieuDatBan(String maPhieuDat, LocalDate ngayDat, LocalTime gioBatDau, LocalTime gioKetThuc, int soNguoi,
			String ghiChu, int trangThai, String maKhachHang, String maNhanVien, String soDienThoai) {
		super();
		setMaPhieuDat(maPhieuDat);
		setngayDat(ngayDat);
		setGioBatDau(gioBatDau);
		setGioKetThuc(gioKetThuc);
		setSoNguoi(soNguoi);
		setGhiChu(ghiChu);
		setTrangThai(trangThai);
		setMaKhachHang(maKhachHang);
		setMaNhanVien(maNhanVien);
		setSoDienThoai(soDienThoai);
		this.dsChiTiet = new ArrayList<>();
	}

	public List<ChiTietDatBan> getDsChiTiet() {
		return dsChiTiet;
	}

	public void themChiTiet(ChiTietDatBan ct) {
		this.dsChiTiet.add(ct);
	}

	public void xoaChiTiet(ChiTietDatBan ct) {
		this.dsChiTiet.remove(ct);
	}

	public String getMaPhieuDat() {
		return maPhieuDat;
	}

	public void setMaPhieuDat(String maPhieuDat) {
		if (maPhieuDat == null || !maPhieuDat.matches("^PDB\\d{3,}$"))
			throw new IllegalArgumentException("Mã phiếu đặt không hợp lệ (phải có dạng PDBxxx)");
		this.maPhieuDat = maPhieuDat;
	}

	public LocalDate getngayDat() {
		return ngayDat;
	}

	public void setngayDat(LocalDate ngayDat) {
		if (ngayDat == null)
			throw new IllegalArgumentException("Ngày không được để trống");
		this.ngayDat = ngayDat;
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
		if (trangThai < 0 || trangThai > 2) // 0 = đã hủy, 1 = đang chờ, 2 = hoàn thành
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

	public LocalDate getNgayDat() {
		return ngayDat;
	}

	public void setNgayDat(LocalDate ngayDat) {
		if (ngayDat.isAfter(LocalDate.now()))
			throw new IllegalArgumentException("Ngày đặt phải trước ngày hôm nay");
		this.ngayDat = ngayDat;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		if (maKhachHang == null || !maKhachHang.matches("^KH\\d{3,}$"))
			throw new IllegalArgumentException("Mã khách hàng không hợp lệ (phải có dạng KHxxx)");
		this.maKhachHang = maKhachHang;
	}

	public void setDsChiTiet(List<ChiTietDatBan> dsChiTiet) {
		this.dsChiTiet = dsChiTiet;
	}

	public void capNhatThongTin(LocalDate ngayDatMoi, int soNguoiMoi, LocalTime gioBatDauMoi) {
		if (ngayDatMoi != null)
			setngayDat(ngayDatMoi);
		if (soNguoiMoi > 0)
			setSoNguoi(soNguoiMoi);
		if (gioBatDauMoi != null)
			setGioBatDau(gioBatDauMoi);
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
		return Objects.equals(maPhieuDat, other.maPhieuDat);
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		if (!soDienThoai.matches("^0\\d{9}$")) {
			throw new IllegalArgumentException("Số điện thoại phải bắt đầu là 0 và có 10 chữ số");
		}
		this.soDienThoai = soDienThoai;
	}

}
