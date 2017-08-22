//Classe utilizada para analisar um arquivo texto que conterá o código de programação em LALGOL.
//Utiliza classe HashMap para armazenamento de palavras resevadas em uma tabela de símbolos.
package compiladorlalgol;

import static compiladorlalgol.CompiladorLALGOL.lista;
import static compiladorlalgol.CompiladorLALGOL.tabelaSimbolos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Bruno Dourado Miranda
 */
public class AnalisadorLexico {

    AnalisadorLexico() {
        //Utilizando o construtor da classe, é feita a povoação da tabela de palavras reservadas;
        // O HashMap tem custo de consulta O(1).
        
        tabelaSimbolos.put("+", "op_sum");
        tabelaSimbolos.put("-", "op_sub");
        tabelaSimbolos.put("*", "op_mul");          //Operações matemáticas;
        tabelaSimbolos.put("/", "op_div");

        tabelaSimbolos.put("program", "program");
        tabelaSimbolos.put("begin", "begin");       //Corpo;
        tabelaSimbolos.put("end", "end");
        tabelaSimbolos.put("procedure", "procedure");

        tabelaSimbolos.put("real", "type_real");
        tabelaSimbolos.put("integer", "type_int");  //Tipos de variáveis;
        tabelaSimbolos.put("var", "type_var");

        tabelaSimbolos.put("read", "cmd_read");     //Leitura e escrita;
        tabelaSimbolos.put("write", "cmd_write");

        tabelaSimbolos.put("while", "op_while");    //Laços;
        tabelaSimbolos.put("do", "op_do");

        tabelaSimbolos.put("(", "simb_open_colch");
        tabelaSimbolos.put(")", "simb_close_colch");
        tabelaSimbolos.put(";", "fim_cmd");         //Colchetes
        tabelaSimbolos.put(",", "simb_virg");
        tabelaSimbolos.put(".", "simb_point");

        tabelaSimbolos.put(":", "simb_pontos");
        tabelaSimbolos.put("=", "simb_atribu");     //Símbolos atribuição;
        tabelaSimbolos.put(">", "simb_maior");
        tabelaSimbolos.put("<", "simb_menor");
        tabelaSimbolos.put(":=", "simb_atribuicao");
        tabelaSimbolos.put("<>", "simb_diferenca");
        tabelaSimbolos.put(">=", "simb_mai_igual");
        tabelaSimbolos.put("<=", "simb_men_igual");

        tabelaSimbolos.put("if", "cmd_if");
        tabelaSimbolos.put("else", "cmd_else");     // Comparações;
        tabelaSimbolos.put("then", "cmd_then");

        tabelaSimbolos.put("!", "erro");
        tabelaSimbolos.put("@", "erro");
        tabelaSimbolos.put("#", "erro");
        tabelaSimbolos.put("%", "erro");
        tabelaSimbolos.put("¨", "erro");
        tabelaSimbolos.put("&", "erro");
        tabelaSimbolos.put("_", "erro");
        tabelaSimbolos.put("§", "erro");
        tabelaSimbolos.put("ª", "erro");
        tabelaSimbolos.put("º", "erro");
        tabelaSimbolos.put("/", "erro");
        tabelaSimbolos.put("¬", "erro");
        tabelaSimbolos.put("'", "erro");
        tabelaSimbolos.put("?", "erro");
        tabelaSimbolos.put(Character.toString('"'), "erro");

    }

    private String consulta(String token, int numeroLinha) {

        //Método que consulta a tabela de palavras reservadas na HashMap para armazenar os tokens inseridos.
        if (tabelaSimbolos.containsKey(token)) {
            lista = lista.encadeamento(lista, token, tabelaSimbolos.get(token), numeroLinha);
        } else if (Character.isDigit(token.charAt(0))) {//Se o primeiro caratere for um dígito... 
            boolean todosNumeros = true;
            char[] numeros = token.toCharArray();

            //Faz a varredura de todo o token para verificar se todos os caracteres são números.
            for (int i = 0; i < numeros.length; i++) {
                if (!Character.isDigit(numeros[i])) {
                    todosNumeros = false;
                    break;
                }
            }

            //Se todos os caracteres são números, então armazena o token como número, senão, como erro.
            if (todosNumeros) {
                lista = lista.encadeamento(lista, token, "num_int", numeroLinha);
            } else {
                lista = lista.encadeamento(lista, token, "erro", numeroLinha);
            }
        } else {
            //Senão, armazena o token como id;
            lista = lista.encadeamento(lista, token, "id", numeroLinha);
        }

        return "";
    }

    private boolean analisador(String linha, int numeroLinha, boolean comentario) {
        
        //O método cria um char array da linha lida e compara caractere a caractere. 
        char[] caracter = linha.toCharArray();
        String token = "";
        String atribuicoes = "";

        for (int i = 0; i < caracter.length; i++) {
            if (comentario == false && caracter[i] != ' ' && caracter[i] != '{') {//Se a linha lida não for um espaço e comentário
                if (tabelaSimbolos.containsKey(Character.toString(caracter[i]))) {
                    if (!(tabelaSimbolos.get(Character.toString(caracter[i])).equals("erro"))) {//Se encontra caractere reservado...

                        if (!token.isEmpty()) {
                            token = consulta(token, numeroLinha);//Se token ñ vazio, ele guarda.
                        }//Aí então, guarda-se o caractere reservado.

                        if (i + 1 < caracter.length && tabelaSimbolos.containsKey(Character.toString(caracter[i + 1])) && (caracter[i + 1] == '<' || caracter[i + 1] == '>' || caracter[i + 1] == '=') && !(tabelaSimbolos.get(Character.toString(caracter[i + 1])).equals("erro"))) {
                            atribuicoes = Character.toString(caracter[i]) + caracter[i + 1];
                            if (tabelaSimbolos.containsKey(atribuicoes)) {
                                lista = lista.encadeamento(lista, atribuicoes, tabelaSimbolos.get(atribuicoes), numeroLinha);
                            }
                            i++;
                        } else {
                            lista = lista.encadeamento(lista, Character.toString(caracter[i]), tabelaSimbolos.get(Character.toString(caracter[i])), numeroLinha);
                        }

                    } else {
                        token = token + caracter[i];
                        i++;//Se o caractere possuir um erro, então armazena todo o token como errado.
                        while (i < caracter.length && caracter[i] != ' ') {
                            token = token + caracter[i];
                            i++;
                        }
                        lista = lista.encadeamento(lista, token, "erro", numeroLinha);
                        token = "";
                    }
                } else {
                    token = token + caracter[i];//Senão, o caractere monta o token.
                }
            } else if (caracter[i] == '{' || comentario == true) {//Ignorando comentários.
                comentario = true;
                while (i < caracter.length && caracter[i] != '}') {
                    i++;
                }

                if (i < caracter.length) {
                    comentario = false;
                }

            } else if (token.length() > 0) {
                token = consulta(token, numeroLinha);
            }
        }
        if (!token.isEmpty()) {
            token = consulta(token, numeroLinha);
        }
        return comentario; //retorna true se comentário está ativo na linha.
    }

    public void leituraDoTexto() {

        FileNameExtensionFilter file = new FileNameExtensionFilter(".txt", "txt");

        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(file);
        fc.setDialogTitle("Importar código-fonte LALGOL");

        int resp = fc.showOpenDialog(null);

        if (resp == JFileChooser.APPROVE_OPTION) {
            File arquivo = new File(fc.getSelectedFile().getAbsolutePath());
            FileReader fr;
            
            boolean analisador = false;
            int numeroLinha = 0;
            
            
            try {
                lista.inicio = null;
                fr = new FileReader(arquivo);
                BufferedReader bfR = new BufferedReader(fr);
                
                while (bfR.ready()) {
                    numeroLinha++;
                    analisador = analisador(bfR.readLine(), numeroLinha, analisador);
                }
                
                lista= lista.encadeamento(lista, "######", "fim_arquivo", numeroLinha + 1);
                bfR.close();
                fr.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro na captura do código-fonte!");
            }
        }
            confereNumerosReais();
    }
    
    private void confereNumerosReais(){
        //Procura para juntar números reais, pois na hora de gravação na tabela, são por padrão todos os números separados.      
        Objeto obj=lista.inicio;
        Objeto aux=null;
        String str="";
        
        while(obj!=null){
            if(obj.identificador.equals("num_int")){
                if(obj.prox!=null && obj.prox.prox!=null && obj.prox.identificador.equals("simb_point")){
                    aux=obj;
                    obj=obj.prox.prox;
                    while(obj!=null && obj.identificador.equals("num_int")){
                        str=str+obj.token;
                        obj=obj.prox;
                    }
                    if(!str.isEmpty()){
                        aux.token=aux.token+"."+str;
                        aux.identificador="num_real";
                        aux.prox=obj;
                        str="";
                    }
                }
            }
            
            if(obj!=null){
                obj=obj.prox;
            }
        }
    }
}
