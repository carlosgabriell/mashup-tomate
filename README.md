# 🍅 mashup-tomate

Serviço REST que integra os outros dois (preço e frete) e aplica regras comerciais para obter o valor final da venda e entrega de tomates.

## 🚀 Como rodar

```bash
cd mashup-tomate
mvn spring-boot:run
```

⚠️ Certifique-se de que os serviços `preco-tomate` (porta 8080) e `frete-tomate` (porta 8081) estejam rodando.

## ✅ Endpoint

- **POST /venda**  
  Recebe a quantidade de caixas e a distância em km. Retorna o valor final com lucro, desconto e imposto.

### 📥 Exemplo de entrada:

```json
{
  "quantidade": 300,
  "distancia": 150
}
```

### 📤 Exemplo de resposta:

```json
{
  "precoTomate": 11700.0,
  "frete": 1250.0,
  "veiculo": "carreta",
  "valorBruto": 12950.0,
  "lucro": 7122.5,
  "desconto": 1505.4375,
  "valorComImpostos": 23580.17
}
```