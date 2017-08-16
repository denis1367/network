package com.docsis.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.docsis.exception.EntradaUsuarioException;




public class TelaUtil {

	public static void tentaSetaNimbus(Component comp) {
		UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		for (UIManager.LookAndFeelInfo look :looks) {
			if (look.getClassName().matches("(?i).*nimbus.*")) {
				try {
					UIManager.setLookAndFeel(look.getClassName());
					SwingUtilities.updateComponentTreeUI(comp);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void centralizar(Window window) {
		Dimension windowDim = Toolkit.getDefaultToolkit().getScreenSize();
		int y = ((int) windowDim.getHeight() / 2) - window.getHeight() / 2;
		int x = ((int) windowDim.getWidth() / 2) - window.getWidth() / 2;
		window.setLocation(x, y);
	}
	
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return date == null ? "" : sdf.format(date);
	}
	
	public static String formataDinheiro(double valor) {
		return "R$ " + String.format("%.2f", valor);
	}
	
	public static Date getDateFromTextField(JFormattedTextField text) throws EntradaUsuarioException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		String dataText = text.getText();
		Date date = null;
		if (!"__/__/____".equals(dataText)) {
			try {
				date = sdf.parse(dataText);
			} catch (Exception e) {
				throw new EntradaUsuarioException(text, "Valor inválido no campo " + text.getName());
			}
		}
		return date;
	}
	
	public static String getEmail(JTextField txtEmail) throws EntradaUsuarioException {
		String email = txtEmail.getText();
		email = email.trim();
		if (!"".equals(email)) {
			if (!email.matches(".+@.+\\.[a-z]+")) {
				throw new EntradaUsuarioException(txtEmail, "Email inválido");
			}
		}
		return email;
	}
	
	public static String getMoneyValue(double valor) {
		return String.format("%.2f", valor);
	}
	
	public static double getCampoObrigatorioDouble(JTextField campoObrigatorio) throws EntradaUsuarioException {
		String valor = getCampoObrigatorio(campoObrigatorio);
		valor = valor.replace(".", ""); 
		valor = valor.replace(",", "."); 
		return Double.parseDouble(valor);
	}	
	
	public static String getCampoObrigatorio(JTextField campoObrigatorio) throws EntradaUsuarioException {
		String valor = campoObrigatorio.getText();
		if (valor == null || "".equals(valor.trim())) {
			throw new EntradaUsuarioException(campoObrigatorio, "Campo " + campoObrigatorio.getName() + " obrigatório");
		}
		return valor.trim();
	}
	
	public static byte[] showTelaEscolheImage(
			File diretorioOrigem, 
			ImagePanel previewPanel,
			JTextField txtCaminhoFoto,
			Component telaPai,
			String ... extensoes) throws EntradaUsuarioException {
		JFileChooser telaEscolheFoto = new JFileChooser(diretorioOrigem);
		telaEscolheFoto.setFileFilter(new FiltroExtensao(extensoes));
		
		telaEscolheFoto.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		byte[] arrayAux = null;
		
		int ret = telaEscolheFoto.showSaveDialog(telaPai);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			File fileFoto = telaEscolheFoto.getSelectedFile();
			txtCaminhoFoto.setText(fileFoto.getAbsolutePath());
			try {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileFoto));
				arrayAux = new byte[bis.available()];
				bis.read(arrayAux);

				previewPanel.setImagem(arrayAux);
				previewPanel.repaint();
			} catch (IOException e) {
				throw new EntradaUsuarioException(txtCaminhoFoto, "Não foi possível ler a imagem da foto", e);
			}
		}
		return arrayAux;
	}
	
}
