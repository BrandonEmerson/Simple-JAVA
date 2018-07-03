
package main;
import java.net.*;
import java.io.*;
public class ServidorArchivo {
    public static void main(String[] args) {
        try{
            ServerSocket s= new ServerSocket(7000);
            for(;;){
                //llega conexxion
                Socket cl=s.accept();
                System.out.println("Conexion establecida desde"+cl.getInetAddress()+":"+cl.getPort());
                DataInputStream dis=new DataInputStream(cl.getInputStream());
                //se define un buffer
                int num = dis.readInt();
                for (int i = 0; i < num; i++) {
                    String nombre= dis.readUTF();
                    System.out.println(nombre);
                }
                /*
                byte[] b = new byte[1024];
                //el tamaño de lo que se envia es el mismo del que se recibe
                String nombre= dis.readUTF();
                System.out.println("Recibimos el archivo"+nombre);
                
                long tam= dis.readLong();
                DataOutputStream dos= new DataOutputStream(new FileOutputStream(nombre));
                long recibidos=0;
                int n, porcentaje;
                // se lee del socket y se escribe el archivo
                while (recibidos < tam){
                    n= dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    recibidos=recibidos+n;
                    porcentaje= (int)(recibidos*100/tam);
                    System.out.println("Recibido:"+porcentaje+"%\r");
                }//while
*/
                //dos.close();
                dis.close();
                cl.close();
            }//for
        }//try
        catch(Exception e){
            e.printStackTrace();
        }
    }//main
}//class
