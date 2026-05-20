public class Saxophone extends Woodwind {
    private String saxophoneType;

    public Saxophone(String id, String brand, int dailyRent, String saxophoneType) {
        super(id, brand, dailyRent);
        setSaxophoneType(saxophoneType);
    }

    public String getSaxophoneType() {
        return saxophoneType;
    }

    public void setSaxophoneType(String saxophoneType) {
        if (saxophoneType == null || saxophoneType.trim().isEmpty()) {
            throw new IllegalArgumentException("薩克斯風種類不可空白");
        }
        this.saxophoneType = saxophoneType.trim();
    }

    @Override
    public String getTypeName() {
        return "薩克斯風";
    }

    @Override
    public int getMaintenanceFee() {
        return 250;
    }

    @Override
    public void showInfo() {
        System.out.println(getBasicInfo()
                + String.format(" | 薩克種類：%s | 保養費：%d 元",
                        saxophoneType, getMaintenanceFee()));
    }
}
