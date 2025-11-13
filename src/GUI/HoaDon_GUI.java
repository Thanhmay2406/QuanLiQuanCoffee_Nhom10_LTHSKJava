/*
 * @ (#) HoaDon_GUI.java   1.1     Nov 10, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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

import DAO.ChiTietHoaDon_DAO;
import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import DAO.KhuyenMai_DAO;
import DAO.PhuongThucThanhToan_DAO;
import DAO.SanPham_DAO;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.KhuyenMai;
import Entity.PhuongThucThanhToan;
import Entity.SanPham;

/*
* @description
* @author: Van Long 
* @date: Nov 10, 2025
* @version: 1.1
*/

public class HoaDon_GUI extends JPanel implements ActionListener {

	private KhuyenMai_DAO khuyenmai_dao;
	private PhuongThucThanhToan_DAO pttt_dao;

	private MainFrame mainFrame;

	private JTextField txtTichDiemSearch;
	private JButton btnTichDiemSearch;
	private JButton btnTaoTaiKhoan;
	private DefaultTableModel tichDiemModel;
	private JTable tichDiemTable;
	private JButton btnDungDiem;

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
	private JLabel lblGhiChu;
	private JLabel lblGhiChuValue;
	private double tongTienHienTai;
	private boolean isDungDiem = false;
	private HoaDon_DAO hd_dao;
	private String maKhachHang_; // lưu để dùng cho lưu file HoaDon
	private String maHoaDon_; // lấy mã hóa đơn từ hàm tạo
	private ChiTietHoaDon_DAO ct_dao;
	private SanPham_DAO sp_dao;

	public HoaDon_GUI(MainFrame mainFrame) {
		this.khuyenmai_dao = new KhuyenMai_DAO();
		this.pttt_dao = new PhuongThucThanhToan_DAO();
		this.menu_gui = new Menu_GUI(mainFrame);
		this.kh_dao = new KhachHang_DAO();
		this.hd_dao = new HoaDon_DAO();
		this.ct_dao = new ChiTietHoaDon_DAO();
		this.sp_dao = new SanPham_DAO();
		this.mainFrame = mainFrame;
		setLayout(new BorderLayout());

		// Tiêu đề chính
		JPanel pnNorth = new JPanel();
		JLabel lblTitle = new JLabel("Tạo Hóa Đơn");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		pnNorth.add(lblTitle);
		add(pnNorth, BorderLayout.NORTH);

		// Nội dung chính
		JSplitPane pnCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		pnCenter.setResizeWeight(0.4);
		// Panel Tích Điểm
		JPanel pnTichDiem = buildTichDiemPanel();
		pnCenter.setLeftComponent(pnTichDiem);

		// Panel Hóa Đơn
		JPanel pnHoaDon = buildHoaDonPanel();
		pnCenter.setRightComponent(pnHoaDon);

		add(pnCenter, BorderLayout.CENTER);
		addListeners();

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

		// content
		JPanel tichdiem_content = new JPanel(new BorderLayout(5, 5));

		// Tìm kiếm
		JPanel tichdiem_Search = new JPanel();
		tichdiem_Search.setLayout(new BoxLayout(tichdiem_Search, BoxLayout.X_AXIS));
		tichdiem_Search.add(new JLabel("Nhập SĐT:"));
		tichdiem_Search.add(txtTichDiemSearch = new JTextField(15));
		tichdiem_Search.add(btnTichDiemSearch = new JButton("Tìm"));
		tichdiem_content.add(tichdiem_Search, BorderLayout.NORTH);

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
		tichdiem_buttons.add(btnTaoTaiKhoan = new JButton("Đăng ký"));
		tichdiem_buttons.add(btnDungDiem = new JButton("Dùng điểm"));
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
		row0.setAlignmentX(Component.LEFT_ALIGNMENT);
		boxInfoLeft.add(row0);
		boxInfoLeft.add(Box.createVerticalStrut(10));

		Box row1 = Box.createHorizontalBox();
		row1.add(lblNgayTao = new JLabel("Ngày tạo: "));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		row1.add(lblNgayTaoText = new JLabel(dtf.format(LocalDateTime.now())));
		row1.setAlignmentX(Component.LEFT_ALIGNMENT);

		boxInfoLeft.add(row1);
		boxInfoLeft.add(Box.createVerticalStrut(10));

		Box row2 = Box.createHorizontalBox();
		row2.add(lblGhiChu = new JLabel("Ghi chú: "));
		row2.add(lblGhiChuValue = new JLabel(""));
		row2.setAlignmentX(Component.LEFT_ALIGNMENT);
		boxInfoLeft.add(row2);
		boxInfoLeft.add(Box.createVerticalStrut(10));
		Dimension lblSize = new Dimension(80, 20);
		row0.getComponent(0).setPreferredSize(lblSize);
		row1.getComponent(0).setPreferredSize(lblSize);
		row2.getComponent(0).setPreferredSize(lblSize);
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
		kmModel.addElement("Chọn khuyến mãi");
		for (KhuyenMai km : dsKM) {
			kmModel.addElement(km.getTenKM());
		}
		cbKhuyenMai.setModel(kmModel);
	}

	public void setTrangThaiMacDinh() {
		// Panel Hóa đơn
		lblHoaDonText.setText("HDxxx");

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		lblNgayTaoText.setText(dtf.format(LocalDateTime.now()));

		hoadon_model.setRowCount(0);

		// Panel Thanh toán
		cbPTTT.setSelectedIndex(0);
		lblTongTienValue.setText("0.0 VND");
		btnThanhToan.setEnabled(false);

		// Panel Tích điểm
		txtTichDiemSearch.setText("");
		tichDiemModel.setRowCount(0);
		btnDungDiem.setEnabled(false);
	}

	private void addListeners() {
		btnTichDiemSearch.addActionListener(this);
		btnDungDiem.addActionListener(this);
		btnThanhToan.addActionListener(this);
		btnTaoTaiKhoan.addActionListener(this);
		cbKhuyenMai.addActionListener(this);
		cbPTTT.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnTichDiemSearch)) {
			String txtSearch = txtTichDiemSearch.getText().trim();
			KhachHang kh = kh_dao.timKhachHangTheoSDT(txtSearch);
			if (kh == null) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng. Tìm lại hoặc Đăng ký");
				return;
			}
			maKhachHang_ = kh.getMaKhachHang(); // lấy mã cho việc đọc file
			tichDiemModel.addRow(new Object[] { kh.getMaKhachHang(), kh.getHoTen(), kh.getDiemTichLuy() });
		} else if (o.equals(btnThanhToan)) {
			luuHoaDonXuongFile();
			// sau khi thanh toán, cộng điểm tích lũy (khách hàng không dùng điểm)
			if (!isDungDiem) {
				if (tichDiemModel.getRowCount() != 0) {
					double diemTichLuyMoi = (tongTienHienTai / 10);
					double diemTichLuyCu = (double) tichDiemModel.getValueAt(0, 2);
					kh_dao.capNhatDiemTichLuy(tichDiemModel.getValueAt(0, 0).toString(),
							diemTichLuyCu + diemTichLuyMoi);
					JOptionPane.showMessageDialog(this, "Cập nhật điểm tích lũy thành công");
					tichDiemModel.setRowCount(0);
				}
			}

		} else if (o == btnDungDiem) {
			double tongTien = tongTienHienTai;
			if (tongTien == 0)
				return;
			if (tichDiemModel.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng tìm khách hàng cần Cộng điểm");
				txtTichDiemSearch.grabFocus();
			}
			double diemTichLuy = (double) tichDiemModel.getValueAt(0, 2);
			if (diemTichLuy >= tongTien) {
				double diemTichLuyMoi = (diemTichLuy - tongTien);
				kh_dao.capNhatDiemTichLuy(tichDiemModel.getValueAt(0, 0).toString().trim(), diemTichLuyMoi);
			} else {
				double diemTichLuyMoi = 0;
				double tongTienMoi = (tongTien - diemTichLuy);
				lblTongTienValue.setText(tongTienMoi + "");
				kh_dao.capNhatDiemTichLuy(tichDiemModel.getValueAt(0, 0).toString().trim(), diemTichLuyMoi);
			}
			tichDiemModel.setRowCount(0);
			JOptionPane.showMessageDialog(this, "Dùng điểm thành công");
			isDungDiem = true; // khách hàng dùng điểm
		} else if (o == btnTaoTaiKhoan) {
			mainFrame.switchToPanel(mainFrame.KEY_KHACH_HANG);
		} else if (o.equals(cbKhuyenMai)) {
			String selected = cbKhuyenMai.getSelectedItem().toString().trim();
			KhuyenMai km = khuyenmai_dao.timKhuyenMaiTheoTen(selected);
			if (km == null) {
				System.out.println("Không tìm thấy khuyến mãi");
				return;
			}
			double tongTienSauKM = tongTienHienTai - (tongTienHienTai * km.getphanTramGiam());
			lblTongTienValue.setText(String.format("%,.2f VND", tongTienSauKM));
		}
	}

	public void nhanDanhSachSanPham(ArrayList<Object[]> orderData) {
		// TODO Auto-generated method stub
		String ghiChuChung = "";
		for (Object[] rowData : orderData) {
			String tenSP = (String) rowData[0];
			int soLuong = (int) rowData[1];
			double donGia = (double) rowData[2];
			if (ghiChuChung.isEmpty()) {
				ghiChuChung = (String) rowData[3];
			}
			nhanSanPhamTuMenu(tenSP, soLuong, donGia);
		}
		lblGhiChuValue.setText(ghiChuChung);
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
			tongTien += Double.parseDouble(hoadon_model.getValueAt(i, 3).toString());
		}
		this.tongTienHienTai = tongTien;
		lblTongTienValue.setText(String.format("%,.2f VND", tongTien));
		btnThanhToan.setVisible(tongTien > 0);
	}

	public String taoMaHoaDon() {
		String maHoaDonCuoi = hd_dao.getMaHoaDonCuoiCung();
		if (maHoaDonCuoi == null) {
			maHoaDon_ = "HD001";
			return maHoaDon_;
		}
		try {
			String phanSo = maHoaDonCuoi.substring(2);
			int soHienTai = Integer.parseInt(phanSo);
			soHienTai++;
			String phanSoMoi = String.format("%03d", soHienTai);
			maHoaDon_ = "HD" + phanSoMoi;
			return maHoaDon_;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maHoaDon_ = "HD_ERROR";
		return maHoaDon_;
	}

	public void luuHoaDonXuongFile() {
		String maHD = taoMaHoaDon();
		LocalDate ngayTao = LocalDate.now();
		String ghiChu = lblGhiChuValue.getText().trim();
		int trangThaiThanhToan = 1; // đã thanh toán
		String maKM;
		KhuyenMai km = khuyenmai_dao.timKhuyenMaiTheoTen(cbKhuyenMai.getSelectedItem().toString().trim());
		if (km == null) {
			maKM = "";
		} else {
			maKM = km.getMaKM();
		}
		String cbpttt = cbPTTT.getSelectedItem().toString();
		String maNV = "NV001";
		String maKH = maKhachHang_;
		PhuongThucThanhToan pttt = pttt_dao.layPTTTTheoTen(cbpttt);
		String maPTTT = pttt == null ? null : pttt.getMaPTTT();
		HoaDon hoaDon = new HoaDon(maHD, ngayTao, ghiChu, trangThaiThanhToan, maKH, maNV, maKM, maPTTT);

		ArrayList<ChiTietHoaDon> dsCT = new ArrayList<ChiTietHoaDon>();
		for (int i = 0; i < hoadon_model.getRowCount(); i++) {
			String tenSP = hoadon_model.getValueAt(i, 0).toString().trim();
			SanPham sp = sp_dao.timSanPhamTheoTen(tenSP);

			if (sp == null) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm " + tenSP);
				return;
			}
			Number donGiaNum = (Number) hoadon_model.getValueAt(i, 2);
			BigDecimal donGia = BigDecimal.valueOf(donGiaNum.doubleValue());
			int soLuong = (int) hoadon_model.getValueAt(i, 1);
			ChiTietHoaDon ct = new ChiTietHoaDon(hoaDon, sp, soLuong, donGia);
			dsCT.add(ct);
		}

		hoaDon.setDsChiTiet(dsCT);

		if (hd_dao.themHoaDon(hoaDon)) {
			System.out.println("Lưu thành công hóa đơn và chi tiết vào database thành công");
			xuatHoaDonThanhFileTxt();
			setTrangThaiMacDinh();
			mainFrame.setTrangThaiHoaDon(false);
		} else {
			JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi lưu. Giao dịch đã được hủy. Vui lòng thử lại.");
		}

	}

	public void xuatHoaDonThanhFileTxt() {
		String projectPath = System.getProperty("user.dir");
		String folderPath = projectPath + File.separator + "file_hoadon";

		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdir();
		}

		String filePath = "HoaDon" + File.separator + maHoaDon_ + ".txt";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {

			writer.write("**************************************************\n");
			writer.write("                QUÁN CAFE LMK");
			writer.write("      12 Nguyễn Văn Bảo, P.4, Q.Gò Vấp, TPHCM\n");
			writer.write("**************************************************\n\n");

			writer.write("             HÓA ĐƠN BÁN HÀNG\n\n");
			writer.write("Mã HĐ:       " + maHoaDon_ + "\n");
			writer.write("Ngày tạo:    " + lblNgayTaoText.getText() + "\n");
			writer.write("Nhân viên:   NV001\n");

			if (tichDiemModel.getRowCount() > 0) {
				writer.write("Khách hàng:  " + tichDiemModel.getValueAt(0, 1) + "\n");
			} else {
				writer.write("Khách hàng:  Khách vãng lai\n");
			}
			writer.write("--------------------------------------------------\n");

			writer.write(String.format("%-25s %3s %10s %10s\n", "Tên Sản Phẩm", "SL", "Đơn Giá", "Thành Tiền"));
			writer.write("--------------------------------------------------\n");

			for (int i = 0; i < hoadon_model.getRowCount(); i++) {
				String tenSP = hoadon_model.getValueAt(i, 0).toString();
				if (tenSP.length() > 25) {
					tenSP = tenSP.substring(0, 22) + "...";
				}

				int sl = (int) hoadon_model.getValueAt(i, 1);
				double donGia = Double.parseDouble(hoadon_model.getValueAt(i, 2).toString());
				double thanhTien = Double.parseDouble(hoadon_model.getValueAt(i, 3).toString());

				writer.write(String.format("%-25s %3d %10.0f %10.0f\n", tenSP, sl, donGia, thanhTien));
			}
			writer.write("--------------------------------------------------\n");

			writer.write(String.format("%-38s %12.0f VND\n", "Tổng tiền:", this.tongTienHienTai));

			writer.write(String.format("%-38s %12s\n", "Phương thức thanh toán:", cbPTTT.getSelectedItem()));
			writer.write(String.format("%-38s %12s\n", "Ghi chú:", lblGhiChuValue.getText()));

			writer.write("\n\n             Cảm ơn quý khách!\n");

			JOptionPane.showMessageDialog(this, "Xuất hóa đơn TXT thành công!\nĐã lưu tại: " + filePath);

		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Lỗi khi xuất file TXT: " + e.getMessage());
		}
	}
}