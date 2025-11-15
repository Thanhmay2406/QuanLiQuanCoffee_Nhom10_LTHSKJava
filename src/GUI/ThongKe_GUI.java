/*
 * @ (#) ThongKe_GUI.java   1.0     Nov 7, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import DAO.ThongKe_DAO;
import Entity.KhachHang;
import Entity.doanhThuSP;

/*
* @description
* @author: Van Long
* @date: Nov 7, 2025
* @version: 1.0
*/

public class ThongKe_GUI extends JPanel implements ActionListener {
	private MainFrame mainFrame;
	
	private String[] cols = {"Sản phẩm", "Số lượng bán ra", "Doanh thu"};
	private JTable table;
	private DefaultTableModel tableModel;
	
	private ThongKe_DAO daoTK;
	private ArrayList<doanhThuSP> arr;

	public ThongKe_GUI(MainFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.mainFrame = mainFrame;
		daoTK = new ThongKe_DAO();
		
		/*
		 * tổng doanh thu tháng này
		 * số lượng sản phẩm bán ra tháng này
		 * sản phẩm bán chạy nhất
		 * */
		setLayout(new BorderLayout());
		
		/*===================NORTH================*/
		JPanel pnNorth = new JPanel();
		JLabel title = new JLabel("THỐNG KÊ");
		title.setFont(new Font("Arial", Font.BOLD, 26));
		pnNorth.add(title);
		add(pnNorth, BorderLayout.NORTH);
		
		/*===================CENTER================*/
		JPanel pnCenter = new JPanel();
		JSplitPane pnSplit = new JSplitPane();
		JPanel pnLeft = new JPanel();
		pnLeft.setPreferredSize(new Dimension(400, 500));
		JPanel pnRight = new JPanel();
		pnRight.setPreferredSize(new Dimension(400, 500));
		
		pnSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight);
		
		// left
		pnLeft.setLayout(new BorderLayout());
		JLabel titleLeft = new JLabel("Tổng quan trong tháng này");
		titleLeft.setFont(new Font("Arial", Font.BOLD, 20));
		JPanel pnTitleLeft = new JPanel();
		pnTitleLeft.add(titleLeft);
		pnLeft.add(pnTitleLeft, BorderLayout.NORTH);
		
		JPanel pnCenterLeft = new JPanel();
		pnCenterLeft.setLayout(new GridLayout(3, 1));
		
		JLabel lb1 = new JLabel("Tổng doanh thu: ");
		JPanel pnlb1 = new JPanel();
		pnlb1.add(lb1);
		lb1.setFont(new Font("Arial", Font.ITALIC, 20));
		
		JLabel lb2 = new JLabel("Tổng số lượng sản phẩm bán ra:");
		JPanel pnlb2 = new JPanel();
		pnlb2.add(lb2);
		lb2.setFont(new Font("Arial", Font.ITALIC, 20));
		
		JLabel lb3 = new JLabel("Sản phẩm bán chạy nhất: ");
		JPanel pnlb3 = new JPanel();
		pnlb3.add(lb3);
		lb3.setFont(new Font("Arial", Font.ITALIC, 20));
		
		JLabel ansLb1 = new JLabel( String.valueOf(daoTK.tongDoanhThu()) );
		ansLb1.setFont(new Font("Arial", Font.BOLD, 18));
		ansLb1.setForeground(Color.RED);
		JPanel pnAns1 = new JPanel();
		pnAns1.add(ansLb1);
		
		JLabel ansLb2 = new JLabel(String.valueOf(daoTK.soLuongSP()));
		ansLb2.setFont(new Font("Arial", Font.BOLD, 18));
		ansLb2.setForeground(Color.RED);
		JPanel pnAns2 = new JPanel();
		pnAns2.add(ansLb2);
		
		JLabel ansLb3 = new JLabel(daoTK.sanPhamBanChayNhat());
		ansLb3.setFont(new Font("Arial", Font.BOLD, 18));
		ansLb3.setForeground(Color.RED);
		JPanel pnAns3 = new JPanel();
		pnAns3.add(ansLb3);
		
		JPanel row1 = new JPanel();
		row1.setLayout(new BoxLayout(row1, BoxLayout.Y_AXIS));
		row1.add(pnlb1);
		row1.add(pnAns1);
		
		JPanel row2 = new JPanel();
		row2.setLayout(new BoxLayout(row2, BoxLayout.Y_AXIS));
		row2.add(pnlb2);
		row2.add(pnAns2);
		
		JPanel row3 = new JPanel();
		row3.setLayout(new BoxLayout(row3, BoxLayout.Y_AXIS));
		row3.add(pnlb3);
		row3.add(pnAns3);
		
		pnCenterLeft.add(row1);
		row1.setBorder(new LineBorder(Color.BLACK, 1));
		
		pnCenterLeft.add(row2);
		row2.setBorder(new LineBorder(Color.BLACK, 1));
		
		pnCenterLeft.add(row3);
		row3.setBorder(new LineBorder(Color.BLACK, 1));
		
		pnLeft.add(pnCenterLeft, BorderLayout.CENTER);
		
		// right
		pnRight.setLayout(new BorderLayout());
		JLabel titleRight = new JLabel("Danh sách sản phẩm bán chạy");
		titleRight.setFont(new Font("Arial", Font.BOLD, 20));
		JPanel pnTitleRight = new JPanel();
		pnTitleRight.add(titleRight);
		
		arr = daoTK.DSDoanhThuSP();
		loadTable();
		
		pnRight.add(pnTitleRight, BorderLayout.NORTH);
		
		tableModel = new DefaultTableModel(cols, 0);
		table = new JTable(tableModel);
		JScrollPane pnScroll = new JScrollPane(table);
		
		pnRight.add(pnScroll);
		
		pnCenter.add(pnSplit);
		add(pnCenter, BorderLayout.CENTER);
	}
	
	public void loadTable() {
//		tableModel.setRowCount(0);
		try {
			for (doanhThuSP it : arr) {
				tableModel.addRow(new Object[] { it.getTen(), it.getSl(), it.getTongTien() });
			}
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Có lỗi khi cập nhật bảng");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
