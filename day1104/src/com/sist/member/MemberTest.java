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

	//회원번호, 회원이름, 회원주소, 회원 나이, 전화번호
	JTextField jtf_no;
	JTextField jtf_name;
	JTextField jtf_addr;
	JTextField jtf_age;
	JTextField jtf_phone;
	
	JTable table;
	Vector colNames; //테이블의 컬럼이름을 위한 벡터를 선언
	Vector<Vector> rowData; //테이블의 데이터를 위한 벡터를 선언

	Connection conn= null;
	Statement stmt = null;
	ResultSet rs=null;
	String driver = "oracle.jdbc.driver.OracleDriver";
	String host = "jdbc:oracle:thin:@localhost:1521:XE";
	String id = "c##sist";
	String pwd = "sist";
	
	
	//목록 메소드
	public void readmember() {
		rowData.clear();
		
		String sql = "select*from member order by no";
		
		try {
			//드라이버 로드
			Class.forName(driver);
			
			//연결
			conn = DriverManager.getConnection(host , id , pwd);
			
			//statement생성
			stmt = conn.createStatement();
			
			//할일
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
			System.out.println("예외발생:"+e.getMessage());
		}finally {
			try {
				//사용했던 자원들을 닫아준다.
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
	
	//추가 메소드 perparstatement사용
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
				System.out.println("등록에 성공했습니다.");
				readmember();
			}else{
				System.out.println("등록에 실패했습니다.");
			}
		} catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}finally {
			try {
				if(conn != null) {
					conn.close();	
				}
				if(pstmt !=null) {
					pstmt.close();
				}
		
			} catch (Exception ex) {
				System.out.println("예외발생:"+ex.getMessage());
			}
		}
	}
	
	
	/*
	//추가 메소드
	public void insertmember(int no,String name,String addr,int age,String phone) {
		String sql = "Insert into member values("+no+",'"+name+"','"+addr+"',"+age+",'"+phone+"')";
		System.out.println(sql);
		
		try {
			//1.드라이버로드
			Class.forName(driver);
			
			//2.DB연결
			conn = DriverManager.getConnection(host , id , pwd);
			
			//3.statement객체생성
			stmt = conn.createStatement();
			
			//4.명령어
			int re = stmt.executeUpdate(sql);	
			
			//5.할일
			if(re == 1) {
				System.out.println("등록에 성공했습니다.");
				readmember();
			}else{
				System.out.println("등록에 실패했습니다.");
			}
			
		} catch (Exception ex) {
				System.out.println("예외발생:"+ex.getMessage());
			}finally {
				try {
					if(stmt !=null) {
						stmt.close();
					}

					if(conn != null) {
						conn.close();	
					}
						
				} catch (Exception ex) {
					System.out.println("예외발생:"+ex.getMessage());
				}
				
				
			}
		}
		*/
	
	
	//수정메소드
	public void updatemember(int no,String name,String addr,int age,String phone) {
		
		String sql = "update member set name='"+name+"',addr='"+addr+"',age="+age+",phone='"+phone+"' where no="+no;
		
		try {
			//드라이버로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//연결
			conn = DriverManager.getConnection(host, id, pwd);
			//statement객체생성
			stmt = conn.createStatement();
			//명령어
			int re = stmt.executeUpdate(sql);
			if(re==1) {
				System.out.println("수정에 성공했습니다.");
				jtf_no.setText("");
				jtf_name.setText("");
				jtf_addr.setText("");
				jtf_age.setText("");
				jtf_phone.setText("");
				
				readmember();
				
			}else {
				System.out.println("수정에 실패했습니다.");
			}

		} catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}finally {
			
			try {
				if(conn != null) {
					conn.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				
			} catch (Exception e2) {
				System.out.println("예외발생:"+e2.getMessage());
			}
			
		}
		
		
		
	}
	
	
	
	
	
	//'삭제'메소드
	public void deletemember(int no) {
		
		try {
			String sql = "delete member where no="+no;
			Class.forName(driver);
			conn = DriverManager.getConnection(host, id, pwd);
			stmt = conn.createStatement();
			int re = stmt.executeUpdate(sql);
			if(re ==1) {
				System.out.println("삭제에 성공했습니다.");
				jtf_no.setText("");
				jtf_name.setText("");
				jtf_addr.setText("");
				jtf_age.setText("");
				jtf_phone.setText("");
				
				readmember();
				
			}else {
				System.out.println("삭제를 실패했습니다.");
			}
		} catch (Exception e) {
		System.out.println("예외발생:"+e.getMessage());	
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(stmt != null) {
					stmt.close();
				}
			} catch (Exception e2) {
				System.out.println("예외발생:"+e2.getMessage());// TODO: handle exception
			}
		}		
	}
	
	
	public MemberTest(){
		jtf_no = new JTextField(20);
		jtf_name = new JTextField(20);
		jtf_addr = new JTextField(20);
		jtf_age = new JTextField(20);
		jtf_phone = new JTextField(20);

		JPanel p = new JPanel();//입력창과 버튼을 담은 패널
		p.setLayout(new GridLayout(2,5));
		p.add(new JLabel("회원번호"));
		p.add(new JLabel("회원이름"));
		p.add(new JLabel("회원주소"));
		p.add(new JLabel("회원나이"));
		p.add(new JLabel("전화번호"));
		p.add(jtf_no);
		p.add(jtf_name);
		p.add(jtf_addr);
		p.add(jtf_age);
		p.add(jtf_phone);
		
		JPanel p1 = new JPanel(); //버튼담은 페널
		JButton btn_list = new JButton("목록");
		JButton btn_add = new JButton("추가");
		JButton btn_update = new JButton("수정");
		JButton btn_del = new JButton("삭제");
		p1.add(btn_list);
		p1.add(btn_add);
		p1.add(btn_update);
		p1.add(btn_del);
		
		JPanel p2 = new JPanel();	
		p2.setLayout(new BorderLayout());
		p2.add(p,BorderLayout.CENTER);
		p2.add(p1,BorderLayout.SOUTH);


		
		//테이블에 들어갈 칼럼이름을 위한 벡터를 생성하고 자료를 추가한다.
		colNames = new Vector<String>();
		colNames.add("회원번호");
		colNames.add("이름");
		colNames.add("주소");
		colNames.add("나이");
		colNames.add("전화번호");
		
		//테이블에 들어갈 실제 데이터들을 담기 위한 벡터를 생성하고 자료를 추가한다.
		rowData = new Vector<Vector>();
		//컬럼이름이 있는 colNames벡터와 실제데이터가있는 rowData벡터를 갖고
		//엑셀과 같은 화면의 테이블을 생성
		table = new JTable(rowData,colNames);
		JScrollPane jsp = new JScrollPane(table);	
		

		add(p2,BorderLayout.SOUTH);
		add(jsp,BorderLayout.CENTER);
		

		setSize(700,300);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		
		//'목록'버튼이벤트 생성
		btn_list.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readmember();
			}
		});
		
		
		//'추가'버튼이벤트 생성
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
		
		
		//'수정'버튼이벤트 생성
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
		
		
		//'삭제'버튼이벤트 생성
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
				int index = table.getSelectedRow(); //선택한 행의 번호를 반환해준다.

				//rowData의 index번째의 벡터를 꺼집어내온다.
				Vector vr = rowData.get(index);
				
				//그 벡터의 요소를 차례로 입력상자에 출력
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
