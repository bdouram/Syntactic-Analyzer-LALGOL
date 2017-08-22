package compiladorlalgol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bruno Dourado Miranda 
 */
public class CompiladorLALGOL {

   public static Map<String,String> tabelaSimbolos = new HashMap(); //Tabela de palavras reservadas; 
   public static TabelaLexica lista= new TabelaLexica();// Lista final do analisador léxico;
   public static void main(String[] args) throws IOException {
       new TelaInicial().setVisible(true);   
    }
    
}
