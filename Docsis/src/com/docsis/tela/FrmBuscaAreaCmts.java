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

import com.docsis.beans.AreaCmts;
import com.docsis.dao.AreaCmtsDAO;
import com.docsis.dao.AreaCmtsDAOImpl;
import com.docsis.exception.DaoException;
import com.docsis.util.TelaUtil;

public class FrmBuscaAreaCmts extends JDialog implements MouseListener, ActionListener {

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
			FrmBuscaAreaCmts dialog = new FrmBuscaAreaCmts();
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws DaoException 
	 */
	public FrmBuscaAreaCmts() throws DaoException {
		setTitle("BUSCAR AREA CMTS");
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
		"Codigo", "Nome","Ativo"})
		{
			boolean[] columnEditables = new boolean[] { false, false,false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.addMouseListener(this);
		
		
		scrollPane.setViewportView(table);
	}
	
	private void funcBuscarTodos( )throws DaoException{
		AreaCmtsDAO opcDAOImpl = new AreaCmtsDAOImpl();
		List<AreaCmts> lista = new ArrayList<AreaCmts>();
		
		lista = opcDAOImpl.buscarTodos();
		DefaultTableModel model = (DefaultTableModel)this.table.getModel();
		model.setRowCount(0);
		for(AreaCmts opc : lista){
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
		AreaCmts areaBeans = new AreaCmts();
		
		AreaCmtsDAO areaCmtsDAO = new AreaCmtsDAOImpl();				
int linha = this.table.getSelectedRow();
int cod = (Integer) this.table.getValueAt(linha, 0);



try {

		areaBeans = areaCmtsDAO.buscarPorCod(cod);
		new FrmAreaCmts(areaBeans);
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
	private void addGrid(DefaultTableModel model, AreaCmts a) {

		Object[] row = new Object[3];
		row[0] = a.getCodareacmts();
		row[1] = a.getNome();
		row[2] = a.getAtivo();
		

		model.addRow(row);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == this.btnIncluir ){
			
			new FrmAreaCmts(null);
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
						
						AreaCmtsDAO areaDao = new AreaCmtsDAOImpl();
						
						try {
						areaDao.apagar(cod);
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
