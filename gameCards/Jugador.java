package gameCards;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;
import javax.swing.JPanel;

public class Jugador {

    public int TOTAL_CARTAS = 10;

    private Random r = new Random();

    private Carta[] cartas;

    public void repartir() {
        cartas = new Carta[TOTAL_CARTAS];
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        for (int i = 0; i < cartas.length; i++) {
            cartas[i].mostrar(pnl, 5 + i * 40, 5);
        }
        pnl.repaint();
    }
    // Método para obtener los puntos de una carta
    private int obtenerPuntosCarta(Carta carta) {
        int indice = carta.getIndice() % 13;
        return (indice == 0 || indice == 1 || indice == 11 || indice == 12) ? 10 : indice;
    }

// Método para incrementar una escalera
    private void incrementarSecuencia(ArrayList<Integer> escaleras, ArrayList<String> pintasEscalera, ArrayList<Integer> puntosCartas, Carta carta) {
        int ultimoElemento = escaleras.size() - 1;
        escaleras.set(ultimoElemento, escaleras.get(ultimoElemento) + 1);
        pintasEscalera.set(ultimoElemento, carta.getPinta().name());
        puntosCartas.set(ultimoElemento, puntosCartas.get(ultimoElemento) + obtenerPuntosCarta(carta));
    }

// Método para iniciar una nueva escalera
    private void iniciarNuevaSecuencia(ArrayList<Integer> escaleras, ArrayList<String> pintasEscalera, ArrayList<Integer> puntosCartas, Carta carta) {
        escaleras.add(1);
        pintasEscalera.add(carta.getPinta().name());
        puntosCartas.add(obtenerPuntosCarta(carta));
    }
    
// Método principal para obtener las escaleras y calcular los puntos
    public String getEscaleras() {
        // Ordenamos las cartas por índice
        Arrays.sort(cartas, Comparator.comparingInt(Carta::getIndice));

        // Inicializamos las listas para almacenar las escaleras, las pintas y los puntos
        ArrayList<Integer> escaleras = new ArrayList<>();
        ArrayList<String> pintasEscalera = new ArrayList<>();
        ArrayList<Integer> puntosCartas = new ArrayList<>();

        // Iniciamos la primera "escalera" con la primera carta
        iniciarNuevaSecuencia(escaleras, pintasEscalera, puntosCartas, cartas[0]);

        // Recorremos el resto de las cartas
        for (int i = 1; i < cartas.length; i++) {
            // Si la carta actual es la siguiente en la secuencia y tiene la misma pinta que la anterior
            if (cartas[i-1].getIndice() == cartas[i].getIndice()-1 && cartas[i-1].getPinta() == cartas[i].getPinta()){
                // Incrementamos la escalera actual
                incrementarSecuencia(escaleras, pintasEscalera, puntosCartas, cartas[i]);
            } 
            // Si la carta actual no es la siguiente en la secuencia o no tiene la misma pinta que la anterior
            else if (cartas[i-1].getIndice() != cartas[i].getIndice() || cartas[i-1].getPinta() != cartas[i].getPinta()){
                // Iniciamos una nueva "escalera" con la carta actual
                iniciarNuevaSecuencia(escaleras, pintasEscalera, puntosCartas, cartas[i]);
            }
        }

        // Verificamos si hay alguna escalera (secuencia de más de 2 cartas)
        boolean hayEscalera = escaleras.stream().anyMatch(e -> e > 2);

        // Inicializamos los puntos que no son de escaleras
        Integer puntosNoEscalera = 0;

        // Preparamos el mensaje de salida
        StringBuilder mensaje = new StringBuilder(hayEscalera ? "Las escaleras encontradas fueron:\n" : "No hay escaleras\n");

        // Recorremos las "escaleras" para construir el mensaje y calcular los puntos
        for (int i = 0; i < escaleras.size(); i++) {
            // Si hay una escalera y la "escalera" actual tiene más de 2 cartas
            if (hayEscalera && escaleras.get(i) > 2){
                // Añadimos la escalera al mensaje
                mensaje.append("Escalera de ").append(escaleras.get(i)).append(" cartas de la pinta ").append(pintasEscalera.get(i)).append("\n");
            } else {
                // Si la "escalera" actual tiene 1 o 2 cartas, añadimos sus puntos a los puntos que no son de escaleras
                puntosNoEscalera += puntosCartas.get(i);
            }
        }

        // Añadimos los puntos que no son de escaleras al mensaje
        mensaje.append("\nPuntos que no son escalera: ").append(puntosNoEscalera);

        // Añadimos las escaleras y los puntos de las cartas al mensaje
        mensaje.append("\nEscaleras\n").append(escaleras.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        mensaje.append("\nPuntos cartas\n").append(puntosCartas.stream().map(String::valueOf).collect(Collectors.joining(" ")));

        // Devolvemos el mensaje como string
        return mensaje.toString();
    }

    public String getGrupos() {
        String mensaje = "No hay grupos";

        int[] contadores = new int[NombreCarta.values().length];
        for (int i = 0; i < cartas.length; i++) {
            contadores[cartas[i].getNombre().ordinal()]++;
        }

        int totalGrupos = 0;
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) {
                totalGrupos++;
            }
        }
        if (totalGrupos > 0) {
            mensaje = "Los grupos encontrados fueron:\n";
            for (int i = 0; i < contadores.length; i++) {
                if (contadores[i] >= 2) {
                    mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i]+"\n";
                }
            }
        }
        return mensaje;
    }
}
