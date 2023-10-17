import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        int serverPort = 7896;
        ServerSocket listenSocket = null;
        Socket clientSocket = null;
        System.out.println("Servidor em execução");
        try {
            listenSocket = new ServerSocket(serverPort);
            while (true) {
                clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
                c.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket) throws IOException {
        clientSocket = aClientSocket;
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void run() {
        try {
            int figura = in.readInt();
            double area = 0;
            double base = 0;
            double altura = 0;
            double raio = 0;

            switch (figura) {
                case 1: // circulo
                    raio = in.readDouble();
                    area = calcularAreaCirculo(raio);
                    break;
                case 2: // triangulo
                    base = in.readDouble();
                    altura = in.readDouble();
                    area = calcularAreaTriangulo(base, altura);
                    break;
                case 3: // retangulo
                    base = in.readDouble();
                    altura = in.readDouble();
                    area = calcularAreaRetangulo(base, altura);
                    break;
                default:
                    System.out.println("Forma não identificada.");
                    break;
            }
            
            out.writeDouble(area);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private double calcularAreaCirculo(double raio) {
        return Math.PI * raio * raio;
    }

    private double calcularAreaTriangulo(double base, double altura) {
        return (base * altura) / 2;
    }

    private double calcularAreaRetangulo(double base, double altura) {
        return base * altura;
    }
}
