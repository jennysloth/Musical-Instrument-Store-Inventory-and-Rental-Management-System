public class Flute extends Woodwind {
    private boolean openHole;

    public Flute(String id, String brand, int dailyRent, boolean openHole) {
        super(id, brand, dailyRent);
        this.openHole = openHole;
    }

    public boolean isOpenHole() {
        return openHole;
    }

    public void setOpenHole(boolean openHole) {
        this.openHole = openHole;
    }

    @Override
    public String getTypeName() {
        return "長笛";
    }

    @Override
    public int getMaintenanceFee() {
        return 150;
    }

    @Override
    public String getSpecialInfo() {
        return "是否開孔：" + (openHole ? "是" : "否");
    }

    @Override
    public void showInfo() {
        System.out.println(getBasicInfo()
                + String.format(" | %s | 保養費：%d 元",
                        getSpecialInfo(), getMaintenanceFee()));
    }
}
