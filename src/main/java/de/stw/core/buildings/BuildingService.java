package de.stw.core.buildings;

import de.stw.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {

    @Autowired
    private UserService userService;

    public void build(int userId, int buildingId) {
//        Optional<User> user = userManager.get(userId);
//        if (!user.isPresent()) {
//            throw new NoSuchElementException(); // TODO MVR meaningful message?
//        }
//        Optional<Building> building = get(buildingId);
//        if (!building.isPresent()) {
//            throw new NoSuchElementException(); // TODO MVR meaningful message?
//        }
//        Optional<Building> userBuilding = user.findBuilding(building);
//        if (userBuilding.isPresent()) {
//            int nextLevel = userBuilding.get().getLevel() + 1;
//        } else {
//            int nextLevel = 1;
//        }
//        Map<Resource, Integer> costs = building.get().getCosts(nextLevel);
//        if (user.get().canAfford(costs)) {
//            enqueue(user, building, nextLevel);
//            user.removeResources(costs);
//        }
    }
}
