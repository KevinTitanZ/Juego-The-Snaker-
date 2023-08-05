package juego;


public class Recorrido {
	
	// Recorrido de un árbol binario en preorden
	public static void preorden(Nodo r) {      //            raiz, izquierda,derecha
		if (r != null) {
			r.visitar();//raiz
			preorden(r.subarbolIzdo());
			preorden(r.subarbolDcho());
		}
	}

	// Recorrido de un árbol binario en inorden
	public static void inorden(Nodo r) { //                 izquierda, raiz, derecha
		if (r != null) {
			inorden(r.subarbolIzdo());
			r.visitar();
			inorden(r.subarbolDcho());
		}
	}

	// Recorrido de un árbol binario en postorden
	public static void postorden(Nodo r) {  //             izquierda, derecha, raiz
		if (r != null) {
			postorden(r.subarbolIzdo());
			postorden(r.subarbolDcho());
			r.visitar();
		}
	}
}
