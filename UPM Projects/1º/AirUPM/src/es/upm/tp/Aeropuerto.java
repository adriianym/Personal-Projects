package es.upm.tp;

/**
 * Aeropuerto es una clase que encapsula las variables
 * de texto empleadas para dar nombre al aeropuerto,
 * para definir su código IATA; las variables dobles
 * que definen la latitud y la longitud respectivas
 * a la posición del aeropuerto; y la variable entera
 * que define el número de terminales que posee el
 * aeropuerto.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class Aeropuerto {

    /**
     * Nombre del aeropuerto.
     */
    private String nombre;

    /**
     * Código IATA del aeropuerto
     * Valor de 3 letras.
     */
    private String codigo;

    /**
     * Latitud en la que se encuentra el aeropuerto.
     */
    private double latitud;

    /**
     * Longitud en la que se encuentra el aeropuerto.
     */
    private double longitud;

    /**
     * Terminales que dispone el aeropuerto.
     */
    private int terminales;

    /**
     * Constructor de la clase Aeropuerto que define
     * el nombre, el código IATA, la posición y la
     * cantidad de terminales que posee el aeropuerto.
     *
     * @param nombre     Nombre del aeropuerto.
     * @param codigo     Código IATA del aeropuerto.
     * @param latitud    Latitud en la que se encuentra el aeropuerto.
     * @param longitud   Longitud en la que se encuentra el aeropuerto.
     * @param terminales Terminales que dispone el aeropuerto.
     */
    public Aeropuerto(String nombre, String codigo, double latitud, double longitud, int terminales) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.terminales = terminales;
    }

    /**
     * Getter del atributo nombre.
     *
     * @return El nombre del aeropuerto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter del atributo codigo.
     *
     * @return El código IATA del aeropuerto.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Getter del atributo latitud.
     *
     * @return La latitud a la que se encuentra el aeropuerto.
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Getter del atributo longitud.
     *
     * @return La longitud a la que se encuentra el aeropuerto.
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Getter del atributo terminales.
     *
     * @return El número de terminales que tiene el aeropuerto.
     */
    public int getTerminales() {
        return terminales;
    }

    /**
     * Método que calcula la distancia entre el aeropuerto que recibe
     * el mensaje y el aeropuerto de destino.
     *
     * @param destino Se refiere al aeropuerto de destino del que se
     *                quiere calcular la distancia hasta el aeropuerto que recibe el mensaje.
     * @return El valor de la distancia entre el aeropuerto principal y el
     * aeropuerto de destino.
     */
    public double distancia(Aeropuerto destino) {
        double latitudOrigen, latitudDestino, longitudOrigen, longitudDestino;
        latitudOrigen = Math.toRadians(latitud);
        latitudDestino = Math.toRadians(destino.getLatitud());
        longitudOrigen = Math.toRadians(longitud);
        longitudDestino = Math.toRadians(destino.getLongitud());
        return Math.acos(Math.sin(latitudOrigen) * Math.sin(latitudDestino)
                + Math.cos(latitudOrigen) * Math.cos(latitudDestino) * Math.cos(longitudOrigen - longitudDestino)) * 6378;
    }

    /**
     * Método que retorna una cadena de texto con el nombre del aeropuerto, su código IATA,
     * sus coordenadas y la cantidad de terminales que posee.
     *
     * @return Cadena de texto con la información relativa al aeropuerto.
     */
    public String toString() {
        return String.format("%s, en (%f %f), con %d terminales",
                toStringSimple(), latitud, longitud, terminales);
    }

    /**
     * Método que retorna una cadena de texto más simplificada, únicamente
     * con el nombre del aeropuerto y su código IATA.
     *
     * @return Cadena de texto con la información relativa al aeropuerto.
     */
    public String toStringSimple() {
        return String.format("%s(%s)", nombre, codigo);
    }
}
