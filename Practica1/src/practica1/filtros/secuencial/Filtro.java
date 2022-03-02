package practica1.filtros.secuencial;
import java.awt.Color;
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

  /**
    * @desc Asigna los valores RGB
    * @param rgb  
  */
  public Filtro (Color[] rgbNuevo){
    this.rgb = rgbNuevo;
  }

  /**
     * @desc Dada una opción aplica un filtro
     * @param op número de la opción del filtro
     */
    public void aplicarFiltro(int op){
      UnaryOperator<Color> f;
      switch (op) {
          //Filtros Grises
          case 1:
              f = (color) -> {
                  int prom = validarRango((color.getRed() + color.getGreen() + color.getBlue()) / 3);
                  return new Color(prom, prom, prom);
              };
              this.doPorPixel(f);
              break;
          case 2:
              f = (color) -> {
                  int r = validarRango(color.getRed()*.03);
                  int g = validarRango(color.getGreen()*.59);
                  int b = validarRango(color.getBlue()*0.11);
                  return new Color(r, g, b);
              };
              this.doPorPixel(f);
              break;
          case 3:
              f = (color) -> {
                  int r = validarRango(color.getRed()*.2126);
                  int g = validarRango(color.getGreen()*0.7152);
                  int b = validarRango(color.getBlue()*0.0722);
                  return new Color(r, g, b);
              };
              this.doPorPixel(f);
              break;
          case 4:
              f = (color) -> {
                  return new Color(color.getRed(), color.getRed(), color.getRed());
              };
              this.doPorPixel(f);
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
              this.doPorPixel(f);
              break;
          //RGB
          case 6:
              f = (color) -> {
                int r = validarRango(color.getRed() & 25);
                int g = validarRango(color.getGreen() &  25);
                int b = validarRango(color.getBlue() &  -18);
                return new Color(r, g, b);
            };
              this.doPorPixel(f);
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
   public Color[] doPorPixel(UnaryOperator<Color> f){
    for(var alfa = 0; alfa < this.rgb.length; alfa++){
      this.rgb[alfa] = f.apply(this.rgb[alfa]);
    }
    return this.rgb;
   }

  /**
     * @desc Calcula el promedio del color de los pixeles
     *       en un cuadrado de long * long
     * @param {number} radio[0] - Ancho del cuadrado
     * @param {number} radio[1] - Alto del cuadrado
     * @param {number} alfa - Punto de inicio del cuadrado coordenada x
     * @param {number} beta - Punto de inicio del cuadrado coordenada y
     * @return {Array} - Arreglo con el promedio del cuadrado por colores
     *                 [0] - promedio del color rojo
     *                 [1] - promedio del color verde
     *                 [2] - promedio del color azul
   */
  // _avarage(radio, alfa, beta){
  //     let suma = [0, 0, 0];//Suma: sum[0]-rojo, sum[1]-verde, sum[2]-azul
  //     let numElem = radio[0] * radio[1];
  //     let site;
  //     for (var i = 0; i < radio[1]; i ++)
  //         for (var j = 0; j < radio[0]; j++) {
  //             site = this.width * (alfa + j) + beta + i;
  //             site = site > this.nPixels? this.nPixels - 1: site;
  //             suma[0] += this._validarRango(this.red[site]);
  //             suma[1] += this._validarRango(this.green[site]);
  //             suma[2] += this._validarRango(this.blue[site]);
  //         }
  //     suma = [suma[0]/numElem, suma[1]/numElem, suma[2]/numElem];//promedio
  //     return suma;
  // }

 /**
    * @desc Crea cuadrantes de ancho * alto en la imagen
    *       El resultado se muestra en el objeto canvas del html
    * @param {number} radio[0] - Ancho del cuadrado
    * @param {number} radio[1] - Alto del cuadrado
  */
  /* doMosaic(radio){
    for (var alfa = 0; alfa < this.height; alfa += radio[1])
      for (var beta = 0; beta < this.width; beta += radio[0]) {
        let prom = this._avarage(radio, alfa, beta);
        let rgb = "rgb("+prom[0]+ ","+prom[1]+ ","+prom[2]+")";
        this.canvasContext.fillStyle = rgb;
        this.canvasContext.fillRect(beta, alfa, radio[0], radio[1]);
    }
    this._updateImageData();
  } */

  /**
    * @desc Calcula el factor de la matriz de convolución 
    * @param {Array} matrix - Matriz de convolución 
    * @return {number} regresa el factor de la matriz, si la suma es
    * mayor a 1 se divide 
  */
  /* _getFactor(matrix){
    let suma = 0
    for (var alfa = 0; alfa < matrix.length; alfa ++)
      for (var beta = 0; beta < matrix.length; beta ++) {
          suma += matrix[alfa][beta];
      }
    suma = suma == 0? 1: 1 / suma;
    return suma;
  } */

  /**
   * Aplica la matriz de convolución a un pixel
   * @param {Array} matrix matriz de convolución
   * @param {number} alfa coordenada x
   * @param {number} beta coordenada y
   * @param {boolean} isMax nos indica si es convolución 
   * normal o filtro maxMin
   * @returns el valor del pixel
   */
  /* _applyMatrix(matrix, alfa, beta, isMax) {
    let valor = [0, 0, 0]; // [0] - r, [1] - g, [2] - b
    let val = this.aux[alfa* this.filtro.width + beta];
    var length = matrix.length;
    for (var i = 0; i < length; i ++)
      for (var j = 0; j < length; j++) {
        var x = (alfa + i - Math.trunc(length/2));
        var y = (beta + j - Math.trunc(length/2));
        var site = this.filtro.width * x + y;
        if(isMax == 3){
          valor[0] += this.filtro.red[site] * matrix[i][j];
          valor[1] += this.filtro.green[site] * matrix[i][j];
          valor[2] += this.filtro.blue[site] * matrix[i][j];
        } else { //Encontramos al Max o al Min
          if(isMax)
            val = Math.max(val, this.aux[site]);
          else
            val = Math.min(val, this.aux[site]);
        }
      }
    if(isMax != 3)
      valor[1] = valor[2] = valor[0] = val;
    return valor;
  } */

  /**
    * @desc Aplica una matriz de convolución a cada pixel
    * @param {Array} matrix - Matriz de convolución 
    * @param {number} biaas - Brillo de la imagen
  */
  /* doConvolution(matrix, bias = 0, isMax = 3){
    let factor = isMax == 3? this._getFactor(matrix): 1;

    for (var i = 0; i < this.filtro.nPixels; i ++){
      //Calculamos alto y ancho (ubicación del pixel)
      var h = Math.trunc(i / this.filtro.height);
      var w = i % this.filtro.height;
      var valor =  this._applyMatrix(matrix, w, h, isMax); //aplicamos la matriz
      //Asignamos los nuevos valores
      this.filtro.red[w * this.filtro.width + h] = this.filtro._validarRango(factor * valor[0] + bias);
      this.filtro.green[w * this.filtro.width + h] = this.filtro._validarRango(factor * valor[1] + bias);
      this.filtro.blue[w * this.filtro.width + h] = this.filtro._validarRango(factor * valor[2] + bias);
    }
    this.filtro._setFromRGB(this.filtro.red, this.filtro.green, this.filtro.blue);
    this.filtro._updateImageData();
  } */
}