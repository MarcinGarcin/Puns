import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DrawControlPanel extends RoundedPanel {
    private DrawPanel drawPanel;
    private Color grey = new Color(51, 51, 51);
    private Color yellow = new Color(252, 222, 6);

    public DrawControlPanel(DrawPanel drawPanel) {
        super(20);
        this.drawPanel = drawPanel;

        CustomCheckBox eraserCheckBox = new CustomCheckBox("Eraser");
        eraserCheckBox.setForeground(yellow);
        eraserCheckBox.setBackground(grey);
        eraserCheckBox.setFocusPainted(false);
        eraserCheckBox.setOpaque(false);
        eraserCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setEraser(eraserCheckBox.isSelected());
                eraserCheckBox.repaint(); // Ensure the checkbox repaints when its state changes
            }
        });

        JSlider sizeSlider = new JSlider(1, 20, 5);
        sizeSlider.setBackground(grey);
        sizeSlider.setForeground(yellow);
        sizeSlider.addChangeListener(e -> drawPanel.setDrawSize(sizeSlider.getValue()));

        JButton colorButton = new JButton("Change Color");
        colorButton.setBackground(grey);
        colorButton.setForeground(yellow);
        colorButton.setBorderPainted(false);
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose a color", drawPanel.getForeground());
                if (newColor != null) {
                    drawPanel.setDrawColor(newColor);
                }
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(eraserCheckBox, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(sizeSlider, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(colorButton, gbc);

        setBackground(Color.LIGHT_GRAY);
    }
}