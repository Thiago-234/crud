package com.thiago.cadastro.Dto;

import com.thiago.cadastro.model.UsuarioModel;

public record UsuarioDto(Long id, String nome, String nasc, String email, String senha) {
	
	    public UsuarioDto(UsuarioModel user) {
	    	this(user.getId(),
	    			user.getNome(),
	    			user.getNasc(),
	    		    user.getEmail(),
	    		    user.getSenha());
	}
}