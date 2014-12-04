package javacourse;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
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

    private final String[] menuInfo = {"����(F)", "����(A)"};
    private final String[][] menuItemInfo = {{"����(L)", "����(S)", "-", "���ѧ��(A)", "ɾ��(D)", "ˢ��(R)", "����(F)", "-", "�˳�(E)"}, {"����(H)", "����(A)"}};
    private final String[] tableColumn = {"���","ѧ��", "����", "�Ա�", "�ֻ�", "E-mail", "ѧԺ"};

    public final JFrame frame;
    private final MenuBar m_MenuBar = new MenuBar();
    private final Menu[] m_Menu = new Menu[menuInfo.length];
    private final MenuItem[] m_MenuItem = new MenuItem[11];
    public final DefaultTableModel table;
    private final JTable m_Table;

    /**
     * ���캯��
     */
    @SuppressWarnings("")
    public MainFrame() {

        window = new JFrame("ѧ����Ϣ����ϵͳ");
        frame = (JFrame) window;
        this.table = new DefaultTableModel(null, tableColumn) {

            /**
             * ���Ʊ���Ƿ�ɱ༭
             *
             * @param row ������
             * @param column ������
             * @return �Ƿ�ɱ༭
             */
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        m_Table = new JTable(table);

        /* �ؼ������ʼ�� */
        this.initMenu();
        this.initTable();

        /* ������λ�á��������� */
        frame.setSize(720, 540);
        frame.setLayout(new GridLayout(1, 1));
        frame.setMenuBar(m_MenuBar);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.init();
    }

    /**
     * ���һ���˵���
     */
    private void initMenu() {
        int num = menuInfo.length;
        for (int i = 0; i < num; ++i) {
            m_Menu[i] = new Menu(menuInfo[i]);
            initMenuItem(i);
            m_MenuBar.add(m_Menu[i]);
        }
    }

    /**
     * ��Ӷ����˵���
     *
     * @param index һ���˵����
     */
    private void initMenuItem(int index) {
        int pre = 0;
        int num = menuItemInfo[index].length;
        if (index > 0) {
            pre = menuItemInfo[index - 1].length;
        }
        for (int i = pre; i < num + pre; ++i) {
            m_MenuItem[i] = new MenuItem(menuItemInfo[index][i - pre]);
            m_Menu[index].add(m_MenuItem[i]);
            m_MenuItem[i].addActionListener(this);
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
     * @param e �¼�
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == m_MenuItem[0]) {  //��������
            this.loadMenuClick();
        } else if (o == m_MenuItem[1]) {  //��������
            this.saveMenuClick();
        } else if (o == m_MenuItem[3]) {  //�������
            this.addMenuClick();
        } else if (o == m_MenuItem[4]) {  //�������
            this.deleteMenuClick();
        } else if (o == m_MenuItem[5]) {  //ˢ������
            this.showMenuClick();
        } else if (o == m_MenuItem[6]) {  //��������
            this.findMenuClick();
        } else if (o == m_MenuItem[8]) {  //�˳�
            this.exitMenuClick();
        } else if (o == m_MenuItem[9]) {  //����
            MessageBox.show("���ް���", "����");
        } else if (o == m_MenuItem[10]) {  //����
            MessageBox.msg("ѧ����Ϣ����ϵͳ\nJava������Ƶ����ογ�ʵ��\n���ߣ����zy1201  ����", "����");
        }
    }

    private void loadMenuClick() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("ѡ�������ļ�");
        chooser.setApproveButtonText("����");
        chooser.setCurrentDirectory(new File("C:\\"));
        chooser.setFileFilter(new FileNameExtensionFilter("�����ļ� (*.data)", "data"));
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                StudentInfoManagment.loadStudentInfo(chooser.getSelectedFile(), table);
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
        AddDialog d = new AddDialog(this);
        d.show();
    }

    private void deleteMenuClick() {
        int cur = m_Table.getSelectedRow();
        if (cur == -1) {
            MessageBox.show("û���κ�ѧ����Ϣ��ѡ�У�");
        } else {
            if (MessageBox.confirm("ȷ��ɾ�� " + table.getValueAt(cur, 2) + " ����Ϣ��")) {
                StudentInfoManagment.deleteStudentInfo(table.getValueAt(cur,1).toString());
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
