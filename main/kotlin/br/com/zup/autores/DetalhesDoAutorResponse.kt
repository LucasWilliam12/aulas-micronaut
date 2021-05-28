package br.com.zup.autores

import io.micronaut.core.annotation.Introspected

@Introspected
data class DetalhesDoAutorResponse(val nome: String,
                                   val email: String,
                                   val descricao: String) {
    constructor(autor: Autor) : this(nome = autor.nome, email = autor.email, descricao = autor.descricao)

}
