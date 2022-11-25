package com.bridgelabzs;

public void edit() {
    System.out.println("Enter your First name:");
    String fname = s.next();

    Iterator<Person> iterator = list.listIterator();

    while (iterator.hasNext()) {
        Person person = iterator.next();

        if (fname.equals(person.getFname())) {
            Address address = person.getAddress();
            System.out.println("Choose field you want to add:");
            System.out
                    .println("1.Last Name\t2.Phone Number\t3.City\t4.Zip\t5. State");
            switch (s.nextInt()) {
            case 1:
                System.out.println("Re-Correct your lastname");
                person.setLname(s.next());
                break;
            case 2:
                System.out.println("Re-Correct your Phone Number");
                person.setPhonenumber(s.nextLong());
                break;
            case 3:
                System.out.println("Re-Correct your City");
                address.setCity(s.next());
                break;
            case 4:
                System.out.println("Re-Correct your Zip");
                address.setZip(s.nextLong());
                break;
            case 5:
                System.out.println("Re-Correct your State");
                address.setState(s.next());
                break;
            }

        }
    }

}
