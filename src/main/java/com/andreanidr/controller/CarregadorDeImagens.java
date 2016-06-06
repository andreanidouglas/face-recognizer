package com.andreanidr.controller;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;

import com.andreanidr.model.Etiqueta;
import com.andreanidr.model.FaceRecognitionModel;
import com.andreanidr.model.Imagem;

public class CarregadorDeImagens {
	
	private ArrayList<Imagem> bancoDeImagens = new ArrayList<Imagem>();
	private FaceRecognitionModel frm;
	
	public CarregadorDeImagens(FaceRecognitionModel frm){
		this.frm = frm;
	}
	
	public void carregarImagem(String path, String nome, int identificador) {
		Etiqueta et = new Etiqueta();
		et.setId(identificador);
		et.setNome(nome);
		
		Imagem im = new Imagem(nome, path, et);
		bancoDeImagens.add(im);
	}
	
	@SuppressWarnings("deprecation")
	public void treinarBancoDeImagens() throws Exception{
		
		if (bancoDeImagens.size() < 1)
		{
			throw new Exception("Sem imagem disponivel para carregar");
		}
		
		MatVector imagens = new MatVector(bancoDeImagens.size());
		Mat etiquetas = new Mat(bancoDeImagens.size(), 1, CV_32SC1);
		IntBuffer bufferEtiquetas = etiquetas.getIntBuffer();
		int counter=0;
		
		for (Imagem imagem: bancoDeImagens)
		{
			imagens.put(counter, imagem.getImagemPB());
			bufferEtiquetas.put(counter, imagem.getEtiqueta().getId());
			counter++;
		}
		
		frm.treinarModelo(imagens, etiquetas);
		bancoDeImagens = new ArrayList<Imagem>();
				
	}
	
	public int predizerImagem(String nome, String caminho){
		Etiqueta et = new Etiqueta();
		Imagem im = new Imagem(nome, caminho, et );
		return frm.predizerImagem(im.getImagemPB());
	}
	
	
}
