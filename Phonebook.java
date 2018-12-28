/*
	Filename:	Phonebook.java
	Purpose:	Maintains a phone book with ArrayLists
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Phonebook extends JFrame implements ActionListener
{
	ArrayList nameArray = new ArrayList();
	ArrayList numberArray = new ArrayList();

	String name;
	String number;

    JButton jbtAdd, jbtDelete, jbtUpdate, jbtFind, jbtClear;
    JTextField jtfName, jtfNumber;

	public static void main(String[] argv)
    {
		int width = 370;
		int height = 130;

        Phonebook f = new Phonebook();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	f.setSize(width, height);
    	f.centerOnScreen(width, height);
    	f.setVisible(true);
    }

    public Phonebook()
    {
		super("Phonebook");

        JLabel label1 = new JLabel("Name:       ");
        jtfName = new JTextField(15);

        JLabel label2 = new JLabel("Phone:	");
        jtfNumber = new JTextField(15);

        jbtAdd = new JButton("Add");
        jbtDelete = new JButton("Delete");
        jbtUpdate = new JButton("Update");
        jbtFind = new JButton("Find");
        jbtClear = new JButton("Clear");

        jbtAdd.addActionListener(this);
        jbtDelete.addActionListener(this);
        jbtUpdate.addActionListener(this);
        jbtFind.addActionListener(this);
        jbtClear.addActionListener(this);

        JPanel namePanel= new JPanel(new BorderLayout(10,10));
        namePanel.add(label1,BorderLayout.WEST);
        namePanel.add(jtfName,BorderLayout.EAST);

        JPanel numPanel= new JPanel(new BorderLayout(19,19));
        numPanel.add(label2,BorderLayout.WEST);
        numPanel.add(jtfNumber,BorderLayout.EAST);

        JPanel buttonPanel= new JPanel(new FlowLayout());
        buttonPanel.add(jbtAdd);
        buttonPanel.add(jbtDelete);
        buttonPanel.add(jbtUpdate);
        buttonPanel.add(jbtFind);
        buttonPanel.add(jbtClear);

        JPanel contentPanel= new JPanel(new FlowLayout());
        contentPanel.add(namePanel);
        contentPanel.add(numPanel);
        contentPanel.add(buttonPanel);
        setContentPane(contentPanel);

		// Enable Enter key for each JButton so user can tab to button
		// and press the Enter key, rather than click button with mouse
		InputMap map;
		map = jbtAdd.getInputMap();
		if (map != null){
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false), "pressed");
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), "released");
		}
		map = jbtDelete.getInputMap();
		if (map != null){
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false), "pressed");
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), "released");
		}
		map = jbtUpdate.getInputMap();
		if (map != null){
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false), "pressed");
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), "released");
		}
		map = jbtFind.getInputMap();
		if (map != null){
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), "released");
		}
    }

  	public void centerOnScreen(int width, int height)
  	{
  	  int top, left, x, y;

  	  // Get the screen dimension
  	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

  	  // Determine the location for the top left corner of the frame
  	  x = (screenSize.width - width)/2;
  	  y = (screenSize.height - height)/2;
  	  top = (x < 0) ? 0 : x;
  	  left = (y < 0) ? 0 : y;

  	  // Set the frame to the specified location
  	  this.setLocation(top, left);
  	}

	public void actionPerformed(ActionEvent e) {
        boolean added = true;

        try {
            if (e.getSource() == jbtAdd)    //user clicked the add button
            {
                name = (jtfName.getText()).trim();
                number = (jtfNumber.getText()).trim();

                if (name.length() > 0 && number.length() > 0)
                    added = nameArray.add(name) && numberArray.add(number);
                if (added) {
                    JOptionPane.showMessageDialog(this, "Contact saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } else if (name.length() == 0) {
                    JOptionPane.showMessageDialog(this, "No name provided", "Try Again", JOptionPane.ERROR_MESSAGE);
                    jtfName.requestFocus();
                } else if (number.length() > 0) {
                    JOptionPane.showMessageDialog(this, "No number provided", "Try Again", JOptionPane.ERROR_MESSAGE);
                    jtfNumber.requestFocus();
                }
            }
            else if (e.getSource() == jbtDelete)    //user clicked the delete button
            {
                if (validName()) {
                    int contactIndex = getIndex();
                    nameArray.remove(contactIndex);
                    numberArray.remove(contactIndex);
                    JOptionPane.showMessageDialog(null,"The contact "+jtfName.getText()+" was successfully removed ","Success",JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                }
            }
            else if(e.getSource() == jbtUpdate)
            {
                int x = getIndex();
                if((jtfNumber.getText().trim()).equals(""))
                JOptionPane.showMessageDialog(this,"Please enter a valid contact number", "Error", JOptionPane.ERROR_MESSAGE);
                else {
                numberArray.set(x, jtfNumber.getText());
                JOptionPane.showMessageDialog(null,"The contact was successfully updated","Success",JOptionPane.INFORMATION_MESSAGE);
                clearFields();}

            }
            else if(e.getSource() == jbtFind)
            {
                int y = getIndex();
                jtfNumber.setText((String)numberArray.get(y));
            }
            else if(e.getSource() == jbtClear)
                clearFields();

        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Fields were not populated appropriately", "Try Again", JOptionPane.ERROR_MESSAGE);
        }
    }

        public boolean validName()
        {
            boolean valid = false;
            if(nameArray.contains(jtfName.getText()))
                valid = true;
            return valid;
        }

        public int getIndex()
        {
            int index=0;

            if(validName())
            {
                for(int i = 0;i<nameArray.size();i++)
                {
                    if(jtfName.getText().equals(nameArray.get(i)))
                        index = i;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"The list does not contain the name "+jtfName.getText(),"Error",JOptionPane.ERROR_MESSAGE);
                clearFields();
            }
            return index;
        }

        public void clearFields()
        {
            jtfName.setText("");
            jtfNumber.setText("");
            jtfName.requestFocus();
        }

}