import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientGUI extends Application {

    private static String HOST = "127.0.0.1";
    private static int PORT = 8081;

    private Client client;

    final ChoiceBox<String> typeInput = new ChoiceBox<String>();
    final ChoiceBox<String> brandInput = new ChoiceBox<String>();
    final TextField maxInput = new TextField();
    final ChoiceBox<String> warehouseInput = new ChoiceBox<String>();

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {

        //creates a client object for the GUI to work with
        linkClient();

        stage.setTitle("Instrument Manager");

        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //type field
        final Label typeLabel = new Label("Instrument Type");
        typeInput.setItems(FXCollections.observableArrayList("Guitar", "Bass", "Keyboard"));
        
        grid.add(typeLabel, 0, 1);
        grid.add(typeInput, 1, 1);

        //brand field
        final Label brandLabel = new Label("Instrument Brand");
// TODO: make this dynamically populate
        brandInput.setItems(FXCollections.observableArrayList("Yamaha", "Gibson", "Fender", "Roland", "Alesis"));
       
        grid.add(brandInput, 1, 2);
        grid.add(brandLabel, 0, 2);

        //maximum cost field
        final Label maxLabel = new Label("Maximum Cost $");
        
        grid.add(maxLabel, 0, 3);
        grid.add(maxInput, 1, 3);

        //warehouse field
        Label warehouseLabel = new Label("Warehouse");
        warehouseInput.setItems(FXCollections.observableArrayList("PNS", "CLT", "DFW"));
        
        grid.add(warehouseLabel, 0, 4);
        grid.add(warehouseInput, 1, 4);

        //submit button
        final Button submit = new Button("Submit");
        grid.add(submit, 0, 5);

        submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent e) {
                //send data to server
                client.send(getOutput());
                //wait for response
                //getResponse();
            }
        });

        Scene scene = new Scene(grid, 400, 275);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * creates a client object for the GUI to work with
     */
    public void linkClient() {
        client = new Client(HOST, PORT);
    }

    private String getOutput() {
        StringBuilder output = new StringBuilder();
        
        output.append(dataReader(typeInput.getValue()));
        output.append(dataReader(brandInput.getValue()));
        
        //if not empty, append value
        if(! maxInput.getText().trim().isEmpty()) {
            output.append(maxInput.getText().toString() + " ");
        } else {
            output.append("null ");
        }

        output.append(dataReader(warehouseInput.getValue()));
        
        System.out.println(output.toString());
        return output.toString();
    }

    private String dataReader(String str) {
        if (str != null) {
            return (str + " ");
        } else {
            return ("all ");
        }
    }
}