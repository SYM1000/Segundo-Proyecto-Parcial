import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class analizador {
	
	//Metodo para conocer si la exprecion es valida o no
	public static Boolean esValida(String cadena) {
		
		String[] arreglo = cadena.split("");
		HashSet<String> oper = new HashSet<String>();
		HashSet<String> numeros = new HashSet<String>();
		Stack<String> pila = new Stack<String>();
		añadirSimbolos(oper, numeros);
		
		for (int i = 1; i < arreglo.length-1; i++) {
			
			String simbolo = arreglo[i];
			
			//EL simbolo es un operador
			if(oper.contains(simbolo)) {
				
				boolean izq = numeros.contains(arreglo[i-1]) || arreglo[i-1].equals(")");
				boolean der = numeros.contains(arreglo[i+1]) || arreglo[i+1].equals("(");
				
				if(izq && der == false) {
					return false;
					}
				
			//EL simbolo es un (
			}else if(simbolo.equals("(")){
				
				boolean izq = oper.contains(arreglo[i-1]) || arreglo[i-1].equals("(");
				boolean der = numeros.contains(arreglo[i+1]) || arreglo[i+1].equals("(");
				
				if(pila.isEmpty() || pila.peek().equals("(")) {
					pila.add("(");
				}else {
					return false;
				}
				
				
				if(izq && der == false) {
					return false;
					}
			
			//EL simbolo es un )
			}else if(simbolo.equals(")")){
				
				boolean izq = numeros.contains(arreglo[i-1]) || arreglo[i-1].equals(")");
				boolean der = oper.contains(arreglo[i+1]) || arreglo[i+1].equals(")");
				
				if(pila.isEmpty() ) {
					return false;
				}else if(pila.peek().equals("(")) {
					pila.pop();
				}
				
				
				if(izq && der == false) {
					return false;
					}
				
				
			//EL simbolo es numero
			}else if(numeros.contains(simbolo)){
				
				boolean izq = numeros.contains(arreglo[i-1]) || arreglo[i-1].equals("(") || oper.contains(arreglo[i-1]);
				boolean der = numeros.contains(arreglo[i+1]) || arreglo[i+1].equals(")") || oper.contains(arreglo[i+1]);
				
				if(izq && der == false) {
					return false;
					}
			
			//El simbolo no es valido
			}else {return false;}
			
			
			
		}
		
		if(oper.contains(arreglo[0]) || arreglo[0].equals(")")) {return false;}
		if(oper.contains(arreglo[arreglo.length-1]) || arreglo[arreglo.length-1].equals("(")) {return false;}
		if(!pila.isEmpty()){return false;}
		
		return true;
	} 
	
	//Añadir simbolo a un HashSet
	private static void añadirSimbolos(HashSet<String> oper, HashSet<String> numeros) {
		oper.add("+");
		oper.add("-");
		oper.add("*");
		for (int i = 0; i < 10; i++) {
			numeros.add( Integer.toString(i) );
		}
	}

	//Convertir de infija a postfija     ----> Meto a la pila los operadores
	public static String convertiraPostfija(String infija) {
			
			String postfija = "";
			String[] arreglo = infija.split("");
			Stack<String> pila = new Stack<String>();
			boolean volver = true;
			
			for (int i = 0; i < arreglo.length; i++) {
				
				try {
					postfija += Integer.parseInt(arreglo[i]) + "";
					continue;
				}catch(NumberFormatException e){ //------> Paso 3
					volver = true;
					while(volver == true) {
						
					if(pila.isEmpty()) {
						pila.add(arreglo[i]);
						volver = false;
						continue;
					}else { //la pila no está vacia y se compara por prioridades

						if(arreglo[i].equals("(")) { //Operador Leido
							pila.add(arreglo[i]);
							volver = false;
							continue;
							/*
							if(pila.lastElement().equals("(")) {
								postfija += pila.pop() + " ";
								volver = true;
								
							}else if(pila.lastElement().equals("+") || pila.lastElement().equals("-")) {
								postfija += pila.pop() + " ";
								volver = true;
								
							}else if(pila.lastElement().equals("*") || pila.lastElement().equals("/")) {
								postfija += pila.pop() + " ";
								volver = true;
								
							}else {
								//"^"
								postfija += pila.pop() + " ";
								volver = true;
							}
							*/
							
						}else if(arreglo[i].equals("+") || arreglo[i].equals("-")) {//Operador Leido
							
							if(pila.lastElement().equals("(")) {
								pila.add(arreglo[i]);
								volver = false;
								continue;
								
							}else if(pila.lastElement().equals("+") || pila.lastElement().equals("-")) {
								postfija += pila.pop() + "";
								volver = true;
								
							}else if(pila.lastElement().equals("*") || pila.lastElement().equals("/")) {
								postfija += pila.pop() + "";
								volver = true;
								
							}else {
								//"^"
								postfija += pila.pop() + "";
								volver = true;
							}
							
						}else if(arreglo[i].equals("*") || arreglo[i].equals("/")) { //Operador Leido
							if(pila.lastElement().equals("(")) {
								pila.add(arreglo[i]);
								volver = false;
								continue;
								
							}else if(pila.lastElement().equals("+") || pila.lastElement().equals("-")) {
								pila.add(arreglo[i]);
								volver = false;
								continue;
								
							}else if(pila.lastElement().equals("*") || pila.lastElement().equals("/")) {
								postfija += pila.pop() + " ";
								volver = true;
								
							}else {
								//"^"
								postfija += pila.pop() + "";
								volver = true;
							}
							
						}else if(arreglo[i].equals("^")) { //Operador Leido
							if(pila.lastElement().equals("(")) {
								pila.add(arreglo[i]);
								volver = false;
								continue;
								
							}else if(pila.lastElement().equals("+") || pila.lastElement().equals("-")) {
								pila.add(arreglo[i]);
								volver = false;
								continue;
								
							}else if(pila.lastElement().equals("*") || pila.lastElement().equals("/")) {
								pila.add(arreglo[i]);
								volver = false;
								continue;
								
							}else {
								//"^"
								postfija += pila.pop() + "";
								volver = true;
							}
							
							
						}else if(arreglo[i].equals(")")) { //Si es parentesis derecho
							//postfija+= pila.pop() + " ";

							while(!pila.lastElement().equals("(")){
								postfija+= pila.pop() + "";
							}
							pila.pop();
							volver= false;
							continue;
						}			
					}
				}//Fin del while
		
				}			
			}
			while(!pila.isEmpty()){
				postfija+= pila.pop() + "";
			}
			
			return postfija;
			
		}
	
	//Evaluacion de la expresión en postfija   ----> Meto a la pila los operandos
	public static double evaluarPostfija(String postfija) {
			String[] arreglo = postfija.split("");
			Stack<Integer> pila = new Stack<Integer>();
			String operador;
			int x = 0;
			int y = 0;
			int z = 0;
			double resultado;
			
			for (int i = 0; i < arreglo.length; i++) {
				
				try {
					pila.add(Integer.parseInt(arreglo[i]));
				}catch (NumberFormatException e) {
					operador = arreglo[i];
					x = (int) pila.pop();
					y = (int) pila.pop();
					
					//Evaluar la operacion
					if (operador.equals("+")) {
						z = y + x;
					}else if(operador.equals("-")) {
						z = y - x;
					}else if(operador.equals("*")) {
						z = y * x;
					}else if(operador.equals("/")) {
						z = y / x;
					}else if(operador.equals("^")) {
						z = (int) Math.pow(y, z);
					}
					
					pila.add(z);
				}
				continue;
			}
			resultado = pila.pop();
			return resultado;
		}
		
	//Genera el árbol de derivación y regresa la raiz del árbol
	public static Node getArbol(String cadena){
		String[] arr = convertiraPostfija(cadena).split("");
		Stack<Node> pila = new Stack<Node>();

		Node nodito;

		for(int i = 0; i<arr.length ; i++) {
			String val = arr[i];
			if( val.equals("0") ||  val.equals("1") || val.equals("2") ||  val.equals("3") || val.equals("4") ||  val.equals("5") || val.equals("6") ||  val.equals("7") || val.equals("8") ||  val.equals("9") ) {
				nodito = new Node<String>(val);
				pila.push(nodito);
				
			}else {
				nodito = new Node<String>(val);			
				nodito.right = pila.pop();
				nodito.left = pila.pop();
				pila.push(nodito);
			}

		}
		
		return pila.pop();
	}
	
	//Metodo que ejecuta el programa
	public static void ejecutarPrograma(String cadena) {
		HashMap<Boolean, String> res = new HashMap<Boolean, String>();
		res.put(true, "válida");
		res.put(false, "inválida");
		
		boolean isValid = esValida(cadena);
		
		
		System.out.println("La cadena: " + cadena +" es " + res.get(isValid));
		
		if(isValid) {
			String postfija = convertiraPostfija(cadena);
			System.out.println("El resultado es: " + evaluarPostfija(postfija));
			Node nodito = getArbol(postfija);
			
			System.out.println();
			System.out.println("Este es el arbol de derivación:");
			BTreePrinter.printNode(nodito);
		}else {
			System.out.println();
			System.out.println("* Ingresa una cadena válida para obtener el resultado y el árbol de derivación *");
		}
		
		
	}


	public static void main(String[] args) {
		
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * INGRESA LA CADENA AQUÍ ABAJO Y MIRA LA MAGIA OCURRIR  *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		String cadena = "3+9"; // <-------------- INGRESA LA CADENA AQUÍ!
		
		ejecutarPrograma(cadena);
		
	}

}


