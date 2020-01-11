/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.AprobacionDTO;
import co.edu.uniandes.csw.requirement.ejb.AprobacionLogic;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;


/**
 * Path de las aprobacion
 */
@Path("aprobaciones")
/**
 * Produce jsons
 */
@Produces("application/json")
/**
 * Lee jsons
 */
@Consumes("application/json")
/**
 * El request scoped.
 */
@RequestScoped
/**
 *Recurso de una aprobacion
 * @author Sofia Alvarez
 */
public class AprobacionResource {
    /**
     * Consola de JS
     */
    private static final Logger LOGGER = Logger.getLogger(AprobacionResource.class.getName());
    
    /**
     * Inyección de la logica de la aprobacion
     */
    @Inject
    private AprobacionLogic aprobacionLogic;
    
    /**
     * Metodo para crear una aprobacion.
     * @param aprobacion es el DTO de la aprobacion a crear
     * @return json de la aprobacion creada
     * @throws BusinessLogicException si se incumple alguna 
     */
    @POST
    public AprobacionDTO createAprobacion(AprobacionDTO aprobacion) throws BusinessLogicException{
        AprobacionDTO aprobacionDTO = new AprobacionDTO(aprobacionLogic.createAprobacion(aprobacion.toEntity()));
        return aprobacionDTO;
    }
    
    /**
     * Lista de aprobaciones 
     * @return una lista con todas las aprobaciones. 
     */
    @GET
    public List<AprobacionDTO> getAprobaciones(){
      List<AprobacionDTO> listaAprobaciones = listEntity2DTO(aprobacionLogic.findAllAprobaciones());
      return listaAprobaciones;
    }
    
    /**
     * Consigue una sola aprobacion dado un id
     * @param id el id de la aprobacion a obtener
     * @return la aprobacion que se quiere.
     */
    @GET
    @Path("{id: \\d+}")
    public AprobacionDTO getAprobacion(@PathParam("id") Long id){
        AprobacionEntity entity = aprobacionLogic.findAprobacionById(id);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+id+" no existe.", 404);
        }
        AprobacionDTO aprobacionDTO = new AprobacionDTO(entity);
            
        return aprobacionDTO;
    }
    
    /**
     * Elimina una aprobacion con un id dado.
     * @param id de la aprobacion para eliminar 
     * @return la aprobacion eliminada
     */
    @DELETE
    @Path("{id: \\d+}")
    public AprobacionDTO deleteAprobacion(@PathParam("id") Long id){
        AprobacionEntity entity = aprobacionLogic.deleteAprobacion(id);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+id+" no existe.", 404);
        }
        AprobacionDTO dto = new AprobacionDTO(entity);
        return dto;
    }
    
    /**
     * Actualiza una aprobacion
     * @param aprobacionId id de la aprobacion
     * @param aprobacion aprobacion a actualizar
     * @return aprobacion actualizada 
     * @throws BusinessLogicException si no se cumplen las reglas de negocio
     */
    @PUT
    @Path("{id: \\d+}")
    public AprobacionDTO updateAprobacion(@PathParam("id") Long aprobacionId, AprobacionDTO aprobacion) throws BusinessLogicException{
        aprobacion.setId(aprobacionId);
        if (aprobacionLogic.findAprobacionById(aprobacionId) == null) {
            throw new WebApplicationException("El recurso /aprobaciones/" + aprobacionId + " no existe.", 404);
        }
        AprobacionDTO aprobDTO = new AprobacionDTO(aprobacionLogic.updateAprobacion(aprobacion.toEntity()));
        return aprobDTO;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos AprobacionEntity a una lista de
     * objetos AprobacionDTO (json)
     *
     * @param entityList corresponde a la lista de aprobaciones de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de aprobaciones en forma DTO (json)
     */
    
     private List<AprobacionDTO> listEntity2DTO(List<AprobacionEntity> entityList) {
        List<AprobacionDTO> list = new ArrayList<>();
        for (AprobacionEntity entity : entityList) {
            list.add(new AprobacionDTO(entity));
        }
        return list;
    }
}
