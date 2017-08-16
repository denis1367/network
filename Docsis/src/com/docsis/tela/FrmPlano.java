package com.docsis.tela;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.docsis.beans.Plano;
import com.docsis.dao.PlanoDAO;
import com.docsis.dao.PlanoDAOImpl;
import com.docsis.exception.DaoException;
import com.docsis.util.TelaUtil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmPlano extends JDialog implements ActionListener {
	private JTextField txtNomePlano;
	private JTextField txtNserver;
	private JCheckBox chAtivo;
	private JTextField txtArquivoB;
	private JButton btnSalvar;
	private JButton btnSair;
	private Plano plano;
	private boolean modoEdicao;
	
	public FrmPlano(Plano plano) {
		this.plano = plano;
		setModal(true);
		montarTela();
		TelaUtil.centralizar(this);
		
		if(this.plano==null){
			mudarParaModoCriacao();
		}else{
		 mudarParaModoEdicao();
		 setBean(this.plano);
		}
		setVisible(true);
		
	}

	private void setBean(Plano pl) {
		
		this.txtNomePlano.setText(pl.getNome());
		this.txtNserver.setText(pl.getNserver());
		this.txtArquivoB.setText(pl.getArqbinario());
		if(pl.getAtivo()==1){
			this.chAtivo.setSelected(true);
		}
		
		
	}

	private void mudarParaModoCriacao(){
		
		setTitle(" CADASTRO DE PLANO");
		this.modoEdicao=false;
	}
	private void mudarParaModoEdicao() {
		setTitle(" ALTERAR PLANO ");
		this.modoEdicao=true;
	}

	private void montarTela() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 624, 239);
		getContentPane().setLayout(null);
		
		txtNomePlano = new JTextField();
		txtNomePlano.setBounds(112, 34, 189, 29);
		getContentPane().add(txtNomePlano);
		txtNomePlano.setColumns(10);
		
		JLabel lblNomeDoPlano = new JLabel("Nome do Plano:");
		lblNomeDoPlano.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNomeDoPlano.setBounds(10, 39, 118, 17);
		getContentPane().add(lblNomeDoPlano);
		
		JLabel lblIpNserver = new JLabel("IP do Nserver:");
		lblIpNserver.setFont(new Font("Arial", Font.PLAIN, 14));
		lblIpNserver.setBounds(311, 39, 98, 17);
		getContentPane().add(lblIpNserver);
		
		txtNserver = new JTextField();
		txtNserver.setColumns(10);
		txtNserver.setBounds(409, 34, 189, 29);
		getContentPane().add(txtNserver);
		
		JLabel lblArquivoBinario = new JLabel("Nome do Arquivo Binario:");
		lblArquivoBinario.setFont(new Font("Arial", Font.PLAIN, 14));
		lblArquivoBinario.setBounds(10, 93, 172, 17);
		getContentPane().add(lblArquivoBinario);
		
		txtArquivoB = new JTextField();
		txtArquivoB.setColumns(10);
		txtArquivoB.setBounds(185, 88, 189, 29);
		getContentPane().add(txtArquivoB);
		
		chAtivo = new JCheckBox("Ativo");
		chAtivo.setFont(new Font("Arial", Font.PLAIN, 14));
		chAtivo.setBounds(409, 90, 97, 23);
		getContentPane().add(chAtivo);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(this);
		btnSalvar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnSalvar.setBounds(154, 148, 89, 23);
		getContentPane().add(btnSalvar);
		
		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);
		btnSair.setFont(new Font("Arial", Font.PLAIN, 14));
		btnSair.setBounds(370, 148, 89, 23);
		getContentPane().add(btnSair);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource()==this.btnSalvar){
			if(valida()){
				
			this.plano = getBean();				
			PlanoDAO plDAO = new PlanoDAOImpl();	
			
			if(modoEdicao){
				
				try {
					plDAO.Alterar(this.plano);
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				try {
					plDAO.Criar(this.plano);
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			JOptionPane.showMessageDialog(this,"SALVO COM SUCESSO !!!");
			dispose();
			
			}else{
				JOptionPane.showMessageDialog(this,"é necessario preencher todos os campos para salvar");
			}
			
		}else{
			if(event.getSource()==this.btnSair){
				
				dispose();
				
			}
		}
		
	}

	private Plano getBean() {
		Plano pl= new Plano();
		
		if(modoEdicao){
			pl = this.plano;
		}
		
		pl.setNome(this.txtNomePlano.getText());
		pl.setArqbinario(this.txtArquivoB.getText());
		pl.setNserver(this.txtNserver.getText());
		if(this.chAtivo.isSelected()){
			pl.setAtivo((byte)(1));
		}else{
			pl.setAtivo((byte)(0));
		}
		
		return pl;
	}

	private boolean valida() {
		if(this.txtArquivoB.getText().isEmpty()||this.txtNomePlano.getText().isEmpty()||this.txtNserver.getText().isEmpty()){
			return false;
		}
		
		return true;
	}
}
