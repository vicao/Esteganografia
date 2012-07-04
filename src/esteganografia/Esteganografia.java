/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esteganografia;

import java.io.IOException;

/**
 *
 * @author guandaline
 */
public class Esteganografia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
         String msg = "Man's capacity exceeds your imagination!"; 
         lsb i = new lsb(); 
        //System.out.print(args[0]);
        //lsb i = new lsb();
        switch (args.length) {
            case 4:
                i.InsreirMensagem(args[1], args[2], args[3]);
                break;
            case 3:
                i.InsreirMensagem(args[0], args[1], args[2]);
                break;
            case 2:
                i.lerMensagem(args[1]);
                break;
            case 1:
                i.lerMensagem(args[0]);
                break;
            default:
                System.out.println("O programa deve ser executado com uma das segintes formas: ");
                System.out.println("Esteganografia inserir imagem_origem imagem_destino \"Mensagem deve vir entre aspas\"");
                System.out.println("Esteganografia imagem_origem imagem_destino \"Mensagem deve vir entre aspas\"");
                System.out.println("ou");
                System.out.println("Esteganografia retirar imagem_origem");
                System.out.println("Esteganografia imagem_origem");
                break;
        }
    }
}
