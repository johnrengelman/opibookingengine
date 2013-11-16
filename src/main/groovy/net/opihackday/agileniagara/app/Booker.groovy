package net.opihackday.agileniagara.app

import org.springframework.context.support.ClassPathXmlApplicationContext

class Booker {



    public static void main(String[] args) {
//        new AnnotationConfigApplicationContext('net.opihackday.agileniagara')
        new ClassPathXmlApplicationContext('applicationContext.xml')
    }
}
