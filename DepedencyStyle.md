# Introduction #

Este extension point permite que seja criado um plugin que defina o estilo a ser aplicado a um dado componente ou dependência.


# Details #

Quem estender este extension point terá que implementar a Interface DependencyStyle que o irá obrigar a fazer Override aos métodos definidos. Esses métodos servirão para definir o estilo da representação do componente em si e das dependencias com outros componentes.