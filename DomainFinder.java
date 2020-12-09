/*
 File name: DomainFinder.java -- to be used with ProteinDomain.java
 Program author: Analia Treviño-Flitton


General Flow:
* All functions are in ProteinDomain class
   - Read in file with ProReader
   - Uses regex generator Regexer to format search parameters that gets passed to DomainFinder
   -  DomainFinder searches for header and seq then for protein domain for each sequence entry
        -- Prints results in two formats if found domain found through Formatter
*/


import java.util.Scanner;



public class DomainFinder {

    public static void main(String[] args) {

        // Initialize
        Scanner input = new Scanner(System.in);
        ProteinDomain pd = new ProteinDomain();


        // Get file path
        System.out.println("Please enter the path to the protein sequence file:");
        String FilePath = (input.nextLine());


        // Domain Selection
        System.out.println("Please select a protein domain to search for:");
        System.out.println("[1] Zinc Finger\n[2] MARCKS Family \n[3] Leucine Zipper");
        int domain = input.nextInt();


        // Call Reader & Finder
        String fileContent = pd.ProReader(FilePath);
        pd.DomainFinder(fileContent, domain);



    }
}