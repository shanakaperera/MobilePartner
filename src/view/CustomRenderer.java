/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MobilePartnerController;
import db_pojo.MessageDefine;
import java.awt.Color;
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import jpa_controllers.MessageDefineJpaController;

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
                    figureColumnValue(colVal), figureColumnValue(colVal) != null);

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

    private Color figureColumnValue(String col_val) {
        try {
            EntityManagerFactory emf = MobilePartnerController.getInstance().getEntityManagerFactory();
            MessageDefineJpaController msgDefineController = new MessageDefineJpaController(emf);
            MessageDefine findMessageDefine = msgDefineController.findMessageDefine(col_val.toLowerCase(), "typeName");
            if (findMessageDefine != null) {
                return new Color(Integer.parseInt(findMessageDefine.getDescription()));
            } else {
                return null;
            }

        } catch (Exception ex) {
            Logger.getLogger(CustomRenderer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
