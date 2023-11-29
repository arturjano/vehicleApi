package pl.jano.vehicleapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/vehicles")
public class VehicleApi {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleApi(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Vehicle>> getVehicles() {

        return new ResponseEntity<>(vehicleService.getVehicleList(), HttpStatus.OK);

    }


    @GetMapping(value = "/id/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable long id) {

        Vehicle vehicle = vehicleService.getVehicleById(id);

        if (Objects.isNull(vehicle)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(vehicle, HttpStatus.OK);

    }

    @GetMapping(value = "/color/{color}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Vehicle>> getVehiclesByColor(@PathVariable String color) {

        List<Vehicle> vehicleListByColor = vehicleService.getVehicleByColor(color);
        if (vehicleListByColor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(vehicleListByColor, HttpStatus.OK);

    }

    @PostMapping()
    public ResponseEntity<String> addVehicle(@Validated @RequestBody Vehicle vehicle) {

        vehicleService.addVehicle(vehicle);
        return new ResponseEntity<>("Nowe id pojazdu: " + vehicle.getId(), HttpStatus.CREATED);

    }

    @PutMapping()
    public ResponseEntity<String> modifyVehicle(@Validated @RequestBody Vehicle newVehicle) {

        Status status = vehicleService.modifyVehicle(newVehicle);
        switch (status) {
            case NOT_FOUND:
                return new ResponseEntity<>("Brak pojazdu do modyfikacji, sprawdź id", HttpStatus.NOT_FOUND);
            case FAILURE:
                return new ResponseEntity<>("Wystąpił błąd podczas usuwania istniejącego", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Zmieniono pojazd", HttpStatus.CREATED);

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteVehicleById(@PathVariable long id) {

        Status status = vehicleService.deleteVehicle(id);

        switch (status) {
            case NOT_FOUND:
                return new ResponseEntity<>("Brak pojazdu o danym Id", HttpStatus.NOT_FOUND);
            case FAILURE:
                return new ResponseEntity<>("Wystąpił błąd podczas usuwania pojazdu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Pojazd usunięty", HttpStatus.OK);

    }

    @PatchMapping("/id/color/{id}/{color}")
    public ResponseEntity<String> changeVehicleColor(@PathVariable long id, @PathVariable String color) {

        Status status = vehicleService.modifyVehicleAttribute(id, Attribute.COLOR, color);
        switch (status) {
            case NOT_FOUND:
                return new ResponseEntity<>("brak pojazdu o danym id", HttpStatus.NOT_FOUND);
            case INVALID_ATTRIBUTE:
                return new ResponseEntity<>("brak obsługi zmiany koloru", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Zmieniono kolor pojazdu", HttpStatus.OK);

    }

    @PatchMapping("/id/mark/{id}/{mark}")
    public ResponseEntity<String> changeVehicleMark(@PathVariable long id, @PathVariable String mark) {

        Status status = vehicleService.modifyVehicleAttribute(id, Attribute.MARK, mark);
        switch (status) {
            case NOT_FOUND:
                return new ResponseEntity<>("brak pojazdu o danym id", HttpStatus.NOT_FOUND);
            case INVALID_ATTRIBUTE:
                return new ResponseEntity<>("brak obsługi zmiany marki", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Zmieniono markę pojazdu", HttpStatus.OK);

    }

    @PatchMapping("/id/model/{id}/{model}")
    public ResponseEntity<String> changeVehicleModel(@PathVariable long id, @PathVariable String model) {

        Status status = vehicleService.modifyVehicleAttribute(id, Attribute.MODEL, model);
        switch (status) {
            case NOT_FOUND:
                return new ResponseEntity<>("brak pojazdu o danym id", HttpStatus.NOT_FOUND);
            case INVALID_ATTRIBUTE:
                return new ResponseEntity<>("brak obsługi zmiany koloru", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Zmieniono model pojazdu", HttpStatus.OK);

    }
}
