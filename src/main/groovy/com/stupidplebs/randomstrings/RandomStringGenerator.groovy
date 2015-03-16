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

        public Builder() {
            this.randomnessProvider = new JavaUtilRandomnessProvider()
        }
        
        public Builder(final RandomnessProvider randomnessProvider) {
            this.randomnessProvider = randomnessProvider
        }
        
        public Builder randomUpperLimit(Integer randomUpperLimit) {
            this.randomUpperLimit = randomUpperLimit
			this
        }
        
        public Builder is(value) {
            sb.append(value.toString())
            this
        }
        
        public Builder optional(value) {
            randomnessProvider.nextBoolean() ? is(value) : this
        }
        
        // RANDOM CHARACTERS
        public Builder randomCharactersOf(String s, n=1) {
            sb.append((0..<n).collect {
                s[ randomnessProvider.nextInt(s.length())]
            }.join())
            this
        }
        
        // EXACTLY ONE
        public Builder oneCharacterOf(String chars) {
            randomCharactersOf(chars, 1)
        }
        
        public Builder oneLetter() {
            randomCharactersOf(ALL_LETTERS, 1)
        }
        
        public Builder oneNumber() {
            randomCharactersOf(NUMBERS, 1)
        }
        
        public Builder oneLowercaseLetter() {
            randomCharactersOf(LOWERCASE_LETTERS, 1)
        }
        
        public Builder oneUppercaseLetter() {
            randomCharactersOf(UPPERCASE_LETTERS, 1)
        }
        
        public Builder oneWhitespaceCharacter() {
            randomCharactersOf(WHITESPACE, 1)
        }
        
        public Builder oneElementOf(List l) {
            is(l[randomnessProvider.nextInt(l.size())])
        }
        
        public Builder oneKeyOf(Map m) {
            is(m.collect{ it.key }[randomnessProvider.nextInt(m.size())])
        }
        
        public Builder oneValueOf(Map m) {
            is(m.collect{ it.value }[randomnessProvider.nextInt(m.size())])
        }
        
        // ZERO OR MORE
        public Builder zeroOrMoreCharactersOf(String chars, Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(chars, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(ALL_LETTERS, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreLowercaseLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(LOWERCASE_LETTERS, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreUppercaseLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(UPPERCASE_LETTERS, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreNumbers(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(NUMBERS, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreWhitespaceCharacters(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(WHITESPACE, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        // AT LEAST ONE
        public Builder atLeastOneCharacterOf(String chars, Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(chars, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneLetter(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(ALL_LETTERS, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneLowercaseLetter(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(LOWERCASE_LETTERS, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneUppercaseLetter(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(UPPERCASE_LETTERS, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneNumber(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(NUMBERS, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneWhitespaceCharacter(Integer butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(WHITESPACE, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        // OPTIONAL
        public Builder optionalCharactersOf(String chars, Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(chars, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(ALL_LETTERS, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalNumbers(Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(NUMBERS, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalLowercaseLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(LOWERCASE_LETTERS, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalUppercaseLetters(Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(UPPERCASE_LETTERS, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalWhitespaceCharacters(Integer butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(WHITESPACE, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalCharacterOf(String chars) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(chars, 1) : this
        }
        
        public Builder optionalLetter() {
            randomnessProvider.nextBoolean() ? randomCharactersOf(ALL_LETTERS, 1) : this
        }
        
        public Builder optionalNumber() {
            randomnessProvider.nextBoolean() ? randomCharactersOf(NUMBERS, 1) : this
        }
        
        public Builder optionalLowercaseLetter() {
            randomnessProvider.nextBoolean() ? randomCharactersOf(LOWERCASE_LETTERS, 1) : this
        }
        
        public Builder optionalUppercaseLetter() {
            randomnessProvider.nextBoolean() ? randomCharactersOf(UPPERCASE_LETTERS, 1) : this
        }
        
        public Builder optionalWhitespaceCharacter() {
            randomnessProvider.nextBoolean() ? randomCharactersOf(WHITESPACE, 1) : this
        }
        
        public Builder optionalElementOf(List l) {
            randomnessProvider.nextBoolean() ? oneElementOf(l) : this
        }
        
        public Builder optionalKeyOf(Map m) {
            randomnessProvider.nextBoolean() ? oneKeyOf(m) : this
        }
        
        public Builder optionalValueOf(Map m) {
            randomnessProvider.nextBoolean() ? oneValueOf(m) : this
        }
        
		public Builder nCharactersOf(String chars, Integer n) {
			randomCharactersOf(chars, n)
		}
		
		public Builder nLetters(Integer n) {
			randomCharactersOf(ALL_LETTERS, n)
		}
		
		public Builder nNumbers(Integer n) {
			randomCharactersOf(NUMBERS, n)
		}
	
		public Builder nLowercaseLetters(Integer n) {
			randomCharactersOf(LOWERCASE_LETTERS, n)			
		}
	
		public Builder nUppercaseLetters(Integer n) {
			randomCharactersOf(UPPERCASE_LETTERS, n)
		}
	
		public Builder nWhitespaceCharacters(Integer n) {
			randomCharactersOf(WHITESPACE, n)
		}
		
        public String build() {
            sb.toString()
        }
        
    }
    
}
