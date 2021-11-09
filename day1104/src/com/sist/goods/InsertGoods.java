package com.sist.goods;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

public class InsertGoods extends JFrame {

	JTextField jtf_no;     //��ǰ��ȣ �Է»���
	JTextField jtf_item;   //��ǰ�� �Է»���
	JTextField jtf_qty;    //���� �Է»���
	JTextField jtf_price;  //���� �Է»���
	
	
	public InsertGoods() {
		jtf_no = new JTextField();
		jtf_item = new JTextField();
		jtf_qty = new JTextField();
		jtf_price = new JTextField();
		
		//�Է»��ڵ�� ������ �Է��ؾ����� �����ϴ� �󺧵��� ������� �г� ����
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(4,2));
		
		//�󺧰� �Է»���(�ؽ�Ʈ�ʵ�)���� �гο� ���ʴ�� ��´�.
		p.add(new JLabel("��ǰ��ȣ:"));
		p.add(jtf_no);
		p.add(new JLabel("��ǰ�̸�:"));
		p.add(jtf_item);
		p.add(new JLabel("��ǰ����:"));
		p.add(jtf_qty);
		p.add(new JLabel("��ǰ�ܰ�:"));
		p.add(jtf_price);
		
		//'�߰�'��ư �����
		JButton btn_add = new JButton("�߰�");
		
		//�������� ����� ����� ��� �Ʒ��ʿ� ��ư�� ��´�.
		add(p,BorderLayout.CENTER);
		add(btn_add,BorderLayout.SOUTH);
		
		//�������� ���α���,���α��̸� �����ϰ� ȭ�鿡 �����ֵ��� ����
		setSize(400,300);
		setVisible(true);
		
		//â�� ������ ���α׷��� �����ϵ��� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//'�߰�'��ư�� ������ ����ڰ� �Է��� ��ǰ��ȣ, ��ǰ�̸�, ��ǰ����,��ǰ��������
		//�����ͺ��̽� ���̺� �ڷḦ �߰��ϵ��� ����
		btn_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int no = Integer.parseInt(jtf_no.getText());
				String item = jtf_item.getText();
				int qty = Integer.parseInt(jtf_qty.getText());
				int price = Integer.parseInt(jtf_price.getText());
				
				String sql = "insert into goods values("+no+",'"+item+"',"+qty+","+price+")";
				
				//finally������ ������ �� �ֵ��� Connection�� Statement������
				//try�� �ٱ��� �����Ѵ�
				Connection conn=null;
				Statement stmt=null;
				
				try {
					//1. jdbc����̹��� �޸𸮷� �ε��Ѵ�.
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
					//2.DB������ ����
					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","c##sist","sist");
					
					//3.�����ͺ��̽������ ������ �� �ִ� Statement��ü�� ����
					stmt = conn.createStatement();
					
					//4.�����ͺ��̽� ����� �����Ѵ�.
					int re = stmt.executeUpdate(sql);
					if(re == 1) {
						System.out.println("��ǰ��Ͽ� �����߽��ϴ�.");
						jtf_no.setText("");
						jtf_item.setText("");
						jtf_qty.setText("");
						jtf_price.setText("");
					}else {
						System.out.println("��ǰ��Ͽ� �����߽��ϴ�.");
					}
					
	
				} catch (Exception ex) {
					System.out.println("���ܹ߻�:"+ex.getMessage());
				}finally {
					try {
						//5.����ߴ� �ڿ��� �ݾ��ش�.
						if(stmt !=null) {
							stmt.close();
						}

						if(conn != null) {
							conn.close();	
						}
							
					} catch (Exception ex) {
					}

				}
			}
		});
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//InsertGoods ��ü ����
		new InsertGoods();
	}

}
