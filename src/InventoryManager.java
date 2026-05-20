import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryManager {
    private final ArrayList<Woodwind> instruments = new ArrayList<>();

    public boolean addInstrument(Woodwind instrument) {
        if (instrument == null) {
            throw new IllegalArgumentException("樂器資料不可為空");
        }
        if (findById(instrument.getId()) != null) {
            return false;
        }
        instruments.add(instrument);
        return true;
    }

    public Woodwind findById(String id) {
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

    public boolean isEmpty() {
        return instruments.isEmpty();
    }

    public List<Woodwind> getInstruments() {
        return Collections.unmodifiableList(instruments);
    }

    public void showAll() {
        if (instruments.isEmpty()) {
            System.out.println("目前沒有任何樂器資料。");
            return;
        }

        System.out.println("\n===== 樂器庫存清單 =====");
        for (Woodwind instrument : instruments) {
            instrument.showInfo();
        }
    }
}
