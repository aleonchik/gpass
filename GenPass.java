/*
 * @author Alexey Leonchik
 * <alexey@leonchik.ru>
 * 
 * Пт. 22 июля 2016
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GenPass {
	
	JFrame mainFrame;
	JTextArea jtaPass;
	JButton btnGenerate, btnExit;
	JCheckBox chkBigCase, chkSmallCase, chkDigits, chkSpecialSymb;
	JTextField txtPassLen;
	Config myConf;
	
	final int PASS_COUNT = 10;
	// final String BIG_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	final String BIG_CHAR = "ABCDEFGHJKLMNPQRSTUVWXYZ";
	// final String SMALL_CHAR = "abcdefghijklmnopqrstuvwxyz";
	final String SMALL_CHAR = "abcdefghkmnpqrstuvwxyz";
	final String DIGITS = "1234567890";
	final String SPEC_SYMB = ".,!?-=[]{}()*^%#@";
	
	public void init() {
		mainFrame = new JFrame("Генератор паролей");
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// mainFrame.setSize(400, 300);
		// Иконка приложения
		String iconPath = "./res/dialog-password.png";
		Image icon = new ImageIcon(getClass().getResource(iconPath)).getImage();
		mainFrame.setIconImage(icon);
		
		mainFrame.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent event) {
				onExit();
            }
            
            public void windowActivated(WindowEvent event) { 
				// System.out.println("windowActivated");
			}
            
            public void windowDeactivated(WindowEvent event) {
				// System.out.println("windowDeactivated");
			}
			
            public void windowDeiconified(WindowEvent event) {
				// System.out.println("windowDeiconified");
			}
            
            public void windowIconified(WindowEvent event) {
				// System.out.println("windowIconified");
			}
			
            public void windowOpened(WindowEvent event) {
				// System.out.println("windowOpened");
			}
			
            public void windowClosed(WindowEvent event) {
				// System.out.println("windowClosed");
			}
		});
		
		// Создаем текстовую область
		jtaPass = new JTextArea(12, 25);
		jtaPass.setEditable(false);
		jtaPass.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 18));
		
		// Создаем панель для текстовой области
		JPanel pnlText = new JPanel();
		
		// Кладем на нее текстовую область на панели скролинга
		pnlText.add(new JScrollPane(jtaPass));
		
		// Кладем панель текста на основной фрейм
		mainFrame.add(pnlText);
		
		// Создаем опции
		chkBigCase = new JCheckBox(BIG_CHAR);
		chkSmallCase = new JCheckBox(SMALL_CHAR);
		chkDigits = new JCheckBox(DIGITS);
		chkDigits.setSelected(true);
		chkSpecialSymb = new JCheckBox(SPEC_SYMB);
		txtPassLen = new JTextField("8", 2);
		
		// Создаем панель опций и добавляем на нее опции
		JPanel pnlOptions = new JPanel();
		pnlOptions.setLayout(new GridLayout(6, 1));
		pnlOptions.add(chkBigCase);
		pnlOptions.add(chkSmallCase);
		pnlOptions.add(chkDigits);
		pnlOptions.add(chkSpecialSymb);
		pnlOptions.add(new JLabel("Длина пароля: "));
		pnlOptions.add(txtPassLen);
		
		// Создаем кнопки и обработчики нажатий
		btnGenerate = new JButton("Генерировать");
		
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generatePass();
			}
		});
		
		btnExit = new JButton("Выход");
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onExit();
				System.exit(0);
			}
		});
		
		// Создаем панель кнопок
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		// Кладем кнопки на панель кнопок
		pnlButtons.add(btnGenerate);
		pnlButtons.add(btnExit);
		
		// Создаем правую панель
		JPanel pnlRight = new JPanel();
		pnlRight.setLayout(new BorderLayout());
		
		// Добавляем на правую панель панель опций и панель кнопок
		pnlRight.add(pnlOptions, BorderLayout.CENTER);
		pnlRight.add(pnlButtons, BorderLayout.SOUTH);
		
		// Добавляем правую панель на основной фрейм
		mainFrame.add(pnlRight);
		mainFrame.pack();
		
		// Загружаем конфиг программы
		myConf = new Config();
		txtPassLen.setText(myConf.getPassLen());
		chkBigCase.setSelected(Boolean.parseBoolean(myConf.getBigCharEnabled()));
		chkSmallCase.setSelected(Boolean.parseBoolean(myConf.getSmallCharEnabled()));
		chkDigits.setSelected(Boolean.parseBoolean(myConf.getDigitsEnabled()));
		chkSpecialSymb.setSelected(Boolean.parseBoolean(myConf.getSpecSymbEnabled()));
	}
	
	public void run() {
		mainFrame.setVisible(true);
		generatePass();
	}
	
	private void generatePass() {
		String strSymb = "";
		
		if(chkBigCase.isSelected()) {
			// System.out.println("Большие буквы");
			strSymb = BIG_CHAR;
		}
		
		if(chkSmallCase.isSelected()) {
			// System.out.println("Маленькие буквы");
			strSymb = strSymb + SMALL_CHAR;
		}
		
		if(chkDigits.isSelected()) {
			// System.out.println("Цифры");
			strSymb = strSymb + DIGITS;
		}
		
		if(chkSpecialSymb.isSelected()) {
			// System.out.println("Спец. символы");
			strSymb = strSymb + SPEC_SYMB;
		}
		
		if(strSymb.length() != 0) {
			int passLen = 0;
			
			try {
				passLen = Integer.parseInt(txtPassLen.getText());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
			if(passLen < 3 || passLen > 25) {
				JOptionPane.showMessageDialog(mainFrame,
					"Длина пароля не менее 3-х символов и не более 25",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			} else {
				Password myPass = new Password(strSymb, passLen);
			
				jtaPass.setText("");
			
				for(int i = 0; i < PASS_COUNT; i++) {
					jtaPass.append(myPass.getPass() + "\n");
				}
			}
			
		} else {
			JOptionPane.showMessageDialog(mainFrame, "Не заданы опции",
				"Ошибка", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void onExit() {
		Boolean tmpVar;
		
		tmpVar = chkBigCase.isSelected();
		myConf.setBigCharEnabled(tmpVar.toString());
		
		tmpVar = chkSmallCase.isSelected();
		myConf.setSmallCharEnabled(tmpVar.toString());
		
		tmpVar = chkDigits.isSelected();
		myConf.setDigitsEnabled(tmpVar.toString());
		
		tmpVar = chkSpecialSymb.isSelected();
		myConf.setSpecSymbEnabled(tmpVar.toString());
		
		myConf.setPassLen(txtPassLen.getText());
		
		myConf.saveParam();
	} // END onExit()
	
	public static void main (String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenPass genPass = new GenPass();
					genPass.init();
					genPass.run();
				}
				catch (Exception e) {	e.printStackTrace(); }
			}
		});
	}
}
