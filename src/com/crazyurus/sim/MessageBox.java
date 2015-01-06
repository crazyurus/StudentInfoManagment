package com.crazyurus.sim;

import javax.swing.JOptionPane;

/**
 * ��Ϣ��ʾ�Ի���
 *
 * @version 1.0.0
 * @author Crazy Urus
 */
public final class MessageBox {

    private final static String title = "ѧ����Ϣ����ϵͳ";

    /**
     * ��ʾ��ͨ�Ի���
     *
     * @param msg ��ʾ��Ϣ
     * @param title
     */
    public static void msg(String msg, String title) {
        JOptionPane.showMessageDialog(null, msg, title,
                JOptionPane.PLAIN_MESSAGE);
    }

    public static void msg(String msg) {
        msg(msg, MessageBox.title);
    }

    /**
     * ��ʾ��ʾ�Ի���
     *
     * @param msg ��ʾ��Ϣ
     * @param title
     */
    public static void show(String msg, String title) {
        JOptionPane.showMessageDialog(null, msg, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void show(String msg) {
        show(msg, MessageBox.title);
    }

    /**
     * ��ʾȷ�϶Ի���
     *
     * @param msg ��ʾ��Ϣ
     * @param title
     * @return ״̬
     */
    public static boolean confirm(String msg, String title) {
        return JOptionPane.showConfirmDialog(null, msg, title,
                JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION;
    }

    public static boolean confirm(String msg) {
        return confirm(msg, MessageBox.title);
    }

}
