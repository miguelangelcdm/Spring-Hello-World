package com.example.demo.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Contacto;

@RestController
public class Vistas {
    public static List<Contacto> database = new ArrayList<Contacto>();
    public static int position = -1;
    public static int contador = 0;

    @GetMapping("/")
    public List<Contacto> index() {
        return Vistas.database;
    }

    @GetMapping("/find/{id}")
    public Contacto find(@PathVariable int id) {
        this.locate(id);
        if (position < 0) {
            return null;
        } else {
            Contacto contacto = Vistas.database.get(position);
            position = -1;
            return contacto;
        }
    }

    @PostMapping("/update/{id}")
    public Contacto update(@PathVariable int id, @RequestBody Contacto contacto) {
        contacto.id = id;
        this.locate(id);
        if (position < 0) {
            return null;
        } else {
            Vistas.database.set(position, contacto);
            position = -1;
            return contacto;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        this.locate(id);
        if (position < 0) {
            return "no se encontro el contacto";
        } else {
            Vistas.database.remove(position);
            position = -1;
            return "contacto eliminado" + id;
        }
    }

    @PostMapping("/save")
    public Contacto save(@RequestBody Contacto contacto) {
        Random rm = new Random();
        contacto.id = rm.nextInt(1000);
        Vistas.database.add(contacto);
        return contacto;
    }

    // reutilizable para encontrar la posicion de un elemento en la lista
    public void locate(int id) {
        Vistas.database.forEach(data -> {
            if (data.id == id) {
                position = contador;
            }
            contador++;
        });
        contador = 0;
    }
}
