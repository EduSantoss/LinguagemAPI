package br.com.alura.linguagens.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
// @ sao mecanismos de anotaçao, outras linguagens tambem chamados de metadados. Sao uma forma de adicionar informações extras no codigo java
@RestController
public class LinguagemController {
     
    @Autowired
    private LinguagemRepository repositorio;
    //   private List<Linguagem> linguagens = 
    //     List.of(
    //        new Linguagem("java", "https://raw.githubusercontent.com/abrahamcalf/programming-languages-logos/master/src/java/java_256x256.png", 1),
    //        new Linguagem("php", "https://raw.githubusercontent.com/abrahamcalf/programming-languages-logos/master/src/php/php_256x256.png", 2)
    //   );

    //  @GetMapping(value = "/linguagem-preferida")
    //  public String processaLinguagemPreferida () {
    //      return "OI, java!";
    //   }

   // bancos de dados nao relacionais guardam documentos, sem tabelas e relaçoes
    @GetMapping(value = "/linguagens") // pegar do Banco de dados (nesse caso, mongoDB)
    public List<Linguagem> obterLinguagens() {
        List<Linguagem> linguagens = repositorio.findAll();
        return linguagens;
    }

    @PostMapping(value = "/linguagens")
    public Linguagem cadastrarLinguagem(@RequestBody Linguagem linguagem) {
         Linguagem linguagemSalva = repositorio.save(linguagem);
         return linguagemSalva;
    }
  //PathVariable pega o que esta na url do id
   @GetMapping(value = "/linguagens/{id}" )
    public Linguagem obterLinguagemPorId(@PathVariable String id) {
        return repositorio.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); //status de pagina nao encontrada 404

    }
  //RequestBody pega o que está no request/ tudo
    @PutMapping(value = "/linguagens/{id}")
    public Linguagem atualizarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagem) {
       if(!repositorio.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }    
       linguagem.setId(id); 
       Linguagem linguagemSalva = repositorio.save(linguagem);
       return linguagemSalva;
    }
    
    @DeleteMapping(value = "/linguagens/{id}")
    public void excluirLinguagem(@PathVariable String id) {
        repositorio.deleteById(id);
    }
} 
