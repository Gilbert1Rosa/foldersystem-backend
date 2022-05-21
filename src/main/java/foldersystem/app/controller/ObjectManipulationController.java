package foldersystem.app.controller;

import foldersystem.app.model.Response;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ObjectManipulationController {

    /**
     * Create an object (File or folder) in an specifc folder.
     *
     * @param params
     * @return
     */
    @PostMapping("/api/object")
    public Response createObject(
            @RequestBody Map<String, Object> params
    ) {
        return new Response();
    }

    /**
     * Gets the list of objects in a folder.
     *
     * @param params
     * @return
     */
    @GetMapping("/api/object")
    public Response getObject(
            @RequestParam Map<String, Object> params
    ) {
        return new Response();
    }

    /**
     * Moves an object to another folder.
     *
     * @param params
     * @return
     */
    @PatchMapping("/api/object")
    public Response moveObject(
            @RequestBody Map<String, Object> params
    ) {
        return new Response();
    }
}
