package juego;

public class Nodo {
    protected String playerName;
	protected Object dato;
	protected Nodo izdo;
	protected Nodo dcho;
	public int score;

	public Nodo(Object valor) {
		dato = valor;
		izdo = dcho = null;
	}
	 public Nodo(String playerName, int score) {
	        this.playerName = playerName;
	        this.score = score;
	        izdo = dcho = null;
	    }
	
	public Nodo(Nodo ramaIzdo, Object valor, Nodo ramaDcho) {
		dato = valor;
		izdo = ramaIzdo;
		dcho = ramaDcho;
	}
	 public int getScore() {
	        return score;
	    }
	// operaciones de acceso
	public Object valorNodo() {
		return dato;
	}

	public Nodo subarbolIzdo() {
		return izdo;
	}

	public Nodo subarbolDcho() {
		return dcho;
	}

	public void nuevoValor(Object d) {
		dato = d;
		
	}

	public void ramaIzdo(Nodo n) {
		izdo = n;
	}

	public void ramaDcho(Nodo n) {
		dcho = n;
	}

	@Override
	public String toString() {
		return "Nodo [dato=" + dato + ", izdo=" + izdo + ", dcho=" + dcho + "]";
	}

	void visitar() {
		System.out.print(dato + " ");
	}
	



	  public void inOrdenRecursivo() {
	        if (izdo != null) {
	            izdo.inOrdenRecursivo();
	        }
	        System.out.println(score);
	        if (dcho != null) {
	            dcho.inOrdenRecursivo();
	        }
	    }

	    // Método para recorrer el árbol en preorden
	    public void preOrdenRecursivo() {
	        System.out.println(score);
	        if (izdo != null) {
	            izdo.preOrdenRecursivo();
	        }
	        if (dcho != null) {
	            dcho.preOrdenRecursivo();
	        }
	    }

	    // Método para recorrer el árbol en postorden
	    public void postOrdenRecursivo() {
	        if (izdo != null) {
	            izdo.postOrdenRecursivo();
	        }
	        if (dcho != null) {
	            dcho.postOrdenRecursivo();
	        }
	        System.out.println(score);
	    }
}



