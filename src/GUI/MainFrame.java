/*
 * @ (#) Interface.java   1.0     Oct 31, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/*
* @description
* @author: Van Long
* @date: Oct 31, 2025
* @version: 1.0
*/
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

	// Key là tên file
	private static final String KEY_DAT_BAN = "DAT_BAN";

	private static final String KEY_BAN_HANG = "BAN_HANG";
	private static final String KEY_SAN_PHAM = "SAN_PHAM";
	private static final String KEY_GIAM_GIA = "GIAM_GIA";
	private static final String KEY_KHACH_HANG = "KHACH_HANG";
	private static final String KEY_THONG_KE = "THONG_KE";

	private CardLayout cardLayout;
	private JPanel contentPanel;
	private JPanel navPanel;
	private List<JButton> navButtons;

	public MainFrame() {
		setTitle("Quản Lý Quán Coffee");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// ----------Thanh điều hướng------------
		navPanel = new JPanel();
		navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
		navPanel.setBackground(new Color(230, 240, 255));
		navPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		navButtons = new ArrayList<>();

		// ---------Nội dung---------
		cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);

		// ---------gắn sự kiện------------
		ActionListener navListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = e.getActionCommand();
				cardLayout.show(contentPanel, key);
				updateNavButtonStyles((JButton) e.getSource());
			}
		};

		// Thêm các panel con (các trang) vào CardLayout với "Key" tương ứng

		// Trang đặt bàn
		JPanel panelDatBan = new JPanel();
		panelDatBan.add(new JLabel("Đây là Đặt bàn"));
		contentPanel.add(panelDatBan, KEY_DAT_BAN);

		// Trang Bán Hàng
		JPanel panelBanHang = new JPanel();
		panelBanHang.add(new JLabel("Đây là trang bán hàng"));
		contentPanel.add(panelBanHang, KEY_BAN_HANG);

		// Trang Sản Phẩm
		JPanel panelSanPham = new JPanel();
		panelSanPham.add(new JLabel("Đây là trang Sản Phẩm"));
		contentPanel.add(panelSanPham, KEY_SAN_PHAM);

		// Trang giam giá
		JPanel panelGiamGia = new JPanel();
		panelGiamGia.setBackground(Color.WHITE);
		panelGiamGia.add(new JLabel("Đây là trang Quản lý khách hàng"));
		contentPanel.add(panelGiamGia, KEY_GIAM_GIA);

		// Trang Khách Hàng
		JPanel panelKhachHang = new JPanel();
		panelKhachHang.add(new JLabel("Đây là trang Quản lý Mã Giảm Giá"));
		contentPanel.add(panelKhachHang, KEY_KHACH_HANG);
		// Trang thống kê
		JPanel panelThongKe = new JPanel();
		panelThongKe.add(new JLabel("Đây là trang Thống kê"));
		contentPanel.add(panelThongKe, KEY_THONG_KE);

		// gán key
		// Thêm các nút vào navPanel

		addNavButton("Đặt bàn", "/img/icon-home.png", KEY_DAT_BAN, navListener);
		addNavButton("Menu", "/img/icon-menu.png", KEY_BAN_HANG, navListener);
		addNavButton("Sản phẩm", "/img/icon-product.png", KEY_SAN_PHAM, navListener);
		addNavButton("Khách hàng", "/img/icon-user.png", KEY_GIAM_GIA, navListener);
		addNavButton("Khuyến mãi", "/img/icon-discount.png", KEY_KHACH_HANG, navListener);
		addNavButton("Thống kê", "/img/icon-statistic.png", KEY_THONG_KE, navListener);

		add(navPanel, BorderLayout.WEST);
		add(contentPanel, BorderLayout.CENTER);

		cardLayout.show(contentPanel, KEY_DAT_BAN);
		if (!navButtons.isEmpty()) {
			updateNavButtonStyles(navButtons.get(0));
		}
	}

	private void addNavButton(String text, String iconPath, String actionCommand, ActionListener listener) {
		ImageIcon icon = null;
		try {
			java.net.URL imgURL = getClass().getResource(iconPath);
			if (imgURL != null) {
				icon = new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			} else {
				System.err.println("Không tìm thấy file icon: " + iconPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JButton button = new JButton(text, icon);
		button.setActionCommand(actionCommand);
		button.addActionListener(listener);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		navPanel.add(button);
		navButtons.add(button);
		navPanel.add(Box.createRigidArea(new Dimension(0, 5)));
	}

	private void updateNavButtonStyles(JButton selectedButton) {
		for (JButton button : navButtons) {
			if (button == selectedButton) {
				button.setContentAreaFilled(true);
				button.setOpaque(true);

				button.setBackground(new Color(190, 215, 240));

				button.setFont(new Font("Arial", Font.BOLD, 14));
				button.setForeground(Color.BLACK);
			} else {
				button.setContentAreaFilled(false);
				button.setOpaque(false);

				button.setFont(new Font("Arial", Font.PLAIN, 14));
				button.setForeground(Color.BLACK);
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new MainFrame().setVisible(true);
		});
	}
}
