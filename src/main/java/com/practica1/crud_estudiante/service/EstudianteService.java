package com.practica1.crud_estudiante.service;

import com.practica1.crud_estudiante.dto.EstudianteDTO;
import com.practica1.crud_estudiante.model.Estudiante;
import com.practica1.crud_estudiante.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service  // La anotación @Service marca esta clase como un servicio en la capa de negocio
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;

    // Constructor que inyecta el repositorio para realizar operaciones sobre los estudiantes
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    // Método privado para convertir una entidad Estudiante a su versión DTO (Data Transfer Object)
    private EstudianteDTO convertirAEstudianteDTO(Estudiante estudiante) {
        return EstudianteDTO.builder()  // Utiliza el builder de EstudianteDTO para crear un nuevo objeto
                .id(estudiante.getId())  // Asigna los valores de los atributos de Estudiante
                .nombre(estudiante.getNombre())
                .apellido(estudiante.getApellido())
                .email(estudiante.getEmail())
                .fechaNacimiento(estudiante.getFechaNacimiento())
                .numeroInscripcion(estudiante.getNumeroInscripcion())
                .build();  // Devuelve el objeto EstudianteDTO
    }

    // Método privado para convertir un EstudianteDTO a la entidad Estudiante
    private Estudiante convertirAEstudiante(EstudianteDTO dto) {
        return Estudiante.builder()  // Utiliza el builder de Estudiante para crear una nueva instancia
                .id(dto.getId())  // Asigna los valores del DTO a la entidad Estudiante
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .fechaNacimiento(dto.getFechaNacimiento())
                .numeroInscripcion(dto.getNumeroInscripcion())
                .build();  // Devuelve el objeto Estudiante
    }

    // Método para obtener todos los estudiantes y convertirlos a EstudianteDTO
    public List<EstudianteDTO> getAllEstudiantes() {
        return estudianteRepository.findAll().stream()  // Obtiene todos los estudiantes desde el repositorio
                .map(this::convertirAEstudianteDTO)  // Convierte cada Estudiante a EstudianteDTO
                .collect(Collectors.toList());  // Recoge todos los resultados en una lista
    }

    // Método para obtener un estudiante por su ID, retornando un EstudianteDTO si se encuentra
    public Optional<EstudianteDTO> getEstudianteById(Long id) {
        return estudianteRepository.findById(id)  // Busca un estudiante por ID en el repositorio
                .map(this::convertirAEstudianteDTO);  // Si se encuentra, lo convierte a EstudianteDTO
    }

    // Método para crear un nuevo estudiante a partir de un DTO
    public EstudianteDTO createEstudiante(EstudianteDTO estudianteDTO) {
        Estudiante estudiante = convertirAEstudiante(estudianteDTO);  // Convierte el DTO a una entidad Estudiante
        estudiante = estudianteRepository.save(estudiante);  // Guarda el estudiante en el repositorio
        return convertirAEstudianteDTO(estudiante);  // Devuelve el estudiante guardado como EstudianteDTO
    }

    // Método para actualizar un estudiante existente
    public EstudianteDTO updateEstudiante(Long id, EstudianteDTO estudianteDTO) {
        Optional<Estudiante> estudianteOpt = estudianteRepository.findById(id);  // Busca el estudiante por ID
        if (estudianteOpt.isPresent()) {  // Si el estudiante existe
            Estudiante estudiante = estudianteOpt.get();  // Obtiene el estudiante
            // Actualiza los atributos del estudiante con los datos del DTO
            estudiante.setNombre(estudianteDTO.getNombre());
            estudiante.setApellido(estudianteDTO.getApellido());
            estudiante.setEmail(estudianteDTO.getEmail());
            estudiante.setFechaNacimiento(estudianteDTO.getFechaNacimiento());
            estudiante.setNumeroInscripcion(estudianteDTO.getNumeroInscripcion());
            return convertirAEstudianteDTO(estudiante);  // Devuelve el estudiante actualizado como DTO
        }
        throw new RuntimeException("Estudiante no encontrado");  // Si no se encuentra el estudiante, lanza una excepción
    }

    // Método para eliminar un estudiante por ID
    public void deleteEstudiante(Long id) {
        estudianteRepository.deleteById(id);  // Llama al repositorio para eliminar el estudiante por su ID
    }
}
