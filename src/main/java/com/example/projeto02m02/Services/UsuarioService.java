package com.example.projeto02m02.Services;

import com.example.projeto02m02.Dtos.FarmaciaByIdDto;
import com.example.projeto02m02.Dtos.FarmaciasDto;
import com.example.projeto02m02.Dtos.UsuariosDto;
import com.example.projeto02m02.Entities.FarmaciaEntity;
import com.example.projeto02m02.Entities.MedicamentoEntity;
import com.example.projeto02m02.Entities.UsuarioEntity;
import com.example.projeto02m02.Repositories.UsuarioRepository;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    private UsuarioRepository repository;
    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }
    public void save(UsuarioEntity usuario){
        repository.save(usuario);
    }

    public ResponseEntity buscarUsuarios(){
        if(repository.findAll().isEmpty()){
            throw new NullPointerException();
        }else{
            List<UsuarioEntity> usuarios = repository.findAll();


            return new ResponseEntity(new UsuariosDto(Response.SC_FOUND,"Usuários escontrados!",usuarios), HttpStatus.FOUND);

        }
    }

    public ResponseEntity findById(Long id){
        if(repository.findById(id).isPresent()){
            Optional<UsuarioEntity> usuario = repository.findById(id);

            FarmaciaByIdDto usuarioDto = new FarmaciaByIdDto();

            return new ResponseEntity(new FarmaciaByIdDto(Response.SC_FOUND,"Usuário encontrado!",usuario), HttpStatus.FOUND);
        }else{
            throw new NullPointerException();
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = repository.findUserByLogin(username);

        if (usuario == null){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new User(usuario.getLogin(), usuario.getPassword(),  usuario.getAuthorities());
    }
    @Transactional(readOnly = true)
    public List<UsuarioEntity> getUsers(){
        List<UsuarioEntity> usuarios = repository.findAll();
        return usuarios;
    }
}
