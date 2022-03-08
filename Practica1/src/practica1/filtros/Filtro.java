package practica1.filtros;
//Para el color del pixel
import java.awt.Color;
//Para las lambdas
import java.util.function.UnaryOperator;
//Para los hilos
import java.util.ArrayList;
import java.util.List;

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

  final private int NUM_HILOS = 100;

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
    * @param sec nos indica si aplicar el filtro de manera
    *            concurrente o secuencial
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
              if (sec) this.doPorPixel(f); else this.doConcurrente(null,f, "0");
              break;
          case 2:
              f = (color) -> {
                  int r = validarRango(color.getRed()*.03);
                  int g = validarRango(color.getGreen()*.59);
                  int b = validarRango(color.getBlue()*0.11);
                  return new Color(r, g, b);
              };
              if (sec) this.doPorPixel(f); else this.doConcurrente(null,f, "0");
              break;
          case 3:
              f = (color) -> {
                  int r = validarRango(color.getRed()*.2126);
                  int g = validarRango(color.getGreen()*0.7152);
                  int b = validarRango(color.getBlue()*0.0722);
                  return new Color(r, g, b);
              };
              if (sec) this.doPorPixel(f); else this.doConcurrente(null,f, "0");
              break;
          case 4:
              f = (color) -> {
                  return new Color(color.getRed(), color.getRed(), color.getRed());
              };
              if (sec) this.doPorPixel(f); else this.doConcurrente(null,f, "0");
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
              if (sec) this.doPorPixel(f); else this.doConcurrente(null,f, "0");
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
              if (sec) this.doPorPixel(f); else this.doConcurrente(null,f, "0");
              break;
          case 7:
          case 8:
          case 9:
          case 10:
            this.selecConvolucion(op - 7, sec);
            break;
          default:
            throw new IllegalArgumentException("Opcion no valida");
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
     * @param f - función que especifica la modificación
     *                   que se le hará a cada color del pixel
   */
   public void doPorPixel(UnaryOperator<Color> f){
    for(var alfa = 0; alfa < this.rgb.length; alfa++){
      this.rgb[alfa] = f.apply(this.rgb[alfa]);
    }
   }
   
  /**
    * @desc Calcula el factor de la matriz de convolución 
    * @param matrix - Matriz de convolución 
    * @return el factor de la matriz, si la suma es
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
   * @param matrix matriz de convolución
   * @param alfa coordenada x
   * @param beta coordenada y
   * @returns el valor del pixel
   */
   private int[] applyMatrix(double[][] matrix, int alfa, int beta) {
    int[] valor = {0, 0, 0}; // [0] - r, [1] - g, [2] - b
    int length = matrix.length;
    for (int i = 0; i < length; i ++)
      for (var j = 0; j < length; j++) {
        //Calculamos alto y ancho (ubicación del pixel)
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
    * @param matrix - Matriz de convolución 
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

  /**
    * @desc Aplica una matriz de convolución a cada pixel
            o odifica los colores de cada pixel , de manera concurrente
    * @param matrix - Matriz de convolución 
    * @param f - función que especifica la modificación
    *            que se le hará a cada color del pixel
    * @param op - nos indica si modificamos el color o aplicamos una matriz
  */
  public void doConcurrente(double[][] matrix, 
                            UnaryOperator<Color> f,String op){
    try {
      FiltroConcurrente mc = new FiltroConcurrente(this.rgb, matrix, f);
      List<Thread> hilosh = new ArrayList<>();
      int hilos = NUM_HILOS;        

      for(int i = 0; i < alto; i++){
        Thread t = new Thread(mc,op+"-"+i);
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
    } catch(InterruptedException e) {
      System.out.println(e.getMessage()); 
    }                         
  }

   /**
    * @desc Selecciona y aplica una matriz de convolución
    * @param op - nos dice que matriz aplicar
    * @param sec - indica si se aplica de manera concurrente o secuencial
  */
  public void selecConvolucion(int op, boolean sec){
    double[][][] matriz = {
      //Blur 1, op 7
      {{0.0, 0.2, 0.0}, 
      {0.2, 0.2, 0.2}, 
      {0.0, 0.2, 0.0}}, 
      //Blur 2, op 8
      {{0, 0, 1, 0, 0},
      {0, 1, 1, 1, 0},
      {1, 1, 1, 1, 1},
      {0, 1, 1, 1, 0},
      {0, 0, 1, 0, 0}},
      //Blur 3, op 9
      {{1, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 1, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 1, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 1, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 1, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 1, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 1, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 1}},
      //Sharpen , op 10
      {{-1, -1, -1}, 
      {-1, 9, -1}, 
      {-1, -1, -1}}, 
    };
    if (sec) this.doConvolution(matriz[op]); else this.doConcurrente(matriz[op], null, "1");
  }

  /**
   * @class
   * Permite aplicar los filtros a la imagen de manera concurrente.
  */
  class FiltroConcurrente implements Runnable{    
    private Color[] salida; //RGB de la imagen
    private double[][] matrix; //matriz de convolución
    //función que especifica la modificación que se le hará a cada color del pixel
    private UnaryOperator<Color> f;
    

    public FiltroConcurrente(Color[] salida, double[][] matrix, UnaryOperator<Color> f){
      this.salida = salida;
      this.matrix = matrix;
      this.f = f;
    }

    /**
      * @desc Aplica una matriz de convolución concurrente
      * @param posicion - número de la fila (hilo)
    */
    public void convolucionConcurrente (int posicion){
      float factor = getFactor(this.matrix);
      for (int i = 0 ; i < ancho; i++) {
        //Calculamos alto y ancho (ubicación del pixel)
        int h = (posicion * ancho + i) / alto;
        int w = (posicion * ancho + i) % alto;
        int[] valor =  applyMatrix(this.matrix, w, h); //aplicamos la matriz
        //Asignamos los nuevos valores
        int r = validarRango(factor * valor[0]);
        int g = validarRango(factor * valor[1]);
        int b = validarRango(factor * valor[2]);
        this.salida[w * ancho + h] = new Color(r, g, b);      
      }
      
    }    

    public void doPorPixelConcurrente(int posicion){
      for (int i = 0; i < ancho; i++) {
        this.salida[posicion * ancho + i] = f.apply(rgb[posicion * ancho + i]);
      }
      
    }

    @Override
    public void run() {
        String[] banderas = Thread.currentThread().getName().split("-");
        if (banderas[0].equals("0"))
          this.doPorPixelConcurrente(Integer.parseInt(banderas[1]));
        else
          this.convolucionConcurrente(Integer.parseInt(banderas[1]));
    }

}

}