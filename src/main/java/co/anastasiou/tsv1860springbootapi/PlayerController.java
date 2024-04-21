package co.anastasiou.tsv1860springbootapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class PlayerController {
    @Autowired
    PlayerDAO dao;

    @GetMapping("/player")
    public PlayerDTO get(@RequestParam("id") Integer id) {
        PlayerDTO player = dao.getById(id);

        if(player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }

        return player;
    }

    @GetMapping("/players")
    public List<PlayerDTO> getAll() {
        return dao.getAll();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<PlayerDTO> add(@RequestBody PlayerDTO player) {
        dao.create(player);
        return new ResponseEntity<PlayerDTO>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public PlayerDTO update(@RequestBody PlayerDTO player) {
        PlayerDTO p = dao.update(player);

        if(p == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "illegal argument");
        }

        return p;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<PlayerDTO> delete(@RequestParam Integer id) {
        PlayerDTO player = dao.getById(id);

        if(player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }

        dao.delete(id);
        return new ResponseEntity<PlayerDTO>(HttpStatus.OK);
    }
}
