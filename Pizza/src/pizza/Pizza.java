package pizza;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Class Pizza
 * @author gzwti
 * @version 1.2 23.05.2016
 * The pizza order user interface
 */
public class Pizza extends JFrame {

	private static final long serialVersionUID = 6542646165558181943L;
	private JPanel contentPane;
	private Map<String, Double> sizeMap;
	private Map<String, Double> toppingMap;
	private List<JCheckBox> boxs;
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;
	private JRadioButton radioButton3;
	private JCheckBox checkBox1;
	private JCheckBox checkBox2;
	private JCheckBox checkBox3;
	private JCheckBox checkBox4;
	private JCheckBox checkBox5;
	private JCheckBox checkBox6;
	private JLabel label;
	private JButton confirmButton;
	private ButtonGroup buttonGroup;

	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Pizza frame = new Pizza();
				frame.setVisible(true);
			} catch (Exception e) {
			}
		});
	}
	
	/**
	 * Default constuctor
	 * @throws Exception
	 */
	public Pizza() throws Exception {
		setResizable(false);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 383, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		sizeMap = new HashMap<String, Double>();
		toppingMap = new HashMap<String, Double>();
		boxs = new ArrayList<JCheckBox>();

		sizeMap.put("Small", 4.0);
		sizeMap.put("Medium", 5.5);
		sizeMap.put("Large", 7.0);

		toppingMap.put("Small", 0.75);
		toppingMap.put("Medium", 1.0);
		toppingMap.put("Large", 1.45);

		radioButton1 = new JRadioButton("Small");
		radioButton1.setBounds(6, 7, 109, 23);
		contentPane.add(radioButton1);

		radioButton2 = new JRadioButton("Medium");
		radioButton2.setBounds(115, 7, 109, 23);
		contentPane.add(radioButton2);

		radioButton3 = new JRadioButton("Large");
		radioButton3.setBounds(228, 7, 109, 23);
		contentPane.add(radioButton3);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButton1);
		buttonGroup.add(radioButton2);
		buttonGroup.add(radioButton3);

		checkBox1 = new JCheckBox("Strawberry");
		checkBox1.setBounds(265, 35, 97, 23);
		contentPane.add(checkBox1);

		checkBox2 = new JCheckBox("Cheese");
		checkBox2.setBounds(265, 63, 97, 23);
		contentPane.add(checkBox2);

		checkBox3 = new JCheckBox("Chocolate");
		checkBox3.setBounds(265, 91, 97, 23);
		contentPane.add(checkBox3);

		checkBox4 = new JCheckBox("Honey");
		checkBox4.setBounds(265, 119, 97, 23);
		contentPane.add(checkBox4);

		checkBox5 = new JCheckBox("Cherry");
		checkBox5.setBounds(265, 147, 97, 23);
		contentPane.add(checkBox5);

		checkBox6 = new JCheckBox("Nuts");
		checkBox6.setBounds(265, 175, 97, 23);
		contentPane.add(checkBox6);

		boxs.add(checkBox1);
		boxs.add(checkBox2);
		boxs.add(checkBox3);
		boxs.add(checkBox4);
		boxs.add(checkBox5);
		boxs.add(checkBox6);

		label = new JLabel("");
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(159, 204, 94, 26);
		contentPane.add(label);

		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(e -> label.setText("Price: " + String.valueOf(calculatePrice()) + "ву"));
		confirmButton.setBounds(265, 207, 89, 23);
		contentPane.add(confirmButton);

		JLabel imageLabel = new JLabel("");
		ImageIcon icon = new ImageIcon("res\\Pizza-capricciosa.jpg");
		imageLabel.setBounds(6, 39, 251, 149);
		icon.setImage(icon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH));
		imageLabel.setIcon(icon);
		contentPane.add(imageLabel);
	}
	
	/**
	 * Calculate the price of the pizza
	 * @return the price
	 */
	private double calculatePrice() {
		double sizePrice = 0;
		double toppingPrice = 0;
		int count = 0;

		Enumeration<AbstractButton> enumeration = buttonGroup.getElements();
		while (enumeration.hasMoreElements()) {
			AbstractButton abstractButton = enumeration.nextElement();
			if (abstractButton.isSelected()) {
				sizePrice = sizeMap.get(abstractButton.getText());
				toppingPrice = toppingMap.get(abstractButton.getText());
			}
		}

		for (JCheckBox a : boxs) {
			if (a.isSelected()) {
				count++;
			}
		}

		return sizePrice + count * toppingPrice;
	}

	public JRadioButton getRadioButton1() {
		return radioButton1;
	}

	public JRadioButton getRadioButton2() {
		return radioButton2;
	}

	public JRadioButton getRadioButton3() {
		return radioButton3;
	}

	public JCheckBox getCheckBox1() {
		return checkBox1;
	}

	public JCheckBox getCheckBox2() {
		return checkBox2;
	}

	public JCheckBox getCheckBox3() {
		return checkBox3;
	}

	public JCheckBox getCheckBox4() {
		return checkBox4;
	}

	public JCheckBox getCheckBox5() {
		return checkBox5;
	}

	public JCheckBox getCheckBox6() {
		return checkBox6;
	}

	public JLabel getLabel() {
		return label;
	}

	public JButton getConfirmButton() {
		return confirmButton;
	}
}