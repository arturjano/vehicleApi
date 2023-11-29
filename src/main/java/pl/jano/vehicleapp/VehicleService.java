package pl.jano.vehicleapp;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final List<Vehicle> vehicleList = new ArrayList<>();


    public VehicleService() {
        vehicleList.add(new Vehicle(0, "Audi", "A4", "black"));
        vehicleList.add(new Vehicle(1, "Fiat", "126", "orange"));
        vehicleList.add(new Vehicle(2, "Wartburg", "353", "brown"));
    }

    public List<Vehicle> getVehicleList() {
        vehicleList.sort(new VehicleByIdComparator());
        return vehicleList;
    }

    public Vehicle getVehicleById(long id) {
        return vehicleList.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Vehicle> getVehicleByColor(String color) {

        return vehicleList.stream()
                .filter(v -> v.getColor().equals(color))
                .sorted(new VehicleByIdComparator())
                .collect(Collectors.toList());

    }

    public void addVehicle(Vehicle vehicle) {

        if (Objects.nonNull(getVehicleById(vehicle.getId()))) {
            vehicleList.stream()
                    .max(Comparator.comparing(Vehicle::getId))
                    .ifPresent(max -> vehicle.setId(max.getId() + 1));
        }

        vehicleList.add(vehicle);
    }

    public Status modifyVehicle(Vehicle newVehicle) {

        Vehicle existingVehicle = getVehicleById(newVehicle.getId());
        if (Objects.isNull(existingVehicle)) {
            return Status.NOT_FOUND;
        }

        if (!vehicleList.remove(existingVehicle)) {
            return Status.FAILURE;
        }

        vehicleList.add(newVehicle);
        return Status.SUCCESS;
    }


    public Status deleteVehicle(long id) {

        Vehicle vehicle = getVehicleById(id);
        if (Objects.isNull(vehicle)) {
            return Status.NOT_FOUND;
        }

        if (!vehicleList.remove(vehicle)) {
            return Status.FAILURE;
        }

        return Status.SUCCESS;
    }


    public Status modifyVehicleAttribute(long id, Attribute attrName, String attrValue) {

        Vehicle vehicle = getVehicleById(id);
        if (Objects.isNull(vehicle)) {
            return Status.NOT_FOUND;
        }

        switch (attrName) {
            case COLOR:
                vehicle.setColor(attrValue);
                break;
            case MARK:
                vehicle.setMark(attrValue);
                break;
            case MODEL:
                vehicle.setModel(attrValue);
                break;
            default:
                return Status.INVALID_ATTRIBUTE;
        }

        return Status.SUCCESS;
    }


}

class VehicleByIdComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle a, Vehicle b) {
        return (int) (a.getId() - b.getId());
    }
}
