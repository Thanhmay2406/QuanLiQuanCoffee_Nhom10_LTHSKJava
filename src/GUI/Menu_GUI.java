package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import DAO.LoaiSanPham_DAO;
import DAO.SanPham_DAO;
import Entity.LoaiSanPham;
import Entity.SanPham;

// Đảm bảo bạn đã extends JPanel và có hàm khởi tạo nhận MainFrame
public class Menu_GUI extends JPanel implements ActionListener {

	private MainFrame mainFrame;
	private JTable orderTable, menuTable;
	private DefaultTableModel orderModel, menuModel;
	private JLabel lblTotalOrder;
	private JButton btnDat, btnTaoHoaDon, btnTrangChu;
	private JComboBox<String> cbFilter;
	private JTextField txtSearch;

	// Định nghĩa chỉ số cột để dễ quản lý
	private final int COL_TEN_SP = 0;
	private final int COL_SO_LUONG = 1;
	private final int COL_GIA = 2; // Cột ẩn chứa đơn giá
	private final int COL_TONG_TIEN = 3; // Cột hiển thị tổng tiền
	private JButton btnXoa;
	private SanPham_DAO sp_dao;
	private LoaiSanPham_DAO loaiSP_dao;

	public Menu_GUI(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.sp_dao = new SanPham_DAO();
		this.loaiSP_dao = new LoaiSanPham_DAO();
		setLayout(new BorderLayout());

		// ----- NORTH: Tiêu đề "Menu" -----
		JLabel title = new JLabel("Menu", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		add(title, BorderLayout.NORTH);

		// ----- CENTER: Hai panel chính -----
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createOrderPanel(), createMenuPanel());
		splitPane.setResizeWeight(0.4); // Panel Order chiếm 40%
		add(splitPane, BorderLayout.CENTER);

		// ----- SOUTH: Các nút footer -----
		JPanel pnSouth = new JPanel(new BorderLayout());
		btnTrangChu = new JButton("Trang chủ");
		btnTaoHoaDon = new JButton("Tạo hóa đơn");
		pnSouth.add(btnTrangChu, BorderLayout.WEST);
		pnSouth.add(btnTaoHoaDon, BorderLayout.EAST);
		add(pnSouth, BorderLayout.SOUTH);

		// ----- Thêm sự kiện -----
		btnDat.addActionListener(this);
		btnTaoHoaDon.addActionListener(this);
		btnTrangChu.addActionListener(this);
		btnXoa.addActionListener(this);
		cbFilter.addActionListener(this);
	}

	// Panel "Order" bên trái
	private JPanel createOrderPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Order", SwingConstants.CENTER), BorderLayout.NORTH);

		String[] orderCols = { "Tên sản phẩm", "Số lượng", "Đơn Giá", "Tổng tiền" };
		orderModel = new DefaultTableModel(orderCols, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Chỉ cho phép sửa cột "Số lượng"
				return column == COL_SO_LUONG;
			}
			@Override
			public void setValueAt(Object aValue, int row, int column) {
				// Khi số lượng thay đổi
				if (column == COL_SO_LUONG) {
					int quantity = (Integer) aValue;
					if (quantity < 0)
						quantity = 0; // Không cho số lượng âm

					super.setValueAt(quantity, row, column);

					// Lấy đơn giá từ cột ẩn
					double price = (Double) getValueAt(row, COL_GIA);
					// Cập nhật cột tổng tiền
					super.setValueAt(quantity * price, row, COL_TONG_TIEN);
				} else {
					super.setValueAt(aValue, row, column);
				}
			}
		};

		orderTable = new JTable(orderModel);

		// ----- ĐÂY LÀ PHẦN QUAN TRỌNG NHẤT -----
		// Gắn Renderer và Editor tùy chỉnh vào cột "Số lượng"
		orderTable.getColumnModel().getColumn(COL_SO_LUONG).setCellRenderer(new QuantityCellRenderer());
		orderTable.getColumnModel().getColumn(COL_SO_LUONG).setCellEditor(new QuantityCellEditor());
		orderTable.setRowHeight(35); // Tăng chiều cao hàng để vừa các nút

		// Ẩn cột "Đơn Giá" (dùng để tính toán)
		orderTable.getColumnModel().removeColumn(orderTable.getColumn("Đơn Giá"));
		// ------------------------------------

		// Thêm listener để cập nhật tổng tiền ở footer
		orderModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				updateTotalOrder();
			}
		});

		panel.add(new JScrollPane(orderTable), BorderLayout.CENTER);

		btnXoa = new JButton("Xóa");
		lblTotalOrder = new JLabel("Tổng tiền: 0 VND", SwingConstants.RIGHT);
		lblTotalOrder.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel pnSouth = new JPanel(new BorderLayout());
		pnSouth.add(btnXoa, BorderLayout.WEST);
		pnSouth.add(lblTotalOrder, BorderLayout.EAST);
		panel.add(pnSouth, BorderLayout.SOUTH);

		return panel;
	}

	// Panel "Menu" bên phải
	private JPanel createMenuPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		// Panel lọc và tìm kiếm
		JPanel pnFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ArrayList<LoaiSanPham> dsLSP = (ArrayList<LoaiSanPham>) loaiSP_dao.layTatCa();
		String loaiSP = "Tất cả,";
		for (LoaiSanPham lsp : dsLSP) {
			loaiSP += lsp.getTenLoai() + ",";
		}
		loaiSP = (String) loaiSP.substring(0, loaiSP.length());
		String[] cbLoai = loaiSP.split(",");
		cbFilter = new JComboBox<>(cbLoai);
		txtSearch = new JTextField(15);
		pnFilter.add(cbFilter);
		pnFilter.add(new JLabel("Tìm kiếm:"));
		pnFilter.add(txtSearch);
		panel.add(pnFilter, BorderLayout.NORTH);

		// Bảng danh sách menu
		String[] menuCols = { "Hình ảnh", "Tên sản phẩm", "Đơn vị tính", "Loại", "Giá" };
		menuModel = new DefaultTableModel(menuCols, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				if (columnIndex == 0) {
					return Icon.class;
				}
				return super.getColumnClass(columnIndex);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Không cho sửa bảng menu
			}

		};

		menuTable = new JTable(menuModel);
		menuTable.setRowHeight(50);
		menuTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		DefaultTableCellRenderer contenntRender = new DefaultTableCellRenderer();
		contenntRender.setHorizontalAlignment(JLabel.CENTER);
		menuTable.setDefaultRenderer(Object.class, contenntRender);
		// Tạo filter cho Jtable
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(menuModel);
		menuTable.setRowSorter(sorter);
		panel.add(new JScrollPane(menuTable), BorderLayout.CENTER);

		// Nút "Đặt"
		JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnDat = new JButton("Đặt");
		pnSouth.add(btnDat);
		panel.add(pnSouth, BorderLayout.SOUTH);

		// Thêm dữ liệu giả lập
		loadMenuData();
		return panel;
	}

	private ImageIcon editAnhSanPham(String imgPath, int width, int height) {
		try {
			java.net.URL imgURL = getClass().getResource(imgPath);
			if (imgURL == null) {
				System.err.println("Không tìm thấy file" + imgPath);
				return null;
			}

			ImageIcon anhGoc = new ImageIcon(imgURL);
			Image img = anhGoc.getImage();

			// Tạo ảnh mới với kênh alpha (để giữ độ trong suốt)
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = bi.createGraphics();

			// Bật chế độ scaling mượt
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

			// Vẽ ảnh cũ vào ảnh mới
			g2d.drawImage(img, 0, 0, width, height, null);
			g2d.dispose();
			return new ImageIcon(bi);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	// Tải dữ liệu giả lập cho bảng menu
	private void loadMenuData() {
		ArrayList<SanPham> dsSP = (ArrayList<SanPham>) sp_dao.layTatCa();
		for (SanPham sp : dsSP) {
			String imgPath = "/img/" + sp.gethinhAnh().trim();
			ImageIcon icon = editAnhSanPham(imgPath, 50, 45);
			LoaiSanPham lsp = loaiSP_dao.layTheoMaLoai(sp.getLoaiSP().getMaLoaiSP());
			String tenlsp;
			if (lsp == null)
				tenlsp = "";
			else {
				tenlsp = lsp.getTenLoai();
			}

			menuModel.addRow(new Object[] { icon, sp.getTenSanPham(), sp.getDonViTinh(), tenlsp, sp.getGia() });
		}
	}

	// Cập nhật tổng tiền ở footer của panel Order
	private void updateTotalOrder() {
		double total = 0;
		for (int i = 0; i < orderModel.getRowCount(); i++) {
			total += (Double) orderModel.getValueAt(i, COL_TONG_TIEN);
		}
		lblTotalOrder.setText(String.format("Tổng tiền: %,.0f VND", total));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o == btnDat) {
			// Xử lý nút "Đặt"
			int selectedRow = menuTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một món trong Menu trước.");
				return;
			}
			int selectedRowInModel = menuTable.convertRowIndexToModel(selectedRow);

			String tenSP = menuModel.getValueAt(selectedRowInModel, 1).toString();
			double donGia = Double.parseDouble(menuModel.getValueAt(selectedRowInModel, 4).toString());

			// Kiểm tra xem món đã có trong order chưa
			for (int i = 0; i < orderModel.getRowCount(); i++) {
				if (orderModel.getValueAt(i, COL_TEN_SP).equals(tenSP)) {
					JOptionPane.showMessageDialog(this, "Món này đã có trong Order.");
					return;
				}
			}

			// Thêm món mới vào order với số lượng 1
			orderModel.addRow(new Object[] { tenSP, 1, donGia, donGia });
		} else if (o == btnTrangChu) {
			// Quay về trang chủ (nếu có)
			mainFrame.switchToPanel(MainFrame.KEY_DAT_BAN); // Ví dụ quay về Đặt Bàn
		} else if (o == btnXoa) {
			int[] selected_rows = orderTable.getSelectedRows();
			if (selected_rows.length != 0) {
				for (int i = selected_rows.length - 1; i >= 0; i--) {
					int row_index = selected_rows[i];
					// nếu có chức năng lọc, sắp xếp
					// int row_index = orderTabel.covertRowIndexToModel(row_index);
					orderModel.removeRow(row_index);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết để xóa");
			}
		} else if (o == cbFilter) {
			actionFilter();
		} else if (o == btnTaoHoaDon) {
			taoHoaDon();
		}
	}

	private void taoHoaDon() {
		// TODO Auto-generated method stub
		if (menuModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm nào");
			return;
		}

		ArrayList<Object[]> orderData = new ArrayList<Object[]>();
		for (int i = 0; i < orderModel.getRowCount(); i++) {
			String tenSP = orderModel.getValueAt(i, COL_TEN_SP).toString();
			int soLuong = (int) orderTable.getValueAt(i, COL_SO_LUONG);
			double donGia = (double) orderTable.getValueAt(i, COL_TONG_TIEN);

			orderData.add(new Object[] { tenSP, soLuong, donGia });

		}
		mainFrame.chuyenDanhSachOrderSangHoaDon(orderData);
		orderModel.setRowCount(0);
		mainFrame.switchToPanel(mainFrame.KEY_HOA_DON);
	}

	private void actionFilter() {
		menuTable.clearSelection(); // làm mới dòng được chọn
		TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) menuTable.getRowSorter();
		RowFilter<DefaultTableModel, Object> rf = null;
		String filter = (String) cbFilter.getSelectedItem();
		int filterColumn = 3;
		if (filter.equals("Tất cả")) {
			sorter.setRowFilter(null);
			return;
		}
		try {
			rf = RowFilter.regexFilter(filter, filterColumn);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
		sorter.setRowFilter(rf);
	}
}