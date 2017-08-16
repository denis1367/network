package com.docsis.tela;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.docsis.beans.Usuario;
import com.docsis.exception.DaoException;
import javax.swing.JButton;

public class MenuPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	
	private Usuario user;
	public MenuPrincipal(Usuario user) {
		this.user = user;
		
		setTitle("Menu Principal");
		MontarTela();
		setVisible(true);
	}

	private void MontarTela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 407);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCadastros = new JMenu("Cadastros");
		menuBar.add(mnCadastros);
		
		JMenuItem mntmCliente = new JMenuItem("Cliente");
		mnCadastros.add(mntmCliente);
		
		JMenuItem mntmGrupo = new JMenuItem("Grupo");
		mnCadastros.add(mntmGrupo);
		
		JMenuItem mntmRede = new JMenuItem("Rede");
		mnCadastros.add(mntmRede);
		
		JMenuItem mntmOpesDhcp = new JMenuItem("Op\u00E7\u00F5es DHCP");
		mntmOpesDhcp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				
					
					new FrmBuscaOpcaoDhcp();
				
				} catch (DaoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnCadastros.add(mntmOpesDhcp);
		
		JMenuItem mntmPlano = new JMenuItem("Plano");
		mntmPlano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new FrmBuscaPlano();
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnCadastros.add(mntmPlano);
		
		JMenuItem mntmUsuario = new JMenuItem("Usuario");
		mnCadastros.add(mntmUsuario);
		
		JMenuItem mntmAreacmts = new JMenuItem("Area_Cmts");
		mntmAreacmts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
				new FrmBuscaAreaCmts();
			} catch (DaoException e1) {
				
				e1.printStackTrace();
			}
			}
		});
		mnCadastros.add(mntmAreacmts);
		contentPane = new JPanel();
		contentPane.setToolTipText("Cadastros");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAlterarSenha = new JButton("Alterar Senha");
		btnAlterarSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Usuario[] userRet = {user};
				new FrmAltSenha(userRet);
				user = userRet[0];
				JOptionPane.showMessageDialog(null,user.getSenha());
				}
		});
		btnAlterarSenha.setBounds(52, 62, 134, 43);
		contentPane.add(btnAlterarSenha);
	}
}
