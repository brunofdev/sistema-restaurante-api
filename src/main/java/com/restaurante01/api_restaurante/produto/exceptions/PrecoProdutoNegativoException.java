<<<<<<<< Updated upstream:src/main/java/com/restaurante01/api_restaurante/excepetions/PrecoProdutoNegativoException.java
package com.restaurante01.api_restaurante.excepetions;
========
package com.restaurante01.api_restaurante.produto.exceptions;
>>>>>>>> Stashed changes:src/main/java/com/restaurante01/api_restaurante/produto/exceptions/PrecoProdutoNegativoException.java

public class PrecoProdutoNegativoException extends RuntimeException {
    public PrecoProdutoNegativoException(String mensagem) {
        super(mensagem);
    }
}
