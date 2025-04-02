package com.practica1.crud_estudiante.controller;

import com.practica1.crud_estudiante.dto.EstudianteDTO;
import com.practica1.crud_estudiante.service.EstudianteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Anotación que indica que esta clase es un controlador REST
@RequestMapping("/api/estudiantes")  // Mapea la URL base del controlador a /api/estudiantes
public class EstudianteController {
    private final EstudianteService estudianteService;  // Se inyecta el servicio para gestionar estudiantes

    // Constructor para inyectar el servicio
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;  // Asigna el servicio al controlador
    }

    // Endpoint para obtener todos los estudiantes
    @GetMapping  // Mapea una solicitud GET para la URL /api/estudiantes
    public List<EstudianteDTO> getAllEstudiantes() {
        // Llama al servicio para obtener todos los estudiantes y devolverlos
        return estudianteService.getAllEstudiantes();
    }

    // Endpoint para obtener un estudiante por su ID
    @GetMapping("/{id}")  // Mapea una solicitud GET con un parámetro ID en la URL, por ejemplo /api/estudiantes/1
    public ResponseEntity<EstudianteDTO> getEstudianteById(@PathVariable Long id) {
        // Llama al servicio para buscar un estudiante por ID y devolver la respuesta correspondiente
        return estudianteService.getEstudianteById(id)
                .map(ResponseEntity::ok)  // Si se encuentra el estudiante, retorna 200 OK
                .orElse(ResponseEntity.notFound().build());  // Si no se encuentra, retorna 404 Not Found
    }

    // Endpoint para crear un nuevo estudiante
    @PostMapping  // Mapea una solicitud POST para la URL /api/estudiantes
    public ResponseEntity<EstudianteDTO> createEstudiante(@RequestBody EstudianteDTO estudianteDTO) {
        // Llama al servicio para crear un nuevo estudiante y devuelve la respuesta con el estado 201 Created
        return ResponseEntity.status(201).body(estudianteService.createEstudiante(estudianteDTO));
    }

    // Endpoint para actualizar un estudiante existente
    @PutMapping("/{id}")  // Mapea una solicitud PUT para la URL /api/estudiantes/{id}, donde {id} es el identificador del estudiante
    public ResponseEntity<EstudianteDTO> updateEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) {
        // Llama al servicio para actualizar el estudiante con el ID especificado y devuelve el estudiante actualizado
        return ResponseEntity.ok(estudianteService.updateEstudiante(id, estudianteDTO));
    }

    // Endpoint para eliminar un estudiante por su ID
    @DeleteMapping("/{id}")  // Mapea una solicitud DELETE para la URL /api/estudiantes/{id}
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
        // Llama al servicio para eliminar el estudiante con el ID especificado
        estudianteService.deleteEstudiante(id);
        // Retorna una respuesta 204 No Content indicando que la eliminación fue exitosa
        return ResponseEntity.noContent().build();
    }
}
