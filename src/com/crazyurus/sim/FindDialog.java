package com.crazyurus.sim;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * ����ѧ����Ϣ�Ի�����
 *
 * @author Crazy Urus
 * @version 1.0.5
 */
public class FindDialog extends BaseWindow implements ActionListener {

    private final String[] btnText = {"����", "�ر�"};

    private final JDialog dialog;
    private final MainFrame parent;

    private final JLabel label = new JLabel("������ѧ��������");
    private final JTextField text = new JTextField();
    private final JButton[] btn = new JButton[2];
    private final JProgressBar progress = new JProgressBar();

    /**
     * ���캯��
     *
     * @param main ���������
     */
    public FindDialog(MainFrame main) {

        this.parent = main;

        /* �����Ի��� */
        window = new JDialog(parent.frame, "����ѧ����Ϣ", false);
        dialog = (JDialog) window;

        /* ��ʼ���Ի��� */
        dialog.setSize(260, 135);
        dialog.setLayout(new GridLayout(3, 1));
        this.init();

        /* ��ӿؼ� */
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(label);
        panel.add(text);
        dialog.add(panel);
        this.initProgress();
        this.addButton();
    }

    /**
     * ��Ӱ�ť
     */
    private void addButton() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        for (int i = 0; i < btn.length; ++i) {
            btn[i] = new JButton(btnText[i]);
            panel.add(btn[i]);
            btn[i].addActionListener(this);
        }
        dialog.add(panel);
    }

    /**
     * ��ʼ��������
     */
    private void initProgress() {
        progress.setOrientation(JProgressBar.HORIZONTAL);
        progress.setStringPainted(true);
        dialog.add(progress);
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
            if (StudentInfoManagment.findStudentInfo(parent.table, progress, text.getText())) {
                //this.close();
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
