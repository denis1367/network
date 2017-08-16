package com.docsis.tela;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.docsis.beans.Usuario;
import com.docsis.dao.UsuarioDAO;
import com.docsis.dao.UsuarioDAOImpl;
import com.docsis.exception.DaoException;
import com.docsis.util.TelaUtil;

public class FrmLogin extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JButton btnLogin;
	private JButton btnSair;

	/**
	 * Launch the application.
	 */
	private static final Logger Log = Logger.getLogger(FrmLogin.class);
	private JPasswordField txtSenha;
	//private Usuario usuario;

	// public static void main(String[] args) {
	//
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	//
	// for (LookAndFeelInfo info : UIManager
	// .getInstalledLookAndFeels()) {
	// if ("Nimbus".equals(info.getName())) {
	// UIManager.setLookAndFeel(info.getClassName());
	// new FrmLogin();
	//
	// PropertyConfigurator.configureAndWatch(
	// "conf/log4j.properties", 10000);
	// Log.info("iciando aplicativo");
	// break;
	// } else {
	// // UIManager.setLookAndFeel
	// // ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	//
	// }
	//
	// }
	//
	// /**
	// * MenuPrincipal frame = new MenuPrincipal();
	// * frame.setVisible(true);
	// */
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	//
	// }

	/**
	 * Create the frame.
	 */
	public FrmLogin() {
		
		
		setTitle("Login");
		setResizable(false);
		
		montaTela();
		TelaUtil.centralizar(this);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	private void montaTela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 370, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
		btnLogin.addActionListener(this);
		btnLogin.setBounds(78, 175, 89, 28);
		contentPane.add(btnLogin);

		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Arial", Font.BOLD, 14));
		btnSair.addActionListener(this);
		btnSair.setBounds(199, 175, 89, 28);
		
		contentPane.add(btnSair);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(145, 80, 143, 28);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		txtSenha = new JPasswordField();
		txtSenha.setFont(new Font("Arial", Font.BOLD, 14));
		txtSenha.setBounds(145, 125, 143, 28);
		contentPane.add(txtSenha);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		lblUsuario.setBounds(78, 86, 58, 14);
		contentPane.add(lblUsuario);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Arial", Font.BOLD, 14));
		lblSenha.setBounds(78, 131, 58, 14);
		contentPane.add(lblSenha);

		JLabel lblFoto = new JLabel("");
		//lblFoto.setIcon(new ImageIcon(FrmLogin.class.getResource("/com/projetotcc/imagem/pizza_logar.jpg")));
		lblFoto.setBounds(10, 0, 355, 296);
		
		contentPane.add(lblFoto);
	}

	@SuppressWarnings("deprecation")
	@Override
	/**
	 * este metodo cria o evendo dos botoes
	 */
	public void actionPerformed(ActionEvent event) {
		
		try{
			if (event.getSource() == this.btnLogin){
			
				funcaoLogar();
				
		}else if(event.getSource() == this.btnSair){
			
			
			int i = JOptionPane.showConfirmDialog(this,
					"voce deseja realmente sair", "Sair",
					JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.YES_OPTION) {
				dispose();
				Log.info("fechando o sistema sem loga");
				
			}
			}	
		}catch(Exception e){
			
		}
	}

	private void funcaoLogar() throws DaoException {
		UsuarioDAO uDAO = new UsuarioDAOImpl();
		Usuario u = new Usuario();
		u.setLogin(this.txtUsuario.getText());
		u.setSenha(this.txtSenha.getText());
		
		Usuario retUs = uDAO.BuscarUserLogin(u);
		if(retUs.getLogin() == null && retUs.getSenha()==null){
			JOptionPane.showMessageDialog(this, "Usuario ou senha invalida");
		}else{
			
			new MenuPrincipal(retUs);
			this.dispose();
			
		}
	}
}

