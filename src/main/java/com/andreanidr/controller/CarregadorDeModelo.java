package com.andreanidr.controller;

import com.andreanidr.model.FaceRecognitionModel;

public class CarregadorDeModelo {

	private FaceRecognitionModel frm;

	public void carregarModelo(String caminho) {
		frm = new FaceRecognitionModel();
		frm.carregarModelo(caminho);
	}

	public void salvarModelo(String caminho) {
		frm.salvarModelo(caminho);
	}

	public FaceRecognitionModel getModelo() throws NullPointerException {
		if (frm == null) {
			throw new NullPointerException();
		}
		return frm;
	}
	
	public void criarNovoModelo(String caminho){
		frm = new FaceRecognitionModel();
	}

}
