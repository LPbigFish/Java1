package lab.manufacture;

public enum Operation {
    CUTTING("Řezání", 2),
    SANDING("Broušení", 20),
    PAINTING("Natírání", 30),
    DRILLING("Vrtání", 5);

    final String name;
    final int duration;

    Operation(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }
    public int getDuration() {
        return duration;
    }
}
