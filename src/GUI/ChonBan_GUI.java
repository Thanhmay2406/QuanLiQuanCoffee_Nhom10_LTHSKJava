/*
 * @ (#) ChonBan_GUI.java   1.0     Nov 7, 2025
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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChonBan_GUI extends JPanel implements ActionListener {
	private MainFrame mainFrame;
	private JLabel title;

	public ChonBan_GUI(MainFrame mainFrame) {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		JPanel pnNorth = new JPanel();
		pnNorth.add(title = new JLabel("Chọn bàn"));
		Font fnt = new Font("Arial", Font.BOLD, 20);
		title.setFont(fnt);

		add(pnNorth, BorderLayout.NORTH);

		JButton btnQuayLai = new JButton("Quay lại");
		btnQuayLai.addActionListener(e -> {
			mainFrame.swicthToPanel(mainFrame.KEY_DAT_BAN);
		});
		add(btnQuayLai);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
