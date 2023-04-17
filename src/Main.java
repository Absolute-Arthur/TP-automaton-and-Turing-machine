import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Console.console();
	}
}

class FiniteAutomaton {
	private List<State> states; // Each state is given a number
	private Set<Letter> alphabet; // The alphabet is composed of letters
	// During a transition, I am in a state, I am given a letter, I must know which states it need to go from there.
	// Therefore, I check this list, select the state I am on, then select the letter I got to find where I need to go
	private Map<State,Map<Letter,State>> transitions;

	public FiniteAutomaton (List<State> states, Set<Letter> alphabet, Map<State,Map<Letter,State>> transitions) {
		this.states = states;
		this.alphabet = alphabet;
		this.transitions = transitions;
	}
	
	public void print() {
		List<String> output = new LinkedList<String>();
		StringBuilder stateLine1 = new StringBuilder();
		StringBuilder stateLine2 = new StringBuilder();
		StringBuilder stateLine3 = new StringBuilder();

		for(State state : states) {
			stateLine1.append("┌" + "─".repeat(state.name().length()) + "┐ ");
			stateLine2.append("│" + state.name() + "│ ");
			stateLine3.append("└" + "─".repeat(state.name().length()) + "┘ ");
		}
		output.add(stateLine1.toString());
		output.add(stateLine2.toString());
		output.add(stateLine3.toString());

		int above = 0;
		int below = 0;
		for(State originState : transitions.keySet()) {
			for(Letter correspondingLetter : transitions.get(originState).keySet()) {
				State destinationState = transitions.get(originState).get(correspondingLetter);
				StringBuilder newLine = new StringBuilder();
				if(states.indexOf(originState) < states.indexOf(destinationState)) {
					int amountOfSpaces = 0;
					for(State state : states.subList(0, states.indexOf(originState))) {
						amountOfSpaces += state.name().length() + 3;
					}
					amountOfSpaces += Math.ceil((originState.name().length()+2)/2);
					newLine.append(" ".repeat(amountOfSpaces));
					newLine.append("┌");
					newLine.append(correspondingLetter);
					int amountOfDashes = 0;
					for(State state : states.subList(0, states.indexOf(destinationState))) {
						amountOfDashes += state.name().length() + 3;
					}
					amountOfDashes -= amountOfSpaces + 4;
					amountOfDashes += Math.floor((destinationState.name().length()+2)/2);
					newLine.append("─".repeat(amountOfDashes));
					newLine.append(">");
					newLine.append("┐");
					int indexBeginningOfArrow = newLine.indexOf("┌");
					int indexEndOfArrow = newLine.indexOf("┐");
					for(int i = 0; i < above ; i++) {
						String lineToChange = output.get(i);
						if(lineToChange.length() <= indexBeginningOfArrow) {
							lineToChange = lineToChange.concat(" ".repeat(indexBeginningOfArrow-lineToChange.length()) + "│");
						} else if(lineToChange.charAt(indexBeginningOfArrow) == ' ') {
							lineToChange = lineToChange.substring(0, indexBeginningOfArrow) + "│" + lineToChange.substring(indexBeginningOfArrow + 1);
						}
						if(lineToChange.length() <= indexEndOfArrow) {
							lineToChange = lineToChange.concat(" ".repeat(indexEndOfArrow-lineToChange.length()) + "│");
						} else if(lineToChange.charAt(indexEndOfArrow) == ' ') {
							lineToChange = lineToChange.substring(0, indexEndOfArrow) + "│" + lineToChange.substring(indexEndOfArrow + 1);
						}
						output.set(i, lineToChange);
					}
					above++;
					output.add(0, newLine.toString());
				} else {
					int amountOfSpaces = 0;
					for(State state : states.subList(0, states.indexOf(destinationState))) {
						amountOfSpaces += state.name().length() + 3;
					}
					amountOfSpaces += Math.ceil((destinationState.name().length()+2)/2);
					newLine.append(" ".repeat(amountOfSpaces));
					newLine.append("└");
					newLine.append("<");
					int amountOfDashes = 0;
					for(State state : states.subList(0, states.indexOf(originState))) {
						amountOfDashes += state.name().length() + 3;
					}
					amountOfDashes -= amountOfSpaces + 4;
					amountOfDashes += Math.floor((originState.name().length()+2)/2);
					newLine.append("─".repeat(amountOfDashes));
					newLine.append(correspondingLetter);
					newLine.append("┘");
					int indexBeginningOfArrow = newLine.indexOf("└");
					int indexEndOfArrow = newLine.indexOf("┘");
					for(int i = output.size()-1 ; i > output.size()-1-below ; i--) {
						String lineToChange = output.get(i);
						if(lineToChange.length() <= indexBeginningOfArrow) {
							lineToChange = lineToChange.concat(" ".repeat(indexBeginningOfArrow-lineToChange.length()) + "│");
						} else if(lineToChange.charAt(indexBeginningOfArrow) == ' ') {
							lineToChange = lineToChange.substring(0, indexBeginningOfArrow) + "│" + lineToChange.substring(indexBeginningOfArrow + 1);
						}
						if(lineToChange.length() <= indexEndOfArrow) {
							lineToChange = lineToChange.concat(" ".repeat(indexEndOfArrow-lineToChange.length()) + "│");
						} else if(lineToChange.charAt(indexEndOfArrow) == ' ') {
							lineToChange = lineToChange.substring(0, indexEndOfArrow) + "│" + lineToChange.substring(indexEndOfArrow + 1);
						}
						output.set(i, lineToChange);
					}
					below++;
					output.add(newLine.toString());
				}
			}
		}

		for (String line : output) System.out.println(line);
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

class CollectionPrinter<T> {
	public static <T> String print(Collection<T> Ts) {
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
		List<State> states = new LinkedList<State>();
		boolean returned;

		do {
			returned = false;
			System.out.println("Current states : " + CollectionPrinter.print(states));
			System.out.print("State name (return to finish) : ");
			currentStringInput = input.nextLine();
			if(currentStringInput == "") {
				if(states.size() == 0) {
					System.out.println("You need to have at least one state");
				}
				else returned = true;
			}
			else if(states.contains(new State(currentStringInput))) {
				System.out.println("The state already exists");
			}
			else states.add(new State(currentStringInput));
		} while (!returned);

		System.out.println("The states will be : " + CollectionPrinter.print(states));
		System.out.println();
		/* End states registration */
		
		/* Begin alphabet registration */
		System.out.println("Let's define the alphabet");
		Set<Letter> alphabet = new HashSet<Letter>();

		char currentLetterInput;

		do {
			returned = false;
			System.out.println("Alphabet : " + CollectionPrinter.print(alphabet));
			System.out.print("Letter (return to finish): ");
			currentStringInput = input.nextLine();
			if(currentStringInput == "") {
				if(alphabet.size() == 0) {
					System.out.println("You need to have at least one letter");
				}
				else returned = true;
			}
			else {
				if(currentStringInput.length() > 1) System.out.println("The first character will be used");
				currentLetterInput = currentStringInput.charAt(0);
				if(alphabet.contains(new Letter(currentLetterInput))) {
					System.out.println("The letter already exists");
				}
				else alphabet.add(new Letter(currentLetterInput));
			}
		} while (!returned);
		
		System.out.println("The alphabet will be : " + CollectionPrinter.print(alphabet));
		System.out.println();
		/* End alphabet registration */

		/* Begin transition registration */
		System.out.println("Let's define the transitions");
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
				repeat = false;
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

				do {
					repeat = false;
					System.out.println("Destination state : ");
					currentStringInput = input.nextLine();
					if(!states.contains(new State(currentStringInput))) {
						System.out.println("The state does not exist");
						repeat = true;
					}
				} while(repeat);
				destinationState = new State(currentStringInput);

				
				do {
					repeat = false;
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

		FiniteAutomaton automaton = new FiniteAutomaton(states, alphabet, transitions);
		
		automaton.print();
	}
}