package javacourse;

import java.util.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

/**
 * ѧ����Ϣ������
 *
 * @author Crazy Urus
 * @version 1.0
 */
public final class StudentInfoManagment {

    private final static HashMap<String, Student> s = new HashMap<>();
    private static File file = new File("C:\\myfile.data");

    /**
     * ���ļ�����ѧ����Ϣ
     *
     * @param file �ļ�
     * @param l ���ؼ�
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public static void loadStudentInfo(File file, DefaultTableModel l) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois;
        try (FileInputStream fis = new FileInputStream(file)) {
            ois = new ObjectInputStream(fis);
            HashMap<String, Student> temp = (HashMap<String, Student>) ois.readObject();
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
    public static void saveStudentInfo() throws FileNotFoundException, IOException {
        ObjectOutputStream oos;
        try (FileOutputStream fos = new FileOutputStream(file)) {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
        }
        oos.close();
    }

    /**
     * ����б�����ʾ����Ϣ
     *
     * @param l �б���
     */
    public static void clearStudentInfo(DefaultTableModel l) {
        for (int i = 0; i < l.getRowCount(); ++i) {
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
            Student e = s.get(key);
            showSingle(l, e);
        }
    }

    /**
     * ���ѧ����Ϣ
     *
     * @param e ѧ��
     */
    public static void addStudentInfo(Student e) {
        s.put(e.getName(), e);
    }

    /**
     * ����ѧ����Ϣ
     *
     * @param l ���ؼ�
     * @param name ����
     * @return �Ƿ��ҵ�
     */
    public static boolean findStudentInfo(DefaultTableModel l, String name) {
        boolean isFind = false;
        clearStudentInfo(l);
        for (String key : s.keySet()) {
            Student e = s.get(key);
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
