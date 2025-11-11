package GUI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class QuantityCellEditor extends AbstractCellEditor implements TableCellEditor {

	private JPanel panel;
	private JButton btnMinus;
	private JButton btnPlus;
	private JLabel lblQuantity;
	private int currentQuantity;

	public QuantityCellEditor() {
		panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		btnMinus = new JButton("-");
		btnPlus = new JButton("+");
		lblQuantity = new JLabel();

		panel.add(btnMinus);
		panel.add(lblQuantity);
		panel.add(btnPlus);

		btnPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentQuantity++;
				lblQuantity.setText(String.valueOf(currentQuantity));

				// ===== THAY ĐỔI QUAN TRỌNG Ở ĐÂY =====
				// Báo cho JTable rằng việc chỉnh sửa đã xong
				// để nó cập nhật giá trị mới vào model NGAY LẬP TỨC
				fireEditingStopped();
			}
		});

		btnMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentQuantity > 0) { // Không cho số lượng < 0
					currentQuantity--;
					lblQuantity.setText(String.valueOf(currentQuantity));

					fireEditingStopped();
				}
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// Khi bắt đầu edit, lấy giá trị hiện tại
		currentQuantity = (Integer) value;
		lblQuantity.setText(String.valueOf(currentQuantity));

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}

		return panel;
	}

	@Override
	public Object getCellEditorValue() {
		// Khi edit xong, trả về giá trị số lượng mới
		return currentQuantity;
	}

	@Override
	public boolean stopCellEditing() {
		// Hàm này được gọi khi JTable muốn dừng edit (ví dụ: bấm sang ô khác)
		// Chúng ta gọi fireEditingStopped() để xác nhận
		// (Bây giờ nó đã được gọi bởi nút, nhưng để đây vẫn tốt)
		fireEditingStopped();
		return super.stopCellEditing();
	}

	@Override
	public void cancelCellEditing() {
		// Khi hủy edit (ví dụ: bấm phím Esc)
		fireEditingCanceled();
		super.cancelCellEditing();
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		// Cho phép chọn ô ngay
		return true;
	}
}