package com.restaurante01.api_restaurante.modulos.avaliacao.dominio;

public enum StatusAvaliacao {
    PENDENTE {
        @Override
        public boolean podeTransacionarPara(StatusAvaliacao novo) {
            return true;
        }
    },
    DISPONIVEL{
       @Override
            public boolean podeTransacionarPara(StatusAvaliacao novo) {
                return true;
            }
        },
    CONCLUIDA{
        @Override
            public boolean podeTransacionarPara(StatusAvaliacao novo) {
                return true;
            }
        },
    EXPIRADA{
        @Override
            public boolean podeTransacionarPara(StatusAvaliacao novo) {
                return true;
            }
        };
public abstract boolean podeTransacionarPara(StatusAvaliacao novo);
}
