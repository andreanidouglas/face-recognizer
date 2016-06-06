package com.andreanidr;

import java.io.File;
import java.io.FilenameFilter;

import com.andreanidr.controller.CarregadorDeImagens;
import com.andreanidr.controller.CarregadorDeModelo;

public class App_bak {

	public static final String files = "/home/cmte/Projects/FaceRecognizer/imagens";
	public static final String resources = "resources";

	public static void main(String[] args) throws Exception {
		File root = new File(files);
		File resourcesFolder = new File(resources);
		
		String [] obamaFiles = new String[3];
		obamaFiles[0] = "/home/cmte/Projects/FaceRecognizer/imagens/obama1.jpg";
		obamaFiles[1] = "/home/cmte/Projects/FaceRecognizer/imagens/obama1.jpg";
		obamaFiles[2] = "/home/cmte/Projects/FaceRecognizer/imagens/obama1.jpg";
		
		String [] trumpFiles = new String[3];
		trumpFiles[0] = "/home/cmte/Projects/FaceRecognizer/imagens/trump1.jpg";
		trumpFiles[1] = "/home/cmte/Projects/FaceRecognizer/imagens/trump2.jpg";
		trumpFiles[2] = "/home/cmte/Projects/FaceRecognizer/imagens/trump3.jpg";
		
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

		for (int i=0;i<obamaFiles.length;i++){
			cdi.carregarImagem(obamaFiles[i], "obama", 1);
			cdi.carregarImagem(trumpFiles[i], "trump", 2);
		}
		CarregadorDeImagens cdi2 = new CarregadorDeImagens(cdm.getModelo());

		File[] faces = resourcesFolder.listFiles(imgFilter);

		for (int i = 0; i < faces.length-1; i++) {
			System.out.println(faces[i].getName());
			if (faces[i].getName().startsWith("obama")){
			System.out.println("Obama");	
				cdi2.carregarImagem(faces[i].getAbsolutePath(), faces[i].getName(), 1);
			}
			else
			{
				System.out.println("Trump");
				
				cdi2.carregarImagem(faces[i].getAbsolutePath(), faces[i].getName(), 2);
			}
		}

		cdi2.treinarBancoDeImagens();
		
		int i = cdi2.predizerImagem("", "/home/cmte/face-recognizer/resources/obama1.jpg");
		
		System.out.println();
		System.out.println();
		System.out.println("Matched: " + i);
		System.out.println();
		System.out.println();
	}

}
