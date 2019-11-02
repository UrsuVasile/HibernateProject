package com.sda.java;

import Database.UserDao;
import Service.Service;

import java.util.LinkedList;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        Service service = new Service();
        service.action();

//        UserDao userDao = new UserDao();
//        String nume = "andrei";
//        String parola = null;
//        System.out.println(parola = service.generateHash("bubulici"+"hogea"));



        //System.out.println(userDao.findUserRol(nume, parola));
//        List<String> list = userDao.findUsername();
//
//        for(String s : list){
//            System.out.println(s);
//        }


    }
}
