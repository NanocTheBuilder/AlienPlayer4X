package com.thilian.se4x.robot.game.scenarios.vpscenario;

public class VpDifficulties {

    public static enum VpSoloDifficulty implements VpDifficulty{
        EASY(2, 5, 0, 50), NORMAL(2, 10, 100, 50), HARD(2, 15, 100, 50);
    
        private final int numberOfAPs;
        private final int cpPerEcon;
        private final int startingBank;
        private final int maxDefenseCp;

        VpSoloDifficulty(int numberOfAPs, int cpPerEcon, int startingBank, int maxDefenseCp) {
            this.numberOfAPs = numberOfAPs;
            this.cpPerEcon = cpPerEcon;
            this.startingBank = startingBank;
            this.maxDefenseCp = maxDefenseCp;
        }

        @Override
        public int getCPPerEcon() {
            return cpPerEcon;
        }

        @Override
        public int getNumberOfAlienPlayers() {
            return numberOfAPs;
        }

        @Override
        public int getStartingBank() {
            return startingBank;
        }

        @Override
        public int getMaxDefenseCp() {
            return maxDefenseCp;
        }
    }

    public static enum Vp2pDifficulty implements VpDifficulty{
        EASY(2, 5, 150, 75), NORMAL(2, 10, 150, 75), HARD(2, 15, 150, 75);
    
        private final int numberOfAPs;
        private final int cpPerEcon;
        private final int startingBank;
        private final int maxDefenseCp;
    
        Vp2pDifficulty(int numberOfAPs, int cpPerEcon, int startingBank, int maxDefenseCp) {
            this.numberOfAPs = numberOfAPs;
            this.cpPerEcon = cpPerEcon;
            this.startingBank = startingBank;
            this.maxDefenseCp = maxDefenseCp;
        }
    
        @Override
        public int getCPPerEcon() {
            return cpPerEcon;
        }
    
        @Override
        public int getNumberOfAlienPlayers() {
            return numberOfAPs;
        }
    
        @Override
        public int getStartingBank() {
            return startingBank;
        }
    
        @Override
        public int getMaxDefenseCp() {
            return maxDefenseCp;
        }
    }
    

    public static enum Vp3pDifficulty implements VpDifficulty{
        EASY(2, 10, 200, 125), NORMAL(2, 15, 200, 125), HARD(2, 20, 200, 155);
    
        private final int numberOfAPs;
        private final int cpPerEcon;
        private final int startingBank;
        private final int maxDefenseCp;
    
        Vp3pDifficulty(int numberOfAPs, int cpPerEcon, int startingBank, int maxDefenseCp) {
            this.numberOfAPs = numberOfAPs;
            this.cpPerEcon = cpPerEcon;
            this.startingBank = startingBank;
            this.maxDefenseCp = maxDefenseCp;
        }
    
        @Override
        public int getCPPerEcon() {
            return cpPerEcon;
        }
    
        @Override
        public int getNumberOfAlienPlayers() {
            return numberOfAPs;
        }
    
        @Override
        public int getStartingBank() {
            return startingBank;
        }
    
        @Override
        public int getMaxDefenseCp() {
            return maxDefenseCp;
        }
    }

}
