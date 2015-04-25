package frontEnd;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class Editor {
	
	//Fields
	public JFrame mainFrame;
	public JToolBar editBar;
	public JMenuBar menuBar;
	public JTabbedPane tabbedPane;
	public ArrayList<FileTab> fileTabsList;

	//Connstructor
	public Editor() {
		fileTabsList = new ArrayList<FileTab>();
		this.prepareEditor();
		//this.showEditor();
	}

	/********************************************************************************************************/
	private void prepareEditor() {
		// Initialization of main Frame.
		mainFrame = new JFrame("TextEditor");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setMinimumSize(new Dimension(1200, 600));
		mainFrame.setLayout(null);

		//Tabbed Pane
		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(10, 60, 1160, 500);
	    mainFrame.add(tabbedPane);

		//MenuBar
		this.createMenu();
		menuBar.setBounds(0, 0, 1200, 20);
		mainFrame.add(menuBar);
	   
	    //creating a panel for cut, copy, paste
	    editBar= new JToolBar();
		editBar.setBounds(0, 25, 1200, 30);
	    //add edit bar to frame
		mainFrame.add(editBar);
	    //add buttons to the ToolBar
	    CreateEditBar();
	
	    mainFrame.pack();
	    mainFrame.setVisible(true);
	    
	}
	
	/********************************************************************************************************/
	public void CreateEditBar(){
		//writing cut, cpoy, paste for now
		
		JButton cut=makeButton("Cut.png", "cut selected text", "Cut");
		//cut.setPreferredSize(new Dimension(25, 25));
		JButton copy= makeButton("Copy.png", "copy selected text", "Copy");
		//copy.setPreferredSize(new Dimension(25, 25));
		JButton paste= makeButton("Paste.png", "paste selected text",  "Paste");
		//paste.setPreferredSize(new Dimension(25, 25));
		
		//adding Undo, Redo, Find, Find&Replace Buttons
		JButton undo=makeButton("Undo.png", "Undo Previous Action", "Undo");
		JButton redo=makeButton("Redo.png", "Redo Action", "Redo");
		JButton find=makeButton("Find.png", "Find A String", "Find");
		JButton findAndReplace=makeButton("FindAndReplace.png", "Find and Replace", "FindAndReplace");
		JButton bold=makeButton("Bold.png", "Make Bold", "Bold");
		JButton italic=makeButton("Italic.png", "Make Italic", "Italic");
		JButton underLine=makeButton("Underline.png", "Make Underline", "Underline");
		
		
		
		//adding the buttons
		editBar.add(cut);
		editBar.add(copy);
		editBar.add(paste);
		editBar.add(undo);
		editBar.add(redo);
		editBar.add(find);
		editBar.add(findAndReplace);
		editBar.add(bold);
		editBar.add(italic);
		editBar.add(underLine);
		
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		JComboBox<String> font = new JComboBox<String>();
		JComboBox<Integer> size = new JComboBox<Integer>();
		for(int i =0;i<fonts.length;i++)
			font.addItem(fonts[i]);
		for(int i = 12;i<33;i+=2)
			size.addItem(i);
		size.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList,"Size"));
		font.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList,"Font"));
		editBar.add(size);
		editBar.add(font);
		font.setBounds(175, 25, 150, 30);
		
	}
	//make the button
	public JButton makeButton(String imagePath, String toolTipText, String action)
	{
		
		//create new JButton
		JButton button= new JButton();
		//set the tool tip text
		button.setToolTipText(toolTipText);
		//set the Icon
		try{
			Image img= ImageIO.read(new FileInputStream(imagePath));
			img= img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(img));			
		}
		catch(IOException e){
			;
		}

		//if action listener is common
		if(action.equals("Cut")){
			button.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Cut"));
		}

		else if(action.equals("Copy")){
			button.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Copy"));
		}
		
		else if(action.equals("Paste")){
			button.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Paste"));
		}

		else if(action.equals("Find")){
			button.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Find"));				
		}
		else if(action.equals("FindAndReplace")){
			button.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "FindAndReplace"));
		}
		else if(action.equals("Undo")){
			button.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Undo"));
		}
		else if(action.equals("Redo")){
			button.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Redo"));
		}
		else if(action.equals("Bold"))
		{
			button.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Bold"));
		}
		else if(action.equals("Italic"))
		{
			button.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Italic"));
		}
		else if(action.equals("Underline"))
		{
			button.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Underline"));
		}		
		else{
			//set Action Listener
			button.addActionListener(new CommonEditBarActionListener(tabbedPane,fileTabsList,action));
		}		
		return button;
	}
	
	
	/********************************************************************************************************/
	private void createMenu() {
		menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
		//setMnemonic
		menu.setMnemonic(KeyEvent.VK_F);
		
		/*********************************************************/
		// Open a File in the new tab
		JMenuItem menuItem = menu.add("Open");
		//add Accelerators
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK , true));
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_O);
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Open"));

		/*********************************************************/
		// create a new Tab
		menuItem = menu.add("New");
		//add Accelerator
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK , true));
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "New"));

		menu.addSeparator();
		/*********************************************************/
		// save a tab
		menuItem = menu.add("Save");
		//add Accelerator
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK , true));
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Save"));

		/*********************************************************/
		// save a tab given file names
		menuItem = menu.add("Save As");
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "SaveAs"));
		
		menu.addSeparator();
		/*********************************************************/
		// exit from the Editor
		menuItem = menu.add("Quit");
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
			}
		});

		menuBar.add(menu);

		// Creating a new Edit Menu

		menu = new JMenu("Edit");
		//set Mnemonic
		menu.setMnemonic(KeyEvent.VK_E);
				
		// Adding MenuItems to the Edit Menu
		/*********************************************************/
		//Cut
		menuItem = menu.add("Cut");
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_C);
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Cut"));
		/*********************************************************/
		//Copy
		menuItem = menu.add("Copy");
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_O);		
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Copy"));
		/*********************************************************/
		// Paste
		menuItem = menu.add("Paste");
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_P);
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Paste"));
		
		menu.addSeparator();
		/*********************************************************/
		// Undo
		menuItem = menu.add("Undo");
		//set Accelerator
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK , true));
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_U);
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Undo"));

		/*********************************************************/
		// Redo
		menuItem = menu.add("Redo");
		//set Accelerator
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_DOWN_MASK , true));
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_R);
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Redo"));
		
		menu.addSeparator();
		/*********************************************************/
		//Find
		//Everytime you press find, have to check if find is already open in the tab, else open it
		//The find function is useless and very weak. Modify.
		menuItem = menu.add("Find");
		//add Accelerator
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK , true));
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_F);
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "Find"));
		
		/*********************************************************/
		//find and replace
		//Pressing this button will open a Dialog box, where the string will be found and highlighted
		//Pressing replace will replace the string in order
		//pressing escape, will as usual close the dialog box and remove the highlights
		menuItem= menu.add("Find&Replace");
		//set accelerator
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_DOWN_MASK , true));
		//set Mnemonic
		menuItem.setMnemonic(KeyEvent.VK_R);
		menuItem.addActionListener(new CommonEditBarActionListener(tabbedPane, fileTabsList, "FindAndReplace"));
		
		menuBar.add(menu);
	}


	public static void main(String[] args) {
		Editor edt = new Editor();
	}
}
