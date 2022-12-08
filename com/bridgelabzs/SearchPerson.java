package com.bridgelabzs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class SearchPerson {
    public AddressBook addressBook;
    Scanner scannerObject = new Scanner(System.in);
    Map<String,AddressBook> addressBookDirectory = new HashMap<>();
    public void operationDirectory() {
        boolean moreChanges = true;
        do {
            System.out.println("\nChoose the operation on the Directory you want to perform");
            System.out.println("1.Add an Address Book\n2.Edit Existing Address Book\n3.Search Person By City\n4.Search Person By State\n5.Display Address book Directory\n6.Exit Address book System");

            switch (scannerObject.nextInt()) {
                case 1:
                    addAddressBook();
                    break;
                case 2:
                    editAddressBook();
                    break;
                case 3:
                    searchByCity();
                    break;
                case 4:
                    searchByState();
                    break;
                case 5:
                    displayDirectoryContents();
                    AddressBook addressBook1= new AddressBook();
                    addressBook1.displayContents();
                    break;
                case 6:
                    moreChanges = false;
                    System.out.println("Exiting Address Book Directory !");
            }
        } while (moreChanges);
    }
    public void addAddressBook() {
        System.out.println("Enter the name of the Address Book you want to add");
        String bookNameToAdd = scannerObject.next();
        if(addressBookDirectory.containsKey(bookNameToAdd)) {
            System.out.println("Book Name Already Exists");
            return;
        }
        AddressBook addressBook = new AddressBook();
        addressBook.setAddressBookName(bookNameToAdd);
        addressBookDirectory.put(bookNameToAdd, addressBook);
        addressBook.operation();
    }
    public void editAddressBook() {
        System.out.println("Enter the Name of the Address Book which you want to edit:");
        String addressBookToEdit = scannerObject.next();

        if(addressBookDirectory.containsKey(addressBookToEdit)) {
            addressBook = addressBookDirectory.get(addressBookToEdit);
            addressBook.operation();
        }
        else {
            System.out.println("Book Does Not Exist");
        }
    }
    public void searchByCity() {
        System.out.println("Enter the name of the City where the Person resides : ");
        String cityName = scannerObject.next();
        System.out.println("Enter the name of the Person : ");
        String personName = scannerObject.next();

        for(AddressBook addressBook : addressBookDirectory.values()) {
            ArrayList<ContactPerson> contactList = addressBook.getContact();
            contactList.stream()
                    .filter(person -> person.getFirstName().equals(personName) && person.getAddress().getCity().equals(cityName))
                    .forEach(person -> System.out.println(person));
        }
    }
    public void searchByState() {
        System.out.println("Enter the name of the State where the Person resides : ");
        String stateName = scannerObject.next();
        System.out.println("Enter the name of the Person : ");
        String personName = scannerObject.next();

        for(AddressBook addressBook : addressBookDirectory.values()) {
            ArrayList<ContactPerson> contactList = ((AddressBook) addressBook).getContact();
            contactList.stream()
                    .filter(person -> person.getFirstName().equals(personName) && person.getAddress().getState().equals(stateName))
                    .forEach(person -> System.out.println(person));
        }
    }
    public void displayDirectoryContents() {
        System.out.println("----- Contents of the Address Book Directory-----");
        for (String eachBookName : addressBookDirectory.keySet()) {

            System.out.println(eachBookName);
        }
        System.out.println("-----------------------------------------");
    }
}
