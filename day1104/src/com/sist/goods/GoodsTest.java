package com.sist.goods;
//���̺� ��ϱ��� ������ ���α׷� �ۼ�
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import oracle.jdbc.AdditionalDatabaseMetaData;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

public class GoodsTest extends JFrame {

	JTextField jtf_no;     //��ǰ��ȣ �Է»���
	JTextField jtf_item;   //��ǰ�� �Է»���
	JTextField jtf_qty;    //���� �Է»���
	JTextField jtf_price;  //���� �Է»���
	
	JTable table;  //��� ��ǰ����� ������ ���� ������� �����ֱ� ���� ���̺��� ����
	Vector colNames; //���̺��� �÷��̸��� ���� ���͸� ����
	Vector<Vector> rowData; //���̺��� �����͸� ���� ���͸� ����
	
	
	public GoodsTest() {
		
		//���̺� �� Į���̸��� ���� ���͸� �����ϰ� �ڷḦ �߰��Ѵ�.
		colNames = new Vector<String>();
		colNames.add("��ǰ��ȣ");
		colNames.add("��ǰ��");
		colNames.add("����");
		colNames.add("�ܰ�");
		
		//���̺� �� ���� �����͵��� ��� ���� ���͸� �����ϰ� �ڷḦ �߰��Ѵ�.
		rowData = new Vector<Vector>();
		Vector v1 = new Vector();
		v1.add("1");
		v1.add("������");
		v1.add("10");
		v1.add("1500");
		Vector v2 = new Vector();
		v2.add("2");
		v2.add("��Ǯ");
		v2.add("20");
		v2.add("700");
		
		rowData.add(v1);
		rowData.add(v2);
		
		//�÷��̸��� �ִ� colNames���Ϳ� ���������Ͱ��ִ� rowData���͸� ����
		//������ ���� ȭ���� ���̺��� ����
		table = new JTable(rowData,colNames);
		
		
		//���̺��� �ڷᰡ �ʹ� ���Ƽ� �� ȭ�鿡 ������ ���� ���� �ڵ�����
		//��ũ���� ����� �ִ� ��ũ�� ���� ����
		JScrollPane jsp = new JScrollPane(table);		
		
		
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
		
		//'���'��ư �����
		JButton btn_list = new JButton("�߰�");

		//��ư���� ���� �г��� ����
		JPanel p2 = new JPanel();
		p2.add(btn_add);
		p2.add(btn_list);
		
		//�Է»��ڵ��� �ִ� �гΰ� ��ư�� �ִ� �г��� ������� �г��� �����.
		JPanel p_center = new JPanel();
		
		p_center.setLayout(new BorderLayout());
		p_center.add(p,BorderLayout.CENTER);
		p_center.add(p2,BorderLayout.SOUTH);
		
		//�������� ����� �Է»��ڿ� ��ư�� ����ִ� p_center�г��� ��´�.
		add(p_center, BorderLayout.CENTER);
		//���̺��� ����ִ� ��ũ������ �������� �Ʒ��ʿ� ��´�.
		add(jsp,BorderLayout.SOUTH);
		
		//�������� ���α���,���α��̸� �����ϰ� ȭ�鿡 �����ֵ��� ����
		setSize(800,600);
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
		new GoodsTest();
	}

}
