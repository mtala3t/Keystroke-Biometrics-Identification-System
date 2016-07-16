import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class KeyStrokeUI extends JFrame implements ActionListener,ItemListener,ChangeListener {

	private JMenuBar myMenuBar;
    private JMenu fileMenu,optionMenu,windowMenu,editMenu,helpMenu,viewMenu;
    private JMenuItem exitItem,newUserItem,existingUserItem,changeBgColor,changeStyle;
    private JMenuItem helpContentsItem,aboutItem;
    private JTabbedPane myTabbedPane;
    
    private KeyStrokeIndexPanel indexPanel;
    
    private String strings[] = {"1. Metal", "2. Motif", "3. Windows"};
    private UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();
    private ButtonGroup group = new ButtonGroup();
    private JRadioButtonMenuItem radio[] = new JRadioButtonMenuItem[strings.length];
    
    Dimension d=Toolkit.getDefaultToolkit().getScreenSize() ;
    int width=(int)d.getWidth();
    int height=(int)d.getHeight();
    
    public KeyStrokeUI() {

		setTitle("::Keystroke Biometrics Identification System::");
		setBounds(300, 60, 700, 580);
		setIconImage(getToolkit().getImage("Images/Home.gif"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				Exit();
			}
		});

		myMenuBar = new JMenuBar();

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic((int) 'E');
		viewMenu = new JMenu("View");
		viewMenu.setMnemonic((int) 'V');
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic((int) 'H');

		exitItem = new JMenuItem("Exit");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				Event.CTRL_MASK));
		exitItem.setMnemonic('Q');
		exitItem.addActionListener(this);

		newUserItem = new JMenuItem("New User", new ImageIcon("Images/add.gif"));
		newUserItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		newUserItem.setMnemonic((int)'N');
		newUserItem.addActionListener(this);
        
        existingUserItem = new JMenuItem("Existing User", new ImageIcon("Images/find.gif"));
        existingUserItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
        existingUserItem.setMnemonic((int)'W');
        existingUserItem.addActionListener(this);
		
		optionMenu = new JMenu("Options");
		optionMenu.setMnemonic('O');

		windowMenu = new JMenu("Window");
		windowMenu.setMnemonic((int) 'W');

		changeBgColor = new JMenuItem("Change Background Color");
		changeBgColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
				Event.CTRL_MASK));
		changeBgColor.setMnemonic('G');
		changeBgColor.addActionListener(this);

		changeStyle = new JMenu("Change Look and Feel");
		changeStyle.setMnemonic((int) 'S');
		for (int i = 0; i < radio.length; i++) {
			radio[i] = new JRadioButtonMenuItem(strings[i]);
			radio[0].setSelected(true);
			radio[i].addItemListener(this);
			group.add(radio[i]);
			changeStyle.add(radio[i]);
		}

		
		helpContentsItem = new JMenuItem("Help Contents", new ImageIcon(
				"Images/Glass.gif"));
		helpContentsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				Event.CTRL_MASK));
		helpContentsItem.setMnemonic((int) 'H');
		helpContentsItem.addActionListener(this);

		aboutItem = new JMenuItem("About Keystroke Biometrics Identification System",
				new ImageIcon("Images/Windows.gif"));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				Event.CTRL_MASK));
		aboutItem.setMnemonic((int) 'L');
		aboutItem.addActionListener(this);

		optionMenu.add(changeBgColor);
		optionMenu.addSeparator();
		optionMenu.add(changeStyle);

		fileMenu.add(newUserItem);
		fileMenu.add(existingUserItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		
		
		optionMenu.add(changeBgColor);
		optionMenu.addSeparator();
		optionMenu.add(changeStyle);

		
		helpMenu.add(helpContentsItem);
		helpMenu.add(aboutItem);

		myMenuBar.add(fileMenu);
		myMenuBar.add(editMenu);
		myMenuBar.add(viewMenu);
		myMenuBar.add(optionMenu);
		myMenuBar.add(windowMenu);
		myMenuBar.add(helpMenu);

		setJMenuBar(myMenuBar);

		myTabbedPane = new JTabbedPane(JTabbedPane.TOP);

		 indexPanel=new KeyStrokeIndexPanel("Help/Index.htm");
		 indexPanel.setName("Index");

		myTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		 myTabbedPane.addTab("Welcome",new
		 ImageIcon("Images/Home.gif"),indexPanel);

		Component comps[] = myTabbedPane.getComponents();
		for (int i = 0; i < comps.length; i++) {
			comps[i].setBackground(new Color(204, 255, 204));
			comps[i].repaint();
		}
		myTabbedPane.addChangeListener(this);
		add(myTabbedPane);
		setVisible(true);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj=e.getSource();
        if(obj.equals(exitItem)) {
            Exit();
        }
        
        if(obj.equals(newUserItem)){
        	new NewUserUI();
        }
        
        if(obj.equals(existingUserItem)){
        	new LoginUI();
        }
        
        if(obj==changeBgColor) {
            Color color = myTabbedPane.getBackground();
            color = JColorChooser.showDialog(this, "Choose Background Color", color);
            if(color==null){} else {
                Component comps[]=myTabbedPane.getComponents();
                for(int i=0;i<comps.length;i++) {
                    comps[i].setBackground(color);
                    comps[i].repaint();
                }
                
            }
        }
        
    
        if(obj==aboutItem) {
            String msg = "Keystroke Biometrics Identification System.\n\n" + "Created & Designed By:\n" +
                    "  -> Mohamed Talaat Saad\n  -> Mohamed Sami Ismail\n  -> Mahmoud Fathy Mohamed\n";
            JOptionPane.showMessageDialog(this, msg, "About Kamosi Personal Dictionary", JOptionPane.PLAIN_MESSAGE);
        }
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
        for( int i=0;i<radio.length; i++ )
            if(radio[i].isSelected()) {
            changeLookAndFeel(i);
            }
    }
    
    public void changeLookAndFeel(int value) {
        try {
            UIManager.setLookAndFeel(looks[value].getClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) { }
        
    }
    
	public void Exit() {
        int res=JOptionPane.showConfirmDialog(this,"Are you sure you want to exit?","Exit Confirmation",JOptionPane.YES_NO_OPTION);
        
        if(res==JOptionPane.YES_OPTION) {
            
            SoundManger souManger=new SoundManger("Sounds/goodbye.wav");
            souManger.play();
            
            setVisible(false);
            dispose();
            System.exit(0);
           
        } else {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }

	public static void main(String args[]){
		new KeyStrokeUI();
	}
}
