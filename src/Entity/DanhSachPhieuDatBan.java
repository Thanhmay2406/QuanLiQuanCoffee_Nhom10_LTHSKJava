package Entity;

import java.util.ArrayList;
import java.util.List;

public class DanhSachPhieuDatBan {
    private List<PhieuDatBan> dsPhieuDat;

    public DanhSachPhieuDatBan() {
        dsPhieuDat = new ArrayList<>();
    }

    public boolean themPhieu(PhieuDatBan p) {
        if (p == null || dsPhieuDat.contains(p))
            return false;
        return dsPhieuDat.add(p);
    }

    public boolean xoaPhieu(String maPhieu) {
        return dsPhieuDat.removeIf(p -> p.getMaPhieuDat().equalsIgnoreCase(maPhieu));
    }

    public PhieuDatBan timPhieu(String maPhieu) {
        for (PhieuDatBan p : dsPhieuDat) {
            if (p.getMaPhieuDat().equalsIgnoreCase(maPhieu))
                return p;
        }
        return null;
    }

    public List<PhieuDatBan> getDSPDB() {
        return dsPhieuDat;
    }
}
