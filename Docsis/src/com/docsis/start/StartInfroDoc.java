package com.docsis.start;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.docsis.tela.FrmLogin;
import com.docsis.tela.FrmSplash;


public class StartInfroDoc {

	private static final Logger LOG = Logger.getLogger(StartInfroDoc.class);
	
	private static final String ARQUIVO_CONFIGURACAO_LOG4J = "etc/log4j.properties";
	
	public static void main(String[] args) {
		
		//PropertyConfigurator.configure(StartSistemaFiado.ARQUIVO_CONFIGURACAO_LOG4J);
		//configureAndWatch() fica monitorando alterações no arquivos de configuracao
		//do log4j a cada intervalo de milissegundos definido no segundo parametro
		//e aplica as alterações.Aqui está para aplicar as alterações a cada 10s
		PropertyConfigurator.configureAndWatch(StartInfroDoc.ARQUIVO_CONFIGURACAO_LOG4J, 10000);
		LOG.info("Iniciando");
		
		
		 new FrmSplash();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							
							new FrmLogin();
							
					
					break;
				} else {
					 UIManager.setLookAndFeel
					 ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

			}
										
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		});
	}

}
