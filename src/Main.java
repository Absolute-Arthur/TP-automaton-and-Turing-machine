import java.util.Map;
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
	public String name;

	public State(String name){
		this.name = name;
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
		System.out.println("Bonjour !");
		
	}
}


