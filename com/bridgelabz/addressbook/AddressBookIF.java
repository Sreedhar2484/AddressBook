package com.bridgelabz.addressbook;

import java.util.List;

public interface AddressBookIF {
    void operation();

    void addContact();

    void deletePerson();

    void displayContents();

    void editPerson();

    void writeToAddressBookFile();

    List<String> readDataFromFile();
}