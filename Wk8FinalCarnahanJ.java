package week8Final;

/*
 * Joshua Carnahan | CMIS 242 | Assignment 4
 * This is a Media Rental program that has the user add multiple different
 * types of media (Book, CD, DVD) to the array and can choose to lookup a specific item,
 * look up a group of a specific type of media (Book, CD, DVD), delete an item,
 * modify, an item, rent an item, or display everything. then lastly an option
 * to exit the program.
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Wk8FinalCarnahanJ {

    // parent class Media
    public static abstract class Media {

        // attributes for media

        // constants
        final int ID;
        final String MEDIAMODEL;
        public static final DecimalFormat DF = new DecimalFormat("0.00");

        public boolean rentStatus;
        public String title;
        public int yearPub;
        public double flatFee = 1.50;

        // constructor
        public Media(int id, boolean rentStatus, String mediaModel, String title, int yearPub) {
            this.ID = id;
            this.rentStatus = rentStatus;
            this.MEDIAMODEL = mediaModel;
            this.title = title;
            this.yearPub = yearPub;
        }

        public void setYearPub(int yearPub) {
            this.yearPub = yearPub;
        }

        public void setRent(boolean rentStatus) {
            this.rentStatus = rentStatus;
        }

        // get methods
        public int getID() {
            return ID;
        }

        public String getTitle() {
            return title;
        }

        public int getYearPub() {
            return yearPub;
        }

        public boolean getStatus() {
            return rentStatus;
        }

        public String getModel() {
            return MEDIAMODEL;
        }

        // method to display values to console
        public String toString() {
            return "ID: " + getID() + "Media Model: "+ getModel() +" rented? " + getStatus() +  " title: " + getTitle() + " year published: " + getYearPub();
        }



    } // end Media

    // EBook child class
    public static class EBook extends Media {

        // EBook attribute
        public int chapters;

        // constructor
        public EBook(int id, boolean rentStatus,String mediaModel, String title, int yearPub, int chapters) {
            super(id, rentStatus, mediaModel, title, yearPub);
            this.chapters = chapters;
        }

        // get method
        public int getChapters() {
            return chapters;
        }

        // set method
        public void setChapters(int chapters) {
            this.chapters = chapters;
        }

        // calculate fee for EBook
        public double rentFee(int chapters) {
            double fee = (chapters * 0.10 + flatFee);

            if (yearPub > 2015) {
                fee += 1.00;
            }
            return fee;
        }

        // method to display values to console
        @Override
        public String toString() {
            return "EBook ID: " + getID() + ". title: " + getTitle() + ". year published: " + getYearPub() + ". with " + getChapters() + " chapters. " + " costs: $" + DF.format(rentFee(getChapters())) + ". - Rented: " + getStatus();
        }


    } // end EBook

    // MusicCD child class
    public static class MusicCD extends Media {

        // musicCD attribute
        public int minutes;

        // constructor
        public MusicCD(int id, boolean rentStatus, String mediaModel, String title, int yearPub, int minutes) {
            super(id, rentStatus, mediaModel, title, yearPub);
            this.minutes = minutes;
        }

        // get method
        public int getMinutes() {
            return minutes;
        }

        // set method
        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        // calculate fee for MusicCD
        public double rentFee(int minutes) {
            double fee = (minutes * 0.045 + flatFee);
            if (yearPub > 2014) {
                fee += 2.00;
            }
            return fee;
        }

        // method to display values to console
        @Override
        public String toString() {
            return "MusicCD ID: " + getID() + ". title: " + getTitle() + ". year published: " + getYearPub() + ". with " + getMinutes() + " minutes. " + " costs: $" + DF.format(rentFee(getMinutes())) + ". - Rented: " + getStatus();
        }

    } // end MusicCD

    // MovieDVD child class
    public static class MovieDVD extends Media {

        // MovieDVD attribute
        public static int megabytes = 1; // default is 1

        // constructor
        public MovieDVD(int id, boolean rentStatus, String mediaModel, String title, int yearPub, int megabytes) {
            super(id, rentStatus, mediaModel, title, yearPub);
            MovieDVD.megabytes = megabytes;
        }

        // get method
        public static int getMegabytes() {
            return megabytes;
        }

        // calculate fee for MovieDVD
        public double rentFee() {
            double fee = (3.25 + flatFee);

            if (yearPub > 2019) {
                fee -= flatFee;
                fee += 5.00; // if the year movie is published is after 2019 flatFee becomes 5.00 instead of 1.50
            }
            return fee;
        }


        // method to display values to console
        @Override
        public String toString() {
            return "MovieDVD ID: " + getID() + ". title: " + getTitle() + ". year published: " + getYearPub() + ". with " + getMegabytes() + " megabytes. " + " costs: $" + DF.format(rentFee()) + ". - Rented: " + getStatus();
        }
    } // end MovieDVD

    public static class Manager {

        private static final ArrayList<Media> inventoryList = new ArrayList<>();

        // validation methods
        public int validateID(String input) {
            Scanner idInp = new Scanner(System.in);
            int id = 0;
            do {
                System.out.println(input);
                if (idInp.hasNextInt()) {
                    id = idInp.nextInt();
                    if (String.valueOf(id).length() != 5) {
                        System.out.println("That is not a valid ID number.");
                        id = 0;
                    }
                } else {
                    System.out.println("That is not a valid ID number.");
                    idInp.next();
                }
            } while (id == 0);
            return id;
        }

        public void checkList() {
            // quick validation to check if there is anything in array list
            if (inventoryList.size() == 0) {
                System.out.println("\n No media in the system yet");
            }
        }

        // get media validation method
        public Media getMedia() {

            Media media = null;

            do {
                int id = validateID("What is the ID? (5 numbers)");
                for (Media inventoryList : inventoryList) {
                    if(inventoryList.getID() == id) {
                        media = inventoryList;
                    }
                }
                if (media == null) {
                    System.out.println("ID does not exist.");
                }
            } while (media == null);

            return media;
        }

        // add media method
        public void addMedia() {
            Scanner input = new Scanner(System.in);

            // declaring variables outside of input statements/validations
            boolean rentStatus;
            String mediaModel;
            int yearPub;

            int id = validateID("What is the ID? (5 numbers)");

            // process to ask user or 0 or 1 for true or false
            int rentCH;
            try {
                System.out.println("Is this product rented? (0: not rented, 1: rented)");
                rentCH = input.nextInt();
                if (rentCH == 0) {
                    rentStatus = false;
                } else if (rentCH == 1) {
                    rentStatus = true;
                } else {
                    System.out.println("Not a valid input");
                    return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Not a valid input");
                return;
            }

            input.nextLine(); // to avoid a line/input skip
            System.out.println("What is the title?");
            String title = input.nextLine();

            // validating that a number is used for year
            try {
                System.out.println("What year was it published?");
                yearPub = input.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Invalid input");
                return;
            }


            // ask for specific scope depending on media model input
            System.out.println("What form of media? (E for Ebook; C for CD; D for DVD)");
            mediaModel = input.next().toUpperCase();
            if (mediaModel.equalsIgnoreCase("E")) {
                System.out.println("How many Chapters are in the Ebook?");
                int chapters = input.nextInt();
                EBook EB = new EBook(id, rentStatus, mediaModel, title, yearPub, chapters); // create book objects
                inventoryList.add(EB); // add to array
                System.out.println("Successfully added EBook to the system");
            } else if (mediaModel.equalsIgnoreCase("C")) {
                System.out.println("How many minutes is the CD?");
                int minutes = input.nextInt();
                MusicCD CD = new MusicCD(id, rentStatus, mediaModel, title, yearPub, minutes); // create CD object
                inventoryList.add(CD); // add to array
                System.out.println("Successfully added MusicCD to the system");
            } else if (mediaModel.equalsIgnoreCase("D")) {
                int megabytes = MovieDVD.getMegabytes();
                MovieDVD DVD = new MovieDVD(id, rentStatus, mediaModel, title, yearPub, megabytes); // create DVD object
                inventoryList.add(DVD); // add to array
                System.out.println("Successfully added MovieDVD to the system");
            } else {
                System.out.println("Not a valid media model");
            }
        } // end addMedia

        //find media method
        public void findMedia() {

            // run validation
            checkList();

            Media media = getMedia();
            System.out.println(media);

        } // end findMedia

        // remove media method
        public void removeMedia() {

            // run validation
            checkList();

            Media media = getMedia();
            inventoryList.remove(media);
            System.out.println("Successfully removed from list");

        } // end removeMedia

        // rent media method
        public void rentMedia() {

            // run validation
            checkList();

            Media media = getMedia();

            // quick validation to make sure the item trying to be rented isn't already being rented
            if(media.getStatus()) {
                System.out.println(media.getTitle() + " is already rented");
            } else {
                media.setRent(true);
                System.out.println(media.getTitle() + " is now being rented");
            }

        } // end rentMedia

        // modify media method
        public void modifyMedia() {
            // run validation
            checkList();

            Scanner input = new Scanner(System.in);
            Media media = getMedia();

            if (media.getModel().equalsIgnoreCase("E")) {
                System.out.println("Do you want to change the number of chapters? (0: yes|1: no)");
                String ch = input.next();
                if (Objects.equals(ch, "0")) {
                    System.out.println("How many chapters is the book?");
                    int chapt = input.nextInt();
                    ((EBook) media).setChapters(chapt);
                    System.out.println("Book num of chapters successfully updated. The new rental fee is: ");
                }

                System.out.println("Do you want to update the Year Published? (0: yes|1: no)");
                ch = input.next();
                if (Objects.equals(ch, "0")) {
                    System.out.println("What year was the EBook published?");
                    int pub = input.nextInt();
                    media.setYearPub(pub);
                    System.out.println("Book published year successfully updated");

                }
                System.out.println("Updated Rental fee for the Book is: $" + ((EBook) media).rentFee(((EBook) media).getChapters()));
            }
            else if (media.getModel().equalsIgnoreCase("C")) {
                System.out.println("Do you want to change the length of the CD? (0: yes|1: no)");
                String ch = input.next();
                if (Objects.equals(ch, "0")) {
                    System.out.println("How many minutes is the CD?");
                    int min = input.nextInt();
                    ((MusicCD) media).setMinutes(min);
                    System.out.println("CD minutes successfully updated");
                }

                System.out.println("Do you want to update the Year Published? (0: yes|1: no)");
                ch = input.next();
                if (Objects.equals(ch, "0")) {
                    System.out.println("What year was the CD published?");
                    int pub = input.nextInt();
                    media.setYearPub(pub);
                    System.out.println("CD published year successfully updated");
                }
                System.out.println("Updated Rental fee for the CD is: $" + ((MusicCD) media).rentFee(((MusicCD) media).getMinutes()));
            }
            else if (media.getModel().equalsIgnoreCase("D")) {
                System.out.println("Do you want to update the Year Published? (0: yes|1: no)");
                String ch = input.next();
                if (Objects.equals(ch, "0")) {
                    System.out.println("What year was the DVD published?");
                    int pub = input.nextInt();
                    media.setYearPub(pub);
                    System.out.println("DVD published year successfully updated");
                }
                System.out.println("Updated Rental fee for the DVD is: $" + ((MovieDVD) media).rentFee());
            }

        } // end modifyMedia

        // method to display a specific item
        public void displayOne() {
            // run validation
            checkList();

            Media media = getMedia();
            System.out.println(media);

        } // end displayOne

        // display all of one type of media method
        public void displayType() {
            // run validation
            checkList();

            Scanner model = new Scanner(System.in);
            System.out.println("What form of media do you want to see? (E for Ebook; C for CD; D for DVD)");
            String medMod = model.next();

            // iterate inventory list
            for (Media inventoryMedia : inventoryList) {
                // iterate list for matching Media Model types
                if (inventoryMedia.getModel().equalsIgnoreCase(medMod)) {
                    System.out.println(inventoryMedia);
                }
            }

        } // end displayType

        // display all method
        public void displayAll() {
            // run validation
            checkList();

            // iterate inventory list
            for (Media inventoryMedia : inventoryList) {
                //print each item
                System.out.println(inventoryMedia);
            }

        } // end displayAll

        // method to display all when terminating the program
        public void closeProg() {
            checkList();

            System.out.println(); // for spacing

            // iterate inventory list
            for (Media inventoryMedia : inventoryList) {
                //print each item
                System.out.println(inventoryMedia);
            }

            System.out.println(); // for spacing
            System.out.println("Thank you for using the program. Goodbye!");

        } // end close prog

        // method to display the menu
        public static void displayMenu() {
            System.out.println("\n MENU");
            System.out.println("1: Add ");
            System.out.println("2: Find ");
            System.out.println("3: Remove ");
            System.out.println("4: Rent ");
            System.out.println("5: Modify ");
            System.out.println("6: Display One Media Object ");
            System.out.println("7: Display All Media of one Type ");
            System.out.println("8: Display Whole Library of Media Objects ");
            System.out.println("9: Exit program");
        } // end displayMenu

        // method to handle user's selection
        public void processChoice(int c) {
            switch (c) {
                case 1 -> addMedia();
                case 2 -> findMedia();
                case 3 -> removeMedia();
                case 4 -> rentMedia();
                case 5 -> modifyMedia();
                case 6 -> displayOne();
                case 7 -> displayType();
                case 8 -> displayAll();
                case 9 -> closeProg();
                default -> System.out.println("Invalid selection");
            }
        } // end processChoice
    } // end Manager

    // main
    public static void main(String[] args) {

        Scanner inp = new Scanner(System.in);

        // create instance of Media Manager
        Manager manager = new Manager();

        int selection;

        do {
            // run program until user closes
            Manager.displayMenu();

            System.out.print("\nEnter your selection: ");
            selection = inp.nextInt();

            manager.processChoice(selection);

        } while (selection != 9);

        inp.close();


    } // end main

} // end Wk8 Final

