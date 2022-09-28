INSERT INTO cliente (id, nome_cliente, cpf, rua, cidade, cep, complemento) VALUES
(1l, 'Thiago', '123456', 'Rua Cliente 1', 'Belo Horizonte', '30410650', 'Ap 01'),
(2l, 'Luciene', '123457', 'Rua Cliente 2', 'Sabara', '000002', 'Ap 03'),
(3l, 'Diego', '123458', 'Rua Cliente 3', 'Contagem', '000003', 'Ap 03');

INSERT INTO restaurante (id, nome_restaurante, rua, cidade, cep, complemento) VALUES
(1l, 'Loja Sushi', 'Rua Loja Sushi', 'Belo Horizonte', '100001', 'Loja 01'),
(2l, 'Loja Acai', 'Rua Loja Acai', 'Sabara', '100002', 'Loja 02'),
(3l, 'Loja Pastel', 'Rua Loja Pastel', 'Contagem', '100003', 'Loja 03');

INSERT INTO produto (id, disponivel, nome_produto, valor_unitario, restaurante_id) VALUES
(1l, 1, 'Carioca Sushi', 2.0, 1l),
(2l, 1, 'Joy Joy Sushi', 3.0, 1l),
(3l, 1, 'Cupuaçu na Tigela', 20.0, 2l),
(4l, 1, 'Acai na Tigela', 25.0, 2l),
(5l, 1, 'Pastel de Palmito', 2.0, 3l),
(6l, 1, 'Pastel de Queijo', 2.0, 3l);

INSERT INTO sacola (id, forma_pagamento, status_sacola, valor_total_sacola, cliente_id, restaurante_id) VALUES
(1l, 'CARTAO', 'PEDIDO_EM_ANDAMENTO', 100.0, 2l, 3l);

INSERT INTO item (id, quantidade, produto_id, sacola_id) VALUES
(1l, 3, 5l, 1l);