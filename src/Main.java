import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Console.console();
	}
}

class FiniteAutomaton {
	private Set<State> states; // Each state is given a number
	private Set<Letter> alphabet; // The alphabet is composed of letters
	// During a transition, I am in a state, I am given a letter, I must know which states it need to go from there.
	// Therefore, I check this list, select the state I am on, then select the letter I got to find where I need to go
	private Set<Map<String,Set<Map<Character,String>>>> transition;

	public FiniteAutomaton (Set<State> states, Set<Letter> alphabet, Set<Map<String,Set<Map<Character,String>>>> transition) {
		this.states = states;
		this.alphabet = alphabet;
		this.transition = transition;
	}
}

class State {
	private String name;

	public State(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public String toString() {
		return name();
	}
}

class StatesPrinter {
	public static String print(Set<State> states) {
		String result = "[";
		Iterator<State> statesIterator = states.iterator();
		if(statesIterator.hasNext()) result = result + statesIterator.next().name();
		while(statesIterator.hasNext()) {
			result = result + ", " + statesIterator.next().name();
		}
		result = result + "]";
		return result;
	}
}

class Letter {
	public char name;

	public Letter(char name){
		this.name = name;
	}
}

class Console {
	public static void console() {
		System.out.println("""
			--------
			Welcome to the finite automaton emulator !
			I will ask, in order 
			1) The name of the states
			2) The letters of the alphabet
			3) The transitions
			""");
			Scanner input = new Scanner(System.in);
			String currentStringInput;
			Set<State> states = new HashSet<State>();

			do {
				System.out.print("State name : ");
				currentStringInput = input.nextLine();
				if(currentStringInput == "") System.out.println("You need to have at least one state");
			} while (currentStringInput == "");

			states.add(new State(currentStringInput));

			do {
				System.out.println("Current states : " + StatesPrinter.print(states));
				System.out.print("State name : ");
				currentStringInput = input.nextLine();
				if(currentStringInput != "") states.add(new State(currentStringInput));
			} while(currentStringInput != "");
			
			System.out.println("The states will be : " + StatesPrinter.print(states));
			input.close();
	}
}


