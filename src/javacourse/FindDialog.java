package javacourse;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * ����ѧ����Ϣ�Ի�����
 *
 * @author Crazy Urus
 * @version 1.0
 */
public class FindDialog extends BaseWindow implements ActionListener {

    private final String[] btnText = {"����", "�ر�"};

    private final Dialog dialog;
    private final MainFrame parent;

    private final JLabel label = new JLabel("������ѧ��������");
    private final JTextField text = new JTextField();
    private final JButton[] btn = new JButton[2];

    /**
     * ���캯��
     *
     * @param main ���������
     */
    public FindDialog(MainFrame main) {

        this.parent = main;

        /* �����Ի��� */
        window = new Dialog(parent.frame, "����ѧ����Ϣ", false);
        dialog = (Dialog) window;

        /* ��ʼ���Ի��� */
        dialog.setBounds(100, 100, 260, 120);
        dialog.setLayout(new GridLayout(2, 2));

        /* ��ӿؼ� */
        dialog.add(label);
        dialog.add(text);
        this.addButton();
    }

    /**
     * ��Ӱ�ť
     */
    private void addButton() {
        for (int i = 0; i < btn.length; ++i) {
            btn[i] = new JButton(btnText[i]);
            dialog.add(btn[i]);
            btn[i].addActionListener(this);
        }
    }

    /**
     * �رնԻ���
     */
    @Override
    public void close() {
        dialog.dispose();
    }

    /**
     * �¼�����
     *
     * @param e �¼�
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btn[0]) {
            this.findBtnClick();
        } else if (o == btn[1]) {
            this.close();
        }
    }

    /**
     * ���Ұ�ť����¼�
     */
    private void findBtnClick() {
        if (isComplete()) {
            if (StudentInfoManagment.findStudentInfo(parent.table, text.getText())) {
                this.close();
            } else {
                MessageBox.show("δ�ҵ���ѧ������Ϣ��");
            }
        } else {
            MessageBox.show("�㻹û������ѧ��������");
        }

    }

    /**
     * �ж���Ч��
     *
     * @return �Ƿ���д����
     */
    private boolean isComplete() {
        return !text.getText().equals("");
    }

}
