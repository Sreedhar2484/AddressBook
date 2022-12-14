package com.bridgelabz.addressbook;
import com.bridgelabz.addressbook.AddressBookIF;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.io.Reader;

public class AddressBook implements AddressBookIF {
    Scanner scannerObject = new Scanner(System.in);
    public Map<String, ContactPerson> contactList = new HashMap<String,ContactPerson>();
    public static HashMap<String, ArrayList<ContactPerson>> personByCity  = new HashMap<String, ArrayList<ContactPerson>>();
    public static HashMap<String, ArrayList<ContactPerson>> personByState = new HashMap<String, ArrayList<ContactPerson>>();
    public String addressBookName;
    public boolean isPresent = false;

    public String getAddressBookName() {
        return addressBookName;
    }
    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }
    public ArrayList<ContactPerson> getContact() {
        return new ArrayList<ContactPerson>(contactList.values());
    }
    public void operation() {
        boolean moreChanges = true;
        do {
            System.out.println("\nChoose the operation you want to perform");
            System.out.println(
                    "1.Add To Address Book\n2.Edit Existing Entry\n3.Delete Contact\n4.Display Address book\n5.Display Sorted Address Book By Custom Criteria\n6.Write To File\n7.Read From File\n8.Exit Address book System");

            switch (scannerObject.nextInt()) {
                case 1:
                    addContact();
                    break;
                case 2:
                    editPerson();
                    break;
                case 3:
                    deletePerson();
                    break;
                case 4:
                    displayContents();
                    break;
                case 5:
                    System.out.println("What Criteria Do You Want Address Book To Be Sorted In ?");
                    System.out.println("1.FirstName\n2.City\n3.State\n4.Zip Code");
                    int sortingChoice = scannerObject.nextInt();
                    sortAddressBook(sortingChoice);
                    break;
                case 6:
                    writeToAddressBookFile();
                    System.out.println("Written To file");
                    break;
                case 7: readDataFromFile();
                    break;
                case 8:
                    moreChanges = false;
                    System.out.println("Exiting Address Book: "+this.getAddressBookName()+" !");
            }
        } while (moreChanges);
    }
    public void addContact() {
        ContactPerson person = new ContactPerson();
        Address address = new Address();

        System.out.println("Enter First Name: ");
        String firstName = scannerObject.next();

        contactList.entrySet().stream().forEach(entry -> {
            if(entry.getKey().equals(firstName.toLowerCase())) {
                System.out.println("Contact Already Exists");
                isPresent = true;
                return;
            }
        });
        if(isPresent == false) {
            System.out.println("Enter Last Name: ");
            String lastName = scannerObject.next();

            System.out.println("Enter Phone Number: ");
            long phoneNumber = scannerObject.nextLong();

            System.out.println("Enter Email: ");
            String email = scannerObject.next();

            System.out.println("Enter City: ");
            String city = scannerObject.next();

            System.out.println("Enter State: ");
            String state = scannerObject.next();

            System.out.println("Enter Zip Code: ");
            long zipCode = scannerObject.nextLong();

            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setPhoneNumber(phoneNumber);
            person.setEmail(email);
            address.setCity(city);
            address.setState(state);
            address.setZip(zipCode);
            person.setAddress(address);
            addPersonToCity(person);
            addPersonToState(person);
            contactList.put(firstName.toLowerCase(), person);
        }
    }
    public void addPersonToCity(ContactPerson contact) {
        if (personByCity.containsKey(contact.getAddress().getCity())) {
            personByCity.get(contact.getAddress().getCity()).add(contact);
        }
        else {
            ArrayList<ContactPerson> cityList = new ArrayList<ContactPerson>();
            cityList.add(contact);
            personByCity.put(contact.getAddress().getCity(), cityList);
        }
    }
    public void addPersonToState(ContactPerson contact) {
        if (personByState.containsKey(contact.getAddress().getState())) {
            personByState.get(contact.getAddress().getState()).add(contact);
        }
        else {
            ArrayList<ContactPerson> stateList = new ArrayList<ContactPerson>();
            stateList.add(contact);
            personByState.put(contact.getAddress().getState(), stateList);
        }
    }
    public void editPerson() {
        ContactPerson person = new ContactPerson();
        System.out.println("Enter the first name:");
        String firstName = scannerObject.next();

        if(contactList.containsKey(firstName)) {
            person = contactList.get(firstName);
            Address address = person.getAddress();
            System.out.println("\nChoose the attribute you want to change:");
            System.out.println("1.Last Name\n2.Phone Number\n3.Email\n4.City\n5.State\n6.ZipCode");
            int choice = scannerObject.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter the correct Last Name :");
                    String lastName = scannerObject.next();
                    person.setLastName(lastName);
                    break;
                case 2:
                    System.out.println("Enter the correct Phone Number :");
                    long phoneNumber = scannerObject.nextLong();
                    person.setPhoneNumber(phoneNumber);
                    break;
                case 3:
                    System.out.println("Enter the correct Email Address :");
                    String email = scannerObject.next();
                    person.setEmail(email);
                    break;
                case 4:
                    System.out.println("Enter the correct City :");
                    String city = scannerObject.next();
                    address.setCity(city);
                    break;
                case 5:
                    System.out.println("Enter the correct State :");
                    String state = scannerObject.next();
                    address.setState(state);
                    break;
                case 6:
                    System.out.println("Enter the correct ZipCode :");
                    long zip = scannerObject.nextLong();
                    address.setZip(zip);
                    break;
            }
        }
        else {
            System.out.println("Book Does Not Exist");
        }
    }
    public void deletePerson() {
        System.out.println("Enter the first name of the person to be deleted");
        String firstName = scannerObject.next();
        if(contactList.containsKey(firstName)) {
            contactList.remove(firstName);
            System.out.println("Successfully Deleted");
        }
        else {
            System.out.println("Contact Not Found!");
        }
    }
    public void displayContents() {
        System.out.println("----- Contents of the Address Book "+this.getAddressBookName()+" -----");
        for (String eachContact : contactList.keySet()) {
            ContactPerson person = contactList.get(eachContact);
            System.out.println(person);
        }
        System.out.println("-----------------------------------------");
    }
    public void printSortedList(List<ContactPerson> sortedContactList) {
        System.out.println("------ Sorted Address Book "+this.getAddressBookName()+" ------");
        Iterator iterator = sortedContactList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            System.out.println();
        }
        System.out.println("-----------------------------------------");
    }
    public void sortAddressBook(int sortingChoice) {
        List<ContactPerson> sortedContactList;
        switch(sortingChoice) {
            case 1: sortedContactList = contactList.values().stream()
                    .sorted((firstperson, secondperson) -> firstperson.getFirstName().compareTo(secondperson.getFirstName()))
                    .collect(Collectors.toList());
                printSortedList(sortedContactList);
                break;
            case 2: sortedContactList = contactList.values().stream()
                    .sorted((firstperson, secondperson) -> firstperson.getAddress().getCity().compareTo(secondperson.getAddress().getCity()))
                    .collect(Collectors.toList());
                printSortedList(sortedContactList);
                break;
            case 3: sortedContactList = contactList.values().stream()
                    .sorted((firstperson, secondperson) -> firstperson.getAddress().getState().compareTo(secondperson.getAddress().getState()))
                    .collect(Collectors.toList());
                printSortedList(sortedContactList);
                break;
            case 4: sortedContactList = contactList.values().stream()
                    .sorted((firstperson, secondperson) -> Long.valueOf(firstperson.getAddress().getZip()).compareTo(Long.valueOf(secondperson.getAddress().getZip())))
                    .collect(Collectors.toList());
                printSortedList(sortedContactList);
                break;
        }
    }
     public class OpenCSVReader{
        private static final String CSV_FILE = "./";
        public static void main(String args[], Object reader) throws IOException{
            try{
                Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE));

            }{
                CsvToBean<CSVUser> csvToBean = new csvToBeanBuilder(reader).withType(CSVUser.class).withIgnoreLeadingWhiteSpace(true).build();
                Iterator<csvUser> cavUserIterator = csvToBean.iterator();
                while(cavUserIterator.hasNext()){
                    CSVUser csvUser = csvUserIterator.next();
                    System.out.println("Name : "+ csvToBean.getName());
                     System.out.println("Email : "+ csvToBean.getEmail());
                      System.out.println("Country : "+ csvToBean.getCountry());
                       System.out.println("PhoneNo : "+ csvToBean.getPhoneNo());
                        System.out.println("======================");

                }
            }
        }
    }

    public class OpenCSVWriter{
        private static final String Object = "./";
        public static void main(String args[]) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
            try{
                Writer writer = Files.newBufferedWriter(paths.get(STRING_ARRAY));

            }{
                StatefulBeanToCsv<MyUser> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

                List<MyUser> myUsers = new ArrayList<>();
                myUsers.add(new MyUser("Sreedhar", "nsridhar@gmail.com", "+91 9828782782", "India"));
                myUsers.add(new MyUser("Srinr", "nsridha@gmail.com", "+91 9828782322", "India"));
                beanToCsv.write(myUsers);

            }
        }
    }

}
   
    
