package Items;

import java.util.Objects;

public class Antique extends Variety{
    private String originLocation;
    private String material;

    public Antique(int id, String name, String description, int manYear, int desiredPrice, int sellerId, String originLocation, String material) {
        super(id, name, description, manYear, desiredPrice, sellerId);
        this.originLocation = originLocation;
        this.material = material;
    }

    public String getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(String originLocation) {
        this.originLocation = originLocation;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Antique antique = (Antique) o;
        return Objects.equals(originLocation, antique.originLocation) && Objects.equals(material, antique.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), originLocation, material);
    }
}
