import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;








public class UpDownGame extends JPanel {

	//this = new GamePanel();

	int minLimit = 1,maxLimit = 100;
	int Limit = 0;

	public enum UserTurn{user1, user2, user3};
	private JPanel labelPanel = new JPanel();	// ���� �� ����Ȯ��
	public JPanel gamePanel = new JPanel();	// �� �ȿ��� ��Ʈ�� �����ư ���� �̹����ߴ� �г�  �����г�  ���� ���̴°�
	private JLabel user1Count, user2Count, turnLabel, user1Label, user2Label,playLabel;
	private JLabel LimitLabel;
	JLabel SGround = new JLabel();
	JLabel MGround = new JLabel();
	JLabel LGround = new JLabel();
	public JPanel panel, countSet, turnLabelPanel, menuPanel,userPanel,LabelPanel;

	public JFrame answer = new JFrame(); //���� ���ߴ� ������  ���� ��ư �θ��� ��

	public JTextField outanswer = new JTextField();			// ������ �ۼ��ϴ� ��


	private JButton quit;						// Quit : ������ ��ư
	private JButton start;						// ��ŷ��� ��ư
	private JButton answerb;

	public JTextField user1,user2;
	String my, you;
	String mLimit,MLimit;
	private Font font;

	private boolean turnCheck=false;
	private boolean startCheck=false;
	public boolean userCheck = false;


	private ImageIcon User1Icon, User2Icon,User1Paly,User2Paly,playgroundL,playground,playgroundS,playstand,UpIcon,DownIcon;





	int Useranswer,user2answer;

	String UserAnswer;
	int answercheck;

	ObjectInputStream reader;	// ���ſ� ��Ʈ��
	ObjectOutputStream writer;	// �۽ſ� ��Ʈ��

	public UpDownGame(){

		panel = new JPanel();
		countSet = new JPanel();
		turnLabelPanel = new JPanel();
		menuPanel = new JPanel();
		userPanel = new JPanel();
		LabelPanel = new JPanel();

		quit = new JButton("Quit");
		quit.setBackground(new Color(215,235,209));
		start = new JButton("Start");
		start.setBackground(new Color(229,238,206));
		answerb = new JButton("answer");
		answerb.setBackground(new Color(229,238,206));
		
		answerb.addActionListener(new AnswerButtonListener());
		quit.addActionListener(new QuitListener());
		User1Icon = new ImageIcon("images/User1count.PNG");		// �̹��� ������ ��ü����
		User2Icon = new ImageIcon("images/User2count.PNG");


		User1Paly = new ImageIcon("images/User1.PNG");		// �̰� �������� �޸��
		User2Paly = new ImageIcon("images/User2.PNG");


		playgroundL  = new ImageIcon("images/���.PNG");
		playgroundS  = new ImageIcon("images/�������.PNG");
		playground  = new ImageIcon("images/ȸ�����.PNG");

		playstand = new ImageIcon("images/standby.PNG");	
		UpIcon  = new ImageIcon("images/UP.PNG");
		DownIcon  = new ImageIcon("images/Down.PNG");

		//�̰� ������ �ϰ� ���� �����ִ� ��
		user1Count = new JLabel(User1Icon, SwingConstants.CENTER);
		user1Count.setHorizontalTextPosition(SwingConstants.HORIZONTAL);
		user2Count = new JLabel(User2Icon, SwingConstants.CENTER);
		user2Count.setHorizontalTextPosition(SwingConstants.HORIZONTAL);


		//�̰� ������ �������Բ� ������ �����ش� 1 2 3�� ������ �����ִ°�
		user1Label = new JLabel(User1Paly, SwingConstants.CENTER);
		user1Label.setHorizontalTextPosition(SwingConstants.HORIZONTAL);
		user2Label = new JLabel(User2Paly, SwingConstants.CENTER);
		user2Label.setHorizontalTextPosition(SwingConstants.HORIZONTAL);


		//�̰� ���� ��ư�� ������ ������ ġ�� ������ �̹�����
		playLabel = new JLabel("",playstand, SwingConstants.CENTER);
		playLabel.setHorizontalTextPosition(SwingConstants.HORIZONTAL);

		turnLabel = new JLabel("Turn ", User1Icon, SwingConstants.CENTER);
		turnLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		user1 = new JTextField();
		user2 = new JTextField();

		mLimit = new String(Integer.toString(minLimit));
		MLimit = new String(Integer.toString(maxLimit));
		LimitLabel = new JLabel("�Ѱ� : "+mLimit+ "~~"+ MLimit);

	
		//�̰� ���ȭ�鿡�� ���ƴ��� �̹���
		
		
				LGround = new JLabel(playgroundL);
			
				MGround = new JLabel(playground);
			 SGround = new JLabel(playgroundS);
		
		//��Ʈ����
		font = new Font("Showcard Gothic", Font.PLAIN, 25);
		turnLabel.setFont(font);
		font = new Font("MD�Ʒ�ü", Font.PLAIN, 25);
		user1.setFont(font);
		user2.setFont(font);

		user1.setEditable(false);
		user2.setEditable(false);



		countSet.setLayout(null);

		user1.setBounds(20,15,70,40);
		user1Count.setBounds(90,10,40,50);
		user2.setBounds(130,15,70,40);
		user2Count.setBounds(200,10,40,50);

		turnLabel.setBounds(360,5,120,60);

		user1Label.setBounds(30, 350, 150, 150);
		user2Label.setBounds(140, 350, 150, 150);


		user1.setHorizontalAlignment(JTextField.CENTER);
		user2.setHorizontalAlignment(JTextField.CENTER);

		font = new Font("Bauhaus 93", Font.PLAIN, 20);
		user1Count.setFont(font);
		user2Count.setFont(font);


		countSet.add(user1);
		countSet.add(user1Count);
		countSet.add(user2);
		countSet.add(user2Count);


		turnLabelPanel.add(turnLabel);
		countSet.add(turnLabelPanel);


		userPanel.add(BorderLayout.SOUTH,MGround);
		userPanel.add(BorderLayout.CENTER,user1Label);
		userPanel.add(BorderLayout.EAST,user2Label);




		start.addActionListener(new StartButtonListener());
		countSet.setLayout(new BoxLayout(countSet, BoxLayout.X_AXIS));
		labelPanel.add(BorderLayout.WEST,countSet);


		menuPanel.setLayout(new GridLayout(1,4));// ������ ũ�⸦ ���� ���� ���� ���� ÷��.	
		menuPanel.add(start);

		menuPanel.add(quit);
		menuPanel.add(answerb);

		menuPanel.add(LimitLabel);
		menuPanel.setBackground(Color.white);

		panel.add(menuPanel);
		panel.add(userPanel);



		countSet.setPreferredSize(new Dimension(55,55));
		countSet.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));



		add(LGround);		
		add(SGround);		
LGround.setBounds(50, 100, 80, 50);
SGround.setBounds(400, 180, 80, 50);

		LabelPanel.add(playLabel);
		LabelPanel.setBackground(Color.white);


		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, countSet);
		add(BorderLayout.CENTER,LabelPanel);
		add(BorderLayout.SOUTH, panel);//�̰� ���߿� �׸��� ���̾ƿ����� ����



		user1Count.setBackground(Color.white);
		user2Count.setBackground(Color.white);
		user1.setBackground(Color.white);
		user2.setBackground(Color.WHITE);
		turnLabel.setBackground(Color.WHITE);
		turnLabelPanel.setBackground(Color.WHITE);
		userPanel.setBackground(Color.WHITE);
		panel.setBackground(Color.white);
		countSet.setBackground(Color.WHITE);

		this.setBackground(Color.WHITE);


	}




	public void setStartCheck(boolean x)
	{
		startCheck = x;
	}
	public void setTurnCheck(boolean x)
	{
		turnCheck = x;
	}
	private class QuitListener implements ActionListener 	// ���� ��ư(��� �����Ӱ� â��  ������ ������.)
	{    
		public void actionPerformed(ActionEvent event) 
		{ 	
			System.exit(100);			// Ŭ���̾�Ʈ ���� ���� 
		}	



	}

	public void gameSet(String my, String you,int Answer){

		Limit = 0;
		minLimit =1;
		maxLimit = 100;
		mLimit = Integer.toString(minLimit); 
		MLimit = Integer.toString(maxLimit);  
		LimitLabel.setText("�Ѱ� : "+mLimit+ "~~"+ MLimit);
		Useranswer = Answer;
		turnLabel.setIcon(User1Icon);
	
		//��������Ʈ
		System.out.printf("����  : %d    \n",Useranswer);


		if(userCheck) {
			user1.setText(my); 
			user2.setText(you); 
		}
		if(!userCheck) {
			user1.setText(you); 
			user2.setText(my);
		}

		if(this.turnCheck){ countSet.setBackground(new Color(100,200,40));}
		else { countSet.setBackground(Color.red);}
	}

	public class StartButtonListener implements ActionListener {
		 Random r =  new Random();
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(!startCheck){ 

				try {

					startCheck = true;
					turnCheck = true;
					userCheck = true;
					Useranswer = (int)(r.nextInt(100)+1);
					if(Useranswer <=0)
						Useranswer =1;
					if(Useranswer >=100)
						Useranswer =100;
					writer.writeObject(new UpDownMessage(UpDownMessage.MsgType.GAME_START, userCheck,Useranswer));
					writer.flush();

				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
					ex.printStackTrace();
				}
			}
			else  JOptionPane.showMessageDialog(null, "������ �������Դϴ�.");
		}
	}
	public class AnswerButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(turnCheck){
				UserAnswer = JOptionPane.showInputDialog("������ �Է��ϼ���");

				if(isStringDouble(UserAnswer)){

					answercheck = Integer.parseInt( UserAnswer );
					if(LimitCheck(answercheck)){
						try {					
							writer.writeObject(new UpDownMessage(UpDownMessage.MsgType.GAME_INFO,  answercheck,minLimit,maxLimit));
							writer.flush();
						}
						catch(Exception ex) {
							JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
							ex.printStackTrace();
						}
					}
					else JOptionPane.showMessageDialog(null, " ������ �Ѱ� ������ ���� �Է��ϼ���.");
				}
				else{
					UserAnswer = JOptionPane.showInputDialog("������ ���ڷ� �ٽ� �Է��ϼ���");
					if(isStringDouble(UserAnswer)){

						answercheck = Integer.parseInt( UserAnswer );
						if(LimitCheck(answercheck)){
							try {
								writer.writeObject(new UpDownMessage(UpDownMessage.MsgType.GAME_INFO,  answercheck,minLimit,maxLimit));
								writer.flush();
							}
							catch(Exception ex) {
								JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
								ex.printStackTrace();
							}
						}
						else JOptionPane.showMessageDialog(null, " ������ �Ѱ� ������ ���� �Է��ϼ���.");
					}
					else JOptionPane.showMessageDialog(null, " ������ ���ڷ� �Է����� �ʾҽ��ϴ�.\n        (��õ� �ϼ���.)");

				}

			}
			else  JOptionPane.showMessageDialog(null, " ������ �Է��Ҽ� �����ϴ�.");
		}
	}
	public static boolean isStringDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	public boolean LimitCheck(int AN){
		if(Limit == 0){
			if(minLimit <= AN && AN <= maxLimit) {


				LimitAdjustment(AN);
				return true;		

			}
			else return false;
		}
		else{
			if(minLimit < AN && AN < maxLimit) {


				LimitAdjustment(AN);
				return true;		

			}
			if(minLimit == AN)
				return false;
			else if(maxLimit == AN)
				return false;
			else return false;
		}

	}
	public void LimitAdjustment(int AN){


		if(AN>Useranswer) maxLimit = AN-1;
		else minLimit = AN+1;
		mLimit = (Integer.toString(minLimit));
		MLimit = (Integer.toString(maxLimit));
		LimitLabel.setText("�Ѱ� : "+mLimit+ "~~"+ MLimit);
	}

	public void GameOverCheck(int i,int min,int max){
		int check;
		boolean overCheck = false;

		minLimit = min;
		maxLimit = max;

		if(i==Useranswer){ 
			overCheck=true;
			if(turnLabel.getIcon() == User1Icon)
				JOptionPane.showMessageDialog(null, user1.getText()+"��  WIN !!"); 
			else 
				JOptionPane.showMessageDialog(null, user2.getText()+"��  WIN !!"); 
		}


		else if(i>Useranswer)
		{
			overCheck=false;
			check = 1;
			BoardSet(check);
		}

		else if(i<Useranswer)
		{
			overCheck=false;
			check = 2;
			BoardSet(check);
		}
		if(overCheck)
		{
			
			playLabel.setIcon(playstand);
			LGround.setIcon(playgroundS);
			SGround.setIcon(playgroundL);
			startCheck = false;
			turnCheck = false;
			userCheck = false;

		}


	}

	public void BoardSet(int check){
		turnCheck=!turnCheck;
		mLimit = Integer.toString(minLimit); 
		MLimit = Integer.toString(maxLimit);  
		LimitLabel.setText("�Ѱ� : "+mLimit+ "~~"+ MLimit);
		LGround.setIcon(playgroundS);
		SGround.setIcon(playgroundL);
		if(this.turnCheck){countSet.setBackground(new Color(100,200,40));}
		else { countSet.setBackground(Color.red);}

		if(user1.getBackground().equals(Color.GREEN)) System.out.println("ü����");//user1.setBackground(Color.RED);
		if(user1.getBackground().equals(Color.RED)) user1.setBackground(Color.GREEN);
		if(user2.getBackground().equals(Color.GREEN)) user1.setBackground(Color.RED);
		if(user2.getBackground().equals(Color.RED)) user1.setBackground(Color.GREEN);

		if(check == 1){

			playLabel.setIcon(DownIcon);
			check =0;
		}

		else if(check == 2){

			playLabel.setIcon(UpIcon);
			check =0;
		}

		if(turnLabel.getIcon() == User1Icon)
			turnLabel.setIcon(User2Icon);
		else 	turnLabel.setIcon(User1Icon);

	}

}


