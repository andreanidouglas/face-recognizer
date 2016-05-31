package com.andreanidr.utils;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvClearMemStorage;
import static org.bytedeco.javacpp.opencv_core.cvCopy;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_INTER_AREA;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_ROUGH_SEARCH;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT;

import java.io.File;
import java.io.IOException;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.CvSize;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

import com.andreanidr.model.Imagem;

public class DetectorDeFaces {

	static int counter = 0;

	public DetectorDeFaces(Imagem imagemTemp) throws IOException {
		CvHaarClassifierCascade classificador;
		File arquivoDeClassificacao = new File("resources/haarcascade_frontalface_alt.xml");

		if (!arquivoDeClassificacao.exists()) {
			throw new IOException();
		}

		Loader.load(opencv_objdetect.class);

		classificador = new CvHaarClassifierCascade(cvLoad(arquivoDeClassificacao.getAbsolutePath()));

		CvMemStorage storage = CvMemStorage.create();

		IplImage imagem = new IplImage(imagemTemp.getImagem());

		IplImage grayImage = IplImage.create(imagem.width(), imagem.height(), IPL_DEPTH_8U, 1);
		IplImage smallImage = IplImage.create(imagem.width() / 4, imagem.height() / 4, IPL_DEPTH_8U, 1);

		cvClearMemStorage(storage);
		cvCvtColor(imagem, grayImage, CV_BGR2GRAY);
		cvResize(grayImage, smallImage, CV_INTER_AREA);
		CvSeq faces = cvHaarDetectObjects(smallImage, classificador, storage, 1.1, 3,
				CV_HAAR_FIND_BIGGEST_OBJECT | CV_HAAR_DO_ROUGH_SEARCH);

		if (faces != null) {
			int total = faces.total();
			for (int i = 0; i < total; i++) {

				CvRect rect = new CvRect(cvGetSeqElem(faces, i));
				System.out.println(rect.x() + "" + rect.y());
				cvSetImageROI(smallImage, rect);
				IplImage crop = cvCreateImage(cvGetSize(smallImage), smallImage.depth(), smallImage.nChannels());
				cvCopy(smallImage, crop);

				IplImage resize = cvCreateImage(new CvSize(128, 128), smallImage.depth(), smallImage.nChannels());
				cvResize(smallImage, resize);
				Mat newImage = new Mat(resize);
				imwrite("resources/face" + counter++ + ".jpg", newImage);

			}
		}
	}

}
