package Data;

/**
 * Created by Mosab on 3/17/2017.
 */
public class Inventory {

    private String id;
    private String material;
    private Double unitPrice;
    private String date;


    public Inventory(String id, String material, Double unitPrice, String date) {
        this.id = id;
        this.material = material;
        this.unitPrice = unitPrice;
        this.date = date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getDate()
    {
        return this.date;
    }
}
