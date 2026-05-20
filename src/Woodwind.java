public abstract class Woodwind {
    private String id;
    private String brand;
    private int dailyRent;

    public Woodwind(String id, String brand, int dailyRent) {
        setId(id);
        setBrand(brand);
        setDailyRent(dailyRent);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("樂器編號不可空白");
        }
        this.id = id.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("品牌不可空白");
        }
        this.brand = brand.trim();
    }

    public int getDailyRent() {
        return dailyRent;
    }

    public void setDailyRent(int dailyRent) {
        if (dailyRent <= 0) {
            throw new IllegalArgumentException("日租金必須大於 0");
        }
        this.dailyRent = dailyRent;
    }

    public int calculateBaseFee(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("租借天數必須大於 0");
        }
        return dailyRent * days;
    }

    public abstract String getTypeName();

    public abstract int getMaintenanceFee();

    public int calculateFee(int days) {
        return calculateBaseFee(days) + getMaintenanceFee();
    }

    public String getBasicInfo() {
        return String.format("編號：%s | 類型：%s | 品牌：%s | 日租金：%d 元",
                id, getTypeName(), brand, dailyRent);
    }

    public abstract void showInfo();
}
