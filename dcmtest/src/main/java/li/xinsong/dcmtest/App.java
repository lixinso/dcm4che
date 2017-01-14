package li.xinsong.dcmtest;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.dcm4che3.image.BufferedImageUtils;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;

/**
 * Hello world!
 *
 */

import org.dcm4che3.tool.dcm2jpg.Dcm2Jpg;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        Dcm2Jpg.listSupportedFormats();
        
        String[] readerFormats = ImageIO.getReaderFormatNames();
        for(String f: readerFormats){
        	System.out.println(f);
        }
        
        String src = "/home/ubuntu/Downloads/CT-MONO2-16-brain";
        String dst = "/home/ubuntu/Downloads/CT-MONO2-16-brain_dst.jpg";
        ImageReader imageReader = ImageIO.getImageReadersByFormatName("dicom").next();
        try {
			ImageInputStream iis = ImageIO.createImageInputStream(new File(src));
	        imageReader.setInput(iis);
	        DicomImageReadParam param =  (DicomImageReadParam) imageReader.getDefaultReadParam();
	        BufferedImage bi = imageReader.read(0, param);
	        
	        ColorModel cm = bi.getColorModel();
	        if(cm.getNumComponents() == 3){
	        	bi = BufferedImageUtils.convertToIntRGB(bi);
	        }
            ImageOutputStream ios = ImageIO.createImageOutputStream(new File(dst));

            Iterator<ImageWriter> imageWriters =
                    ImageIO.getImageWritersByFormatName("JPEG");
            
            ImageWriter imageWriter = imageWriters.next();
            imageWriter.setOutput(ios);
            ImageWriteParam paramWriter = imageWriter.getDefaultWriteParam();
            imageWriter.write(null, new IIOImage(bi, null, null), paramWriter);
            ios.close(); 
            
            System.out.println("");
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
