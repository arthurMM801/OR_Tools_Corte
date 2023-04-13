# OR_Tools_Corte

Esse projeto ira resolver um probelma de Programação linear inteiro,
através do modelo de corte, sendo desenvolvido para solucionar-lo de forma genérica.

## Problema
Considere o problema de corte unidimensional onde barras de ferro de 150m
são utilizadas para produzir demandas de barras menores nos tamanhos de
80m, 60m e 50m. A produçãoao de cada um dos tipos de barra de ferro da
demanda deve ser de 70, 100, e 120 unidades respectivamente. Escreva um
modelo que minimize o desperdício na produção da demanda desejada.

## variáveis

Sendo xi a quantidade produzida de cada padrão i, com i = 1, 2, 3, 4 e 5.

Padrões:
p1: 80m – 60m (desperdício = 10m)</br>
p2: 80m – 50m (desperdício = 20m)</br>
p3: 60m – 60m (desperdício = 30m)</br>
p4: 60m – 50m (desperdício = 40m)</br>
p5: 50m – 50m – 50m (desperício = 0m)</br>

## Modelagem

minimizar 10x1 + 20x2 + 30x3 + 40x4 + 0x5</br>
sujeito a 1x1 + 1x2 ≥ 70</br>
1x1 + 2x3 + 1x4 ≥ 100</br>
1x2 + 1x4 + 3x5 ≥ 120</br>
x1, x2, x3, x4, x5 ≥ 0 e inteiras</br>
