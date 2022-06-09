package com.postman.rest.demopostman;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController //Esta annotation reemplaza a @Controller y @ResponseBody (para las nuevas versiones)
@RequestMapping("/personas")
public class PersonaRestController {

    private static List<Persona> personaList = new ArrayList<Persona>();

    static {
        Persona persona1 = new Persona("Megan", "Gonzales", 7);
        Persona persona2 = new Persona("Luis", "Frias", 34);
        Persona persona3 = new Persona("Maria", "Molina", 27);

        personaList.add(persona1);
        personaList.add(persona2);
        personaList.add(persona3);
    }

//    @RequestMapping("/personas")
//    @ResponseBody //Esta annotation permite mostrar todos los datos en formato JSON

//    @GetMapping
//    public List<Persona> listarTodos() {
//        return personaList;
//    }

    @GetMapping
    public List<PersonaDTO> listarTodos() {
        //A partir de la lista, la convertimos en un stream
        //Realizar una operacion de map
        //Utilizaremos un metodo de referencias sobre el constructor
        //Convertir en un stream de personaDTO
        //Usar un collector para generar la nueva lista, con base a lo anterior

        //return personaList.stream().map(PersonaDTO::new).collect(Collectors.toList());
        throw new RuntimeException("Falló el servicio", new Exception("No se tiene establecida la conexión a la BD"));
    }

    //    @RequestMapping("/personas/{nombre}")
//    @ResponseBody

//    @GetMapping("/{nombre}")
//    public Persona buscarUnaPersona(@PathVariable String nombre) {
//        return personaList.stream().filter(p -> p.getNombre().equals(nombre)).findFirst().orElse(null);
//    }

    @GetMapping("/{nombre}")
    public PersonaDTO buscarUnaPersona(@PathVariable String nombre) {
        return personaList.stream().filter(p -> p.getNombre().equals(nombre)).findFirst().map(PersonaDTO::new).orElse(null);
    }

//    @RequestMapping(value = "/personas/{nombre}", method = RequestMethod.DELETE)
//    @ResponseBody
    @DeleteMapping(value = "/{nombre}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //Cambia el estado a NO CONTIENE y su código es 204
    public void borrarUnaPersona(@PathVariable String nombre) {
        personaList.removeIf(p -> p.getNombre().equals(nombre));
    }

/*
    //Insertar persona y retornar la persona insertada
    @RequestMapping(value = "/personas", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED) //Cambia el valor del estado a 201 y da el mensaje de que el registro fue CREADO
    public Persona insertarPersona(@RequestBody Persona persona){
        personaList.add(persona);
        return persona;
    }
*/

//    @RequestMapping(value = "/personas", method = RequestMethod.POST)
//    @ResponseBody
    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED) //Cambia el valor del estado a 201 y da el mensaje de que el registro fue CREADO
    public ResponseEntity<Void> insertarPersona(@RequestBody Persona persona, UriComponentsBuilder builder) {
        personaList.add(persona);

        HttpHeaders headers = new HttpHeaders();
        UriComponents miUri = builder.path("/personas/{nombre}").buildAndExpand(persona.getNombre());
        headers.setLocation(miUri.toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //Cambia el valor del estado a 201 y da el mensaje de que el registro fue CREADO
    public ResponseEntity<PersonaDTO> insertarPersona(@RequestBody PersonaDTO personaDTO, UriComponentsBuilder builder) {
        Persona persona = new Persona(personaDTO.getNombre(), personaDTO.getApellidos(), personaDTO.getEdad());
        personaList.add(persona);

        HttpHeaders headers = new HttpHeaders();
        UriComponents miUri = builder.path("/personas/{nombre}").buildAndExpand(persona.getNombre());
        headers.setLocation(miUri.toUri());

        return new ResponseEntity<PersonaDTO>(headers, HttpStatus.CREATED);

    }


    /*@RequestMapping(value = "/personas/{nombre}", method = RequestMethod.PUT)
    @ResponseBody
    public void actualizarPersona(@PathVariable String nombre, @RequestBody Persona persona)
    {
        //Recibimos como parámetro la persona.
        //Usamos el constructor que tenemos para crear una nueva instancia sobre la base del parámetro asignado a la URL.
        int posicion = personaList.indexOf(new Persona(nombre));
        //Buscar a la persona en la lista a través del equals y hashCode
        //finalmente lo intercambiamos
        personaList.set(posicion, persona);
    }*/

//    @RequestMapping(value = "/personas/{nombre}", method = RequestMethod.PUT)
//    @ResponseBody
    /*@PutMapping(value = "/{nombre}")
    public ResponseEntity<Void> actualizarPersona(@PathVariable String nombre, @RequestBody Persona persona) {
        int posicion = personaList.indexOf(new Persona(nombre));

        if (posicion != -1)
        {
            personaList.set(posicion, persona);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else
        {
            personaList.add(persona);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }*/

    @PutMapping(value = "/{nombre}")
    public ResponseEntity<PersonaDTO> actualizarPersona(@PathVariable String nombre, @RequestBody PersonaDTO personaDTO) {
        Persona persona = new Persona(personaDTO.getNombre(), personaDTO.getApellidos(), personaDTO.getEdad());
        int posicion = personaList.indexOf(new Persona(nombre));

        if (posicion != -1)
        {
            personaList.set(posicion, persona);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else
        {
            personaList.add(persona);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @PatchMapping(value = "/{nombre}")
    public ResponseEntity<PersonaDTO> actualizarParcialPersona(@PathVariable String nombre, @RequestBody PersonaParcialDTO personaParcialDTO) {

        int posicion = personaList.indexOf(new Persona(nombre));

        Persona persona = personaList.get(posicion);

        persona.setApellidos(personaParcialDTO.getApellidos());
        persona.setEdad(personaParcialDTO.getEdad());

        if (posicion != -1)
        {
            personaList.set(posicion, persona);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}