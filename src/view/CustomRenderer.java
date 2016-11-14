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
 * @author shanaka
 */
public class CustomRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JTextField editor = new JTextField();
        editor.setFont(new java.awt.Font("Kinnari", 1, 14));
        editor.setHorizontalAlignment(JTextField.CENTER);
        if (value != null) {

            editor.setText(value.toString());

            String colVal = table.getModel().getValueAt(row, 3).toString();
           // System.out.println(colVal);
            setColumnForegroundColorWithColumnLogic(column, 2, editor,
                    Color.green, colVal.equalsIgnoreCase("success"));
            setColumnForegroundColorWithColumnLogic(column, 2, editor,
                    Color.orange, colVal.equalsIgnoreCase("warning"));
            //  setRowBackgroundColorWithColumnLogic(editor, Color.YELLOW, age <= 18);

            if (isSelected) {
                editor.setBackground(Color.BLUE);

            } else {
                // else something......

            }

        }

        return editor;

    }

    private void setColumnForegroundColorWithColumnLogic(int column_get, int column_needed,
            JComponent component, Color color_foreground, boolean logic) {
        if (column_get == column_needed && logic) {

            component.setForeground(color_foreground);

        }
    }

    private void setRowBackgroundColorWithColumnLogic(JComponent component,
            Color color_background, boolean logic) {
        if (logic) {

            component.setBackground(color_background);
        }

    }

}
