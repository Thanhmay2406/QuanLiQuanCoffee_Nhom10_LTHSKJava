package Entity;

import java.util.ArrayList;
import java.util.List;

public class DanhSachPhuongThucThanhToan {
    private List<PhuongThucThanhToan> dsPhuongThuc;

    public DanhSachPhuongThucThanhToan() {
        dsPhuongThuc = new ArrayList<>();
    }

    public boolean themPhuongThuc(PhuongThucThanhToan pt) {
        if (pt == null) return false;
        if (dsPhuongThuc.contains(pt)) return false;
        return dsPhuongThuc.add(pt);
    }

    public boolean xoaPhuongThuc(String maPTTT) {
        return dsPhuongThuc.removeIf(p -> p.getMaPTTT().equalsIgnoreCase(maPTTT));
    }

    public PhuongThucThanhToan timTheoMa(String maPTTT) {
        for (PhuongThucThanhToan p : dsPhuongThuc) {
            if (p.getMaPTTT().equalsIgnoreCase(maPTTT))
                return p;
        }
        return null;
    }

    public List<PhuongThucThanhToan> getDSPTTT() {
        return new ArrayList<>(dsPhuongThuc);
    }

    
}

