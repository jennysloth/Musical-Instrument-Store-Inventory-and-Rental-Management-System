import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InventoryManager manager = new InventoryManager();

    public static void main(String[] args) {
        seedData();
        boolean running = true;

        System.out.println("歡迎使用樂器行庫存與租借管理系統");
        while (running) {
            printMenu();
            int choice = readInt("請選擇功能：");

            switch (choice) {
                case 1:
                    addInstrument();
                    break;
                case 2:
                    searchInstrument();
                    break;
                case 3:
                    manager.showAll();
                    break;
                case 4:
                    calculateRent();
                    break;
                case 5:
                    rentInstrument();
                    break;
                case 6:
                    returnInstrument();
                    break;
                case 0:
                    running = false;
                    System.out.println("系統已結束，謝謝使用。");
                    break;
                default:
                    System.out.println("沒有這個選項，請重新輸入。");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== 主選單 =====");
        System.out.println("1. 新增樂器資料");
        System.out.println("2. 依編號查詢樂器");
        System.out.println("3. 顯示所有庫存");
        System.out.println("4. 計算租借總金額");
        System.out.println("5. 租借樂器");
        System.out.println("6. 歸還樂器");
        System.out.println("0. 離開系統");
    }

    private static void seedData() {
        manager.addInstrument(new Flute("F001", "Yamaha", 300, true));
        manager.addInstrument(new Clarinet("C001", "Buffet Crampon", 350, "降B調"));
        manager.addInstrument(new Saxophone("S001", "Selmer", 600, "中音薩克斯風"));
        // 新增雙簧管與低音管範例資料，仍以 Woodwind 多型方式存入 ArrayList。
        manager.addInstrument(new Oboe("O001", "Yamaha", 500));
        manager.addInstrument(new Bassoon("B001", "Fox", 800));
    }

    private static void addInstrument() {
        System.out.println("\n===== 新增樂器資料 =====");
        System.out.println("1. 長笛");
        System.out.println("2. 豎笛");
        System.out.println("3. 薩克斯風");
        System.out.println("4. 雙簧管");
        System.out.println("5. 低音管");

        int type = readInt("請選擇樂器類型：");
        String id = readLine("請輸入樂器編號：");
        if (manager.findById(id) != null) {
            System.out.println("新增失敗：此編號已存在。");
            return;
        }

        String brand = readLine("請輸入品牌：");
        int dailyRent = readInt("請輸入日租金：");

        try {
            Woodwind instrument;
            switch (type) {
                case 1:
                    instrument = new Flute(id, brand, dailyRent, readYesNo("是否為開孔長笛？(Y/N)："));
                    break;
                case 2:
                    instrument = new Clarinet(id, brand, dailyRent, readLine("請輸入豎笛種類："));
                    break;
                case 3:
                    instrument = new Saxophone(id, brand, dailyRent, readLine("請輸入薩克種類："));
                    break;
                case 4:
                    instrument = new Oboe(id, brand, dailyRent);
                    break;
                case 5:
                    instrument = new Bassoon(id, brand, dailyRent);
                    break;
                default:
                    instrument = null;
                    break;
            }

            if (instrument == null) {
                System.out.println("新增失敗：沒有這個樂器類型。");
                return;
            }

            manager.addInstrument(instrument);
            System.out.println("新增成功！");
        } catch (IllegalArgumentException ex) {
            System.out.println("新增失敗：" + ex.getMessage());
        }
    }

    private static void searchInstrument() {
        System.out.println("\n===== 查詢樂器資料 =====");
        Woodwind instrument = findInstrumentFromInput();
        if (instrument != null) {
            instrument.showInfo();
        }
    }

    private static void calculateRent() {
        System.out.println("\n===== 計算租借總金額 =====");
        Woodwind instrument = findInstrumentFromInput();
        if (instrument == null) {
            return;
        }

        // 已出借的樂器不可再計算新的租借費用。
        if (instrument.isRented()) {
            System.out.println("此樂器目前出借中，無法計算新的租借費用。");
            return;
        }

        int days = readInt("請輸入租借天數：");
        try {
            int baseFee = instrument.calculateBaseFee(days);
            int maintenanceFee = instrument.getMaintenanceFee();
            int totalFee = instrument.calculateFee(days);

            System.out.println("\n===== 租金明細 =====");
            instrument.showInfo();
            System.out.printf("計算方式：%d 元 × %d 天 + %d 元保養費 = %d 元%n",
                    instrument.getDailyRent(), days, maintenanceFee, totalFee);
            System.out.println("基本租金：" + baseFee + " 元");
            System.out.println("應收總租金：" + totalFee + " 元");
        } catch (IllegalArgumentException ex) {
            System.out.println("計算失敗：" + ex.getMessage());
        }
    }

    private static void rentInstrument() {
        System.out.println("\n===== 租借樂器 =====");
        Woodwind instrument = findInstrumentFromInput();
        if (instrument == null) {
            return;
        }

        // 在庫時才能租借，避免同一件樂器被重複出借。
        if (!instrument.isRented()) {
            instrument.setRented(true);
            System.out.println("租借成功！此樂器已標記為已被借走。");
        } else {
            System.out.println("此樂器目前出借中，請選擇其他樂器。");
        }
    }

    private static void returnInstrument() {
        System.out.println("\n===== 歸還樂器 =====");
        Woodwind instrument = findInstrumentFromInput();
        if (instrument == null) {
            return;
        }

        // 只有出借中的樂器需要歸還。
        if (instrument.isRented()) {
            instrument.setRented(false);
            System.out.println("歸還成功！此樂器已恢復為在庫可租。");
        } else {
            System.out.println("此樂器目前已在庫，無需歸還。");
        }
    }

    private static Woodwind findInstrumentFromInput() {
        if (manager.isEmpty()) {
            System.out.println("目前沒有庫存資料。");
            return null;
        }

        String id = readLine("請輸入樂器編號：");
        Woodwind instrument = manager.findById(id);
        if (instrument == null) {
            System.out.println("查無此樂器編號。");
        }
        return instrument;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("請輸入整數數字。");
            }
        }
    }

    private static String readLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("輸入不可空白。");
        }
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            String input = readLine(prompt).toUpperCase();
            if (input.equals("Y") || input.equals("YES") || input.equals("是")) {
                return true;
            }
            if (input.equals("N") || input.equals("NO") || input.equals("否")) {
                return false;
            }
            System.out.println("請輸入 Y 或 N。");
        }
    }
}
