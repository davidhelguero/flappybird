//COMO EL GRUPO DECIDIO, CON AUTORIZACIÓN DEL PROFESOR, NO UTILIZAR ARMA, EL CONTROL DE LOS DISPAROS SE REALIZARA EN ESTA CLASE
package juego;
import java.awt.Image;
import java.awt.Rectangle;

import javax.sound.sampled.Clip;

import entorno.Entorno;
import entorno.Herramientas;

public class Bird{
	private Rectangle bird;
	private Rectangle bala;
	private boolean estaDisparando;
	private Image imagenBird;
	private Image imagenBala;
	private Clip sonidoBala;
	
	Bird(){
		this.bird=new Rectangle(280, 290, 30, 30);
		this.bala=new Rectangle(0,0,0,0);
		this.estaDisparando=false;
		imagenBird = Herramientas.cargarImagen("Bird.png");
		imagenBala = Herramientas.cargarImagen("Bala.png");
		sonidoBala = Herramientas.cargarSonido("Bala.wav");
	}
	
	public Rectangle getBird() {
		return bird;
	}

	public Rectangle getBala() {
		return bala;
	}

	public boolean getEstaDisparando() {
		return estaDisparando;
	}

	public void setEstaDisparando(boolean estaDisparando) {
		this.estaDisparando = estaDisparando;
	}
	
	public void dibujarBird(Entorno entorno){
		entorno.dibujarImagen(imagenBird, bird.x, bird.y, 0.0, 0.18);
	}
	
	public void elevar(){
		bird.y--;
	}
	
	public void caer(){
		bird.y++;
	}
	
	public void instanciarBala(){
			this.bala=new Rectangle(bird.x+bird.width/2,bird.y,10,10);
	}
	
	public void dibujarBala(Entorno entorno){ 
		entorno.dibujarImagen(imagenBala, bala.x, bala.y, 0.0, 0.8);
	}
	
	public void moverBala(){
		if(bala.x-bala.width/2 < 800) 
			bala.x+=2;
	}
	
	public void controlarBala() {
		if(bala.x-bala.width/2 < 800)
			estaDisparando=true;
		else
			estaDisparando=false;
	}
	
	public void controlarDisparo(Alimentos alimentos) {
		if(balaColisionaConHamburguesa(alimentos)) {
			sonidoBala.stop();
			sonidoBala.setFramePosition(0); // resetea la posicion del sonido para que se vuelva a usar
			estaDisparando=false;	
		}
		//ESTA DISPARANDO		
		else if(bala.x-bala.width/2 < 800) {
			sonidoBala.start(); 
			estaDisparando=true;
		}
		//SI LLEGO AL FINAL DE LA PANTALLA
		else {
			sonidoBala.stop();
			sonidoBala.setFramePosition(0); 
			estaDisparando=false;	
		}
	}
	
	private boolean balaColisionaConHamburguesa(Alimentos alimentos) {
		for(Alimento alimento: alimentos.getAlimentos()) {
			if(!alimento.getEsVegetal() && colisionaCon(alimento))
				return true;
		}
		return false;
	}
	
	public void colisionConHamburguesa(Alimentos alimentos) {
		for(Alimento alimento: alimentos.getAlimentos()) {
			if(!alimento.getEsVegetal() && colisionaCon(alimento)) {
				alimento.setEsVegetal(true);
				alimentos.setHamburguesasTransformadas(alimentos.getHamburguesasTransformadas()+1);
			}
		}
	}
	
	private boolean colisionaCon(Alimento alimento){
		if( ((bala.x-bala.width/2 >= alimento.getX()-alimento.getWidth()/2 && bala.x-bala.width/2 <= alimento.getX()+alimento.getWidth()/2) 
		   ||(bala.x+bala.width/2 >= alimento.getX()-alimento.getWidth()/2 && bala.x+bala.width/2 <= alimento.getX()+alimento.getWidth()/2)) &&
			((bala.y-bala.height/2 >= alimento.getY()-alimento.getWidth()/2 && bala.y-bala.height/2 <= alimento.getY()+alimento.getWidth()/2)
		   ||(bala.y+bala.height/2 >= alimento.getY()-alimento.getWidth()/2 && bala.y+bala.height/2 <= alimento.getY()+alimento.getWidth()/2)) )
				return true;
		return false;
	}

	}

