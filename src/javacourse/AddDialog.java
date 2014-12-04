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
public class AddDialog extends BaseWindow implements ActionListener {

    private final String[] labelText = {"����", "�绰", "E-mail", "ѧԺ", "�Ա�"};
    private final String[] radioText = {"��", "Ů"};
    private final String[] btnText = {"�ύ", "����", "�ر�"};
    private final String[] college = {"���Ͽ�ѧ�빤��ѧԺ", "��ͨѧԺ", "����ѧԺ", "���繤��ѧԺ", "��Դ�붯������ѧԺ", "��ľ�����뽨��ѧԺ", "��������ѧԺ", "��Դ�뻷������ѧԺ", "��Ϣ����ѧԺ", "�������ѧ�뼼��ѧԺ", "�Զ���ѧԺ", "����ѧԺ", "��������ѧԺ", "��ѧԺ", "��ѧ������������ѧѧԺ", "����ѧԺ", "���������ѧԺ", "�����ѧԺ", "�ķ�ѧԺ", "����������ѧԺ", "���ʽ���ѧԺ", "����˼����ѧԺ", "����/��������ѧԺ", "ְҵ����ѧԺ", "������"};

    private final Dialog dialog;
    private final MainFrame parent;

    private final JTextField[] text = new JTextField[labelText.length - 2];
    private final JButton[] btn = new JButton[btnText.length];
    private final JRadioButton[] radio = new JRadioButton[radioText.length];
    private final JComboBox combo = new JComboBox(college);

    /**
     * ���캯��
     *
     * @param main ���������
     */
    public AddDialog(MainFrame main) {

        this.parent = main;

        /* �����Ի��� */
        window = new Dialog(parent.frame, "����ѧ����Ϣ", false);
        dialog = (Dialog) window;

        /* ��ʼ���Ի��� */
        dialog.setBounds(100, 100, 400, 240);
        dialog.setLayout(new GridLayout(6, 1));

        /* ���ӿؼ� */
        this.addLabel();
        this.addCombo();
        this.addRadio();
        this.addButton();
    }

    /**
     * ���ӱ�ǩ
     */
    private void addLabel() {
        JPanel labelPanel;
        for (int i = 0; i < text.length; ++i) {
            text[i] = new JTextField();
            labelPanel = new JPanel();
            labelPanel.setLayout(new GridLayout(1, 2));
            labelPanel.add(new JLabel(labelText[i] + "��"));
            labelPanel.add(text[i]);
            dialog.add(labelPanel);
        }
    }

    /**
     * ������Ͽ�
     */
    private void addCombo() {
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new GridLayout(1, 2));
        comboPanel.add(new JLabel(labelText[labelText.length - 2] + "��"));
        comboPanel.add(combo);
        dialog.add(comboPanel);
    }

    /**
     * ���ӵ�ѡ��
     */
    private void addRadio() {
        ButtonGroup group = new ButtonGroup();
        JPanel radioPanel = new JPanel();
        JPanel labelPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(1, 2));
        labelPanel.setLayout(new GridLayout(1, 2));
        labelPanel.add(new JLabel(labelText[labelText.length - 1] + "��"));
        for (int i = 0; i < radio.length; ++i) {
            radio[i] = new JRadioButton(radioText[i], false);
            group.add(radio[i]);
            radioPanel.add(radio[i]);
        }
        labelPanel.add(radioPanel);
        dialog.add(labelPanel);
    }

    /**
     * ���Ӱ�ť
     */
    private void addButton() {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 3));
        for (int i = 0; i < btn.length; ++i) {
            btn[i] = new JButton(btnText[i]);
            btnPanel.add(btn[i]);
            btn[i].addActionListener(this);
        }
        dialog.add(btnPanel);
    }

    /**
     * �رնԻ���
     */
    @Override
    public void close() {
        StudentInfoManagment.showStudentInfo(parent.table);
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
            this.submitBtnClick();
        } else if (o == btn[1]) {
            this.resetBtnClick();
        } else if (o == btn[2]) {
            this.close();
        }
    }

    /**
     * �ύ��ť����¼�
     */
    private void submitBtnClick() {
        if (isComplete()) {
            Student s = new Student(text[0].getText(), text[1].getText(), text[2].getText(), combo.getSelectedItem().toString(), radio[1].isSelected());
            StudentInfoManagment.addStudentInfo(s);
            this.resetBtnClick();
        } else {
            MessageBox.show("��д����Ϣ��������");
        }
    }

    /**
     * ���ð�ť����¼�
     */
    private void resetBtnClick() {
        for (JTextField t : text) {
            t.setText("");
        }
        radio[0].setSelected(true);
    }

    /**
     * �ж���Ч��
     *
     * @return �Ƿ���д����
     */
    private boolean isComplete() {
        return (radio[0].isSelected() || radio[1].isSelected()) && !text[0].getText().equals("") && !text[1].getText().equals("") && !text[2].getText().equals("");
    }

}