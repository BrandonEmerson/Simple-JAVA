import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
/* Huffman coding , decoding */
package alumno;

public class Huffman {
    static final boolean readFromFile = true;
    static final boolean newTextBasedOnOldOne = true;

    static PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static TreeMap<Character, String> codes = new TreeMap<>();
    static String text = "";
    static String encoded = "";
    static String decoded = "";
    static String cadena = "";
    static int ASCII[] = new int[128];

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = (readFromFile) ? new Scanner(new File("original.txt")) : new Scanner(System.in);
        int decision = 1;
        while (decision != -1) {
            if (handlingDecision(scanner, decision)) continue;
            decision = consoleMenu(scanner);
        }
    }

    private static int consoleMenu(Scanner scanner) {
        int decision;
       // System.out.println("\n---- Menu ----\n" +
       //         "-- [-1] to exit \n" +
       //         "-- [1] to enter new text\n" +
       //         "-- [2] to encode a text\n" +
       //         "-- [3] to decode a text");
        decision = Integer.parseInt(scanner.nextLine());
        if (readFromFile)
            System.out.println("Decision: " + decision + "\n---- End of Menu ----\n");
        return decision;
    }

    private static boolean handlingDecision(Scanner scanner, int decision) {
        if (decision == 1) {
            if (handleNewText(scanner)) return true;
        } else if (decision == 2) {
            if (handleEncodingNewText(scanner)) return true;
        } else if (decision == 3) {
            handleDecodingNewText(scanner);
        }
        return false;
    }

    private static void handleDecodingNewText(Scanner scanner) {
        System.out.println("Enter the text to decode:");
        encoded = scanner.nextLine();
        System.out.println("Text to Decode: " + encoded);
        decodeText();
    }

    private static boolean handleEncodingNewText(Scanner scanner) {
        System.out.println("Enter the text to encode:");
        text = scanner.nextLine();
        System.out.println("Text to Encode: " + text);

        if (!IsSameCharacterSet()) {
            System.out.println("Not Valid input");
            text = "";
            return true;
        }
        encodeText();
        return false;
    }

    private static boolean handleNewText(Scanner scanner) {
        int oldTextLength = text.length();
        System.out.println("Enter the text:");
        text = scanner.nextLine();
        if (newTextBasedOnOldOne && (oldTextLength != 0 && !IsSameCharacterSet())) {
            System.out.println("Not Valid input");
            text = "";
            return true;
        }
            ASCII = new int[128];
            nodes.clear();
            codes.clear();
            encoded = "";
            decoded = "";
            System.out.println("Text: " + text);
            calculateCharIntervals(nodes, true);
            buildTree(nodes);
            generateCodes(nodes.peek(), "");

            printCodes();
            System.out.println("-- Encoding/Decoding --");
            encodeText();
            decodeText();
            return false;



    }

    private static boolean IsSameCharacterSet() {
        boolean flag = true;
        for (int i = 0; i < text.length(); i++)
            if (ASCII[text.charAt(i)] == 0) {
                flag = false;
                break;
            }
        return flag;
    }

    private static void decodeText() {
        decoded = "";
        Node node = nodes.peek();
        for (int i = 0; i < encoded.length(); ) {
            Node tmpNode = node;
            while (tmpNode.left != null && tmpNode.right != null && i < encoded.length()) {
                if (encoded.charAt(i) == '1')
                    tmpNode = tmpNode.right;
                else tmpNode = tmpNode.left;
                i++;
            }
            if (tmpNode != null)
                if (tmpNode.character.length() == 1)
                    decoded += tmpNode.character;
                else
                    System.out.println("Input not Valid");

        }
        System.out.println("Decoded Text: " + decoded);
        FileWriter fichero = null;
        PrintWriter pw = null;
        try{
        	fichero = new FileWriter("archivo_decodificado.txt");
        	pw = new PrintWriter(fichero);
        	pw.println(decoded);
        } catch (Exception e){
        	e.printStackTrace();
        } finally{
        	try{
        		if(null != fichero)
        			fichero.close();
        	}catch (Exception e2){
        		e2.printStackTrace();
        	}
        }
    }

    private static void encodeText() {
        encoded = "";
        for (int i = 0; i < text.length(); i++)
            encoded += codes.get(text.charAt(i));
        System.out.println("Encoded Text: " + encoded);
        FileWriter fichero = null;
        PrintWriter pw = null;
        try{
        	fichero = new FileWriter("archivo_codificado.txt");
        	pw = new PrintWriter(fichero);
        	pw.println(encoded);
        } catch (Exception e){
        	e.printStackTrace();
        } finally{
        	try{
        		if(null != fichero)
        			fichero.close();
        	}catch (Exception e2){
        		e2.printStackTrace();
        	}
        }
    }

    private static void buildTree(PriorityQueue<Node> vector) {
        while (vector.size() > 1)
            vector.add(new Node(vector.poll(), vector.poll()));
    }

    private static void printCodes() {
        System.out.println("--- Printing Codes ---");
        codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v) );
        codes.forEach((k, v) -> {cadena += "'" + k + "' : " + v +"\n"; });
        
        FileWriter fichero = null;
        PrintWriter pw = null;
        try{
        	fichero = new FileWriter("Codificacion.txt");
        	pw = new PrintWriter(fichero);
        	pw.println(cadena);
        } catch (Exception e){
        	e.printStackTrace();
        } finally{
        	try{
        		if(null != fichero)
        			fichero.close();
        	}catch (Exception e2){
        		e2.printStackTrace();
        	}
        }
    }

    private static void calculateCharIntervals(PriorityQueue<Node> vector, boolean printIntervals) {
       String frecuencies = "";
        if (printIntervals) //System.out.println("-- intervals --");
        for (int i = 0; i < text.length(); i++)
            ASCII[text.charAt(i)]++;

        for (int i = 0; i < ASCII.length; i++)
            if (ASCII[i] > 0) {
                vector.add(new Node(ASCII[i] / (text.length() * 1.0), ((char) i) + ""));
                //if (printIntervals)
                  //  System.out.println("'" + ((char) i) + "' : " + ASCII[i] / (text.length() * 1.0));
            }
        
        System.out.println("-- Frecuencies --");
        Contabilizador contabilizador = new Contabilizador();
        contabilizador.contabiliza(text);
        Map<String, Long> ocurrencias = contabilizador.getOcurrencias();
        Set<String> keys = ocurrencias.keySet();
        Iterator<String> iterator = keys.iterator();
        String key = null;
        while(iterator.hasNext()) {
            key = iterator.next();
            //System.out.println("Caracter: " + key + " Value: " + ocurrencias.get(key));
            frecuencies += "Caracter: " + key + " Value: " + ocurrencias.get(key) + "\n";
        }
        System.out.println(frecuencies);
        FileWriter fichero = null;
        PrintWriter pw = null;
        try{
        	fichero = new FileWriter("Frecuencias.txt");
        	pw = new PrintWriter(fichero);
        	pw.println(frecuencies);
        } catch (Exception e){
        	e.printStackTrace();
        } finally{
        	try{
        		if(null != fichero)
        			fichero.close();
        	}catch (Exception e2){
        		e2.printStackTrace();
        	}
        }
    }
    

    private static void generateCodes(Node node, String s) {
        if (node != null) {
            if (node.right != null)
                generateCodes(node.right, s + "1");

            if (node.left != null)
                generateCodes(node.left, s + "0");

            if (node.left == null && node.right == null)
                codes.put(node.character.charAt(0), s);
        }
    }
}

class Node {
    Node left, right;
    double value;
    String character;

    public Node(double value, String character) {
        this.value = value;
        this.character = character;
        left = null;
        right = null;
    }

    public Node(Node left, Node right) {
        this.value = left.value + right.value;
        character = left.character + right.character;
        if (left.value < right.value) {
            this.right = right;
            this.left = left;
        } else {
            this.right = left;
            this.left = right;
        }
    }
}
class Contabilizador {
    public Map<String, Long> ocurrencias; //Map porque son... UNICODE!
    public Contabilizador() {
        ocurrencias = new HashMap<String, Long>();
    }
    public void contabiliza(String linea) {
        for(int i=0;i<linea.length();i++) {
            if(null == ocurrencias.get(""+linea.charAt(i))) {
                ocurrencias.put(""+linea.charAt(i),1L);
            }else {
                Long valor = ocurrencias.get(""+linea.charAt(i));
                valor++;
                ocurrencias.put(""+linea.charAt(i),valor);
            }
        }
    }
    public Map<String, Long> getOcurrencias() {
        return ocurrencias;
    }
}
