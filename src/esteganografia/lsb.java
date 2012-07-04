/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esteganografia;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author guandaline
 */
public class lsb {

    public BufferedImage openFile(String file) {

        System.out.println("Carregando " + file + " ...");
        try {
            File inputFile = new File(file);
            BufferedImage bufi = ImageIO.read(inputFile);
            return bufi;
        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
        return null;

    }

    public String getStringBinary(String mensagem) {

        System.out.println("Codificando mensagem para Binario ...");
        try {
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < mensagem.length(); i++) {
                buf.append(getBinary(mensagem.charAt(i)));
            }
            return buf.toString();
        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
        return null;

    }

    public String getBinary(char caracter) {

        try {
            StringBuilder builder = new StringBuilder();
            String bits;
            bits = Integer.toBinaryString(caracter);
            for (int j = 0; j < 8 - bits.length(); j++) {
                builder.append(Integer.toString(0));
            }
            builder.append(bits);
            return builder.toString();

        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
        return null;
    }

    public int trocaCor(int cor, char bit) {
        try {
            char[] c_array = null;
            String nova_cor = null;
            String sbinaria = Integer.toBinaryString(cor);
            c_array = sbinaria.toCharArray();
            c_array[sbinaria.length() - 1] = bit;
            nova_cor = new String(c_array);
            return Integer.parseInt(nova_cor, 2);
        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
        return -1;
    }

    public void salvarImagem(BufferedImage img, int[] pixels, int w, int h, String novaimg) {

        try {

            System.out.println("Salvando " + novaimg + " ...");
            img.setRGB(0, 0, w, h, pixels, 0, w);
            ImageIO.write(img, "PNG", new File(novaimg));

        } catch (Exception E) {
            System.out.println(E.getMessage());
        }

    }

    public void InsreirMensagem(String arquivo, String destino, String mensagem) {

        try {

            int h, w, cor_atual, lin, col, tampixel;
            int red, green, blue, alpha;
            Color pixel = null;
            BufferedImage img = openFile(arquivo);
            String binario = getStringBinary("<m>" + mensagem + "</m>");
            cor_atual = 0;
            lin = 0;
            col = 0;

            red = green = blue = alpha = 0;

            h = img.getHeight();
            w = img.getWidth();
            int[] pixels = img.getRGB(0, 0, w, h, null, 0, w);

            pixel = new Color(img.getRGB(lin, col));

            tampixel = 2;
            System.out.println("Inserindo Mensagem na imagem ...");
            //System.out.println(binario.substring(0, 200));
            //retiraMesnsagem(binario);
            for (int i = 0; i < binario.length(); i++) {

                switch (cor_atual) {
                    case 0:
                        red = trocaCor(pixel.getRed(), binario.charAt(i));
                        //System.out.println("red =" + pixel.getRed() + " new red =" + red +" bin =" + binario.charAt(i));
                        break;
                    case 1:
                        green = trocaCor(pixel.getGreen(), binario.charAt(i));
                        //System.out.println("green =" + pixel.getGreen() + " new green =" + green +" bin =" + binario.charAt(i));
                        break;
                    case 2:
                        blue = trocaCor(pixel.getBlue(), binario.charAt(i));
                       // System.out.println("blue =" + pixel.getBlue() + " new blue =" + blue +" bin =" + binario.charAt(i));
                        break;
                    case 3:
                        alpha = trocaCor(pixel.getAlpha(), binario.charAt(i));
                        break;
                }
                
                cor_atual++;
                if (cor_atual > tampixel) {

                    pixel = new Color(img.getRGB(lin, col));
                    pixels[lin + col] = new Color(red, green, blue).getRGB();
                    cor_atual = 0;
                    col++;
                    if (col >= w) {
                        lin++;
                        col = 0;
                        if (lin >= h) {
                            break;
                        }
                    }
                }
                
            }

            if (cor_atual > 0) {
                pixels[lin + col] = new Color(red, green, blue).getRGB();
            }

            salvarImagem(img, pixels, w, h, destino);

        } catch (Exception E) {
            System.out.println(E.getMessage());
        }

    }

    public String montaMensagem(BufferedImage img) {

        try {
            System.out.println("Carregando o texto ...");
            Color c;
            int h, w;
            String red, green, blue;
            StringBuilder pixels = new StringBuilder();
            h = img.getHeight();
            w = img.getWidth();

            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    c = new Color(img.getRGB(j, i), true);

                    red = Integer.toBinaryString(c.getRed());
                    green = Integer.toBinaryString(c.getGreen());
                    blue = Integer.toBinaryString(c.getBlue());
                    pixels.append(red.charAt(red.length() - 1));
                    pixels.append(green.charAt(green.length() - 1));
                    pixels.append(blue.charAt(blue.length() - 1));

                }
            }

            return pixels.toString();

        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
        return null;
    }

    public String retiraMesnsagem(String texto) {
        try {
            System.out.println("Retirando mensagem do texto ...");
            //System.out.println(texto.substring(0, 200));
            StringBuilder msg = new StringBuilder();
            StringBuilder aux = new StringBuilder();
            int letra, ini, fim;
            int cont = 0;
            for (int i = 0; i < texto.length() - 1; i++) {
                //System.out.print(texto.charAt(i));
                aux.append(texto.charAt(i));
                cont++;
                if (cont == 8) {
                    letra = Integer.parseInt(aux.toString(), 2);
                    msg.append((char) letra);
                   // System.out.println(" " + (char) letra);
                    aux.delete(0, cont);
                    cont = 0;
                }
                
            }

            if (cont > 0) {
                letra = Integer.parseInt(aux.toString(), 2);
                msg.append((char) letra);
                //System.out.print(letra);
            }

            
              ini = msg.indexOf("<m>") + 3; 
              fim = msg.indexOf("</m>");
             // System.out.println("ini = " + ini + "fim = " + fim);
             // System.out.println(msg.substring(ini, fim));
             if(fim > -1)
                return msg.substring(ini, fim);
             else
                 return "Não pode ser lida mensagem nesta imagem. \n Verifique o formato da imagem.";
        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
        return null;
    }

    public String lerMensagem(String arquivo) {

        try {

            BufferedImage img = openFile(arquivo);
            String pixels = montaMensagem(img);
            String msg = retiraMesnsagem(pixels);
            System.out.println(msg);
            return msg;
        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
        return null;
    }
}
