public class Oboe extends Woodwind {
    public Oboe(String id, String brand, int dailyRent) {
        super(id, brand, dailyRent);
    }

    @Override
    public String getTypeName() {
        return "雙簧管";
    }

    @Override
    public int getMaintenanceFee() {
        return 300;
    }

    // 覆寫租金計算：日租金乘以天數，再加上雙簧管保養費。
    @Override
    public int calculateFee(int days) {
        return calculateBaseFee(days) + 300;
    }

    @Override
    public String getSpecialInfo() {
        return "";
    }

    // 顯示雙簧管資料。
    @Override
    public void showInfo() {
        System.out.println(getBasicInfo()
                + String.format(" | 保養費：%d 元", getMaintenanceFee()));
    }
}
