package models;

public class Pokemon {
    private String nickname;
    private Specie specie;
    private int currentHp;
    private int levelRepr;
    private boolean asleep;
    private boolean poisoned;
    private boolean burned;
    private boolean frozen;
    private boolean paralyzed;
    private Type type1;
    private Type type2;
    private int catchRate;
    private Move move1;
    private Move move2;
    private Move move3;
    private Move move4;
    private int otId;
    private String otName;
    private long exp;
    private int hpEv;
    private int attackEv;
    private int defenseEv;
    private int speedEv;
    private int specialEv;
    private int hpIv;
    private int attackIv;
    private int defenseIv;
    private int speedIv;
    private int specialIv;
    private int currentLevel;
    private int maxHp;
    private int defense;
    private int attack;
    private int speed;
    private int special;

    public Pokemon(String nickname, Specie specie, int currentHp, int levelRepr, boolean asleep, boolean poisoned, boolean burned,
                   boolean frozen, boolean paralyzed, Type type1, Type type2, int catchRate, Move move1, Move move2,
                   Move move3, Move move4, int otId, String otName, long exp, int hpEv, int attackEv, int defenseEv,
                   int speedEv, int specialEv, int hpIv, int attackIv, int defenseIv, int speedIv, int specialIv,
                   int currentLevel, int maxHp, int attack, int defense, int speed, int special) {
        this.nickname = nickname;
        this.specie = specie;
        this.currentHp = currentHp;
        this.levelRepr = levelRepr;
        this.asleep = asleep;
        this.poisoned = poisoned;
        this.burned = burned;
        this.frozen = frozen;
        this.paralyzed = paralyzed;
        this.type1 = type1;
        this.type2 = type2;
        this.catchRate = catchRate;
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.move4 = move4;
        this.otId = otId;
        this.otName = otName;
        this.exp = exp;
        this.hpEv = hpEv;
        this.attackEv = attackEv;
        this.defenseEv = defenseEv;
        this.speedEv = speedEv;
        this.specialEv = specialEv;
        this.hpIv = hpIv;
        this.attackIv = attackIv;
        this.defenseIv = defenseIv;
        this.speedIv = speedIv;
        this.specialIv = specialIv;
        this.currentLevel = currentLevel;
        this.maxHp = maxHp;
        this.defense = defense;
        this.attack = attack;
        this.speed = speed;
        this.special = special;
    }

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
        this.specie = specie;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getLevelRepr() {
        return levelRepr;
    }

    public void setLevelRepr(int levelRepr) {
        this.levelRepr = levelRepr;
    }

    public boolean isAsleep() {
        return asleep;
    }

    public void setAsleep(boolean asleep) {
        this.asleep = asleep;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    public boolean isBurned() {
        return burned;
    }

    public void setBurned(boolean burned) {
        this.burned = burned;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isParalyzed() {
        return paralyzed;
    }

    public void setParalyzed(boolean paralyzed) {
        this.paralyzed = paralyzed;
    }

    public Type getType1() {
        return type1;
    }

    public void setType1(Type type1) {
        this.type1 = type1;
    }

    public Type getType2() {
        return type2;
    }

    public void setType2(Type type2) {
        this.type2 = type2;
    }

    public int getCatchRate() {
        return catchRate;
    }

    public void setCatchRate(int catchRate) {
        this.catchRate = catchRate;
    }

    public Move getMove1() {
        return move1;
    }

    public void setMove1(Move move1) {
        this.move1 = move1;
    }

    public Move getMove2() {
        return move2;
    }

    public void setMove2(Move move2) {
        this.move2 = move2;
    }

    public Move getMove3() {
        return move3;
    }

    public void setMove3(Move move3) {
        this.move3 = move3;
    }

    public Move getMove4() {
        return move4;
    }

    public void setMove4(Move move4) {
        this.move4 = move4;
    }

    public int getOtId() {
        return otId;
    }

    public void setOtId(int otId) {
        this.otId = otId;
    }

    public String getOtName() {
        return otName;
    }

    public void setOtName(String otName) {
        this.otName = otName;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public int getHpEv() {
        return hpEv;
    }

    public void setHpEv(int hpEv) {
        this.hpEv = hpEv;
    }

    public int getAttackEv() {
        return attackEv;
    }

    public void setAttackEv(int attackEv) {
        this.attackEv = attackEv;
    }

    public int getDefenseEv() {
        return defenseEv;
    }

    public void setDefenseEv(int defenseEv) {
        this.defenseEv = defenseEv;
    }

    public int getSpeedEv() {
        return speedEv;
    }

    public void setSpeedEv(int speedEv) {
        this.speedEv = speedEv;
    }

    public int getSpecialEv() {
        return specialEv;
    }

    public void setSpecialEv(int specialEv) {
        this.specialEv = specialEv;
    }

    public int getHpIv() {
        return hpIv;
    }

    public void setHpIv(int hpIv) {
        this.hpIv = hpIv;
    }

    public int getAttackIv() {
        return attackIv;
    }

    public void setAttackIv(int attackIv) {
        this.attackIv = attackIv;
    }

    public int getDefenseIv() {
        return defenseIv;
    }

    public void setDefenseIv(int defenseIv) {
        this.defenseIv = defenseIv;
    }

    public int getSpeedIv() {
        return speedIv;
    }

    public void setSpeedIv(int speedIv) {
        this.speedIv = speedIv;
    }

    public int getSpecialIv() {
        return specialIv;
    }

    public void setSpecialIv(int specialIv) {
        this.specialIv = specialIv;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
