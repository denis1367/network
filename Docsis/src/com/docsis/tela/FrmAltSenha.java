package com.docsis.tela;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.docsis.beans.Usuario;
import com.docsis.dao.UsuarioDAO;
import com.docsis.dao.UsuarioDAOImpl;
import com.docsis.exception.DaoException;

public class FrmAltSenha extends JDialog implements ActionListener {
	private JPasswordField txtNovaSenha;
	private JPasswordField txtConfirmaSenha;
	private JPasswordField txtSenhaAtual;
	private JButton btnSalvar;
	private JButton btnSair;
	private Usuario usuario;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public FrmAltSenha(Usuario[] userRet) {

		this.usuario = userRet[0];
		montarTela();
		setVisible(true);

	}

	private void montarTela() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		txtNovaSenha = new JPasswordField();
		txtNovaSenha.setBounds(157, 79, 229, 40);
		getContentPane().add(txtNovaSenha);

		txtConfirmaSenha = new JPasswordField();
		txtConfirmaSenha.setBounds(157, 141, 229, 40);
		getContentPane().add(txtConfirmaSenha);

		txtSenhaAtual = new JPasswordField();
		txtSenhaAtual.setBounds(157, 11, 232, 40);
		getContentPane().add(txtSenhaAtual);

		JLabel lblSenhaAnterior = new JLabel("Senha Anterior");
		lblSenhaAnterior.setBounds(20, 24, 101, 14);
		getContentPane().add(lblSenhaAnterior);

		JLabel lblNovaSenha = new JLabel("Nova Senha");
		lblNovaSenha.setBounds(20, 92, 114, 14);
		getContentPane().add(lblNovaSenha);

		JLabel lblConfirmaNovaSenha = new JLabel("Confirma Nova Senha");
		lblConfirmaNovaSenha.setBounds(20, 154, 114, 14);
		getContentPane().add(lblConfirmaNovaSenha);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(this);
		btnSalvar.setBounds(110, 210, 89, 23);
		getContentPane().add(btnSalvar);

		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);
		btnSair.setBounds(274, 210, 89, 23);
		getContentPane().add(btnSair);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == this.btnSalvar) {

			if (this.txtSenhaAtual.getText().equals(usuario.getSenha())) {

				if (txtConfirmaSenha.getText().equals(txtNovaSenha.getText())) {

					UsuarioDAO uDAO = new UsuarioDAOImpl();
					
					usuario.setSenha(txtNovaSenha.getText());
					Usuario[] userRet= {usuario};
					
					try {
						uDAO.AlterarSeha(usuario);
						
						JOptionPane.showMessageDialog(this, "senha alterarda com sucesso !!!"+usuario.getCod());
					this.dispose();
					} catch (DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				} else {
					JOptionPane.showMessageDialog(null,
							"As senhas digitadas não são iguais");
				}

			}
		}
		if (event.getSource() == this.btnSair) {
			
			this.dispose();
		}

	}
}
