import javax.swing.JFrame;

public class Maxwell {


    private static void createAndShowGUI(){

        GameWindow gameWindow = new GameWindow();
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        gameWindow.setTitle("Maxwell's Demon");
        gameWindow.setSize(800, 600);
        gameWindow.setVisible(true);
    }
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());

    }
    
}