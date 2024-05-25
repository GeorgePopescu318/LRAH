package Items;

import java.util.Objects;

public class Variety{
    protected int id;
    protected String name;
    protected String description;
    protected int manYear;

    protected int  desiredPrice;

    protected int soldPrice;

    protected float commissionValue;

    protected int sellerId;
    protected int buyerId;

    public Variety(int id, String name, String description, int manYear, int desiredPrice, int sellerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manYear = manYear;
        this.desiredPrice = desiredPrice;
        this.soldPrice = -1;
        this.commissionValue = (float) (desiredPrice * 0.2);
        this.sellerId = sellerId;
        this.buyerId = -1;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getManYear() {
        return manYear;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setManYear(int manYear) {
        this.manYear = manYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variety variety = (Variety) o;
        return id == variety.id && manYear == variety.manYear && Objects.equals(name, variety.name) && Objects.equals(description, variety.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, manYear);
    }

    //TO DO
    // Print object of UI
}