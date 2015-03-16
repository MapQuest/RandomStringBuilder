package com.stupidplebs.randomstrings

class RandomStringGenerator {
    static final def allLetters = (('a'..'z') + ('A'..'Z')).join()
    static final def numbers = ('0'..'9').join()
    static final def lowercaseLetters = ('a'..'z').join()
    static final def uppercaseLetters = ('A'..'Z').join()
    static final def whitespace = " \t"
    
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
        
        public Builder is(s) {
            sb.append(s.toString())
            this
        }
        
        public Builder optional(s) {
            randomnessProvider.nextBoolean() ? is(s) : this
        }
        
        // RANDOM CHARACTERS
        public Builder randomCharactersOf(s, count=1) {
            sb.append((0..<count).collect {
                s[ randomnessProvider.nextInt(s.length())]
            }.join())
            this
        }
        
        // EXACTLY ONE
        public Builder oneCharacterOf(chars) {
            randomCharactersOf(chars, 1)
        }
        
        public Builder oneLetter() {
            randomCharactersOf(allLetters, 1)
        }
        
        public Builder oneNumber() {
            randomCharactersOf(numbers, 1)
        }
        
        public Builder oneLowercaseLetter() {
            randomCharactersOf(lowercaseLetters, 1)
        }
        
        public Builder oneUppercaseLetter() {
            randomCharactersOf(uppercaseLetters, 1)
        }
        
        public Builder oneWhitespaceCharacter() {
            randomCharactersOf(whitespace, 1)
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
        public Builder zeroOrMoreCharactersOf(s, butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(s, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreLetters(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(allLetters, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreLowercaseLetters(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(lowercaseLetters, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreUppercaseLetters(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(uppercaseLetters, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreNumbers(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(numbers, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreWhitespaceCharacters(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(whitespace, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        // AT LEAST ONE
        public Builder atLeastOneCharacterOf(chars, butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(chars, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneLetter(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(allLetters, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneLowercaseLetter(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(lowercaseLetters, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneUppercaseLetter(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(uppercaseLetters, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneNumber(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(numbers, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneWhitespaceCharacter(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(whitespace, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        // OPTIONAL
        public Builder optionalCharactersOf(s, butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(s, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalLetters(butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(allLetters, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalNumbers(butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(numbers, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalLowercaseLetters(butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(lowercaseLetters, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalUppercaseLetters(butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(uppercaseLetters, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalWhitespaceCharacters(butNoMoreThan=randomUpperLimit) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(whitespace, randomnessProvider.nextInt(butNoMoreThan)) : this
        }
        
        public Builder optionalCharacterOf(s) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(s, 1) : this
        }
        
        public Builder optionalLetter() {
            randomnessProvider.nextBoolean() ? randomCharactersOf(allLetters, 1) : this
        }
        
        public Builder optionalNumber() {
            randomnessProvider.nextBoolean() ? randomCharactersOf(numbers, 1) : this
        }
        
        public Builder optionalLowercaseLetter() {
            randomnessProvider.nextBoolean() ? randomCharactersOf(lowercaseLetters, 1) : this
        }
        
        public Builder optionalUppercaseLetter() {
            randomnessProvider.nextBoolean() ? randomCharactersOf(uppercaseLetters, 1) : this
        }
        
        public Builder optionalWhitespaceCharacter() {
            randomnessProvider.nextBoolean() ? randomCharactersOf(whitespace, 1) : this
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
        
		public Builder nCharactersOf(s, Integer n) {
			randomCharactersOf(s, n)
		}
		
		public Builder nLetters(Integer n) {
			randomCharactersOf(allLetters, n)
		}
		
		public Builder nNumbers(Integer n) {
			randomCharactersOf(numbers, n)
		}
	
		public Builder nLowercaseLetters(Integer n) {
			randomCharactersOf(lowercaseLetters, n)			
		}
	
		public Builder nUppercaseLetters(Integer n) {
			randomCharactersOf(uppercaseLetters, n)
		}
	
		public Builder nWhitespaceCharacters(Integer n) {
			randomCharactersOf(whitespace, n)
		}
		
        public String build() {
            sb.toString()
        }
        
    }
    
}
