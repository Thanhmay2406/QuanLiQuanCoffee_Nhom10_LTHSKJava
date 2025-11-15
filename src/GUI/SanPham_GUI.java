package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener; // Thêm
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BorderFactory;
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
import javax.swing.table.DefaultTableModel;

import DAO.LoaiSanPham_DAO;
import DAO.SanPham_DAO;
import Entity.LoaiSanPham;
import Entity.SanPham;

public class SanPham_GUI extends JPanel implements ActionListener, ComponentListener {
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
	private JTextField txtSearch;
	private JButton btnSearch;
	private JLabel lblGia;
	private JTextField txtGia;
	private JButton btnClear;
	private JComboBox cboDVT;
	private JComboBox cboTrangThai;
	private JTextField txtHienTenAnh;
	private JButton btnThemAnh;
	private JComboBox cboLoaiSP;
	private LoaiSanPham_DAO loaiDAO;
	private JButton btnTrangChu;

	public SanPham_GUI(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.sanPhamDAO = new SanPham_DAO();
		this.loaiDAO = new LoaiSanPham_DAO();
		this.setLayout(new BorderLayout());

		// -------North--------
		JPanel pnNorth = new JPanel();
		pnNorth.add(title = new JLabel("Quản lý Sản phẩm"));
		Font fnt = new Font("Arial", Font.BOLD, 20);
		title.setFont(fnt);
		add(pnNorth, BorderLayout.NORTH);
		JPanel pnCenter = new JPanel(new BorderLayout(10, 10));
		add(pnCenter, BorderLayout.CENTER);

		// Form nhập liệu
		JPanel pnInput = new JPanel(new GridLayout(7, 2, 10, 10));
		pnInput.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));

		pnInput.add(lblMaSP = new JLabel("Mã sản phẩm:"));
		txtMaSP = new JTextField(20);
		txtMaSP.setEditable(false);
		pnInput.add(txtMaSP);

		pnInput.add(new JLabel("Loại sản phẩm:"));
		cboLoaiSP = new JComboBox<>();
		pnInput.add(cboLoaiSP);

		loadTenLoaiSanPham();

		pnInput.add(lblTenSP = new JLabel("Tên sản phẩm:"));
		txtTenSP = new JTextField(20);
		pnInput.add(txtTenSP);

		pnInput.add(new JLabel("Đơn vị tính:"));
		String[] donVi = { "Chọn đơn vị tính", "Ly", "Tách", "Ấm", "Miếng", "Cái", "Dĩa" };
		cboDVT = new JComboBox<>(donVi);
		pnInput.add(cboDVT);

		pnInput.add(new JLabel("Giá:"));
		txtGia = new JTextField(20);
		pnInput.add(txtGia);

		pnInput.add(new JLabel("Trạng thái:"));
		String[] trangThai = { "Chọn trạng thái", "Còn hàng", "Hết hàng" };
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

		tblSanPham = new JTable();
		JScrollPane sp = new JScrollPane(tblSanPham);
		sp.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));
		pnCenter.add(sp, BorderLayout.CENTER);

		hienThiSanPham();

		// pnSouth
		JPanel pnSouth = new JPanel(new BorderLayout());
		btnTrangChu = new JButton("Trang chủ");
		btnXoa = new JButton("Xóa");
		btnXoa.setPreferredSize(btnSize);
		btnTrangChu.setPreferredSize(btnSize);
		pnSouth.add(btnTrangChu, BorderLayout.WEST);
		pnSouth.add(btnXoa, BorderLayout.EAST);
		add(pnSouth, BorderLayout.SOUTH);

		btnThem.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnXoa.addActionListener(this);
		btnClear.addActionListener(this);
		btnThemAnh.addActionListener(this);
		btnTrangChu.addActionListener(this);

		tblSanPham.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				hienThiChiTietSanPham();
			}
		});

		refreshMa();
	}

	@SuppressWarnings("unchecked")
	private void loadTenLoaiSanPham() {
		cboLoaiSP.removeAllItems();
		cboLoaiSP.addItem("Loại sản phẩm");
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
		} else if (o.equals(btnClear)) {
			xuLyLamMoi();
		} else if (o.equals(btnThemAnh)) {
			xuLyChonAnh();
		} else if (o.equals(btnCapNhat)) {
			xuLyCapNhatSanPham();
		} else if (o.equals(btnTrangChu)) {
			mainFrame.switchToPanel(mainFrame.KEY_DAT_BAN);
		}
	}

	private void xuLyXoaSanPham() {
		int row = tblSanPham.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận",
				JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION)
			return;

		String maSP = model.getValueAt(row, 0).toString();

		if (sanPhamDAO.xoaSanPham(maSP)) {
			JOptionPane.showMessageDialog(this, "Sản phẩm đã xóa!");
			hienThiSanPham();
			xuLyLamMoi();
			refreshMa();
		} else {
			JOptionPane.showMessageDialog(this, "Xóa thất bại!");
		}
	}

	private void refreshMa() {
		txtMaSP.setText(taoMaSanPham());
	}

	public String taoMaSanPham() {
		String maSPCuoi = sanPhamDAO.getMaSanPhamCuoiCung();
		if (maSPCuoi == null) {
			return "SP001";
		}
		try {
			String phanSo = maSPCuoi.substring(2);
			int soHienTai = Integer.parseInt(phanSo);
			soHienTai++;
			String phanSoMoi = String.format("%03d", soHienTai);
			return "SP" + phanSoMoi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SP_ERROR";
	}

	private void xuLyThemSanPham() {
		String maSP = txtMaSP.getText().trim();
		String tenSP = txtTenSP.getText().trim();
		String donVi = cboDVT.getSelectedItem().toString();
		String trangThaiStr = cboTrangThai.getSelectedItem().toString();
		String tenLoai = cboLoaiSP.getSelectedItem().toString();
		String tenAnh = txtHienTenAnh.getText().trim();

		if (!maSP.matches("SP\\d{3}")) {
			JOptionPane.showMessageDialog(this, "Mã sản phẩm phải có dạng SPxxx!");
			return;
		}
		if (tenSP.isEmpty() || donVi.equals("Chọn đơn vị tính") || trangThaiStr.equals("Chọn trạng thái")
				|| tenLoai.equals("Loại sản phẩm")) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		LoaiSanPham loaiSP = loaiDAO.layTheoTenLoai(tenLoai);
		if (loaiSP == null) {
			JOptionPane.showMessageDialog(this, "Loại sản phẩm không hợp lệ!");
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

		SanPham sp = new SanPham(maSP, tenSP, donVi, new BigDecimal(gia), tenAnh, trangThai, loaiSP);

		if (sanPhamDAO.themSanPham(sp)) {
			JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
			hienThiSanPham();
			xuLyLamMoi();
			refreshMa();
		} else {
			JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!");
		}
	}

	private void xuLyLamMoi() {
		txtMaSP.setText(taoMaSanPham());

		txtTenSP.setText("");
		txtGia.setText("");
		txtHienTenAnh.setText("");
		cboDVT.setSelectedIndex(0);
		cboTrangThai.setSelectedIndex(0);

		if (cboLoaiSP.getItemCount() > 0)
			cboLoaiSP.setSelectedIndex(0);

		tblSanPham.clearSelection();
	}

	public void hienThiSanPham() {
		String[] columnNames = { "Mã SP", "Loại SP", "Tên SP", "Đơn vị tính", "Giá", "Trạng thái", "Ảnh", "tenAnh" };
		model = new DefaultTableModel(columnNames, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 6)
					return ImageIcon.class;
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
			String tenLoai = sp.getLoaiSP().getTenLoai();
			String tenSP = sp.getTenSanPham();
			String dvt = sp.getDonViTinh();
			double gia = sp.getGia().doubleValue();
			String trangThaiStr = sp.getTrangThai() == 1 ? "Còn hàng" : "Hết hàng";
			String tenAnh = sp.gethinhAnh();

			ImageIcon icon = null;
			if (tenAnh != null && !tenAnh.isEmpty()) {
				java.net.URL imgURL = getClass().getResource("/img/" + tenAnh);
				if (imgURL != null) {
					icon = new ImageIcon(
							new ImageIcon(imgURL).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
				} else {
					System.err.println("Không tìm thấy ảnh: " + tenAnh);
				}
			}

			model.addRow(new Object[] { maSP, tenLoai, tenSP, dvt, gia, trangThaiStr, icon, tenAnh });
		}

		tblSanPham.setModel(model);
		tblSanPham.setRowHeight(60);

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
			txtMaSP.setText(model.getValueAt(row, 0).toString());

			String tenLoai = model.getValueAt(row, 1).toString();
			LoaiSanPham loaiSP = loaiDAO.layTheoTenLoai(tenLoai);
			if (loaiSP != null) {
				cboLoaiSP.setSelectedItem(loaiSP.getTenLoai());
			}

			txtTenSP.setText(model.getValueAt(row, 2).toString());
			cboDVT.setSelectedItem(model.getValueAt(row, 3).toString());
			txtGia.setText(model.getValueAt(row, 4).toString());
			cboTrangThai.setSelectedItem(model.getValueAt(row, 5).toString());
			txtHienTenAnh.setText(model.getValueAt(row, 7).toString());
		}
	}

	public void xuLyChonAnh() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
		fileChooser.setFileFilter(
				new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "jpeg", "gif"));

		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			java.io.File file = fileChooser.getSelectedFile();
			txtHienTenAnh.setText(file.getName());
		}
	}

	private void xuLyCapNhatSanPham() {
		try {
			String maSP = txtMaSP.getText().trim();
			String tenSP = txtTenSP.getText().trim();
			String donVi = cboDVT.getSelectedItem().toString();
			BigDecimal gia = new BigDecimal(txtGia.getText().trim());
			int trangThai = cboTrangThai.getSelectedItem().toString().equals("Còn hàng") ? 1 : 0;
			String tenLoai = cboLoaiSP.getSelectedItem().toString().trim();
			LoaiSanPham loaiMoi = loaiDAO.layTheoTenLoai(tenLoai);

			String tenAnh = txtHienTenAnh.getText().trim();

			SanPham spMoi = new SanPham(maSP, tenSP, donVi, gia, tenAnh, trangThai, loaiMoi);

			if (sanPhamDAO.capNhatSanPham(spMoi)) {
				JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");
				hienThiSanPham();
				xuLyLamMoi();
			} else {
				JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Giá phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật");
			e.printStackTrace();
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentShown(ComponentEvent e) {
		xuLyLamMoi();
		refreshMa();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
	}
}