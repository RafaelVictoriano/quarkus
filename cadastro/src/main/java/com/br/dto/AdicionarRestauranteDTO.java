package com.br.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Valid
public class AdicionarRestauranteDTO {

    @CNPJ
    public String cnpj;

    @Size(min = 3, max = 30)
    public String nomeFantasia;

    public String proprietario;

    public LocalizacaoDTO localizacao;

}
