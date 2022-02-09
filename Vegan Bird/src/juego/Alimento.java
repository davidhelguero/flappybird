package juego;

public class Alimento {
	private double x;
	private double y;
	private double width;
	private double height;
	private boolean fueComido;
	private boolean esVegetal;
	
	Alimento(double x, double y, boolean esVegetal){
		this.x=x;
		this.y=y;
		this.width=15;
		this.height=15;
		this.fueComido=false;
		this.esVegetal=esVegetal;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		if(width>0)
			this.width = width;
		else
			throw new RuntimeException("El alto no puede ser negativo");
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		if(width>0)
			this.height = height;
		else
			throw new RuntimeException("El ancho no puede ser negativo");
	}

	public boolean getFueComido() {
		return fueComido;
	}

	public void setFueComido(boolean fueComido) {
		this.fueComido = fueComido;
	}

	public boolean getEsVegetal() {
		return esVegetal;
	}

	public void setEsVegetal(boolean esVegetal) {
		this.esVegetal = esVegetal;
	}
	
}
