package rs.ac.bg.etf.pp1.sm130075d;
import java_cup.runtime.*;

import java.io.FileWriter;
import java.io.IOException;

class TestLexer {
    public static void main(String []argv) {
	if (argv.length == 0) {
	    System.out.println("Usage : java Lexer <inputfile> [-f]");
	}
	else {
		Logger logger;
		boolean writeToFile = false;
		FileWriter oFile = null;
		if (argv.length >= 2) {
			if(argv[1].equals("-f")) {
				writeToFile = true;
			}

		}

		logger = new Logger(writeToFile, "TestLexer");

		Lexer scanner = null;
		try {
		    scanner = new Lexer( new java.io.FileReader(argv[0]) );
		    while (true) {
				Symbol token = scanner.next_token();
				if (token.sym == sym.EOF) break;
				if (token != null) {
					if(token.sym == sym.error)
						logger.error("" + token.left + ":" + token.right + " Token: " + token.value.toString() + "nije preopoznat");
					else
						logger.log(token.value.toString());
				}
			}
		}

		catch (java.io.FileNotFoundException e) {
		    System.out.println("File not found : \""+argv[0]+"\"");
		}
		catch (java.io.IOException e) {
		    System.out.println("IO error scanning file \""+argv[0]+"\"");
		    System.out.println(e);
		}
		catch (Exception e) {
		    System.out.println("Unexpected exception:");
		    e.printStackTrace();
		}

	}
    }
}
