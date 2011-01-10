package io.s4.example.twittertopiccount.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TwitterUI4 extends javax.swing.JFrame {

    /** Creates new form TwitterUI4 */
    public TwitterUI4() {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        this.setSize(605, 580);
        this.setBackground(Color.white);
        jLabel1.setText("S4 Twitter top N tweets");
        getContentPane().add(jLabel1);
        jLabel1.setFont(new Font("Times New Roman", Font.BOLD, 18));
 //       readTopNTweets();

        getContentPane().setVisible(true);
    }// </editor-fold>

    public void drawComponents(Object [][] tweets, long maxCount) {
        
        int y = 110;
        int x1 = 90;
        int x2 = 290;
        
        getContentPane().removeAll();
        
        jLabel1.setText("S4 Twitter top N tweets");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(200, 40, 400, 25);
        
        for(int i = 0; i < 10; i++) {
            javax.swing.JLabel jLabelTweet = new javax.swing.JLabel();
            javax.swing.JLabel jLabelFrequency = new javax.swing.JLabel();
            jLabelTweet.setText((String) tweets[i][0]);
            long count = ((Long)tweets[i][1]);
            jLabelFrequency.setText(Long.toString((Long)tweets[i][1]));
            jLabelTweet.setBounds(x1, y+(i*40), 200, 13);
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
        final TwitterUI4 twitterUI = new TwitterUI4();
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
    private String fileName;
    // End of variables declaration

}
