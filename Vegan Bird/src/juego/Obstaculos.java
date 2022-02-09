package juego;
import java.awt.Image;
import java.awt.Rectangle;

import javax.sound.sampled.Clip;

import entorno.Entorno;
import entorno.Herramientas;

public class Obstaculos {
	
	private Rectangle[] obstaculos;
	private Image Fondo;
	private Image Tubo1y3;
	private Image Tubo2;
	private Image Tubo4y6;
	private Image Tubo5;
	private Image Tubo7;
	private Clip sonidoColision;
	
	public Obstaculos(){
		this.obstaculos = new Rectangle[8];
		obstaculos[0] = new Rectangle (400, 40, 100, 300);  //2
		obstaculos[1] = new Rectangle (400, 468, 100, 150);  //5 
		obstaculos[2] = new Rectangle (140, 40, 100, 190);  //1
		obstaculos[3] = new Rectangle (140, 433, 100, 220); //4 
		obstaculos[4] = new Rectangle (650, 40, 100, 190); //3
		obstaculos[5] = new Rectangle (650, 433, 100, 220);  //6  
		obstaculos[6] = new Rectangle (900, 468, 100, 150);  //8 
		obstaculos[7] = new Rectangle (900, 40, 100, 300);  //7
		
		Fondo = Herramientas.cargarImagen("Fondo.png"); 
		Tubo1y3 = Herramientas.cargarImagen("Tubo1y3.png"); 
		Tubo2 = Herramientas.cargarImagen("Tubo2.png"); 
		Tubo4y6 = Herramientas.cargarImagen("Tubo4y6.png");	
		Tubo5 = Herramientas.cargarImagen("Tubo5.png");
		Tubo7 = Herramientas.cargarImagen("Tubo7.png");
		sonidoColision = Herramientas.cargarSonido("Colision.wav");
	}
	
	public Rectangle[] getObstaculos() {
		return obstaculos;
	}

	
	public void dibujarObstaculos(Entorno entorno) {
		entorno.dibujarImagen(Fondo, 400, 300, 0, 0.5); //Fondo
		entorno.dibujarImagen(Tubo1y3, obstaculos[2].x, obstaculos[2].y, 0.0, 0.5); //Tubo 1
		entorno.dibujarImagen(Tubo2, obstaculos[0].x, obstaculos[0].y, 0.0, 0.5);  // Tubo 2
		entorno.dibujarImagen(Tubo1y3, obstaculos[4].x, obstaculos[4].y, 0.0, 0.5); // Tubo 3
		entorno.dibujarImagen(Tubo4y6, obstaculos[3].x, obstaculos[3].y, 0.0, 0.5); // Tubo 4
		entorno.dibujarImagen(Tubo5, obstaculos[1].x, obstaculos[1].y, 0.0, 0.5); // Tubo 5
		entorno.dibujarImagen(Tubo4y6, obstaculos[5].x, obstaculos[5].y, 0.0, 0.5); // Tubo 6
		entorno.dibujarImagen(Tubo7, obstaculos[7].x, obstaculos[7].y, 0.0, 0.5); // Tubo 7
		entorno.dibujarImagen(Tubo5, obstaculos[6].x, obstaculos[6].y, 0.0, 0.5); //Tubo 8
	}
	
	public void mover(int velocidad){
		for (Rectangle obstaculo: obstaculos) {
			 obstaculo.x-=velocidad;
			 if (obstaculo.x<-50)
				 obstaculo.x=950;
		}
	}
	
	public void reubicar() {
		for (Rectangle obstaculo: obstaculos) {
			if (obstaculo.x==-50)
				obstaculo.x=950;
		}
	}
	
	public boolean colisionaCon(Bird b){
		for(Rectangle obstaculo: obstaculos){
			if( ((b.getBird().x-b.getBird().width/2 >= obstaculo.x-obstaculo.width/2 && b.getBird().x-b.getBird().width/2 <= obstaculo.x+obstaculo.width/2) 
			   ||(b.getBird().x+b.getBird().width/2 >= obstaculo.x-obstaculo.width/2 && b.getBird().x+b.getBird().width/2 <= obstaculo.x+obstaculo.width/2)) &&
				((b.getBird().y-b.getBird().height/2 >= obstaculo.y-obstaculo.height/2 && b.getBird().y-b.getBird().height/2 <= obstaculo.y+obstaculo.height/2)
			   ||(b.getBird().y+b.getBird().height/2 >= obstaculo.y-obstaculo.height/2 && b.getBird().y+b.getBird().height/2 <= obstaculo.y+obstaculo.height/2)) )
					return true;
		}
		return false;
	}
	
	public void sonidoColision(Bird b) {
		if (colisionaCon(b)) {
			sonidoColision.start();		
		}
	}
}

