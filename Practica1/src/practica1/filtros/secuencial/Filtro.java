package practica1.filtros.secuencial;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;




/**
 * @class
 * Clase filtro para controlar los filtros que se le aplican a la imagen.
 */
public class Filtro {
  /* RGB de la imagen 
      [0] - r [1] - g [2] - b
    */
  private Color[] rgb;
  private int ancho; // Alto de la imagen
  private int alto; // Ancho de la imagen

  /**
    * @desc Asigna los valores RGB
    * @param rgb  
  */
  public Filtro (Color[] rgbNuevo, int alto, int ancho){
    this.rgb = rgbNuevo;
    this.ancho = ancho;
    this.alto = alto;
  }

  /**
     * @desc Dada una opción aplica un filtro
     * @param op número de la opción del filtro
     */
    public void aplicarFiltro(int op, boolean sec){
      UnaryOperator<Color> f;
      switch (op) {
          //Filtros Grises
          case 1:
              f = (color) -> {
                  int prom = validarRango((color.getRed() + color.getGreen() + color.getBlue()) / 3);
                  return new Color(prom, prom, prom);
              };
              sec ? this.doPorPixel(f) : this.doPorPixelConcurrente(f);
              break;
          case 2:
              f = (color) -> {
                  int r = validarRango(color.getRed()*.03);
                  int g = validarRango(color.getGreen()*.59);
                  int b = validarRango(color.getBlue()*0.11);
                  return new Color(r, g, b);
              };
              sec ? this.doPorPixel(f) : this.doPorPixelConcurrente(f);
              break;
          case 3:
              f = (color) -> {
                  int r = validarRango(color.getRed()*.2126);
                  int g = validarRango(color.getGreen()*0.7152);
                  int b = validarRango(color.getBlue()*0.0722);
                  return new Color(r, g, b);
              };
              sec ? this.doPorPixel(f) : this.doPorPixelConcurrente(f);
              break;
          case 4:
              f = (color) -> {
                  return new Color(color.getRed(), color.getRed(), color.getRed());
              };
              sec ? this.doPorPixel(f) : this.doPorPixelConcurrente(f);
              break;
          //Alto contraste
          case 5:
              f = (color) -> {
                  int r = validarRango(color.getRed()*.03);
                  int g = validarRango(color.getGreen()*.59);
                  int b = validarRango(color.getBlue()*0.11);
                  int val = (r + g + b) > 127? 255: 0;
                  return new Color(val, val, val);
              };
              sec ? this.doPorPixel(f) : this.doPorPixelConcurrente(f);
              break;
          //RGB
          case 6:
              f = (color) -> {
                //Falta ver como leer las variables
                int r = validarRango(color.getRed() & 25);
                int g = validarRango(color.getGreen() &  25);
                int b = validarRango(color.getBlue() &  -18);
                return new Color(r, g, b);
            };
              sec ? this.doPorPixel(f) : this.doPorPixelConcurrente(f);
              break;
          case 7:
          case 8:
          case 9:
          case 10:
            this.selecConvolucion(op - 7);
            break;
          default:
              break;
      }
  }

  /**
   * @desc Valida que el valor este ente 0 y 255
   * @return {number} valor RGB del pixel
  */
  public int validarRango(double v){
    int valor = (int) v;
    if(valor > 255)
      valor = 255;
    else if(valor < 0)
      valor = 0;

    return valor;
  }

  /**
     * @desc Modifica los colores de cada pixel
     *       Muestra el resultado en el objeto canvas del html
     * @param {function} f - función que especifica la modificación
     *                   que se le hará a cada color del pixel
   */
   public void doPorPixel(UnaryOperator<Color> f){
    for(var alfa = 0; alfa < this.rgb.length; alfa++){
      this.rgb[alfa] = f.apply(this.rgb[alfa]);
    }
   }

   public void doPorPixelConcurrente(UnaryOperator<Color> f) throws InterruptedException{
    FiltroConcurrente mc = new FiltroConcurrente(this.rgb, null, f);
    List<Thread> hilosh = new ArrayList<>();
    int n = this.rgb.length;
    int hilos = 5;        

        for(int i = 0; i < n; i++){
            Thread t = new Thread(mc,""+i);
            hilosh.add(t);
            t.start();

            if(hilosh.size() == hilos){
                for(Thread threads: hilosh){
                    threads.join();
                }
                hilosh.clear();
            }
        }

        for(Thread threads: hilosh){
            threads.join();
        }
   }
   
  /**
    * @desc Calcula el factor de la matriz de convolución 
    * @param {Array} matrix - Matriz de convolución 
    * @return {number} regresa el factor de la matriz, si la suma es
    * mayor a 1 se divide 
  */
  public float getFactor(double[][] matrix){
    float suma = 0;
    for (int alfa = 0; alfa < matrix.length; alfa ++)
      for (int beta = 0; beta < matrix.length; beta ++) {
          suma += matrix[alfa][beta];
      }
    suma = suma == 0? 1: 1 / suma;
    return suma;
  }

  /**
   * Aplica la matriz de convolución a un pixel
   * @param {Array} matrix matriz de convolución
   * @param {number} alfa coordenada x
   * @param {number} beta coordenada y
   * @returns el valor del pixel
   */
   private int[] applyMatrix(double[][] matrix, int alfa, int beta) {
    int[] valor = {0, 0, 0}; // [0] - r, [1] - g, [2] - b
    int length = matrix.length;
    for (int i = 0; i < length; i ++)
      for (var j = 0; j < length; j++) {
        int x = Math.floorMod((alfa + i - (length/2)),this.ancho);
        int y = Math.floorMod((beta + j - (length/2)), this.alto);
        int site = this.ancho * x + y;
        valor[0] += this.rgb[site].getRed() * matrix[i][j];
        valor[1] += this.rgb[site].getGreen() * matrix[i][j];
        valor[2] += this.rgb[site].getBlue() * matrix[i][j];
      }
    return valor;
  } 

  /**
    * @desc Aplica una matriz de convolución a cada pixel
    * @param {Array} matrix - Matriz de convolución 
  */
  public void doConvolution(double[][] matrix){
    float factor = this.getFactor(matrix);

    for (int i = 0; i < this.rgb.length; i ++){
      //Calculamos alto y ancho (ubicación del pixel)
      int h = i / this.alto;
      int w = i % this.alto;
      int[] valor =  this.applyMatrix(matrix, w, h); //aplicamos la matriz
      //Asignamos los nuevos valores
      int r = this.validarRango(factor * valor[0]);
      int g = this.validarRango(factor * valor[1]);
      int b = this.validarRango(factor * valor[2]);
      this.rgb[w * this.ancho + h] = new Color(r, g, b);
    }
  }

  public void doConvolutionConcurrente (double[][] matrix) throws InterruptedException{
    FiltroConcurrente mc = new FiltroConcurrente(this.rgb, matrix, null);
    List<Thread> hilosh = new ArrayList<>();
    int n = this.rgb.length;
    int hilos = 5;        

        for(int i = 0; i < n; i++){
            Thread t = new Thread(mc,""+i);
            hilosh.add(t);
            t.start();

            if(hilosh.size() == hilos){
                for(Thread threads: hilosh){
                    threads.join();
                }
                hilosh.clear();
            }
        }

        for(Thread threads: hilosh){
            threads.join();
        }
  
  }


  public void selecConvolucion(int op){
    double[][][] matriz = {
      //Blur 1
      {{0.0, 0.2, 0.0}, 
      {0.2, 0.2, 0.2}, 
      {0.0, 0.2, 0.0}}, 
      //Blur 2
      {{0, 0, 1, 0, 0},
      {0, 1, 1, 1, 0},
      {1, 1, 1, 1, 1},
      {0, 1, 1, 1, 0},
      {0, 0, 1, 0, 0}},
      //Blur 3
      {{1, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 1, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 1, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 1, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 1, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 1, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 1, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 1}},
      //Sharpen
      {{-1, -1, -1}, 
      {-1, 9, -1}, 
      {-1, -1, -1}}, 
    };
    this.doConvolution(matriz[op]);
  }



  class FiltroConcurrente implements Runnable{    
    private Color[] salida;
    private double[][] matrix; 
    private UnaryOperator<Color> f;
    
    public FiltroConcurrente(Color[] salida, double[][] matrix, UnaryOperator<Color> f){
      this.salida = salida;
      this.matrix = matrix;
      this.f = f;
    }


    public void convolucionConcurrente (int i){
      float factor = getFactor(this.matrix);
      
        //Calculamos alto y ancho (ubicación del pixel)
        int h = i / alto;
        int w = i % alto;
        int[] valor =  applyMatrix(this.matrix, w, h); //aplicamos la matriz
        //Asignamos los nuevos valores
        int r = validarRango(factor * valor[0]);
        int g = validarRango(factor * valor[1]);
        int b = validarRango(factor * valor[2]);
        this.salida[w * ancho + h] = new Color(r, g, b);      
    }    

    public void doPorPixelConcurrente(){
      for(var alfa = 0; alfa < this.salida.length; alfa++){
        this.salida[alfa] = f.apply(rgb[alfa]);
      }
     }

    @Override
    public void run() {
        String posicion = Thread.currentThread().getName();
        convolucionConcurrente(Integer.parseInt(posicion));
    }

}




}