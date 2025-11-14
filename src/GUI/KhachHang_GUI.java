/*
 * @ (#) KhachHang_GUI.java   1.0     Nov 7, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DAO.KhachHang_DAO;
import Entity.DanhSach_KhachHang;
import Entity.KhachHang;

/*
* @description
* @author: Van Long
* @date: Nov 7, 2025
* @version: 1.0
*/

public class KhachHang_GUI extends JPanel implements ActionListener, MouseListener {
	private DanhSach_KhachHang ds;

	private MainFrame mainFrame;

	private JTextField txtMaKH, txtHoTen, txtSDT, txtEmail, txtDiaChi, txtDiemTichLuy, txtNgayDangKy, txtSearch;
	private JButton btnSearch, btnDelete, btnHome, btnSave, btnAdd, btnUpdate;

	private DefaultTableModel tableModel;
	private JTable table;
	private String[] headerTable = { "Mã KH", "Họ tên", "STĐ", "Email", "Địa chỉ", "Điểm tích lũy", "Ngày đăng ký" };

	public KhachHang_GUI(MainFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.mainFrame = mainFrame;

		ds = new DanhSach_KhachHang();

		setLayout(new BorderLayout());

		/* ======================NORTH===================== */
		JPanel pnNorth = new JPanel();
		JLabel title = new JLabel("KHÁCH HÀNG");
		title.setFont(new Font("Arial", Font.BOLD, 26));
		pnNorth.add(title);
		add(pnNorth, BorderLayout.NORTH);

		/* ======================CENTER===================== */
		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new BorderLayout());

		// up
		JPanel pnUp = new JPanel();
		pnUp.setLayout(new BorderLayout());
		pnUp.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

		JPanel pnForm = new JPanel();
		pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));

		txtMaKH = new JTextField(50);
		txtMaKH.setEditable(false);
		txtHoTen = new JTextField(50);
		txtSDT = new JTextField(50);
		txtEmail = new JTextField(50);
		txtDiaChi = new JTextField(50);
		txtDiemTichLuy = new JTextField(20);
		txtDiemTichLuy.setEditable(false);
		txtNgayDangKy = new JTextField(20);
		txtNgayDangKy.setEditable(false);

		JPanel row1 = new JPanel();
		row1.add(new JLabel("Mã khách hàng: "));
		row1.add(txtMaKH);
		pnForm.add(row1);

		JPanel row2 = new JPanel();
		row2.add(Box.createHorizontalStrut(45));
		row2.add(new JLabel("Họ tên: "));
		row2.add(txtHoTen);
		pnForm.add(row2);

		JPanel row3 = new JPanel();
		row3.add(Box.createHorizontalStrut(5));
		row3.add(new JLabel("Số điện thoại: "));
		row3.add(txtSDT);
		pnForm.add(row3);

		JPanel row4 = new JPanel();
		row4.add(Box.createHorizontalStrut(47));
		row4.add(new JLabel("Email: "));
		row4.add(txtEmail);
		pnForm.add(row4);

		JPanel row5 = new JPanel();
		row5.add(Box.createHorizontalStrut(42));
		row5.add(new JLabel("Địa chỉ: "));
		row5.add(txtDiaChi);
		pnForm.add(row5);

		JPanel row6 = new JPanel();
		row6.add(new JLabel("Điểm tích lũy: "));
		row6.add(txtDiemTichLuy);
		row6.add(new JLabel("Ngày đăng ký: "));
		row6.add(txtNgayDangKy);
		pnForm.add(row6);

		pnUp.add(pnForm, BorderLayout.CENTER);

		JPanel row7 = new JPanel();
		btnAdd = new JButton("Thêm");
		btnDelete = new JButton("Xóa");
		btnUpdate = new JButton("Cập nhật");
		row7.add(btnAdd);
		row7.add(Box.createHorizontalStrut(10));
		row7.add(btnDelete);
		row7.add(Box.createHorizontalStrut(10));
		row7.add(btnUpdate);

		pnUp.add(row7, BorderLayout.SOUTH);

		pnCenter.add(pnUp, BorderLayout.NORTH);

		// down
		JPanel pnDown = new JPanel();
		tableModel = new DefaultTableModel(headerTable, 0);
		table = new JTable(tableModel);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);

		JScrollPane scroll = new JScrollPane(table);

		scroll.setPreferredSize(new Dimension(850, 300));

		pnDown.add(scroll);

		pnCenter.add(pnDown, BorderLayout.CENTER);

		add(pnCenter, BorderLayout.CENTER);

		/* ======================SOUTH===================== */
		JPanel pnSouth = new JPanel();

		pnSouth.add(new JLabel("Nhập STĐ cần tìm: "));
		txtSearch = new JTextField(20);
		pnSouth.add(txtSearch);
		btnSearch = new JButton("Tìm kiếm");
		pnSouth.add(btnSearch);
		pnSouth.add(Box.createHorizontalStrut(30));
		btnHome = new JButton("Trang chủ");
		pnSouth.add(Box.createHorizontalStrut(10));
		btnSave = new JButton("Lưu");
		pnSouth.add(btnHome);

		pnSouth.add(Box.createHorizontalStrut(10));
		pnSouth.add(btnSave);

		add(pnSouth, BorderLayout.SOUTH);

		/* ======================EVENT===================== */
		loadDSKH();
		btnAdd.addActionListener(this);
		btnDelete.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnSearch.addActionListener(this);
		btnHome.addActionListener(this);
		btnSave.addActionListener(this);
		table.addMouseListener(this);
	}

	public void loadDSKH() {
		try {
			ds = new DanhSach_KhachHang();
			updateTableData(ds.getDs());
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Có lỗi khi tải dữ liệu lên bảng");
		}
	}

	public void updateTableData(ArrayList<KhachHang> ds) {
		tableModel.setRowCount(0);
		try {
			for (KhachHang it : ds) {
				tableModel.addRow(new Object[] { it.getMaKhachHang(), it.getHoTen(), it.getSoDienThoai(), it.getEmail(),
						it.getDiaChi(), it.getDiemTichLuy(), it.getNgayDangKy() });
			}
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Có lỗi khi cập nhật bảng");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnAdd)) {
			themKhachHang();
		} else if (o.equals(btnDelete)) {
			xoaKH();
		} else if (o.equals(btnUpdate)) {
			capNhat();
		} else if (o.equals(btnSearch)) {
			timKiem();
		} else if (o.equals(btnHome)) {

		} else if (o.equals(btnSave)) {
			luu();
		}
	}

	public void clearTXT() {
		txtMaKH.setText("");
		txtHoTen.setText("");
		txtSDT.setText("");
		txtEmail.setText("");
		txtDiaChi.setText("");
		txtDiemTichLuy.setText("");
		txtNgayDangKy.setText("");
		txtHoTen.requestFocus();
	}

	public void themKhachHang() {
		try {
			String maKH = txtMaKH.getText();
			String hoTen = txtHoTen.getText();
			String std = txtSDT.getText();
			String email = txtEmail.getText();
			String diaChi = txtDiaChi.getText();
			if (maKH.equals("")) {
				ds.themKhachHang(hoTen, std, email, diaChi);
				updateTableData(ds.getDs());
				clearTXT();
			} else {
				JOptionPane.showMessageDialog(null, "Không thể thêm khách hàng (do khách hàng đã tồn tại)");
			}
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
			clearTXT();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Có lỗi khi thêm khách hàng");
			clearTXT();
			return;
		}
	}

	public void xoaKH() {
		int row = table.getSelectedRow();
		if (row != -1) {
			String maKH = table.getModel().getValueAt(row, 0).toString();
			int hoiNhac = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không?", "Xác nhận",
					JOptionPane.YES_NO_OPTION);

			if (hoiNhac == JOptionPane.YES_OPTION) {
				if (ds.xoaKhachHang(maKH)) {
					tableModel.removeRow(row);
				}
			}
		}
	}

	public void capNhat() {
		try {
			String maKH = txtMaKH.getText();
			String hoTen = txtHoTen.getText();
			String std = txtSDT.getText();
			String email = txtEmail.getText();
			String diaChi = txtDiaChi.getText();
			double diemTichLuy = Double.parseDouble(txtDiemTichLuy.getText());
			LocalDate ngayDangKy = LocalDate.parse(txtNgayDangKy.getText());
			if (maKH.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Khách hàng chưa tồn tại");
				return;
			}

			KhachHang X = new KhachHang(maKH, hoTen, std, email, diaChi, diemTichLuy, ngayDangKy);
			ds.capNhatKH(X);
			updateTableData(ds.getDs());
			JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công");
			clearTXT();
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
			clearTXT();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Có lỗi khi cập nhật khách hàng");
		}
	}
	
	public void timKiem() {
		String s = txtSearch.getText();
		if (s.isEmpty()) {
			JOptionPane.showMessageDialog(null, "chưa nhập số điện thoại");
			txtSearch.setText("");
			txtSearch.requestFocus();
			return;
		}
		
		int row = ds.timKiem_STD(s);
		
		table.setRowSelectionInterval(row, 0);
		txtMaKH.setText(table.getValueAt(row, 0).toString());
		txtHoTen.setText(table.getValueAt(row, 1).toString());
		txtSDT.setText(table.getValueAt(row, 2).toString());
		txtEmail.setText(table.getValueAt(row, 3).toString());
		txtDiaChi.setText(table.getValueAt(row, 4).toString());
		txtDiemTichLuy.setText(table.getValueAt(row, 5).toString());
		txtNgayDangKy.setText(table.getValueAt(row, 6).toString());
	}
	
	public void luu() {
		if (ds.luuTatCa()) {
			JOptionPane.showMessageDialog(null, "Lưu thành công");
		}
		else {
			JOptionPane.showMessageDialog(null, "Lưu thất bại");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		if (row >= 0) {
			txtMaKH.setText(table.getValueAt(row, 0).toString());
			txtHoTen.setText(table.getValueAt(row, 1).toString());
			txtSDT.setText(table.getValueAt(row, 2).toString());
			txtEmail.setText(table.getValueAt(row, 3).toString());
			txtDiaChi.setText(table.getValueAt(row, 4).toString());
			txtDiemTichLuy.setText(table.getValueAt(row, 5).toString());
			txtNgayDangKy.setText(table.getValueAt(row, 6).toString());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
