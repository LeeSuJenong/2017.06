import java.awt.event.ActionEvent;
import java.io.Serializable;

import javax.swing.JButton;

public class UpDownMessage implements Serializable {
	// �޽��� Ÿ�� ����
	// 1���� �޽��� ���� �ʵ�� 3���� String�� �ʵ�.
	// NO_ACT�� ������ �� �ִ� Dummy �޽���. ������ ������ ����ϱ� ���� ����� ����
	// (1) Ŭ���̾�Ʈ�� ������ �޽��� ����
	//	- LOGIN  : CLIENT �α���.
	//		�޽��� ���� : LOGIN, "�۽���", "", ""
	//	- LOGOUT : CLIENT �α׾ƿ�.
	//		�޽��� ���� : LOGOUT, "�۽���", "", ""
	// 	- CLIENT_MSG : �������� ������  ��ȭ .
	// 		�޽�������  : CLIENT_MSG, "�۽���", "������", "����"
	// (2) ������ ������ �޽��� ����
	// 	- LOGIN_FAILURE  : �α��� ����
	//		�޽��� ���� : LOGIN_FAILURE, "", "", "�α��� ���� ����"
	// 	- SERVER_MSG : Ŭ���̾�Ʈ���� �������� ������ ��ȭ 
	//		�޽�������  : SERVER_MSG, "�۽���", "", "����" 
	// 	- LOGIN_LIST : ���� �α����� ����� ����Ʈ.
	//		�޽��� ���� : LOGIN_LIST, "", "", "/�� ���е� ����� ����Ʈ"
	public enum MsgType {NO_ACT, LOGIN, LOGOUT, CLIENT_MSG, LOGIN_FAILURE, SERVER_MSG, LOGIN_LIST, GAME_INFO, GAME_START};
	public static final String ALL = "��ü";	 // ����� �� �� �ڽ��� ������ ��� �α��εǾ� �ִ�
											 // ����ڸ� ��Ÿ���� �ĺ���
	private MsgType type;
	private String sender;
	private String receiver;
	private String contents;	

	public int min;
	public int max;
	public int Answer;
	
	
	public int i,j;
	public boolean userCheck;
	
	public UpDownMessage() {
		this(MsgType.NO_ACT, "", "", "");
	}
	public UpDownMessage(MsgType t, String sID, String rID, String mesg) {
		type = t;
		sender = sID;
		receiver = rID;
		contents = mesg;
	}
	
	public UpDownMessage(MsgType t, int i){
		type = t;
		this.i=i;
		
	}
	public UpDownMessage(MsgType t, int i,int min,int max){
		type = t;
		this.i=i;
		this.min = min;
		this.max = max;
	}
	
	public UpDownMessage(MsgType t, boolean uk,int Answer){
		type = t;
		userCheck = uk;
		this.Answer = Answer;
	}
	
	public void setType (MsgType t) {
		type = t;
	}
	public MsgType getType() {
		return type;
	}

	public void setSender (String id) {
		sender = id;
	}
	public String getSender() {
		return sender;
	}
	
	public void setReceiver (String id) {
		receiver = id;
	}
	public String getReceiver() {
		return receiver;
	}
	
	public void setContents (String mesg) {
		contents = mesg;
	}
	public String getContents() {
		return contents;
	}
	
	public String toString() {
		return ("�޽��� ���� : " + type + "\n" +
				"�۽���         : " + sender + "\n" +
				"������         : " + receiver + "\n" +
				"�޽��� ���� : " + contents + "\n");
	}	
}
