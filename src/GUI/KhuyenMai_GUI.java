package GUI;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import DAO.KhuyenMai_DAO;
import Entity.KhuyenMai;
import Entity.SanPham;

public class KhuyenMai_GUI extends JPanel implements ActionListener {
	private MainFrame mainFrame;
	private KhuyenMai_DAO khuyenMaiDAO;
	private JLabel title;
	private JLabel lblMaKM;
	private JTextField txtMaKM;
	private JLabel lblTenKM;
	private JTextField txtTenKM;
	private JLabel lblPhanTram;
	private JTextField txtPhanTram;
	private JLabel lblLoaiKM;
	private JComboBox cboLoaiKM;
	private JLabel lblNgayBD;
	private JSpinner spNgayBD;
	private JLabel lblNgayKT;
	private JSpinner spNgayKT;
	private JLabel lblTrangThai;
	private JComboBox cboTrangThai;
	private JButton btnThem;
	private JButton btnClear;
	private JButton btnCapNhat;
	private DefaultTableModel model;
	private JTable tblKM;
	private JButton btnXoa;
	private JButton btnLuu;
	private ArrayList<KhuyenMai> dsKhuyenMaiThem;
	private List<String> dsKhuyenMaiXoa = new ArrayList<>(); 
	private KhuyenMai kmHienTai;
	private JComboBox cbLocTheoLoai;
	private JButton btnTim;
	private JTextField txtTim;
	
	public KhuyenMai_GUI(MainFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.mainFrame = mainFrame;
		this.khuyenMaiDAO = new KhuyenMai_DAO();
        this.dsKhuyenMaiThem = new ArrayList<>();
        setLayout(new BorderLayout());

        // ===== NORTH: TIÊU ĐỀ =====
        JPanel pnNorth = new JPanel();
        title = new JLabel("Quản lý khuyến mãi");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        pnNorth.add(title);
        add(pnNorth, BorderLayout.NORTH);

        // ===== CENTER: FORM VÀ BẢNG =====
        JPanel pnCenter = new JPanel(new BorderLayout(10, 10));
        add(pnCenter, BorderLayout.CENTER);

        // --- FORM NHẬP DỮ LIỆU ---
        JPanel pnInput = new JPanel(new GridLayout(7, 2, 10, 10));
        pnInput.setBorder(BorderFactory.createTitledBorder("Thông tin khuyến mãi"));

        pnInput.add(lblMaKM = new JLabel("Mã khuyến mãi:"));
        txtMaKM = new JTextField(20);
        pnInput.add(txtMaKM);

        pnInput.add(lblTenKM = new JLabel("Tên khuyến mãi:"));
        txtTenKM = new JTextField(20);
        pnInput.add(txtTenKM);

        pnInput.add(lblPhanTram = new JLabel("Phần trăm giảm (%):"));
        txtPhanTram = new JTextField(20);
        pnInput.add(txtPhanTram);

        pnInput.add(lblLoaiKM = new JLabel("Loại khuyến mãi:"));
        cboLoaiKM = new JComboBox<>(new String[]{"- - - - - - - - - -", "Theo sản phẩm", "Theo hóa đơn"});
        pnInput.add(cboLoaiKM);

        pnInput.add(lblNgayBD = new JLabel("Ngày bắt đầu:"));
        spNgayBD = new JSpinner(new SpinnerDateModel());
        spNgayBD.setEditor(new JSpinner.DateEditor(spNgayBD, "yyyy-MM-dd"));
        pnInput.add(spNgayBD);

        pnInput.add(lblNgayKT = new JLabel("Ngày kết thúc:"));
        spNgayKT = new JSpinner(new SpinnerDateModel());
        spNgayKT.setEditor(new JSpinner.DateEditor(spNgayKT, "yyyy-MM-dd"));
        pnInput.add(spNgayKT);

        pnInput.add(lblTrangThai = new JLabel("Trạng thái:"));
        cboTrangThai = new JComboBox<>(new String[]{"- - - - - - - - - -", "Còn hiệu lực", "Hết hiệu lực"});
        pnInput.add(cboTrangThai);

        // --- NÚT THÊM + LÀM MỚI + CẬP NHẬT ---
        JPanel pnFormButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnThem = new JButton("Thêm");
        btnClear = new JButton("Làm mới");
        btnCapNhat = new JButton("Cập nhật");
        Dimension btnSize = new Dimension(100, 30);
        btnThem.setPreferredSize(btnSize);
        btnClear.setPreferredSize(btnSize);
        btnCapNhat.setPreferredSize(btnSize);
        
        JPanel pnLocTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		cbLocTheoLoai = new JComboBox<>(new String[]{"Tất cả", "Theo sản phẩm", "Theo hóa đơn"});
		btnTim = new JButton("Tìm");
		btnTim.setPreferredSize(btnSize);
		txtTim = new JTextField(15);
		
		pnLocTimKiem.add(cbLocTheoLoai);
		pnLocTimKiem.add(txtTim);
		pnLocTimKiem.add(btnTim);
		pnLocTimKiem.setBorder(BorderFactory.createTitledBorder(""));
		
		pnFormButtons.add(pnLocTimKiem);
        pnFormButtons.add(btnThem);
        pnFormButtons.add(btnClear);
        pnFormButtons.add(btnCapNhat);

        JPanel pnTop = new JPanel(new BorderLayout());
        pnTop.add(pnInput, BorderLayout.CENTER);
        pnTop.add(pnFormButtons, BorderLayout.SOUTH);

        pnCenter.add(pnTop, BorderLayout.NORTH);

        // --- BẢNG DANH SÁCH ---
        String[] columnNames = {"Mã KM", "Tên KM", "% Giảm", "Loại KM", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"};
        model = new DefaultTableModel(columnNames, 0);
        tblKM = new JTable(model);
        JScrollPane sp = new JScrollPane(tblKM);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách khuyến mãi"));
        pnCenter.add(sp, BorderLayout.CENTER);

        hienThiDanhSach();

        // --- SOUTH: NÚT XOÁ + LƯU ---
        JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnXoa.setPreferredSize(btnSize);
        btnLuu.setPreferredSize(btnSize);
        pnSouth.add(btnXoa);
        pnSouth.add(btnLuu);
        add(pnSouth, BorderLayout.SOUTH);

        btnLuu.setEnabled(false);
        btnCapNhat.setEnabled(false);

        // --- EVENT ---
        btnThem.addActionListener(this);
        btnClear.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLuu.addActionListener(this);
        btnTim.addActionListener(this);
		cbLocTheoLoai.addActionListener(e -> locTheoLoai());
        tblKM.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) hienThiChiTietKhuyenMai();
        });
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    Object o = e.getSource();

	    if (o.equals(btnThem)) {
	    	themKhuyenMai();
	    } else if (o.equals(btnXoa)) {
	        xoaKhuyenMai();
	    } else if (o.equals(btnLuu)) {
	        luuKhuyenMai();
	    } else if (o.equals(btnClear)) {
	        xuLyLamMoi();
	    }
		 else if (o.equals(btnCapNhat)) {
        	capNhatKhuyenMai();
		}
		 else if (o.equals(btnTim)) {
	        	timKiemKhuyenMai();
		}
	}
	
	private void locTheoLoai() {
		String tenLoai = cbLocTheoLoai.getSelectedItem().toString();
		if (tenLoai.equals("Tất cả")) {
	        hienThiDanhSach();
	        return;
	    }
		model.setRowCount(0);
	    List<KhuyenMai> ds = khuyenMaiDAO.layTatCa();

	    for (KhuyenMai km : ds) {
	        if (tenLoai.equalsIgnoreCase("Tất cả") ||
	            km.getloaiKM().equalsIgnoreCase(tenLoai)) {

	            model.addRow(new Object[]{
	                    km.getMaKM(),
	                    km.getTenKM(),
	                    km.getPhanTramGiam(),
	                    km.getloaiKM(),
	                    km.getNgayBatDau(),
	                    km.getNgayKetThuc(),
	                    km.getTrangThai() == 1 ? "Còn hiệu lực" : "Hết hiệu lực"
	            });
	        }
	    }
	}
	
	private void timKiemKhuyenMai() {
	    String tuKhoa = txtTim.getText().trim().toLowerCase();

	    model.setRowCount(0);
	    List<KhuyenMai> ds = khuyenMaiDAO.layTatCa();

	    for (KhuyenMai km : ds) {
	        String tenKM = km.getTenKM().toLowerCase();
	        if (tenKM.contains(tuKhoa)) {
	            model.addRow(new Object[]{
	                    km.getMaKM(),
	                    km.getTenKM(),
	                    km.getPhanTramGiam(),
	                    km.getloaiKM(),
	                    km.getNgayBatDau(),
	                    km.getNgayKetThuc(),
	                    km.getTrangThai() == 1 ? "Còn hiệu lực" : "Hết hiệu lực"
	            });
	        }
	    }
	}
	
	private void luuKhuyenMai() {
	    if (dsKhuyenMaiThem.isEmpty() && dsKhuyenMaiXoa.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Danh sách tạm rỗng, không có gì để lưu!");
	        return;
	    }

	    int soLuongThem = 0;
	    int soLuongXoa = 0;
	    for (KhuyenMai km : dsKhuyenMaiThem) {
	        if (khuyenMaiDAO.themKhuyenMai(km)) {
	            soLuongThem++;
	        }
	    }
	    for (String maKM : dsKhuyenMaiXoa) {
	        if (khuyenMaiDAO.xoaKhuyenMai(maKM)) {
	            soLuongXoa++;
	        }
	    }

	    JOptionPane.showMessageDialog(this,
	            "Đã lưu: " + soLuongThem + " khuyến mãi thêm, " + soLuongXoa + " khuyến mãi xóa.");

	    dsKhuyenMaiThem.clear();
	    dsKhuyenMaiXoa.clear();

	    xuLyLamMoi();

	    // ====== Vô hiệu nút lưu ======
	    btnLuu.setEnabled(false);
	}

	private void xoaKhuyenMai() {
	    int row = tblKM.getSelectedRow();

	    if (row < 0) {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    int confirm = JOptionPane.showConfirmDialog(
	            this,
	            "Bạn có chắc muốn xóa khuyến mãi này không?",
	            "Xác nhận xóa",
	            JOptionPane.YES_NO_OPTION
	    );

	    if (confirm != JOptionPane.YES_OPTION) {
	        return;
	    }

	    String maKM = tblKM.getValueAt(row, 0).toString();

	    DefaultTableModel model = (DefaultTableModel) tblKM.getModel();
	    model.removeRow(row);

	    dsKhuyenMaiXoa.add(maKM);

	    btnLuu.setEnabled(true);

	    JOptionPane.showMessageDialog(this, "Đã xóa khuyến mãi!");
	    xuLyLamMoi();
	}

	private void hienThiDanhSach() {
        model.setRowCount(0);
        List<KhuyenMai> ds = khuyenMaiDAO.layTatCa();
        for (KhuyenMai km : ds) {
            model.addRow(new Object[]{
                    km.getMaKM(),
                    km.getTenKM(),
                    km.getPhanTramGiam(),
                    km.getloaiKM(),
                    km.getNgayBatDau(),
                    km.getNgayKetThuc(),
                    km.getTrangThai() == 1 ? "Còn hiệu lực" : "Hết hiệu lực"
            });
        }
    }
	

	private void hienThiChiTietKhuyenMai() {
	    int row = tblKM.getSelectedRow();
	    if (row >= 0) {
	        String maKM = tblKM.getValueAt(row, 0).toString();
	        kmHienTai = khuyenMaiDAO.timTheoMaKhuyenMai(maKM);

	        if (kmHienTai != null) {
	            txtMaKM.setText(kmHienTai.getMaKM());
	            txtMaKM.setEditable(false);

	            txtTenKM.setText(kmHienTai.getTenKM());
	            txtPhanTram.setText(String.valueOf(kmHienTai.getPhanTramGiam()));
	            cboLoaiKM.setSelectedItem(kmHienTai.getloaiKM());
	            cboTrangThai.setSelectedItem(kmHienTai.getTrangThai() == 1 ? "Còn hiệu lực" : "Hết hiệu lực");

	            spNgayBD.setValue(java.sql.Date.valueOf(kmHienTai.getNgayBatDau()));
	            spNgayKT.setValue(java.sql.Date.valueOf(kmHienTai.getNgayKetThuc()));

	            btnCapNhat.setEnabled(false);
	            addChangeListenersKM();
	        }
	    }
	}

	private void addChangeListenersKM() {
	    DocumentListener docListener = new DocumentListener() {
	        public void changedUpdate(DocumentEvent e) { checkChangeKM(); }
	        public void removeUpdate(DocumentEvent e) { checkChangeKM(); }
	        public void insertUpdate(DocumentEvent e) { checkChangeKM(); }
	    };

	    txtTenKM.getDocument().addDocumentListener(docListener);
	    txtPhanTram.getDocument().addDocumentListener(docListener);

	    ActionListener comboListener = e -> checkChangeKM();
	    cboLoaiKM.addActionListener(comboListener);
	    cboTrangThai.addActionListener(comboListener);
	    spNgayBD.addChangeListener(e -> checkChangeKM());
	    spNgayKT.addChangeListener(e -> checkChangeKM());
	}

	private void checkChangeKM() {
	    if (kmHienTai == null) return;

	    boolean thayDoi = false;
	    LocalDate ngayBD = ((java.util.Date) spNgayBD.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate ngayKT = ((java.util.Date) spNgayKT.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    
	    if (!txtTenKM.getText().trim().equals(kmHienTai.getTenKM())) thayDoi = true;
	    else if (!txtPhanTram.getText().trim().equals(String.valueOf(kmHienTai.getPhanTramGiam()))) thayDoi = true;
	    else if (!cboLoaiKM.getSelectedItem().toString().equals(kmHienTai.getloaiKM())) thayDoi = true;
	    else if (!cboTrangThai.getSelectedItem().toString().equals(kmHienTai.getTrangThai() == 1 ? "Đang áp dụng" : "Ngưng áp dụng")) thayDoi = true;
	    else if (!ngayBD.equals(kmHienTai.getNgayBatDau())) thayDoi = true;
	    else if (!ngayKT.equals(kmHienTai.getNgayKetThuc())) thayDoi = true;

	    btnCapNhat.setEnabled(thayDoi);
	}

	private void capNhatKhuyenMai() {
	    if (kmHienTai == null) return;
	    java.util.Date ngayBDUtil = (java.util.Date) spNgayBD.getValue();
	    java.util.Date ngayKTUtil = (java.util.Date) spNgayKT.getValue();
	    LocalDate ngayBD = ngayBDUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate ngayKT = ngayKTUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    
	    kmHienTai.setTenKM(txtTenKM.getText().trim());
	    kmHienTai.setPhanTramGiam(Double.parseDouble(txtPhanTram.getText().trim()));
	    kmHienTai.setloaiKM(cboLoaiKM.getSelectedItem().toString());
	    kmHienTai.setTrangThai(cboTrangThai.getSelectedItem().toString().equals("Đang áp dụng") ? 1 : 0);
	    kmHienTai.setNgayBatDau(ngayBD);
	    kmHienTai.setNgayKetThuc(ngayKT);

	    if (khuyenMaiDAO.capNhatKhuyenMai(kmHienTai)) {
	        JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!");
	        hienThiDanhSach(); 
	        btnCapNhat.setEnabled(false);
	    } else {
	        JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
	    }
	}

	
	private void themKhuyenMai() {
	    String maKM = txtMaKM.getText().trim();
	    String tenKM = txtTenKM.getText().trim();
	    String phanTramStr = txtPhanTram.getText().trim();
	    String loaiKM = (String) cboLoaiKM.getSelectedItem();
	    String trangThaiStr = (String) cboTrangThai.getSelectedItem();

	    if (maKM.isEmpty() || tenKM.isEmpty() || phanTramStr.isEmpty()
	            || cboLoaiKM.getSelectedIndex() == 0
	            || cboTrangThai.getSelectedIndex() == 0) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    double phanTramGiam;
	    try {
	        phanTramGiam = Double.parseDouble(phanTramStr);
	        if (phanTramGiam < 0 || phanTramGiam > 100) {
	            JOptionPane.showMessageDialog(this, "Phần trăm giảm phải nằm trong khoảng 0 - 100!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Phần trăm giảm phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    for (int i = 0; i < tblKM.getRowCount(); i++) {
	        if (tblKM.getValueAt(i, 0).toString().equalsIgnoreCase(maKM)) {
	            JOptionPane.showMessageDialog(this, "Mã khuyến mãi đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	    }
	    java.util.Date utilDateBD = (java.util.Date) spNgayBD.getValue();
	    java.util.Date utilDateKT = (java.util.Date) spNgayKT.getValue();

	    LocalDate ngayBD = utilDateBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate ngayKT = utilDateKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    if (ngayKT.isBefore(ngayBD)) {
	        JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    int trangThai = trangThaiStr.equalsIgnoreCase("Hoạt động") ? 1 : 0;
	    KhuyenMai km = new KhuyenMai(maKM, tenKM, phanTramGiam, loaiKM, ngayBD, ngayKT, trangThai);

	    DefaultTableModel model = (DefaultTableModel) tblKM.getModel();
	    model.addRow(new Object[]{
	            maKM,
	            tenKM,
	            phanTramGiam,
	            loaiKM,
	            ngayBD,
	            ngayKT,
	            trangThaiStr
	    });

	    dsKhuyenMaiThem.add(km);
	    btnLuu.setEnabled(true);

	    JOptionPane.showMessageDialog(this, "Đã thêm khuyến mãi mới!");
	    xuLyLamMoi();
	}


	
	private void xuLyLamMoi() {
	    txtMaKM.setText("");
	    txtTenKM.setText("");
	    txtPhanTram.setText("");
	    cboLoaiKM.setSelectedIndex(0);
	    cboTrangThai.setSelectedIndex(0);
	    btnCapNhat.setEnabled(false);
	}



}
