package com.jessica.appjessica.controllers;

import com.jessica.appjessica.entities.people.Cliente;
import com.jessica.appjessica.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//localhost:8080/people/clientes/formClientes

@Controller
@RequestMapping("/people/clientes")
public class ClienteController {
    public static final String VISTA_FORMULARIO = "people/form-cliente";
    public static final String VISTA_LISTA = "people/form-lista_cliente";
    public static final String REDIRECT_FORMULARIO = "redirect:/people/clientes/formClientes";
    public static final String DELETE_SUCCESS = "Éxito";
    public static final String DELETE_SUCCESS_MESSAGE = "El cliente fue eliminado";
    public static final String DELETE_ERROR = "Error";
    public static final String DELETE_ERROR_MESSAGE = "Problemas al eliminar cliente";
    public static final String REDIRECT_LISTA = "redirect:/people/clientes/formListarClientes";

    @Autowired
    private IClienteService iClienteService;

    @GetMapping("/formClientes")
    public String formClientes(Model model) {
        model.addAttribute("formclientes", new Cliente());
        return VISTA_FORMULARIO;
    }

    @GetMapping("/formListarClientes")
    public String formListarClientes(Model model) {
        model.addAttribute("formlistarclientes", iClienteService.listarTodosLosClientes());
        return VISTA_LISTA;
    }

    @PostMapping("/grabarClientes")
    public String grabarClientes(@Validated @ModelAttribute("formclientes") Cliente cliente,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return VISTA_FORMULARIO;
        } else{
            iClienteService.grabarCliente(cliente);
        }
        return REDIRECT_FORMULARIO;
    }

    @DeleteMapping("/deleteClientes/{id}")
    public String deleteCliente(@PathVariable("id") int id, RedirectAttributes flash){
        if(id > 0){
            iClienteService.borrarClienteById(id);
            flash.addFlashAttribute(DELETE_SUCCESS, DELETE_SUCCESS_MESSAGE);
        } else {
            flash.addFlashAttribute(DELETE_ERROR, DELETE_ERROR_MESSAGE);
        }
        return REDIRECT_LISTA;
    }

    @GetMapping("/updateCliente/{id}")
    public String updateCliente(@PathVariable("id") int id, Model model){
        Cliente cliente;
        cliente = iClienteService.encontrarUnCliente(id);
        model.addAttribute("formclientes", cliente);
        return VISTA_FORMULARIO;
    }
}
