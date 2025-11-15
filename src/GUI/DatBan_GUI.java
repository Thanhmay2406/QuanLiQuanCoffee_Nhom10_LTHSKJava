/*
 * @ (#) DatBan_GUI.java   1.0     Nov 7, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package GUI;
/*
* @description
* @author: Van Long
* @date: Nov 7, 2025
* @version: 1.0
*/

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DAO.ChiTietDatBan_DAO;
import DAO.PhieuDatBan_DAO;
import Entity.ChiTietDatBan;
import Entity.PhieuDatBan;

public class DatBan_GUI extends JPanel implements ActionListener, ComponentListener {

	private JLabel title;
	private JButton btnSearch, btnChonBan, btnChonMon;
	private JTextField txtSearch;
	private DefaultTableModel modelTabel;
	private JTable table;
	private PhieuDatBan_DAO pdb_dao;
	private MainFrame mainFrame;
	private JLabel lblSearch;
	private ChiTietDatBan_DAO ctdb_dao;
	private JButton btnHuyPhieu;

	public DatBan_GUI(MainFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.mainFrame = mainFrame;
		this.pdb_dao = new PhieuDatBan_DAO();
		this.ctdb_dao = new ChiTietDatBan_DAO();
		this.setLayout(new BorderLayout());
		// -------North--------
		JPanel pnNorth = new JPanel();
		pnNorth.add(title = new JLabel("Quản lý Đặt bàn"));
		Font fnt = new Font("Arial", Font.BOLD, 20);
		title.setFont(fnt);
		// --------Center--------
		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		searchPanel.add(lblSearch = new JLabel("Nhập số điện thoại: "));
		searchPanel.add(txtSearch = new JTextField(10));
		searchPanel.add(btnSearch = new JButton("Tìm kiếm"));
		pnCenter.add(searchPanel, BorderLayout.NORTH);
		pnCenter.add(Box.createVerticalStrut(10));
		JPanel tableData = new JPanel();
		tableData.setLayout(new BorderLayout());
		String[] cols = { "Mã phiếu đặt bàn", "Ngày đặt bàn", "Giờ băt đầu", "Giờ kết thúc", "Số người", "Ghi chú",
				"Trạng thái", "Mã khách hàng", "Mã nhân viên", "Số điện thoại" };
		modelTabel = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return col != 0;
			}
		};
		table = new JTable(modelTabel);
		JScrollPane sp = new JScrollPane(table);
		sp.setBorder(BorderFactory.createEmptyBorder());
		tableData.add(sp, BorderLayout.CENTER);
		pnCenter.add(tableData, BorderLayout.CENTER);

		// -------South---------
		JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnSouth.add(btnHuyPhieu = new JButton("Hủy phiếu"));
		pnSouth.add(Box.createHorizontalStrut(10));
		pnSouth.add(btnChonBan = new JButton("Chọn bàn"));
		pnSouth.add(btnChonMon = new JButton("Chọn món"));

		add(pnNorth, BorderLayout.NORTH);
		add(pnCenter, BorderLayout.CENTER);
		add(pnSouth, BorderLayout.SOUTH);
		loadPhieuDatBan();
		this.addComponentListener(this);
		btnChonBan.addActionListener(this);
		btnChonMon.addActionListener(this);
		btnSearch.addActionListener(this);
		btnHuyPhieu.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o == btnChonMon) {
			chonMon();
		}
		if (o == btnChonBan) {
			chonBan();
		}
		if (o == btnSearch) {
			timKiemPhieuDatBan();
		}
		if (o == btnHuyPhieu) {
			huyPhieu();
		}
	}

	private void huyPhieu() {
		// TODO Auto-generated method stub
		int row_selected = table.getSelectedRow();
		if (row_selected != -1) {
			String maPDB = modelTabel.getValueAt(row_selected, 0).toString();
			int hoiNhac = JOptionPane.showConfirmDialog(this, "Chắc chắn hủy phiếu " + maPDB, "Xác nhận",
					JOptionPane.YES_NO_OPTION);
			if (hoiNhac == JOptionPane.YES_OPTION) {
				if (pdb_dao.capNhatTrangThaiPhieu(maPDB, 0)) {
					JOptionPane.showMessageDialog(this, "Đã xóa phiếu " + maPDB);
					loadPhieuDatBan();
				}
			}
		}
	}

	private void timKiemPhieuDatBan() {
		// TODO Auto-generated method stub
		String sdt = txtSearch.getText().trim();
		if (sdt == null || sdt.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại");
			return;
		}

		try {
			ArrayList<PhieuDatBan> dsPDB = pdb_dao.layPhieuDatBanTheoSoDienThoai(sdt);
			updateTableData(dsPDB);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private void chonBan() {
		// TODO Auto-generated method stub
		mainFrame.switchToPanel(mainFrame.KEY_CHON_BAN);
	}

	private void chonMon() {
		// TODO Auto-generated method stub
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			String maPhieu = modelTabel.getValueAt(selectedRow, 0).toString();
			String trangThai = modelTabel.getValueAt(selectedRow, 6).toString();

			if (trangThai.equals("Chưa sử dụng")) {
				try {
					pdb_dao.capNhatTrangThaiPhieu(maPhieu, 2); // trangThai = 1 (đang chờ)
					mainFrame.switchToPanel(mainFrame.KEY_BAN_HANG);
					modelTabel.setValueAt("Đang sử dụng", selectedRow, 6); // set "Đã sử dụng" khi nhấn thanh toán thành
																			// công
					// lấy maBan, maPDB lưu vào trung gian MainFrame
					ArrayList<String> dsMaBan = layMaBanTuCTDB(maPhieu);
					String maKhachHang = modelTabel.getValueAt(selectedRow, 7).toString();
					mainFrame.setMaKhachHang(maKhachHang);
					mainFrame.setMaPhieuDatBan(maPhieu);
					mainFrame.setDsMaBan(dsMaBan);

				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(selectedRow);
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Phiếu này đã được hoặc đang được sử dụng");
				return;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt bàn");
			return;
		}

	}

	private ArrayList<String> layMaBanTuCTDB(String maPhieu) {
		ArrayList<String> dsMaBan = new ArrayList<String>();
		ArrayList<ChiTietDatBan> dsCT = (ArrayList<ChiTietDatBan>) ctdb_dao.layChiTietTheoMaPDB(maPhieu);
		for (ChiTietDatBan ct : dsCT) {
			dsMaBan.add(ct.getBan().getMaBan());
		}
		return dsMaBan;
		// TODO Auto-generated method stub

	}

	public void loadPhieuDatBan() {
		modelTabel.setRowCount(0);
		try {
			ArrayList<PhieuDatBan> dsPDB = (ArrayList<PhieuDatBan>) pdb_dao.layPhieuDatBanHopLe();
			updateTableData(dsPDB);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void updateTableData(ArrayList<PhieuDatBan> dsPDB) {
		modelTabel.setRowCount(0);
		try {
			for (PhieuDatBan pdb : dsPDB) {
				String trangThai = "";
				int trangThai_pdb = pdb.getTrangThai();
				if (trangThai_pdb == 1) {
					trangThai = "Chưa sử dụng";
				} else if (trangThai_pdb == 0) {
					trangThai = "Đã hủy";
				} else if (trangThai_pdb == 2) {
					trangThai = "Đã sử dụng";
				} else if (trangThai_pdb == 3) {
					trangThai = "Hết hạn";
				}
				modelTabel.addRow(
						new Object[] { pdb.getMaPhieuDat(), pdb.getNgayDat(), pdb.getGioBatDau(), pdb.getGioKetThuc(),
								pdb.getSoNguoi(), pdb.getGhiChu(), trangThai, pdb.getKhachHang().getMaKhachHang(),
								pdb.getNhanVien().getMaNhanVien(), pdb.getKhachHang().getSoDienThoai() });
			}
		} catch (

		Exception e) {
			// TODO: handle exception
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
		// TODO Auto-generated method stub
		loadPhieuDatBan();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}
