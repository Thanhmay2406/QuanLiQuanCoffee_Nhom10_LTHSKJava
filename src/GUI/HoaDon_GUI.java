/*
 * @ (#) HoaDon_GUI.java   1.1     Nov 10, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DAO.KhachHang_DAO;
import DAO.KhuyenMai_DAO;
import DAO.PhuongThucThanhToan_DAO;
import Entity.KhachHang;
import Entity.KhuyenMai;
import Entity.PhuongThucThanhToan;

/*
* @description
* @author: Van Long (đã được Gemini chỉnh sửa và bổ sung)
* @date: Nov 10, 2025
* @version: 1.1
*/

public class HoaDon_GUI extends JPanel implements ActionListener {

	// Thay vì 'new DAO()' ở đây, tốt hơn là truyền chúng vào qua constructor
	// Hoặc sử dụng kỹ thuật "Dependency Injection"
	private KhuyenMai_DAO khuyenmai_dao;
	private PhuongThucThanhToan_DAO pttt_dao;

	private MainFrame mainFrame; // Dùng để lấy thông tin nhân viên đăng nhập

	// Components cho Panel Tích điểm (Trái)
	private JTextField txtTichDiemSearch;
	private JButton btnTichDiemSearch;
	private DefaultTableModel tichDiemModel;
	private JTable tichDiemTable;
	private JButton btnDungDiem;
	private JButton btnCongDiem;

	// Components cho Panel Hóa đơn (Phải)
	private JTextField txtMaHoaDon;
	private JTextField txtNgayTao;
	private JTextField txtNhanVien;
	private DefaultTableModel hoadon_model;
	private JTable hoadon_table;
	private JComboBox<String> cbPTTT, cbKhuyenMai;
	private JLabel lblTongTienValue;
	private JButton btnThanhToan;
	private Menu_GUI menu_gui;
	private JLabel lblHoaDon;
	private JLabel lblNgayTao;
	private KhachHang_DAO kh_dao;
	private JLabel lblHoaDonText;
	private JLabel lblNgayTaoText;

	/**
	 * Constructor
	 * 
	 * @param mainFrame Frame chính của ứng dụng
	 */
	public HoaDon_GUI(MainFrame mainFrame) { // Nên truyền MainFrame vào
		this.mainFrame = mainFrame;
		this.khuyenmai_dao = new KhuyenMai_DAO();
		this.pttt_dao = new PhuongThucThanhToan_DAO();
		this.menu_gui = new Menu_GUI(mainFrame);
		this.kh_dao = new KhachHang_DAO();
		setLayout(new BorderLayout());

		// === Tiêu đề chính ===
		JPanel pnNorth = new JPanel();
		JLabel lblTitle = new JLabel("Tạo Hóa Đơn");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		pnNorth.add(lblTitle);
		add(pnNorth, BorderLayout.NORTH);

		// === Nội dung chính ===
		JSplitPane pnCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		pnCenter.setResizeWeight(0.4); // Panel bên trái chiếm 40%

		// === Panel Tích Điểm ===
		JPanel pnTichDiem = buildTichDiemPanel();
		pnCenter.setLeftComponent(pnTichDiem);

		// === Panel Hóa Đơn ===
		JPanel pnHoaDon = buildHoaDonPanel();
		pnCenter.setRightComponent(pnHoaDon);

		add(pnCenter, BorderLayout.CENTER);
		addListeners();

		// Tải dữ liệu cho ComboBox
		loadDataToComboBox();
		loadDataToComboBoxKhuyenMai();
	}

	// Panel Tích điểm
	private JPanel buildTichDiemPanel() {
		JPanel pnTichDiem = new JPanel(new BorderLayout(10, 10));
		pnTichDiem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Tiêu đề
		JLabel td_title = new JLabel("Tích điểm khách hàng");
		td_title.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel tichdiem_title = new JPanel(new FlowLayout(FlowLayout.CENTER));
		tichdiem_title.add(td_title);
		pnTichDiem.add(tichdiem_title, BorderLayout.NORTH);

		// Nội dung (Tìm kiếm và Bảng)
		JPanel tichdiem_content = new JPanel(new BorderLayout(5, 5));

		// Tìm kiếm
		JPanel tichdiem_Search = new JPanel();
		tichdiem_Search.setLayout(new BoxLayout(tichdiem_Search, BoxLayout.X_AXIS));
		tichdiem_Search.add(new JLabel("Nhập SĐT:"));
		tichdiem_Search.add(txtTichDiemSearch = new JTextField(15));
		tichdiem_Search.add(btnTichDiemSearch = new JButton("Tìm"));
		tichdiem_content.add(tichdiem_Search, BorderLayout.NORTH);

		// Bảng thông tin khách hàng
		String[] cols = { "Mã khách hàng", "Tên khách hàng", "Điểm tích lũy" };
		tichDiemModel = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		tichDiemTable = new JTable(tichDiemModel);
		JScrollPane scrollTichDiem = new JScrollPane(tichDiemTable);
		scrollTichDiem.setPreferredSize(new Dimension(300, 200));
		tichdiem_content.add(scrollTichDiem, BorderLayout.CENTER);

		pnTichDiem.add(tichdiem_content, BorderLayout.CENTER);

		JPanel tichdiem_buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		tichdiem_buttons.add(btnDungDiem = new JButton("Dùng điểm"));
		tichdiem_buttons.add(btnCongDiem = new JButton("Cộng điểm"));
		pnTichDiem.add(tichdiem_buttons, BorderLayout.SOUTH);

		return pnTichDiem;
	}

	// Panel Hóa đơn
	private JPanel buildHoaDonPanel() {
		JPanel pnHoaDon = new JPanel(new BorderLayout(10, 10));
		pnHoaDon.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Tiêu đề
		JLabel hd_title = new JLabel("Hóa Đơn");
		hd_title.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel hoadon_title = new JPanel(new FlowLayout(FlowLayout.CENTER));
		hoadon_title.add(hd_title);
		pnHoaDon.add(hoadon_title, BorderLayout.NORTH);

		// Nội dung
		JPanel hoadon_content = new JPanel(new BorderLayout(10, 10));
		// Thông tin chung
		JPanel hoadon_info = new JPanel(new BorderLayout());

		Box boxInfoLeft = Box.createVerticalBox();

		Box row0 = Box.createHorizontalBox();
		row0.add(lblHoaDon = new JLabel("Mã hóa đơn: "));
		row0.add(lblHoaDonText = new JLabel("HD-xx"));
		boxInfoLeft.add(row0);
		boxInfoLeft.add(Box.createVerticalStrut(10));

		Box row1 = Box.createHorizontalBox();
		row1.add(lblNgayTao = new JLabel("Ngày tạo: "));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		row1.add(lblNgayTaoText = new JLabel(dtf.format(LocalDateTime.now())));
		boxInfoLeft.add(row1);
		boxInfoLeft.add(Box.createVerticalStrut(10));

		Dimension lblSize = new Dimension(80, 20);
		row0.getComponent(0).setPreferredSize(lblSize);
		row1.getComponent(0).setPreferredSize(lblSize);

		hoadon_info.add(boxInfoLeft, BorderLayout.WEST);

		JPanel pnKhuyenMai = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		hoadon_info.add(pnKhuyenMai, BorderLayout.EAST);
		hoadon_content.add(hoadon_info, BorderLayout.NORTH);

		String[] hoadon_cols = { "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền" };
		hoadon_model = new DefaultTableModel(hoadon_cols, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Không cho sửa trực tiếp trên bảng
			}
		};
		hoadon_table = new JTable(hoadon_model);
		hoadon_content.add(new JScrollPane(hoadon_table), BorderLayout.CENTER);
		cbKhuyenMai = new JComboBox<String>();
		pnKhuyenMai.add(cbKhuyenMai);
		pnHoaDon.add(hoadon_content, BorderLayout.CENTER);

		// Panel Thanh toán
		JPanel hoaDon_south = new JPanel(new BorderLayout(10, 5));
		hoaDon_south.setBorder(BorderFactory.createTitledBorder("Thanh toán"));

		// Khu vực PTTT
		JPanel pnPTTT = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnPTTT.add(new JLabel("Chọn PTTT:"));
		cbPTTT = new JComboBox<String>();
		cbPTTT.setPreferredSize(new Dimension(150, 25));
		pnPTTT.add(cbPTTT);
		hoaDon_south.add(pnPTTT, BorderLayout.WEST);

		// Khu vực Tổng tiền
		JPanel pnTongTien = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lblTongTienTitle = new JLabel("Tổng tiền: ");
		lblTongTienTitle.setFont(new Font("Arial", Font.BOLD, 16));
		lblTongTienValue = new JLabel("0.0 VND");
		lblTongTienValue.setFont(new Font("Arial", Font.BOLD, 16));
		lblTongTienValue.setForeground(Color.RED);
		pnTongTien.add(lblTongTienTitle);
		pnTongTien.add(lblTongTienValue);
		hoaDon_south.add(pnTongTien, BorderLayout.CENTER);

		JPanel pnThanhToanBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnThanhToan = new JButton("Thanh Toán");
		btnThanhToan.setFont(new Font("Arial", Font.BOLD, 14));
		btnThanhToan.setPreferredSize(new Dimension(120, 40));
		pnThanhToanBtn.add(btnThanhToan);
		hoaDon_south.add(pnThanhToanBtn, BorderLayout.EAST);

		pnHoaDon.add(hoaDon_south, BorderLayout.SOUTH);

		return pnHoaDon;
	}

	private void loadDataToComboBox() {
		// Tải PTTT
		ArrayList<PhuongThucThanhToan> dsPTTT = (ArrayList<PhuongThucThanhToan>) pttt_dao.layTatCa();
		DefaultComboBoxModel<String> ptttModel = new DefaultComboBoxModel<>();

		for (PhuongThucThanhToan pttt : dsPTTT) {
			ptttModel.addElement(pttt.getTenPTTT());
		}
		cbPTTT.setModel(ptttModel);
	}

	private void loadDataToComboBoxKhuyenMai() {
		ArrayList<KhuyenMai> dsKM = (ArrayList<KhuyenMai>) khuyenmai_dao.layTatCa();
		DefaultComboBoxModel<String> kmModel = new DefaultComboBoxModel<>();

		for (KhuyenMai km : dsKM) {
			kmModel.addElement(km.getTenKM());
		}
		cbKhuyenMai.setModel(kmModel);
	}

	public void setTrangThaiMacDinh() {
		// Panel Hóa đơn
		lblHoaDon.setText("Mã hóa đơn: " + "<Mới>");

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		lblNgayTao.setText("Ngày tạo: " + dtf.format(LocalDateTime.now()));

		hoadon_model.setRowCount(0);

		// Panel Thanh toán
		cbPTTT.setSelectedIndex(0);
		lblTongTienValue.setText("0.0 VND");
		btnThanhToan.setEnabled(false);

		// Panel Tích điểm
		txtTichDiemSearch.setText("");
		tichDiemModel.setRowCount(0);
		btnDungDiem.setEnabled(false);
		btnCongDiem.setEnabled(false);
	}

	private void addListeners() {
		btnTichDiemSearch.addActionListener(this);
		btnDungDiem.addActionListener(this);
		btnCongDiem.addActionListener(this);
		btnThanhToan.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnTichDiemSearch)) {
			String txtSearch = txtTichDiemSearch.getText().trim();
			KhachHang kh = kh_dao.timKhachHangTheoSDT(txtSearch);
			if (kh == null)
				return;
			tichDiemModel.addRow(new Object[] { kh.getMaKhachHang(), kh.getHoTen(), kh.getDiemTichLuy() });
		} else if (o.equals(btnThanhToan)) {
			JOptionPane.showMessageDialog(this, "Thanh toán thành công. Hóa đơn được lưu vào c:/..");
			setTrangThaiMacDinh();
		} else if (o == btnDungDiem) {
			String lblTongTien = lblTongTienValue.getText().trim().substring(0,
					lblTongTienValue.getText().trim().length() - 3);
			double tongTien = Double.parseDouble(lblTongTien.trim());
			if (tongTien == 0 || tichDiemModel.getRowCount() == 0)
				return;
			int diemTichLuy = (int) tichDiemModel.getValueAt(0, 1);
			if (diemTichLuy >= tongTien) {
				int diemTichLuyMoi = (int) (diemTichLuy - tongTien);
				kh_dao.capNhatDiemTichLuy(tichDiemModel.getValueAt(0, 0).toString().trim(), diemTichLuyMoi);
			} else {
				int diemTichLuyMoi = 0;
				int tongTienMoi = (int) (tongTien - diemTichLuy);
				lblTongTienValue.setText(tongTienMoi + "");
				kh_dao.capNhatDiemTichLuy(tichDiemModel.getValueAt(0, 0).toString().trim(), diemTichLuyMoi);
			}

			JOptionPane.showMessageDialog(this, "Dùng điểm thành công");
		} else if (o == btnCongDiem) {
			if (tichDiemModel.getRowCount() == 0)
				return;
			String lblTongTien = lblTongTienValue.getText().trim().substring(0,
					lblTongTienValue.getText().trim().length() - 3);
			double tongTien = Double.parseDouble(lblTongTien.trim());
			int diemTichLuyMoi = (int) (tongTien / 10);
			int diemTichLuyCu = (int) tichDiemModel.getValueAt(0, 2);
			kh_dao.capNhatDiemTichLuy(tichDiemModel.getValueAt(0, 0).toString(), diemTichLuyCu + diemTichLuyMoi);
			JOptionPane.showMessageDialog(this, "Cập nhật điểm tích lũy thành công");
		}
	}

	public void nhanDanhSachSanPham(ArrayList<Object[]> orderData) {
		// TODO Auto-generated method stub
		for (Object[] rowData : orderData) {
			String tenSP = (String) rowData[0];
			int soLuong = (int) rowData[1];
			double donGia = (double) rowData[2];
			nhanSanPhamTuMenu(tenSP, soLuong, donGia);
		}
	}

	private void nhanSanPhamTuMenu(String tenSP, int soLuong, double donGia) {
		// TODO Auto-generated method stub
		for (int i = 0; i < hoadon_model.getRowCount(); i++) {
			String tenSanPham_hoadon = hoadon_model.getValueAt(i, 0).toString();
			if (tenSanPham_hoadon.equals(tenSP)) {
				int soLuongMoi = (int) hoadon_model.getValueAt(i, 1) + soLuong;
				double thanhTien = soLuongMoi * donGia;
				hoadon_model.setValueAt(soLuongMoi, i, 1);
				hoadon_model.setValueAt(donGia, i, 2);
				hoadon_model.setValueAt(thanhTien, i, 3);
				capNhatTongTien();
				return;
			}
		}

		double thanhTien = soLuong * donGia;
		Object[] rowData = new Object[] { tenSP, soLuong, donGia, thanhTien };
		hoadon_model.addRow(rowData);
		capNhatTongTien();

	}

	private void capNhatTongTien() {
		double tongTien = 0;
		for (int i = 0; i < hoadon_model.getRowCount(); i++) {
			tongTien += (double) hoadon_model.getValueAt(i, 3);
		}
		lblTongTienValue.setText(String.format("%,.00f VND", tongTien));
		btnThanhToan.setVisible(tongTien > 0);
	}
}