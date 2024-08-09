# MyFood Delivery 🥗🌭🍰

## Índice
- [Subdomínios Principais](#subdomínios-principais)
    - [Customer (Cliente)](#customer-cliente)
    - [Catalog (Catálogo de Produtos)](#catalog-catálogo-de-produtos)
    - [Cart (Carrinho de Compras)](#cart-carrinho-de-compras)
    - [Order (Pedido)](#order-pedido)
    - [Delivery (Entrega)](#delivery-entrega)
- [Subdomínios Auxiliares](#subdomínios-auxiliares)
    - [Payment (Pagamento)](#payment-pagamento)
    - [Notification (Notificação)](#notification-notificação)
- [Interações entre Subdomínios](#interações-entre-subdomínios)
    - [Fluxo de Pedido](#fluxo-de-pedido)
- [Bounded Contexts](#bounded-contexts)
- [Considerações Finais](#considerações-finais)
 
## Subdomínios Principais

### Customer (Cliente) 🙋

Este subdomínio gerencia os dados e o relacionamento com os clientes.

- Entidades:
    - Customer: Representa o cliente, com informações como nome, e-mail, endereço e histórico de pedidos.
    - Address: Value Object que encapsula os dados de endereço do cliente.
    - PaymentMethod: Entidade que representa os métodos de pagamento associados ao cliente.


- Regras de Negócio:
  - Validação e atualização dos dados do cliente.
  - Gerenciamento dos métodos de pagamento.
  
### Catalog (Catálogo de Produtos) 📖

O subdomínio Catalog gerencia os produtos oferecidos pelos restaurantes, incluindo menus e itens de menu.

- Entidades:
  - Restaurant: Entidade que representa um restaurante no sistema.
  - Menu: Agregado que contém uma lista de itens de menu disponíveis em um restaurante.
  - MenuItem: Value Object que representa um item de menu, com informações como nome, descrição, preço e disponibilidade.
  

- Regras de Negócio:
    - Gerenciamento e atualização de menus e itens de menu.
    - Relacionamento entre restaurantes e seus respectivos menus.

### Cart (Carrinho de Compras) 🛒

Este subdomínio gerencia o carrinho de compras do cliente, onde os itens selecionados são armazenados antes de finalizar o pedido.

- Entidades:
  - Cart: Agregado que encapsula os itens que o cliente deseja comprar. Está associado a um cliente específico e contém os itens de menu selecionados.
  - CartItem: Value Object que representa um item no carrinho, relacionado a um item de menu do catálogo.
  

- Regras de Negócio:
    - Adicionar, remover e atualizar itens no carrinho.
    - Calcular o total do carrinho com base nos preços dos itens.
    - Validação da disponibilidade dos itens no momento do checkout. 

### Order (Pedido) ✍️

Este subdomínio gerencia o ciclo de vida dos pedidos feitos pelos clientes.

- Entidades:
  - Order: Agregado que representa um pedido confirmado, criado a partir do carrinho. Contém informações como itens do pedido, total, status, e cliente associado.
  - OrderItem: Value Object que representa um item no pedido, derivado do CartItem.
  

- Regras de Negócio:
    - Criação de pedidos a partir do carrinho.
    - Cálculo do total do pedido (incluindo taxas e descontos).
    - Gerenciamento do status do pedido (e.g., pendente, em preparação, a caminho, entregue).

### Delivery (Entrega) 📦

O subdomínio Delivery gerencia o processo de entrega dos pedidos.

- Entidades:
  - Delivery: Agregado que representa uma entrega específica, contendo informações sobre o pedido associado, entregador, e status da entrega. 
  - Driver: Entidade que representa o entregador.


- Regras de Negócio:
  - Atribuição de entregadores para pedidos.
  - Roteirização e otimização de entregas. 
  - Rastreamento e atualização do status de entrega. 

## Subdomínios Auxiliares
   
### Notification (Notificação) ✉️

Responsável por enviar notificações aos clientes e entregadores sobre o status dos pedidos e entregas.

- Entidades:
  - Notification: Entidade que encapsula os detalhes de uma notificação enviada. 
  - NotificationChannel: Value Object que representa o canal de comunicação (e.g., SMS, e-mail, push notification). 


- Regras de Negócio:
  - Envio de notificações automáticas baseadas em eventos.
  - Configuração de preferências de notificação.

## Interações entre Subdomínios

### Fluxo de Pedido
   
  - Customer seleciona os itens desejados do Catalog e os adiciona ao Cart.
  - O Cart calcula o total e mantém os itens até que o cliente esteja pronto para o checkout.
  - Quando o cliente confirma o pedido, um Order é criado a partir do Cart.
  - O Payment é processado e, se bem-sucedido, o pedido é confirmado.
  - O pedido é passado ao subdomínio Delivery, onde é atribuído a um Driver para entrega.
  - Notification envia atualizações ao cliente e ao entregador durante as diferentes etapas do pedido.

## Bounded Contexts

  - Customer Context: Gerencia informações e preferências dos clientes.
  - Catalog Context: Centraliza a lógica relacionada a produtos e menus.
  - Cart Context: Focado na gestão do carrinho de compras e validação de itens.
  - Order Context: Controla o ciclo de vida dos pedidos.
  - Delivery Context: Gerencia a logística de entrega.
  - Notification Context: Gerencia a comunicação com clientes e entregadores.
