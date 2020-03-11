package de.stw.phoenix.game.engine.modules;

import org.springframework.stereotype.Service;

@Service
public class EnergyModule {
//    implements
//} GameModule {
//
//    @Override
//    public List<PlayerUpdate> getPlayerUpdates(ImmutablePlayer player, Tick currentTick) {
//        PlayerUpdate playerUpdate = new PlayerUpdate() {
//
//            @Override
//            public int getPhase() {
//                return Phases.EnergyProduction;
//            }
//
//            @Override
//            public void preUpdate(MutablePlayer player, Tick tick) {
//                int totalEnergyConsumption = player.getBuildings().stream().mapToInt(b -> b.getBuilding().getEnergyConsumption() * b.getLevel()).sum();
//                int totalEnergyProduction = 100 * player.getBuilding(Buildings.Powerplant).getLevel();
//                if (totalEnergyConsumption > totalEnergyProduction) {
//                    player.addModifier(Modifiers.CRITICAL_ENERGY_LEVEL);
//                } else {
//                    player.removeModifier(Modifiers.CRITICAL_ENERGY_LEVEL);
//                }
//            }
//
//            @Override
//            public void update(MutablePlayer player, Tick tick) {
//
//            }
//
//            @Override
//            public void postUpdate(MutablePlayer player, Tick tick) {
//                preUpdate(player, tick);
//            }
//        };
//        return Lists.newArrayList(playerUpdate);
//    }
}
