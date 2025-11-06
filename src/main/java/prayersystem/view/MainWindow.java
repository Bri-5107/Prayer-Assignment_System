package prayersystem.view;

import prayersystem.model.*;
import prayersystem.data.FamilyManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import com.google.gson.Gson;
import javax.swing.Timer;



public class MainWindow extends JFrame implements ActionListener {
    JPanel familyListPanel, memberListPanel, header1, header2, addFamily, addMember, inputPanel, generatePanel, headerPanel;
    JLabel family, member, familyName, familyMembers, memberName, timeLabel;
    JButton add1,add2, delete1, delete2, save1, save2, generate;
    javax.swing.table.DefaultTableModel dataModel1, dataModel2; 
    JTable table1, table2;
    JTextField familyNameInput, familyMembersInput, memberNameInput;
    JDialog dialog1, dialog2;
    JMenuBar menuBar;
    JMenu file;
    JMenuItem exitItem, aboutItem, generateItem;
    JSplitPane splitPane;
    private Timer timer;
    private FamilyManager manager;
    
    public MainWindow(){
        manager = new FamilyManager();
        setTitle("Church Prayer Assignment System");
        setSize(500,500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                 syncManagerWithTables();
                manager.saveToFile();
                System.out.println("[INFO] Window closing - data saved");
                System.exit(0);
            }
        });
         
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1,2));
        
        
        // create Timer
        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timer = new Timer(1000, e -> timeLabel.setText("" + new java.util.Date()));
        timer.start();
        
        // create Menu 
        
        menuBar = new JMenuBar();
        file = new JMenu("File");
        file.setFont(new Font("Arial", Font.BOLD, 14));
        
        generateItem = new JMenuItem("Generate Assignments");
        generateItem.setFont(new Font("Arial", Font.PLAIN, 12));
        generateItem.addActionListener(this);
        
  
        exitItem = new JMenuItem("Exit");
        exitItem.setFont(new Font("Arial", Font.PLAIN, 12));
        exitItem.addActionListener(this);


        aboutItem = new JMenuItem("About");
        aboutItem.setFont(new Font("Arial", Font.PLAIN, 12));
        aboutItem.addActionListener(this);


        file.add(generateItem);
        file.addSeparator();
        file.add(aboutItem);
        file.addSeparator();
        file.add(exitItem);

        menuBar.add(file);
        menuBar.add(Box.createHorizontalGlue()); // Pushes timer to the right
        menuBar.add(timeLabel);


        setJMenuBar(menuBar);
        
        // create header
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(79, 70, 229)); // Indigo
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Church Prayer Assignment System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Assign families to prayer members fairly");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(224, 231, 255));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(new Color(79, 70, 229));
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);
        
        headerPanel.add(textPanel, BorderLayout.WEST);
        String[] columnNames1 = {"Family Name", "Members"};
        Object[][] data1 = {{"Cruz", "Maria Cruz, Josie Cruz"}}; 
        table1 = new JTable (data1, columnNames1);
        add(headerPanel, BorderLayout.NORTH);

        
        // create panel separator
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(2); 
        splitPane.setResizeWeight(0.5); 
        splitPane.setDividerLocation(0.5); 
        
        
        // call memerListPanel
        memberListPanel = new JPanel();
        memberListPanel.setBackground(Color.blue);
        splitPane.setLeftComponent(memberListPanel);
        family = new JLabel("Family");
        
        header1 = new JPanel();
        header1.add(family);
        
        add1 = new JButton("Add");
        delete1 = new JButton("Delete");
        header1.add(add1);
        header1.add(delete1);
               
        add1.addActionListener(this);
        delete1.addActionListener(this);
        
        memberListPanel.setLayout(new BorderLayout());
        memberListPanel.add(header1, BorderLayout.NORTH);
        memberListPanel.add(table1, BorderLayout.CENTER);
        
        dataModel1 = new javax.swing.table.DefaultTableModel(columnNames1,0);
        
      
        // call familyListPanel
        familyListPanel = new JPanel();
        familyListPanel.setBackground(Color.red);
        splitPane.setRightComponent(familyListPanel);
        member = new JLabel("Member");
        familyListPanel.add(member);
        add2 = new JButton("Add");
        delete2 = new JButton("Delete");
        familyListPanel.add(add2);
        familyListPanel.add(delete2);
        
        add2.addActionListener(this);
        delete2.addActionListener(this);
        
        String[] columnNames2= {"Member Name", "Present"};
        Object[][] data2 = {{"Malino Cruz", false}};
        
 
        
        table2 = new JTable(data2, columnNames2);
        dataModel2 = new javax.swing.table.DefaultTableModel(columnNames2, 0) {
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex == 1) {
                    return Boolean.class;
                }
                
                return String.class;
            }
          
        };
                
        header2 = new JPanel();
        header2.add(member);
        header2.add(add2);
        header2.add(delete2);
        
        
        familyListPanel.setLayout(new BorderLayout());
        familyListPanel.add(header2, BorderLayout.NORTH);
        familyListPanel.add(table2, BorderLayout.CENTER);
        
        //create generate button
        generatePanel = new JPanel();
        generatePanel.setLayout(new GridBagLayout());
        generatePanel.setBackground(new Color(79, 70, 229)); // Indigo
        
        
        add(inputPanel, BorderLayout.CENTER);
        add(generatePanel, BorderLayout.SOUTH);
        generate = new JButton("Generate");
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        generatePanel.add(generate,c);
        generate.addActionListener(this);
   
 
       table1.setModel(dataModel1);
       table2.setModel(dataModel2);
       inputPanel.add(splitPane, BorderLayout.CENTER);
       
       loadDataIntoTable();
       
        setVisible(true);
    }
    
    //helper method
    
    private void loadDataIntoTable(){
        dataModel1.setRowCount(0);
        dataModel2.setRowCount(0);
    
        // Load families into table1
        for (Family family : manager.getFamilies()) {
            String membersString = String.join(", ", family.getFamilyMembers());
            dataModel1.addRow(new Object[]{family.getFamilyName(), membersString});
        }

        // Load members into table2
        for (PrayerMember member : manager.getMembers()) {
            dataModel2.addRow(new Object[]{member.getMemberName(), member.getPresent()});
        }

        System.out.println("[INFO] Data loaded into tables");
    }
    
    private void syncManagerWithTables() {
      
        manager.getFamilies().clear();
        manager.getMembers().clear();


        for (int i = 0; i < dataModel1.getRowCount(); i++) {
            String familyName = (String) dataModel1.getValueAt(i, 0);
            String membersString = (String) dataModel1.getValueAt(i, 1);
            String[] membersArray = membersString.split(",\\s*");
            List<String> membersList = Arrays.asList(membersArray);
            manager.getFamilies().add(new Family(familyName, membersList));
        }

        for (int i = 0; i < dataModel2.getRowCount(); i++) {
            String memberName = (String) dataModel2.getValueAt(i, 0);
            Boolean isPresent = (Boolean) dataModel2.getValueAt(i, 1);
            PrayerMember member = new PrayerMember(memberName);
            member.setPresent(isPresent);
            manager.getMembers().add(member);
        }

        System.out.println("[INFO] Synced " + manager.getFamilies().size() + 
                          " families and " + manager.getMembers().size() + " members");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add1) {
            
       addFamily = new JPanel();
       addFamily.setLayout(new GridBagLayout());
       GridBagConstraints c = new GridBagConstraints();
       
       familyName = new JLabel("Enter Family Name: ");
       c.anchor = GridBagConstraints.FIRST_LINE_START;
       c.gridx = 0;
       c.gridy = 0;
       addFamily.add(familyName,c);
       
       familyMembers= new JLabel("Enter Family Members: ");
       c.anchor = GridBagConstraints.FIRST_LINE_END;
       c.gridx = 0;
       c.gridy = 1;
       addFamily.add(familyMembers,c);
       
       familyNameInput = new JTextField(15);
       c.anchor = GridBagConstraints.LINE_START;
       c.gridx = 2;
       c.gridy = 0;
       addFamily.add(familyNameInput,c);
       
       familyMembersInput = new JTextField(15);
       c.anchor = GridBagConstraints.LINE_END;
       c.gridx = 2;
       c.gridy = 1;
       addFamily.add(familyMembersInput,c);
       
       // Create button as LOCAL variable instead of using instance variable
       save1 = new JButton("Save");
       c.anchor = GridBagConstraints.PAGE_END;
       c.gridx = 1;
       c.gridy = 2;
       c.insets = new Insets (6,0,0,0);
       addFamily.add(save1,c);
      
       
       dialog1 = new JDialog(this, "Add Family", true);
       dialog1.getContentPane().add(addFamily);
       dialog1.setLocationRelativeTo(this);
       dialog1.setSize(450, 200);
     
        save1.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e) {
                
                String familyMembersAsString = familyMembersInput.getText();
                String[] familyMembersSplit = familyMembersAsString.split(",");
                List<String> familyMembersAsList = new ArrayList<String>(Arrays.asList(familyMembersSplit));
                
                Family family = new Family(familyNameInput.getText(), familyMembersAsList);
              
                
                Gson gson = new Gson();
                String json = gson.toJson(family);
                System.out.println("JSON: " + json);
                
                manager.addFamily(family);
                
                loadDataIntoTable();
                
                // Save to file
                manager.saveToFile();
                
                dialog1.dispose();
             }
             
          
          
        });
        
        dialog1.setVisible(true);
 

               

           
                
        } else if (e.getSource() == delete1) {
          
            
            manager.removeFamily(table1.getSelectedRow());
            
            loadDataIntoTable();
            
            // Save to file
            manager.saveToFile();
            
        } else if (e.getSource() == add2) {
             addMember = new JPanel();
       addMember.setLayout(new GridBagLayout());
       GridBagConstraints c = new GridBagConstraints();
       
       memberName = new JLabel("Enter Member Name: ");
       c.anchor = GridBagConstraints.FIRST_LINE_START;
       c.gridx = 0;
       c.gridy = 0;
       addMember.add(memberName,c);
      
       
       memberNameInput = new JTextField(15);
       c.anchor = GridBagConstraints.LINE_START;
       c.gridx = 2;
       c.gridy = 0;
       addMember.add(memberNameInput,c);
       
     
      
       save2 = new JButton("Save");
       c.anchor = GridBagConstraints.PAGE_END;
       c.gridx = 1;
       c.gridy = 2;
       c.insets = new Insets (6,0,0,0);
       addMember.add(save2,c);
      
       
       dialog2 = new JDialog(this, "Add Member", true);
       dialog2.getContentPane().add(addMember);
       dialog2.setLocationRelativeTo(this);
       dialog2.setSize(450, 200);
     
        save2.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e) {
                 
                PrayerMember member = new PrayerMember(memberNameInput.getText());
                Gson gson = new Gson();
                String json = gson.toJson(member);
                System.out.println("JSON: " + json);
                
                manager.addMember(member);
                
                 loadDataIntoTable();
                
                // Save to file
                manager.saveToFile();
                
            
               dialog2.dispose();
             }
             
          
          
        });
        
        dialog2.setVisible(true);
        
        } else if (e.getSource() == delete2) {
           
            
            manager.removeMember(table2.getSelectedRow());
            
            loadDataIntoTable();
            
            // Save to file
            manager.saveToFile();
            
        } else if (e.getSource() == generate){
            
            //convert dataModel1, dataModel2 to List
            List<Family> allFamilies = new ArrayList<>();
            List<PrayerMember> allMembers = new ArrayList<>();
            
            for (int i = 0; i < dataModel1.getRowCount(); i++) {
                
               String family=  (String) dataModel1.getValueAt(i, 0);
               String familyMembers = (String) dataModel1.getValueAt(i, 1);
               String[] membersArray = familyMembers.split(",\\s*");
               List<String> membersList = new ArrayList<>();
               membersList.add(familyMembers);
               allFamilies.add(new Family(family,membersList));
               
               
            }
            
            for(int i = 0; i < dataModel2.getRowCount(); i++) {
                
                String members = (String) dataModel2.getValueAt(i, 0);
                Boolean isPresent = (Boolean) dataModel2.getValueAt(i, 1);
                PrayerMember member = new PrayerMember(members);
                member.setPresent(isPresent);
                allMembers.add(member);
            }
            
            Map<PrayerMember, Family> assignments =
            PrayerAssigner.assignFamily(allMembers, allFamilies);

            
            StringBuilder result = new StringBuilder("Assignments:\n");
            for (Map.Entry<PrayerMember, Family> entry : assignments.entrySet()) {
                result.append(entry.getKey().getMemberName())
                      .append(" → ")
                      .append(entry.getValue().getFamilyName())
                      .append("   Members: ")
                      .append(entry.getValue().getFamilyMembers())
                      .append("\n\n");  
            }

            JOptionPane.showMessageDialog(this, result.toString(),
                "Family Assignments", JOptionPane.INFORMATION_MESSAGE);
           
             
        
        } else if (e.getSource() == exitItem) {
            // Same as window closing
            syncManagerWithTables();
            manager.saveToFile();
            System.out.println("[INFO] Exit menu clicked - data saved");
            System.exit(0);

        } else if (e.getSource() == generateItem) {
            // Same logic as generate button
            //convert dataModel1, dataModel2 to List
            List<Family> allFamilies = new ArrayList<>();
            List<PrayerMember> allMembers = new ArrayList<>();

            for (int i = 0; i < dataModel1.getRowCount(); i++) {
                String family = (String) dataModel1.getValueAt(i, 0);
                String familyMembers = (String) dataModel1.getValueAt(i, 1);
                String[] membersArray = familyMembers.split(",\\s*");
                List<String> membersList = Arrays.asList(membersArray);
                allFamilies.add(new Family(family, membersList));
            }

            for(int i = 0; i < dataModel2.getRowCount(); i++) {
                String members = (String) dataModel2.getValueAt(i, 0);
                Boolean isPresent = (Boolean) dataModel2.getValueAt(i, 1);
                PrayerMember member = new PrayerMember(members);
                member.setPresent(isPresent);
                allMembers.add(member);
            }

            Map<PrayerMember, Family> assignments =
                PrayerAssigner.assignFamily(allMembers, allFamilies);

            StringBuilder result = new StringBuilder("Assignments:\n\n");
            for (Map.Entry<PrayerMember, Family> entry : assignments.entrySet()) {
                result.append(entry.getKey().getMemberName())
                      .append(" → ")
                      .append(entry.getValue().getFamilyName())
                      .append("\n   Members: ")
                      .append(String.join(", ", entry.getValue().getFamilyMembers()))
                      .append("\n\n");  
            }

            JOptionPane.showMessageDialog(this, result.toString(),
                "Family Assignments", JOptionPane.INFORMATION_MESSAGE);

        } else if (e.getSource() == aboutItem) {
            // About Dialog
            String aboutText = 
                "Church Prayer Assignment System\n" +
                "Version 1.0\n\n" +
                "DESCRIPTION:\n" +
                "This application helps organize prayer assignments\n" +
                "for church members. It ensures fair distribution of\n" +
                "families to pray for, with the following features:\n\n" +
                "• One family assigned per member\n" +
                "• Members don't pray for their own family\n" +
                "• Queue rotation system for fair distribution\n" +
                "• Unassigned families get priority next time\n" +
                "• Attendance tracking via checkboxes\n\n" +
                "HOW TO USE:\n" +
                "1. Add families and their members\n" +
                "2. Add prayer members\n" +
                "3. Check 'Present' for attending members\n" +
                "4. Click 'Generate' to create assignments\n\n" +
                "Developed by: Brian Daniel C. Dionisio \n" +
                "© 2025";

            JOptionPane.showMessageDialog(this, 
                aboutText,
                "About Prayer Assignment System", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
    
