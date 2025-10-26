package Entity;

import java.util.Objects;

public class PhuongThucThanhToan {
    private String maPTTT;
    private String tenPTTT;
    private String moTa;

    public PhuongThucThanhToan() {
    }

    public PhuongThucThanhToan(String maPTTT, String tenPTTT, String moTa) {
        this.maPTTT = maPTTT;
        this.tenPTTT = tenPTTT;
        this.moTa = moTa;
    }

    public String getMaPTTT() {
        return maPTTT;
    }

    public void setMaPTTT(String maPTTT) {
        this.maPTTT = maPTTT;
    }

    public String getTenPTTT() {
        return tenPTTT;
    }

    public void setTenPTTT(String tenPTTT) {
        this.tenPTTT = tenPTTT;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    @Override
	public int hashCode() {
		return Objects.hash(maPTTT);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhuongThucThanhToan other = (PhuongThucThanhToan) obj;
		return Objects.equals(maPTTT, other.maPTTT) ;
	}
}
