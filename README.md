<div align="center">
<img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
<img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" />
<img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white" />
<img src="https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
<img src="https://img.shields.io/badge/Render-46E3B7?style=for-the-badge&logo=render&logoColor=white" />
</div>

<br />

<div align="center">
<h1>🏎️ Forza Anpassen - API Engine</h1>
<p><strong>Ecossistema Backend para gestão de serviços automotivos.</strong></p>
<p>Uma solução escalável com autenticação híbrida, segurança JWT e comunicação via API transacional.</p>
</div>

<br />

## 🛠️ Tecnologias & Arquitetura
O projeto foi construído seguindo os padrões da indústria para aplicações Stateless e resilientes, otimizado para ambientes de nuvem.

* **Core:** Spring Boot 3.x & Java 17.
* **Segurança:** Spring Security + OAuth2 (Google/GitHub) + JWT.
* **Banco de Dados:** PostgreSQL hospedado.
* **Comunicação:** **Brevo API (Sib API V3)** - Integração via SDK para disparos de e-mails transacionais (bypass de bloqueio SMTP).
* **Deploy:** Infraestrutura PaaS no **Render**.
* **Resiliência:** Tratamento de exceções centralizado (`@RestControllerAdvice`) com retornos JSON padronizados.

<br />

## 🛡️ Segurança & Regras de Negócio
### 🔐 Autenticação e Privacidade
* **Token JWT:** Implementação de autenticação via Bearer Token/Cookies para sessões seguras.
* **Social Login:** Integração nativa com Google e GitHub via OAuth2.

### 🚫 Integridade do Fluxo
* **Controle de Duplicidade:** Filtros customizados impedem pedidos idênticos em análise.
* **RBAC (Role-Based Access Control):** Controle rigoroso de permissões para Usuários e Administradores.
* **Verificação em Duas Etapas:** Fluxo de registro e recuperação de senha protegidos por códigos dinâmicos via API de e-mail.

<br />

## 📍 Endpoints da API

### 🔑 Autenticação & Recuperação (Auth)
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
<tr>
<td><kbd>POST</kbd></td>
<td><code>/auth/forgot-password</code></td>
<td>Solicita código de recuperação de senha</td>
</tr>
<tr>
<td><kbd>POST</kbd></td>
<td><code>/auth/reset-password</code></td>
<td>Define nova senha usando o código recebido</td>
</tr>
</table>

<br />

### 🛠️ Catálogo de Serviços
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
<td>Lista todos os serviços disponíveis</td>
</tr>
<tr>
<td><kbd>POST</kbd></td>
<td><code>/services</code></td>
<td><b>Admin</b></td>
<td>Cadastro de novos serviços no catálogo</td>
</tr>
</table>

<br />

### 📋 Gestão de Pedidos (Orders)
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
<td>Solicita um novo serviço automotivo</td>
</tr>
<tr>
<td><kbd>GET</kbd></td>
<td><code>/orders/my</code></td>
<td><b>User</b></td>
<td>Histórico pessoal de pedidos do usuário logado</td>
</tr>
<tr>
<td><kbd>GET</kbd></td>
<td><code>/orders/all</code></td>
<td><b>Admin</b></td>
<td>Painel geral de solicitações para gestão</td>
</tr>
<tr>
<td><kbd>PATCH</kbd></td>
<td><code>/orders/{id}/status</code></td>
<td><b>Admin</b></td>
<td>Atualização do status (Pendente, Em Processo, Concluído)</td>
</tr>
<tr>
<td><kbd>DELETE</kbd></td>
<td><code>/orders/{id}</code></td>
<td><b>Admin</b></td>
<td>Remoção de registro do sistema</td>
</tr>
</table>

<br />
