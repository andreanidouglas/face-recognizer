package com.andreanidr;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import com.andreanidr.controller.CarregadorDeImagens;
import com.andreanidr.controller.CarregadorDeModelo;

public class App {

	public static final String files = "/home/cmte/Projects/FaceRecognizer/imagens";
	public static final String resources = "resources";

	public static void main(String[] args) throws Exception {
		File root = new File(files);
		File resourcesFolder = new File(resources);
		
		
		CarregadorDeModelo cdm = new CarregadorDeModelo();
		cdm.criarNovoModelo(files + "/modelo1.yml");

		CarregadorDeImagens cdi = new CarregadorDeImagens(cdm.getModelo());
		FilenameFilter imgFilter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				name = name.toLowerCase();
				return name.endsWith(".jpg");

			}
		};

		File[] imgFiles = root.listFiles(imgFilter);

		for (int i = 0; i < imgFiles.length; i++) {
			cdi.carregarImagem(imgFiles[i].getAbsolutePath(), imgFiles[i].getName(), 1);
		}

		try {
			cdi.detectarFaces();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CarregadorDeImagens cdi2 = new CarregadorDeImagens(cdm.getModelo());

		File[] faces = resourcesFolder.listFiles(imgFilter);

		for (int i = 0; i < faces.length-1; i++) {
			cdi2.carregarImagem(faces[i].getAbsolutePath(), faces[i].getName(), i);
		}

		cdi2.treinarBancoDeImagens();
		int i = cdi2.predizerImagem(faces[faces.length-1].getAbsolutePath());
		System.out.println("Matched: " + i);
	}

}
