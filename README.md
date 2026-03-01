<div align="center">
<img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
<img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" />
<img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white" />
<img src="https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
</div>

<br />

<div align="center">
<h1>🏎️ forza Anpassen - API Engine</h1>
<p><strong>Ecossistema Backend para gestão de serviços automotivos.</strong></p>
<p>Uma solução escalável com autenticação híbrida, segurança JWT e proteção de dados.</p>
</div>

<br />

<br />

🛠️ Tecnologias & Arquitetura
O projeto foi construído seguindo os padrões da indústria para aplicações Stateless e resilientes.

Core: Spring Boot 3.x & Java 17.

Segurança: Spring Security + OAuth2 (Google/GitHub) + JWT.

Banco de Dados: PostgreSQL com Spring Data JPA & Hibernate.

Comunicação: Java Mail Sender (Integração SMTP para verificação de contas).

Resiliência: Tratamento de exceções centralizado (@RestControllerAdvice) com retornos JSON padronizados.

<br />

<br />

🛡️ Segurança & Regras de Negócio
🔐 Autenticação e Privacidade
Implementação de HttpOnly Cookies para o armazenamento de tokens, blindando a aplicação contra ataques XSS.

<br />

🚫 Integridade do Fluxo
Controle de Duplicidade: Filtros customizados impedem que um usuário realize múltiplos pedidos idênticos enquanto houver uma solicitação em análise.

RBAC (Role-Based Access Control): Controle rigoroso de permissões via roles.

Verificação de Conta: Fluxo de registro protegido por código de verificação enviado via e-mail.

<br />

<br />

📍 Endpoints da API
<br />

🔑 Autenticação (Auth)
<table>
<tr>
<th>Método</th>
<th>Endpoint</th>
<th>Descrição</th>
</tr>
<tr>
<td><kbd>POST</kbd></td>
<td><code>/auth/register</code></td>
<td>Cadastro de novo usuário</td>
</tr>
<tr>
<td><kbd>POST</kbd></td>
<td><code>/auth/login</code></td>
<td>Login tradicional e geração de token</td>
</tr>
<tr>
<td><kbd>POST</kbd></td>
<td><code>/auth/verify</code></td>
<td>Validação de conta via código de e-mail</td>
</tr>
</table>

<br />

🛠️ Catálogo de Serviços
<table>
<tr>
<th>Método</th>
<th>Endpoint</th>
<th>Acesso</th>
<th>Descrição</th>
</tr>
<tr>
<td><kbd>GET</kbd></td>
<td><code>/services</code></td>
<td>Público</td>
<td>Lista todos os serviços</td>
</tr>
<tr>
<td><kbd>POST</kbd></td>
<td><code>/services</code></td>
<td><b>Admin</b></td>
<td>Cadastro de novos serviços</td>
</tr>
</table>

<br />

📋 Gestão de Pedidos (Orders)
<table>
<tr>
<th>Método</th>
<th>Endpoint</th>
<th>Acesso</th>
<th>Descrição</th>
</tr>
<tr>
<td><kbd>POST</kbd></td>
<td><code>/orders</code></td>
<td><b>User</b></td>
<td>Solicita um novo serviço</td>
</tr>
<tr>
<td><kbd>GET</kbd></td>
<td><code>/orders/my</code></td>
<td><b>User</b></td>
<td>Histórico pessoal de pedidos</td>
</tr>
<tr>
<td><kbd>GET</kbd></td>
<td><code>/orders/all</code></td>
<td><b>Admin</b></td>
<td>Painel geral de solicitações</td>
</tr>
<tr>
<td><kbd>PATCH</kbd></td>
<td><code>/orders/{id}/status</code></td>
<td><b>Admin</b></td>
<td>Atualização do status do pedido</td>
</tr>
<tr>
<td><kbd>DELETE</kbd></td>
<td><code>/orders/{id}</code></td>
<td><b>Admin</b></td>
<td>Remoção de registro do sistema</td>
</tr>
</table>

<br />

<br />
