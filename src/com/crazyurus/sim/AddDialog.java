package com.crazyurus.sim;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * ���ѧ����Ϣ�Ի�����
 *
 * @author Crazy Urus
 * @version 1.1.3
 */
public class AddDialog extends BaseWindow implements ActionListener {

    private final String[] labelText = {"ѧ��", "����", "�绰", "E-mail", "����",
        "ѧԺ", "�Ա�"};
    private final String[] radioText = {"��", "Ů"};
    private final String[] btnText = {"null", "����", "�ر�"};
    private final String[] college = {"���Ͽ�ѧ�빤��ѧԺ", "��ͨѧԺ", "����ѧԺ", "���繤��ѧԺ",
        "��Դ�붯������ѧԺ", "��ľ�����뽨��ѧԺ", "��������ѧԺ", "��Դ�뻷������ѧԺ", "��Ϣ����ѧԺ",
        "�������ѧ�뼼��ѧԺ", "�Զ���ѧԺ", "����ѧԺ", "��������ѧԺ", "��ѧԺ", "��ѧ������������ѧѧԺ",
        "����ѧԺ", "���������ѧԺ", "�����ѧԺ", "�ķ�ѧԺ", "����������ѧԺ", "���ʽ���ѧԺ", "���˼����ѧԺ",
        "����/��������ѧԺ", "ְҵ����ѧԺ", "������"};

    private final JDialog dialog;
    private final MainFrame parent;

    private final JTextField[] text = new JTextField[labelText.length - 3];
    private final JButton[] btn = new JButton[btnText.length];
    private final JRadioButton[] radio = new JRadioButton[radioText.length];
    private final JComboBox<?> combo = new JComboBox<Object>(college);
    private final ButtonGroup group = new ButtonGroup();
    private final SpinnerModel model = new SpinnerNumberModel(0, 0, 100, 1);
    private final JSpinner spinner = new JSpinner(model);

    private int type = 1;
    private int index = 1;

    /**
     * ���캯��
     *
     * @param main ���������
     */
    public AddDialog(MainFrame main, int type) {

        this.parent = main;

        /* ���öԻ������� */
        this.type = type;
        String dlgTitle = "";
        if (type == 1) {
            btnText[0] = "���";
            dlgTitle = "���ѧ����Ϣ";
        } else {
            btnText[0] = "�޸�";
            dlgTitle = "�޸�ѧ����Ϣ";
        }

        /* �����Ի��� */
        window = new JDialog(parent.frame, dlgTitle, false);
        dialog = (JDialog) window;

        /* ��ʼ���Ի��� */
        dialog.setSize(400, 270);
        dialog.setLayout(new GridLayout(8, 1));
        this.init();

        /* ��ӿؼ� */
        this.addLabel();
        this.addSpinner();
        this.addCombo();
        this.addRadio();
        this.addButton();
    }

    /**
     * ��ӱ�ǩ
     */
    private void addLabel() {
        JPanel labelPanel;
        for (int i = 0; i < text.length; ++i) {
            text[i] = new JTextField();
            labelPanel = new JPanel(new GridLayout(1, 2));
            labelPanel.add(new JLabel(labelText[i] + "��"));
            labelPanel.add(text[i]);
            dialog.add(labelPanel);
        }
        text[0].setDocument(new NumberLenghtLimited(13));
        text[2].setDocument(new NumberLenghtLimited(11));
    }

    /**
     * �����Ͽ�
     */
    private void addCombo() {
        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.add(new JLabel(labelText[labelText.length - 2] + "��"));
        labelPanel.add(combo);
        dialog.add(labelPanel);
    }

    /**
     * ���΢����
     */
    private void addSpinner() {
        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.add(new JLabel(labelText[labelText.length - 3] + "��"));
        labelPanel.add(spinner);
        dialog.add(labelPanel);
    }

    /**
     * ��ӵ�ѡ��
     */
    private void addRadio() {
        JPanel radioPanel = new JPanel(new GridLayout(1, 2));
        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
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
     * ��Ӱ�ť
     */
    private void addButton() {
        JPanel btnPanel = new JPanel(new GridLayout(1, 3));
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
            Student s = new Student(text[0].getText(), text[1].getText(),
                    (Integer) spinner.getValue(), text[2].getText(),
                    text[3].getText(), combo.getSelectedItem().toString(),
                    radio[1].isSelected());
            if (type == 1) {
                StudentInfoManagment.addStudentInfo(parent.table, s);
                this.resetBtnClick();
            } else {
                StudentInfoManagment.deleteStudentInfo(s.getSNO());
                int i = 0;
                for (String item : s.toStringArray(index + 1)) {
                    parent.table.setValueAt(item, index, i++);
                }
                this.close();
            }
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
        group.clearSelection();
        combo.setSelectedIndex(0);
        spinner.setValue(0);
    }

    /**
     * �ж���Ч��
     *
     * @return �Ƿ���д����
     */
    private boolean isComplete() {
        return (radio[0].isSelected() || radio[1].isSelected())
                && !text[0].getText().equals("")
                && !text[1].getText().equals("")
                && !text[2].getText().equals("")
                && !text[3].getText().equals("")
                && (Integer) spinner.getValue() != 0;
    }

    private int findCollege(String str) {
        for (int i = 0; i < college.length; ++i) {
            if (college[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * д��ѧ����Ϣ
     *
     * @param Student ѧ��
     *
     */
    public void setStudentInfo(Student s, int index) {
        this.index = index;
        text[0].setText(s.getSNO());
        text[1].setText(s.getName());
        text[2].setText(s.getPhone());
        text[3].setText(s.getEmail());
        spinner.setValue(s.getAge());
        if (!s.isFemale()) {
            radio[0].setSelected(true);
        } else {
            radio[1].setSelected(true);
        }
        combo.setSelectedIndex(findCollege(s.getCollege()));
    }

}
