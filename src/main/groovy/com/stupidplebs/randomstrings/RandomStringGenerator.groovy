package com.stupidplebs.randomstrings

class RandomStringGenerator {
    static final def ALL_LETTERS = (('a'..'z') + ('A'..'Z')).join()
    static final def NUMBERS = ('0'..'9').join()
    static final def LOWERCASE_LETTERS = ('a'..'z').join()
    static final def UPPERCASE_LETTERS = ('A'..'Z').join()
    static final def WHITESPACE = " \t"

    private RandomStringGenerator() {}

    public static class Builder {
        def final RandomnessProvider randomnessProvider
        def final StringBuilder sb = new StringBuilder()
        def Integer randomUpperLimit = 25

        /**
         * Constructor that defaults to using the JDK random number 
         * generator
         */
        public Builder() {
            this.randomnessProvider = new JavaUtilRandomnessProvider()
        }

        /** 
         * Constructor that takes a RandomnessProvider that will handle 
         * generating random booleans and integers.  It's really only 
         * exposed for unit testing purposes, but an implementation could
         * be supplied for testing other conditions.  
         * 
         * @param randomnessProvider
         */
        public Builder(final RandomnessProvider randomnessProvider) {
            this.randomnessProvider = randomnessProvider
        }

        /**
         * Set the upper limit on random number of characters to append
         * 
         * @param randomUpperLimit
         * @return
         */
        public Builder randomUpperLimit(Integer randomUpperLimit) {
            this.randomUpperLimit = randomUpperLimit
            this
        }

        /**
         * Append the supplied value
         * 
         * @param value
         * @return
         */
        public Builder is(value) {
            sb.append(value.toString())
            this
        }

        /**
         * Optionally append the supplied value
         * 
         * @param value
         * @return
         */
        public Builder optional(value) {
            randomnessProvider.nextBoolean() ? is(value) : this
        }

        /**
         * Append up to n characters from s
         * 
         * @param s
         * @param n - the number of characters to append, defaults to 1
         * @return
         */
        public Builder randomCharactersOf(String s, n=1) {
            sb.append((0..<n).collect {
                s[randomnessProvider.nextInt(s.length())]
            }.join())
            this
        }

        /**
         * Append exactly 1 character from chars
         * 
         * @param chars
         * @return
         */
        public Builder oneCharacterOf(String chars) {
            randomCharactersOf(chars, 1)
        }

        /**
         * Append exactly 1 uppercase or lowercase letters
         * 
         * @return
         */
        public Builder oneLetter() {
            randomCharactersOf(ALL_LETTERS, 1)
        }

        /**
         * Append exactly 1 number
         * 
         * @return
         */
        public Builder oneNumber() {
            randomCharactersOf(NUMBERS, 1)
        }

        /**
         * Append exactly 1 lowercase letter
         * 
         * @return
         */
        public Builder oneLowercaseLetter() {
            randomCharactersOf(LOWERCASE_LETTERS, 1)
        }

        /**
         * Append exactly 1 uppercase letter
         * 
         * @return
         */
        public Builder oneUppercaseLetter() {
            randomCharactersOf(UPPERCASE_LETTERS, 1)
        }

        /**
         * Append exactly 1 whitespace character
         * 
         * @return
         */
        public Builder oneWhitespaceCharacter() {
            randomCharactersOf(WHITESPACE, 1)
        }

        /**
         * Append exactly one element of the supplied list
         * 
         * @param l
         * @return
         */
        public Builder oneElementOf(List l) {
            is(l[randomnessProvider.nextInt(l.size())])
        }

        /**
         * Append exactly one key of the supplied map
         * 
         * @param m
         * @return
         */
        public Builder oneKeyOf(Map m) {
            is(m.collect{ it.key }[randomnessProvider.nextInt(m.size())])
        }

        /**
         * Append exactly one value of the supplied map
         * 
         * @param m
         * @return
         */
        public Builder oneValueOf(Map m) {
            is(m.collect{ it.value }[randomnessProvider.nextInt(m.size())])
        }

        /**
         * Append 0 or more characters (up to butNoMoreThan) from n
         * 
         * @param chars - 
         * @param butNoMoreThan - the max number of characters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder zeroOrMoreCharactersOf(String chars, Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(chars, randomnessProvider.nextInt(butNoMoreThan))
        }

        /**
         * Append 0 or more letters (up to butNoMoreThan)
         * 
         * @param butNoMoreThan - the max number of letters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder zeroOrMoreLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(ALL_LETTERS, randomnessProvider.nextInt(butNoMoreThan))
        }

        /**
         * Append 0 or more lowercase letters (up to butNoMoreThan)
         * 
         * @param butNoMoreThan - the max number of letters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder zeroOrMoreLowercaseLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(LOWERCASE_LETTERS, randomnessProvider.nextInt(butNoMoreThan))
        }

        /**
         * Append 0 or more uppercase letters (up to butNoMoreThan)
         * 
         * @param butNoMoreThan - the max number of letters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder zeroOrMoreUppercaseLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(UPPERCASE_LETTERS, randomnessProvider.nextInt(butNoMoreThan))
        }

        /**
         * Append 0 or more numbers (up to butNoMoreThan)
         * 
         * @param butNoMoreThan - the max number of numbers to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder zeroOrMoreNumbers(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(NUMBERS, randomnessProvider.nextInt(butNoMoreThan))
        }

        /**
         * Append 0 or more whitespace characters (up to butNoMoreThan)
         * 
         * @param butNoMoreThan - the max number of whitespace characters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder zeroOrMoreWhitespaceCharacters(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(WHITESPACE, randomnessProvider.nextInt(butNoMoreThan))
        }

        /**
         * Append 1 or more characters (up to butNoMoreThan) from n
         * 
         * @param chars - 
         * @param butNoMoreThan - the max number of characters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder atLeastOneCharacterOf(String chars, Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(chars, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }

        /**
         * Append 1 or more letters (up to butNoMoreThan)
         * 
         * @param butNoMoreThan - the max number of letters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder atLeastOneLetter(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(ALL_LETTERS, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }

        /**
         * Append 1 or more lowercase letters (up to butNoMoreThan)
         * 
         * @param butNoMoreThan - the max number of letters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder atLeastOneLowercaseLetter(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(LOWERCASE_LETTERS, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }

        /**
         * Append 1 or more uppercase letters (up to butNoMoreThan)
         * 
         * @param butNoMoreThan - the max number of letters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder atLeastOneUppercaseLetter(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(UPPERCASE_LETTERS, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }

        /**
         * Append 1 or more numbers (up to butNoMoreThan)
         * 
         * @param butNoMoreThan - the max number of numbers to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder atLeastOneNumber(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(NUMBERS, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }

        /**
         * Append 1 or more whitespace characters (up to butNoMoreThan)
         * 
         * @param butNoMoreThan - the max number of whitespace characters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder atLeastOneWhitespaceCharacter(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(WHITESPACE, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }

        /**
         * Optionally append up to butNoMoreThan characters from chars   
         *     
         * @param chars
         * @param butNoMoreThan - the max number of characters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder optionalCharactersOf(String chars, Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? atLeastOneCharacterOf(chars, butNoMoreThan) : this
        }

        /**
         * Optionally append up to butNoMoreThan letters
         *  
         * @param butNoMoreThan - the max number of letters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder optionalLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? atLeastOneLetter(butNoMoreThan) : this
        }

        /**
         * Optionally append up to butNoMoreThan numbers
         * 
         * @param butNoMoreThan - the max number of numbers to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder optionalNumbers(Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? atLeastOneNumber(butNoMoreThan) : this
        }

        /**
         * Optionally append up to butNoMoreThan lowercase letters
         *  
         * @param butNoMoreThan - the max number of letters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder optionalLowercaseLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? atLeastOneLowercaseLetter(butNoMoreThan) : this
        }

        /**
         * Optionally append up to butNoMoreThan uppercase letters
         *  
         * @param butNoMoreThan - the max number of letters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder optionalUppercaseLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? atLeastOneUppercaseLetter(butNoMoreThan) : this
        }

        /**
         * Optionally append up to butNoMoreThan whitespace characters
         *  
         * @param butNoMoreThan - the max number of letters to append, defaulting to the randomUpperLimit
         * @return
         */
        public Builder optionalWhitespaceCharacters(Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? atLeastOneWhitespaceCharacter(butNoMoreThan) : this
        }

        /**
         * Optionally append 1 character from chars   
         *     
         * @param chars
         * @return
         */
        public Builder optionalCharacterOf(String chars) {
            randomnessProvider.nextBoolean() ? oneCharacterOf(chars) : this
        }

        /**
         * Optionally append 1 letter
         *     
         * @return
         */
        public Builder optionalLetter() {
            randomnessProvider.nextBoolean() ? oneLetter() : this
        }

        /**
         * Optionally append 1 number
         *     
         * @return
         */
        public Builder optionalNumber() {
            randomnessProvider.nextBoolean() ? oneNumber() : this
        }

        /**
         * Optionally append 1 lowercase letter
         *     
         * @return
         */
        public Builder optionalLowercaseLetter() {
            randomnessProvider.nextBoolean() ? oneLowercaseLetter() : this
        }

        /**
         * Optionally append 1 uppercase letter
         *     
         * @return
         */
        public Builder optionalUppercaseLetter() {
            randomnessProvider.nextBoolean() ? oneUppercaseLetter() : this
        }

        /**
         * Optionally append 1 whitespace character
         *     
         * @return
         */
        public Builder optionalWhitespaceCharacter() {
            randomnessProvider.nextBoolean() ? oneWhitespaceCharacter() : this
        }

        /**
         * Optionally append 1 space
         *     
         * @return
         */
        public Builder optionalSpace() {
            randomnessProvider.nextBoolean() ? is(' ') : this
        }

        /**
         * Optionally append 1 element from l
         * 
         * @param l
         * @return
         */
        public Builder optionalElementOf(List l) {
            randomnessProvider.nextBoolean() ? oneElementOf(l) : this
        }

        /**
         * Optionally append 1 key from m
         * 
         * @param l
         * @return
         */
        public Builder optionalKeyOf(Map m) {
            randomnessProvider.nextBoolean() ? oneKeyOf(m) : this
        }

        /**
         * Optionally append 1 value from m
         * 
         * @param l
         * @return
         */
        public Builder optionalValueOf(Map m) {
            randomnessProvider.nextBoolean() ? oneValueOf(m) : this
        }

        /**
         * Append n characters from chars
         *
         * @param chars
         * @param l
         * @return
         */
        public Builder nCharactersOf(String chars, Integer n) {
            randomCharactersOf(chars, n)
        }

        /**
         * Append n letters
         * 
         * @param n
         * @return
         */
        public Builder nLetters(Integer n) {
            randomCharactersOf(ALL_LETTERS, n)
        }

        /**
         * Append n numbers
         * 
         * @param n
         * @return
         */
        public Builder nNumbers(Integer n) {
            randomCharactersOf(NUMBERS, n)
        }

        /**
         * Append n lowercase letters
         * 
         * @param n
         * @return
         */
        public Builder nLowercaseLetters(Integer n) {
            randomCharactersOf(LOWERCASE_LETTERS, n)
        }

        /**
         * Append n uppercase letters
         * 
         * @param n
         * @return
         */
        public Builder nUppercaseLetters(Integer n) {
            randomCharactersOf(UPPERCASE_LETTERS, n)
        }

        /**
         * Append n whitespace characters
         * 
         * @param n
         * @return
         */
        public Builder nWhitespaceCharacters(Integer n) {
            randomCharactersOf(WHITESPACE, n)
        }

        /**
         * Return the assembled string built so far 
         * 
         * @return The assembled string
         */
        public String build() {
            sb.toString()
        }

    }

}
