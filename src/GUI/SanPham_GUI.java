package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import ConnectDB.ConnectDB;
import DAO.LoaiSanPham_DAO;
import DAO.SanPham_DAO;
import Entity.LoaiSanPham;
import Entity.SanPham;


public class SanPham_GUI extends JPanel implements ActionListener {
	private MainFrame mainFrame;
	private SanPham_DAO sanPhamDAO;
	private JLabel title;
	private JTextField txtMaSP;
	private JTextField txtTenSP;
	private DefaultTableModel model;
	private JTable tblSanPham;
	private JButton btnThem;
	private JButton btnCapNhat;
	private JButton btnXoa;
	private JLabel lblMaSP;
	private JLabel lblTenSP;
	private JComboBox cboLoai;
	private JTextField txtSearch;
	private JButton btnSearch;
	private JButton btnLuu;
	private JLabel lblLoaiSP;
	private JTextField txtLoaiSP;
	private JLabel lblDVT;
	private JTextField txtDVT;
	private JLabel lblGia;
	private JTextField txtGia;
	private JButton btnClear;
	private JComboBox cboDVT;
	private JComboBox cboTrangThai;
	private JTextField txtHienTenAnh;
	private JButton btnThemAnh;
	private JComboBox cboLoaiSP;
	private LoaiSanPham_DAO loaiDAO;
	private ArrayList<SanPham> dsSanPhamThem;
	private List<String> dsSanPhamXoa = new ArrayList<>();
	private SanPham  spHienTai = null;
	
	public SanPham_GUI(MainFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.mainFrame = mainFrame;
		this.sanPhamDAO = new SanPham_DAO();
		this.loaiDAO = new LoaiSanPham_DAO();
		this.dsSanPhamThem = new ArrayList<>();
		this.setLayout(new BorderLayout());
		// -------North--------
		JPanel pnNorth = new JPanel();
		pnNorth.add(title = new JLabel("Sản phẩm"));
		Font fnt = new Font("Arial", Font.BOLD, 20);
		title.setFont(fnt);
		add(pnNorth, BorderLayout.NORTH);
		JPanel pnCenter = new JPanel(new BorderLayout(10, 10));
		add(pnCenter, BorderLayout.CENTER);

		// ====== TRÊN: FORM NHẬP DỮ LIỆU ======
		// Form nhập liệu
		JPanel pnInput = new JPanel(new GridLayout(7, 2, 10, 10)); // 5 hàng, 2 cột, 10px khoảng cách
		pnInput.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));

		pnInput.add(lblMaSP = new JLabel("Mã sản phẩm:"));
		txtMaSP = new JTextField(20);
		pnInput.add(txtMaSP);

		pnInput.add(new JLabel("Loại sản phẩm:"));
		cboLoaiSP = new JComboBox<>();
		pnInput.add(cboLoaiSP);

		loadTenLoaiSanPham();
		
		pnInput.add(lblTenSP = new JLabel("Tên sản phẩm:"));
		txtTenSP = new JTextField(20);
		pnInput.add(txtTenSP);

		pnInput.add(new JLabel("Đơn vị tính:"));
		String[] donVi = {"- - - - - - - - - -","Ly", "Tách", "Ấm", "Miếng", "Cái", "Dĩa"};
		cboDVT = new JComboBox<>(donVi);
		pnInput.add(cboDVT);

		pnInput.add(new JLabel("Giá:"));
		txtGia = new JTextField(20);
		pnInput.add(txtGia);
		
		pnInput.add(new JLabel("Trạng thái:"));
		String[] trangThai = {"- - - - - - - - - -","Còn hàng", "Hết hàng"};
		cboTrangThai = new JComboBox<>(trangThai);
		pnInput.add(cboTrangThai);
		
		pnInput.add(new JLabel("Ảnh:"));
		JPanel pnAnh = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		txtHienTenAnh = new JTextField(12);
		txtHienTenAnh.setEditable(false);
		btnThemAnh = new JButton("Chọn ảnh");
		pnAnh.add(txtHienTenAnh);
		pnAnh.add(btnThemAnh);
		pnInput.add(pnAnh);

		// ====== NGAY DƯỚI FORM: NÚT THÊM & LÀM MỚI ======
		JPanel pnFormButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		btnThem = new JButton("Thêm");
		btnClear = new JButton("Làm mới");
		btnCapNhat = new JButton("Cập nhật");
		Dimension btnSize = new Dimension(100, 30);
		btnThem.setPreferredSize(btnSize);
		btnClear.setPreferredSize(btnSize);
		btnCapNhat.setPreferredSize(btnSize);
		pnFormButtons.add(btnThem);
		pnFormButtons.add(btnClear);
		pnFormButtons.add(btnCapNhat);

		JPanel pnTop = new JPanel();
		pnTop.setLayout(new BorderLayout());
		pnTop.add(pnInput, BorderLayout.CENTER);
		pnTop.add(pnFormButtons, BorderLayout.SOUTH);

		pnCenter.add(pnTop, BorderLayout.NORTH);

		// ====== GIỮA: BẢNG SẢN PHẨM ======
		tblSanPham = new JTable();
		JScrollPane sp = new JScrollPane(tblSanPham);
		sp.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));
		pnCenter.add(sp, BorderLayout.CENTER);

		// Sau khi tạo bảng, mới load dữ liệu
		hienThiSanPham();

		// ====== SOUTH: NÚT XÓA & LƯU ======
		JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		btnXoa = new JButton("Xóa");
		btnLuu = new JButton("Lưu");

		btnXoa.setPreferredSize(btnSize);
		btnLuu.setPreferredSize(btnSize);

		pnSouth.add(btnXoa);
		pnSouth.add(btnLuu);
		
		add(pnSouth, BorderLayout.SOUTH);
		btnLuu.setEnabled(false);
		btnThem.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnXoa.addActionListener(this);
		btnLuu.addActionListener(this);
		btnClear.addActionListener(this);
		btnThemAnh.addActionListener(this);
		
	}

	private void loadTenLoaiSanPham() {
	    cboLoaiSP.removeAllItems();
	    cboLoaiSP.addItem("- - - - - - - - - -");
	    List<LoaiSanPham> dsLoai = loaiDAO.layTatCa();
	    for (LoaiSanPham l : dsLoai) {
	        cboLoaiSP.addItem(l.getTenLoai());
	    }
	}


	@Override
	public void actionPerformed(ActionEvent e) {
	    Object o = e.getSource();

	    if (o.equals(btnThem)) {
	        xuLyThemSanPham();
	    } else if (o.equals(btnXoa)) {
	        xuLyXoaSanPham();
	    } else if (o.equals(btnLuu)) {
	        xuLyLuuSanPham();
	    } else if (o.equals(btnClear)) {
	        xuLyLamMoi();
	    } else if (o.equals(btnThemAnh)) {
	        xuLyChonAnh();
	    }
		 else if (o.equals(btnCapNhat)) {
        	xuLyCapNhatSanPham();
		}
	}
	
	private void capNhatTrangThaiNutLuu() {
	    btnLuu.setEnabled(!dsSanPhamThem.isEmpty());
	}

	private void xuLyLuuSanPham() {
	    int soLuongLuu = 0;
	    for (SanPham sp : dsSanPhamThem) {
	        if (sanPhamDAO.themSanPham(sp)) soLuongLuu++;
	    }

	    int soLuongXoa = 0;
	    for (String maSP : dsSanPhamXoa) {
	        if (sanPhamDAO.xoaSanPham(maSP)) soLuongXoa++;
	    }

	    JOptionPane.showMessageDialog(this, "Đã lưu " + soLuongLuu + " sản phẩm thêm, xóa " + soLuongXoa + " sản phẩm.");

	    dsSanPhamThem.clear();
	    dsSanPhamXoa.clear();
	    hienThiSanPham(); 
	    btnLuu.setEnabled(false);
	}


	private void xuLyXoaSanPham() {
	    int row = tblSanPham.getSelectedRow();
	    if (row < 0) {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
	        return;
	    }

	    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
	    if (confirm != JOptionPane.YES_OPTION) return;

	    String maSP = model.getValueAt(row, 0).toString();

	    // Nếu sản phẩm mới thêm chưa lưu thì xóa khỏi dsSanPhamThem
	    dsSanPhamThem.removeIf(sp -> sp.getMaSanPham().equals(maSP));

	    // Nếu là sản phẩm đã có trong DB thì lưu mã vào dsSanPhamXoa
	    dsSanPhamXoa.add(maSP);

	    model.removeRow(row);
	    btnLuu.setEnabled(true);

	    JOptionPane.showMessageDialog(this, "Sản phẩm đã xóa !");
	}

	private void xuLyThemSanPham() {
	    String maSP = txtMaSP.getText().trim();
	    String tenSP = txtTenSP.getText().trim();
	    String donVi = cboDVT.getSelectedItem().toString();
	    String trangThaiStr = cboTrangThai.getSelectedItem().toString();
	    String tenLoai = cboLoaiSP.getSelectedItem().toString();
	    String tenAnh = txtHienTenAnh.getText().trim(); // đường dẫn tạm

	    String maLoai = layMaLoaiTuTen(tenLoai);
	    LoaiSanPham loaiSP = loaiDAO.layTheoMaLoai(maLoai);
	    if (loaiSP == null) {
	        JOptionPane.showMessageDialog(this, "Loại sản phẩm không hợp lệ!");
	        return;
	    }

	    if (!maSP.matches("SP\\d{3}")) {
	        JOptionPane.showMessageDialog(this, "Mã sản phẩm phải có dạng SPxxx!");
	        return;
	    }
	    if (tenSP.isEmpty() || donVi.equals("- - - - - - - - - -") 
	        || trangThaiStr.equals("- - - - - - - - - -")) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
	        return;
	    }

	    double gia;
	    try {
	        gia = Double.parseDouble(txtGia.getText().trim());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Giá phải là số!");
	        return;
	    }

	    int trangThai = trangThaiStr.equals("Còn hàng") ? 1 : 0;

	    boolean trungMaDS = dsSanPhamThem.stream().anyMatch(sp -> sp.getMaSanPham().equals(maSP));
	    boolean trungMaDB = sanPhamDAO.timTheoMa(maSP) != null;

	    if (trungMaDS || trungMaDB) {
	        JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại!");
	        return;
	    }

	    SanPham sp = new SanPham(maSP, tenSP, donVi, new BigDecimal(gia), tenAnh, trangThai, loaiSP);
	    dsSanPhamThem.add(sp);
	    capNhatTrangThaiNutLuu();
	    ImageIcon icon = null;
	    if (tenAnh != null && !tenAnh.isEmpty()) {
	        java.io.File file = new java.io.File(tenAnh);
	        if (file.exists()) {
	            icon = new ImageIcon(new ImageIcon(tenAnh).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	        }
	    }

	    model.addRow(new Object[]{maSP, loaiSP.getMaLoaiSP(), tenSP, donVi, gia, trangThaiStr, icon, tenAnh});
	    tblSanPham.setRowHeight(60);

	    xuLyLamMoi(); 
	}




	
	private String layMaLoaiTuTen(String tenLoai) {
	    List<LoaiSanPham> dsLoai = loaiDAO.layTatCa(); 
	    for (LoaiSanPham l : dsLoai) {
	        if (l.getTenLoai().equals(tenLoai)) {
	            return l.getMaLoaiSP();
	        }
	    }
	    return null; 
	}

	private void xuLyLamMoi() {
	    txtMaSP.setText("");
	    txtMaSP.setEditable(true);
	    txtTenSP.setText("");
	    txtGia.setText("");
	    txtHienTenAnh.setText("");
	    cboDVT.setSelectedIndex(0);
	    cboTrangThai.setSelectedIndex(0);

	    if (cboLoaiSP.getItemCount() > 0)
	        cboLoaiSP.setSelectedIndex(0);
	    btnCapNhat.setEnabled(false);
	    txtMaSP.requestFocus();
	}

	public void hienThiSanPham() {
	    String[] columnNames = {"Mã SP", "Mã loại SP", "Tên SP", "Đơn vị tính", "Giá", "Trạng thái", "Ảnh", "tenAnh"};
	    model = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public Class<?> getColumnClass(int columnIndex) {
	            if (columnIndex == 6) return ImageIcon.class; 
	            return Object.class;
	        }

	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; 
	        }
	    };

	    List<SanPham> dsSP = sanPhamDAO.layTatCa();

	    for (SanPham sp : dsSP) {
	        String maSP = sp.getMaSanPham();
	        String maLoai = sp.getLoaiSP().getMaLoaiSP();
	        String tenSP = sp.getTenSanPham();
	        String dvt = sp.getDonViTinh();
	        double gia = sp.getGia().doubleValue();
	        String trangThaiStr = sp.getTrangThai() == 1 ? "Còn hàng" : "Hết hàng";
	        String tenAnh = sp.gethinhAnh();

	        ImageIcon icon = null;
	        if (tenAnh != null && !tenAnh.isEmpty()) {
	            java.net.URL imgURL = getClass().getResource("/img/" + tenAnh);
	            if (imgURL != null) {
	                icon = new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	            }
	        }

	        model.addRow(new Object[]{maSP, maLoai, tenSP, dvt, gia, trangThaiStr, icon, tenAnh});
	    }

	    tblSanPham.setModel(model);
	    tblSanPham.setRowHeight(60);

	    // Ẩn cột ẩn tên ảnh
	    tblSanPham.getColumnModel().getColumn(7).setMinWidth(0);
	    tblSanPham.getColumnModel().getColumn(7).setMaxWidth(0);
	    tblSanPham.getColumnModel().getColumn(7).setWidth(0);

	    
	    tblSanPham.getSelectionModel().addListSelectionListener(e -> {
	        if (!e.getValueIsAdjusting()) {
	            hienThiChiTietSanPham();
	        }
	    });
	}

	

	private void hienThiChiTietSanPham() {
	    int row = tblSanPham.getSelectedRow();
	    if (row >= 0) {
	        String maSP = model.getValueAt(row, 0).toString();

	        spHienTai = sanPhamDAO.timTheoMa(maSP);

	        txtMaSP.setText(spHienTai.getMaSanPham());
	        txtMaSP.setEditable(false);

	        String tenLoai = spHienTai.getLoaiSP().getTenLoai();
	        cboLoaiSP.setSelectedItem(tenLoai);

	        txtTenSP.setText(spHienTai.getTenSanPham());
	        cboDVT.setSelectedItem(spHienTai.getDonViTinh());
	        txtGia.setText(spHienTai.getGia().toString());
	        cboTrangThai.setSelectedItem(spHienTai.getTrangThai() == 1 ? "Còn hàng" : "Hết hàng");
	        txtHienTenAnh.setText(spHienTai.gethinhAnh());

	        btnCapNhat.setEnabled(false);
	        addChangeListeners();
	    }
	}


	public void xuLyChonAnh() {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
	    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
	            "Image files", "jpg", "png", "jpeg", "gif"));

	    int result = fileChooser.showOpenDialog(this);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        java.io.File file = fileChooser.getSelectedFile();
	        txtHienTenAnh.setText(file.getAbsolutePath());
	    }
	}


	
	
	private void addChangeListeners() {
	    DocumentListener docListener = new DocumentListener() {
	        public void changedUpdate(DocumentEvent e) { checkChange(); }
	        public void removeUpdate(DocumentEvent e) { checkChange(); }
	        public void insertUpdate(DocumentEvent e) { checkChange(); }
	    };

	    txtTenSP.getDocument().addDocumentListener(docListener);
	    txtGia.getDocument().addDocumentListener(docListener);
	    txtHienTenAnh.getDocument().addDocumentListener(docListener);

	    ActionListener comboListener = e -> checkChange();
	    cboDVT.addActionListener(comboListener);
	    cboTrangThai.addActionListener(comboListener);
	    cboLoaiSP.addActionListener(comboListener);
	}

	private void checkChange() {
	    if (spHienTai == null) return;

	    boolean thayDoi = false;

	    if (!txtTenSP.getText().trim().equals(spHienTai.getTenSanPham())) thayDoi = true;
	    else if (!cboDVT.getSelectedItem().toString().equals(spHienTai.getDonViTinh())) thayDoi = true;
	    else if (!txtGia.getText().trim().equals(spHienTai.getGia().toString())) thayDoi = true;
	    else if (!cboTrangThai.getSelectedItem().toString().equals(spHienTai.getTrangThai() == 1 ? "Còn hàng" : "Hết hàng")) thayDoi = true;
	    else if (!cboLoaiSP.getSelectedItem().toString().equals(spHienTai.getLoaiSP().getTenLoai())) thayDoi = true;
	    else if (!txtHienTenAnh.getText().trim().equals(spHienTai.gethinhAnh())) thayDoi = true;

	    btnCapNhat.setEnabled(thayDoi);
	}


	private void xuLyCapNhatSanPham() {
	    if (spHienTai == null) return;

	    spHienTai.setTenSanPham(txtTenSP.getText().trim());
	    spHienTai.setDonViTinh(cboDVT.getSelectedItem().toString());
	    spHienTai.setGia(new BigDecimal(txtGia.getText().trim()));
	    spHienTai.setTrangThai(cboTrangThai.getSelectedItem().toString().equals("Còn hàng") ? 1 : 0);

	    String maLoaiMoi = layMaLoaiTuTen(cboLoaiSP.getSelectedItem().toString());
	    LoaiSanPham loaiMoi = loaiDAO.layTheoMaLoai(maLoaiMoi);
	    spHienTai.setLoaiSP(loaiMoi);

	    spHienTai.sethinhAnh(txtHienTenAnh.getText().trim());

	    if (sanPhamDAO.capNhatSanPham(spHienTai)) {
	        JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");
	        hienThiSanPham(); 
	        btnCapNhat.setEnabled(false);
	    } else {
	        JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
	    }
	}

}
