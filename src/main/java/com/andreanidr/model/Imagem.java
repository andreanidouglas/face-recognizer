package com.andreanidr.model;

import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

import java.io.File;

import org.bytedeco.javacpp.opencv_core.Mat;

public class Imagem {

	private Mat imagem;
	private Mat imagemPB;
	private String caminhoAbsoluto;
	private String name;
	private String identifier;
	private Etiqueta etiqueta;

	public Imagem(String nome, String caminho, Etiqueta etiqueta) throws NullPointerException {

		File arquivoImagem = new File(caminho);
		imagem = imread(caminho);
		imagemPB = imread(caminho, CV_LOAD_IMAGE_GRAYSCALE);
		caminhoAbsoluto = arquivoImagem.getAbsolutePath();
		name = arquivoImagem.getName();
		this.etiqueta = etiqueta;
		this.identifier = nome;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public Mat getImagem() {
		return imagem;
	}

	public Mat getImagemPB() {
		return imagemPB;
	}

	public String getCaminhoAbsoluto() {
		return caminhoAbsoluto;
	}

	public String getName() {
		return name;
	}

	public Etiqueta getEtiqueta() {
		return etiqueta;
	}

}
