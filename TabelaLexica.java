
package compiladorlalgol;

/**
 *
 * @author Bruno Dourado Miranda
 */
public class TabelaLexica {
    Objeto inicio;
    Objeto fim;
    
    public TabelaLexica encadeamento(TabelaLexica t, String token, String identificador,int numeroLinha){
        if(t.inicio==null){
            t.inicio=new Objeto();
            t.inicio.token=token;
            t.inicio.numeroLinha=numeroLinha;
            t.inicio.identificador=identificador;
            t.fim=t.inicio;
        }else{
            t.fim.prox=new Objeto();
            t.fim=t.fim.prox;
            t.fim.identificador=identificador;
            t.fim.token=token;
            t.fim.numeroLinha=numeroLinha;
        }
        return t; 
    }
}
