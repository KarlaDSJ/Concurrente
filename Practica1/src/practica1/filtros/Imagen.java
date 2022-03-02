package practica1.filtros;
//para leer y crear img
import java.io.File;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
//Para mostrar las img
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Clase que crea una imagen para aplicarle filtros 
 * y mostrarlos en pantalla
 */
public class Imagen {
    //Archivo de la imagen original
    private BufferedImage img;
    /* RGB original
        [0] - r [1] - g [2] - b
    */
    private Color[] rgb;
    // RGB para aplicar un filtro
    private Color[] rgbFiltro;
    private int ancho;
    private int alto; 
    
    // un método para aplicar un filtro ** uno para concurrente y otro normal
    // otro método para guardar la imagen con el filtro

    public Imagen() throws Exception{
        File file= new File("/home/karla/Documentos/Concurrente/Practica1/src/practica1/filtros/secuencial/ejemplo.jpeg");
        img = ImageIO.read(file);
        ancho = img.getWidth();
        alto = img.getHeight();
        Color[] rgb = new Color[alto*ancho];

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //Retrieving contents of a pixel
                int pixel = img.getRGB(x,y);
                //Objeto color de cada pixel
                Color color = new Color(pixel, true);
                //Obtenemos los valores R G B 
                rgb[(ancho*y)+x] = color;
            }
        }
        rgbFiltro = rgb;
    }

    /**
     * @desc Regresa los valores RGB de cada pixel
     * @return Array - una copia del arreglo
     */
    public Color[] getRGB(){
        return rgb;
    }

    /**
     * @desc Regresa los valores RGB de cada pixel con los filtros
     * @return Array - una copia del arreglo 
     */
    public Color[] getRGBFiltro(){
        return rgbFiltro;
    }

    /**
     * Crea una imagen con los pixeles a los que le aplicamos algún filtro
     * @return la nueva imagen 
     */
    private BufferedImage getImgFiltro(){
        BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                imagen.setRGB(x,y, rgbFiltro[(ancho*y)+x].getRGB());
            }
        }
        return imagen;
    }

    /**
     * @desc Muestra en pantalla la imagen con y sin filtros
     */
    public void mostrarImagen(){
        //Cargamos las img
        ImageIcon original = new ImageIcon(img);
        ImageIcon filtro = new ImageIcon(getImgFiltro());
        //Creamos la vantana
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        //Le damos un tamaño
        frame.setSize(1000, 600);
        panel.setPreferredSize (new Dimension(1000, 600));
        //Agregamos las img a la ventana
        JLabel label1 = new JLabel(original, SwingConstants.LEFT);
        JLabel label2 = new JLabel(filtro, SwingConstants.RIGHT);
        panel.add(label1);
        panel.add(label2);
        //Las mostramos
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * @desc Limpia los datos de la imagen con filtro 
     */
    public void reset(){
        rgbFiltro = rgb;
    }
}
