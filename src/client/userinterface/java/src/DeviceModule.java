import java.util.ArrayList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class DeviceModule{
    ArrayList <String[]> propertys =  new ArrayList <String[]>();
    ArrayList <DevicePort> ports =  new ArrayList <DevicePort>();
    String name;
    DeviceModule reference;
    
    public DeviceModule(String name){
        reference = this;
        this.name = name;}
    
    public String toString(){
        return "Module: "+name;}  
        
    public void addPort(DevicePort port){
        ports.add(port);}
        
    public void updateInfo(){
        Repository.frame.mainpanel.p4.dut.additem.setEnabled(true);
        Repository.frame.mainpanel.p4.dut.additem.setText("Add port");
        Repository.frame.mainpanel.p4.dut.remitem.setEnabled(true);
        Repository.frame.mainpanel.p4.dut.remitem.setText("Remove module");
        Repository.frame.mainpanel.p4.dut.temp2 = reference;
        Repository.frame.mainpanel.p4.dut.tname2.setText(name);
        Repository.frame.mainpanel.p4.dut.propname.setText("");
        Repository.frame.mainpanel.p4.dut.propvalue.setText("");
        updatePropertys();}
        
    public void setName(String name){
        this.name = name;}
    
    public void updatePropertys(){
        Repository.frame.mainpanel.p4.dut.propertys2.removeAll();
        if(Repository.frame.mainpanel.p4.dut.nodetemp2.getChildAt(Repository.frame.mainpanel.p4.dut.nodetemp2.getChildCount()-1).isLeaf()){
            while(Repository.frame.mainpanel.p4.dut.nodetemp2.getChildCount()>1){
                ((DefaultTreeModel)Repository.frame.mainpanel.p4.dut.explorer.tree.getModel()).removeNodeFromParent(((DefaultMutableTreeNode)Repository.frame.mainpanel.p4.dut.nodetemp2.getChildAt(1)));}}
        else{
            while(Repository.frame.mainpanel.p4.dut.nodetemp2.getChildAt(1).isLeaf()){
                ((DefaultTreeModel)Repository.frame.mainpanel.p4.dut.explorer.tree.getModel()).removeNodeFromParent(((DefaultMutableTreeNode)Repository.frame.mainpanel.p4.dut.nodetemp2.getChildAt(1)));}}
        for(int i=0;i<propertys.size();i++){
            DefaultMutableTreeNode child2 = new DefaultMutableTreeNode(propertys.get(i)[0]+" - "+propertys.get(i)[1],false);
            if(Repository.frame.mainpanel.p4.dut.nodetemp2.getChildAt(Repository.frame.mainpanel.p4.dut.nodetemp2.getChildCount()-1).isLeaf()){
                ((DefaultTreeModel)Repository.frame.mainpanel.p4.dut.explorer.tree.getModel()).insertNodeInto(child2,Repository.frame.mainpanel.p4.dut.nodetemp2,Repository.frame.mainpanel.p4.dut.nodetemp2.getChildCount());}
            else{
                ((DefaultTreeModel)Repository.frame.mainpanel.p4.dut.explorer.tree.getModel()).insertNodeInto(child2,Repository.frame.mainpanel.p4.dut.nodetemp2,1+i);}
            final JButton b = new JButton("remove");
            b.setBounds(280,i*23+18,78,19);
            b.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev){
                    propertys.remove(Repository.frame.mainpanel.p4.dut.propertys2.getComponentZOrder(b)/3);
                    updatePropertys();}});
            Repository.frame.mainpanel.p4.dut.propertys2.add(b);
            final JTextField text1 = new JTextField();
            text1.setText(propertys.get(i)[0]);
            text1.setBounds(6,i*23+18,135,20);
            text1.addKeyListener(new KeyAdapter(){
                public void keyReleased(KeyEvent ev){
                    propertys.get(Repository.frame.mainpanel.p4.dut.propertys2.getComponentZOrder(text1)/3)[0]=text1.getText();
                    ((DefaultMutableTreeNode)Repository.frame.mainpanel.p4.dut.nodetemp2.getChildAt(1+(Repository.frame.mainpanel.p4.dut.propertys2.getComponentZOrder(text1)/3))).setUserObject(text1.getText()+" - "+propertys.get(Repository.frame.mainpanel.p4.dut.propertys2.getComponentZOrder(text1)/3)[1]);
                    ((DefaultTreeModel)Repository.frame.mainpanel.p4.dut.explorer.tree.getModel()).nodeChanged(Repository.frame.mainpanel.p4.dut.nodetemp2.getChildAt(1+(Repository.frame.mainpanel.p4.dut.propertys2.getComponentZOrder(text1)/3)));}});
            final JTextField text2 = new JTextField();
            text2.setText(propertys.get(i)[1]);
            text2.setBounds(143,i*23+18,135,20);    
            text2.addKeyListener(new KeyAdapter(){
                public void keyReleased(KeyEvent ev){
                    propertys.get(Repository.frame.mainpanel.p4.dut.propertys2.getComponentZOrder(text1)/3)[1]=text2.getText();
                    ((DefaultMutableTreeNode)Repository.frame.mainpanel.p4.dut.nodetemp2.getChildAt(1+(Repository.frame.mainpanel.p4.dut.propertys2.getComponentZOrder(text1)/3))).setUserObject(propertys.get(Repository.frame.mainpanel.p4.dut.propertys2.getComponentZOrder(text1)/3)[0]+" - "+text2.getText());
                    ((DefaultTreeModel)Repository.frame.mainpanel.p4.dut.explorer.tree.getModel()).nodeChanged(Repository.frame.mainpanel.p4.dut.nodetemp2.getChildAt(1+(Repository.frame.mainpanel.p4.dut.propertys2.getComponentZOrder(text1)/3)));}});
            Repository.frame.mainpanel.p4.dut.propertys2.add(text2);
            Repository.frame.mainpanel.p4.dut.propertys2.add(text1);}
        Repository.frame.mainpanel.p4.dut.propertys2.setPreferredSize(new Dimension(Repository.frame.mainpanel.p4.dut.propertys2.getWidth(),propertys.size()*23+18));
        Repository.frame.mainpanel.p4.dut.propertys2.revalidate();
        Repository.frame.mainpanel.p4.dut.propertys2.repaint();}}