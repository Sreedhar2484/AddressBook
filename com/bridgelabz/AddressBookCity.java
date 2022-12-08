package com.bridgelabz;

import java.util.Scanner;

public class AddressBookCity{
	public static Scanner scannerObject = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("---------- Welcome To Address Book Program!! ----------");
		
		AddressBookDirectory addressBookDirectory = new AddressBookDirectory();
        addressBookDirectory.operationDirectory();
        AddressBook addressBook = new AddressBook();
        addressBook.operation();
	}
	
}