# 樂器行庫存與租借管理系統

使用 Java 實作木管樂器庫存與租借管理。

## 系統功能

- 新增樂器資料，並存入 `ArrayList`
- 顯示所有庫存資料
- 依樂器編號精確查詢資料
- 輸入租借天數後，自動計算含保養費的總租金

## 物件導向設計

- `Woodwind`：父類別，封裝編號、品牌、日租金
- `Flute`：長笛，專屬屬性為是否開孔，保養費 150 元
- `Clarinet`：豎笛，專屬屬性為豎笛種類，保養費 200 元
- `Saxophone`：薩克斯風，專屬屬性為薩克種類，保養費 250 元
- `InventoryManager`：使用 `ArrayList<Woodwind>` 管理資料
- `Main`：選單式主程式

## 編譯與執行

```powershell
javac -encoding UTF-8 -d out src\*.java
java -cp out Main
```

## 範例資料

系統啟動時會先建立三筆範例資料：

- `F001` Yamaha 長笛
- `C001` Buffet Crampon 豎笛
- `S001` Selmer 薩克斯風
