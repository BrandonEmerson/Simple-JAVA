
package main;
import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class ClienteArchivo {
    public static void main(String[] args) {
        try{
			//se lee la entrada del teclado
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escriba la direccion del servidor");
            String host = br.readLine();
            System.out.println("Escriba el puerto");
            int pto = Integer.parseInt(br.readLine());
			// se crea socket 
                        //socket de flujo se intenta conectar al servidor en cuanto se crea...
                        // el socket de datagrama, no
            Socket cl = new Socket(host,pto);
            
            
			//Se seleccionará un archivo abriendo la ventana
            JFileChooser jf = new JFileChooser();
            
            jf.setMultiSelectionEnabled(true);
            int r = jf.showOpenDialog(null);
			//cuando el usuario da aceptar....
            if(r == JFileChooser.APPROVE_OPTION){
                
                File[] f = jf.getSelectedFiles();
                
                
				//Enviar (out) y recibir (in) datos 
                
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                //DataInputStream dis = new DataInputStream(new FileInputStream(archivo));
				//escribe la cadena en UTF e inlcuye inf de la longitud
                                //envia los datos para la creacion del archivo
                                
                dos.writeInt(f.length);
                dos.flush();
                for (int i=0; i<f.length; i++){
                    String nf = f[i].getName();
                    dos.writeUTF(nf);
                    dos.flush();
                }
                /*
                dos.writeUTF(name);
                dos.flush();
                dos.writeLong(tam);
                dos.flush();
                                // Se crea buffer
                byte[] b = new byte[1024];
                long enviados=0;
                int porcentaje, n;
                //ciclo while hasta enviar todos los datos
                // lee el archivo, lo escribe y lo envia
                while (enviados < tam){
                    n= dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    enviados= enviados +n;
                    porcentaje= (int)(enviados*100/tam);
                    System.out.println("Enviados"+porcentaje+"%\r");
                }// while
                System.out.println("\n\nArchivo Enviado");
*/
                dos.close();
                //dis.close();
                cl.close();
            }//if
        }// try
        catch(Exception e){
            System.out.println(e);
        }//catch
    }//main    
}//class