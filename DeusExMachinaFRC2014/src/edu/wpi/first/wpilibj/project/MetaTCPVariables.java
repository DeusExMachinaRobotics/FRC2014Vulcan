/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.project;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.util.SquawkHashtable;
import com.sun.squawk.util.StringTokenizer;
import java.io.IOException;
import java.util.Enumeration;
import java.util.*;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.SocketConnection;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.ServerSocketConnection;
import edu.wpi.first.wpilibj.DriverStationLCD;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.microedition.io.*;
import dem.world.Target;
import edu.wpi.first.wpilibj.project.RobotMap;

/** MetaTCPVariables
 * For receiving variables from the dashboard.
 *
 * @author Team 2035 Programmers
 */
class MetaTCPVariables {

    Vector connections;
    SquawkHashtable variables;
    int numberOfConnections;
    InputStream dashboardStream;
    InputStreamReader reader;
    BufferedReader buffRead;
    Thread connect;
    ServerSocketConnection server;
    UDPDatagramConnection server2;
    public Vector dataMessage = new Vector();
    
    SocketConnection socket;
    
    InputStreamReader isr;
    private int lastByte;
    private boolean updaterunning;

    public static final int PORT = 1130;  // TCP listen connection port
    
    public float x1, x2, x3, x4, y1, y2, y3, y4, range;
    
    /**
     * Starting the constructor causes it to accept new connections and
     * the open connections will be read for data.
     */
    public MetaTCPVariables()
    {
       server = null;
       server2 = null;
       connections = new Vector(); // Vector of open connections.
       variables = new SquawkHashtable(); // Hastable of key/value pairs to store values
       numberOfConnections = 0;
       buffRead = null;
       isr = null;
       updaterunning = false;
       x1 = x2 = x3 = x4 = y1 = y2 = y3 = y4 = range = (float)0.0;
       
       connect = new Thread() {
           public void run() {
               acceptConnections();
           }
       };
       connect.start();
       
//       new Thread() {
//           public void run() {
//               update();
//               try {
//                    Thread.sleep(2);
//                    System.out.println("update sleep");
//                } catch (InterruptedException ex1) {
//                    System.out.println("update except");
//                }
//           }
//       }.start(); 
    }
    
    /** newVariableValue
     * Called internally to update the key pair in the Hashtable
     * @param s called the key, this is String such as "positionx"
     * @param o called the value, this is an Object that stores its content
     */
    private void newVariableValue(String s, Object o)
    {
        variables.put(s, o);
    }
    
    /**
     * 
     * @param s called the key, this is the String identifer of the object of interest such as "rectanglelength" 
     * @return The Object.  The Object will need to be cast in its use.
     */
    public Object getVariableValue(String s)
    {
        return variables.get(s);
    }

    /**
     * 
     * @param s called the key, this is the String identifer of the object of interest such as "rectanglelength" 
     * @return a float value of the object or -99 if there is an error.
     */
    public float getVariableFloatValue(String s)
    {
//        Object o = variables.get(s);
//        if (o instanceof Float)
//        {
//            return ((Float)o).floatValue();
//        }
        Float o = (Float)variables.get(s);
        if (o != null)
            return o.floatValue();
        return (float)-99.0;
    }

    /**
     * Accepts UDP connections to the robot on the specified port.
     */
    private synchronized void acceptConnections() 
    {
        System.out.println("acceptConnections");
        // Open the server
        while (true) {
            try {
                server = (ServerSocketConnection) Connector.open("socket://:" + PORT);
                //server2 = (UDPDatagramConnection) Connector.open("datagram://:1131");
                System.out.println("Connector open");
                break;
            } catch (IOException ex) {
                //ex.printStackTrace();
                try {
                    
                    Thread.sleep(2000);
                } catch (InterruptedException ex1) {
                    ex1.printStackTrace();
                }
            }
        }

        try {
            //while (true) {
                // Wait for a connection
                socket = (SocketConnection) server.acceptAndOpen();

                //socket.setSocketOption(SocketConnection.LINGER, 0);
                //socket.setSocketOption(SocketConnection.KEEPALIVE, 100);
                //socket.setSocketOption(SocketConnection.RCVBUF, 4096);
                System.out.println("socket option");
                
            //}
        } catch (IOException ex) {
            System.out.println("MetaTCP: LOST SERVER!");
            ex.printStackTrace();
        }
        try {
            
            dashboardStream = socket.openInputStream();
            System.out.println("Input stream");
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "Listening on " + server.getLocalAddress());
            System.out.println("InputStream established");
        } catch (IOException ex){
            System.out.println("Input stream fail");
        }
        DriverStationLCD.getInstance().updateLCD();
        connections.addElement(server);
        numberOfConnections++;
        
        isr = new InputStreamReader(dashboardStream);
        buffRead = new BufferedReader(isr);
        
        
        System.out.println("buffRead created");
        if (updaterunning == false)
        {
            updaterunning = true;
            new Thread() {
            public void run() {
                while(true) {
                    update();
                    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 1, dataMessage.toString());
    //               try {
    //                    Thread.sleep(2);
    //                    System.out.println("update sleep");
    //                } catch (InterruptedException ex1) {
    //                    System.out.println("update except");
    //                }
                    //System.out.println("update 2");
                    }
                }
            }.start(); 
        }
        
    }
    
    public int getConnections(){
        //System.out.println("number of connections = " + numberOfConnections);        
        return numberOfConnections;
    }
    
    public int getCount() 
    {
        return variables.size();
    }
    
    public float getrange()
    {
        return this.range;
        
    }
    
    public float getx2()
    {
        return this.x2;
    }
    
    /**
     * Reads the UDP connection and updates the stored values for the key.
     * It is assumed that only one key is updated per UDP datagram.
     * This only handles float variables at the present time.
     */
    public void update()
    {
        
        //System.out.println("update");
        //Enumeration e = connections.elements();
        //while( e.hasMoreElements() )
        //{
            //Object Enum = e.nextElement();
            //if (Enum instanceof SocketConnection)
            //{
        if(buffRead != null)
        {
            try 
            {
                while (buffRead.ready())
                {
                //System.out.println("buffRead != null");
                    
                    String message;
                    //String message = readString();
                    //System.out.println("readLine");
                    while ((message = buffRead.readLine()) != null) 
                    {
                        Vector values = new Vector();
                        StringTokenizer st = new StringTokenizer(message);
                        while(st.hasMoreTokens()) {
                            values.addElement(st.nextToken());
                        }
                       
                        //float f = Float.parseFloat(values.elementAt(0).toString());
                        //Target t = new Target(f, f, f, f, f, f, RobotMap.Vulcan);
                        dataMessage.addElement(values);
                    }
                }
                    
            } catch (IOException z) {
                // TBD: what to do?
                System.out.println("update fail");
            }
        }
            //}
        //}
        
    }

}