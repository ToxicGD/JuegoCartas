/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameCards;

/**
 *
 * @author Daimer
 */
public class PintaEscalera{
    
 private String pinta;
  private int numero;

  public PintaEscalera(String pinta, int numero) {
    this.pinta = pinta;
    this.numero = numero;
  }

  public String getPinta() {
    return pinta;
  }

  public void setPinta(String pinta) {
    this.pinta = pinta;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }
}