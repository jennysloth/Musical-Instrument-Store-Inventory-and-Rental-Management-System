public class Clarinet extends Woodwind {
    private String clarinetType;

    public Clarinet(String id, String brand, int dailyRent, String clarinetType) {
        super(id, brand, dailyRent);
        setClarinetType(clarinetType);
    }

    public String getClarinetType() {
        return clarinetType;
    }

    public void setClarinetType(String clarinetType) {
        if (clarinetType == null || clarinetType.trim().isEmpty()) {
            throw new IllegalArgumentException("豎笛種類不可空白");
        }
        this.clarinetType = clarinetType.trim();
    }

    @Override
    public String getTypeName() {
        return "豎笛";
    }

    @Override
    public int getMaintenanceFee() {
        return 200;
    }

    @Override
    public void showInfo() {
        System.out.println(getBasicInfo()
                + String.format(" | 豎笛種類：%s | 保養費：%d 元",
                        clarinetType, getMaintenanceFee()));
    }
}
