# Protein-Domain-Finder

*Currently only able to search for the following protein domains:
Zinc Finger, MARCKS Family & Leucine Zipper*

Reads in a text file and searches for the protein domain the user selects.
If it is present in an entry it will return two parts for each entry.
* Part 1: Returns the title line of the sequence, the protein domain sequence, the position it is located, and the entire entry sequence

* Part 2: Prints the same as part one with the addition of stars underlining the protein domain in the sequence print out



<dl>
  <dt> Required files for program
  </dt>

  <dd>

---
DomainFinder.java
* Where main is located, prompts the user for the path to the file and to select the protein domain to search for
---

ProteinDomain.java
* The class file with the ProReader, Regexer, DomainFinder, and Formatter functions

---

zincFinger.txt
* Text file with various FASTA formatted sequences. Zinc Finger and Leucine Zipper present in file
---
</dt>
