package ProjectJDBC;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class ShowResult {
	
	private static int printLine;
	private static int totalLines;
	private static int totalColumn;
	private static int attributeNumber;
    private static ResultSet resultQuery;
    private static String autoPrintString;
    private static HashMap<Integer, Integer> maxLength;
    private static HashMap<Integer, String> attributeDictionary;
    private static HashMap<Integer, String> nameAttributeDictionary;
    
	public static void setResultQueryExecute(String resultQuery) throws SQLException {
		ShowResult.resultQuery = DB_Connection.getStatement().executeQuery(resultQuery);
	}
	
	public static int setResultQueryUpdate(String resultQuery) throws SQLException {
		return DB_Connection.getStatement().executeUpdate(resultQuery);
	}

	public static ResultSet getResultQuery() {
		return resultQuery;
	}
	
	public static int getPrintLine() {
		return printLine;
	}

	public static void setPrintLine(int printLine) {
		ShowResult.printLine = printLine;
	}

	public static String getAutoPrintString() {
		return autoPrintString;
	}

	public static void setAutoPrintString(String autoPrintString) {
		ShowResult.autoPrintString = autoPrintString;
	}

	public static int getTotalLines() {
		return totalLines;
	}

	public static void setTotalLines(int totalLines) {
		ShowResult.totalLines = totalLines;
	}

	public static int getTotalColumn() {
		return totalColumn;
	}

	public static void setTotalColumn(int totalColumn) {
		ShowResult.totalColumn = totalColumn;
	}
	
	public static int getAttributeNumber() {
		return attributeNumber;
	}

	public static void setAttributeNumber(int attributeNumber) {
		ShowResult.attributeNumber = attributeNumber;
	}

	public static HashMap<Integer, String> getNameAttributeDictionary() {
		return nameAttributeDictionary;
	}

	public static void setMaxLength(HashMap<Integer, Integer> maxLength) {
		ShowResult.maxLength = maxLength;
	}

	public static void setAttributeDictionary(HashMap<Integer, String> attributeDictionary) {
		ShowResult.attributeDictionary = attributeDictionary;
	}

	public static void setNameAttributeDictionary(HashMap<Integer, String> nameAttributeDictionary) {
		ShowResult.nameAttributeDictionary = nameAttributeDictionary;
	}

	public static HashMap<Integer, String> getAttributeDictionary() {
		return attributeDictionary;
	}

	public ShowResult(boolean isQueryUpdate, boolean showInformation) {
		try {
			setMaxLength(new HashMap<>()); //Reset maxLenght hash map
			setAttributeDictionary(new HashMap<>()); //Reset attributeDictionary hash map
			setNameAttributeDictionary(new HashMap<>()); //Reset nameattributeDictionary hash map
			if (isQueryUpdate) {
				int feedbackChanges = setResultQueryUpdate(App.getSqlQuery());
				System.out.println("\n\nExecuting the following query:\n" + App.getSqlQuery());
				System.out.println(" --- Rows affected: " + feedbackChanges + " ---\n");
			} else {
				setResultQueryExecute(App.getSqlQuery());
				if (!getResultQuery().next() || getResultQuery().wasNull()) {
					if (showInformation) {
						System.err.println("\nNO RESULTS FOUND for --> " + App.getSqlQuery());
					}
		        } else {
		        	if (showInformation) {
						System.out.println("\n\nExecuting the following query:\n" + App.getSqlQuery());
					}
		        	System.out.println();
		        	ResultSetMetaData rsmd = getResultQuery().getMetaData();
		        	
		        	//Store all values on hash table
		        	setTotalLines(1); //Reset totalLines
		        	setTotalColumn(1); //Reset totalColumn
		        	do { //Database lines
		        		setAttributeNumber(1); //Reset attribute number
		        		for(int currentColumn = 1; currentColumn <= rsmd.getColumnCount(); currentColumn++) { //Database columns
		        			if (getTotalLines() == 1) { //Execute only on first line
		        				nameAttributeDictionary.put(getTotalColumn(), rsmd.getColumnName(currentColumn)); //Add database result to HashMap
		        			}
		        			if (getResultQuery().getString(currentColumn) == null) { //If database returns null
		        				attributeDictionary.put(getTotalColumn(), "NULL"); //Add NULL to HashMap
		        			} else {
		        				attributeDictionary.put(getTotalColumn(), getResultQuery().getString(currentColumn)); //Add database result to HashMap
		        			}
		        			try { //Hash table have values
		        				if (rsmd.getColumnName(currentColumn).length() > maxLength.get(currentColumn)) {
		        					maxLength.put(currentColumn, rsmd.getColumnName(currentColumn).length()); //Add maximum length element
		        				}
		        			} catch (NullPointerException e) { //HashMap does not have values
		        				maxLength.put(currentColumn, rsmd.getColumnName(currentColumn).length()); //Add maximum length element
		        			} finally { //Execute always
		        				try {
		        					if (getResultQuery().getString(currentColumn).length() > rsmd.getColumnName(currentColumn).length()) {
			        					maxLength.put(currentColumn, getResultQuery().getString(currentColumn).length()); //Add maximum length element
			        				}
								} catch (NullPointerException e2) {  //If table return a null value
									if ("NULL".length() > rsmd.getColumnName(currentColumn).length()) {
			        					maxLength.put(currentColumn, "NULL".length()); //Add NULL length to HashMap
			        				}
								}
		        			}
		        			setTotalColumn(getTotalColumn() + 1);
		        			setAttributeNumber(getAttributeNumber() + 1);
		        		}
		        		setTotalLines(getTotalLines() + 1);
		        	} while(getResultQuery().next()); //getResultQuery().next() must be checked after first line
		        	
		        	//Visual print
		        	for(int currentLine = 0, index = 0; currentLine <= getTotalLines() + 2; currentLine++) { //Print line
		        		if (currentLine > 3) {
		        			index += getAttributeNumber() - 1; //Only increase when is time to write values since second time
		        		}
		        		for(int currentColumn = 1; currentColumn < getAttributeNumber(); currentColumn++) { //Print column
		        			System.out.print("|"); //Separator bar
		        			do {
		        				if (getAutoPrintString() == null) {
		        					if (currentLine == 0 || currentLine == 2 || currentLine == getTotalLines() + 2) { //First and last lines
			        					setAutoPrintString("-");
			        					setPrintLine(-1); //Add two more lines
			        				} else if (currentLine == 1) {
			        					setAutoPrintString(" ");
				        				System.out.print(" " + nameAttributeDictionary.get(currentColumn));
				        				setPrintLine(nameAttributeDictionary.get(currentColumn).length()); //String size
				        			} else {
				        				setAutoPrintString(" ");
				        				System.out.print(" " + attributeDictionary.get(index + currentColumn));
				        				setPrintLine(attributeDictionary.get(index + currentColumn).length()); //String size
									}
		        				}
		        				System.out.print(getAutoPrintString());
		        				setPrintLine(getPrintLine() + 1);
							} while (getPrintLine() <= maxLength.get(currentColumn));
		        			setAutoPrintString(null);
		        		}
		        		System.out.println("|");  //End bar
		        	}
		        }
			}
		} catch (SQLException e) {
			System.err.println("Invalid query, please correct your query!");
		} 
	}
}
