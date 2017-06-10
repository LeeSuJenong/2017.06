import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;


public class UpDownClient {
	JFrame frame;
	String frameTitle = "ä�� Ŭ���̾�Ʈ";
	JTextArea incoming;			// ���ŵ� �޽����� ����ϴ� ��
	JTextArea outgoing;			// �۽��� �޽����� �ۼ��ϴ� ��
	JList counterParts;			// ���� �α����� ä�� ������� ��Ÿ���� ����Ʈ.
	ObjectInputStream reader;	// ���ſ� ��Ʈ��
	ObjectOutputStream writer;	// �۽ſ� ��Ʈ��
	Socket sock;				// ���� ����� ����
	String user;				// �� Ŭ���̾�Ʈ�� �α��� �� ������ �̸�
	JButton logButton;			// ����� �Ǵ� �α���/�α׾ƿ� ��ư
	JScrollPane qScroller;
	UpDownGame game;
	Image TextAreaImage = new ImageIcon("images/Text.PNG").getImage();
	Image TextAreaImage2 = new ImageIcon("images/Text2.PNG").getImage();

	public static void main(String[] args) {
		UpDownClient client = new UpDownClient();
		client.go();
	}
	private void go() {
		// build GUI
		frame = new JFrame(frameTitle + " : �α����ϼ���");
		game = new UpDownGame();


		incoming = new JTextArea(17,20){
            { setOpaque( false ) ; }
            public void paintComponent(Graphics g){
                g.drawImage(TextAreaImage,0,0,null);       //�̹��� �׸���
                super.paintComponent(g);
            }
        };


		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
	
		qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		String[] list = {UpDownMessage.ALL};
		counterParts = new JList(list);
		JScrollPane cScroller = new JScrollPane(counterParts);
		cScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


		counterParts.setVisibleRowCount(5);
		counterParts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		counterParts.setFixedCellWidth(100);

		// �޽��� ������ ���� ��ư
		JButton sendButton = new JButton("Send");
		sendButton.setBackground(new Color(215,235,209));
		sendButton.addActionListener(new SendButtonListener());

		// �޽��� ���÷��� â  
		outgoing = new JTextArea(5,20){
            { setOpaque( false ) ; }
            public void paintComponent(Graphics g){
                g.drawImage(TextAreaImage2,0,0,null);       //�̹��� �׸���
                super.paintComponent(g);
            }
        };
		outgoing.addKeyListener(new EnterKeyListener());
		outgoing.setLineWrap(true);
		outgoing.setWrapStyleWord(true);
		outgoing.setEditable(true);

		JScrollPane oScroller = new JScrollPane(outgoing);
		oScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		oScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// �α��ΰ� �ƿ��� ����ϴ� ��ư. ó������ Login �̾��ٰ� �ϴ� �α��� �ǰ��� Logout���� �ٲ�
		logButton = new JButton("Login");
		logButton.setBackground(new Color(229,238,206));
		logButton.addActionListener(new LogButtonListener());

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		upperPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
		lowerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));

		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

		JPanel sendPanel = new JPanel();
		sendPanel.setLayout(new BorderLayout());
		sendPanel.setBackground(Color.white);

		userPanel.add(new JLabel("��ȭ�����"));
		userPanel.add(Box.createRigidArea(new Dimension(0,5)));
		userPanel.setPreferredSize(new Dimension(100,200));	
		userPanel.add(cScroller);
		userPanel.setBackground(Color.WHITE);

		inputPanel.add(new JLabel("�޽����Է�"));
		inputPanel.add(Box.createRigidArea(new Dimension(0,5)));
		inputPanel.add(oScroller);
		inputPanel.setBackground(Color.WHITE);

		buttonPanel.add(sendButton);
		buttonPanel.add(logButton);
		buttonPanel.setBackground(Color.WHITE);

		sendPanel.add(BorderLayout.CENTER, inputPanel);
		sendPanel.add(BorderLayout.SOUTH, buttonPanel);

		lowerPanel.add(userPanel);
		lowerPanel.add(Box.createRigidArea(new Dimension(5,0)));
		lowerPanel.add(sendPanel);
		lowerPanel.add(Box.createRigidArea(new Dimension(5,0)));
		lowerPanel.setBackground(Color.white);

		upperPanel.add(qScroller);
		upperPanel.setBackground(Color.WHITE);
		
		mainPanel.add(upperPanel);
		mainPanel.add(lowerPanel);
		mainPanel.setBackground(Color.WHITE);
		
		// ��Ʈ��ŷ�� �õ��ϰ�, �������� �޽����� ���� ������ ����
		setUpNetworking();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
		
		

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(BorderLayout.EAST, mainPanel);
		frame.getContentPane().add(BorderLayout.CENTER, game);
		frame.setSize(900,600);
		frame.setVisible(true);
	}

	private void setUpNetworking() {  
		try {
			// sock = new Socket("220.69.203.11", 5000);
			sock = new Socket("127.0.0.1", 5000);			// ���� ����� ���� ��Ʈ�� 5000�� ���Ű�� ��
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream());
			game.writer=writer;
			game.reader=reader;
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "�������ӿ� �����Ͽ����ϴ�. ������ �����մϴ�.");
			ex.printStackTrace();
			frame.dispose();		// ��Ʈ��ũ�� �ʱ� ���� �ȵǸ� Ŭ���̾�Ʈ ���� ����
		}
	} // close setUpNetworking   

	// �α��ΰ� �ƿ��� ����ϴ� ��ư�� ��û��. ó������ Login �̾��ٰ� �ϴ� �α��� �ǰ��� Logout�� ó��
	public class LogButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (logButton.getText().equals("Login")) {
				processLogin();
				logButton.setText("Logout");
			}
			else
				processLogout();
		}
		// �α��� ó��
		private void processLogin() {
			user = JOptionPane.showInputDialog("����� �̸��� �Է��ϼ���");
			try {
				writer.writeObject(new UpDownMessage(UpDownMessage.MsgType.LOGIN, user, "", ""));
				writer.flush();
				frame.setTitle(frameTitle + " (�α��� : " + user + ")");
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "�α��� �� �������ӿ� ������ �߻��Ͽ����ϴ�.");
				ex.printStackTrace();
			}
		}
		// �α׾ƿ� ó��
		private void processLogout() {
			int choice = JOptionPane.showConfirmDialog(null, "Logout�մϴ�");
			if (choice == JOptionPane.YES_OPTION) {
				try {
					writer.writeObject(new UpDownMessage(UpDownMessage.MsgType.LOGOUT, user, "", ""));
					writer.flush();
					// ����� ��� ��Ʈ���� ������ �ݰ� ���α׷��� ���� ��
					writer.close(); reader.close(); sock.close();
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "�α׾ƿ� �� �������ӿ� ������ �߻��Ͽ����ϴ�. ���������մϴ�");
					ex.printStackTrace();
				} finally {
					System.exit(100);			// Ŭ���̾�Ʈ ���� ���� 
				}
			}
		}
	}  // close LoginButtonListener inner class
	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String to = (String) counterParts.getSelectedValue();
			if (to == null) {
				JOptionPane.showMessageDialog(null, "�۽��� ����� ������ �� �޽����� ��������");
				return;
			}
			try {
				incoming.append(user + " : " + outgoing.getText() + "\n"); // ���� �޽��� â�� ���̱�
				writer.writeObject(new UpDownMessage(UpDownMessage.MsgType.CLIENT_MSG, user, to, outgoing.getText()));
				writer.flush();
				outgoing.setText("");
				outgoing.requestFocus();
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
				ex.printStackTrace();
			}
		}
	}  // close SendButtonListener inner class

	public class EnterKeyListener implements KeyListener{
		boolean presscheck=false;
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_SHIFT){
				presscheck = true;
			}
			else if(e.getKeyCode()==KeyEvent.VK_ENTER){
				if(presscheck == true){
					String str = outgoing.getText() +"\r\n";
					outgoing.setText(str);
					presscheck = false;
				}
				else{
					e.consume();
					presscheck = false;
					String to = (String) counterParts.getSelectedValue();
					if (to == null) {
						JOptionPane.showMessageDialog(null, "�۽��� ����� ������ �� �޽����� ��������");
						return;
					}
					try {
						incoming.append(user + " : " + outgoing.getText() + "\n"); // ���� �޽��� â�� ���̱�
						incoming.setSelectionStart(incoming.getText().length());
						qScroller.getVerticalScrollBar().setValue(qScroller.getVerticalScrollBar().getMaximum());
						writer.writeObject(new UpDownMessage(UpDownMessage.MsgType.CLIENT_MSG, user, to, outgoing.getText()));
						writer.flush();
						outgoing.setText("");
						outgoing.requestFocus();
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
						ex.printStackTrace();
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_SHIFT){
				presscheck = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	// �������� ������ �޽����� �޴� ������ �۾��� �����ϴ� Ŭ����
	public class IncomingReader implements Runnable {
		public void run() {
			UpDownMessage message;             
			UpDownMessage.MsgType type;
			String[] users={};
			try {
				while (true) {
					message = (UpDownMessage) reader.readObject();     	 // �����α� ������ �޽��� ���                   
					type = message.getType();
					if (type == UpDownMessage.MsgType.LOGIN_FAILURE) {	 // �α����� ������ �����
						JOptionPane.showMessageDialog(null, "Login�� �����Ͽ����ϴ�. �ٽ� �α����ϼ���");
						frame.setTitle(frameTitle + " : �α��� �ϼ���");
						logButton.setText("Login");
					} else if (type == UpDownMessage.MsgType.SERVER_MSG) { // �޽����� �޾Ҵٸ� ������
						if (message.getSender().equals(user)) continue;  // ���� ���� ������ ���� �ʿ� ����
						incoming.append(message.getSender() + " : " + message.getContents() + "\n");
						qScroller.getVerticalScrollBar().setValue(qScroller.getVerticalScrollBar().getMaximum());

					} else if (type == UpDownMessage.MsgType.LOGIN_LIST) {
						// ���� ����Ʈ�� ���� �ؼ� counterParts ����Ʈ�� �־� ��.
						// ����  ���� (""�� ����� ���� �� ����Ʈ �� �տ� ���� ��)
						users = message.getContents().split("/");
						for (int i=0; i<users.length; i++) {
							if (user.equals(users[i]))users[i] = "";
						}
						users = sortUsers(users);		// ���� ����� ���� �� �� �ֵ��� �����ؼ� ����
						users[0] =  UpDownMessage.ALL;	// ����Ʈ �� �տ� "��ü"�� ������ ��
						counterParts.setListData(users);
						counterParts.setSelectedIndex(0);
						frame.repaint();

					} else if (type == UpDownMessage.MsgType.NO_ACT){
						// �ƹ� �׼��� �ʿ���� �޽���. �׳� ��ŵ
					} else if (type == UpDownMessage.MsgType.GAME_INFO){
						game.GameOverCheck(message.i,message.min,message.max);
						
					} else if(type == UpDownMessage.MsgType.GAME_START){

						String my="", you="";
						for (int i=0; i<users.length; i++) {
							if (user.equals(users[i]));
							else you = users[i];
						}
						if(users.length>1) 
						{
							game.gameSet(user , you,message.Answer);
						
							game.setStartCheck(true);

							//	
						}

						else
						{	
							JOptionPane.showMessageDialog(null, "���� ��밡 �����ϴ�.");
							
							game.setTurnCheck(false);
						}
					}else if (type == UpDownMessage.MsgType.LOGOUT){
						try {
							writer.writeObject(new UpDownMessage(UpDownMessage.MsgType.LOGOUT, user, "", ""));
							writer.flush();
							// ����� ��� ��Ʈ���� ������ �ݰ� ���α׷��� ���� ��
							writer.close(); reader.close(); sock.close();
						} catch(Exception ex) {
							JOptionPane.showMessageDialog(null, "�α׾ƿ� �� �������ӿ� ������ �߻��Ͽ����ϴ�. ���������մϴ�");
							ex.printStackTrace();
						} finally {
							System.exit(100);			// Ŭ���̾�Ʈ ���� ���� 
						}
					} 
						else {
							// ��ü�� Ȯ�ε��� �ʴ� �̻��� �޽���
							throw new Exception("�������� �� �� ���� �޽��� ��������");
						}
					} // close while
				} catch(Exception ex) {
					System.out.println("Ŭ���̾�Ʈ ������ ����");		// �������� ����� ��� �̸� ���� ������ ����
				}
			} // close run
		}
		private String [] sortUsers(String [] users) {
			String [] outList = new String[users.length];
			ArrayList<String> list = new ArrayList<String>();
			for (String s : users) {
				list.add(s);
			}
			Collections.sort(list);				// Collections.sort�� ����� �ѹ濡 ����
			for (int i=0; i<users.length; i++) {
				outList[i] = list.get(i);
			}
			return outList;
		}
		
	} // close inner class     

