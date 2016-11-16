/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Shanaka P
 */
public class CustomRendererPredefines implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JTextField editor = new JTextField();
        editor.setFont(new java.awt.Font("Kinnari", 1, 14));
        editor.setHorizontalAlignment(JTextField.CENTER);
        if (value != null) {

            if (column != 1) {
                editor.setText(value.toString());
            }

            String colVal = table.getModel().getValueAt(row, 2).toString();
            // System.out.println(colVal);

            setColumnForegroundColorWithColumnLogic(column, 1, editor,
                    new Color(Integer.parseInt(colVal)));

            if (isSelected) {
                editor.setBackground(Color.BLUE);

            } else {
                // else something......

            }

        }

        return editor;
    }

    private void setColumnForegroundColorWithColumnLogic(int column_get, int column_needed,
            JComponent component, Color color_foreground) {
        if (column_get == column_needed) {

            component.setBackground(color_foreground);

        }
    }

    private void setRowBackgroundColorWithColumnLogic(JComponent component,
            Color color_background, boolean logic) {
        if (logic) {

            component.setBackground(color_background);
        }

    }

}
