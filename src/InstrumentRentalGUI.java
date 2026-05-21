import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class InstrumentRentalGUI extends JFrame {
    private final ArrayList<Woodwind> instruments = new ArrayList<>();
    private final JTextArea displayArea = new JTextArea();

    public InstrumentRentalGUI() {
        setTitle("樂器行庫存與租借管理系統");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        seedData();
        buildLayout();
    }

    private void buildLayout() {
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("樂器行庫存與租借管理系統", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(8, 1, 8, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(180, 0));

        JButton addButton = createButton("新增樂器");
        JButton searchButton = createButton("查詢樂器");
        JButton showAllButton = createButton("顯示所有樂器");
        JButton feeButton = createButton("計算租金");
        JButton rentButton = createButton("租借樂器");
        JButton returnButton = createButton("歸還樂器");
        JButton clearButton = createButton("清除畫面");
        JButton exitButton = createButton("離開系統");

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(showAllButton);
        buttonPanel.add(feeButton);
        buttonPanel.add(rentButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.WEST);

        displayArea.setEditable(false);
        displayArea.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 16));
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        displayArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("資料顯示區"));
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> addInstrument());
        searchButton.addActionListener(e -> searchInstrument());
        showAllButton.addActionListener(e -> showAllInstruments());
        feeButton.addActionListener(e -> calculateRent());
        rentButton.addActionListener(e -> rentInstrument());
        returnButton.addActionListener(e -> returnInstrument());
        clearButton.addActionListener(e -> displayArea.setText(""));
        exitButton.addActionListener(e -> dispose());

        showWelcomeMessage();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Microsoft JhengHei", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBackground(new Color(245, 247, 250));
        return button;
    }

    private void seedData() {
        instruments.add(new Flute("F001", "Yamaha", 300, true));
        instruments.add(new Clarinet("C001", "Buffet Crampon", 350, "降B調"));
        instruments.add(new Saxophone("S001", "Selmer", 600, "中音薩克斯風"));
        instruments.add(new Oboe("O001", "Yamaha", 500));
        instruments.add(new Bassoon("B001", "Fox", 800));
    }

    private void showWelcomeMessage() {
        displayArea.setText("\n\n\n"
                + "        歡迎來到樂器行庫存與租借管理系統\n\n"
                + "        請從左側功能按鈕選擇要執行的操作。");
    }

    private void addInstrument() {
        String[] types = {"Flute", "Clarinet", "Saxophone", "Oboe", "Bassoon"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        JTextField idField = new JTextField();
        JTextField brandField = new JTextField();
        JTextField rentField = new JTextField();
        JTextField specialField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.add(new JLabel("樂器種類："));
        panel.add(typeBox);
        panel.add(new JLabel("樂器編號："));
        panel.add(idField);
        panel.add(new JLabel("品牌："));
        panel.add(brandField);
        panel.add(new JLabel("日租金："));
        panel.add(rentField);
        panel.add(new JLabel("專屬屬性："));
        panel.add(specialField);

        typeBox.addActionListener(e -> updateSpecialField(typeBox, specialField));
        updateSpecialField(typeBox, specialField);

        int result = JOptionPane.showConfirmDialog(this, panel, "新增樂器",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            String type = (String) typeBox.getSelectedItem();
            String id = idField.getText().trim();
            String brand = brandField.getText().trim();
            int dailyRent = Integer.parseInt(rentField.getText().trim());
            String special = specialField.getText().trim();

            if (findById(id) != null) {
                showMessage("新增失敗：此編號已存在。");
                return;
            }

            Woodwind instrument = createInstrument(type, id, brand, dailyRent, special);
            instruments.add(instrument);
            showMessage("新增成功！");
            showAllInstruments();
        } catch (NumberFormatException ex) {
            showMessage("新增失敗：日租金請輸入整數。");
        } catch (IllegalArgumentException ex) {
            showMessage("新增失敗：" + ex.getMessage());
        }
    }

    private Woodwind createInstrument(String type, String id, String brand, int dailyRent, String special) {
        switch (type) {
            case "Flute":
                boolean openHole = special.equalsIgnoreCase("Y")
                        || special.equalsIgnoreCase("YES")
                        || special.equals("是")
                        || special.equals("開孔");
                return new Flute(id, brand, dailyRent, openHole);
            case "Clarinet":
                return new Clarinet(id, brand, dailyRent, special);
            case "Saxophone":
                return new Saxophone(id, brand, dailyRent, special);
            case "Oboe":
                return new Oboe(id, brand, dailyRent);
            case "Bassoon":
                return new Bassoon(id, brand, dailyRent);
            default:
                throw new IllegalArgumentException("沒有這個樂器種類");
        }
    }

    private void updateSpecialField(JComboBox<String> typeBox, JTextField specialField) {
        String type = (String) typeBox.getSelectedItem();
        specialField.setText("");
        if ("Oboe".equals(type) || "Bassoon".equals(type)) {
            specialField.setEnabled(false);
            specialField.setText("不需填寫");
        } else {
            specialField.setEnabled(true);
        }
    }

    private void searchInstrument() {
        Woodwind instrument = askInstrumentById("請輸入要查詢的樂器編號：");
        if (instrument != null) {
            displayArea.setText(instrument.getDisplayInfo());
        }
    }

    private void showAllInstruments() {
        if (instruments.isEmpty()) {
            displayArea.setText("目前沒有任何樂器資料。");
            return;
        }

        StringBuilder builder = new StringBuilder("===== 所有樂器資料 =====\n\n");
        for (Woodwind instrument : instruments) {
            builder.append(instrument.getDisplayInfo()).append("\n");
            builder.append("------------------------------\n");
        }
        displayArea.setText(builder.toString());
    }

    private void calculateRent() {
        Woodwind instrument = askInstrumentById("請輸入要計算租金的樂器編號：");
        if (instrument == null) {
            return;
        }

        if (instrument.isRented()) {
            displayArea.setText("此樂器目前出借中，無法計算新的租借費用。");
            return;
        }

        String input = JOptionPane.showInputDialog(this, "請輸入租借天數：");
        if (input == null) {
            return;
        }

        try {
            int days = Integer.parseInt(input.trim());
            int total = instrument.calculateFee(days);
            displayArea.setText("===== 租金計算結果 =====\n\n"
                    + "樂器編號：" + instrument.getId() + "\n"
                    + "品牌：" + instrument.getBrand() + "\n"
                    + "樂器種類：" + instrument.getTypeName() + "\n"
                    + "租借天數：" + days + " 天\n"
                    + "總租金：" + total + " 元");
        } catch (NumberFormatException ex) {
            showMessage("租借天數請輸入整數。");
        } catch (IllegalArgumentException ex) {
            showMessage(ex.getMessage());
        }
    }

    private void rentInstrument() {
        Woodwind instrument = askInstrumentById("請輸入要租借的樂器編號：");
        if (instrument == null) {
            return;
        }

        if (!instrument.isRented()) {
            instrument.setRented(true);
            displayArea.setText("租借成功！此樂器已標記為已被借走。\n\n" + instrument.getDisplayInfo());
        } else {
            displayArea.setText("此樂器目前出借中，請選擇其他樂器。");
        }
    }

    private void returnInstrument() {
        Woodwind instrument = askInstrumentById("請輸入要歸還的樂器編號：");
        if (instrument == null) {
            return;
        }

        if (instrument.isRented()) {
            instrument.setRented(false);
            displayArea.setText("歸還成功！此樂器已恢復為在庫可租。\n\n" + instrument.getDisplayInfo());
        } else {
            displayArea.setText("此樂器目前已在庫，無需歸還。");
        }
    }

    private Woodwind askInstrumentById(String message) {
        String id = JOptionPane.showInputDialog(this, message);
        if (id == null) {
            return null;
        }

        Woodwind instrument = findById(id);
        if (instrument == null) {
            displayArea.setText("查無此樂器編號");
        }
        return instrument;
    }

    private Woodwind findById(String id) {
        if (id == null) {
            return null;
        }
        for (Woodwind instrument : instruments) {
            if (instrument.getId().equalsIgnoreCase(id.trim())) {
                return instrument;
            }
        }
        return null;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
        displayArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InstrumentRentalGUI gui = new InstrumentRentalGUI();
            gui.setVisible(true);
        });
    }
}
