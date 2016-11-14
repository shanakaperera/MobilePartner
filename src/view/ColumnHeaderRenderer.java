/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author shanaka
 */
public class ColumnHeaderRenderer extends JLabel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(StringUtils.capitalize(value.toString()));
        setFont(new java.awt.Font("Liberation Mono", 1, 16));
        setHorizontalAlignment(JLabel.CENTER);
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setToolTipText((String) value);
        return this;
    }

}
