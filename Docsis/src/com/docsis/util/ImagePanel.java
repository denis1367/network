package com.docsis.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	private byte[] imagem;

	public ImagePanel(byte[] imagem) {
		this.imagem = imagem;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	
	@Override
	public void paint(Graphics g) {
		if (this.imagem != null) { 
			
			BufferedImage img;
			try {
				img = ImageIO.read(new ByteArrayInputStream(imagem));
				g.drawImage(
						img,
						0,
						0, 
						getWidth(), 
						getHeight(),
						this);
				
			} catch (IOException e) {
			}
			

		} else {
			g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		}
		
	}
}
