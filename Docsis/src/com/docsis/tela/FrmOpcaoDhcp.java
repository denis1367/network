package com.docsis.tela;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.docsis.beans.OpcaoDhcp;
import com.docsis.dao.OpcaodhcpDAO;
import com.docsis.dao.OpcaodhcpDAOImpl;
import com.docsis.exception.DaoException;
import com.docsis.util.TelaUtil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmOpcaoDhcp extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtOpcao;
	private JTextField txtValor;
	private JCheckBox chckbxAtivo;
	private OpcaoDhcp opcBeans;
	private boolean modoEdicao;
	private JButton btnSalvar;
	private JButton btnSair;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the dialog.
	 */
	public FrmOpcaoDhcp(OpcaoDhcp opcIn ) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.opcBeans = opcIn;
		setModal(true);
		MontarTela();
		TelaUtil.centralizar(this);
		
		if(this.opcBeans != null){
		
		setBean(this.opcBeans);
		mudarParaModoEdicao();
	}else{
	
		mudarParaModoCriacao();		
		
		
		}
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

	private void MontarTela() {
		setBounds(100, 100, 475, 149);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblOpo = new JLabel("Op\u00E7\u00E3o");
		lblOpo.setBounds(68, 11, 48, 26);
		lblOpo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPanel.add(lblOpo);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(262, 14, 54, 20);
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPanel.add(lblValor);
		
		chckbxAtivo = new JCheckBox("ativo");
		chckbxAtivo.setBounds(386, 32, 65, 24);
		chckbxAtivo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPanel.add(chckbxAtivo);
		
		txtOpcao = new JTextField();
		txtOpcao.setBounds(10, 36, 181, 29);
		contentPanel.add(txtOpcao);
		txtOpcao.setColumns(10);
		
		txtValor = new JTextField();
		txtValor.setBounds(201, 36, 161, 29);
		contentPanel.add(txtValor);
		txtValor.setColumns(10);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(this);
		btnSalvar.setBounds(126, 76, 89, 23);
		contentPanel.add(btnSalvar);
		
		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);
		btnSair.setBounds(257, 76, 89, 23);
		contentPanel.add(btnSair);
	}
	
	private OpcaoDhcp getBean() throws DaoException{
		OpcaoDhcp opcGetBean = new OpcaoDhcp();
		
		if(modoEdicao){
		opcGetBean = this.opcBeans;
		}
		
		opcGetBean.setOption(this.txtOpcao.getText());
		opcGetBean.setValor(this.txtValor.getText());
		
		if(this.chckbxAtivo.isSelected()){
			
			opcGetBean.setAtivo((byte)1);
		}else{
			opcGetBean.setAtivo((byte)0);
		}
		
		
		
		return opcGetBean;
		
	}
	private void setBean(OpcaoDhcp opc){
		
	this.txtOpcao.setText(opc.getOption());
	this.txtValor.setText(opc.getValor());
	if(opc.getAtivo()==1){
	this.chckbxAtivo.setSelected(true);
	}
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		OpcaodhcpDAO opcaoDAO = new OpcaodhcpDAOImpl();
		if(event.getSource()==this.btnSalvar){
			
			funcaoSalvar(opcaoDAO);	
			
			
		}else{
			if(event.getSource()==this.btnSair){
				this.dispose();
			}
		}
		
	}

	private void funcaoSalvar(OpcaodhcpDAO opcaoDAO) {
		
		if(valida()){
		
		try {
			this.opcBeans = getBean();
			
			if(modoEdicao){
			opcaoDAO.alterar(this.opcBeans);
			}else{
				
				opcaoDAO.criar(this.opcBeans);
			}
			JOptionPane.showMessageDialog(this,"SALVO COM SUCESSO !!!");
			this.dispose();
		} catch (DaoException e) {
			
			e.printStackTrace();
		}
		}else{
			JOptionPane.showMessageDialog(this," OS DOIS CAMPOS NÃO PODE ESTÁR EM BRANCO ");
		}
		}

	private boolean valida() {
		
		if(txtOpcao.getText().isEmpty() || txtValor.getText().isEmpty()){
			return false;	
		}
		return true;
		
		
	}
}
