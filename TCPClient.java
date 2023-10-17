import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {

        String hostname = "127.0.0.1";
        int serverPort = 7896;

        Socket s = null;

        try {
            s = new Socket(hostname, serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            Scanner scan = new Scanner(System.in);
            System.out.println("Escolha a figura: ");
            System.out.println("Circulo: digite 1\nTriangulo: digite 2\nRetangulo: digite 3");
            int figura = scan.nextInt();
            out.writeInt(figura);

            double raio = 0;
            double base = 0;
            double altura = 0;
            switch (figura) {
                case 1: // circulo
                    System.out.println("Digite o valor do raio: ");
                    raio = scan.nextDouble();
                    out.writeDouble(raio);
                    break;
                case 2: // triangulo
                    System.out.print("Digite a base: ");
                    base = scan.nextDouble();
                    System.out.print("Digite a altura: ");
                    altura = scan.nextDouble();
                    out.writeDouble(base);
                    out.writeDouble(altura);
                    break;
                case 3: // retangulo
                    System.out.print("Digite a base: ");
                    base = scan.nextDouble();
                    System.out.print("Digite a altura: ");
                    altura = scan.nextDouble();
                    out.writeDouble(base);
                    out.writeDouble(altura);
                    break;
                default:
                    System.out.println("Forma nao identificada.");
                    break;
            }

            double area = in.readDouble();
            System.out.println("Área: " + area);
            scan.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (s != null) {
                    
                    s.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
