package pa.iscde.formulas.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JLabel;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class FormulaImage {

	private Composite viewArea;
	private String formula_string;

	public FormulaImage(Composite viewArea2, String formula_string) {
		this.viewArea = viewArea2;
		this.formula_string = formula_string;
	}

	public org.eclipse.swt.graphics.Image getImage() {
		TeXFormula formula = new TeXFormula(formula_string);
		TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
		icon.setInsets(new Insets(5, 5, 5, 5));
		BufferedImage image = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
		JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));
		icon.paintIcon(jl, g2, 0, 0);
		return new org.eclipse.swt.graphics.Image(viewArea.getDisplay(),
				convertAWTImageToSWT(image));
	}
	
	private ImageData convertAWTImageToSWT(Image image) {
	        if (image == null) {
	            throw new IllegalArgumentException("Null 'image' argument.");
	        }
	        int w = image.getWidth(null);
	        int h = image.getHeight(null);
	        if (w == -1 || h == -1) {
	            return null;
	        }
	        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	        Graphics g = bi.getGraphics();
	        g.drawImage(image, 0, 0, null);
	        g.dispose();
	        return convertToSWT(bi);
	    }

	  
	private ImageData convertToSWT(BufferedImage bufferedImage) {
	        if (bufferedImage.getColorModel() instanceof DirectColorModel) {
	            DirectColorModel colorModel
	                    = (DirectColorModel) bufferedImage.getColorModel();
	            PaletteData palette = new PaletteData(colorModel.getRedMask(),
	                    colorModel.getGreenMask(), colorModel.getBlueMask());
	            ImageData data = new ImageData(bufferedImage.getWidth(),
	                    bufferedImage.getHeight(), colorModel.getPixelSize(),
	                    palette);
	            WritableRaster raster = bufferedImage.getRaster();
	            int[] pixelArray = new int[3];
	            for (int y = 0; y < data.height; y++) {
	                for (int x = 0; x < data.width; x++) {
	                    raster.getPixel(x, y, pixelArray);
	                    int pixel = palette.getPixel(new RGB(pixelArray[0],
	                            pixelArray[1], pixelArray[2]));
	                    data.setPixel(x, y, pixel);
	                }
	            }
	            return data;
	        }
	        else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
	            IndexColorModel colorModel = (IndexColorModel)
	                    bufferedImage.getColorModel();
	            int size = colorModel.getMapSize();
	            byte[] reds = new byte[size];
	            byte[] greens = new byte[size];
	            byte[] blues = new byte[size];
	            colorModel.getReds(reds);
	            colorModel.getGreens(greens);
	            colorModel.getBlues(blues);
	            RGB[] rgbs = new RGB[size];
	            for (int i = 0; i < rgbs.length; i++) {
	                rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF,
	                        blues[i] & 0xFF);
	            }
	            PaletteData palette = new PaletteData(rgbs);
	            ImageData data = new ImageData(bufferedImage.getWidth(),
	                    bufferedImage.getHeight(), colorModel.getPixelSize(),
	                    palette);
	            data.transparentPixel = colorModel.getTransparentPixel();
	            WritableRaster raster = bufferedImage.getRaster();
	            int[] pixelArray = new int[1];
	            for (int y = 0; y < data.height; y++) {
	                for (int x = 0; x < data.width; x++) {
	                    raster.getPixel(x, y, pixelArray);
	                    data.setPixel(x, y, pixelArray[0]);
	                }
	            }
	            return data;
	        }
	        return null;
	    }

}
