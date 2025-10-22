/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package prayersystem;
import prayersystem.view.MainWindow;
import javax.swing.*;


/**
 *
 * @author briandionisio
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainWindow window = new MainWindow();
                window.setVisible(true);
            }
        });
     
    }
}
