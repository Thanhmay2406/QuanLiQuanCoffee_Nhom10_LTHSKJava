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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DAO.KhuyenMai_DAO;
import DAO.PhuongThucThanhToan_DAO;
import Entity.KhuyenMai;
import Entity.PhuongThucThanhToan;
// Giả sử bạn có MainFrame để lấy thông tin nhân viên
// import Core.MainFrame; 

/*
* @description
* @author: Van Long (đã được Gemini chỉnh sửa và bổ sung)
* @date: Nov 10, 2025
* @version: 1.1
*/

public class HoaDon_GUI extends JPanel implements ActionListener, ComponentListener {

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
		setLayout(new BorderLayout());

		// === Tiêu đề chính ===
		JPanel pnNorth = new JPanel();
		JLabel lblTitle = new JLabel("Tạo Hóa Đơn");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		pnNorth.add(lblTitle);
		add(pnNorth, BorderLayout.NORTH);

		// === Nội dung chính (Chia đôi) ===
		JSplitPane pnCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		pnCenter.setResizeWeight(0.4); // Panel bên trái chiếm 40%

		// === Panel Tích Điểm (Bên Trái) ===
		JPanel pnTichDiem = buildTichDiemPanel();
		pnCenter.setLeftComponent(pnTichDiem);

		// === Panel Hóa Đơn (Bên Phải) ===
		JPanel pnHoaDon = buildHoaDonPanel();
		pnCenter.setRightComponent(pnHoaDon);

		add(pnCenter, BorderLayout.CENTER);

		// Thêm listeners
		addListeners();

		// Thêm ComponentListener để phát hiện khi panel này được hiển thị
		addComponentListener(this);

		// Tải dữ liệu cho ComboBox
		loadDataToComboBox();
		loadDataToComboBoxKhuyenMai();
	}

	/**
	 * Xây dựng Panel Tích điểm (bên trái)
	 */
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
		String[] cols = { "Tên khách hàng", "Điểm tích lũy" };
		tichDiemModel = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		tichDiemTable = new JTable(tichDiemModel);
		JScrollPane scrollTichDiem = new JScrollPane(tichDiemTable);
		// Đặt kích thước mong muốn cho bảng để JSplitPane hoạt động tốt
		scrollTichDiem.setPreferredSize(new Dimension(300, 200));
		tichdiem_content.add(scrollTichDiem, BorderLayout.CENTER);

		pnTichDiem.add(tichdiem_content, BorderLayout.CENTER);

		// Các nút chức năng
		JPanel tichdiem_buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		tichdiem_buttons.add(btnDungDiem = new JButton("Dùng điểm"));
		tichdiem_buttons.add(btnCongDiem = new JButton("Cộng điểm"));
		pnTichDiem.add(tichdiem_buttons, BorderLayout.SOUTH);

		return pnTichDiem;
	}

	/**
	 * Xây dựng Panel Hóa đơn (bên phải)
	 */
	private JPanel buildHoaDonPanel() {
		JPanel pnHoaDon = new JPanel(new BorderLayout(10, 10));
		pnHoaDon.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Tiêu đề
		JLabel hd_title = new JLabel("Hóa Đơn");
		hd_title.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel hoadon_title = new JPanel(new FlowLayout(FlowLayout.CENTER));
		hoadon_title.add(hd_title);
		pnHoaDon.add(hoadon_title, BorderLayout.NORTH);

		// Nội dung (Thông tin và Bảng chi tiết)
		JPanel hoadon_content = new JPanel(new BorderLayout(10, 10));

		// Thông tin chung (Mã HĐ, Ngày, NV, Khuyến mãi)
		JPanel hoadon_info = new JPanel(new BorderLayout());

		// Box cho Mã HĐ, Ngày, NV
		Box boxInfoLeft = Box.createVerticalBox();

		Box row0 = Box.createHorizontalBox();
		row0.add(new JLabel("Mã hóa đơn:"));
		row0.add(Box.createHorizontalStrut(5));
		row0.add(txtMaHoaDon = new JTextField(15));
		boxInfoLeft.add(row0);
		boxInfoLeft.add(Box.createVerticalStrut(10));

		Box row1 = Box.createHorizontalBox();
		row1.add(new JLabel("Ngày tạo: "));
		row1.add(Box.createHorizontalStrut(5));
		row1.add(txtNgayTao = new JTextField(15));
		boxInfoLeft.add(row1);
		boxInfoLeft.add(Box.createVerticalStrut(10));

		// Căn chỉnh các label cho đều
		Dimension lblSize = new Dimension(80, 20); // Đặt kích thước chung
		row0.getComponent(0).setPreferredSize(lblSize);
		row1.getComponent(0).setPreferredSize(lblSize);

		hoadon_info.add(boxInfoLeft, BorderLayout.WEST);

		// Nút Khuyến mãi (bên phải)
		JPanel pnKhuyenMai = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		hoadon_info.add(pnKhuyenMai, BorderLayout.EAST);
		hoadon_content.add(hoadon_info, BorderLayout.NORTH);

		// Bảng chi tiết hóa đơn
		String[] hoadon_cols = { "Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền" };
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

		// Panel Thanh toán (dưới cùng)
		JPanel hoaDon_south = new JPanel(new BorderLayout(10, 5));
		hoaDon_south.setBorder(BorderFactory.createTitledBorder("Thanh toán"));

		// Khu vực PTTT (Tây)
		JPanel pnPTTT = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnPTTT.add(new JLabel("Chọn PTTT:"));
		cbPTTT = new JComboBox<String>();
		cbPTTT.setPreferredSize(new Dimension(150, 25));
		pnPTTT.add(cbPTTT);
		hoaDon_south.add(pnPTTT, BorderLayout.WEST);

		// Khu vực Tổng tiền (Trung tâm, căn phải)
		JPanel pnTongTien = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lblTongTienTitle = new JLabel("Tổng tiền: ");
		lblTongTienTitle.setFont(new Font("Arial", Font.BOLD, 16));
		lblTongTienValue = new JLabel("0.0 VND");
		lblTongTienValue.setFont(new Font("Arial", Font.BOLD, 16));
		lblTongTienValue.setForeground(Color.RED);
		pnTongTien.add(lblTongTienTitle);
		pnTongTien.add(lblTongTienValue);
		hoaDon_south.add(pnTongTien, BorderLayout.CENTER);

		// Khu vực Nút Thanh toán (Đông)
		JPanel pnThanhToanBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnThanhToan = new JButton("Thanh Toán");
		btnThanhToan.setFont(new Font("Arial", Font.BOLD, 14));
		btnThanhToan.setPreferredSize(new Dimension(120, 40));
		pnThanhToanBtn.add(btnThanhToan);
		hoaDon_south.add(pnThanhToanBtn, BorderLayout.EAST);

		pnHoaDon.add(hoaDon_south, BorderLayout.SOUTH);

		return pnHoaDon;
	}

	/**
	 * Tải dữ liệu cho các ComboBox (ví dụ: PTTT)
	 */
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

	/**
	 * Thiết lập trạng thái mặc định cho giao diện (khi tạo hóa đơn mới)
	 */
	public void setTrangThaiMacDinh() {
		// Panel Hóa đơn
		txtMaHoaDon.setText("<Mới>");
		txtMaHoaDon.setEditable(false);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		txtNgayTao.setText(dtf.format(LocalDateTime.now()));
		txtNgayTao.setEditable(false);

		// Giả sử MainFrame có phương thức này
		// txtNhanVien.setText(mainFrame.getNhanVienDangNhap().getTenNhanVien());

		hoadon_model.setRowCount(0); // Xóa sạch bảng

		// Panel Thanh toán
		cbPTTT.setSelectedIndex(0); // Chọn PTTT mặc định (ví dụ: Tiền mặt)
		lblTongTienValue.setText("0.0 VND");
		btnThanhToan.setEnabled(false); // Chưa có hàng thì không cho thanh toán

		// Panel Tích điểm
		txtTichDiemSearch.setText("");
		tichDiemModel.setRowCount(0); // Xóa bảng khách hàng
		btnDungDiem.setEnabled(false); // Chưa tìm thấy KH thì không cho dùng
		btnCongDiem.setEnabled(false);
	}

	/**
	 * Gắn các trình xử lý sự kiện
	 */
	private void addListeners() {
		btnTichDiemSearch.addActionListener(this);
		btnDungDiem.addActionListener(this);
		btnCongDiem.addActionListener(this);
		btnThanhToan.addActionListener(this);
	}

	private void loadHoaDon() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnTichDiemSearch)) {
			// Xử lý tìm khách hàng
			System.out.println("Nút Tìm SĐT được nhấn");
			// Sau khi tìm thấy -> setEnabled(true) cho btnDungDiem, btnCongDiem
		} else if (o.equals(btnThanhToan)) {
			// Xử lý thanh toán
			System.out.println("Nút Thanh toán được nhấn");
			// 1. Thu thập dữ liệu
			// 2. Gọi DAO để lưu
			// 3. Nhận về Hóa Đơn đã lưu (có mã HĐ)
			// 4. Cập nhật txtMaHoaDon.setText(...)
			// 5. Vô hiệu hóa các nút (setEditable(false) cho mọi thứ)
		}
	}

	// === Các sự kiện của ComponentListener ===

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
		// Khi panel này được hiển thị (ví dụ: chuyển tab),
		// chúng ta thiết lập lại trạng thái ban đầu.
		System.out.println("Panel Hóa Đơn được hiển thị -> Đang reset về trạng thái mặc định.");
		setTrangThaiMacDinh();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
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

	}
}