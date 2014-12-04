package javacourse;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Crazy Urus
 */
abstract public class BaseWindow extends WindowAdapter implements ActionListener {

    protected Window window;
    
    /**
     * ��ʾ������
     */
    public void show() {
        window.setVisible(true);
    }

    /**
     * ����������
     */
    public void hide() {
        window.setVisible(false);
    }

    /**
     * �ر�
     */
    abstract public void close();

    /**
     * �¼�����
     *
     * @param e �¼�
     */
    @Override
    abstract public void actionPerformed(ActionEvent e);

    /**
     * ϵͳ�ر��¼�
     *
     * @param e �¼�
     */
    @Override
    public void windowClosing(WindowEvent e) {
        this.close();
    }
}