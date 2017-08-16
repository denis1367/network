package com.docsis.tela;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class FrmSplash extends JFrame {

	private JPanel contentPane;
	private static JProgressBar progressBar;


	public FrmSplash() {
		
		setEnabled(false);
		setUndecorated(true);
		setResizable(false);
		
		montarTela();
		
		setVisible(true);
		AbrirFechar();
		

	}

	private void montarTela() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("/com/docsis/imagem/pizzaico.jpg"));
		
		setBounds(0, 0, 302, 344);
		getContentPane().setLayout(null);

		JLabel label = new JLabel("");
				label.setBounds(0, 0, 302, 325);
		getContentPane().add(label);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(0, 323, 302, 20);
		getContentPane().add(progressBar);
		setLocationRelativeTo(null);

	}

	public void carrega() {
		for (int i = 0; i < 101; i++) {

			try {

				Thread.sleep(50);
				progressBar.setValue(i);

			} catch (InterruptedException e) {
				JOptionPane
						.showMessageDialog(null, "não foi possivel carregar");
				e.printStackTrace();

			}

		}

	}

	public void AbrirFechar() {
		carrega();
		dispose();
	}
}