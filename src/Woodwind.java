public abstract class Woodwind {
    private String id;
    private String brand;
    private int dailyRent;
    private boolean isRented;

    public Woodwind(String id, String brand, int dailyRent) {
        setId(id);
        setBrand(brand);
        setDailyRent(dailyRent);
        // 預設狀態為在庫可租。
        setRented(false);
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

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        this.isRented = rented;
    }

    public String getStatus() {
        return isRented ? "已被借走" : "在庫可租";
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
        return String.format("編號：%s | 類型：%s | 品牌：%s | 日租金：%d 元 | 租借狀態：%s",
                id, getTypeName(), brand, dailyRent, getStatus());
    }

    public String getDisplayInfo() {
        String specialInfo = getSpecialInfo();
        String text = "樂器編號：" + id + "\n"
                + "品牌：" + brand + "\n"
                + "日租金：" + dailyRent + " 元\n"
                + "樂器種類：" + getTypeName() + "\n";
        if (specialInfo != null && !specialInfo.isEmpty()) {
            text += specialInfo + "\n";
        }
        text += "租借狀態：" + getStatus();
        return text;
    }

    public abstract String getSpecialInfo();

    public abstract void showInfo();
}
