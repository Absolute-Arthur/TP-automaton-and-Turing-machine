import java.util.HashMap;
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
	private Map<State,Map<Letter,State>> transitions;

	public FiniteAutomaton (Set<State> states, Set<Letter> alphabet, Map<State,Map<Letter,State>> transitions) {
		this.states = states;
		this.alphabet = alphabet;
		this.transitions = transitions;
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

	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof State))
			return false;
		State other = (State)o;
		return name.equals(other.name());
	}
}

class Letter {
	private char name;

	public Letter(char name){
		this.name = name;
	}
	
	public char name() {
		return name;
	}

	public String toString() {
		return String.valueOf(name());
	}

	public int hashCode() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Letter))
			return false;
			Letter other = (Letter)o;
		return name == other.name();
	}
}

class SetPrinter<T> {
	public static <T> String print(Set<T> Ts) {
		String result = "[";
		Iterator<T> TsIterator = Ts.iterator();
		if(TsIterator.hasNext()) result = result + TsIterator.next();
		while(TsIterator.hasNext()) {
			result = result + ", " + TsIterator.next();
		}
		result = result + "]";
		return result;
	}
}

class TransitionsPrinter {
	public static void print(Map<State,Map<Letter,State>> transitions) {
		for(State originState : transitions.keySet()) {
			for(Letter correspondingLetter : transitions.get(originState).keySet()) {
				System.out.println(originState + " --" + correspondingLetter + "-> " + transitions.get(originState).get(correspondingLetter));
			}
		}
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

		/* Begin states registration */
		System.out.println("Let's define the states");
		Set<State> states = new HashSet<State>();

		do {
			System.out.print("State name : ");
			currentStringInput = input.nextLine();
			if(currentStringInput == "") System.out.println("You need to have at least one state");
		} while (currentStringInput == "");

		states.add(new State(currentStringInput));

		do {
			System.out.println("Current states : " + SetPrinter.print(states));
			System.out.print("State name (return to end) : ");
			currentStringInput = input.nextLine();
			if(currentStringInput != "") states.add(new State(currentStringInput));
		} while(currentStringInput != "");
		
		System.out.println("The states will be : " + SetPrinter.print(states));
		System.out.println();
		/* End states registration */
		
		/* Begin alphabet registration */
		System.out.println("Let's define the alphabet");
		Set<Letter> alphabet = new HashSet<Letter>();

		do {
			System.out.print("Letter : ");
			currentStringInput = input.nextLine();
			if (currentStringInput == "") System.out.println("You need to have at least one character");
			else {
				if (currentStringInput.length() > 1) System.out.println("The first character will be used");
				alphabet.add(new Letter(currentStringInput.charAt(0)));
			}
		} while (currentStringInput == "");

		do {
			System.out.println("Current alphabet : " + SetPrinter.print(alphabet));
			System.out.print("Letter (return to end) : ");
			currentStringInput = input.nextLine();
			if(currentStringInput != "") {
				if (currentStringInput.length() > 1) System.out.println("The first character will be used");
				alphabet.add(new Letter(currentStringInput.charAt(0)));
			}
		} while(currentStringInput != "");
		
		System.out.println("The alphabet will be : " + SetPrinter.print(alphabet));
		System.out.println();
		/* End alphabet registration */

		/* Begin transition registration */
		System.out.println("Let's define the transitions");
		boolean returned;
		boolean repeat;
		State originState;
		State destinationState;
		Letter correspondingLetter;
		Map<State,Map<Letter,State>> transitions = new HashMap<State,Map<Letter,State>>();
		do {
			originState = null;
			destinationState = null;
			correspondingLetter = null;
			returned = false;
			repeat = false;
			do {
				System.out.println("Origin state (return to end) : ");
				currentStringInput = input.nextLine();
				if(currentStringInput == "") returned = true;
				else {
					if(!states.contains(new State(currentStringInput))) {
						System.out.println("The state does not exist");
						repeat = true;
					}
				}
			} while (repeat && !returned);
			if(!returned) {
				originState = new State(currentStringInput);

				repeat = false;
				do {
					System.out.println("Destination state : ");
					currentStringInput = input.nextLine();
					if(!states.contains(new State(currentStringInput))) {
						System.out.println("The state does not exist");
						repeat = true;
					}
				} while(repeat);
				destinationState = new State(currentStringInput);

				
				repeat = false;
				do {
					System.out.println("Corresponding letter : ");
					currentStringInput = input.nextLine();
					if(!alphabet.contains(new Letter(currentStringInput.charAt(0)))) {
						System.out.println("The letter is not in the alphabet");
						repeat = true;
					}
				} while(repeat);
				correspondingLetter = new Letter(currentStringInput.charAt(0));
			}
			if(!returned) {
				if(transitions.containsKey(originState)) {
					Map<Letter,State> existingMap = transitions.get(originState);
					if(existingMap.containsKey(correspondingLetter)) {
						System.out.println("The letter for this origin state is already set");
					} else {
						existingMap.put(correspondingLetter, destinationState);
						transitions.replace(originState, existingMap);
					}
				} else {
					Map<Letter,State> newMap = new HashMap<Letter,State>();
					newMap.put(correspondingLetter, destinationState);
					transitions.put(originState, newMap);
				}
			}
			System.out.println("The current transitions are : ");
			TransitionsPrinter.print(transitions);
		} while(!returned);
		/* End transition registration */
		input.close();

		new FiniteAutomaton(states, alphabet, transitions);
	}
}