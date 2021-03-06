package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Created by Kaleb on 10/9/2016.
 */
public class Sintactico {



    Controller ctrl;
    int contadorNodos=1;
    int cordX=350, cordY=100;
    int hijosLibreria=0;
    int hijosVar=0;
    int hijosInicio=0;

    @FXML
    TextArea corregidoCorregido;

    boolean encontradaLibreria=false,encontradaVar=false,encontradaInicio=false;

    public  static ArrayList<String> nombre_Nodo = new ArrayList<>(Arrays.asList());//Nombre del nodo
    public  static ArrayList<Integer> coordenada_X = new ArrayList<>(Arrays.asList());//Coordenada X
    public  static ArrayList<Integer> coodernada_Y = new ArrayList<>(Arrays.asList());//Coordenanda Y


    //INICIA TABLA DE SIMBOLOS
    //Listas para tabla de símbolos
    public static ArrayList<String> Directorio_Tabla = new ArrayList<>(Arrays.asList());
    public static ArrayList<String> Linea_Tabla = new ArrayList<>(Arrays.asList());
    public static ArrayList<String> Token_Tabla= new ArrayList<>(Arrays.asList());
    public static ArrayList<String> Rol_Tabla = new ArrayList<>(Arrays.asList());
    public static ArrayList<String> Ambito_Tabla = new ArrayList<>(Arrays.asList());
    public static ArrayList<String> valorInicial_Tabla = new ArrayList<>(Arrays.asList());
    public static ArrayList<String> valorFinal_Tabla = new ArrayList<>(Arrays.asList());//


    Hashtable<String,String> valorFinal_Hash = new Hashtable<>();
    //ACABA TABLA DE SÏMBOLOS




    boolean errorSintactico_Encontrado_Parar=false;
    String mensajeError_Sintactico=null;

    boolean programaEncontrado=false;
    public void detectarNodos_Arbol(){
        boolean verificaLibreria=false;
        boolean errorLibreria=false;
        boolean verificaVar=false;
        boolean verificaInicio=false;
        boolean errorVar=false;

        boolean libCompleta=false;
        boolean varCompleta=false;
        boolean inicioCompleta=false;

        boolean auxLibreria=false;
        boolean libreriaNOT=false;





        int hijos_Padre=0;
        int auxContador=0;

        for(int i=0;i<ctrl.lexico_Token.size();i++){
            auxContador=0;
            auxContador=i+1;

            if(errorSintactico_Encontrado_Parar==true)//Sa termina for
                break;

            if(ctrl.lexico_Token.get(i).equals("programa")){
                if(auxContador<ctrl.lexico_Token.size()){
                    if(ctrl.lexico_Token.get(i).matches("[a-zA-Z]+")){
                        programaEncontrado=true;

                        nombre_Nodo.add(ctrl.lexico_Token.get(i+1));//Agregamos la palabra reservada libreria
                        coordenada_X.add(100);
                        coodernada_Y.add(200);
                        i++;
                    }
                }
            }

            if(ctrl.lexico_Token.get(i).equals("libreria") && ctrl.lexico_Identificador.get(i).equals("Reservada") || verificaLibreria==true && libCompleta==false) {//Libreria

                encontradaLibreria=true;
                libreriaNOT=true;
                System.out.println("Entro libreria");

                if(auxLibreria==false) {
                    cordX = 170;
                    cordY = 200;
                    verificaLibreria = true;


                    nombre_Nodo.add(ctrl.lexico_Token.get(i));//Agregamos la palabra reservada libreria
                    coordenada_X.add(cordX);
                    coodernada_Y.add(cordY);
                    contadorNodos++;
                    hijos_Padre++;
                    hijosLibreria++;

                    if (hijos_Padre == 1) {
                        cordX = cordX + 90;
                    }

                /*
                if(ctrl.lexico_Token.get(i+1).matches("[<][A-Z][A-Za-z0-9]+[.][p][>]"))//Verificar siguiente parte
                    i++;
                else {
                    errorLibreria = true;
                    System.out.println("Error en libreria");
                }

                if(errorLibreria==true)
                    break;
                 */

                    i++;

                    nombre_Nodo.add(ctrl.lexico_Token.get(i));//Agregamos el nombre de la libreria <Hola.p>
                    coordenada_X.add(cordX);
                    coodernada_Y.add(cordY);
                    contadorNodos++;
                    hijosLibreria++;
                    cordX=cordX+80;
                    cordY=cordY+100;
                }

                if(auxLibreria==true) {
                    nombre_Nodo.add(ctrl.lexico_Token.get(i));//Agregamos lo que siga
                    coordenada_X.add(cordX);
                    coodernada_Y.add(cordY);
                    contadorNodos++;
                    hijosLibreria++;

                }
                auxLibreria=true;
                if (auxContador < ctrl.lexico_Token.size()) {
                    if (ctrl.lexico_Token.get(i + 1).equals("var")) {
                        verificaLibreria = false;
                        libCompleta = true;
                    }
                }

            }
            if(errorLibreria==true)//Terminar por error
                break;

            if(ctrl.lexico_Token.get(i).equals("var") && ctrl.lexico_Identificador.get(i).equals("Reservada") || verificaVar==true && varCompleta==false && libCompleta==true || libreriaNOT==false ) {//Var. Declaración

                System.out.println("Entró var");
                verificaVar=true;
                encontradaVar=true;

                if(hijosVar<1) {
                    i = i + 1;

                    cordX = 450;
                    cordY = 200;
                }
                if(ctrl.lexico_Token.get(i).equals("var"))
                    i++;

                nombre_Nodo.add(ctrl.lexico_Token.get(i));//Agregamos los elemento de var
                coordenada_X.add(cordX);
                coodernada_Y.add(cordY);
                contadorNodos++;
                cordY=cordY+100;
                hijosVar++;

                if(auxContador<ctrl.lexico_Token.size()) {
                    if (ctrl.lexico_Token.get(i + 1).equals("inicio")) {
                        verificaVar = false;
                        varCompleta = true;
                    }
                }
            }

            if(ctrl.lexico_Token.get(i).equals("inicio") && ctrl.lexico_Identificador.get(i).equals("Reservada") || verificaInicio==true && varCompleta==true && libCompleta==true) {//Var. Declaración

                verificaInicio=true;
                encontradaInicio=true;
                System.out.println("Entró inicio");

                if(hijosInicio<1) {
                    i++;

                    cordX = 550;
                    cordY = 200;
                }

                if(ctrl.lexico_Token.get(i).equals("inicio"))
                    i++;


                if(auxContador<ctrl.lexico_Token.size()) {
                    if (ctrl.lexico_Token.get(i + 1).equals(":=")) {
                        nombre_Nodo.add("<Asignacion>");//Agregamos los elemento de inicio
                        coordenada_X.add(cordX);
                        coodernada_Y.add(cordY);
                        contadorNodos++;
                        cordY = cordY + 100;
                        hijosInicio++;
                    }
                }


                nombre_Nodo.add(ctrl.lexico_Token.get(i));//Agregamos los elemento de inicio
                coordenada_X.add(cordX);
                coodernada_Y.add(cordY);
                contadorNodos++;
                cordY=cordY+100;
                hijosInicio++;
            }
        }
        System.out.println("Tamaño: "+nombre_Nodo.size());
        abrirArbol();
    }


    boolean programaVerificado=false, libreriaVerificado=false, varVerificado=false, inicioVerificado=false, inicioVerificado_Auxiliar=false;
    boolean inicializaCoordenada_Y_VAR=false;
    boolean inicializaCoordenada_Y_INICIO=false;


    public void deteccionNodos_Tokens() throws  IOException{//Usar banderas para que entre a un area si encuentra la palabra principal
        int listaLexico_Size = ctrl.lexico_Token.size();
        int auxSize_Lexico = 0;
        for (int i = 0; i < listaLexico_Size && errorSintactico_Encontrado_Parar==false; i++) {//Orden de la estructura del programa
            auxSize_Lexico = i + 1;

            if(errorSintactico_Encontrado_Parar==true) {//Sa termina for
                mostrarCodigoCorregido();
                break;
            }

            System.out.println("Token actual: "+ctrl.lexico_Token.get(i));

            //Checar banderas para evitar que ingrese donde no debe. Posible solución variación de estructura según lo que puede encontrar
            if (ctrl.lexico_Token.get(i).equals("programa")) {
                System.out.println("Verifica prograa");
                if (auxSize_Lexico < listaLexico_Size) {//Evitar que truene por dirección inválida por tamaño inferior

                    seccionPrograma(ctrl.lexico_Token.get(i), ctrl.lexico_Token.get(auxSize_Lexico), ctrl.lexico_Linea.get(i));//Pasamos los valores
                }
            } else if (ctrl.lexico_Token.get(i).equals("libreria")) {
                System.out.println("Verifica libreria");
                if (auxSize_Lexico < listaLexico_Size) {//Evitar que truene por dirección inválida por tamaño inferior
                    seccionLibreria(ctrl.lexico_Token.get(i), ctrl.lexico_Token.get(auxSize_Lexico), ctrl.lexico_Linea.get(i));//Pasamos los valores
                }
            } else if (ctrl.lexico_Token.get(i).equals("var") || varVerificado == true) {
                varVerificado = true;
                if (ctrl.lexico_Token.get(i).equals("var")) {
                    inicializaBanderas_Var();
                    auxVar = 0;
                }

                System.out.println("Verificar var");

                if (ctrl.lexico_Identificador.get(i).equals("Comentario Bloque") || ctrl.lexico_Identificador.get(i).equals("Comentario Linea")) {
                    if (auxSize_Lexico < listaLexico_Size) {
                        i++;
                    }
                }
                System.out.println("Verificando: " + ctrl.lexico_Token.get(i));


                if (!ctrl.lexico_Token.get(i).equals("var")) {//Ver en la lista de identificador si es comentario i++

                    if (inicializaCoordenada_Y_VAR == false)
                        cordY = 200;
                    inicializaCoordenada_Y_VAR = true;

                    if (auxSize_Lexico < listaLexico_Size) {
                        if (ctrl.lexico_Token.get(auxSize_Lexico).equals(":")) {
                            nombre_Nodo.add("<Asignación>");
                            coordenada_X.add(450);
                            coodernada_Y.add(cordY);
                            cordY = cordY + 100;
                        }

                        Asignacion_Var(ctrl.lexico_Token.get(i), ctrl.lexico_Linea.get(i));
                        //Si detecta funcion o procedimiento entra a esa sección para verificar

                        if (ctrl.lexico_Token.get(i).equals("inicio")) {//Ver como iría esta verificación
                            System.out.println("Se encontró inicio");
                            varVerificado = false; // no se activa si funcion_procedimientoProhibido está en true, para
                            //que siga verificando en la parte de var lo de inicio y lo deje con su coordenada específica
                            i--;
                        }
                    }
                }
                } else if (ctrl.lexico_Token.get(i).equals("inicio") && esperaFin_Func==false && esperaFin_Proc==false || inicioVerificado==true) {//Arreglar la bandera
                    inicioVerificado = true;

                //Si espera está encendida entra al inicio auxilar


                /*
                    Si la bandera de esperaFin_Proc o eperaFin_Func cambia a false se reinicia inicioVerficiado=false
                    Esto para que pueda entrar cuando ya sea la sección de inicio normal
                 */

                    System.out.println("Verifica inicio");

                    if (inicializaCoordenada_Y_INICIO == false)
                        cordY = 200;
                    inicializaCoordenada_Y_INICIO = true;

                    if (ctrl.lexico_Identificador.get(i).equals("Comentario Bloque") || ctrl.lexico_Identificador.get(i).equals("Comentario Linea")) {
                        if (auxSize_Lexico < listaLexico_Size) {
                            i++;
                        }
                    }

                    if (ctrl.lexico_Token.get(i).equals("inicio")) {
                        inicializaBanderas_Var();
                        auxVar = 0;
                    }
                    if (!ctrl.lexico_Token.get(i).equals("inicio")) {//AQUI
                        String Identificador = "([A-Z]{1,1}[a-zA-Z0-9]{2,254})";
                        if (auxSize_Lexico < listaLexico_Size) {
                            if (ctrl.lexico_Token.get(auxSize_Lexico).equals(":=") && ctrl.lexico_Token.get(i).matches(Identificador))  {
                                asignacionInicio = true;
                                System.out.println("Va asignación");
                                nombre_Nodo.add("<Asignación>");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                            }
                            if (ctrl.lexico_Token.get(i).equals("para")) {
                                para = true;
                                System.out.println("Va para");
                                nombre_Nodo.add("<Para>");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                            }
                            if (ctrl.lexico_Token.get(i).equals("escribir")) {
                                escribir = true;
                                System.out.println("Va escribir");
                                nombre_Nodo.add("<Escribir>");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                            }
                            if (ctrl.lexico_Token.get(i).equals("escribirSL")) {
                                escribirSL = true;
                                System.out.println("Va escribirSL");
                                nombre_Nodo.add("<EscribirSL>");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                            }
                            if (ctrl.lexico_Token.get(i).equals("leer")) {
                                leer = true;
                                System.out.println("Va leer");
                                nombre_Nodo.add("<Leer>");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                            }
                            if (ctrl.lexico_Token.get(i).equals("si")) {
                                si_Inicio = true;
                                System.out.println("Va si");
                                nombre_Nodo.add("<Si>");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                            }
                            else if (ctrl.lexico_Token.get(auxSize_Lexico).equals("(") && si_Inicio==false  && escribir==false && escribirSL==false && leer==false && ctrl.lexico_Token.get(i).matches(Identificador)) {
                                invocacionInicio = true;
                                System.out.println("Va invocación");
                                nombre_Nodo.add("<Invocación>");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                            }
                        }

                        if(ctrl.lexico_Token.get(i).equals("hazlo_si")){
                            hazlo_siInicio=true;
                            System.out.println("Va hazlo_si");
                            nombre_Nodo.add("<Va hazlo si>");
                            coordenada_X.add(550);
                            coodernada_Y.add(cordY);
                            cordY=cordY+100;

                        }

                        if(ctrl.lexico_Token.get(i).equals("repite")){
                            repiteInicio=true;
                            System.out.println("Va repite");
                            nombre_Nodo.add("<Va repite>");
                            coordenada_X.add(550);
                            coodernada_Y.add(cordY);
                            cordY=cordY+100;

                        }
                        if (auxSize_Lexico < listaLexico_Size && auxSize_Lexico + 1 < listaLexico_Size) {
                            if(esperaFin_Para==true) {
                                if (ctrl.lexico_Token.get(i).equals("finpara")) {//Fin hazlo si
                                    //según la bandera que esté encendida
                                    //esperaFinFunc
                                    //esperaFinProc
                                    if (ctrl.lexico_Token.get(i + 1).equals(";")) {
                                        System.out.println("Fin para detectado");
                                        nombre_Nodo.add("fin para");
                                        coordenada_X.add(550);
                                        coodernada_Y.add(cordY);
                                        cordY = cordY + 100;

                                        nombre_Nodo.add(";");
                                        coordenada_X.add(550);
                                        coodernada_Y.add(cordY);
                                        cordY = cordY + 100;
                                        //abrirArbol();
                                        esperaFin_Para=false;
                                    }
                                }
                            }
                        }

                        System.out.println("Verificando en inicio: " + ctrl.lexico_Token.get(i));
                        //Agregar de minetras sea difrente a inicio como en la parte de var
                        secciónInicio(ctrl.lexico_Token.get(i), ctrl.lexico_Linea.get(i));
                    }
                    if (auxSize_Lexico < listaLexico_Size && auxSize_Lexico + 1 < listaLexico_Size) {
                        if (ctrl.lexico_Token.get(auxSize_Lexico).equals("fin")) {
                            if (ctrl.lexico_Token.get(auxSize_Lexico + 1).equals(".")) {
                                System.out.println("Fin detectado oficial");
                                nombre_Nodo.add("fin");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;

                                nombre_Nodo.add(".");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                                abrirArbol();
                            }
                        }
                    }
                    //Contiene un bloque. El cual es génerico
                }else if (ctrl.lexico_Token.get(i).equals("inicio") || esperaFin_Func==true || esperaFin_Proc==true || inicioVerificado_Auxiliar == true) {
                inicioVerificado_Auxiliar = true;

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////Inicio auxiliar////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////


                /*
                    Si la bandera de esperaFin_Proc o eperaFin_Func cambia a false se reinicia inicioVerficiado=false
                    Esto para que pueda entrar cuando ya sea la sección de inicio normal
                 */

                System.out.println("Verifica inicio auxiliar");



                if (ctrl.lexico_Identificador.get(i).equals("Comentario Bloque") || ctrl.lexico_Identificador.get(i).equals("Comentario Linea")) {
                    if (auxSize_Lexico < listaLexico_Size) {
                        i++;
                    }
                }

                if (ctrl.lexico_Token.get(i).equals("inicio")) {
                    inicializaBanderas_Var();
                    auxVar = 0;
                }
                if (!ctrl.lexico_Token.get(i).equals("inicio")) {
                    String Identificador = "([A-Z]{1,1}[a-zA-Z0-9]{2,254})";
                    if (auxSize_Lexico < listaLexico_Size) {
                        if (ctrl.lexico_Token.get(auxSize_Lexico).equals(":=") && ctrl.lexico_Token.get(i).matches(Identificador))  {
                            asignacionInicio = true;
                            System.out.println("Va asignación");
                            nombre_Nodo.add("<Asignación>");
                            coordenada_X.add(450);
                            coodernada_Y.add(cordY);
                            cordY = cordY + 100;
                        }
                        if (ctrl.lexico_Token.get(i).equals("para")) {
                            para = true;
                            System.out.println("Va para");
                            nombre_Nodo.add("<Para>");
                            coordenada_X.add(450);
                            coodernada_Y.add(cordY);
                            cordY = cordY + 100;
                        }
                        if (ctrl.lexico_Token.get(i).equals("escribir")) {
                            escribir = true;
                            System.out.println("Va escribir");
                            nombre_Nodo.add("<Escribir>");
                            coordenada_X.add(450);
                            coodernada_Y.add(cordY);
                            cordY = cordY + 100;
                        }
                        if (ctrl.lexico_Token.get(i).equals("escribirSL")) {
                            escribirSL = true;
                            System.out.println("Va escribirSL");
                            nombre_Nodo.add("<EscribirSL>");
                            coordenada_X.add(450);
                            coodernada_Y.add(cordY);
                            cordY = cordY + 100;
                        }
                        if (ctrl.lexico_Token.get(i).equals("leer")) {
                            leer = true;
                            System.out.println("Va leer");
                            nombre_Nodo.add("<Leer>");
                            coordenada_X.add(450);
                            coodernada_Y.add(cordY);
                            cordY = cordY + 100;
                        }
                        if (ctrl.lexico_Token.get(i).equals("si")) {
                            si_Inicio = true;
                            System.out.println("Va si");
                            nombre_Nodo.add("<Si>");
                            coordenada_X.add(450);
                            coodernada_Y.add(cordY);
                            cordY = cordY + 100;
                        }
                        else if (ctrl.lexico_Token.get(auxSize_Lexico).equals("(") && si_Inicio==false && escribir==false && escribirSL==false && leer==false && ctrl.lexico_Token.get(i).matches(Identificador)) {
                            invocacionInicio = true;
                            System.out.println("Va invocación");
                            nombre_Nodo.add("<Invocación>");
                            coordenada_X.add(450);
                            coodernada_Y.add(cordY);
                            cordY = cordY + 100;
                        }
                    }

                    if(ctrl.lexico_Token.get(i).equals("hazlo_si")){
                        hazlo_siInicio=true;
                        System.out.println("Va hazlo_si");
                        nombre_Nodo.add("<Va hazlo si>");
                        coordenada_X.add(550);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;

                    }

                    if(ctrl.lexico_Token.get(i).equals("repite")){
                        repiteInicio=true;
                        System.out.println("Va repite");
                        nombre_Nodo.add("<Va repite>");
                        coordenada_X.add(550);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;

                    }

                    System.out.println("Verificando en inicio auxiliar: " + ctrl.lexico_Token.get(i));
                    //Agregar de minetras sea difrente a inicio como en la parte de var
                    secciónInicio_VAR(ctrl.lexico_Token.get(i), ctrl.lexico_Linea.get(i));
                }
                if (auxSize_Lexico < listaLexico_Size && auxSize_Lexico + 1 < listaLexico_Size) {
                    if(esperaFin_Func==true) {
                        if (ctrl.lexico_Token.get(auxSize_Lexico).equals("finfunc")) {//Cambiar por el por finfunc o finproc
                            //según la bandera que esté encendida
                            //esperaFinFunc
                            //esperaFinProc
                            System.out.println("FINFUNC_DETECTADO");
                            if (ctrl.lexico_Token.get(auxSize_Lexico + 1).equals(";")) {
                                System.out.println("Fin función detectado");
                                nombre_Nodo.add("finfunc");
                                coordenada_X.add(450);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;

                                nombre_Nodo.add(";");
                                coordenada_X.add(450);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                                //abrirArbol();
                                esperaFin_Func=false;//Se pueda contiuar validando var
                                inicioVerificado_Auxiliar=false;//Se pueda verificar otro
                                funcion_procedimientoProhibido=false;//Se pueda verificar otro
                            }
                        }
                    }
                }
                if (auxSize_Lexico < listaLexico_Size && auxSize_Lexico + 1 < listaLexico_Size) {
                    if(esperaFin_Proc==true) {
                        if (ctrl.lexico_Token.get(i).equals("finproc")) {//Cambiar por el por finfunc o finproc
                            //según la bandera que esté encendida
                            //esperaFinFunc
                            //esperaFinProc
                            if (ctrl.lexico_Token.get(i + 1).equals(";")) {
                                System.out.println("Fin procedimiento detectado");
                                nombre_Nodo.add("finproc");
                                coordenada_X.add(450);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;

                                nombre_Nodo.add(";");
                                coordenada_X.add(450);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                                //abrirArbol();
                                esperaFin_Proc=false;
                                inicioVerificado_Auxiliar=false;
                                funcion_procedimientoProhibido=false;
                            }
                        }
                    }
                }


                if (auxSize_Lexico < listaLexico_Size && auxSize_Lexico + 1 < listaLexico_Size) {
                    if(esperaFin_Proc==true) {
                        if (ctrl.lexico_Token.get(auxSize_Lexico).equals("finhazlo")) {//Fin hazlo si
                            //según la bandera que esté encendida
                            //esperaFinFunc
                            //esperaFinProc
                            if (ctrl.lexico_Token.get(auxSize_Lexico + 1).equals(";")) {
                                System.out.println("Fin hazlo_si detectado");
                                nombre_Nodo.add("finhazlo");
                                coordenada_X.add(450);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;

                                nombre_Nodo.add(";");
                                coordenada_X.add(450);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                                //abrirArbol();
                                hazlo_siInicio=false;
                            }
                        }
                    }
                }

                if (auxSize_Lexico < listaLexico_Size && auxSize_Lexico + 1 < listaLexico_Size) {
                    if(esperaFin_Para==true) {
                        if (ctrl.lexico_Token.get(i).equals("finpara")) {//Fin hazlo si
                            //según la bandera que esté encendida
                            //esperaFinFunc
                            //esperaFinProc
                            if (ctrl.lexico_Token.get(i + 1).equals(";")) {
                                System.out.println("Fin para detectado");
                                nombre_Nodo.add("fin para");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;

                                nombre_Nodo.add(";");
                                coordenada_X.add(550);
                                coodernada_Y.add(cordY);
                                cordY = cordY + 100;
                                //abrirArbol();
                                esperaFin_Para=false;
                            }
                        }
                    }
                }


                //Contiene un bloque. El cual es génerico
            }
            }
        }



    public void mostrarCodigoCorregido()throws IOException{
        System.out.println("asdasdsa: "+archivoCorregido);
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("erroresCorregido.fxml"));
        //root.getStylesheets().add(Controller.class.getResource("java-keywords.css").toExternalForm());//Importamos el css a usar
        primaryStage.setTitle("Compilador Manual - Traductores II(Errores)");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();


    }

    public void agregarCorrecion(){
        //corregidoCorregido.setText(archivoCorregido);
        System.out.println("Corregio: "+archivoCorregido + archivoCorregido.length());
        corregidoCorregido.setText(archivoCorregido);














    }

    public static String archivoCorregido="";
    int auxLinea=1;






    public void seccionPrograma(String Token1, String Token2, Integer Linea){

        //Token1
        Directorio_Tabla.add(System.getProperty("user.dir"));
        Linea_Tabla.add(Linea.toString());
        Token_Tabla.add(Token1);
        Rol_Tabla.add("PR");
        Ambito_Tabla.add("G");
        valorInicial_Tabla.add("");
        valorFinal_Tabla.add("");

        //Token 2
        Directorio_Tabla.add(System.getProperty("user.dir"));
        Linea_Tabla.add(Linea.toString());
        Token_Tabla.add(Token2);
        Rol_Tabla.add("Identificador");
        Ambito_Tabla.add("G");
        valorInicial_Tabla.add("");
        valorFinal_Tabla.add("");

        if(Token1.equals("programa")) {
            if(Token2.matches("[A-Z][a-zA-Z0-9]+")) {
                nombre_Nodo.add(Token1);
                coordenada_X.add(100);
                coodernada_Y.add(200);

                nombre_Nodo.add(Token2);
                coordenada_X.add(100);
                coodernada_Y.add(300);
                programaVerificado=true;
                archivoCorregido+=Token1+" ";
                archivoCorregido+=Token2+" ";
            }
            else {
                System.out.println("Error en declaración de programa. Se esperaba nombre de programa");
                //ctrl.txtMensajes.appendText("Error en declaración de programa. Se esperaba nombre de programa");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Error en declaración de programa. Se esperaba nombre de programa"+" En linea: "+Linea;
            }
        }
    }

    public void seccionLibreria(String Token1, String Token2, Integer Linea){

        //Token1
        Directorio_Tabla.add(System.getProperty("user.dir"));
        Linea_Tabla.add(Linea.toString());
        Token_Tabla.add(Token1);
        Rol_Tabla.add("PR");
        Ambito_Tabla.add("Li");
        valorInicial_Tabla.add(" ");
        valorFinal_Tabla.add(" ");

        //Token 2
        Directorio_Tabla.add(System.getProperty("user.dir"));
        Linea_Tabla.add(Linea.toString());
        Token_Tabla.add(Token2);
        Rol_Tabla.add("Identificador");
        Ambito_Tabla.add("Li");
        valorInicial_Tabla.add(" ");
        valorFinal_Tabla.add(" ");

        if(Token1.equals("libreria")) {
            if(Token2.matches("[<][A-Z][a-zA-Z0-9]+[.][p][>]")) {//Si hay otra aumenta 100 en Y
                nombre_Nodo.add(Token1);
                coordenada_X.add(170);
                coodernada_Y.add(200);

                nombre_Nodo.add(Token2);
                coordenada_X.add(260);
                coodernada_Y.add(200);
                libreriaVerificado=true;

                archivoCorregido+=Token1+" ";
                archivoCorregido+=Token2+" ";
            }
            else {
                System.out.println("Error en declaración de libreria. Se esperaba nombre de libreria");
                //ctrl.txtMensajes.appendText("Error en declaración de libreria. Se esperaba nombre de programa");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Error en declaración de libreria. Se esperaba nombre de programa"+" En linea: "+Linea;
            }
        }
    }

    boolean ID_Encontrado=false, Asignacion_Encontrado=false, tipoDato_Encontrado=false, tipoArreglo_Encontrado=false, Separador_Encontrado=false;
    boolean abrirArreglo_Encontrado=false, inicioArreglo_Encontrado=false, puntosArreglo_Encontrado=false, limiteArreglo_Encontrado=false, cerrarArreglo_Encontrado=false, deArreglo_Encontrado=false;
    int auxVar=0;
    boolean errorSintactico_Encontrado=false;
    boolean arregloDetectado=false;
    boolean funcionDetectado=false; // Ver como se inicializa
    boolean procedimientoDetectado=false; // Ver como se inicializa
    boolean funcion_sinParametros=false;
    boolean funcion_parametroEncontrado=false;
    boolean funcion_unParametro=false;
    boolean funcion_dosParametros=false;

    boolean procedimiento_unParametro=false;
    boolean procedimiento_dosParametro=false;

    boolean funcion_procedimientoProhibido=false;

    public void inicializaBanderas_Var(){
        ID_Encontrado=false; Asignacion_Encontrado=false; tipoDato_Encontrado=false; tipoArreglo_Encontrado=false; Separador_Encontrado=false;
        abrirArreglo_Encontrado=false; inicioArreglo_Encontrado=false; puntosArreglo_Encontrado=false; limiteArreglo_Encontrado=false; cerrarArreglo_Encontrado=false; deArreglo_Encontrado=false;

        auxVar=-1;

        errorSintactico_Encontrado=false; arregloDetectado=false;

        funcion_sinParametros=false; funcion_parametroEncontrado=false; funcion_unParametro=false; funcion_dosParametros=false;

        procedimiento_unParametro=false; procedimiento_dosParametro=false;

        System.out.println("Banderas inicializadas");
    }

    public void seccionVar(String Token){
        String Identificador = "([A-Z]{1,1}[a-zA-Z0-9]{2,254})";
        String tipoDato = "(bool|entero|largo|byte|string|flotante)";
        String comentarioLinea = ("[{].[}]");
        String comentarioBloque = ("[{//].[//}]");

        System.out.println("Token Var: "+Token);//Areglar la sucesión por la bandera
        if(Token.matches(Identificador) || ID_Encontrado==true){//Declaración normal
            ID_Encontrado=true;
            if(Token.matches(":") || Asignacion_Encontrado==true){
                Asignacion_Encontrado=true;
                if(Token.matches(tipoDato) || tipoDato_Encontrado==true){
                    tipoDato_Encontrado=true;
                    if(Token.equals(";") || Separador_Encontrado==true){
                        Separador_Encontrado=true;
                        //Volvemos a inicializar las banderas
                        inicializaBanderas_Var();
                    }
                    else {
                            System.out.println("Se esperaba ;");
                    }
                }
                else if(Token.equals("arreglo") || tipoArreglo_Encontrado==true){//Declaraciópn de arreglo
                    tipoArreglo_Encontrado=true;
                    if(Token.equals("[") || abrirArreglo_Encontrado==true){
                        abrirArreglo_Encontrado=true;
                        if(Token.equals("1") || inicioArreglo_Encontrado==true){
                            inicioArreglo_Encontrado=true;
                            if(Token.equals("..") || puntosArreglo_Encontrado==true){
                                puntosArreglo_Encontrado=true;
                                if(Token.matches("[0-9]+") || limiteArreglo_Encontrado==true){
                                    limiteArreglo_Encontrado=true;
                                    if(Token.equals("]") || cerrarArreglo_Encontrado==true){
                                        cerrarArreglo_Encontrado=true;
                                        if(Token.equals("de") ||deArreglo_Encontrado==true){
                                            deArreglo_Encontrado=true;
                                            if(Token.matches(tipoDato) || tipoDato_Encontrado==true){
                                                tipoDato_Encontrado=true;
                                                if (Token.equals(";") || Separador_Encontrado==true){
                                                    Separador_Encontrado=true;
                                                    //Volvemos a inicializar las banderas
                                                    inicializaBanderas_Var();
                                                }
                                                else {
                                                        System.out.println("Se esperaba ;");
                                                }
                                            }
                                            else {
                                                    System.out.println("Se esperaba tipo de dato para el arreglo");
                                            }
                                        }
                                        else {
                                                System.out.println("Se esperaba declarion de tipo de arreglo de");
                                        }
                                    }
                                    else {
                                            System.out.println("Se esperaba cerrado de arreglo ]");
                                    }
                                }
                                else {
                                        System.out.println("Se esperaba límite de arreglo (Número)");
                                }
                            }
                            else {
                                    System.out.println("Se esperaba .. de arreglo");
                            }
                        }
                        else {
                                System.out.println("Se esperaba comienzo de arreglo 1");
                        }
                    }
                    else {

                            System.out.println("Se esperaba apertura de arreglo [");
                    }
                }
                else {

                        System.out.println("Se esperaba un tipo de dato");
                }
            }
            else {
                    System.out.println("Se esperaba :");
            }
        }
        else if(Token.matches(comentarioLinea)){
            System.out.println("");
            inicializaBanderas_Var();
        }
        else if(Token.matches(comentarioBloque)){
            System.out.println("");
            inicializaBanderas_Var();
        }
        auxVar=auxVar+1;
    }


    boolean esperaFin_Proc=false;
    boolean esperaFin_Func=false;
    boolean esperaFin_Para=false;

    public void Asignacion_Var(String Token, Integer Linea){
        String Identificador = "([A-Z]{1,1}[a-zA-Z0-9]{2,254})";
        String tipoDato = "(bool|entero|largo|byte|string|flotante)";

        System.out.println("auxVar = "+auxVar);

        //Se ocupa agregar para que se agregue dentro de cada validación, ya que cambiar lo de rol y ambito
        Directorio_Tabla.add(System.getProperty("user.dir"));
        Linea_Tabla.add(Linea.toString());
        Token_Tabla.add(Token);
        Rol_Tabla.add("");
        Ambito_Tabla.add("");
        valorInicial_Tabla.add("");
        valorFinal_Tabla.add("");

        if(auxVar==0){
            if(Token.matches(Identificador)) {
                System.out.println("Identificador correcto");
                //cordY=200;
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;

                Linea_Tabla.add(Linea.toString());
                Token_Tabla.add(Token);
                Rol_Tabla.add("Identificador");
                if(esperaFin_Proc==true || esperaFin_Proc==true)
                    Ambito_Tabla.add("L");
                else
                    Ambito_Tabla.add("G");

                archivoCorregido+=Token+" ";
            }
            else if(Token.equals("funcion") && funcion_procedimientoProhibido==false) {//Si está activa que no pueda verificar la
                //declaración de una función o procedimiento, ya que está prohibido
                System.out.println("función correcto. Identificado");
                funcionDetectado=true;
                Directorio_Tabla.add(System.getProperty("user.dir"));
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                funcion_procedimientoProhibido=true;//Encendemos bandera.
                esperaFin_Func=true;

                Directorio_Tabla.add(System.getProperty("user.dir"));
                Linea_Tabla.add(Linea.toString());
                Token_Tabla.add(Token);
                Rol_Tabla.add("PR");
                Ambito_Tabla.add("Función");
                valorInicial_Tabla.add(" ");
                valorFinal_Tabla.add(" ");

                archivoCorregido+=Token+" ";
            }
            else if(Token.equals("procedimiento") && funcion_procedimientoProhibido==false) {//Si está activa que no pueda verificar la
                //declaración de una función o procedimiento, ya que está prohibido
                System.out.println("procedimiento correcto. Identificado");
                procedimientoDetectado=true;
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                funcion_procedimientoProhibido=true;//Encendemos bandera
                esperaFin_Proc=true;

                Directorio_Tabla.add(System.getProperty("user.dir"));
                Linea_Tabla.add(Linea.toString());
                Token_Tabla.add(Token);
                Rol_Tabla.add("PR");
                Ambito_Tabla.add("Procedimiento");
                valorInicial_Tabla.add(" ");
                valorFinal_Tabla.add(" ");

                archivoCorregido+=Token+" ";
            }
        }
        if(auxVar==1 && funcionDetectado==false && procedimientoDetectado==false){
            if(Token.equals(":")){
                System.out.println("Asignación correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;

                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba :");
                //ctrl.txtMensajes.appendText("\nSe esperaba :");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba :"+" En linea: "+Linea;
                archivoCorregido+=":"+" ";
            }
        }
        if(auxVar==2 && funcionDetectado==false && procedimientoDetectado==false){
            if(Token.matches(tipoDato)){
                System.out.println("Tipo de dato correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                if(Token.equals("entero"))
                    valorInicial_Tabla.add("0");
                else if(Token.equals("flotante"))
                    valorInicial_Tabla.add("0.0");
                if(Token.equals("bool"))
                    valorInicial_Tabla.add("0");
                if(Token.equals("largo"))
                    valorInicial_Tabla.add("0");
                if(Token.equals("byte"))
                    valorInicial_Tabla.add("0");
                if(Token.equals("string"))
                    valorInicial_Tabla.add("null");

                archivoCorregido+=Token+" ";
            }
            else if(Token.equals("arreglo")){
                System.out.println("Tipo de dato arreglo correcto");
                arregloDetectado=true;
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;

                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba tipo de dato");
                //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
            }
        }
        if(auxVar==3 && arregloDetectado==false && funcionDetectado==false && procedimientoDetectado==false){//Será para declaración normal
            if(Token.equals(";")){
                System.out.println("Separador correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                inicializaBanderas_Var();


                Directorio_Tabla.add(System.getProperty("user.dir"));
                Linea_Tabla.add(Linea.toString());
                Token_Tabla.add(Token);
                Rol_Tabla.add("Separador de instrucción");
                Ambito_Tabla.add("Local");
                valorInicial_Tabla.add(" ");
                valorFinal_Tabla.add(" ");

                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba ;");
                //ctrl.txtMensajes.appendText("\nSe esperaba ;");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba ;"+" En linea: "+(Linea-1);
                archivoCorregido+=";"+" ";
            }
        }
        if(auxVar==3 && arregloDetectado==true){//Arreglo detectado
            if(Token.equals("[")){
                System.out.println("Apertura de arreglo correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;

                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba [");
                //ctrl.txtMensajes.appendText("\nSe esperaba [");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba ["+" En linea: "+Linea;
                archivoCorregido+="["+" ";
            }
        }
        if(auxVar==4 && arregloDetectado==true){
            if(Token.equals("1")){
                System.out.println("Inicio de arreglo correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba inicio de arreglo 1");
                //ctrl.txtMensajes.appendText("\nSe esperaba inicio de arreglo 1");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba inicio de arreglo 1"+" En linea: "+Linea;
                archivoCorregido+="1"+" ";
            }
        }
        if(auxVar==5 && arregloDetectado==true){
            if(Token.equals("..")){
                System.out.println(".. correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba .. de arreglo ");
                //ctrl.txtMensajes.appendText("\nSe esperaba .. de arreglo");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba .. de arreglo"+" En linea: "+Linea;
                archivoCorregido+=".."+" ";
            }
        }
        if(auxVar==6 && arregloDetectado==true){
            if(Token.matches("[0-9]+")){
                System.out.println("Límite de arreglo correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba límite de arreglo");
                //ctrl.txtMensajes.appendText("\nSe esperaba limite de areglo");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba limite de arreglo"+" En linea: "+Linea;
            }
        }
        if(auxVar==7 && arregloDetectado==true){
            if(Token.equals("]")){
                System.out.println("Cierre de arreglo correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba ]");
                //ctrl.txtMensajes.appendText("\nSe esperaba ]");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba ]"+" En linea: "+Linea;
                archivoCorregido+="]"+" ";
            }
        }
        if(auxVar==8 && arregloDetectado==true){
            if(Token.equals("de")){
                System.out.println("de correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba de");
                //ctrl.txtMensajes.appendText("\nSe esperaba de");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba de"+" En linea: "+Linea;
                archivoCorregido+="de"+" ";
            }
        }
        if(auxVar==9 && arregloDetectado==true){
            if(Token.matches(tipoDato)){
                System.out.println("Tipo de arreglo correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                if(Token.equals("entero"))
                    valorInicial_Tabla.add("0");
                else if(Token.equals("flotante"))
                    valorInicial_Tabla.add("0.0");
                if(Token.equals("bool"))
                    valorInicial_Tabla.add("0");
                if(Token.equals("largo"))
                    valorInicial_Tabla.add("0");
                if(Token.equals("byte"))
                    valorInicial_Tabla.add("0");
                if(Token.equals("string"))
                    valorInicial_Tabla.add("null");
                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba tipo de dato de arreglo");
                //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato de arreglo");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba tipo de dato de arreglo"+" En linea: "+Linea;
            }
        }
        if(auxVar==10 && arregloDetectado==true){
            if(Token.equals(";")){
                System.out.println("Separador correcto");
                nombre_Nodo.add(Token);
                coordenada_X.add(450);
                coodernada_Y.add(cordY);
                cordY=cordY+100;
                inicializaBanderas_Var();

                Directorio_Tabla.add(System.getProperty("user.dir"));
                Linea_Tabla.add(Linea.toString());
                Token_Tabla.add(Token);
                Rol_Tabla.add("Separador de instrucción");
                Ambito_Tabla.add("Local");
                valorInicial_Tabla.add(" ");
                valorFinal_Tabla.add(" ");

                archivoCorregido+=Token+" ";
            }
            else {
                System.out.println("Se esperaba ;");
                //ctrl.txtMensajes.appendText("\nSe esperaba ;");
                errorSintactico_Encontrado_Parar=true;
                mensajeError_Sintactico="Se esperaba ;"+" En linea: "+(Linea-1);
                archivoCorregido+=";"+" ";
            }
        }


        //Función
        //funcion <identificador> ([ <tipodato> <identificador> [, <tipodato> <identificador>] ]) como <tipodato>
        // funcion Funcion ( entero Numero , string Cadena ) como flotante
        if(funcionDetectado==true) {//Solo entra si se detectó una función el auxVar==0
            if (auxVar == 1) {
                if (Token.matches(Identificador)) {
                    System.out.println("Identificador correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(450);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Identificador");
                    Ambito_Tabla.add("Local");
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba un identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba un identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba un identificador"+" En linea: "+Linea;
                }
            }
            if(auxVar==2){
                if (Token.equals("(")) {
                    System.out.println("Apertura correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(450);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba un (");
                    //ctrl.txtMensajes.appendText("\nSe esperaba un (");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba un ("+" En linea: "+Linea;
                    archivoCorregido+="("+" ";
                }
            }
            if(auxVar==3){
                if (Token.equals(")")) {//Sin parametros
                    System.out.println("Cerrado correcto");
                    funcion_sinParametros=true;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(450);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else if (Token.matches(tipoDato)) {//Parametro detectado. Puede ser uno o doble
                    System.out.println("Tipo de dato correcto");
                    funcion_parametroEncontrado=true;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(450);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                }
                else {
                    System.out.println("Se esperaba un tipo de dato o paréntesis de carrado");
                    //ctrl.txtMensajes.appendText("\nSe esperaba un tipo de dato o paréntesis de cerrado");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba tipo de dato o parentesis de cerrado"+" En linea: "+Linea;
                }
            }


            if(funcion_sinParametros==true){//No se encontraon parametros
                if(auxVar==4){
                    if (Token.equals("como")) {
                        System.out.println("Palabra de asignacion de funcion \"como\" correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba palabra de asignacion de funcion \"como\"");
                        //ctrl.txtMensajes.appendText("\nSe esperaba palabra de asignación de funcion \"como\"");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba palabra de asignacion de funcion \"como\""+" En linea: "+Linea;
                        archivoCorregido+="como"+" ";
                    }
                }
                if(auxVar==5){
                    if (Token.matches(tipoDato)) {
                        System.out.println("Tipo de dato correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        inicializaBanderas_Var();
                        funcionDetectado=false;
                        Directorio_Tabla.add(System.getProperty("user.dir"));
                        Linea_Tabla.add(Linea.toString());
                        Token_Tabla.add(Token);
                        Rol_Tabla.add("Identificador");
                        Ambito_Tabla.add("Local");
                        if(Token.equals("entero"))
                            valorInicial_Tabla.add("0");
                        else if(Token.equals("flotante"))
                            valorInicial_Tabla.add("0.0");
                        if(Token.equals("bool"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("largo"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("byte"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("string"))
                            valorInicial_Tabla.add("null");

                    }
                    else {
                        System.out.println("Se esperaba un tipo de dato");
                        //ctrl.txtMensajes.appendText("\nSe esperaba un tipo de dato");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
                    }
                }
            }


            if(funcion_parametroEncontrado==true){//Se encontró parametro
                if(auxVar==4){
                    if (Token.matches(Identificador)) {
                        System.out.println("Identificador correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        Directorio_Tabla.add(System.getProperty("user.dir"));
                        Linea_Tabla.add(Linea.toString());
                        Token_Tabla.add(Token);
                        Rol_Tabla.add("Identificador");
                        Ambito_Tabla.add("Local");
                    }
                    else {
                        System.out.println("Se esperaba un identificador");
                        //ctrl.txtMensajes.appendText("\nSe esperaba un identificador");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba un identificador"+" En linea: "+Linea;
                    }
                }
                if(auxVar==5){
                    if (Token.equals(")")) {
                        System.out.println("Parentesis de cerrado correcto");
                        funcion_unParametro=true;//Se procede a verificar el fin
                        funcion_parametroEncontrado=false;
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        archivoCorregido+=Token+" ";
                    }
                    if (Token.equals(",")) {
                        System.out.println("Coma de segundo parametro correcto");
                        funcion_dosParametros=true;//Se procede a verificar el otro parametro
                        funcion_parametroEncontrado=false;
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba una coma o  paréntesis de cerrado");
                        //ctrl.txtMensajes.appendText("\nSe esperaba una coma o paréntesis de cerrado");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba una coma o parentesis de cerrado"+" En linea: "+Linea;
                    }
                }
            }


            if(funcion_unParametro==true){//Se encontró un parametro
                if(auxVar==6){
                    if (Token.equals("como")) {
                        System.out.println("Palabra de asignacion de funcion \"como\" correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba palabra de asignacion de funcion \"como\"");
                        //ctrl.txtMensajes.appendText("\nSe esperaba palabra de asignación o de función \"como\"");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba una palabra de asignación o de funcion \"como\""+" En linea: "+Linea;
                        archivoCorregido+="como"+" ";
                    }
                }
                if(auxVar==7){
                    if (Token.matches(tipoDato)) {
                        System.out.println("Tipo de dato correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        inicializaBanderas_Var();
                        funcionDetectado=false;
                        if(Token.equals("entero"))
                            valorInicial_Tabla.add("0");
                        else if(Token.equals("flotante"))
                            valorInicial_Tabla.add("0.0");
                        if(Token.equals("bool"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("largo"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("byte"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("string"))
                            valorInicial_Tabla.add("null");
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba un tipo de dato");
                        //ctrl.txtMensajes.appendText("\nSe esperaba un tipo de dato");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
                    }
                }
            }


            if(funcion_dosParametros==true){//Se encontró otro parámetro. Fueorn dobles
                if(auxVar==6){
                    if (Token.matches(tipoDato)) {
                        System.out.println("Tipo de dato correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        if(Token.equals("entero"))
                            valorInicial_Tabla.add("0");
                        else if(Token.equals("flotante"))
                            valorInicial_Tabla.add("0.0");
                        if(Token.equals("bool"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("largo"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("byte"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("string"))
                            valorInicial_Tabla.add("null");
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba un tipo de dato");
                        //ctrl.txtMensajes.appendText("\nSe esperaba un tipo de dato");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
                    }
                }
                if(auxVar==7){
                    if (Token.matches(Identificador)) {
                        System.out.println("Identificador correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;

                        Directorio_Tabla.add(System.getProperty("user.dir"));
                        Linea_Tabla.add(Linea.toString());
                        Token_Tabla.add(Token);
                        Rol_Tabla.add("Identificador");
                        Ambito_Tabla.add("Local");
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba un identificador");
                        //ctrl.txtMensajes.appendText("\nSe esperaba un identificador");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba un identificador"+" En linea: "+Linea;
                    }
                }
                if(auxVar==8){
                    if (Token.equals(")")) {
                        System.out.println("Parentesis de cerrado correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esparaba parentesis de cerrado )");
                        //ctrl.txtMensajes.appendText("\nSe esperaba parentesis de cerrado )");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba parentesis de cerrado )"+" En linea: "+Linea;
                        archivoCorregido+=")"+" ";
                    }
                }
                if(auxVar==9){
                    if (Token.equals("como")) {
                        System.out.println("Palabra de asignacion de funcion \"como\" correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba palabra de asignacion de funcion \"como\"");
                        //ctrl.txtMensajes.appendText("\nSe esperaba palabra de asignación de función \"como\"");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba palabra de asignacion de funcion \"como\""+" En linea: "+Linea;
                        archivoCorregido+="como"+" ";
                    }
                }
                if(auxVar==10){
                    if (Token.matches(tipoDato)) {
                        System.out.println("Tipo de dato correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        inicializaBanderas_Var();
                        funcionDetectado=false;
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba un tipo de dato");
                        //ctrl.txtMensajes.appendText("\nSe esperaba un tipo de dato");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
                    }
                }
            }

        }




        //procedimiento Algo (entero Rand, string Cadena)
        if(procedimientoDetectado==true){//Solo entra si se detectó un procedimiento el auxVar==0
            if (auxVar == 1) {
                if (Token.matches(Identificador)) {
                    System.out.println("Identificador correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(450);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Identificador");
                    Ambito_Tabla.add("Local");
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba un identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba un identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
                }
            }
            if(auxVar==2){
                if (Token.equals("(")) {
                    System.out.println("Apertura correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(450);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba un (");
                    //ctrl.txtMensajes.appendText("\nSe esperaba un (");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba un ("+" En linea: "+Linea;
                    archivoCorregido+="("+" ";
                }
            }
            if(auxVar==3){
                if (Token.equals(")")) {//Sin parametros
                    System.out.println("Cerrado correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(450);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();
                    procedimientoDetectado=false;
                    archivoCorregido+=Token+" ";
                }
                else if (Token.matches(tipoDato)) {//Parametro detectado. Puede ser uno o doble
                    System.out.println("Tipo de dato correcto");
                    procedimiento_unParametro=true;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(450);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;

                    if(Token.equals("entero"))
                        valorInicial_Tabla.add("0");
                    else if(Token.equals("flotante"))
                        valorInicial_Tabla.add("0.0");
                    if(Token.equals("bool"))
                        valorInicial_Tabla.add("0");
                    if(Token.equals("largo"))
                        valorInicial_Tabla.add("0");
                    if(Token.equals("byte"))
                        valorInicial_Tabla.add("0");
                    if(Token.equals("string"))
                        valorInicial_Tabla.add("null");
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba un tipo de dato o paréntesis de carrado");
                    //ctrl.txtMensajes.appendText("\nSe esperaba un tipo de dato o paréntesis de cerrado");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba tipo de dato o parentesis de cerrado"+" En linea: "+Linea;
                }
            }

            if(procedimiento_unParametro==true){
                if (auxVar == 4) {
                    if (Token.matches(Identificador)) {
                        System.out.println("Identificador correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        Directorio_Tabla.add(System.getProperty("user.dir"));
                        Linea_Tabla.add(Linea.toString());
                        Token_Tabla.add(Token);
                        Rol_Tabla.add("Identificador");
                        Ambito_Tabla.add("Local");
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba un identificador");
                        //ctrl.txtMensajes.appendText("\nSe esperaba un identificador");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba un identificador"+" En linea: "+Linea;
                    }
                }
                if(auxVar==5){
                    if (Token.equals(")")) {
                        System.out.println("Parentesis de cerrado correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        inicializaBanderas_Var();
                        procedimientoDetectado=false;
                        archivoCorregido+=Token+" ";
                    }
                    if (Token.equals(",")) {
                        System.out.println("Coma de segundo parametro correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        procedimiento_dosParametro=true;//Se procede a verificar el otro parametro
                        procedimiento_unParametro=false;
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba una coma o  paréntesis de cerrado");
                        //ctrl.txtMensajes.appendText("\nSe esperaba una coma o paréntesis de cerrado");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba una coma o parentesis de cerrado"+" En linea: "+Linea;
                    }
                }
            }


            if(procedimiento_dosParametro==true){
                if(auxVar==6){
                    if (Token.matches(tipoDato)) {
                        System.out.println("Tipo de dato correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;

                        if(Token.equals("entero"))
                            valorInicial_Tabla.add("0");
                        else if(Token.equals("flotante"))
                            valorInicial_Tabla.add("0.0");
                        if(Token.equals("bool"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("largo"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("byte"))
                            valorInicial_Tabla.add("0");
                        if(Token.equals("string"))
                            valorInicial_Tabla.add("null");
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba un tipo de dato");
                        //ctrl.txtMensajes.appendText("\nSe esperaba un tipo de dato");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
                    }
                }
                if(auxVar==7){
                    if (Token.matches(Identificador)) {
                        System.out.println("Identificador correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        Directorio_Tabla.add(System.getProperty("user.dir"));
                        Linea_Tabla.add(Linea.toString());
                        Token_Tabla.add(Token);
                        Rol_Tabla.add("Identificador");
                        Ambito_Tabla.add("Local");
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esperaba un identificador");
                        //ctrl.txtMensajes.appendText("\nSe esperaba un identificador");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba un identificador"+" En linea: "+Linea;
                    }
                }
                if(auxVar==8){
                    if (Token.equals(")")) {
                        System.out.println("Parentesis de cerrado correcto");
                        nombre_Nodo.add(Token);
                        coordenada_X.add(450);
                        coodernada_Y.add(cordY);
                        cordY=cordY+100;
                        inicializaBanderas_Var();
                        procedimientoDetectado=false;
                        archivoCorregido+=Token+" ";
                    }
                    else {
                        System.out.println("Se esparaba parentesis de cerrado )");
                        //ctrl.txtMensajes.appendText("\nSe esperaba paréntesis de cerrado");
                        errorSintactico_Encontrado_Parar=true;
                        mensajeError_Sintactico="Se esperaba parentesis de cerrado"+" En linea: "+Linea;
                        archivoCorregido+=")"+" ";
                    }
                }
            }

        }

        auxVar=auxVar+1;
    }

    boolean asignacionInicio=false;
    boolean invocacionInicio=false;
    boolean hazlo_siInicio=false;
    boolean repiteInicio=false;
    boolean si_Inicio=false;
    boolean leer=false;
    boolean escribir=false;
    boolean escribirSL=false;

    boolean para=false;


    //seecionInicio_Para funciones o procedimientos. Es copia de lo abajo

    int coordenadaX_VAR=450;


    public void secciónInicio(String Token, Integer Linea){//Puede ser usada pra los procedimientos y funciones
        //El for de Tokens se encarga de la declaración de cordX y cordY
        /*
        <bloque> ::= <asignacion> | <si> | <invocar_funcion> |  <invocar_procedimiento> | <para> | <repite> | <hazlo_si> | <encasode> | leer
            | escribir | <comentarios>
         */
        String Identificador = "([A-Z]{1,1}[a-zA-Z0-9]{2,254})";
        String tipoDato = "(bool|entero|largo|byte|string|flotante)";
        String comp = ("(<|>|>=|<=|==|<>)");
        String cientifico = "([0-9]+[E][+|-][0-9]+)";

        //Se ocupa agregar para que se agregue dentro de cada validación, ya que cambiar lo de rol y ambito
        //  valorFinal_Tabla.add("");   Aquí seria para el valor final


        System.out.println("auxVar - Inicio: "+auxVar);



        if(para==true && errorSintactico_Encontrado_Parar==false){//COMANDO PARA(FOR_CODIGO)
            if (auxVar == 0) {//para
                if (Token.equals("para")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("para correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba para");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba para"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {//Identificador
                if (Token.matches(Identificador)) {//Asignar, llamar función o llamar procedimiento
                    System.out.println("Identificador correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba Identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Identificador"+" En linea: "+Linea;
                }
            }
            if (auxVar == 2) {//Como
                if (Token.equals("como")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("como correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba como");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba como"+" En linea: "+Linea;
                }
            }
            if (auxVar == 3) {//Tipo numero largo
                if (Token.matches("[0-9]+") || Token.matches("[0-9]+[.][0-9]+")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("numero correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba numero");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba numero"+" En linea: "+Linea;
                }
            }
            if (auxVar == 4) {//:= Asignación
                if (Token.equals(":=")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println(":= correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba :=");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba :="+" En linea: "+Linea;
                }
            }
            if (auxVar == 5) {//Tipo numero largo
                if (Token.matches("[0-9]+") || Token.matches("[0-9]+[.][0-9]+")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("escribir correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba numero");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba numero"+" En linea: "+Linea;
                }
            }
            if (auxVar == 6) {//hasta
                if (Token.equals("hasta")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("hasta correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se epseraba hasta");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba hasta"+" En linea: "+Linea;
                }
            }
            if (auxVar == 7) {//Tipo numero largo
                if (Token.matches("[0-9]+") || Token.matches("[0-9]+[.][0-9]+")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("escribir correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba numero");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba numero"+" En linea: "+Linea;
                }
            }
            if (auxVar == 8) {//modo
                if (Token.equals("modo")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("modo correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperada modo");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba modo"+" En linea: "+Linea;
                }
            }
            if (auxVar == 9) {//incremento ++ decremento --
                if (Token.equals("++") || Token.equals("--")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("inc/dec correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                    para=false;
                    esperaFin_Para=true;
                    inicializaBanderas_Var();
                } else {
                    System.out.println("Se esperada inc(dec");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba inc/dec"+" En linea: "+Linea;
                }
            }
        }

        if(escribir==true && errorSintactico_Encontrado_Parar==false){
            if (auxVar == 0) {//Asignación
                if (Token.equals("escribir")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("escribir correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";



                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba escribir");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba escribir"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {
                if (Token.equals("(")) {
                    System.out.println("( correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba ( ");
                    //ctrl.txtMensajes.appendText("\nSe esperaba asiganación :=");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ( "+" En linea: "+Linea;
                    archivoCorregido+="("+" ";
                }
            }
            if (auxVar == 2) {
                if (Token.matches(Identificador) ) {
                    System.out.println("Tipo de dato correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba Identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Identificador"+" En linea: "+Linea;
                }
            }
            if(auxVar == 3){
                if(Token.equals(")")){
                    System.out.println(") correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;


                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ) "+" En linea: "+Linea;
                    archivoCorregido+=")"+" ";
                }
            }
            if(auxVar == 4){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    asignacionInicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();
                    escribir=false;


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ;"+" En linea: "+(Linea-1);
                    archivoCorregido+=";"+" ";
                }
            }
        }

        if(escribirSL==true && errorSintactico_Encontrado_Parar==false){
            if (auxVar == 0) {//Asignación
                if (Token.equals("escribirSL")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("escribirSL correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");

                } else {
                    System.out.println("Se esperaba escribirSL");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba escribirSL"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {
                if (Token.equals("(")) {
                    System.out.println("( correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba ( ");
                    //ctrl.txtMensajes.appendText("\nSe esperaba asiganación :=");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ( "+" En linea: "+Linea;
                    archivoCorregido+="("+" ";
                }
            }
            if (auxVar == 2) {
                if (Token.matches(Identificador) ) {
                    System.out.println("Tipo de dato correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba Identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Identificador"+" En linea: "+Linea;
                }
            }
            if(auxVar == 3){
                if(Token.equals(")")){
                    System.out.println(") correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;


                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ) "+" En linea: "+Linea;
                    archivoCorregido+=")"+" ";
                }
            }
            if(auxVar == 4){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    asignacionInicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();
                    escribirSL=false;


                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ;"+" En linea: "+(Linea-1);
                    archivoCorregido+=";"+" ";
                }
            }
        }

        if(leer==true && errorSintactico_Encontrado_Parar==false){
            if (auxVar == 0) {//Asignación
                if (Token.equals("leer")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("leer correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba leer");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba leer"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {
                if (Token.equals("(")) {
                    System.out.println("( correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba ( ");
                    //ctrl.txtMensajes.appendText("\nSe esperaba asiganación :=");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ( "+" En linea: "+Linea;
                    archivoCorregido+="("+" ";
                }
            }
            if (auxVar == 2) {
                if (Token.matches(Identificador) ) {
                    System.out.println("Tipo de dato correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba Identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Identificador"+" En linea: "+Linea;
                }
            }
            if(auxVar == 3){
                if(Token.equals(")")){
                    System.out.println(") correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;


                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada ) ");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ) "+" En linea: "+Linea;
                    archivoCorregido+=")"+" ";
                }
            }
            if(auxVar == 4){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    asignacionInicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();
                    leer=false;


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ;"+" En linea: "+(Linea-1);
                    archivoCorregido+=";"+" ";
                }
            }
        }

        if(asignacionInicio==true && errorSintactico_Encontrado_Parar==false) {//La bandera cambiar por que en el for verifico el token siguiente y si es := se activa la bandera
            if (auxVar == 0) {//Asignación
                if (Token.matches(Identificador)) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("Identificador correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba identificador"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {
                if (Token.equals(":=")) {
                    System.out.println("Asignación correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba asignación :=");
                    //ctrl.txtMensajes.appendText("\nSe esperaba asiganación :=");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba asignacion :="+" En linea: "+Linea;
                    archivoCorregido+=":="+" ";
                }
            }
            if (auxVar == 2) {
                if (Token.matches("[0-9]+") ) {
                    System.out.println("Tipo de dato correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }else if(Token.matches("[\"].*[\"]")){
                    System.out.println("Tipo de dato correcto. Tipo cadema");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else if(Token.matches(cientifico)){
                    System.out.println("Tipo de dato correcto. Tipo cientifico");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba tipo de dato");
                    //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
                }
            }
            if(auxVar == 3){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    asignacionInicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ;"+" En linea: "+(Linea-1);
                    archivoCorregido+=";"+" ";
                }
            }
        }

        //String comp = ("(<|>|>=|<=|==|<>)");
        if(hazlo_siInicio==true && errorSintactico_Encontrado_Parar==false){
            if(auxVar==0){
                if(Token.equals("hazlo_si")){
                    System.out.println("Inicio de hazlo si correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    //INICIOCON
                }
                else {
                    System.out.println("Se esperada instrucción hazlo_si");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba instrucción hazlo_si"+" En linea: "+Linea;
                }
            }
            if(auxVar==1){
                if(Token.matches("CONDICION")){//Condicón   (NUMERO/IDENT) COMP (NUMERO/IDENT)
                    System.out.println("Condicíón de hazlo_si correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();
                }
                else {
                    System.out.println("Se esperada condición de  hazlo_si");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba condición de  hazlo_si"+" En linea: "+(Linea-1);
                }
            }

        }

        if(invocacionInicio==true && errorSintactico_Encontrado_Parar==false) {//Invocar procedimiento o funcion
            if (auxVar == 0) {
                if (Token.matches(Identificador)) {
                    System.out.println("Identificador correcto. Función");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba identificador"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {
                if (Token.equals("(")) {
                    System.out.println("Parentesis de apertura correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba (");
                    //ctrl.txtMensajes.appendText("\nSe esperaba (");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ("+" En linea: "+Linea;
                }
            }
            if (auxVar == 2) {
                if (Token.equals(")")) {
                    System.out.println("Parentesis de cerrado correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba )");
                    //ctrl.txtMensajes.appendText("\nSe esperaba )");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba )"+" En linea: "+Linea;
                }
            }
            if (auxVar == 3) {
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    invocacionInicio = false;
                    inicializaBanderas_Var();

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                }
                else {
                    System.out.println("Se esperaba separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ;"+" En linea: "+(Linea-1);
                }
            }
        }

        if(si_Inicio==true && errorSintactico_Encontrado_Parar==false){
            if(auxVar==0){
                if(Token.equals("si")){
                    System.out.println("si correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba si");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba si"+" En linea: "+Linea;
                }
            }
            if(auxVar==1){
                if(Token.equals("(")){
                    System.out.println("( correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba (");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ("+" En linea: "+Linea;
                }
            }
            if(auxVar==2){
                if(Token.matches(Identificador)){
                    System.out.println("Identificador correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba Identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Identificador"+" En linea: "+Linea;
                }
            }
            if(auxVar==3){
                if(Token.matches(comp)){
                    System.out.println("comparador correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba comparador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba comparador"+" En linea: "+Linea;
                }
            }
            if(auxVar==4){
                if(Token.matches("[0-9]+")){
                    System.out.println("Numero correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                }else{
                    System.out.println("Se esperaba Numero");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Numero"+" En linea: "+Linea;
                }
            }
            if(auxVar==5){
                if(Token.equals(")")){
                    System.out.println(") correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba )");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba )"+" En linea: "+Linea;
                }
            }
            if(auxVar==6){
                if(Token.equals("entonces")){
                    System.out.println("entonces correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba entonces");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba entonces"+" En linea: "+Linea;
                }
            }
            if (auxVar == 7) {//Asignación
                if (Token.matches(Identificador)) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("Identificador correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba identificador"+" En linea: "+Linea;
                }
            }
            if (auxVar == 8) {
                if (Token.equals(":=")) {
                    System.out.println("Asignación correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba asignación :=");
                    //ctrl.txtMensajes.appendText("\nSe esperaba asiganción :=");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba asignación :="+" En linea: "+Linea;
                }
            }
            if (auxVar == 9) {
                if (Token.matches("[0-9]+")) {
                    System.out.println("Tipo de dato correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                }else if(Token.matches("[\"].*[\"]")){
                    System.out.println("Tipo de dato correcto. Tipo cadema");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                }
                else if(Token.matches(cientifico)){
                    System.out.println("Tipo de dato correcto. Tipo cientifico");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba tipo de dato");
                    //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
                }
            }
            if(auxVar == 10){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    asignacionInicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    //inicializaBanderas_Var();

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ; "+" En linea: "+(Linea);
                }
            }
            if(auxVar==11){
                if(Token.equals("finsi")){
                    System.out.println("finsi correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba finsi");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba finsi"+" En linea: "+Linea;
                }
            }
            if(auxVar == 12){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    si_Inicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ; "+" En linea: "+(Linea-1);
                }
            }
        }



        auxVar=auxVar+1;
    }

    public void secciónInicio_VAR(String Token, Integer Linea){//Puede ser usada pra los procedimientos y funciones
        //El for de Tokens se encarga de la declaración de cordX y cordY
        /*
        <bloque> ::= <asignacion> | <si> | <invocar_funcion> |  <invocar_procedimiento> | <para> | <repite> | <hazlo_si> | <encasode> | leer
            | escribir | <comentarios>
         */
        String Identificador = "([A-Z]{1,1}[a-zA-Z0-9]{2,254})";
        String tipoDato = "(bool|entero|largo|byte|string|flotante)";
        String comp = ("(<|>|>=|<=|==|<>)");
        String cientifico = "([0-9]+[E][+|-][0-9]+)";


        //Se ocupa agregar para que se agregue dentro de cada validación, ya que cambiar lo de rol y ambito



        System.out.println("auxVar - Inicio: "+auxVar);



        if(para==true && errorSintactico_Encontrado_Parar==false){//COMANDO PARA(FOR_CODIGO)
            if (auxVar == 0) {//para
                if (Token.equals("para")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("para correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba para");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba para"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {//Identificador
                if (Token.matches(Identificador)) {//Asignar, llamar función o llamar procedimiento
                    System.out.println("Identificador correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba Identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Identificador"+" En linea: "+Linea;
                }
            }
            if (auxVar == 2) {//Como
                if (Token.equals("como")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("como correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba como");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba como"+" En linea: "+Linea;
                }
            }
            if (auxVar == 3) {//Tipo numero largo
                if (Token.matches("[0-9]+") || Token.matches("[0-9]+[.][0-9]+")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("numero correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba numero");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba numero"+" En linea: "+Linea;
                }
            }
            if (auxVar == 4) {//:= Asignación
                if (Token.equals(":=")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println(":= correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba :=");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba :="+" En linea: "+Linea;
                }
            }
            if (auxVar == 5) {//Tipo numero largo
                if (Token.matches("[0-9]+") || Token.matches("[0-9]+[.][0-9]+")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("escribir correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba numero");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba numero"+" En linea: "+Linea;
                }
            }
            if (auxVar == 6) {//hasta
                if (Token.equals("hasta")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("hasta correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se epseraba hasta");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba hasta"+" En linea: "+Linea;
                }
            }
            if (auxVar == 7) {//Tipo numero largo
                if (Token.matches("[0-9]+") || Token.matches("[0-9]+[.][0-9]+")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("escribir correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba numero");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba numero"+" En linea: "+Linea;
                }
            }
            if (auxVar == 8) {//modo
                if (Token.equals("modo")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("modo correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperada modo");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba modo"+" En linea: "+Linea;
                }
            }
            if (auxVar == 9) {//incremento ++ decremento --
                if (Token.equals("++") || Token.equals("--")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("inc/dec correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                    para=false;
                    esperaFin_Para=true;
                    inicializaBanderas_Var();
                } else {
                    System.out.println("Se esperada inc(dec");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba inc/dec"+" En linea: "+Linea;
                }
            }
        }


        if(escribir==true && errorSintactico_Encontrado_Parar==false){
            if (auxVar == 0) {//Asignación
                if (Token.equals("escribir")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("escribir correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba escribir");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba escribir"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {
                if (Token.equals("(")) {
                    System.out.println("( correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba ( ");
                    //ctrl.txtMensajes.appendText("\nSe esperaba asiganación :=");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ( "+" En linea: "+Linea;
                    archivoCorregido+="("+" ";
                }
            }
            if (auxVar == 2) {
                if (Token.matches(Identificador) ) {
                    System.out.println("Tipo de dato correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba Identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Identificador"+" En linea: "+Linea;
                }
            }
            if(auxVar == 3){
                if(Token.equals(")")){
                    System.out.println(") correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;


                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ) "+" En linea: "+Linea;
                    archivoCorregido+=")"+" ";
                }
            }
            if(auxVar == 4){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    asignacionInicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();
                    escribir=false;


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ;"+" En linea: "+(Linea-1);
                    archivoCorregido+=";"+" ";
                }
            }
        }

        if(escribirSL==true && errorSintactico_Encontrado_Parar==false){
            if (auxVar == 0) {//Asignación
                if (Token.equals("escribirSL")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("escribirSL correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba escribirSL");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba escribirSL"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {
                if (Token.equals("(")) {
                    System.out.println("( correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba ( ");
                    //ctrl.txtMensajes.appendText("\nSe esperaba asiganación :=");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ( "+" En linea: "+Linea;
                    archivoCorregido+="("+" ";
                }
            }
            if (auxVar == 2) {
                if (Token.matches(Identificador) ) {
                    System.out.println("Tipo de dato correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba Identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Identificador"+" En linea: "+Linea;
                }
            }
            if(auxVar == 3){
                if(Token.equals(")")){
                    System.out.println(") correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;


                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ) "+" En linea: "+Linea;
                    archivoCorregido+=")"+" ";
                }
            }
            if(auxVar == 4){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    asignacionInicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();
                    escribirSL=false;


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ;"+" En linea: "+(Linea-1);
                    archivoCorregido+=";"+" ";
                }
            }
        }

        if(leer==true && errorSintactico_Encontrado_Parar==false){
            if (auxVar == 0) {//Asignación
                if (Token.equals("leer")) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("leer correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba leer");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba leer"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {
                if (Token.equals("(")) {
                    System.out.println("( correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                } else {
                    System.out.println("Se esperaba ( ");
                    //ctrl.txtMensajes.appendText("\nSe esperaba asiganación :=");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ( "+" En linea: "+Linea;
                    archivoCorregido+="("+" ";
                }
            }
            if (auxVar == 2) {
                if (Token.matches(Identificador) ) {
                    System.out.println("Tipo de dato correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba Identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Identificador"+" En linea: "+Linea;
                }
            }
            if(auxVar == 3){
                if(Token.equals(")")){
                    System.out.println(") correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;



                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada ) ");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ) "+" En linea: "+Linea;
                    archivoCorregido+=")"+" ";
                }
            }
            if(auxVar == 4){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    asignacionInicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();
                    escribir=false;


                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                    archivoCorregido+=Token+" ";
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ;"+" En linea: "+(Linea-1);
                    archivoCorregido+=";"+" ";
                }
            }
        }


        if(asignacionInicio==true && errorSintactico_Encontrado_Parar==false) {//La bandera cambiar por que en el for verifico el token siguiente y si es := se activa la bandera
            if (auxVar == 0) {//Asignación
                if (Token.matches(Identificador)) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("Identificador correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba identificador"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {
                if (Token.equals(":=")) {
                    System.out.println("Asignación correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba asignación :=");
                    //ctrl.txtMensajes.appendText("\nSe esperaba asiganción :=");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba asignación :="+" En linea: "+Linea;
                }
            }
            if (auxVar == 2) {
                if (Token.matches("[0-9]+")) {
                    System.out.println("Tipo de dato correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                }else if(Token.matches("[\"].*[\"]")){
                    System.out.println("Tipo de dato correcto. Tipo cadema");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                }
                else if(Token.matches(cientifico)){
                    System.out.println("Tipo de dato correcto. Tipo cientifico");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba tipo de dato");
                    //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
                }
            }
            if(auxVar == 3){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    asignacionInicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ; "+" En linea: "+(Linea-1);
                }
            }
        }

        if(hazlo_siInicio==true && errorSintactico_Encontrado_Parar==false){
            if(auxVar==0){
                if(Token.equals("hazlo_si")){
                    System.out.println("Inicio de hazlo si correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    //INICIOCON
                }
                else {
                    System.out.println("Se esperada instrucción hazlo_si");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba instrucción hazlo_si"+" En linea: "+Linea;
                }
            }
            if(auxVar==1){
                if(Token.equals("CONDICION")){//Condicón
                    System.out.println("Condicíón de hazlo_si correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(550);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();
                }
                else {
                    System.out.println("Se esperada condición de  hazlo_si");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba condición de  hazlo_si"+" En linea: "+Linea;
                }
            }

        }

        if(invocacionInicio==true && errorSintactico_Encontrado_Parar==false) {//Invocar procedimiento o funcion
            if (auxVar == 0) {
                if (Token.matches(Identificador)) {
                    System.out.println("Identificador correcto. Función");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba identificador"+" En linea: "+Linea;
                }
            }
            if (auxVar == 1) {
                if (Token.equals("(")) {
                    System.out.println("Parentesis de apertura correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba (");
                    //ctrl.txtMensajes.appendText("\nSe esperaba (");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ("+" En linea: "+Linea;
                }
            }
            if (auxVar == 2) {
                if (Token.equals(")")) {
                    System.out.println("Parentesis de cerrado correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba )");
                    //ctrl.txtMensajes.appendText("\nSe esperaba )");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba )"+" En linea: "+Linea;
                }
            }
            if (auxVar == 3) {
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    invocacionInicio = false;
                    inicializaBanderas_Var();

                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                }
                else {
                    System.out.println("Se esperaba separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ;"+" En linea: "+(Linea-1);
                }
            }
        }


        if(si_Inicio==true && errorSintactico_Encontrado_Parar==false){
            if(auxVar==0){
                if(Token.equals("si")){
                    System.out.println("si correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba si");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba si"+" En linea: "+Linea;
                }
            }
            if(auxVar==1){
                if(Token.equals("(")){
                    System.out.println("( correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba (");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba ("+" En linea: "+Linea;
                }
            }
            if(auxVar==2){
                if(Token.matches(Identificador)){
                    System.out.println("Identificador correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba Identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Identificador"+" En linea: "+Linea;
                }
            }
            if(auxVar==3){
                if(Token.matches(comp)){
                    System.out.println("comparador correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba comparador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba comparador"+" En linea: "+Linea;
                }
            }
            if(auxVar==4){
                if(Token.matches("[0-9]+")){
                    System.out.println("Numero correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                }
                else if(Token.matches("[\"].*[\"]")){
                    System.out.println("Tipo de dato correcto. Tipo cadema");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                }
                else if(Token.matches(cientifico)){
                    System.out.println("Tipo de dato correcto. Tipo cientifico");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }else{
                    System.out.println("Se esperaba Numero");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba Numero"+" En linea: "+Linea;
                }
            }
            if(auxVar==5){
                if(Token.equals(")")){
                    System.out.println(") correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba )");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba )"+" En linea: "+Linea;
                }
            }
            if(auxVar==6){
                if(Token.equals("entonces")){
                    System.out.println("entonces correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba entonces");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba entonces"+" En linea: "+Linea;
                }
            }
            if (auxVar == 7) {//Asignación
                if (Token.matches(Identificador)) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("Identificador correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);//Para la temporal de var cambiar este valor
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba identificador");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba identificador"+" En linea: "+Linea;
                }
            }
            if (auxVar == 8) {
                if (Token.equals(":=")) {
                    System.out.println("Asignación correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                } else {
                    System.out.println("Se esperaba asignación :=");
                    //ctrl.txtMensajes.appendText("\nSe esperaba asiganción :=");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba asignación :="+" En linea: "+Linea;
                }
            }
            if (auxVar == 9) {
                if (Token.matches("[0-9]+")) {
                    System.out.println("Tipo de dato correcto");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                }else if(Token.matches("[\"].*[\"]")){
                    System.out.println("Tipo de dato correcto. Tipo cadema");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                }
                else if(Token.matches(cientifico)){
                    System.out.println("Tipo de dato correcto. Tipo cientifico");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    archivoCorregido+=Token+" ";
                }
                else {
                    System.out.println("Se esperaba tipo de dato");
                    //ctrl.txtMensajes.appendText("\nSe esperaba tipo de dato");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba tipo de dato"+" En linea: "+Linea;
                }
            }
            if(auxVar == 10){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    asignacionInicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    //inicializaBanderas_Var();

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ; "+" En linea: "+(Linea-1);
                }
            }
            if(auxVar==11){
                if(Token.equals("finsi")){
                    System.out.println("finsi correcto. Si");
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Pr");
                    Ambito_Tabla.add("G");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                } else {
                    System.out.println("Se esperaba sinsi");
                    //ctrl.txtMensajes.appendText("\nSe esperaba identificador");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba finsi"+" En linea: "+Linea;
                }
            }
            if(auxVar == 12){
                if(Token.equals(";")){
                    System.out.println("Separador correcto");
                    si_Inicio = false;
                    nombre_Nodo.add(Token);
                    coordenada_X.add(coordenadaX_VAR);
                    coodernada_Y.add(cordY);
                    cordY=cordY+100;
                    inicializaBanderas_Var();

                    Directorio_Tabla.add(System.getProperty("user.dir"));
                    Linea_Tabla.add(Linea.toString());
                    Token_Tabla.add(Token);
                    Rol_Tabla.add("Separador");
                    Ambito_Tabla.add("L");
                    valorInicial_Tabla.add(" ");
                    valorFinal_Tabla.add(" ");
                }
                else {
                    System.out.println("Se esperada separador ;");
                    //ctrl.txtMensajes.appendText("\nSe esperaba separador ;");
                    errorSintactico_Encontrado_Parar=true;
                    mensajeError_Sintactico="Se esperaba separador ; "+" En linea: "+(Linea-1);
                }
            }
        }



        auxVar=auxVar+1;
    }


    public void abrirArbol(){
        arbolSintactico arb = new arbolSintactico();
        arb.setArbol();
    }


}
