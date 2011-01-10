package io.s4.example.twittertopiccount.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;

import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TwitterUI extends javax.swing.JFrame {

    /** Creates new form TwitterUI */
    public TwitterUI() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated   by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        this.setSize(569, 580);
        this.setBackground(Color.white);
 //       readTopNTweets();

        getContentPane().setVisible(true);
    }// </editor-fold>

    public void drawComponents(Object [][] tweets, long maxCount) {
        
        int y = 110;
        int x1 = 90;
        int x2 = 290;
        
        getContentPane().removeAll();
        
        jLabel1.setText("Top Twitter Topics");
        jLabel1.setFont(new Font("Times New Roman", Font.BOLD, 18));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(193, 40, 400, 25);
        jLabel2.setIcon(new javax.swing.ImageIcon("/mnt/home/s4.png")); 
        if (Desktop.isDesktopSupported()) {
            jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                   if(evt.getClickCount() > 0){
                     try {
                             Desktop desktop = Desktop.getDesktop();
                             desktop.browse(new URI("http://s4.io/"));
                     }
                     catch (Exception e) {
                         e.printStackTrace();
                     }
                    }
                 }
                 });
        }
        getContentPane().add(jLabel2);
        jLabel2.setBounds(164, 529, 124, 19);
        jLabel3.setText("<html><u>Get Started</u></html>");
        jLabel3.setFont(new Font("Times New Roman", Font.ITALIC, 11));
        jLabel3.setBounds(299, 529, 124, 19);
        if (Desktop.isDesktopSupported()) {
            jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                   if(evt.getClickCount() > 0){
                     try {
                             Desktop desktop = Desktop.getDesktop();
                             desktop.browse(new URI("http://wiki.s4.io/Tutorials/GettingStarted"));
                     }
                     catch (Exception e) {
                         e.printStackTrace();
                     }
                    }
                 }
                 });
        }
        getContentPane().add(jLabel3);
        
        for(int i = 0; i < 10; i++) {
            javax.swing.JLabel jLabelTweet = new javax.swing.JLabel();
            javax.swing.JLabel jLabelFrequency = new javax.swing.JLabel();
            final String tweet = (String) tweets[i][0];
            jLabelTweet.setText("<html><u>" + tweet + "</u></html>");
            long count = ((Long)tweets[i][1]);
            jLabelFrequency.setText(Long.toString((Long)tweets[i][1]));
            jLabelTweet.setBounds(x1, y+(i*40), 200, 13);
            if (Desktop.isDesktopSupported()) {
                jLabelTweet.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                       if(evt.getClickCount() > 0){
                         try {
                                 Desktop desktop = Desktop.getDesktop();
                                 URLEncoder.encode(tweet, "UTF-8");
                                 desktop.browse(new URI("http://twitter.com/search?q=" + tweet));
                         }
                         catch (Exception e) {
                             e.printStackTrace();
                         }
                        }
                     }
                     });
            }
            getContentPane().add(jLabelTweet);
            jLabelFrequency.setBounds(x2, y+(i*40), (int)((200/maxCount)*count), 13);
            jLabelFrequency.setBackground(new java.awt.Color(246, 232, 252));
            jLabelFrequency.setForeground(new java.awt.Color(51, 0, 51));
            jLabelFrequency.setOpaque(true);
            getContentPane().add(jLabelFrequency);
        }
        getContentPane().repaint();
        getContentPane().setVisible(true);
    }
    
    public Object[][] readTopNTweets() {
        Object[][] tweets = new Object[10][2];
        long maxCount = 0;
        BufferedReader reader = null;
        try {
            System.out.println("fileName " + fileName);
            reader = new BufferedReader(new FileReader(new File(
                    fileName)));
            String line = reader.readLine();
            JSONObject jsonObject = new JSONObject(line);
            System.out.println(line);
            System.out.println(jsonObject.get("topN"));
            JSONArray jsonArray = jsonObject.getJSONArray("topN");
            int numberOfTweets = jsonArray.length();
            for (int i = 0; i < numberOfTweets; i++) {
                JSONObject record = (JSONObject) jsonArray.get(i);
                System.out.println(record);
                tweets[i][0] = record.getString("topic");
                long count = record.getLong("count");
                if (count > maxCount) {
                    maxCount = count;
                }
                String countString = Long.toString(record.getLong("count"));
                JLabel countLabel = new JLabel(countString);
                countLabel.setSize((int)((400/maxCount) * count), 40);
                countLabel.setBackground(new java.awt.Color(183, 123, 213));
                tweets[i][1] = record.getLong("count");
            }
            for (int j = 0; j < numberOfTweets; j++) {
                System.out.println("Tweet " + tweets[j][0] + " frequency "
                        + tweets[j][1]);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        drawComponents(tweets, maxCount);
        return tweets;
    }
    
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        final TwitterUI twitterUI = new TwitterUI();
        twitterUI.fileName = args[0];
        
        while (true) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    twitterUI.readTopNTweets();
                    twitterUI.setVisible(true);
                }
            });
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jLabel2;
    private javax.swing.JLabel jLabel3;
    private String fileName;
    // End of variables declaration

}
