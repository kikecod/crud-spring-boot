package com.practica1.crud_estudiante.repository;

import com.practica1.crud_estudiante.model.Estudiante;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository  // Indica que esta clase es un repositorio y que se gestiona como un componente de Spring
public class EstudianteRepository {
    private final List<Estudiante> estudiantes = new ArrayList<>();  // Lista en memoria para almacenar los estudiantes
    private final AtomicLong idGenerator = new AtomicLong(1);  // Generador de IDs únicos para los estudiantes

    // Método para obtener todos los estudiantes
    public List<Estudiante> findAll() {
        return estudiantes;  // Devuelve la lista completa de estudiantes almacenados
    }

    // Método para obtener un estudiante por su ID
    public Optional<Estudiante> findById(Long id) {
        // Filtra la lista de estudiantes y busca aquel con el ID coincidente
        return estudiantes.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    // Método para guardar (crear o actualizar) un estudiante
    public Estudiante save(Estudiante estudiante) {
        // Si el estudiante no tiene ID, genera un nuevo ID utilizando el contador
        if (estudiante.getId() == null) {
            estudiante.setId(idGenerator.getAndIncrement());  // Asigna un ID único
        }
        estudiantes.add(estudiante);  // Añade el estudiante a la lista
        return estudiante;  // Devuelve el estudiante guardado
    }

    // Método para eliminar un estudiante por su ID
    public void deleteById(Long id) {
        // Elimina el estudiante de la lista cuya ID coincida con el ID proporcionado
        estudiantes.removeIf(e -> e.getId().equals(id));
    }
}
