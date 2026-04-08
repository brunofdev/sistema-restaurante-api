package com.restaurante01.api_restaurante.pedido.Enum;

public enum StatusPedido {
    PENDENTE {
        @Override
        public boolean podeTransicionarPara(StatusPedido novo) {
            return novo == EM_PREPARACAO || novo == CANCELADO;
        }
    },
    EM_PREPARACAO {
        @Override
        public boolean podeTransicionarPara(StatusPedido novo) {
            return novo == SAIU_PARA_ENTREGA || novo == CANCELADO;
        }
    },
    SAIU_PARA_ENTREGA {
        @Override
        public boolean podeTransicionarPara(StatusPedido novo) {
            return novo == ENTREGUE || novo == CANCELADO;
        }
    },
    ENTREGUE {
        @Override
        public boolean podeTransicionarPara(StatusPedido novo) {
            return false;
        }
    },
    CANCELADO {
        @Override
        public boolean podeTransicionarPara(StatusPedido novo) {
            return false;
        }
    };

    public abstract boolean podeTransicionarPara(StatusPedido novo);
}