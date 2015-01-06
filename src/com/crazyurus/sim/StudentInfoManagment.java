package com.crazyurus.sim;

import java.util.*;
import java.io.*;

import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

/**
 * ѧ����Ϣ������
 *
 * @author Crazy Urus
 * @version 1.1.0
 */
public final class StudentInfoManagment {

    private final static HashMap<String, Student> s = new HashMap<>();
    private static File file = new File("D:\\myfile.data");

    /**
     * ���ļ�����ѧ����Ϣ
     *
     * @param file �ļ�
     * @param l ���ؼ�
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static void loadStudentInfo(File file, DefaultTableModel l)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois;
        try (FileInputStream fis = new FileInputStream(file)) {
            ois = new ObjectInputStream(fis);
            HashMap<String, Student> temp = (HashMap<String, Student>) ois
                    .readObject();
            for (String key : temp.keySet()) {
                Student t = temp.get(key);
                s.put(key, t);
                showSingle(l, t);
            }
        }
        StudentInfoManagment.file = file;
        ois.close();
    }

    /**
     * ����ѧ����Ϣ���ļ�
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void saveStudentInfo() throws FileNotFoundException,
            IOException {
        ObjectOutputStream oos;
        try (FileOutputStream fos = new FileOutputStream(file)) {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
        }
        oos.close();
    }

    /**
     * ɾ��ѧ����Ϣ
     *
     * @param sno ѧ��
     */
    public static void deleteStudentInfo(String sno) {
        s.remove(sno);
    }

    public static void deleteStudentInfo(DefaultTableModel l, String sno, int index) {
        l.removeRow(index);
        deleteStudentInfo(sno);
    }

    /**
     * ����б�����ʾ����Ϣ
     *
     * @param l �б���
     */
    public static void clearStudentInfo(DefaultTableModel l) {
        int num = l.getRowCount();
        for (int i = num - 1; i >= 0; --i) {
            l.removeRow(i);
        }
    }

    /**
     * ���б�����ʾѧ����Ϣ
     *
     * @param l ���ؼ�
     */
    public static void showStudentInfo(DefaultTableModel l) {
        clearStudentInfo(l);
        for (String key : s.keySet()) {
            Student e = getStudentInfo(key);
            showSingle(l, e);
        }
    }

    /**
     * ���ѧ����Ϣ
     *
     * @param l ���ؼ�
     * @param e ѧ��
     */
    public static void addStudentInfo(DefaultTableModel l, Student e) {
        s.put(e.getSNO(), e);
        showSingle(l, e);
    }

    /**
     * ��ȡѧ����Ϣ
     *
     * @param e ѧ��ѧ��
     * @return ѧ��
     */
    public static Student getStudentInfo(String sno) {
        return s.get(sno);
    }

    /**
     * ����ѧ����Ϣ
     *
     * @param l ���ؼ�
     * @param p �������ؼ�
     * @param name ����
     * @return �Ƿ��ҵ�
     */
    public static boolean findStudentInfo(DefaultTableModel l, JProgressBar p,
            String name) {
        boolean isFind = false;
        int i = 1;
        p.setMinimum(0);
        p.setMaximum(s.keySet().size() + 5);
        clearStudentInfo(l);
        p.setValue(5);
        for (String key : s.keySet()) {
            Student e = getStudentInfo(key);
            p.setValue(5 + i++);
            if (name.equals(e.getName())) {
                showSingle(l, e);
                isFind = true;
            }
        }
        return isFind;
    }

    /**
     * ���б���ӵ���ѧ����Ϣ
     *
     * @param l ���ؼ�
     * @param e ѧ��
     */
    private static void showSingle(DefaultTableModel l, Student e) {
        int index = l.getRowCount();
        l.addRow(e.toStringArray(index + 1));
    }

}
