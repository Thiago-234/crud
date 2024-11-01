package com.thiago.cadastro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiago.cadastro.Dto.UsuarioDto;
import com.thiago.cadastro.model.UsuarioModel;
import com.thiago.cadastro.repository.UsuarioRepository;
import com.thiago.cadastro.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cadastro")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	private final UsuarioRepository usuarioRepository;
	
	 public UsuarioController(UsuarioRepository usuarioRepository) {
	        this.usuarioRepository = usuarioRepository;
	    }
	
	@PostMapping
	public String registerUsuario(@RequestBody UsuarioModel usuario) {
		return usuarioService.registerUsuario(usuario);
	}
	@Operation(description = "busca uma pessoa por id")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna uma pessoa"),
			@ApiResponse(responseCode = "400", description = "Não existe a pessoa com este id")
	})
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable Long id) {
		return usuarioRepository.findById(id)
	            .map(user -> ResponseEntity.ok(new UsuarioDto(user)))
	            .orElse(ResponseEntity.notFound().build());
	    }
	
	@GetMapping("/all")
	public List<UsuarioModel> getAllUsuarios(){
		return usuarioService.findAllUsuarios();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUsuario(@PathVariable Long id){
		usuarioService.deleteUsuario(id);
		return ResponseEntity.ok("Usuário deletado com sucesso!");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioModel> updateUsuario(@PathVariable Long id, @RequestBody UsuarioModel usuarioAtualizado){
		UsuarioModel usuario = usuarioService.updateUsuario(id, usuarioAtualizado);
		return ResponseEntity.ok(usuario); //Retorna o usuário atualizado
	}
}
