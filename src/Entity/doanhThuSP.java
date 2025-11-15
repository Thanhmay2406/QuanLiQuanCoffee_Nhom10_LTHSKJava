package Entity;

public class doanhThuSP {
	private String ten;
	private int sl;
	private double tongTien;
	public doanhThuSP(String ten, int sl, double tongTien) {
		super();
		this.ten = ten;
		this.sl = sl;
		this.tongTien = tongTien;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public int getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	public double getTongTien() {
		return tongTien;
	}
	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}
	@Override
	public String toString() {
		return "doanhThuSP [ten=" + ten + ", sl=" + sl + ", tongTien=" + tongTien + "]";
	}
	
}
