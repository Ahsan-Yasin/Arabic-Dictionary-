package UiLayer;

import javax.swing.*;

public interface UI {
   void display();
//    default void showOnEDT() {
//        SwingUtilities.invokeLater(this::display);
//    }
}
