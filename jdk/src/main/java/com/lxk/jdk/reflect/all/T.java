package com.lxk.jdk.reflect.all;

import com.lxk.bean.reflect.Computer;
import com.lxk.bean.reflect.ComputerRepository;
import com.lxk.bean.reflect.Phone;
import com.lxk.bean.reflect.PhoneRepository;
import org.junit.Test;

/**
 * @author LiXuekai on 2021/10/28
 */
public class T {
    private final PhoneRepository phoneRepository = new PhoneRepository();
    private final ComputerRepository computerRepository = new ComputerRepository();

    @Test
    public void t() {

        Iterable<Phone> phones = phoneRepository.findAll();
        phones = phoneRepository.findAll(Phone.class);
        System.out.println();

        Iterable<Computer> computers = computerRepository.findAll();
        computers = computerRepository.findAll(Computer.class);
        System.out.println();
    }

}
