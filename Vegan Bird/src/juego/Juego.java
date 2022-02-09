package juego;

import java.awt.Image;

import javax.sound.sampled.Clip;

import entorno.*;

public class Juego extends InterfaceJuego
{
	//El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	//Variables y métodos propios de cada grupo
	public Bird bird;
	public Obstaculos obstaculos;
	public Alimentos alimentos;
	public boolean pressTECLA_ARRIBA;	
	public boolean pressTECLA_ESPACIO;
	public boolean pressTECLA_ENTER;
	public boolean reinicio;
	public int velocidad;
	public int nivel;
	public boolean gano;
	public boolean flagNivel2;
	public boolean flagNivel3;
	public boolean perdio;
	private Image menu;
	private Image finPerdiste;
	private Image finGano;
	private Clip sonidoFondo;
	private Clip sonidoWin;
	
	Juego()
	{
		//Inicializa el objeto entorno
		this.entorno = new Entorno(this, "VeganBird - Grupo: Helguero - Krujoski - Rages",800,600);
		
		//Inicializar lo que haga falta para el juego
		this.bird = new Bird();
		this.obstaculos = new Obstaculos();
		this.alimentos = new Alimentos();
		this.pressTECLA_ARRIBA = false;
		this.pressTECLA_ESPACIO = false;
		this.pressTECLA_ENTER = false;
		this.reinicio = false;
		this.velocidad = 1;
		this.nivel = 1;
		this.gano = false;
		this.menu = Herramientas.cargarImagen("Menu.gif");
		this.finPerdiste = Herramientas.cargarImagen("FinPerder.png");
		this.finGano = Herramientas.cargarImagen("FinGanar.png");
		this.sonidoFondo = Herramientas.cargarSonido("SonidoFondo.wav");
		this.sonidoWin = Herramientas.cargarSonido("Win.wav");
		this.flagNivel2 = false;
		this.flagNivel3 = false;
		this.perdio = false;
		//Inicia el juego!
		this.entorno.iniciar();
	}

	public void tick()
	{
		//Procesamiento de un instante de tiempo
		if(!gano) {
			if(entorno.sePresiono(entorno.TECLA_ENTER))
				pressTECLA_ENTER=true;
			//PANTALLA DE JUEGO
			if(pressTECLA_ENTER) {
				jugar();
				
			}
			//PANTALLA PRINCIPAL
			else{
				sonidoFondo.start();
				entorno.dibujarImagen(menu, 400, 300, 0, 0.5);
			}
			//VUELVE A LA PANTALLA DE JUEGO SI LO REINICIA
			if(reinicio) {
				sonidoFondo.start();
				bird = new Bird();
				obstaculos = new Obstaculos();
				alimentos = new Alimentos();
				pressTECLA_ARRIBA = false;
				pressTECLA_ESPACIO = false;
				reinicio = false;
				velocidad = 1;
				nivel = 1;
				gano = false;
				flagNivel2 = false;
				flagNivel3 = false;
				perdio = false;
				jugar();
			}
		}
		//PANTALLA Y SONIDO DE GANADOR
		else{
			sonidoFondo.stop(); //para el sonido de fondo y lo reinicia
			sonidoFondo.setFramePosition(0);
			sonidoWin.start();
			dibujar();
			entorno.dibujarImagen(finGano, 400, 300, 0, 0.5);
			mostrarPuntaje();
			if(entorno.sePresiono((entorno.TECLA_INICIO))) {
				sonidoWin.stop();
				sonidoWin.setFramePosition(0);
				gano = false;
				reinicio = true;
			}
		}
	}

	
	public void jugar() {
		dibujar();
		if(pressTECLA_ENTER && !perdio) {
		sonidoFondo.start();
		sonidoFondo.loop(1);
		}
		if(entorno.sePresiono(entorno.TECLA_ARRIBA))
			pressTECLA_ARRIBA = true;
		if(pressTECLA_ARRIBA && !obstaculos.colisionaCon(bird)){
			moverObstaculos();
			//MOVIMIENTO DEL PAJARO
			if(entorno.estaPresionada(entorno.TECLA_ARRIBA))
				bird.elevar();
			else
				bird.caer();
			//DISPARO
			if(entorno.sePresiono(entorno.TECLA_ESPACIO) && !bird.getEstaDisparando()){
				bird.instanciarBala();
				pressTECLA_ESPACIO = true;
				bird.setEstaDisparando(true);
			}
			if(pressTECLA_ESPACIO && bird.getEstaDisparando()){
				bird.dibujarBala(entorno);
				bird.moverBala();
				bird.controlarDisparo(alimentos);
				bird.colisionConHamburguesa(alimentos);
			}
			mostrarPuntajeEnJuego();
		}
		//SI PIERDE
		if(obstaculos.colisionaCon(bird)) {
			perdio=true;
			obstaculos.sonidoColision(bird);
			sonidoFondo.stop();
			sonidoFondo.setFramePosition(0);
			entorno.dibujarImagen(finPerdiste, 400, 300, 0, 0.5);
			mostrarPuntaje();
			if(entorno.sePresiono((entorno.TECLA_INICIO))) {
				reinicio = true;
			}
		}
		if(alimentos.puntaje() > 150) {
			gano=true;
		}
	}
	
	void dibujar(){
		obstaculos.dibujarObstaculos(entorno);
		bird.dibujarBird(entorno);	
		alimentos.dibujarYContar(entorno,bird);
	}
	
	void mostrarPuntaje() {
		entorno.escribirTexto(Integer.toString(alimentos.getVegetalesConsumidos()), 445, 222);
		entorno.escribirTexto(Integer.toString(alimentos.getHamburguesasConsumidas()), 480, 275);
		entorno.escribirTexto(Integer.toString(alimentos.getHamburguesasTransformadas()), 505, 330);
		entorno.escribirTexto(Integer.toString(alimentos.puntaje()), 375, 383);
	}
	
	void moverObstaculos(){
		nivelActual();
		if(nivel==2)
			this.velocidad = 2;
		else if(nivel==3)
			this.velocidad = 3;
		obstaculos.mover(velocidad);
		alimentos.mover(velocidad);
		alimentos.controlarDistancia();
	}
	
	void nivelActual() {
		if(alimentos.puntaje() > 50 && alimentos.puntaje() < 100 && !flagNivel2) {
			this.nivel = 2;
			this.flagNivel2 = true;
		}
		else if(alimentos.puntaje() > 100 && !flagNivel3) {
			this.nivel = 3;
			this.flagNivel3 = true;
		}
	}
	
	//ESQUINA IZQUIERDA
	void mostrarPuntajeEnJuego() {
		entorno.escribirTexto("Nivel "+ this.nivel,600, 20);
		entorno.escribirTexto("Hamburguesas transformadas: "+alimentos.getHamburguesasTransformadas(), 600, 40);
		entorno.escribirTexto("Vegetales consumidos: "+alimentos.getVegetalesConsumidos(), 600, 60);
		entorno.escribirTexto("Hamburguesas consumidas: "+alimentos.getHamburguesasConsumidas(), 600, 80);
		entorno.escribirTexto("Puntaje: "+alimentos.puntaje(),600,100);
	}
		
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Juego juego = new Juego();
	}
}
