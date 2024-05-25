package Items;

import java.util.Objects;

public class Art extends Variety {

    private String artistName;
    enum ArtStyle {
        Classicism,
        Baroque,
        Impressionism,
        Minimalism,
        Symbolism,
        Digital_Art
    };
    private ArtStyle style;

    private int height;
    private int width;

    public Art(int id, String name, String description, int manYear, int desiredPrice, int sellerId, String artistName, ArtStyle style, int height, int width) {
        super(id, name, description, manYear, desiredPrice, sellerId);
        this.artistName = artistName;
        this.style = style;
        this.height = height;
        this.width = width;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public ArtStyle getStyle() {
        return style;
    }

    public void setStyle(ArtStyle style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Art art = (Art) o;
        return Objects.equals(artistName, art.artistName) && style == art.style;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), artistName, style);
    }
}
