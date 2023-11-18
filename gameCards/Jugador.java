package gameCards;

import java.awt.List;
import java.util.ArrayList;
import java.util.Random;
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
    
    public List<Pinta> obtenerFigurasEnEscaleraYPuntaje() {

        List<Pinta> figurasEnEscalera = new ArrayList<>();
        int puntaje = 0;

        for (int i = 0; i < cartas.size() - 3; i++) {

            if (cartas.get(i).getPinta() == cartas.get(i + 1).getPinta() &&
                cartas.get(i).getPinta() == cartas.get(i + 2).getPinta() &&
                cartas.get(i).getPinta() == cartas.get(i + 3).getPinta()) {

                figurasEnEscalera.add(cartas.get(i).getPinta());
            }

            if (cartas.get(i).getNumero() >= 10) {
              puntaje += 10;
            } else {
              puntaje += cartas.get(i).getNumero();
            }
        }

        return figurasEnEscalera;
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
            mensaje = "Los gruspo encontrados fueron:\n";
            for (int i = 0; i < contadores.length; i++) {
                if (contadores[i] >= 2) {
                    mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i]+"\n";
                }
            }
        }
        return mensaje;
    }
}
