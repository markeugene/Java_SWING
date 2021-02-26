package com.company;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Panel extends JFrame {

    int k;
    static int c = 0;
    String card;
    Object box;
    String chosen1=null, chosen2=null;
    File file = new File("D://01_QUIZ//card.txt");//файл

    static JButton send;//кнопка отправить
    static JRadioButton sex1, sex2;//кнопка пола
    static JTextField textfio, inf;//текстовые поля
    static JComboBox check;//выпадающий список
    static String[] Mylist = {"Minsk", "Vitebsk", "Brest", "Gomel", "Grodno", "Brest", "Mogiliev"};//города
    static String[] Cards = {"Visa", "Mastercard"};//типы кард
    public Panel() {
        super("Create a debit card");//вызов конструктора супер класса
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//действие по закрытию
        setSize(1080, 720);//размера окна
        setResizable(false);//нельзя его изменять(растягивать)
        setVisible(true);//становиться видимым

        JButton send = new JButton("Send");//создание кнопки сенд и выделение памяти

        JRadioButton sex1 = new JRadioButton("Male");//тоже самое
        JRadioButton sex2 = new JRadioButton("Female");

        JList list = new JList(Mylist);//выделение памяти под лист(города)
        check = new JComboBox(Cards);//выделение памяти подвыпадающий список
        JLabel fio = new JLabel("Full name: ");//т.п
        JLabel sex = new JLabel("Sex: ");
        JLabel city = new JLabel("City: ");
        JLabel model = new JLabel("Type of card: ");
        JLabel additionally = new JLabel("Additionally:");
        textfio = new JTextField();
        inf = new JTextField();


        setLayout(null);
        getContentPane().add(send);
        //установка размера кнопок, списков,полей и т.д + их расположение
        send.setSize(100, 50);
        send.setLocation(540, 600);

        fio.setSize(250, 30);
        fio.setLocation(50, 100);

        textfio.setSize(500, 30);
        textfio.setLocation(200, 100);

        sex.setLocation(50, 175);
        sex.setSize(150, 30);
        sex1.setLocation(200, 167);
        sex1.setSize(100, 50);
        sex2.setLocation(400, 167);
        sex2.setSize(100, 50);

        city.setSize(150, 30);
        city.setLocation(50, 250);

        list.setSize(150, 110);
        list.setLocation(200, 250);

        model.setSize(150, 30);
        model.setLocation(50, 400);
        check.setSize(100, 30);
        check.setLocation(200, 400);

        additionally.setLocation(50, 500);
        additionally.setSize(150, 30);
        inf.setSize(300, 50);
        inf.setLocation(200, 500);

        //добавление кнопок в окно
        add(sex);
        add(sex1);
        add(sex2);
        add(city);
        add(list);
        add(fio);
        add(textfio);
        add(model);
        add(check);
        add(additionally);
        add(inf);

        //реакция на действия пользователя
        send.addActionListener(new AddActionListener());
        sex1.addActionListener(new FlagActionListener());
        sex2.addActionListener(new FlagActionListener());
        list.addListSelectionListener(new listSelectionListener());
        check.addActionListener(new BoxActionListener());


        //реакция на клик мыши
        list.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                //если в списке(ГОРОДА) я кликаю на один из элементов 1 раз, то я получаю его значение и закидываю в отдельную переменную
                if (e.getClickCount()==1) {
                    // Получение элемента
                    int selected = list.locationToIndex(e.getPoint());
                    chosen1=Mylist[selected];
                }
            }
        });
    }


    //для выпадающего списка, если я кликаю на элемент, то я его получаю, и перевожу в тип СТРИНГ
    public class BoxActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource()==check) {
                box = check.getSelectedItem();
                card=box.toString();
            }
        }
    }



    //работа со списком
    class listSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            // Выделенная строка
            int selected = ((JList<?>) e.getSource()).
                    getSelectedIndex();
        }
    }

    // для определния на какой пол я нажал
    public class FlagActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            k = 0;
            if (e.getSource() == sex1) {
                k++;


            } if (e.getSource() == sex2) {
                k--;

            }
        }
    }



    public class AddActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {//пытаюсь открыть файл, если его нет, то создаю
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter out = new FileWriter(file, true);//для записи в файл

                try {
                    String text1 = textfio.getText();//получаю введенное фио
                    String text2 = inf.getText();//получаю введенную доп инфу
                    c++;
                    //записываю в файл
                    out.write("FIO: " + text1 + "\n");
                    out.write("Sex: " + (k == 1 ? "Female" : "Male") + "\n");
                    out.write("Additional information: "+text2+ "\n");
                    out.write("City: "+chosen1+ "\n");
                    out.write("Type of card: "+card+"\n");

                    out.write("---------------------------------------------------");
                } finally {
                    //если все ок прошло, то я закрываю файл, и сбрасываю все значения
                    out.close();
                    textfio.setText(null);
                    inf.setText(null);
                    k=0;
                    chosen1=null;
                    box=null;
                }
            } catch (IOException err) {
                throw new RuntimeException(err);
            }
        }
    }
}


