package Entity;

import java.util.ArrayList;
import java.util.List;

public class DanhSachKhachHang {
    private List<KhachHang> dsKhachHang;

    public DanhSachKhachHang() {
        dsKhachHang = new ArrayList<>();
    }

    public boolean themKhachHang(KhachHang kh) {
        if (kh == null || dsKhachHang.contains(kh))
            return false;
        return dsKhachHang.add(kh);
    }

    public boolean xoaKhachHang(String maKH) {
        return dsKhachHang.removeIf(kh -> kh.getMaKhachHang().equalsIgnoreCase(maKH));
    }

    public KhachHang timKhachHang(String maKH) {
        for (KhachHang kh : dsKhachHang) {
            if (kh.getMaKhachHang().equalsIgnoreCase(maKH))
                return kh;
        }
        return null;
    }

    public List<KhachHang> getDSKH() {
        return dsKhachHang;
    }
}
