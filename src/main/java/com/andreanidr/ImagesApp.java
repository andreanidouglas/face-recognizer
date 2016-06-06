package com.andreanidr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.andreanidr.controller.CarregadorDeImagens;
import com.andreanidr.controller.CarregadorDeModelo;
import com.andreanidr.model.Etiqueta;
import com.andreanidr.model.Imagem;
import com.andreanidr.utils.DetectorDeFaces;
import com.andreanidr.utils.ReadImagesAssets;

public class ImagesApp {
	
	private final String csvFileInput = "/home/cmte/face-recognizer/resources/assets/assets_list.csv";
	private final String csvFileOutput = "/home/cmte/face-recognizer/resources/assets/assets_faces/assets_faces.csv";
	private final String modelPath = "/home/cmte/face-recognizer/resources/image_model";
	private final String tempFolder = "/home/cmte/face-recognizer/resources/temp";
	
	private CarregadorDeModelo cdm = new CarregadorDeModelo();
	private CarregadorDeImagens cdi;
	private ReadImagesAssets ria = new ReadImagesAssets(csvFileInput, csvFileOutput);
	private ArrayList<Imagem> faces;
	
	public void init() throws Exception{
		clear();
		ria.read();
		cdm.criarNovoModelo(modelPath + "model.yml");
		cdi = new CarregadorDeImagens(cdm.getModelo());
		ria.read();
		ria.readFaces();
		faces = ria.getFaces();
		
		for (Imagem i : faces)
		{
			cdi.carregarImagem(i.getCaminhoAbsoluto(), i.getName(), i.getEtiqueta().getId());
		}
		
		cdi.treinarBancoDeImagens();
	}
	
	public void clear(){
		File assets_face = new File(csvFileOutput);
		assets_face.delete();
	}
	
	public String matchImage(String path) throws IOException
	{
		String tempCSV = tempFolder + "/" + new Date().getTime();
		String line="";
		String caminho="";
		Imagem im = new Imagem("procurar", path, new Etiqueta());
		new DetectorDeFaces(im, tempCSV);
		
		
		BufferedReader reader = new BufferedReader(new FileReader(tempCSV));
		while ((line = reader.readLine()) != null) {
			String[] imagem = line.split(",");
			caminho = imagem[2];
		}
		
		int label = cdi.predizerImagem("", "./"+caminho);
		String returned = ria.findImageByLabel(label);
		return returned;
	}
	
	


}
