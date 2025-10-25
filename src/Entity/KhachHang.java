package Entity;

import java.time.LocalDate;

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

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKhachHang='" + maKhachHang + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", diemTichLuy=" + diemTichLuy +
                ", ngayDangKy=" + ngayDangKy +
                '}';
    }
}
