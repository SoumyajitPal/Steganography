import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class GreenChange {
	
	private BufferedImage newImage = null;
	int width;
	int height;
	int red;
	int blue;
	int green;
	int col;
	
	public GreenChange() {
		// TODO Auto-generated constructor stub
	}
	
	public GreenChange(String Source, String Text) {
		// TODO Auto-generated constructor stub
		try {
			newImage = ImageIO.read(new File(Source));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		Source = Source.replace("\\", "\\\\");
		int i = 0, j = 0;
		int flag = 0;
		width = newImage.getWidth();
		height = newImage.getHeight();
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		BufferedReader buffer = null;
		File file = null;
		try {
			file = new File(Text);
			buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		long factor = (height*width)/file.length();
		if(factor<1){
			JOptionPane.showMessageDialog(null, "The given file is too large to fit in this picture!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(null, "The factor is : "+ factor);
		}
		factor = (long)Math.sqrt((double)factor);
		char ch;
		int end;
		Color c;
		try {
			for(i=0; i<height; i++){
				for(j=0;j<width; j++){
					c = new Color(newImage.getRGB(j, i));
					red = c.getRed();
					blue = c.getBlue();
					green = c.getGreen();
					if(i%factor==0 && j%factor==0){
						end = buffer.read();
						if(end!=-1){
							ch = (char)end;
							
							green = (int)ch;
							
							System.out.print(ch);
						}
						else{
							flag = 1;
							break;
						}
					}
					
					col = (red<<16)|(green<<8)|blue;
					img.setRGB(j, i, col);
				}
				if(flag == 1)break;
			}
			while(i<height){
				for(j=0; j<width; j++){
					col = newImage.getRGB(j, i);
				    img.setRGB(j, i, col);
				    
				}
				i++;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println("Conversion completed");           
		try {
			buffer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*for(i=0; i<18; i++){
			for(j=0; j<img.getWidth(); j++){
				System.out.print((char)(new Color(img.getRGB(j, i))).getBlue());
			}
		}*/
		
		try {
			ImageIO.write(img, "png", new FileOutputStream("Green"+Source.substring(Source.length()-8, Source.length()-3)+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(i=0; i<img.getHeight(); i+=factor){
			for(j=0; j<img.getWidth(); j+=factor){
				c = new Color(img.getRGB(j, i));
				System.out.print((char)c.getGreen());
			}
		}
	}

}
