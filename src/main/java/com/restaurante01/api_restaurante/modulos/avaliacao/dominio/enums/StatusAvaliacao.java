package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums;

public enum StatusAvaliacao {

    PENDENTE {
        @Override
        public boolean podeTransicionarPara(StatusAvaliacao novo) {
            return switch (novo) {
                case DISPONIVEL -> true;
                default -> false;
            };
        }
    },
    DISPONIVEL {
        @Override
        public boolean podeTransicionarPara(StatusAvaliacao novo) {
            return switch (novo) {
                case CONCLUIDA, EXPIRADA -> true;
                default -> false;
            };
        }
    },
    CONCLUIDA {
        @Override
        public boolean podeTransicionarPara(StatusAvaliacao novo) {
            return false; // estado final
        }
    },
    EXPIRADA {
        @Override
        public boolean podeTransicionarPara(StatusAvaliacao novo) {
            return false; // estado final
        }
    };

    public abstract boolean podeTransicionarPara(StatusAvaliacao novo);
}
