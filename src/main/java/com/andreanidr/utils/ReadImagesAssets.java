package com.andreanidr.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.andreanidr.model.Etiqueta;
import com.andreanidr.model.Imagem;

public class ReadImagesAssets {

	private String csvFileInput = "/home/cmte/face-recognizer/resources/assets/assets_list.csv";
	private String csvFileOutput = "/home/cmte/face-recognizer/resources/assets/assets_faces/assets_faces.csv";
	private BufferedReader reader = null;
	private String line = "";
	private String csvSplitBy = ",";
	private ArrayList<Imagem> faces = new ArrayList<Imagem>();

	public ArrayList<Imagem> getFaces() {
		return faces;
	}

	public ReadImagesAssets() {

	}

	public ReadImagesAssets(String csvFileInput, String csvFileOutput) {
		this.csvFileInput = csvFileInput;
		this.csvFileOutput = csvFileOutput;
	}

	public void read() {

		try {
			reader = new BufferedReader(new FileReader(csvFileInput));
			while ((line = reader.readLine()) != null) {
				String[] imagem = line.split(csvSplitBy);
				Etiqueta et = new Etiqueta();
				et.setId(Integer.parseInt(imagem[3]));
				et.setNome(imagem[1]);
				Imagem im = new Imagem(imagem[1], imagem[2], et);

				@SuppressWarnings("unused")
				DetectorDeFaces ddf = new DetectorDeFaces(im, csvFileOutput);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String findImageByLabel(Integer label)
	{
		try {
			reader = new BufferedReader(new FileReader(csvFileInput));
			while ((line = reader.readLine()) != null) {
				String[] imagem = line.split(csvSplitBy);
				if (imagem[3].equals(Integer.toString(label)))
				{
					return imagem[1];
				}
			}
		}catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return new String ("");
	}

	public void readFaces() {
		try {
			reader = new BufferedReader(new FileReader(csvFileOutput));
			while ((line = reader.readLine()) != null) {
				String[] imagem = line.split(csvSplitBy);
				Etiqueta et = new Etiqueta();
				et.setId(Integer.parseInt(imagem[3]));
				et.setNome(imagem[0]);
				Imagem im = new Imagem(imagem[0], imagem[2], et);
				faces.add(im);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
