public class Bassoon extends Woodwind {
    public Bassoon(String id, String brand, int dailyRent) {
        super(id, brand, dailyRent);
    }

    @Override
    public String getTypeName() {
        return "低音管";
    }

    @Override
    public int getMaintenanceFee() {
        return 350;
    }

    // 覆寫租金計算：日租金乘以天數，再加上低音管保養費。
    @Override
    public int calculateFee(int days) {
        return calculateBaseFee(days) + 350;
    }

    @Override
    public String getSpecialInfo() {
        return "";
    }

    // 顯示低音管資料。
    @Override
    public void showInfo() {
        System.out.println(getBasicInfo()
                + String.format(" | 保養費：%d 元", getMaintenanceFee()));
    }
}
