package Entity;

import java.time.LocalDate;
import java.util.Objects;

public class KhachHang {
    private String maKhachHang;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private double diemTichLuy;
    private LocalDate ngayDangKy;

    public KhachHang() {
    }

    public KhachHang(String maKhachHang, String hoTen, String email, String soDienThoai, double diemTichLuy,
                     LocalDate ngayDangKy) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diemTichLuy = diemTichLuy;
        this.ngayDangKy = ngayDangKy;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public double getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(double diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public LocalDate getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(LocalDate ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }
    public void tichDiem() {
    	
    }
    @Override
	public int hashCode() {
		return Objects.hash(maKhachHang);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKhachHang, other.maKhachHang) ;
	}
}
