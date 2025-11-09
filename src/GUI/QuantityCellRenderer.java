/*
 * @ (#) QuantityCellRenderer.java   1.0     Nov 8, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package GUI;
/*
* @description
* @author: Van Long
* @date: Nov 8, 2025
* @version: 1.0
*/

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

// Lớp này dùng để *VẼ* panel +/- lên ô của JTable
public class QuantityCellRenderer extends JPanel implements TableCellRenderer {

	private JButton btnMinus;
	private JButton btnPlus;
	private JLabel lblQuantity;

	public QuantityCellRenderer() {
		super(new FlowLayout(FlowLayout.CENTER, 5, 0));
		btnMinus = new JButton("-");
		btnPlus = new JButton("+");
		lblQuantity = new JLabel("1");

		add(btnMinus);
		add(lblQuantity);
		add(btnPlus);

		// Chỉ để vẽ, không cho tương tác
		btnMinus.setEnabled(false);
		btnPlus.setEnabled(false);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		// Lấy giá trị số lượng từ JTable và set cho label
		lblQuantity.setText(value != null ? value.toString() : "0");

		// Set màu nền khi ô được chọn
		if (isSelected) {
			setBackground(table.getSelectionBackground());
		} else {
			setBackground(table.getBackground());
		}

		return this; // Trả về chính panel này để vẽ
	}
}
