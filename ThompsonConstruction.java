import java.util.*;

class NFAState {
    int id;
    Map<Character, List<NFAState>> transitions;
    List<NFAState> epsilonTransitions;

    public NFAState(int id) {
        this.id = id;
        this.transitions = new HashMap<>();
        this.epsilonTransitions = new ArrayList<>();
    }

    public void addTransition(char symbol, NFAState state) {
        transitions.computeIfAbsent(symbol, k -> new ArrayList<>()).add(state);
    }

    public void addEpsilonTransition(NFAState state) {
        epsilonTransitions.add(state);
    }
}

class NFA {
    NFAState startState;
    NFAState acceptState;

    public NFA(NFAState startState, NFAState acceptState) {
        this.startState = startState;
        this.acceptState = acceptState;
    }

    public static NFA concatenate(NFA nfa1, NFA nfa2) {
        nfa1.acceptState.addEpsilonTransition(nfa2.startState);
        return new NFA(nfa1.startState, nfa2.acceptState);
    }

    public static NFA union(NFA nfa1, NFA nfa2, int stateIdCounter) {
        NFAState newStart = new NFAState(stateIdCounter++);
        NFAState newAccept = new NFAState(stateIdCounter);

        newStart.addEpsilonTransition(nfa1.startState);
        newStart.addEpsilonTransition(nfa2.startState);
        nfa1.acceptState.addEpsilonTransition(newAccept);
        nfa2.acceptState.addEpsilonTransition(newAccept);

        return new NFA(newStart, newAccept);
    }

    public static NFA kleeneStar(NFA nfa, int stateIdCounter) {
        NFAState newStart = new NFAState(stateIdCounter++);
        NFAState newAccept = new NFAState(stateIdCounter);

        newStart.addEpsilonTransition(nfa.startState);
        newStart.addEpsilonTransition(newAccept);
        nfa.acceptState.addEpsilonTransition(nfa.startState);
        nfa.acceptState.addEpsilonTransition(newAccept);

        return new NFA(newStart, newAccept);
    }
}

public class ThompsonConstruction {

    private static int stateIdCounter = 0;

    public static NFA regexToNFA(String regex) {
        Stack<NFA> nfaStack = new Stack<>();

        for (char ch : regex.toCharArray()) {
            switch (ch) {
                case '*': {
                    NFA nfa = nfaStack.pop();
                    nfaStack.push(NFA.kleeneStar(nfa, stateIdCounter));
                    stateIdCounter += 2;
                    break;
                }
                case '|': {
                    NFA nfa2 = nfaStack.pop();
                    NFA nfa1 = nfaStack.pop();
                    nfaStack.push(NFA.union(nfa1, nfa2, stateIdCounter));
                    stateIdCounter += 2;
                    break;
                }
                case '.': {
                    NFA nfa2 = nfaStack.pop();
                    NFA nfa1 = nfaStack.pop();
                    nfaStack.push(NFA.concatenate(nfa1, nfa2));
                    break;
                }
                default: {
                    NFAState start = new NFAState(stateIdCounter++);
                    NFAState accept = new NFAState(stateIdCounter++);
                    start.addTransition(ch, accept);
                    nfaStack.push(new NFA(start, accept));
                    break;
                }
            }
        }

        return nfaStack.pop();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a regular expression: ");
        String regex = scanner.nextLine();

        NFA nfa = regexToNFA(regex);
        System.out.println("NFA successfully created for the given regex!");
    }
}