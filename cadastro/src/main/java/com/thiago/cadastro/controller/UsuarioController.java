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

	 @Operation(description = "cadastra uma pessoa passando os campos")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "retorna a mensagem 'Cadastro realizado', "
				+ "indicando que a operação foi realizada"),
				@ApiResponse(responseCode = "400", description = "quer dizer que ocorreu um erro durante o cadastro, "
				+ "pode ser a falta de um campo ou a forma escrita")
})
	@PostMapping
	public String registerUsuario(@RequestBody UsuarioModel usuario) {
		return usuarioService.registerUsuario(usuario);
	}
	 
	 
	 @Operation(description = "busca uma pessoa por um id existente")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "retorna uma pessoa existente"),
				@ApiResponse(responseCode = "404", description = "este usuário não existe/id inexistente")
})
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable Long id) {
		return usuarioRepository.findById(id)
	            .map(user -> ResponseEntity.ok(new UsuarioDto(user)))
	            .orElse(ResponseEntity.notFound().build());
	    }
	
	 @Operation(description = "retorna todos os usuarios cadastrados no banco de dados")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "retorna todos os usuários cadastrados")
		})
	@GetMapping("/all")
	public List<UsuarioModel> getAllUsuarios(){
		return usuarioService.findAllUsuarios();
	}
	
	 @Operation(description = "deleta um usuário existete passando o id")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "deleta uma pessoa existente e retorna a mensagem 'Usuário deletado'"),
				@ApiResponse(responseCode = "404", description = "este usuário não existe/id inexistente")
		})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUsuario(@PathVariable Long id){
		usuarioService.deleteUsuario(id);
		return ResponseEntity.ok("Usuário deletado com sucesso!");
	}
	
	
	 @Operation(description = "atualiza um usuário existente passando o id")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "atualiza uma pessoa existente passando seu id e a retorna"),
				@ApiResponse(responseCode = "404", description = "se não passar ou passar um id existente,"
				+ "ele dará como não encontrado/não existente")
		})
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioModel> updateUsuario(@PathVariable Long id, @RequestBody UsuarioModel usuarioAtualizado){
		UsuarioModel usuario = usuarioService.updateUsuario(id, usuarioAtualizado);
		return ResponseEntity.ok(usuario); 
	}
}
}
