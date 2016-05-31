package com.andreanidr.model;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;

import static org.bytedeco.javacpp.opencv_face.*;


public class FaceRecognitionModel {

	
	FaceRecognizer faceRecognizer;
	
	public FaceRecognitionModel(){
		faceRecognizer = org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer();
	}
	
	public void treinarModelo(MatVector imagens, Mat etiquetas)
	{
		faceRecognizer.train(imagens, etiquetas);
	}
	
	public int predizerImagem(Mat imagem)
	{
		return faceRecognizer.predict(imagem);
	}
	
	public void  salvarModelo(String caminho){
		faceRecognizer.save(caminho);
	}
	
	public void carregarModelo(String caminho){
		faceRecognizer.load(caminho);
	}
	
}
