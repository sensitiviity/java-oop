package uni.com;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.UUID;

/**
 * JavaFX GUI для лабораторної роботи №16.
 * Повністю реалізує вимоги ЛР16.
 */
public class ClothesGui {
    private final Store store;
    private final ListView<String> shortListView;
    private final TextArea fullInfoArea;
    private final TextField nameField, priceField, uuidSearchField;
    private ComboBox<Size> sizeCombo;
    private ComboBox<String> typeCombo;
    private Scene scene;

    public ClothesGui(Store store) {
        this.store = store;
        this.shortListView = new ListView<>();
        this.fullInfoArea = new TextArea();
        this.nameField = new TextField();
        this.priceField = new TextField();
        this.uuidSearchField = new TextField();
        setupGui();
        refreshShortList();
    }

    private void setupGui() {
        Label nameLabel = new Label("Name:");
        Label priceLabel = new Label("Price:");
        Label sizeLabel = new Label("Size:");
        Label typeLabel = new Label("Type:");

        sizeCombo = new ComboBox<>();
        sizeCombo.getItems().addAll(Size.values());
        sizeCombo.setValue(Size.M);

        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("T-Shirt", "Pants", "Shirts", "Jeans");
        typeCombo.setValue("T-Shirt");

        Button addButton = new Button("Add Clothes");

        HBox addPanel = new HBox(8,
                nameLabel, nameField,
                priceLabel, priceField,
                sizeLabel, sizeCombo,
                typeLabel, typeCombo,
                addButton
        );
        HBox.setHgrow(nameField, Priority.ALWAYS);
        HBox.setHgrow(priceField, Priority.ALWAYS);
        addPanel.setPadding(new Insets(10));
        addPanel.setStyle("-fx-background-color: #e0f7fa;");

        VBox centerPanel = new VBox(10, new Label("All items:"), shortListView);
        VBox.setVgrow(shortListView, Priority.ALWAYS);
        centerPanel.setPadding(new Insets(10));

        Label uuidLabel = new Label("UUID (full or first 8 chars):");
        Button searchButton = new Button("Find by UUID");

        HBox searchPanel = new HBox(10, uuidLabel, uuidSearchField, searchButton);
        HBox.setHgrow(uuidSearchField, Priority.ALWAYS);
        searchPanel.setPadding(new Insets(10, 10, 5, 10));

        fullInfoArea.setEditable(false);
        fullInfoArea.setPrefRowCount(10);
        fullInfoArea.setStyle("-fx-font-family: 'Monospaced';");

        VBox bottomPanel = new VBox(5, searchPanel, new Label("Full information:"), fullInfoArea);
        bottomPanel.setPadding(new Insets(0, 10, 10, 10));

        addButton.setOnAction(e -> addClothes());
        searchButton.setOnAction(e -> searchByUuid());
        shortListView.setOnMouseClicked(e -> showSelectedInfo());

        BorderPane root = new BorderPane();
        root.setTop(addPanel);
        root.setCenter(centerPanel);
        root.setBottom(bottomPanel);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f4f4f4, #e8e8e8);");

        scene = new Scene(root, 1000, 800);
    }

    public void show(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("Clothes Store");
        stage.setResizable(true);
        stage.show();
    }

    private void addClothes() {
        try {
            String name = nameField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            Size size = sizeCombo.getValue();
            String type = typeCombo.getValue();

            if (name.isEmpty() || price <= 0 || size == null || type == null) {
                showAlert("Fill all fields");
                return;
            }

            Clothes item;
            switch (type) {
                case "T-Shirt":
                    item = new TShirts(name, "white", size, price, "Mass market", "cotton", false, false);
                    break;
                case "Pants":
                    item = new Pants(name, "black", size, price, "Mass market", "cotton", true);
                    break;
                case "Shirts":
                    item = new Shirts(name, "white", size, price, "Mass market", "cotton", true);
                    break;
                case "Jeans":
                    item = new Jeans(name, "blue", size, price, "Mass market", "denim", true, false);
                    break;
                default:
                    return;
            }

            store.addNewClothes(item, 1);
            refreshShortList();
            clearAddFields();
            showAlert("Added " + item.getClass().getSimpleName() + "\nUUID: " + item.getUuid().toString().substring(0, 8));
        } catch (NumberFormatException e) {
            showAlert("Incorrect price");
        } catch (Exception e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    private void searchByUuid() {
        try {
            String uuidStr = uuidSearchField.getText().trim();
            if (uuidStr.isEmpty()) {
                fullInfoArea.setText("Enter UUID.");
                return;
            }

            Clothes found = store.searchByUuid(uuidStr);
            if (found != null) {
                fullInfoArea.setText("Found:\n" + found.toString());
                fullInfoArea.setStyle("-fx-text-fill: green;");
            } else {
                fullInfoArea.setText("UUID not found: " + uuidStr);
                fullInfoArea.setStyle("-fx-text-fill: red;");
            }
        } catch (IllegalArgumentException e) {
            fullInfoArea.setText("Incorrect UUID: " + e.getMessage());
            fullInfoArea.setStyle("-fx-text-fill: red;");
        }
    }

    private void showSelectedInfo() {
        String selected = shortListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                String uuidStr = selected.split("\\| UUID: ")[1].trim();
                Clothes found = store.searchByUuid(uuidStr);
                if (found != null) {
                    fullInfoArea.setText("Chosen:\n" + found.toString());
                    fullInfoArea.setStyle("-fx-text-fill: blue;");
                }
            } catch (Exception e) {
                fullInfoArea.setText("Loading error");
            }
        }
    }

    private void refreshShortList() {
        shortListView.getItems().clear();
        for (Clothes c : store.getClothesList()) {
            String shortInfo = c.getClass().getSimpleName() + ": " + c.getName() + " | UUID: " + c.getUuid().toString().substring(0, 8);
            shortListView.getItems().add(shortInfo);
        }
    }

    private void clearAddFields() {
        nameField.clear();
        priceField.clear();
        sizeCombo.setValue(Size.M);
        typeCombo.setValue("T-Shirt");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}