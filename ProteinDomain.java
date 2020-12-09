/*
 File name: ProteinDomain.java  -- to be used with main (DomainFinder.java)
 Program author: Analia Treviño-Flitton
 */




import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.*;



/* Class contains the following Methods in this order :
    - ProReader: Reads in the protein sequence file and returns a string of the file's contents

    - Regexer: Generates a regex Matcher to capture the protein entry, title line, and protein sequences

    - DomainFinder: Sets the protein domain regex sequence, calls the Regexer for all the matchers
                    and searches each protein sequence one by one for the protein domain. If the
                    domain is present, the formatter is called.

    - Formatter: Where the final printing occurs if the protein domain is present
        -- Part 1: Prints the title line, the protein domain sequence, the position it is located and the entire
                   sequence

        -- Part 2: Prints the same as part one with the addition of stars underlining the protein domain in the sequence
 */


public class ProteinDomain {



    public String ProReader(String FilePath) {
        String fileContent = "";

        // Read the file line by line & catch exceptions
        try {

            // Initialize
            BufferedReader read = new BufferedReader(new FileReader(FilePath));
            StringBuilder buildString = new StringBuilder();
            String fileLine;


            // While there is a line in the file, read & add to string builder
            while (( fileLine = read.readLine()) != null) {
                buildString.append( fileLine );
            }
            read.close();


            // Send builder to string for return
            fileContent = buildString.toString();


        } catch (FileNotFoundException e) {
            System.out.println("Unable to locate file");

        } catch (IOException ioException) {
            System.out.println("Please retry with a readable file-type");
        }

        return fileContent;
    }



    public Matcher Regexer(String regexPattern, String searchString) {

        // Regex matcher generator to capture the protein entry, title line, and protein sequences

        Pattern pattern = Pattern.compile( regexPattern );
        return pattern.matcher( searchString );
    }




    public void DomainFinder(String fileContent, int domainSelect ) {
        String domainRegex = "";
        String domainName = "";
        String anyPro = "ABCDEFGHIKLMNPQRSTVWYZ";


        // Set regex & name for protein domain selected
        switch (domainSelect) {
            case 1:
                domainName = "Zinc Finger";
                domainRegex = "C[" + anyPro + "]{2}C[" + anyPro + "]{17}C[" + anyPro + "]{2}C";
                break;
            case 2:
                domainName = "MARCKS Family";
                domainRegex = "GQENGHV[KR]";
                break;
            case 3:
                domainName = "Leucine Zipper";
                domainRegex = "L[" + anyPro + "]{6}L[" + anyPro + "]{6}L[" + anyPro + "]{6}L";
                break;
        }



        // Regex Patterns for protein entry, title lines, and protein seq
        String entryRegex = "(>[\\w\\|\\s.\\[\\]]+)";

        String headerRegex = "(>[\\w\\|\\d.\\s\\w.\\-\\d]+\\[\\w+.+\\])";

        String seqRegex = "]([" + anyPro + "]+)";



        // Protein Entry Regex - Search file string for protein entry
        Matcher entryMatch = Regexer(entryRegex, fileContent);

        // For each entry- Find header, protein seq, & protein domain
        while (entryMatch.find()) {
            String entry = (entryMatch.group());



            // Header Regex
            Matcher headerMatch = Regexer(headerRegex, entry);

            // Capture the header from the entry
            while (headerMatch.find()) {
                String header = headerMatch.group();



                // Protein Sequence Regex
                Matcher proMatch = Regexer(seqRegex, entry);

                // Capture the protein sequence from entry
                while (proMatch.find()) {
                    String proSeq = proMatch.group();
                    proSeq = proSeq.substring(1);



                    // Domain Regex
                    Matcher domainMatch = Regexer(domainRegex, proSeq);

                    // If the domain is present, store & call formatter
                    if (domainMatch.find()) {
                        String domainSeq = domainMatch.group();
                        int domainStart = domainMatch.start();
                        int domainEnd = domainMatch.end();


                        // Call the output Formatter
                        Formatter(header, domainName, domainSeq, domainStart, domainEnd, proSeq);

                    }
                }
            }
        }
        System.out.println("Written by: Analia Treviño-Flitton");


    }




    public void Formatter(String header, String domainName,String domainSeq, int domainStart, int domainEnd, String proSeq ) {

        // Part 1 Formatting

        // Initialize
        StringBuilder formatBuilder = new StringBuilder();
        formatBuilder.append( proSeq );


        // Set protein seq lines to 75 char & save to string
        for (int x = 74; (x <= proSeq.length()) ; x= x+75) {
            formatBuilder.insert(x, "\n");
        }
        String proSeqForm = formatBuilder.toString();



        // Build P1 format & print
        formatBuilder.setLength(0);
        formatBuilder.append( header ).append("\n").append( domainName ).append(" site: ").append( domainSeq );
        formatBuilder.append("\nLocated at: ").append( domainStart ).append(" - ").append( domainEnd );
        formatBuilder.append("\n").append(proSeqForm);

        System.out.println("\nPart 1:\n" + formatBuilder );





        // Part 2 Formatting

        // Initialize
        StringBuilder starString = new StringBuilder();
        StringBuilder p2Format = new StringBuilder();
        String star = "*";


        // Line spacing of stars
        int lineStart = domainStart/75;
        int lineEnd = domainEnd/75;
        int lineBreak = ((lineEnd+1)*75);
        int posStart = domainStart+75+ lineStart;


        // Build protein seq, add extra spaces for star additions
        starString.append( proSeq );
        starString.append((" ").repeat(75*2));


        // Set protein seq lines to 75 char
        for ( int x = 74; (x <= starString.length()) ; x= x+75 ) {
            starString.insert(x, "\n");
        }


        // Insert line break & add stars
        starString.insert( lineBreak,(" ").repeat( 75-domainSeq.length() ) + "\n");
        starString.insert( posStart, star.repeat( domainSeq.length() ) );
        String proFinal = starString.toString();


        // Build P2 format & print
        p2Format.append( header ).append("\n").append( domainName ).append(" site: ").append( domainSeq );
        p2Format.append("\nLocated at: ").append( domainStart ).append(" - ").append( domainEnd );
        p2Format.append("\n").append(proFinal);
        System.out.println("\n\nPart 2:\n" + p2Format);

    }


}





