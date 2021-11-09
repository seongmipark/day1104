package com.sist.member;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Vector;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.MouseListener;

public class MemberTest extends JFrame {

	//ȸ����ȣ, ȸ���̸�, ȸ���ּ�, ȸ�� ����, ��ȭ��ȣ
	JTextField jtf_no;
	JTextField jtf_name;
	JTextField jtf_addr;
	JTextField jtf_age;
	JTextField jtf_phone;
	
	JTable table;
	Vector colNames; //���̺��� �÷��̸��� ���� ���͸� ����
	Vector<Vector> rowData; //���̺��� �����͸� ���� ���͸� ����

	Connection conn= null;
	Statement stmt = null;
	ResultSet rs=null;
	String driver = "oracle.jdbc.driver.OracleDriver";
	String host = "jdbc:oracle:thin:@localhost:1521:XE";
	String id = "c##sist";
	String pwd = "sist";
	
	
	//��� �޼ҵ�
	public void readmember() {
		rowData.clear();
		
		String sql = "select*from member order by no";
		
		try {
			//����̹� �ε�
			Class.forName(driver);
			
			//����
			conn = DriverManager.getConnection(host , id , pwd);
			
			//statement����
			stmt = conn.createStatement();
			
			//����
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String addr = rs.getString(3);
				int age = rs.getInt(4);
				String phone =  rs.getString(5);
				
				Vector v = new Vector();
				v.add(no);
				v.add(name);
				v.add(addr);
				v.add(age);
				v.add(phone);
				
				rowData.add(v);
				table.updateUI();
			}
			
		} catch (Exception e) {
			System.out.println("���ܹ߻�:"+e.getMessage());
		}finally {
			try {
				//����ߴ� �ڿ����� �ݾ��ش�.
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}	

	}
	}
	
	//�߰� �޼ҵ� perparstatement���
	public void insertmember(int no,String name,String addr,int age,String phone) {
		String sql = "Insert into member values(?,?,?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(host , id , pwd);	
			pstmt = conn.prepareStatement(sql);		
			
			pstmt.setInt(1,no);
			pstmt.setString(2,name);
			pstmt.setString(3,addr);
			pstmt.setInt(4,age);
			pstmt.setString(5,phone);
			
			int re = pstmt.executeUpdate();
			if(re == 1) {
				System.out.println("��Ͽ� �����߽��ϴ�.");
				readmember();
			}else{
				System.out.println("��Ͽ� �����߽��ϴ�.");
			}
		} catch (Exception e) {
			System.out.println("���ܹ߻�:"+e.getMessage());
		}finally {
			try {
				if(conn != null) {
					conn.close();	
				}
				if(pstmt !=null) {
					pstmt.close();
				}
		
			} catch (Exception ex) {
				System.out.println("���ܹ߻�:"+ex.getMessage());
			}
		}
	}
	
	
	/*
	//�߰� �޼ҵ�
	public void insertmember(int no,String name,String addr,int age,String phone) {
		String sql = "Insert into member values("+no+",'"+name+"','"+addr+"',"+age+",'"+phone+"')";
		System.out.println(sql);
		
		try {
			//1.����̹��ε�
			Class.forName(driver);
			
			//2.DB����
			conn = DriverManager.getConnection(host , id , pwd);
			
			//3.statement��ü����
			stmt = conn.createStatement();
			
			//4.��ɾ�
			int re = stmt.executeUpdate(sql);	
			
			//5.����
			if(re == 1) {
				System.out.println("��Ͽ� �����߽��ϴ�.");
				readmember();
			}else{
				System.out.println("��Ͽ� �����߽��ϴ�.");
			}
			
		} catch (Exception ex) {
				System.out.println("���ܹ߻�:"+ex.getMessage());
			}finally {
				try {
					if(stmt !=null) {
						stmt.close();
					}

					if(conn != null) {
						conn.close();	
					}
						
				} catch (Exception ex) {
					System.out.println("���ܹ߻�:"+ex.getMessage());
				}
				
				
			}
		}
		*/
	
	
	//�����޼ҵ�
	public void updatemember(int no,String name,String addr,int age,String phone) {
		
		String sql = "update member set name='"+name+"',addr='"+addr+"',age="+age+",phone='"+phone+"' where no="+no;
		
		try {
			//����̹��ε�
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//����
			conn = DriverManager.getConnection(host, id, pwd);
			//statement��ü����
			stmt = conn.createStatement();
			//��ɾ�
			int re = stmt.executeUpdate(sql);
			if(re==1) {
				System.out.println("������ �����߽��ϴ�.");
				jtf_no.setText("");
				jtf_name.setText("");
				jtf_addr.setText("");
				jtf_age.setText("");
				jtf_phone.setText("");
				
				readmember();
				
			}else {
				System.out.println("������ �����߽��ϴ�.");
			}

		} catch (Exception e) {
			System.out.println("���ܹ߻�:"+e.getMessage());
		}finally {
			
			try {
				if(conn != null) {
					conn.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				
			} catch (Exception e2) {
				System.out.println("���ܹ߻�:"+e2.getMessage());
			}
			
		}
		
		
		
	}
	
	
	
	
	
	//'����'�޼ҵ�
	public void deletemember(int no) {
		
		try {
			String sql = "delete member where no="+no;
			Class.forName(driver);
			conn = DriverManager.getConnection(host, id, pwd);
			stmt = conn.createStatement();
			int re = stmt.executeUpdate(sql);
			if(re ==1) {
				System.out.println("������ �����߽��ϴ�.");
				jtf_no.setText("");
				jtf_name.setText("");
				jtf_addr.setText("");
				jtf_age.setText("");
				jtf_phone.setText("");
				
				readmember();
				
			}else {
				System.out.println("������ �����߽��ϴ�.");
			}
		} catch (Exception e) {
		System.out.println("���ܹ߻�:"+e.getMessage());	
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(stmt != null) {
					stmt.close();
				}
			} catch (Exception e2) {
				System.out.println("���ܹ߻�:"+e2.getMessage());// TODO: handle exception
			}
		}		
	}
	
	
	public MemberTest(){
		jtf_no = new JTextField(20);
		jtf_name = new JTextField(20);
		jtf_addr = new JTextField(20);
		jtf_age = new JTextField(20);
		jtf_phone = new JTextField(20);

		JPanel p = new JPanel();//�Է�â�� ��ư�� ���� �г�
		p.setLayout(new GridLayout(2,5));
		p.add(new JLabel("ȸ����ȣ"));
		p.add(new JLabel("ȸ���̸�"));
		p.add(new JLabel("ȸ���ּ�"));
		p.add(new JLabel("ȸ������"));
		p.add(new JLabel("��ȭ��ȣ"));
		p.add(jtf_no);
		p.add(jtf_name);
		p.add(jtf_addr);
		p.add(jtf_age);
		p.add(jtf_phone);
		
		JPanel p1 = new JPanel(); //��ư���� ���
		JButton btn_list = new JButton("���");
		JButton btn_add = new JButton("�߰�");
		JButton btn_update = new JButton("����");
		JButton btn_del = new JButton("����");
		p1.add(btn_list);
		p1.add(btn_add);
		p1.add(btn_update);
		p1.add(btn_del);
		
		JPanel p2 = new JPanel();	
		p2.setLayout(new BorderLayout());
		p2.add(p,BorderLayout.CENTER);
		p2.add(p1,BorderLayout.SOUTH);


		
		//���̺� �� Į���̸��� ���� ���͸� �����ϰ� �ڷḦ �߰��Ѵ�.
		colNames = new Vector<String>();
		colNames.add("ȸ����ȣ");
		colNames.add("�̸�");
		colNames.add("�ּ�");
		colNames.add("����");
		colNames.add("��ȭ��ȣ");
		
		//���̺� �� ���� �����͵��� ��� ���� ���͸� �����ϰ� �ڷḦ �߰��Ѵ�.
		rowData = new Vector<Vector>();
		//�÷��̸��� �ִ� colNames���Ϳ� ���������Ͱ��ִ� rowData���͸� ����
		//������ ���� ȭ���� ���̺��� ����
		table = new JTable(rowData,colNames);
		JScrollPane jsp = new JScrollPane(table);	
		

		add(p2,BorderLayout.SOUTH);
		add(jsp,BorderLayout.CENTER);
		

		setSize(700,300);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		
		//'���'��ư�̺�Ʈ ����
		btn_list.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readmember();
			}
		});
		
		
		//'�߰�'��ư�̺�Ʈ ����
		btn_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int no = Integer.parseInt(jtf_no.getText()); 
				String name = jtf_name.getText();
				String addr = jtf_addr.getText();
				int age = Integer.parseInt(jtf_age.getText()); 
				String phone = jtf_phone.getText();
				
				insertmember(no,name,addr,age,phone);

			}
		});
		
		
		//'����'��ư�̺�Ʈ ����
		btn_update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int no = Integer.parseInt(jtf_no.getText()); 
				String name = jtf_name.getText();
				String addr = jtf_addr.getText();
				int age = Integer.parseInt(jtf_age.getText()); 
				String phone = jtf_phone.getText();		
	
				updatemember(no, name, addr, age, phone);
				
				
			}
		});
		
		
		//'����'��ư�̺�Ʈ ����
		btn_del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int no =Integer.parseInt(jtf_no.getText()); 
				deletemember(no);
			}
		});
		

		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				int index = table.getSelectedRow(); //������ ���� ��ȣ�� ��ȯ���ش�.

				//rowData�� index��°�� ���͸� ������´�.
				Vector vr = rowData.get(index);
				
				//�� ������ ��Ҹ� ���ʷ� �Է»��ڿ� ���
				jtf_no.setText(vr.get(0)+"");
				jtf_name.setText(vr.get(1)+"");
				jtf_addr.setText(vr.get(2)+"");
				jtf_age.setText(vr.get(3)+"");
				jtf_phone.setText(vr.get(4)+"");
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub				
			}
		});
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MemberTest();
	}

}
