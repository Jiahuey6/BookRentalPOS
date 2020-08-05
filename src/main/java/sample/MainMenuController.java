package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    public Button transactionButton;
    public Button bookButton;
    public Button memberButton;
    public Button profileButton;
    public Button logOutButton;
    public Button confirmLogOutButton;
    public Button cancelLogOutButton;
    public static Stage mainWindow;

    public void toManageTransaction(MouseEvent event) throws IOException {
        Parent manageBookParent = FXMLLoader.load(getClass().getResource("/FXML/TransactionManager/transactionChoose.fxml"));
        Scene manageBookScene = new Scene(manageBookParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Transaction Manager - Huahee Library");
        window.setScene(manageBookScene);
    }

    public void toManageBook(MouseEvent event) throws IOException {
        Parent manageBookParent = FXMLLoader.load(getClass().getResource("/FXML/BookManager/bookManager.fxml"));
        Scene manageBookScene = new Scene(manageBookParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Book Manager - Huahee Library");
        window.setScene(manageBookScene);
    }

    public void toManageMember(MouseEvent event) throws IOException {
        Parent manageBookParent = FXMLLoader.load(getClass().getResource("/FXML/MemberManager/memberManager.fxml"));
        Scene manageBookScene = new Scene(manageBookParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Member Manager - Huahee Library");
        window.setScene(manageBookScene);
    }

    public void popEditProfile() throws IOException {
        Parent editProfileParent = FXMLLoader.load(getClass().getResource("/FXML/EditProfile/editProfile.fxml"));
        Stage profileWindow = new Stage();

        profileWindow.initModality(Modality.APPLICATION_MODAL);
        profileWindow.setTitle("Edit Profile - Huahee Library");
        profileWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        profileWindow.setScene(new Scene(editProfileParent, 600, 350));
        profileWindow.showAndWait();
    }

    public void popLogOut(MouseEvent event) throws IOException {
        Parent logOutParent = FXMLLoader.load(getClass().getResource("/FXML/logOut.fxml"));
        Stage logOutWindow = new Stage();
        mainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();

        logOutWindow.initModality(Modality.APPLICATION_MODAL);
        logOutWindow.setTitle("Logout - Huahee Library");
        logOutWindow.getIcons().add(new Image(Main.class.getResourceAsStream("/Image/icon.png")));
        logOutWindow.setScene(new Scene(logOutParent, 400, 150));
        logOutWindow.setResizable(false);
        logOutWindow.showAndWait();
    }

    public void confirmLogOut(MouseEvent event) throws IOException {
        Parent loginScreenParent = FXMLLoader.load(getClass().getResource("/FXML/loginInterface.fxml"));
        Scene loginScreenScene = new Scene(loginScreenParent, 800, 600);

        mainWindow.setScene(loginScreenScene);
        mainWindow.centerOnScreen();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void cancelLogOut(MouseEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}