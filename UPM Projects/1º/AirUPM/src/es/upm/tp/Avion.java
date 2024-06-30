package es.upm.tp;

/**
 * Avión es una clase que encapsula las variables de texto
 * empleadas para definir la marca, el modelo y la matrícula
 * de un avión, así como sus filas, sus columnas y su alcance
 * máximo.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class Avion {

    /**
     * Marca del avión
     */
    private String marca;

    /**
     * Modelo del avión.
     */
    private String modelo;

    /**
     * Matrícula del avión.
     */
    private String matricula;

    /**
     * Filas del avión.
     */
    private int filas;

    /**
     * Columnas del avión
     */
    private int columnas;

    /**
     * Alcance del avión.
     */
    private double alcance;

    /**
     * Constructor de la clase Avión usado para fijar
     * la marca, la modelo, la matrícula del avión, como
     * sus filas, sus columnas y su alcance.
     *
     * @param marca     Marca del avión.
     * @param modelo    Modelo del avión.
     * @param matricula Matrícula del avión.
     * @param columnas  Columnas del avión.
     * @param filas     Filas del avión.
     * @param alcance   Alcance del avión.
     */
    public Avion(String marca, String modelo, String matricula, int columnas, int filas, double alcance) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.columnas = columnas;
        this.filas = filas;
        this.alcance = alcance;
    }

    /**
     * Getter del atributo marca.
     *
     * @return La marca del avión.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Getter del atributo modelo.
     *
     * @return El modelo del avión.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Getter del atributo matricula.
     *
     * @return La matrícula del avión.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Getter del atributo columnas.
     *
     * @return Las columnas que tiene el avión.
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Getter del atributo filas.
     *
     * @return Las filas que tiene el avión.
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Getter del atributo alcance.
     *
     * @return El alcance que tiene el avión.
     */
    public double getAlcance() {
        return alcance;
    }

    /**
     * Método que retorna una cadena de texto con la marca, el modelo y
     * la matrícula del avión, así como las filas y columnas que posee
     * y el alcance máximo que tiene.
     *
     * @return Cadena de texto con la información relativa al avión.
     */
    public String toString() {
        return String.format("%s: %d asientos, hasta ", toStringSimple(), (getFilas() * getColumnas())) + getAlcance() + " km";
    }

    /**
     * Método que retorna una cadena de texto más simplificada, únicamente
     * con marca, el modelo y la matrícula del avión.
     *
     * @return Cadena de texto con la información simplificada del avión.
     */
    public String toStringSimple() {
        return String.format("%s %s(%s)", getMarca(), getModelo(), getMatricula());
    }
}
