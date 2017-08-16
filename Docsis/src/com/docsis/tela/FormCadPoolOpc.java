package com.docsis.tela;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import com.docsis.beans.PoolOpc;

import java.awt.Toolkit;

public class FormCadPoolOpc extends JDialog {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormCadPoolOpc dialog = new FormCadPoolOpc();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	private boolean modoEdicao;
	private PoolOpc[] poolOpc ;
	private PoolOpc plOpc;
	public FormCadPoolOpc(PoolOpc[] poolOpc) {
		
		this.poolOpc = poolOpc;
		this.plOpc = poolOpc[0];
		
		if(poolOpc == null){
			modoCria
		}
		
		
		montarTela();
	

	}

	private void montarTela() {
		setModal(true);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 353, 228);
		getContentPane().setLayout(null);
		
		JLabel lblNomeTipo = new JLabel("Nome Tipo:");
		lblNomeTipo.setBounds(26, 13, 75, 35);
		getContentPane().add(lblNomeTipo);
		
		textField = new JTextField();
		textField.setBounds(95, 13, 218, 35);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(26, 61, 41, 35);
		getContentPane().add(lblValor);
		
		textField_1 = new JTextField();
		textField_1.setBounds(64, 61, 178, 35);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JCheckBox chckbxAtivo = new JCheckBox("Ativo");
		chckbxAtivo.setBounds(250, 66, 63, 25);
		getContentPane().add(chckbxAtivo);
		
		JButton btnIcluir = new JButton("Salvar");
		btnIcluir.setBounds(85, 130, 75, 25);
		getContentPane().add(btnIcluir);
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(203, 130, 74, 25);
		getContentPane().add(btnSair);
		
		setVisible(true);
	}
	private void mudarParaModoCriacao() {
		this.setTitle("Cadastro da OpcaoDhcp");
		this.modoEdicao=false;
		
	}

	private void mudarParaModoEdicao() {
		this.setTitle("Alteração da Opcao do dhcp");
		this.modoEdicao=true;
		
	}

}
