package com.docsis.tela;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.docsis.beans.AreaCmts;
import com.docsis.dao.AreaCmtsDAO;
import com.docsis.dao.AreaCmtsDAOImpl;
import com.docsis.exception.DaoException;
import com.docsis.util.TelaUtil;

public class FrmAreaCmts extends JDialog implements ActionListener {

	/**
	 * Launch the application.
	 */
	
	private AreaCmts area;
	private boolean modoEdicao;
	private JTextField txtNomeCmts;
	private JCheckBox chckbxAtivo;
	private JButton btnSair;
	private JButton btnSalvar;
	private JComponent contentPanel;

	/**
	 * Create the dialog.
	 */
	public static void main(String[] args) {
		new FrmAreaCmts(null);
	}
	public FrmAreaCmts(AreaCmts area) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.area = area;
		setModal(true);
		MontarTela();
		TelaUtil.centralizar(this);
		
		
		if(this.area != null){
		
		setBean(this.area);
		mudarParaModoEdicao();
	}else{
	
		mudarParaModoCriacao();		
		
		
		}
		setVisible(true);
		
		
		

	}
	
	private AreaCmts getBean(){
		
		AreaCmts areaRet = new AreaCmts();
		
		if(modoEdicao){
			areaRet = this.area;
		}
		
		areaRet.setNome(this.txtNomeCmts.getText());
		if(this.chckbxAtivo.isSelected()){
		areaRet.setAtivo((byte)1);
		}else{
			areaRet.setAtivo((byte)0);
		}
		
		return areaRet;
	}

	private void setBean(AreaCmts area1) {
		this.txtNomeCmts.setText(area1.getNome());
		if(area1.getAtivo()==1){
			this.chckbxAtivo.setSelected(true);	
		}else{
			this.chckbxAtivo.setSelected(false);
		}
				
	}

	private void mudarParaModoCriacao() {
		this.setTitle("Cadastrar novo Cmts");
		this.modoEdicao=false;
	}

	private void mudarParaModoEdicao() {
		setTitle("Alteração do  Cmts");
		this.modoEdicao=true;
		
	}
	
	private boolean valida() {
		
		if(this.txtNomeCmts.getText().isEmpty()){
			return false;	
		}
		return true;
		
		
	}

	private void MontarTela() {
		setBounds(100, 100, 346, 131);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 21, 50, 23);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 14));
		getContentPane().add(lblNome);
		
		txtNomeCmts = new JTextField();
		txtNomeCmts.setBounds(59, 11, 187, 36);
		txtNomeCmts.setFont(new Font("Arial", Font.PLAIN, 14));
		getContentPane().add(txtNomeCmts);
		txtNomeCmts.setColumns(10);
		
		chckbxAtivo = new JCheckBox("Ativo");
		chckbxAtivo.setBounds(252, 17, 66, 23);
		chckbxAtivo.setFont(new Font("Arial", Font.PLAIN, 14));
		getContentPane().add(chckbxAtivo);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(this);
		btnSalvar.setBounds(54, 58, 89, 23);
		btnSalvar.setFont(new Font("Arial", Font.PLAIN, 14));
		getContentPane().add(btnSalvar);
		
		btnSair = new JButton("Sair");
		btnSair.setBounds(183, 58, 89, 23);
		btnSair.addActionListener(this);
		btnSair.setFont(new Font("Arial", Font.PLAIN, 14));
		getContentPane().add(btnSair);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == this.btnSair){
			dispose();
		}else{
			if(event.getSource()==this.btnSalvar){
			AreaCmtsDAO areaCmtsDAO = new AreaCmtsDAOImpl();
			if(valida()){
				
				try {
					this.area = getBean();
					
					if(modoEdicao){
					areaCmtsDAO.alterar(this.area);
					}else{
						
						areaCmtsDAO.criar(this.area);
					}
					JOptionPane.showMessageDialog(this,"SALVO COM SUCESSO !!!");
					this.dispose();
				} catch (DaoException e) {
					
					JOptionPane.showMessageDialog(this,"OCORREU UM ERRO  !!!"+e.getMessage());
					e.printStackTrace();
				}
				}else{
					JOptionPane.showMessageDialog(this," OS CAMPO NOME NÃO PODE ESTÁR EM BRANCO ");
				}
		}
		}
	}
}
