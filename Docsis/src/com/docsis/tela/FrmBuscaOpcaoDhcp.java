package com.docsis.tela;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.docsis.beans.OpcaoDhcp;
import com.docsis.dao.OpcaodhcpDAO;
import com.docsis.dao.OpcaodhcpDAOImpl;
import com.docsis.exception.DaoException;
import com.docsis.util.TelaUtil;

public class FrmBuscaOpcaoDhcp extends JDialog implements MouseListener, ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JButton btnIncluir;
	private JButton btnSair;
	private JMenuItem menuItemApagar;
	private JMenuItem menuItemAlterar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FrmBuscaOpcaoDhcp dialog = new FrmBuscaOpcaoDhcp();
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws DaoException 
	 */
	public FrmBuscaOpcaoDhcp() throws DaoException {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		MontarTela();
		setModal(true);
		TelaUtil.centralizar(this);
		funcBuscarTodos();
		setVisible(true);
	
	}

	private void MontarTela() {
		setBounds(100, 100, 592, 348);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(this);
		btnIncluir.setBounds(103, 275, 89, 23);
		contentPanel.add(btnIncluir);
		
		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);
		btnSair.setBounds(372, 275, 89, 23);
		contentPanel.add(btnSair);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 553, 253);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] {
	},
	new String[] {
		"Codigo", "Opcao","Valor","Ativo"})
		{
			boolean[] columnEditables = new boolean[] { false, false,false,false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.addMouseListener(this);
		
		
		scrollPane.setViewportView(table);
	}
	
	private void funcBuscarTodos( )throws DaoException{
		OpcaodhcpDAO opcDAOImpl = new OpcaodhcpDAOImpl();
		List<OpcaoDhcp> lista = new ArrayList<OpcaoDhcp>();
		
		lista = opcDAOImpl.buscarTodos();
		DefaultTableModel model = (DefaultTableModel)this.table.getModel();
		model.setRowCount(0);
		for(OpcaoDhcp opc : lista){
			addGrid(model, opc);
		}
		
		
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
		
		
		if(event.getButton() == MouseEvent.BUTTON1){
			if(event.getClickCount() == 2 ){
				
				funcAlterar();
			
			}
				
		
		
		}else {
			if(event.getButton() == MouseEvent.BUTTON3){
				if(event.getClickCount()==1){
					int linha = this.table.getSelectedRow();
					if(linha!=-1){
						
						if(this.table.isCellSelected(linha, 0)){
							JPopupMenu menu = new JPopupMenu();
							
							menuItemApagar = new JMenuItem("Excluir");
							menuItemApagar.addActionListener(this);
							menu.add(menuItemApagar);
							
							menuItemAlterar = new JMenuItem("Alterar");
							menuItemAlterar.addActionListener(this);
							menu.add(menuItemAlterar);
							
							
							
							menu.show(this,event.getX()+20,event.getY()+40);
							
						}
					}
					
					
				}
			}
			
		}
		
	}

	private void funcAlterar() {
		OpcaoDhcp opcaoBeans = new OpcaoDhcp();
		
		OpcaodhcpDAO opcaoDAO = new OpcaodhcpDAOImpl();				
int linha = this.table.getSelectedRow();
int cod = (Integer) this.table.getValueAt(linha, 0);



try {

		opcaoBeans = opcaoDAO.buscarPorCodigo(cod);
		new FrmOpcaoDhcp(opcaoBeans);
		funcBuscarTodos();
		
} catch (DaoException e) {
		
		e.printStackTrace();
}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private void addGrid(DefaultTableModel model, OpcaoDhcp o) {

		Object[] row = new Object[4];
		row[0] = o.getCod();
		row[1] = o.getOption();
		row[2] = o.getValor();
		row[3] = o.getAtivo();

		model.addRow(row);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == this.btnIncluir ){
			
			new FrmOpcaoDhcp(null);
			try {
				funcBuscarTodos();
			} catch (DaoException e) {
			
				e.printStackTrace();
			}
			
		}else{
			if(event.getSource()==this.btnSair){
				this.dispose();
			}else{
				if(event.getSource()==this.menuItemApagar){
					int linha = this.table.getSelectedRow();
					int cod = (Integer) this.table.getValueAt(linha, 0);
					
					int jOpton = JOptionPane.showConfirmDialog(this, "VOCÊ TEM CERTEZA QUE DESEJA APAGAR");
					if(JOptionPane.YES_OPTION==jOpton){
						
						OpcaodhcpDAO opcao = new OpcaodhcpDAOImpl();
						try {
							opcao.apagar(cod);
							funcBuscarTodos();
						} catch (DaoException e) {
							e.printStackTrace();
						}
									
					}
					
					
				}else{
					if(event.getSource()==this.menuItemAlterar){
						funcAlterar();
						}
				}
			}
		}
		
	}

}
