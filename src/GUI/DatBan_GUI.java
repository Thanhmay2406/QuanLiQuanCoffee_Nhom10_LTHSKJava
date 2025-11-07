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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DAO.PhieuDatBan_DAO;
import Entity.PhieuDatBan;

public class DatBan_GUI extends JPanel implements ActionListener, MouseListener {

	private JLabel title;
	private JButton btnSearch, btnChonBan, btnChonMon;
	private JTextField txtSearch;
	private DefaultTableModel modelTabel;
	private JTable table;
	private PhieuDatBan_DAO pdb_dao;

	public DatBan_GUI() {
		// TODO Auto-generated constructor stub

		setLayout(new BorderLayout());
		// -------North--------
		JPanel pnNorth = new JPanel();
		pnNorth.add(title = new JLabel("Quản lý Đặt bàn"));
		Font fnt = new Font("Arial", Font.BOLD, 20);
		title.setFont(fnt);
		// --------Center--------
		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		searchPanel.add(txtSearch = new JTextField(10));
		searchPanel.add(btnSearch = new JButton("Tìm kiếm"));
		pnCenter.add(searchPanel, BorderLayout.NORTH);
		pnCenter.add(Box.createVerticalStrut(10));
		JPanel tableData = new JPanel();
		tableData.setLayout(new BorderLayout());
		String[] cols = { "Mã phiếu đặt bàn", "Ngày đặt bàn", "Giờ băt đầu", "Giờ kết thúc", "Số người", "Ghi chú",
				"Trạng thái", "Mã khách hàng", "Mã nhân viên" };
		modelTabel = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return row != 0;
			}
		};
		table = new JTable(modelTabel);
		JScrollPane sp = new JScrollPane(table);
		sp.setBorder(BorderFactory.createEmptyBorder());
		tableData.add(sp, BorderLayout.CENTER);
		pnCenter.add(tableData, BorderLayout.CENTER);

		// -------South---------
		JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnSouth.add(btnChonBan = new JButton("Chọn bàn"));
		pnSouth.add(btnChonMon = new JButton("Chọn món"));

		add(pnNorth, BorderLayout.NORTH);
		add(pnCenter, BorderLayout.CENTER);
		add(pnSouth, BorderLayout.SOUTH);

		// Hiển thị danh sách Phiếu đặt bàn
		ArrayList<PhieuDatBan> dsPDB = new ArrayList<PhieuDatBan>();
		pdb_dao = new PhieuDatBan_DAO();
		try {
			dsPDB = (ArrayList<PhieuDatBan>) pdb_dao.layTatCa();
			for (PhieuDatBan pdb : dsPDB) {
				String trangThai;
				int trangThai_pdb = pdb.getTrangThai();
				if (trangThai_pdb == 0) {
					trangThai = "Chưa thanh toán";
				} else if (trangThai_pdb == 1) {
					trangThai = "Đã thanh toán";
				}
				modelTabel.addRow(new Object[] { pdb.getMaPhieuDat(), pdb.getGioBatDau(), pdb.getGioKetThuc(),
						pdb.getSoNguoi(), pdb.getGhiChu(), });
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
