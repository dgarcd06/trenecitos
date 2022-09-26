import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Trenecitos {
	static final int MAXFILAS = 100;
	static final int MAXCOLUMNAS = 100;
	static int num_filas;
	static int num_columnas;
	private static class Tren implements Comparable<Tren>{

		private char direccion;
		private int longitud;
		private int pos_x;
		private int pos_y;
		private int num_tren;
		private int tipo;
		public int id;
		private ArrayList<Integer[]> posiciones = new ArrayList<Integer[]>();
		//posicion en x e y del ultimo vagon
		private int vagonFinal[] = new int[2];
		//colision
		private Colision colision;
		//constructor 1
		public Tren() {
			
		}
		//constructor 2
		public Tren(char direccion,int longitud,int pos_x,int pos_y) {
			this.direccion = direccion;
			this.longitud = longitud;
			this.pos_x = pos_x;
			this.pos_y = pos_y;
		}
		//getters y setters
		public int getLongitud() {
			return this.longitud;
		}
		public void setLongitud(int longitud) {
			this.longitud = longitud;
		}
		
		public char getDireccion() {
			return this.direccion;
		}
		public void setDireccion(char direccion) {
			this.direccion = direccion;
		}
		
		public int getPosX() {
			return this.pos_x;
		}
		public void setPos_x(int pos_x) {
			this.pos_x = pos_x;
		}
		
		public int getPosY() {
			return this.pos_y;
		}
		public void setPos_y(int pos_y) {
			this.pos_y = pos_y;
		}
		
		public int[] getVagonFinal() {
			return this.vagonFinal;
		}
		public void setVagonFinal() {
			switch(direccion) {
			case 'A':
				this.vagonFinal[0] = this.pos_x;
				this.vagonFinal[1] = this.pos_y - this.longitud;
			break;
			case 'B':
				this.vagonFinal[0] = this.pos_x;
				this.vagonFinal[1] = this.pos_y + this.longitud;
			break;
			case 'I':
				this.vagonFinal[0] = this.pos_x + this.longitud;
				this.vagonFinal[1] = this.pos_y;
			break;
			case 'D':
				this.vagonFinal[0] = this.pos_x - this.longitud;
				this.vagonFinal[1] = this.pos_y;
			break;
			}
		}

		public int getNum_tren() {
			return num_tren;
		}
		public void setNum_tren(int num_tren) {
			this.num_tren = num_tren;
		}
		public int getTipo() {
			return this.tipo;
		}
		public void setTipo(char direccion) {
			switch(direccion) {
			case 'A':
				this.tipo = 1;
			break;
			case 'B':
				this.tipo = 0;
			break;
			case 'I':
				this.tipo = 2;
			break;
			case 'D':
				this.tipo = 3;
			break;
			}
		}
		
		
		public ArrayList<Integer[]> getPosiciones() {
			return posiciones;
		}
		public void setPosiciones() {
			Integer[] posicion = new Integer[2];
				for(int i=0;i<longitud;i++) {
					switch(this.getDireccion()) {
					case 'A':
						posicion[0] = this.getPosX();
						posicion[1] = this.getPosY() - i;
						posiciones.add(posicion);
						break;
					case 'B':
						posicion[0] = this.getPosX();
						posicion[1] = this.getPosY() + 1;
						posiciones.add(posicion);
						break;
					case 'I':
						posicion[0] = this.getPosX() + 1;
						posicion[1] = this.getPosY();
						posiciones.add(posicion);;
					case 'D':
						posicion[0] = this.getPosX() - 1;
						posicion[1] = this.getPosY();
						posiciones.add(posicion);
						break;
					}
					
				}
		}
		
		public Colision getColision() {
			return colision;
		}
		public void setColision(int x,int y) {
			this.colision = new Colision(x,y,'X');
		}
		

		//metodo para comparar trenes segun su tipo
		public int compareTo(Tren tren) {
			if(this.getTipo() > tren.getTipo()) {
				return -1;
			}else if(this.getTipo() < tren.getTipo()) {
				return 1;
			}else {
				return 0;
			}
		}
		/**
		 * Metodo para que el tren avance una casilla en la direccion indicada en el input
		 * @param direccion Direccion hacia la que avanza el tren
		 */
		public void avanzar(char direccion) {
			switch(direccion) {
			case 'A':
				if(this.pos_y < 29) {
					this.pos_y++;
					this.vagonFinal[1]++;
				}else {
					this.longitud--;
					this.vagonFinal[1]++;
				}
				
			break;
			case 'B':
				if(this.pos_y > 0) {
					this.pos_y--;
					this.vagonFinal[1]--;
				}else {
					this.longitud--;
					this.vagonFinal[1]--;
				}
				
			break;
			case 'I':
				if(this.pos_x > 0) {
					this.pos_x--;
					this.vagonFinal[0]--;
				}else {
					this.longitud--;
					this.vagonFinal[0]--;
				}
			break;
			case 'D':
				if(this.pos_x < 29) {
					this.pos_x++;
					this.vagonFinal[0]++;
				}else {
					this.longitud--;
					this.vagonFinal[0]++;
				}
			break;
			}
		}

		public void avanceColision(char direccion) {
			switch(direccion) {
				case 'A':
						this.longitud--;
						this.vagonFinal[1]++;
					break;
				case 'B':
						this.longitud--;
						this.vagonFinal[1]--;
					break;
				case 'I':
						this.longitud--;
						this.vagonFinal[0]--;
					break;
				case 'D':
						this.longitud--;
						this.vagonFinal[0]++;
					break;
			}
		}
		/**
		 * Metodo booleano que comprueba si un tren que viaja perpendicular a otro esta entre su posicion
		 * @param tren Tren que viaja perpendicular 
		 * @return 
		 */
		public boolean betweenPosTren(Tren tren) {
			boolean correcto = false;
			switch(tren.getDireccion()) {
			case 'A':
					if((this.getDireccion() == 'D' || this.getDireccion() == 'I') && this.getPosY() <= tren.getPosY() && this.getPosY() >= tren.getVagonFinal()[1]) {
						correcto = true;
					}
				break;
			case 'B':
				if((this.getDireccion() == 'D' || this.getDireccion() == 'I') && this.getPosY() >= tren.getPosY() && this.getPosY() <= tren.getVagonFinal()[1]) {
					correcto = true;
				}
				break;
			case 'I':
				if((this.getDireccion() == 'B' || this.getDireccion() == 'A') && this.getPosX() >= tren.getPosX() && this.getPosX() <= tren.getVagonFinal()[0]) {
					correcto = true;
				}
				break;
			case 'D':
				if((this.getDireccion() == 'B' || this.getDireccion() == 'A') && this.getPosX() <= tren.getPosX() && this.getPosX() >= tren.getVagonFinal()[0]) {
					correcto = true;
				}
				break;
		}
			return correcto;
		}
		/**
		 * Metodo que comprueba si dos trenes coinciden al añadirlos
		 * @param trenes ArrayList que contiene los trenes ya añadidos
		 * @return Array con la posicion de la colision y la posicion en el array del tren colisionado
		 * TODO
		 */
		public int[] trenesCoinciden(ArrayList<Tren> trenes) {
			Integer[] posible_colision = new Integer[3];
			int aux[] = new int[3];
			boolean condicion = false;
			for(int i=0;i<trenes.size();i++) {
				for(int j=0;j<this.getPosiciones().size();j++) {
					for(int k=0;k<trenes.get(i).getPosiciones().size();k++) {
						if(this.id != trenes.get(i).id && this.getPosiciones().get(j) == trenes.get(i).getPosiciones().get(k)) {
							posible_colision[0] = this.getPosiciones().get(j)[0];
							posible_colision[1] = this.getPosiciones().get(j)[1];
							posible_colision[2] = i;
							this.setColision(posible_colision[0], posible_colision[1]);
							trenes.get(i).setColision(posible_colision[0], posible_colision[1]);
							condicion = true;
							break;
						}
					}
						if(condicion == true) {
							break;
						}
				}
		
			if(condicion == false) {
				aux[0] = -1;
				aux[1] = -1;
				aux[2] = -1;
				break;
			}else {
			aux[0] = posible_colision[0];
			aux[1] = posible_colision[1];
			aux[2] = posible_colision[2];
			break;
			}
			}
			return aux;
		}
	}
	
	private static class Colision{
		private int pos_y;
		private int pos_x;
		private char tipo;
		public Colision(int posx,int posy) {
			this.pos_x = posx;
			this.pos_y = posy;
		}
		public Colision(int posx,int posy,char tipo) {
			this.pos_x = posx;
			this.pos_y = posy;
			this.tipo = tipo;
		}
		/**
		 * @return the pos_y
		 */
		public int getPos_y() {
			return pos_y;
		}
		/**
		 * @param pos_y the pos_y to set
		 */
		public void setPos_y(int pos_y) {
			this.pos_y = pos_y;
		}
		/**
		 * @return the pos_x
		 */
		public int getPos_x() {
			return pos_x;
		}
		/**
		 * @param pos_x the pos_x to set
		 */
		public void setPos_x(int pos_x) {
			this.pos_x = pos_x;
		}
		public char getTipo() {
			return tipo;
		}
		public void setTipo(char tipo) {
			this.tipo = tipo;
		}
		
		
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		boolean entrada_correcta = false;
		Scanner scan = new Scanner(System.in);
		String lectura;
		ArrayList<Tren> trenes = new ArrayList<Tren>();
		ArrayList<Colision> colisiones = new ArrayList<Colision>();
		char dir;
		int num_trenes,posicionX,posicionY,tam;
		int error[] = {-1,-1,-1};
		//input
		while(scan.hasNext()) {
			num_columnas = scan.nextInt();
			num_filas = scan.nextInt();
			if(num_columnas > MAXCOLUMNAS || num_filas > MAXFILAS) {
				System.out.println("Conjunto de trenes incorrecto.");
				System.exit(0);
			}
			num_trenes = scan.nextInt();
			for(int i=0;i<num_trenes;i++) {
				if(scan.hasNext() == false) {
					System.out.println("Conjunto de trenes incorrecto.");
					System.exit(0);
				}
				lectura = scan.next();
				dir = lectura.charAt(0);
				if(dir != 'A' && dir != 'B' && dir != 'I' && dir != 'D' && dir != 'H') {
					System.out.println("Conjunto de trenes incorrecto.");
					System.exit(0);
				}
				if(dir == 'H') {
					posicionX = scan.nextInt();
					posicionY = scan.nextInt();
					colisiones.add(new Colision(posicionX,posicionY,'H'));
					
				}else {
					entrada_correcta = true;
					Tren tren = new Tren();
					tren.setNum_tren(i);
					tren.id = i;
					
					tren.setDireccion(dir);
					
					tam = scan.nextInt();
					tren.setLongitud(tam);
					//para las posiciones tendremos que comprobar si hay colision, tanto en el primer vagon como en el resto
					posicionX = scan.nextInt();
					tren.setPos_x(posicionX);
					posicionY = scan.nextInt();
					tren.setPos_y(posicionY);
					tren.setTipo(dir);
					tren.setVagonFinal();
					tren.setPosiciones();
					if(tren.pos_x >= num_columnas && tren.pos_y >= num_filas) {
						System.out.println("Conjunto de trenes incorrecto.");
						System.exit(0);
					}else {
						trenes.add(tren);
					}
					/**
					 * Aqui se comprueba si algun tren coincide
					 * Si entra en el if es que coincide alguno
					 * En ese caso crearemos la colision y haremos los cambios oportunos en los trenes
					 */
					if(entrada_correcta == true && trenes.size() > 1) {
						boolean comprobacion = false;
						int aux = 0;
						int[] coincidencia = tren.trenesCoinciden(trenes);
						for(int h=0;h<3;h++) {
							if(error[h] == coincidencia[h]) {
								aux++;
							}
							
						}
						if(aux == 3) {
							comprobacion = true;
						}
						if(comprobacion == false) {
							Colision col = new Colision(coincidencia[0],coincidencia[1],'X');
							colisiones.add(col);
							if(col.getPos_x() == trenes.get(coincidencia[2]).getPosX() && col.getPos_y() == trenes.get(coincidencia[2]).getPosY()) {
								switch(trenes.get(coincidencia[2]).getDireccion()) {
								case 'A':
									trenes.get(coincidencia[2]).setPos_y(tren.getPosY() - 1);
								break;
								case 'B':
									trenes.get(coincidencia[2]).setPos_y(tren.getPosY() + 1);
								break;
								case 'I':
									trenes.get(coincidencia[2]).setPos_x(tren.getPosX() + 1);
								break;
								case 'D':
									trenes.get(coincidencia[2]).setPos_x(tren.getPosX() - 1);
								break;
								}
									
							}else {
									nuevoTren(trenes,trenes.get(coincidencia[2]));
							}
							
							if(col.getPos_x() == tren.getPosX() && col.getPos_y() == tren.getPosY()) {
								switch(tren.getDireccion()) {
								case 'A':
									tren.setPos_y(tren.getPosY() - 1);
								break;
								case 'B':
									tren.setPos_y(tren.getPosY() + 1);
								break;
								case 'I':
									tren.setPos_x(tren.getPosX() + 1);
								break;
								case 'D':
									tren.setPos_x(tren.getPosX() - 1);
								break;
								}
							}else {
								nuevoTren(trenes,tren);
							}
							
						}
					}
					
				}
				
			}
			
			if(entrada_correcta == true) {
				Collections.sort(trenes, new Comparator<Tren>(){
					
					public int compare(Tren t1, Tren t2) {
						return new Integer(t1.getTipo()).compareTo(new Integer(t2.getTipo()));
					}
				});
				simulacion(trenes,colisiones);
				System.out.println(tableroFinal(colisiones));
				System.out.println();
			
			}else {
				System.out.println("Error:los datos introducidos no son correctos");
				System.exit(0);
			}
		}
		scan.close();
	}
	
	public static void simulacion(ArrayList<Tren> trenes,ArrayList<Colision> colisiones) {
		while(hayTrenes(trenes)) {
			for(int i=0;i<trenes.size();i++) {
				//hayColision comprueba si tiene una colision en frente. Esa colision YA EXISTE Y LE PERTENECE A UN TREN
				if(hayColision(trenes.get(i), trenes, colisiones)) {
					trenes.get(i).avanceColision(trenes.get(i).getDireccion());
					switch(trenes.get(i).getDireccion()) {
					case 'A':
						for(int h=0;h<colisiones.size();h++) {
							if(colisiones.get(h).getPos_x() == trenes.get(i).getPosX() && colisiones.get(h).getPos_y() == trenes.get(i).getPosY() + 1) {
								if(colisiones.get(h).getTipo() == 'H') {
									for(int k=0;k<trenes.size();k++) {
										if(trenes.get(k).getDireccion() == 'I' || trenes.get(k).getDireccion() == 'D') {
											Tren trenaux = trenes.get(k);
											trenes.remove(trenaux);		
											k = 0;
										}
									}
								}
							}
						}
						break; 
					case 'B':
						for(int h=0;h<colisiones.size();h++) {
							if(colisiones.get(h).getPos_x() == trenes.get(i).getPosX() && colisiones.get(h).getPos_y() == trenes.get(i).getPosY() - 1) {
								if(colisiones.get(h).getTipo() == 'H') {
									for(int k=0;k<trenes.size();k++) {
										if(trenes.get(k).getDireccion() == 'I' || trenes.get(k).getDireccion() == 'D') {
											Tren trenaux = trenes.get(k);
											trenes.remove(trenaux);		
											k=0;
											}
									}
								}
							}
						}
						break;
					case 'I':
						for(int h=0;h<colisiones.size();h++) {
							if(colisiones.get(h).getPos_x() == trenes.get(i).getPosX() - 1 && colisiones.get(h).getPos_y() == trenes.get(i).getPosY()) {
								if(colisiones.get(h).getTipo() == 'H') {
									for(int k=0;k<trenes.size();k++) {
										if(trenes.get(k).getDireccion() == 'I' || trenes.get(k).getDireccion() == 'D') {
											Tren trenaux = trenes.get(k);
											trenes.remove(trenaux);	
											k = 0;
										}
									}
								}
							}
						}
						break;
					case 'D':
						for(int h=0;h<colisiones.size();h++) {
							if(colisiones.get(h).getPos_x() == trenes.get(i).getPosX() + 1 && colisiones.get(h).getPos_y() == trenes.get(i).getPosY()) {
								if(colisiones.get(h).getTipo() == 'H') {
									for(int k=0;k<trenes.size();k++) {
										if(trenes.get(k).getDireccion() == 'I' || trenes.get(k).getDireccion() == 'D') {
											Tren trenaux = trenes.get(k);
											trenes.remove(trenaux);		
											k=0;
											}
									}
								}
							}
						}
						break;
					
					}
				}else {
					//el metodo de colision se encarga de comprobar si se produce colision, crearla y almacenarla en el tren que la recibe
					Tren auxiliar = colision(colisiones,trenes.get(i),trenes);
					if(auxiliar != null) {
							//si la colision es por delante
							if(auxiliar.getColision().getPos_x() == auxiliar.getPosX() && auxiliar.getColision().getPos_y() == auxiliar.getPosY()) {
								trenes.remove(auxiliar);
								switch(auxiliar.getDireccion()) {
								case 'A':
									auxiliar.setPos_y(auxiliar.getPosY() - 1);
									break; 
								case 'B':
									auxiliar.setPos_y(auxiliar.getPosY() + 1);
									break;
								case 'I':
									auxiliar.setPos_x(auxiliar.getPosX() + 1);
									break;
								case 'D':
									auxiliar.setPos_x(auxiliar.getPosX() - 1);
									break;
								}
								auxiliar.setLongitud(auxiliar.getLongitud() - 1);
								trenes.add(auxiliar);
								Collections.sort(trenes, new Comparator<Tren>(){
									
									public int compare(Tren t1, Tren t2) {
										return new Integer(t1.getTipo()).compareTo(new Integer(t2.getTipo()));
									}
								});
								
							//si la colision es por detras	
							}else if(auxiliar.getColision().getPos_x() == auxiliar.getVagonFinal()[0] && auxiliar.getColision().getPos_y() == auxiliar.getVagonFinal()[1]) {
								trenes.remove(auxiliar);
								switch(auxiliar.getDireccion()) {
								case 'A':
									auxiliar.vagonFinal[1] += 1;
									break;
								case 'B':
									auxiliar.vagonFinal[1] -= 1;
									break;
								case 'I':
									auxiliar.vagonFinal[0] -= 1;
									break;
								case 'D':
									auxiliar.vagonFinal[0] += 1;
									break;
								}
								auxiliar.setLongitud(auxiliar.getLongitud() - 1);
								trenes.add(auxiliar);
								Collections.sort(trenes, new Comparator<Tren>(){
									
									public int compare(Tren t1, Tren t2) {
										return new Integer(t1.getTipo()).compareTo(new Integer(t2.getTipo()));
									}
								});
								
							//si la colision no es al principio ni al final
							}else {
								nuevoTren(trenes,auxiliar);
								if((i + 1) == trenes.indexOf(auxiliar)){
									i++;
								}
								
							}
						
					}else {
						trenes.get(i).avanzar(trenes.get(i).getDireccion());
					}
					
				}
				if(trenes.get(i).getLongitud() == 0) {
					trenes.remove(trenes.get(i));
				}
			}
		}
	}
	/**
	 * Metodo que comprueba si quedan trenes en el tablero
	 * @param trenes Array con los trenes que hay en el tablero
	 * @return True si quedan trenes, false si no quedan
	 */
	public static boolean hayTrenes(ArrayList<Tren> trenes) {
		if(trenes.isEmpty()) {
			return false;
			
		}else {
			return true;
		}
	}
	/**
	 * Metodo que imprime el output final del programa en forma de tablero, con sus colisiones
	 * @param colisiones ArrayList que guarda las colisiones ocurridas durante la simulacion
	 * @return El tablero despues de la simulacion de los trenes en forma de String
	 */
	public static String tableroFinal(ArrayList<Colision> colisiones) {
		StringBuffer tablero = new StringBuffer();
		tablero.append("  ");
		boolean colision = false;
		for(int i=0;i<num_filas;i++) {
			tablero.append(" " + i/10);
			
		}
		tablero.append("\n  ");
		int w=0;
		for(int i=0;i<num_filas;i++) {
			
				tablero.append(" " + w);
				w+=1;
				if(w == 10) {
					w = 0;
				}
			
		}
		//adaptar las colisiones al tablero
		//guardamos por filas la posicion en y de las colisiones
		boolean casilla_colision = false;
		for(int i=num_columnas-1;i>=0;i--) {
			if(i >= 10) {
				tablero.append("\n" + i);
			}else {
				tablero.append("\n0" + i);
			}
			
			for(int j=0;j<num_filas;j++) {
				for(int k=0;k<colisiones.size();k++) {
					casilla_colision = false;
					if(colisiones.get(k).getPos_y() == i && colisiones.get(k).getPos_x() -1 == j && colisiones.get(k).getTipo() == 'X') {
						casilla_colision = true;
						tablero.append(" " + String.valueOf(colisiones.get(k).getTipo()));
						break;
					}else if(colisiones.get(k).getPos_y() == i && colisiones.get(k).getPos_x() == j && colisiones.get(k).getTipo() == 'H') {
						casilla_colision = true;
						tablero.append(" " + String.valueOf(colisiones.get(k).getTipo()));
						break;
						
					}
				}
				if(casilla_colision == false) {
					tablero.append(" .");
					
				}
			}
		}
		return tablero.toString();
	}
	/**
	 * Metodo que comprueba si un tren tiene delante una colision
	 * @param tren Tren nuevo creado en el main 
	 * @param trenes Array de los trenes ya creados
	 * @param colisiones array con las colisiones
	 * @return Falso si no hay colision, true si hay colision
	 */
	public static boolean hayColision(Tren tren,ArrayList<Tren> trenes,ArrayList<Colision> colisiones) {
		boolean condicion = false;
		for(int i=0;i<colisiones.size();i++) {
			switch(tren.getDireccion()) {
			 case 'A':
				 if(tren.getPosX() == colisiones.get(i).getPos_x() && tren.getPosY() + 1 == colisiones.get(i).getPos_y()) {
					 condicion = true;
					 break;
				 }
				 break;
			 case 'B':
				 if(tren.getPosX() == colisiones.get(i).getPos_x() && tren.getPosY() - 1 == colisiones.get(i).getPos_y()) {
					 condicion = true;
					 break;
				 }
				 break;
			 case 'I':
				 if(tren.getPosX() - 1 == colisiones.get(i).getPos_x() && tren.getPosY() == colisiones.get(i).getPos_y()) {
					 condicion = true;
					 break;
				 }
				 break;
			 case 'D':
				 if(tren.getPosX() + 1 == colisiones.get(i).getPos_x() && tren.getPosY() == colisiones.get(i).getPos_y()) {
					 condicion = true;
					 break;
				 }
				 break;
			 }
		}
		
		 return condicion;
	}
	/**
	 * Comprueba si un tren entrara en colision con un tren en el siguiente movimiento y crea la colision
	 * @param colisiones Array al que se le a�ade una colision en caso de haberla
	 * @param tren Tren con el que se comprueba si hay colision
	 * @param trenes Array que guarda todos los trenes del tablero
	 */
	public static Tren colision(ArrayList<Colision> colisiones,Tren tren,ArrayList<Tren> trenes) {
			//si va a chocar de frente se crea la colision donde va a ser el choque
			//si va a chocar por detras lo mismo que de frente
			//si va a chocar de lado crea la colision en el tren
		Tren tren_auxiliar = null;
		//trenes.remove(tren);
		switch(tren.getDireccion()) {
		case 'A':
			for(int i=0;i<trenes.size();i++) {
				if(tren.getPosY() + 1 == trenes.get(i).getPosY() && tren.betweenPosTren(trenes.get(i))) {
					tren.avanceColision(trenes.get(i).getDireccion());
					trenes.get(i).setColision(tren.getPosX(), tren.getPosY()+1);
					Colision col = new Colision(tren.getPosX()+1,tren.getPosY()+1,'X');
					tren_auxiliar = trenes.get(i);
					trenes.remove(tren_auxiliar);
					boolean col_ya_anyadida = false;
					for(Colision col1: colisiones) {
						if(col1.getPos_x() == col.getPos_x() && col1.getPos_y() == col.getPos_y()) {
							col_ya_anyadida = true;
						}
					}
					if(col_ya_anyadida == false) {
						colisiones.add(col);
					}
					break;
				}
			}
			break;
		case 'B':
			for(int i=0;i<trenes.size();i++) {
				if(tren.getPosY() - 1 == trenes.get(i).getPosY() && tren.betweenPosTren(trenes.get(i))) {
					tren.avanceColision(trenes.get(i).getDireccion());
					trenes.get(i).setColision(tren.getPosX(), tren.getPosY()-1);
					Colision col = new Colision(tren.getPosX()+1, tren.getPosY()-1,'X');
					tren_auxiliar = trenes.get(i);
					trenes.remove(tren_auxiliar);
					boolean col_ya_anyadida = false;
					for(Colision col1: colisiones) {
						if(col1.getPos_x() == col.getPos_x() && col1.getPos_y() == col.getPos_y()) {
							col_ya_anyadida = true;
						}
					}
					if(col_ya_anyadida == false) {
						colisiones.add(col);
					}
					break;
				}
			}
			break;
		case 'I':
			for(int i=0;i<trenes.size();i++) {
				if(tren.getPosX() - 1 == trenes.get(i).getPosX() && tren.betweenPosTren(trenes.get(i))) {
					tren.avanceColision(trenes.get(i).getDireccion());
					trenes.get(i).setColision(tren.getPosX() - 1, tren.getPosY());
					Colision col = new Colision(tren.getPosX(), tren.getPosY(),'X');
					tren_auxiliar = trenes.get(i);
					trenes.remove(tren_auxiliar);
					boolean col_ya_anyadida = false;
					for(Colision col1: colisiones) {
						if(col1.getPos_x() == col.getPos_x() && col1.getPos_y() == col.getPos_y()) {
							col_ya_anyadida = true;
						}
					}
					if(col_ya_anyadida == false) {
						colisiones.add(col);
					}
					break;
				}
			}
			break;
		case 'D':
			for(int i=0;i<trenes.size();i++) {
				if(tren.getPosX() + 1 == trenes.get(i).getPosX() && tren.betweenPosTren(trenes.get(i))) {
					tren.avanceColision(trenes.get(i).getDireccion());
					trenes.get(i).setColision(tren.getPosX() + 1, tren.getPosY());
					Colision col = new Colision(tren.getPosX() + 2, tren.getPosY(),'X');
					tren_auxiliar = trenes.get(i);
					trenes.remove(tren_auxiliar);
					boolean col_ya_anyadida = false;
					for(Colision col1: colisiones) {
						if(col1.getPos_x() == col.getPos_x() && col1.getPos_y() == col.getPos_y()) {
							col_ya_anyadida = true;
						}
					}
					if(col_ya_anyadida == false) {
						colisiones.add(col);
					}
					break;
				}
			}
			break;
		}
		//trenes.add(tren);
	/*	Collections.sort(trenes, new Comparator<Tren>(){
			
			public int compare(Tren t1, Tren t2) {
				return new Integer(t1.getTipo()).compareTo(new Integer(t2.getTipo()));
			}
		});*/
		return tren_auxiliar;
	}
	/**
	 * Metodo que se invoca cuando un tren recibe una colision lateral. El tren colisionado se divide en dos trenes.
	 * @param trenes ArrayList de trenes, donde se a�ade el nuevo tren creado
	 * @param tren Tren colisionado por el lateral, que cambiara su longitud
	 */
	public static void nuevoTren(ArrayList<Tren> trenes, Tren tren) {
		//entra en la condicion si la colision es lateral. Sino no hace nada
		
			//borramos el tren colisionado del array, porque hay que modificarlo y dividirlo en dos trenes
		
			trenes.remove(tren);
			//creamos el nuevo tren y le pasamos sus atributos. despues actualizamos el otro tren. del tren antiguo solo se modifica la longitud y la posicion del vagon final
			Tren nuevo = new Tren();
			nuevo.setDireccion(tren.getDireccion());
			switch(nuevo.getDireccion()) {
			case 'A':
				nuevo.setPos_x(tren.getPosX());
				nuevo.setPos_y(tren.getColision().getPos_y() - 1);
				nuevo.vagonFinal = tren.getVagonFinal();
				nuevo.setLongitud(nuevo.getPosY() - nuevo.getVagonFinal()[1] + 1);
				
				tren.vagonFinal[1] = tren.getColision().getPos_y() + 1;
				tren.setLongitud(tren.getLongitud() - nuevo.getLongitud());
				break;
			case 'B':
				nuevo.setPos_x(tren.getPosX());
				nuevo.setPos_y(tren.getColision().getPos_y() + 1);
				nuevo.vagonFinal = tren.getVagonFinal();
				nuevo.setLongitud(nuevo.getVagonFinal()[1] - nuevo.getPosY() + 1);
				
				tren.vagonFinal[1] = tren.getColision().getPos_y() - 1;
				tren.setLongitud(tren.getLongitud() - nuevo.getLongitud());
				break;
			case 'I':
				nuevo.setPos_x(tren.getColision().getPos_x() + 1);
				nuevo.setPos_y(tren.getPosY());
				nuevo.vagonFinal = tren.getVagonFinal();
				nuevo.setLongitud(nuevo.getVagonFinal()[0] - nuevo.getPosX() + 1);
				
 				tren.vagonFinal[0] = tren.getColision().getPos_x() - 1;
				tren.setLongitud(tren.getLongitud() - nuevo.getLongitud());
				break;
			case 'D':
				nuevo.setPos_x(tren.getColision().getPos_x() - 1);
				nuevo.setPos_y(tren.getPosY());
				nuevo.vagonFinal = tren.getVagonFinal();
				nuevo.setLongitud(nuevo.getPosX() - nuevo.getVagonFinal()[0] + 1);
				
				tren.vagonFinal[0] = tren.getColision().getPos_x() + 1;
				tren.setLongitud(tren.getLongitud() - nuevo.getLongitud());
				break;
			}
		
			
			trenes.add(tren);
			trenes.add(nuevo);
			Collections.sort(trenes, new Comparator<Tren>(){
				
				public int compare(Tren t1, Tren t2) {
					return new Integer(t1.getTipo()).compareTo(new Integer(t2.getTipo()));
				}
			});
			for(int i=0;i<trenes.size();i++) {
				if(trenes.get(i).getLongitud() <= 0) {
					trenes.remove(i);
				}
			}
		}
}