package com.crazyurus.sim;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.filechooser.*;

/**
 * ��������
 * 
 * @author Crazy Urus
 * @version 1.0
 */
public final class MainFrame extends BaseWindow implements ActionListener {

	private final String[] menuInfo = { "����(F)", "����(A)" };
	private final String[][] menuItemInfo = {
			{ "����(L)", "����(S)", "-", "���ѧ��(A)", "�༭(E)", "ɾ��(D)", "ˢ��(R)",
					"����(F)", "-", "�˳�(E)" }, { "����(H)", "����(A)" } };
	private final String[] tableColumn = { "���", "ѧ��", "����", "�Ա�", "����", "�ֻ�",
			"E-mail", "ѧԺ" };

	public final JFrame frame;
	private final JMenuBar m_MenuBar = new JMenuBar();
	private final JMenu[] m_Menu = new JMenu[menuInfo.length];
	private final JMenuItem[] m_MenuItem = new JMenuItem[12];
	public final DefaultTableModel table;
	private final JTable m_Table;

	/**
	 * ���캯��
	 */
	public MainFrame() {

		window = new JFrame("ѧ����Ϣ����ϵͳ");
		frame = (JFrame) window;
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		this.table = new DefaultTableModel(null, tableColumn) {
			private static final long serialVersionUID = 1L;

			/**
			 * ���Ʊ���Ƿ�ɱ༭
			 * 
			 * @param row
			 *            ������
			 * @param column
			 *            ������
			 * @return �Ƿ�ɱ༭
			 */
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		m_Table = new JTable(table);
		m_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_Table.setRowSorter(new TableRowSorter<>(table));

		/* �ؼ������ʼ�� */
		this.initMenu();
		this.initTable();

		/* ������λ�á��������� */
		frame.setSize(720, 540);
		frame.setLayout(new GridLayout(1, 1));
		frame.setJMenuBar(m_MenuBar);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.init();
	}

	/**
	 * ���һ���˵���
	 */
	private void initMenu() {
		int num = menuInfo.length;
		for (int i = 0; i < num; ++i) {
			m_Menu[i] = new JMenu(menuInfo[i]);
			initMenuItem(i);
			m_MenuBar.add(m_Menu[i]);
		}
	}

	/**
	 * ��Ӷ����˵���
	 * 
	 * @param index
	 *            һ���˵����
	 */
	private void initMenuItem(int index) {
		int pre = 0;
		int num = menuItemInfo[index].length;
		if (index > 0) {
			pre = menuItemInfo[index - 1].length;
		}
		for (int i = pre; i < num + pre; ++i) {
			String value = menuItemInfo[index][i - pre];
			if (value.equals("-")) {
				m_Menu[index].addSeparator();
			} else {
				m_MenuItem[i] = new JMenuItem(value);
				m_Menu[index].add(m_MenuItem[i]);
				m_MenuItem[i].addActionListener(this);
			}
		}
	}

	/**
	 * ��ʼ���б�
	 */
	private void initTable() {
		JScrollPane jsc = new JScrollPane(m_Table);
		frame.add(jsc);
	}

	/**
	 * �жϱ���Ƿ�Ϊ��
	 * 
	 * @return �Ƿ�Ϊ��
	 */
	private boolean isEmpty() {
		return table.getRowCount() == 0;
	}
	
	/**
	 * �жϱ���Ƿ���ѡ����
	 * 
	 * @return ѡ����index
	 */
	private int checkSelect() {
		int cur = m_Table.getSelectedRow();
		if (cur == -1) {
			MessageBox.show("û���κ�ѧ����Ϣ��ѡ�У�");
		}
		return cur;
	}

	/**
	 * �رնԻ���
	 */
	@Override
	public void close() {
		if (MessageBox.confirm("ȷ��Ҫ�˳���ϵͳ��")) {
			System.exit(0);
		}
	}

	/**
	 * �¼�����
	 * 
	 * @param e
	 *            �¼�
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == m_MenuItem[0]) { // ��������
			this.loadMenuClick();
		} else if (o == m_MenuItem[1]) { // ��������
			this.saveMenuClick();
		} else if (o == m_MenuItem[3]) { // �������
			this.addMenuClick();
		} else if (o == m_MenuItem[4]) { // �༭����
			this.editMenuClick();
		} else if (o == m_MenuItem[5]) { // ɾ������
			this.deleteMenuClick();
		} else if (o == m_MenuItem[6]) { // ˢ������
			this.showMenuClick();
		} else if (o == m_MenuItem[7]) { // ��������
			this.findMenuClick();
		} else if (o == m_MenuItem[9]) { // �˳�
			this.exitMenuClick();
		} else if (o == m_MenuItem[10]) { // ����
			MessageBox.show("���ް���", "����");
		} else if (o == m_MenuItem[11]) { // ����
			MessageBox.msg("ѧ����Ϣ����ϵͳ\n����������߿γ����\n���ߣ����zy1201  ����", "����");
		}
	}

	private void loadMenuClick() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("ѡ�������ļ�");
		chooser.setApproveButtonText("����");
		chooser.setCurrentDirectory(new File("C:\\"));
		chooser.setFileFilter(new FileNameExtensionFilter("�����ļ� (*.data)",
				"data"));
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			try {
				StudentInfoManagment.loadStudentInfo(chooser.getSelectedFile(),
						table);
			} catch (IOException | ClassNotFoundException ex) {
				MessageBox.show("���������ļ�ʱ����");
			}
		}
	}

	private void saveMenuClick() {
		if (this.isEmpty()) {
			MessageBox.show("����ѧ����Ϣ�ɱ��棡");
		} else {
			try {
				StudentInfoManagment.saveStudentInfo();
				MessageBox.show("���ݱ���ɹ���");
			} catch (IOException ex) {
				MessageBox.show("��������ʱ����");
			}
		}

	}

	private void addMenuClick() {
		AddDialog d = new AddDialog(this, 1);
		d.show();
	}

	private void editMenuClick() {
		int cur = checkSelect();
		if (cur != -1) {
			AddDialog d = new AddDialog(this, 2);
			String sno=(String) table.getValueAt(cur, 1);
			d.setStudentInfo(StudentInfoManagment.getStudentInfo(sno));
			d.show();
			StudentInfoManagment.deleteStudentInfo(sno);
			table.removeRow(cur);
		}
	}

	private void deleteMenuClick() {
		int cur = checkSelect();
		if (cur != -1) {
			if (MessageBox
					.confirm("ȷ��ɾ�� " + table.getValueAt(cur, 2) + " ����Ϣ��")) {
				StudentInfoManagment.deleteStudentInfo(table.getValueAt(cur, 1)
						.toString());
				table.removeRow(cur);
			}
		}
	}

	private void showMenuClick() {
		StudentInfoManagment.showStudentInfo(table);
	}

	private void findMenuClick() {
		if (this.isEmpty()) {
			MessageBox.show("����ѧ����Ϣ�ɹ����ң�");
		} else {
			FindDialog d = new FindDialog(this);
			d.show();
		}
	}

	private void exitMenuClick() {
		this.close();
	}

}
