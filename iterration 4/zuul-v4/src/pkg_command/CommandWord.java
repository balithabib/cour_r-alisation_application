package src.pkg_command;

/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public enum CommandWord
{
    /* A value for each command word along with its
     * corresponding user interface string.
     */

    GO("go"), QUIT("quit"), HELP("help"), LOOK("look"), EAT("eat"), BACK("back"), TEST("test"), TAKE("take"), DROP("drop"), ITEMS("items"),  DECLANCHER("declancher"), CHARGER("charger"), SAVE("save"), LOAD("load"), UNKNOWN("?");
    
    /* The command string.
     */
    private String commandString;
    
    /**
     * Initializes with the corresponding command word.
     * @param commandString
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * Gets the command word as a string.
     * @return commandString
     */
    public String toString()
    {
        return commandString;
    }
}
