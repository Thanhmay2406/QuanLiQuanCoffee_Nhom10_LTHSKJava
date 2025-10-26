package Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ThanhToan {
    private String maThanhToan;
    private BigDecimal soTien;
    private LocalDate ngayThanhToan;

    public ThanhToan() {
    }

    public ThanhToan(String maThanhToan, BigDecimal soTien, LocalDate ngayThanhToan) {
        this.maThanhToan = maThanhToan;
        this.soTien = soTien;
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getMaThanhToan() {
        return maThanhToan;
    }

    public void setMaThanhToan(String maThanhToan) {
        this.maThanhToan = maThanhToan;
    }

    public BigDecimal getSoTien() {
        return soTien;
    }

    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }

    public LocalDate getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDate ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    @Override
	public int hashCode() {
		return Objects.hash(maThanhToan);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThanhToan other = (ThanhToan) obj;
		return Objects.equals(maThanhToan, other.maThanhToan) ;
	}
}
