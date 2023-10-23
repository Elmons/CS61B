public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> deque = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return palindromeHelper(deque);
    }

    private boolean palindromeHelper(Deque<Character> deque) {
        if (deque.size() == 1 || deque.size() == 0) {
            return true;
        } else if (deque.removeFirst() == deque.removeLast()) {
            return palindromeHelper(deque);
        } else {
            return  false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return palindromeHelper(deque, cc);
    }

    private boolean palindromeHelper(Deque<Character> deque, CharacterComparator cc) {
        if (deque.size() == 1 || deque.size() == 0) {
            return true;
        } else if (cc.equalChars(deque.removeFirst(), deque.removeLast())) {
            return palindromeHelper(deque, cc);
        } else {
            return  false;
        }
    }
}
