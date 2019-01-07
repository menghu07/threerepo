package com.apeny.classiniit;

public class SuperClass {
     public SuperClass() {
         this.toOverride();
     }
     
     public void toOverride() {
 
     }
}
 
class SubClass extends SuperClass {
     private int x = 23;
     public SubClass() {
         x = 90;
     }
     public void toOverride() {
         System.out.println("this.x: " + this.x + "==" + "0");
    }
}