package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Alimentos {

	private Alimento[] alimentos;
	private Random r;
	private int vegetalesConsumidos;
	private int hamburguesasTransformadas;
	private int hamburguesasConsumidas;
	private Image Hamburguesa;
	private Image Zanahoria;
	
	Alimentos(){
		this.alimentos = new Alimento[4];
		alimentos[0] = new Alimento(200,300,true);
		alimentos[1] = new Alimento(400,250,false);
		alimentos[2] = new Alimento(600,300,true);
		alimentos[3] = new Alimento(800,250,false);
		this.r = new Random();
		this.vegetalesConsumidos = 0;
		this.hamburguesasTransformadas = 0;
		this.hamburguesasConsumidas = 0;
		Hamburguesa = Herramientas.cargarImagen("Hamburguesa.png");	
		Zanahoria = Herramientas.cargarImagen("Zanahoria.png");
	}
	
	public Alimento[] getAlimentos() {
		return alimentos;
	}

	public int getVegetalesConsumidos() {
		return vegetalesConsumidos;
	}

	public void setVegetalesConsumidos(int vegetalesConsumidos) {
		if(vegetalesConsumidos>=0)
			this.vegetalesConsumidos = vegetalesConsumidos;
		else
			throw new RuntimeException("La cantidad de vegetales no puede ser negativa");
	}

	public int getHamburguesasTransformadas() {
		return hamburguesasTransformadas;
	}

	public void setHamburguesasTransformadas(int hamburguesasTransformadas) {
		if(hamburguesasConsumidas>=0)
			this.hamburguesasTransformadas = hamburguesasTransformadas;
		else
			throw new RuntimeException("La cantidad de hamburguesas transformadas no puede ser negativa");
	}

	public int getHamburguesasConsumidas() {
		return hamburguesasConsumidas;
	}

	public void setHamburguesasConsumidas(int hamburguesasConsumidas) {
		if(hamburguesasConsumidas>=0)
			this.hamburguesasConsumidas = hamburguesasConsumidas;
		else
			throw new RuntimeException("La cantidad de hamburguesas consumidas no puede ser negativa");
	}

	public int puntaje() {
		return vegetalesConsumidos*5+ hamburguesasTransformadas*3 - hamburguesasConsumidas*3;
	}
	
	public void dibujarYContar(Entorno entorno, Bird bird){
		for (Alimento alimento: alimentos){
			if(colisionan(bird,alimento) && alimento.getEsVegetal() && !alimento.getFueComido()) {
				alimento.setFueComido(true);
				vegetalesConsumidos++;
			}
			else if(colisionan(bird,alimento) && !alimento.getEsVegetal() && !alimento.getFueComido()) {
				alimento.setFueComido(true);
				hamburguesasConsumidas++;
			}
			if(!alimento.getFueComido()) {
				if(alimento.getEsVegetal())
					entorno.dibujarImagen(Zanahoria, alimento.getX(), alimento.getY(), 0.0, 0.20);
					//entorno.dibujarRectangulo(alimento.getX(), alimento.getY(), alimento.getWidth(), alimento.getHeight(), 0, Color.blue);
				else
					entorno.dibujarImagen(Hamburguesa, alimento.getX(), alimento.getY(), 0.0, 0.15);
					//entorno.dibujarRectangulo(alimento.getX(), alimento.getY(), alimento.getWidth(), alimento.getHeight(), 0, Color.yellow);
			}
		}
	}
	
	public void mover(int velocidad){
		for (Alimento alimento: alimentos)
			alimento.setX(alimento.getX()-(velocidad+1));
	}
	
	public void controlarDistancia() {
		for (Alimento alimento: alimentos) {
			if (alimento.getX()+alimento.getWidth()/2<0)
				reubicarAleatoriamente(alimento);
		}
	}
	
	private void reubicarAleatoriamente(Alimento alimento){
		int y = r.nextInt(131)+180;
		boolean esVegetal;

		if(y%2 == 0)
			esVegetal=true;
		else
			esVegetal=false;
		
		alimento.setX(850);
		alimento.setY(y);
		alimento.setEsVegetal(esVegetal);
		alimento.setFueComido(false);
	}
	
	private boolean colisionan(Bird b, Alimento alimento){
		if( ((alimento.getX()-alimento.getWidth()/2 >= b.getBird().x-b.getBird().width/2 && alimento.getX()-alimento.getWidth()/2 <= b.getBird().x+b.getBird().width/2) 
		   ||(alimento.getX()+alimento.getWidth()/2 >= b.getBird().x-b.getBird().width/2 && alimento.getX()+alimento.getWidth()/2 <= b.getBird().x+b.getBird().width/2)) &&
			((alimento.getY()-alimento.getWidth()/2>=b.getBird().y-b.getBird().height/2 && alimento.getY()-alimento.getHeight()/2 <= b.getBird().y+b.getBird().height/2)
		   ||(alimento.getY()+alimento.getWidth()/2>=b.getBird().y-b.getBird().height/2 && alimento.getY()+alimento.getHeight()/2 <= b.getBird().y+b.getBird().height/2)) )
				return true;
		return false;
	}
	
}
